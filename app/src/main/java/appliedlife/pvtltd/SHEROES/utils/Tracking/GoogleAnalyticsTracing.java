package appliedlife.pvtltd.SHEROES.utils.Tracking;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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
        Tracker tr=analytics.newTracker("UA-43289318-2");
        tr.setScreenName(screenname);

        tr.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
