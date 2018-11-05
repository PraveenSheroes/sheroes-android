package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerLib;
import java.util.HashMap;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

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

        String languageName = CommonUtil.getPrefStringValue(LANGUAGE_KEY);
        if (StringUtil.isNotNullOrEmptyString(languageName)) {
            properties.put(SuperProperty.LANGUAGE.getString(), languageName);
        }

        MixpanelHelper.trackScreenOpen(sAppContext, screenName, properties);

        // track screen CleverTap
        properties.put(MixpanelHelper.SCREEN_NAME, screenName);
        CleverTapHelper.trackScreen(sAppContext, properties);
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

    public static void trackNonInteractionEvent(Event event, Map<String, Object> properties) {
        if (!canSend()) {
            return;
        }

        if (properties == null) {
            properties = new HashMap<>();
        }

        event.addProperties(properties);

        // TODO(Sowrabh/Avinash): Track global properties also like Total Calls made etc.
        MixpanelHelper.trackEvent(sAppContext, event.type.name + " " + event.name, properties);
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

    public static void incrementPeopleProperty(PeopleProperty peopleProperty) {
        if (peopleProperty != null) {
            MixpanelHelper.getInstance(sAppContext).getPeople().increment(peopleProperty.getString(), 1);

        }
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