
package appliedlife.pvtltd.SHEROES.analytics;


import android.content.Context;
import android.text.TextUtils;


import com.appsflyer.AppsFlyerLib;
import com.moe.pushlibrary.MoEHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;


import static appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil.isNotNullOrEmptyString;


/**
 * Created by ujjwal on 27/09/17.
 */
public class AnalyticsManager {

    private static final String TAG = "AnalyticsManager";
    private static Context sAppContext = null;

    private static boolean canSend() {
        return sAppContext != null;
    }

    // region Individual Analytics Initializers

    public static void initializeMixpanel(final Context context, final Boolean isNewUser) {
        sAppContext = context;
        MixpanelHelper mixpanelHelper = new MixpanelHelper();
        mixpanelHelper.setupMixpanel(context);
        mixpanelHelper.setupUser(context, isNewUser);
    }

    public static void initializeMixpanel(Context context){
        initializeMixpanel(context, false);
    }

    //endregion

    // endregion

    // region Primary Event Methods
    public static void trackAppOpen(Context context){
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
        if(!canSend()) {
            return;
        }

        if (properties == null) {
            properties = new HashMap<>();
        }

        if(!TextUtils.isEmpty(previousScreenName)) {
            properties.put(EventProperty.SOURCE.getString(), previousScreenName);
        }
        MixpanelHelper.trackScreenOpen(sAppContext, screenName, properties);

        if (properties == null) {
            properties = new HashMap<>();
        }

        // track all event to Moengage
        properties.put(MixpanelHelper.SCREEN_NAME, screenName);
        JSONObject jsonObj = new JSONObject(properties);
        MoEHelper.getInstance(sAppContext).trackEvent(MixpanelHelper.SCREEN_OPEN, jsonObj);
    }

    public static void timeScreenView(String screenName) {
        if(!canSend()) {
            return;
        }

        MixpanelHelper.timeScreenOpen(sAppContext, screenName);
    }

    @Deprecated
    public static void trackEvent(Event event, Map<String, Object> properties, String sourceScreen){
        trackEvent(event, sourceScreen, properties);
    }

    public static void trackNonInteractionEvent(Event event, Map<String, Object> properties){
        if(!canSend()) {
            return;
        }

        if(properties == null) {
            properties = new HashMap<>();
        }

        event.addProperties(properties);

        // TODO(Sowrabh/Avinash): Track global properties also like Total Calls made etc.
        MixpanelHelper.trackEvent(sAppContext, event.type.name + " " + event.name, properties);
    }

    public static void trackEvent(Event event, String screenName, Map<String, Object> properties){
        if(!canSend()) {
            return;
        }

        if(properties == null) {
            properties = new HashMap<>();
        }

        event.addProperties(properties);

        if (isNotNullOrEmptyString(screenName) && !properties.containsKey(screenName)) {
            properties.put(EventProperty.SOURCE.getString(), screenName);
        }

        if (event.trackEventToProvider(AnalyticsProvider.MIXPANEL)) {
            MixpanelHelper.trackEvent(sAppContext, event.getFullName(), properties);
        }
        if (event.trackEventToProvider(AnalyticsProvider.APPSFLYER)) {
            AppsFlyerLib.getInstance().trackEvent(sAppContext, event.getFullName(), properties);
        }

        // track all event to Moengage
        JSONObject jsonObj = new JSONObject(properties);
        MoEHelper.getInstance(sAppContext).trackEvent(event.getFullName(), jsonObj);

    }

    public static void trackPostAction(Event event, FeedDetail feedDetail, String screenName) {
        if (!canSend()) {
            return;
        }
        MixpanelHelper.trackPostActionEvent(event, feedDetail, screenName);
    }

    public static void timeEvent(Event event) {
        MixpanelHelper.timeEvent(sAppContext,  event.getFullName());
    }

    public static void incrementPeopleProperty(PeopleProperty peopleProperty){
        if(peopleProperty != null) {
            MixpanelHelper.getInstance(sAppContext).getPeople().increment(peopleProperty.getString(), 1);

        }
    }

    public static void flushEvents(){
        MixpanelHelper.flushEvents(sAppContext);
    }

    public static void ensureTelemetry(Context context, final Boolean isNewUser){
        if(isNewUser==null){
            AnalyticsManager.initializeMixpanel(context);
        }else {
            AnalyticsManager.initializeMixpanel(context, isNewUser);
        }
    }
    //endregion
}