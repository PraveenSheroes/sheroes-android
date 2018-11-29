package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by Purnima
 */
class GoogleAnalyticsHelper {

    private final static String TAG = "Google Analytics Helper";
    private static Context sAppContext = null;
    private static Tracker sTracker;

    private static boolean canSend() {
        // We can only send Google Analytics when ALL the following conditions are true:
        return (sAppContext != null && sTracker != null);
    }

    //To send a screen view
    public static void sendScreenView(String screenName) {
        if (canSend()) {
            sTracker.setScreenName(screenName);
            sTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    public static void sendEvent(String category, String action, String label, long value, HitBuilders.EventBuilder eventBuilder) {
        if (canSend()) {
            sTracker.send(new HitBuilders.EventBuilder()
                    .setCategory(category)
                    .setAction(action)
                    .setLabel(label)
                    .setValue(value)
                    .build());
        }
    }

    public static void sendEvent(String category, String action, String label) {
        HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
        sendEvent(category, action, label, 0, eventBuilder);
    }

    //Initialize the Analytics Tracker
    public static synchronized void initializeAnalyticsTracker(Context context) {
        sAppContext = context;
        if (sTracker == null) {
            int useProfile;
            if (BuildConfig.DEBUG) {
                useProfile = R.xml.debug_ga_tracker;
            } else {
                useProfile = R.xml.app_tracker;
            }
            sTracker = GoogleAnalytics.getInstance(context).newTracker(useProfile);
            sTracker.enableAdvertisingIdCollection(true);
        }
    }
}

