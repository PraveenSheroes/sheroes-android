package appliedlife.pvtltd.SHEROES.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerLib;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SearchFragment;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.LANGUAGE_KEY;
import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.isNotNullOrEmptyString;

/**
 * Created by ujjwal on 27/09/17.
 */
public class AnalyticsManager {

    //region private variable & constants
    private static final String TAG = "AnalyticsManager";
    private static Context sAppContext = null;
    private static AnalyticsManager sInstance;
    private static FirebaseAnalytics mFirebaseAnalytics;
    //endregion

    //region private method
    private static boolean canSend() {
        return sAppContext != null;
    }

    private AnalyticsManager() {
    }
    //endregion

    public static synchronized AnalyticsManager getInstance() {
        if (sInstance == null) {
            sInstance = new AnalyticsManager();
        }
        return sInstance;
    }

    // region Individual Analytics Initializers
    public static synchronized void initializeGoogleAnalytics(Context context) {
        sAppContext = context;
        GoogleAnalyticsHelper.initializeAnalyticsTracker(context);
    }

    public static synchronized void initializeFirebaseAnalytics(Context context) {
        sAppContext = context;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
    }

    public static void initializeMixpanel(final Context context, final Boolean isNewUser) {
        sAppContext = context;
        MixpanelHelper mixpanelHelper = new MixpanelHelper();
        mixpanelHelper.setupMixpanel(context);
        mixpanelHelper.setupUser(context, isNewUser);
    }

    public static void initializeMixpanel(Context context) {
        initializeMixpanel(context, true);
    }

    public static void initializeCleverTap(Context context, boolean isNewUser) {
        sAppContext = context;
        CleverTapHelper cleverTapHelper = new CleverTapHelper();
        cleverTapHelper.setupCleverTap(context);
        cleverTapHelper.setupUser(context, isNewUser);
    }

    public static void initializeFbAnalytics(Context context) {
        FBAnalyticsHelper.initializeAnalyticsTracker(context);
    }
    //endregion

    // region Primary Event Methods
    public static void trackAppOpen(Context context) {
        MixpanelHelper.incrementAppOpens(context);
    }

    public static void trackScreenView(String screenName) {
        trackScreenView(screenName, null);
    }

    @Deprecated // TODO(Ujjwal): Clean this up
    public static void trackScreenView(String screenName, Map<String, Object> properties) {
        if (screenName == null) {
            return;
        }
        trackScreenView(screenName, null, properties);
    }

    public static void trackScreenView(String screenName, String previousScreenName, Map<String, Object> properties) {
        if (!canSend()) {
            return;
        }

        if (properties == null) {
            properties = new HashMap<>();
        }

        if (!TextUtils.isEmpty(previousScreenName)) {
            properties.put(EventProperty.SOURCE.getString(), previousScreenName);
        }

        if (HomeActivity.isSearchClicked) {
            properties.put(EventProperty.SOURCE.getString(), HomeFragment.PREVIOUS_SCREEN);
            if (screenName.equalsIgnoreCase(SearchFragment.SCREEN_LABEL)) {
                properties.put(EventProperty.SOURCE_TAB_TITLE.getString(), HomeFragment.SOURCE_ACTIVE_TAB);
            } else if (screenName.equalsIgnoreCase(ArticleActivity.SCREEN_LABEL) || screenName.equalsIgnoreCase(PostDetailActivity.SCREEN_LABEL)) {
                properties.put(EventProperty.SOURCE_TAB_TITLE.getString(), SearchFragment.searchTabName);
                properties.put(EventProperty.SOURCE.getString(), HomeFragment.PREVIOUS_SCREEN);
            } else if (screenName.equalsIgnoreCase(CommunityDetailActivity.SCREEN_LABEL)) {
                properties.put(EventProperty.TAB_TITLE.getString(), SearchFragment.searchTabName);
                properties.put(EventProperty.TAB_KEY.getString(), null);
            }

            if (HomeFragment.PREVIOUS_SCREEN.equalsIgnoreCase(SearchFragment.SCREEN_LABEL)) {
                properties.put(EventProperty.SEARCH_QUERY.getString(), SearchFragment.searchText);
            }
        } else {
            properties.remove(EventProperty.SEARCH_QUERY.getString());
        }

        String languageName = CommonUtil.getPrefStringValue(LANGUAGE_KEY);
        if (StringUtil.isNotNullOrEmptyString(languageName)) {
            properties.put(SuperProperty.LANGUAGE.getString(), languageName);
        }

        MixpanelHelper.trackScreenOpen(sAppContext, screenName, properties);

        // track screen CleverTap
        properties.put(MixpanelHelper.SCREEN_NAME, screenName);
        //CleverTap
        CleverTapHelper.trackScreen(sAppContext, properties);

        //Google Analytics
        GoogleAnalyticsHelper.sendScreenView(screenName);

        /*
         * Firebase Analytics
         * It doesn't allow spaces in event's names and type of events for analytics
         */
        if (getActivityFromContext(sAppContext) != null && screenName != null) {
            mFirebaseAnalytics.setCurrentScreen(getActivityFromContext(sAppContext), screenName.replaceAll(" ", "_"), null);
        }
    }

