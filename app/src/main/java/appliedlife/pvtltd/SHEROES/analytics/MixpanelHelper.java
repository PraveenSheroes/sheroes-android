package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.SuperPropertyUpdate;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import io.branch.referral.Branch;

@Singleton
public class MixpanelHelper {

    public static final String SCREEN_OPEN = "Screen open";
    public static final String SCREEN_NAME = "Screen name";

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<Configuration> mConfiguration;

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

            //set user details in fabric
            Crashlytics.getInstance().core.setUserIdentifier(Long.toString(userSummary.getUserId()));
            Crashlytics.getInstance().core.setUserEmail(userSummary.getEmailId());
            Crashlytics.getInstance().core.setUserName(userSummary.getFirstName() + " " + userSummary.getLastName());

            final SuperProperty.Builder superPropertiesBuilder = new SuperProperty.Builder()
                    .userId(Long.toString(userSummary.getUserId()))
                    .userName(userSummary.getFirstName() + " " + userSummary.getLastName())
                    .dateOfBirth(userSummary.getUserBO().getDob())
                    .createdDate(userSummary.getUserBO().getCrdt())
                    .mobileNumber(userSummary.getMobile())
                    .appsflyerID(AppsFlyerLib.getInstance().getAppsFlyerUID(context))
                    .configType(mConfiguration.isSet() && mConfiguration.get() != null && mConfiguration.get().configType != null ? mConfiguration.get().configType : "")
                    .configVersion(mConfiguration.isSet() && mConfiguration.get() != null && mConfiguration.get().configVersion != null ? mConfiguration.get().configVersion : "")
                    .emailId(userSummary.getEmailId());

        /*int year = YearClass.get(CareApplication.getAppContext());
        if (year > 0) {
            superPropertiesBuilder.mobileYear(Integer.toString(year));
        }*/
            // TODO: check if install id and device id are same
           /* if (EncryptionUtils.getInstance().getDeviceId(SheroesApplication.mContext) != null) {
                superPropertiesBuilder.installId(EncryptionUtils.getInstance().getDeviceId(SheroesApplication.mContext));
            }*/

            if (isNewUser) {
                mixpanel.registerSuperPropertiesOnce(superPropertiesBuilder.build());
            } else {
                SuperPropertyUpdate superPropertyUpdate = new SuperPropertyUpdate() {
                    @Override
                    public JSONObject update(JSONObject jsonObject) {
                        return superPropertiesBuilder.build();
                    }
                };
                mixpanel.updateSuperProperties(superPropertyUpdate);
            }
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

            if (!TextUtils.isEmpty(userSummary.getMobile())) {
                mixpanel.getPeople().set("$name", userSummary.getFirstName() + " " + userSummary.getLastName());
            }

            if (!TextUtils.isEmpty(userSummary.getFirstName())) {
                mixpanel.getPeople().set("Display Name", userSummary.getFirstName() + " " + userSummary.getLastName());
            }

            if (!TextUtils.isEmpty(userSummary.getEmailId())) {
                mixpanel.getPeople().set("$email", userSummary.getEmailId());
            }

            if (!TextUtils.isEmpty(userSummary.getMobile())) {
                mixpanel.getPeople().set("$mobile", userSummary.getMobile());
            }

            mixpanel.getPeople().setOnce("$created", userSummary.getUserBO().getCrdt());
        }

        Branch.getInstance().setIdentity(Long.toString(userSummary.getUserId()));

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

    public static void trackPostActionEvent(Event event, FeedDetail feedDetail, String screenName) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            UserPostSolrObj userPostSolrObj = null;
            if(feedDetail instanceof UserPostSolrObj){
                userPostSolrObj = (UserPostSolrObj) feedDetail;
            }
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                            .postId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                            .communityName(userPostSolrObj!=null ? userPostSolrObj.getPostCommunityName() : "")
                            .title(feedDetail.getNameOrTitle())
                            .type(getTypeFromSubtype(feedDetail.getSubType()))
                            .positionInList(feedDetail.getItemPosition())
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);
            AnalyticsManager.trackEvent(event, feedDetail.getScreenName(), properties);
        }
    }

    public static HashMap<String, Object> getPostProperties(FeedDetail feedDetail, String screenName) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            UserPostSolrObj userPostSolrObj = null;
            if(feedDetail instanceof UserPostSolrObj){
                userPostSolrObj = (UserPostSolrObj) feedDetail;
            }
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                            .postId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                            .communityName(userPostSolrObj!=null ? userPostSolrObj.getPostCommunityName() : "")
                            .title(feedDetail.getNameOrTitle())
                            .type(getTypeFromSubtype(feedDetail.getSubType()))
                            .positionInList(feedDetail.getItemPosition())
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);
            return properties;
        }else {
            return null;
        }
    }

    //TODO - Fix this with ujjwal
    public static void trackCommunityEvent(Event event, CommunityFeedSolrObj communityDetails, String screenName, String positionInCarousel, String positionOfCarousel) {
        if (StringUtil.isNotNullOrEmptyString(communityDetails.getSubType())) {
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(communityDetails.getIdOfEntityOrParticipant()))
                            .title(communityDetails.getNameOrTitle())
                            .communityCategory(communityDetails.getCommunityType())
                            .positionInCarousel(positionInCarousel)
                            .positionOfCarousel(positionOfCarousel)
                            .type(getTypeFromSubtype(communityDetails.getSubType()))
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);

            AnalyticsManager.trackEvent(event, communityDetails.getScreenName(), properties);
        }
    }

    public static void trackCommunityEvent(Event event, CommunityFeedSolrObj communityDetails, String screenName) {
        if (StringUtil.isNotNullOrEmptyString(communityDetails.getSubType())) {
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(communityDetails.getIdOfEntityOrParticipant()))
                            .title(communityDetails.getNameOrTitle())
                            .communityCategory(communityDetails.getCommunityType())
                            .type(getTypeFromSubtype(communityDetails.getSubType()))
                            .positionInList(communityDetails.getItemPosition())
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);

            AnalyticsManager.trackEvent(event, communityDetails.getScreenName(), properties);
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

