package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.appevents.AppEventsLogger;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.SuperPropertyUpdate;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import io.branch.referral.Branch;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.LANGUAGE_KEY;

@Singleton
public class MixpanelHelper {

    public static final String SCREEN_OPEN = "Screen open";
    public static final String SCREEN_NAME = "Screen name";
    public static final String COMMUNITY_POST = "community post";
    public static final String ARTICLE = "article";

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<AppConfiguration> mConfiguration;

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
        if (mUserPreference.isSet() && mUserPreference.get().getUserSummary() != null) {
            userSummary = mUserPreference.get().getUserSummary();
        }
        if (userSummary == null) {
            return;
        }
      /*  User currentUser = User.getCurrentUser();
        Installation installation = Installation.getCurrentInstallation();*/

        MixpanelAPI mixpanel = MixpanelHelper.getInstance(context);

        if (mixpanel != null) {
            mixpanel.identify(Long.toString(userSummary.getUserId()));
            mixpanel.getPeople().identify(Long.toString(userSummary.getUserId()));

            //set user details in fabric
            Crashlytics.getInstance().core.setUserIdentifier(Long.toString(userSummary.getUserId()));
            Crashlytics.getInstance().core.setUserEmail(userSummary.getEmailId());
            Crashlytics.getInstance().core.setUserName(userSummary.getFirstName() + " " + userSummary.getLastName());

            String setOrderKey = CommonUtil.getPref(AppConstants.SET_ORDER_KEY);
            String feedConfigVersion = CommonUtil.getPref(AppConstants.FEED_CONFIG_VERSION);
            String languageName= CommonUtil.getPrefStringValue(LANGUAGE_KEY);
            final SuperProperty.Builder superPropertiesBuilder = new SuperProperty.Builder()
                    .userId(Long.toString(userSummary.getUserId()))
                    .userName(userSummary.getFirstName() + " " + userSummary.getLastName())
                    .dateOfBirth(userSummary.getUserBO().getDob())
                    .createdDate(userSummary.getUserBO().getCrdt())
                    .mobileNumber(userSummary.getMobile())
                    .setOrderKey(setOrderKey)
                    .feedConfigVersion(feedConfigVersion)
                    .appsflyerID(AppsFlyerLib.getInstance().getAppsFlyerUID(context))
                    .configType(mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configType != null ? mConfiguration.get().configType : "")
                    .configVersion(mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configVersion != null ? mConfiguration.get().configVersion : "")
                    .emailId(userSummary.getEmailId())
                    .language(languageName);

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
            Branch.getInstance().setIdentity(Long.toString(userSummary.getUserId()));
            AppEventsLogger.setUserID(Long.toString(userSummary.getUserId()));
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
        if (feedDetail == null) {
            return;
        }
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {

            if (feedDetail instanceof UserPostSolrObj) {
                UserPostSolrObj userPostSolrObj = (UserPostSolrObj) feedDetail;
                final HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                                .postId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                                .communityName(userPostSolrObj.getPostCommunityName())
                                .title(feedDetail.getNameOrTitle())
                                .communityId(Long.toString(userPostSolrObj.getCommunityId()))
                                .type(getTypeFromSubtype(feedDetail.getSubType()))
                                .isSharedFromExternalApp(String.valueOf(feedDetail.isSharedFromExternalApp()))
                                .streamType(CommonUtil.isNotEmpty(feedDetail.getStreamType()) ? feedDetail.getStreamType() : "")
                                .positionInList(feedDetail.getItemPosition())
                                .build();
                properties.put(EventProperty.SOURCE.getString(), screenName);
                AnalyticsManager.trackEvent(event, feedDetail.getScreenName(), properties);
            } else if (feedDetail instanceof PollSolarObj) {
                PollSolarObj pollSolarObj = (PollSolarObj) feedDetail;
                final HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                                .postId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                                .communityName(pollSolarObj.getPollCommunityName())
                                .title(feedDetail.getNameOrTitle())
                                .communityId(Long.toString(pollSolarObj.getCommunityId()))
                                .type(getTypeFromSubtype(feedDetail.getSubType()))
                                .isSharedFromExternalApp(String.valueOf(feedDetail.isSharedFromExternalApp()))
                                .streamType(CommonUtil.isNotEmpty(feedDetail.getStreamType()) ? feedDetail.getStreamType() : "")
                                .positionInList(feedDetail.getItemPosition())
                                .build();
                properties.put(EventProperty.SOURCE.getString(), screenName);
                AnalyticsManager.trackEvent(event, feedDetail.getScreenName(), properties);
            }
        }
    }

