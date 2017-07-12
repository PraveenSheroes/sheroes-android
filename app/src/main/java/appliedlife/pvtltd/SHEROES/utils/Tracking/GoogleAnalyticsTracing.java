package appliedlife.pvtltd.SHEROES.utils.Tracking;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by Ajit Kumar on 10-01-2017.
 */

public class GoogleAnalyticsTracing {
    public static void eventTracking(Context cn,String eventname)
    {

    }
    public static void screenNameTracking(Context cn,String screenname)
    {
        GoogleAnalytics analytics= GoogleAnalytics.getInstance(cn);
        Tracker tr=analytics.newTracker(AppConstants.GOOGLE_ANALYTICS);
        tr.setScreenName(screenname);
        tr.send(new HitBuilders.ScreenViewBuilder().build());
    }
    public static void setUserIdTracking(Context cn,String userId)
    {
        GoogleAnalytics analytics= GoogleAnalytics.getInstance(cn);
        Tracker tr=analytics.newTracker(AppConstants.GOOGLE_ANALYTICS);
        tr.set("&uid", userId);
        tr.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("User Sign In")
                .build());
    }
}
