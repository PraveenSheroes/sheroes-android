package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;

import com.facebook.appevents.AppEventsLogger;

/**
 * Created by ujjwal on 07/03/17.
 */

class FBAnalyticsHelper {

    private static Context sAppContext = null;

    private static AppEventsLogger mLogger;

    private static boolean canSend() {
        // We can only send FB Analytics when ALL the following conditions are true:
        return sAppContext != null && mLogger != null;
    }

    static synchronized void initializeAnalyticsTracker(Context context) {
        sAppContext = context;
        if (mLogger == null) {
            mLogger = AppEventsLogger.newLogger(sAppContext);
        }
    }

    static void logEvent(String category, String action, String label) {
        if (canSend()) {
            // TODO: Think about what to do with labels
            mLogger.logEvent(category + "_" + action);
        }
    }
}