    public static Activity getActivityFromContext(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            } else {
                return getActivityFromContext(((ContextWrapper) context).getBaseContext());
            }
        }
        return null;
    }

    public static void timeScreenView(String screenName) {
        if (!canSend()) {
            return;
        }

        MixpanelHelper.timeScreenOpen(sAppContext, screenName);
        CleverTapHelper.getInstance().timeScreenOpen();
    }

    @Deprecated
    public static void trackEvent(Event event, Map<String, Object> properties, String sourceScreen) {
        trackEvent(event, sourceScreen, properties);
    }

    public static void trackEvent(Event event, String screenName, Map<String, Object> properties) {
        if (!canSend()) {
            return;
        }

        if (properties == null) {
            properties = new HashMap<>();
        }

        event.addProperties(properties);
        String languageName = CommonUtil.getPrefStringValue(LANGUAGE_KEY);
        if (StringUtil.isNotNullOrEmptyString(languageName)) {
            properties.put(SuperProperty.LANGUAGE.getString(), languageName);
        }

        if (isNotNullOrEmptyString(screenName) && !properties.containsKey(screenName)) {
            properties.put(EventProperty.SOURCE.getString(), screenName);
        }

        if (event.trackEventToProvider(AnalyticsProvider.MIXPANEL)) {
            MixpanelHelper.trackEvent(sAppContext, event.getFullName(), properties);
        }
        if (event.trackEventToProvider(AnalyticsProvider.APPSFLYER)) {
            AppsFlyerLib.getInstance().trackEvent(sAppContext, event.getFullName(), properties);
        }

        if (event.trackEventToProvider(AnalyticsProvider.FACEBOOK)) {
            FBAnalyticsHelper.logEvent(event.type.name, event.name, null);
        }

        //track all event to CleverTap
        CleverTapHelper.trackEvent(sAppContext, event, properties);

        //track all event to Googel Analytics
        if (event.trackEventToProvider(AnalyticsProvider.GOOGLE_ANALYTICS)) {
            GoogleAnalyticsHelper.sendEvent(event.type.name, event.name, null);
        }

        //track all events to Firebase Analytics
        if (event.trackEventToProvider(AnalyticsProvider.FIREBASE)) {
            Bundle bundle = new Bundle();
            try {
                bundle = mapToBundle(properties, bundle, (event.type.name).replaceAll(" ", "_"), (event.name).replaceAll(" ", "_"));
            } catch (Exception e) {
                Crashlytics.getInstance().core.logException(e);
            }

            if (bundle != null) {
                mFirebaseAnalytics.logEvent((event.type.name).replaceAll(" ", "_"), bundle);
            }
        }
    }

    public static Bundle mapToBundle(Map<String, Object> data, Bundle bundle, String eventType, String eventName) throws Exception {
        if (bundle == null) {
            bundle = new Bundle();
        }

        bundle.putString(eventType, eventName);
        int faParameters = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (faParameters < AppConstants.FIREBASE_MAX_PARAMETERS - 1) {
                String key = entry.getKey().replaceAll(" ", "_");
                key = key.replaceAll("[$]", "");
                //google_ , ga_ , firebase_ are reserved prefixes in firebase so can't be used in parameter
                if (key.equals("google_play_services"))
                    key = key.replace(key, "fa_play_services");
                if (entry.getValue() instanceof String) {
                    bundle.putString(key, (String) entry.getValue());
                } else if (entry.getValue() instanceof Double) {
                    bundle.putDouble(key, ((Double) entry.getValue()));
                } else if (entry.getValue() instanceof Integer) {
                    bundle.putInt(key, (Integer) entry.getValue());
                } else if (entry.getValue() instanceof Float) {
                    bundle.putFloat(key, ((Float) entry.getValue()));
                }
                faParameters++;
            } else
                break;
        }
        return bundle;
    }

    public static void trackCommentAction(Event event, FeedDetail feedDetail, String screenName) {
        if (!canSend()) {
            return;
        }
        MixpanelHelper.trackCommentActionEvent(event, feedDetail, screenName);
    }

    public static void trackPostAction(Event event, FeedDetail feedDetail, String screenName) {
        if (!canSend()) {
            return;
        }
        MixpanelHelper.trackPostActionEvent(event, feedDetail, screenName);
    }

    public static void trackPollAction(Event event, FeedDetail feedDetail, String screenName, long pollOptionId) {
        if (!canSend()) {
            return;
        }
        MixpanelHelper.trackPollActionEvent(event, feedDetail, screenName, pollOptionId);
    }

    public static void trackPollAction(Event event, FeedDetail feedDetail, String screenName) {
        if (!canSend()) {
            return;
        }
        MixpanelHelper.trackPollActionEvent(event, feedDetail, screenName);
    }

    public static void trackPostAction(Event event, FeedDetail feedDetail, String screenName, HashMap<String, Object> properties) {
        if (!canSend()) {
            return;
        }
        MixpanelHelper.trackPostActionEvent(event, feedDetail, screenName, properties);
    }


    //------------TODO- fix with ujjwal
    public static void trackCommunityAction(Event event, CommunityFeedSolrObj feedDetail, String screenName, String positionInCarousel, String positionOfCarousel) {
        if (!canSend()) {
            return;
        }
        MixpanelHelper.trackCommunityEvent(event, feedDetail, screenName, positionInCarousel, positionOfCarousel);
    }

    public static void trackCommunityAction(Event event, CommunityFeedSolrObj feedDetail, String screenName) {
        if (!canSend()) {
            return;
        }
        MixpanelHelper.trackCommunityEvent(event, feedDetail, screenName);
    }
    //--------

    public static void timeEvent(Event event) {
        MixpanelHelper.timeEvent(sAppContext, event.getFullName());
    }

    public static void flushEvents() {
        MixpanelHelper.flushEvents(sAppContext);
    }

    public static void ensureTelemetry(Context context, final Boolean isNewUser) {
        if (isNewUser == null) {
            AnalyticsManager.initializeMixpanel(context);
        } else {
            AnalyticsManager.initializeMixpanel(context, isNewUser);
        }
    }
    //endregion
}