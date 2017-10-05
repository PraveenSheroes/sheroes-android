package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;

import com.f2prateek.rx.preferences.Preference;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.MixpanelAPI.People;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageEvent;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;

@Singleton
public class MixpanelHelper {

    public static final String SCREEN_OPEN = "Screen open";
    public static final String SCREEN_NAME = "Screen name";

    @Inject
    Preference<LoginResponse> mUserPreference;

    /**
     * Should be run on login or app open
     *
     * @param context
     */
    public void setupMixpanel(Context context) {
        //Mixpanel
        MixpanelHelper.getInstance(context);
    }

    public void setupUser(Context context, final Boolean isNewUser) {
        SheroesApplication.getAppComponent(context).inject(this);
        UserSummary userSummary = null;
        if (mUserPreference == null) {
            return;
        }
        if (mUserPreference.isSet() && mUserPreference.get() != null && mUserPreference.get().getUserSummary() != null) {
            userSummary = mUserPreference.get().getUserSummary();
        }
        if (userSummary == null) {
            return;
        }
      /*  User currentUser = User.getCurrentUser();
        Installation installation = Installation.getCurrentInstallation();*/

        MixpanelAPI mixpanel = MixpanelHelper.getInstance(context);

        if (userSummary != null) {
            mixpanel.identify(Long.toString(userSummary.getUserId()));
            mixpanel.getPeople().identify(Long.toString(userSummary.getUserId()));

            SuperProperty.Builder superPropertiesBuilder = new SuperProperty.Builder()
                    .userId(Long.toString(userSummary.getUserId()))
                    .firstName(userSummary.getFirstName())
                    .lastName(userSummary.getLastName())
                    .dateOfBirth(userSummary.getUserBO().getDob())
                    .createdDate(userSummary.getUserBO().getCrdt())
                    .mobileNumber(userSummary.getMobile())
                    .emailId(userSummary.getEmailId());

        /*int year = YearClass.get(CareApplication.getAppContext());
        if (year > 0) {
            superPropertiesBuilder.mobileYear(Integer.toString(year));
        }*/
            // TODO: check if install id and device id are same
           /* if (EncryptionUtils.getInstance().getDeviceId(SheroesApplication.mContext) != null) {
                superPropertiesBuilder.installId(EncryptionUtils.getInstance().getDeviceId(SheroesApplication.mContext));
            }*/

            mixpanel.registerSuperPropertiesOnce(superPropertiesBuilder.build());

            if (userSummary.getUserBO().getInterestLabel() != null && !userSummary.getUserBO().getInterestLabel().isEmpty()) {
                mixpanel.getPeople().set(PeopleProperty.INTEREST.getString(), userSummary.getUserBO().getInterestLabel());
            }

            if (userSummary.getUserBO().getSkillsLabel() != null && !userSummary.getUserBO().getSkillsLabel().isEmpty()) {
                mixpanel.getPeople().set(PeopleProperty.SKILLS.getString(), userSummary.getUserBO().getSkillsLabel());
            }

            if (userSummary.getUserBO().getJobTag() != null && !userSummary.getUserBO().getJobTag().isEmpty()) {
                mixpanel.getPeople().set(PeopleProperty.CURRENT_STATUS.getString(), userSummary.getUserBO().getJobTag());
            }

            mixpanel.getPeople().set(PeopleProperty.WORK_EXPERIENCE.getString(), userSummary.getUserBO().getTotalExp());

            //mixpanel.getPeople().setOnce("$created", userSummary.get);
        }

    }

    public static MixpanelAPI getInstance(Context context) {
        MixpanelAPI mixpanel;
        if (BuildConfig.DEBUG) {
            mixpanel = MixpanelAPI.getInstance(context, "11ca4132b26aa31fd05c8d6139c7036d");
        } else {
            mixpanel = MixpanelAPI.getInstance(context, "d26c5bc4799a617871f3eab3cb6d5c91");
        }
        return mixpanel;
    }

    public static People getPeople(Context context) {
        return getInstance(context).getPeople();
    }

    public static void trackEvent(Context context, String eventName, Map<String, Object> properties) {
        if (properties == null) {
            properties = new HashMap<>();
        }
        JSONObject jsonObj = new JSONObject(properties);

        getInstance(context).track(eventName, jsonObj);
    }

    public static void trackScreenOpen(Context context, String screenName, Map<String, Object> properties) {
        if (properties == null) {
            properties = new HashMap<>();
        }
        properties.put(MixpanelHelper.SCREEN_NAME, screenName);

        trackEvent(context, SCREEN_OPEN, properties);
    }

    public static void timeScreenOpen(Context context, String screenName) {
        timeEvent(context, SCREEN_OPEN);
    }

    public static void trackScreenOpen(Context context,
                                       String screenName, String source, Map<String, Object> properties) {
        if (properties == null) {
            properties = new HashMap<String, Object>();
        }
        if (screenName != null)
            properties.put(MixpanelHelper.SCREEN_NAME, screenName);

        if (source != null)
            properties.put("Source", source);

        trackEvent(context, SCREEN_OPEN, properties);
    }

    public static void flushEvents(Context context) {
        if (context != null) {
            getInstance(context).flush();
        }
    }

    public static void timeEvent(final Context context, String eventName) {
        getInstance(context).timeEvent(eventName);
    }

    public static void clearMixpanel(final Context context) {
        getInstance(context).clearSuperProperties();
    }

    static void incrementAppOpens(Context context) {
        getInstance(context).getPeople().increment("App opens", 1);
    }

    public static void trackPostActionEvent(Event event, FeedDetail feedDetail) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                            .title(feedDetail.getNameOrTitle())
                            .type(getTypeFromSubtype(feedDetail.getSubType()))
                            .positionInList(feedDetail.getItemPosition())
                            .build();
            AnalyticsManager.trackEvent(event, feedDetail.getScreenName(), properties);
        }
    }

    private static String getTypeFromSubtype(String subType) {
        String type = null;
        switch (subType) {
            case AppConstants.FEED_ARTICLE:
                type = MoEngageConstants.ARTICLE;
                break;

            case AppConstants.FEED_COMMUNITY_POST:
                type = MoEngageConstants.COMMUNITY_POST;
                break;
            case AppConstants.FEED_JOB:
                type = MoEngageConstants.JOB;
                break;
        }
        return type;
    }
}
