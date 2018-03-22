package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.moe.pushlibrary.MoEHelper;

import java.io.File;

import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.models.AppInstallationHelper;
import appliedlife.pvtltd.SHEROES.social.AnalyticsTrackers;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.branch.referral.Branch;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Application level context and all app componets register here.
 * dagger used for components injection in app.
 */
public class SheroesApplication extends MultiDexApplication  {
    private final String TAG = LogUtils.makeLogTag(SheroesApplication.class);
    SheroesAppComponent mSheroesAppComponent;
    public static volatile SheroesApplication mContext;
    private String mCurrentActivityName;

    public static SheroesAppComponent getAppComponent(Context context) {
        return (mContext).mSheroesAppComponent;
    }

    protected void setAppComponent(SheroesAppComponent sheroesAppComponent) {
        this.mSheroesAppComponent = sheroesAppComponent;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        final CrashlyticsCore core = new CrashlyticsCore.Builder().build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build(), new Crashlytics());
        MoEHelper.getInstance(getApplicationContext()).autoIntegrate(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
        File cacheFile = new File(getCacheDir(), "responses");
        mSheroesAppComponent = DaggerSheroesAppComponent.builder().sheroesAppModule(new SheroesAppModule(cacheFile,this)).build();
        setAppComponent(mSheroesAppComponent);
        Branch.getAutoInstance(this);
        AnalyticsManager.initializeMixpanel(mContext);
        AnalyticsManager.initializeFbAnalytics(mContext);
        Stetho.initializeWithDefaults(this);
    }

    public String getCurrentActivityName() {
        return mCurrentActivityName;
    }

    public void setCurrentActivityName(String mCurrentActivity) {
        this.mCurrentActivityName = mCurrentActivity;
    }

    public void notifyIfAppInBackground() {
        try {
            if (getCurrentActivityName() == null) { // App is sent to background perform a background operation
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            LogUtils.error(TAG, AppConstants.ERROR_OCCUR, e);
        }
    }

    public static SharedPreferences getAppSharedPrefs(){
        if(mContext == null){
            return null;
        }
        return mContext.getSharedPreferences(AppConstants.SHARED_PREFS, MODE_PRIVATE);
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);

    }
    public void trackUserId(String userId) {
        Tracker t = getGoogleAnalyticsTracker();
        t.set("&uid", userId);
       t.send(new HitBuilders.EventBuilder()
                .setCategory("UX")
                .setAction("User Sign In")
                .build());

    }
    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();
       /* if(!StringUtil.isNotNullOrEmptyString(label))
        {
            label="-";
        }*/
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }
}