    public static void trackPollActionEvent(Event event, FeedDetail feedDetail, String screenName) {
        if (feedDetail == null) {
            return;
        }
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            PollSolarObj pollSolarObj = null;
            if (feedDetail instanceof PollSolarObj) {
                pollSolarObj = (PollSolarObj) feedDetail;
            }
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                            .pollId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                            .communityName(pollSolarObj != null ? pollSolarObj.getPollCommunityName() : "")
                            .title(feedDetail.getNameOrTitle())
                            .communityId(pollSolarObj != null ? Long.toString(pollSolarObj.getCommunityId()) : "not defined")
                            .type(getTypeFromSubtype(feedDetail.getSubType()))
                            .isSharedFromExternalApp(String.valueOf(feedDetail.isSharedFromExternalApp()))
                            .streamType(CommonUtil.isNotEmpty(feedDetail.getStreamType()) ? feedDetail.getStreamType() : "")
                            .positionInList(feedDetail.getItemPosition())
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);
            AnalyticsManager.trackEvent(event, feedDetail.getScreenName(), properties);
        }
    }
    public static void trackPollActionEvent(Event event, FeedDetail feedDetail, String screenName,long pollOptionId) {
        if (feedDetail == null) {
            return;
        }
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            PollSolarObj pollSolarObj = null;
            if (feedDetail instanceof PollSolarObj) {
                pollSolarObj = (PollSolarObj) feedDetail;
            }
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                            .pollId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                            .optionId(Long.toString(pollOptionId))
                            .communityName(pollSolarObj != null ? pollSolarObj.getPollCommunityName() : "")
                            .title(feedDetail.getNameOrTitle())
                            .communityId(pollSolarObj != null ? Long.toString(pollSolarObj.getCommunityId()) : "not defined")
                            .type(getTypeFromSubtype(feedDetail.getSubType()))
                            .isSharedFromExternalApp(String.valueOf(feedDetail.isSharedFromExternalApp()))
                            .streamType(CommonUtil.isNotEmpty(feedDetail.getStreamType()) ? feedDetail.getStreamType() : "")
                            .positionInList(feedDetail.getItemPosition())
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);
            AnalyticsManager.trackEvent(event, feedDetail.getScreenName(), properties);
        }
    }
    public static void trackCommentActionEvent(Event event, FeedDetail feedDetail, String screenName) {
        if (feedDetail == null) {
            return;
        }
        if (StringUtil.isNotEmptyCollection(feedDetail.getLastComments())) {

            Comment comment = feedDetail.getLastComments().get(0);
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(comment.getId()))
                            .postType(getTypeFromSubtype(feedDetail.getSubType()))
                            .postId(Long.toString(comment.getEntityId()))
                            .communityId(comment.getCommunityId())
                            .body(comment.getComment())
                            .streamType(CommonUtil.isNotEmpty(feedDetail.getStreamType()) ? feedDetail.getStreamType() : "")
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);
            AnalyticsManager.trackEvent(event, feedDetail.getScreenName(), properties);
        }
    }

    public static void trackPostActionEvent(Event event, FeedDetail feedDetail, String screenName, HashMap<String, Object> passedProperties) {
        if (feedDetail == null) {
            return;
        }
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            UserPostSolrObj userPostSolrObj = null;
            if (feedDetail instanceof UserPostSolrObj) {
                userPostSolrObj = (UserPostSolrObj) feedDetail;
            }
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                            .postId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                            .communityName(userPostSolrObj != null ? userPostSolrObj.getPostCommunityName() : "")
                            .title(feedDetail.getNameOrTitle())
                            .communityId(userPostSolrObj != null ? Long.toString(userPostSolrObj.getCommunityId()) : "not defined")
                            .type(getTypeFromSubtype(feedDetail.getSubType()))
                            .isSharedFromExternalApp(String.valueOf(feedDetail.isSharedFromExternalApp()))
                            .streamType(CommonUtil.isNotEmpty(feedDetail.getStreamType()) ? feedDetail.getStreamType() : "")
                            .positionInList(feedDetail.getItemPosition())
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);
            properties.putAll(passedProperties);
            AnalyticsManager.trackEvent(event, feedDetail.getScreenName(), properties);
        }
    }

    public static HashMap<String, Object> getPostProperties(FeedDetail feedDetail, String screenName) {
        if (feedDetail == null) {
            return null;
        }
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            UserPostSolrObj userPostSolrObj = null;
            if (feedDetail instanceof UserPostSolrObj) {
                userPostSolrObj = (UserPostSolrObj) feedDetail;
            }
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                            .postId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                            .communityName(userPostSolrObj != null ? userPostSolrObj.getPostCommunityName() : "")
                            .communityId(userPostSolrObj != null ? Long.toString(userPostSolrObj.getCommunityId()) : "not defined")
                            .title(feedDetail.getNameOrTitle())
                            .streamType(CommonUtil.isNotEmpty(feedDetail.getStreamType()) ? feedDetail.getStreamType() : "")
                            .type(getTypeFromSubtype(feedDetail.getSubType()))
                            .positionInList(feedDetail.getItemPosition())
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);
            return properties;
        } else {
            return null;
        }
    }

    public static HashMap<String, Object> getPollProperties(FeedDetail feedDetail, String screenName) {
        if (feedDetail == null) {
            return null;
        }
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getSubType())) {
            PollSolarObj pollSolarObj = null;
            if (feedDetail instanceof PollSolarObj) {
                pollSolarObj = (PollSolarObj) feedDetail;
            }
            final HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(feedDetail.getEntityOrParticipantId()))
                            .pollId(Long.toString(feedDetail.getIdOfEntityOrParticipant()))
                            .communityName(pollSolarObj != null ? pollSolarObj.getPollCommunityName() : "")
                            .communityId(pollSolarObj != null ? Long.toString(pollSolarObj.getCommunityId()) : "not defined")
                            .title(feedDetail.getNameOrTitle())
                            .streamType(CommonUtil.isNotEmpty(feedDetail.getStreamType()) ? feedDetail.getStreamType() : "")
                            .type(getTypeFromSubtype(feedDetail.getSubType()))
                            .positionInList(feedDetail.getItemPosition())
                            .build();
            properties.put(EventProperty.SOURCE.getString(), screenName);
            return properties;
        } else {
            return null;
        }
    }

    public static HashMap<String, Object> getArticleOrStoryProperties(ArticleSolrObj articleSolrObj, String screenName) {
        if (articleSolrObj == null) {
            return null;
        }
        final HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(articleSolrObj.getEntityOrParticipantId()))
                        .title(articleSolrObj.getNameOrTitle())
                        .authorId(String.valueOf(articleSolrObj.getAuthorId()))
                        .authorName(articleSolrObj.getAuthorName())
                        .build();
        properties.put(EventProperty.SOURCE.getString(), screenName);
        return properties;

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
                            .streamType(CommonUtil.isNotEmpty(communityDetails.getStreamType()) ? communityDetails.getStreamType() : "")
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
                            .streamType(CommonUtil.isNotEmpty(communityDetails.getStreamType()) ? communityDetails.getStreamType() : "")
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
                type = ARTICLE;
                break;

            case AppConstants.FEED_COMMUNITY_POST:
                type = COMMUNITY_POST;
                break;

        }
        return type;
    }
}

