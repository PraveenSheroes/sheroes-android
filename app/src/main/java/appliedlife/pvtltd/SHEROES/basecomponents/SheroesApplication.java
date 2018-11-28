package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;

import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.social.AnalyticsTrackers;
import appliedlife.pvtltd.SHEROES.util.StethoUtil;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
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
public class SheroesApplication extends MultiDexApplication {
    private final String TAG = LogUtils.makeLogTag(SheroesApplication.class);
    public static volatile SheroesApplication mContext;
    private String mCurrentActivityName;
    SheroesAppComponent mSheroesAppComponent;

    public static SheroesAppComponent getAppComponent(Context context) {
        return (mContext).mSheroesAppComponent;
    }

    protected void setAppComponent(SheroesAppComponent sheroesAppComponent) {
        this.mSheroesAppComponent = sheroesAppComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();
        mContext = this;
        final CrashlyticsCore core = new CrashlyticsCore.Builder().build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build(), new Crashlytics());
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
        File cacheFile = new File(getCacheDir(), "responses");
        mSheroesAppComponent = DaggerSheroesAppComponent.builder().sheroesAppModule(new SheroesAppModule(cacheFile, this)).build();
        setAppComponent(mSheroesAppComponent);
        Branch.enableLogging();
        Branch.getAutoInstance(this);
        AnalyticsManager.initializeMixpanel(mContext);
        AnalyticsManager.initializeFbAnalytics(mContext);
        AnalyticsManager.initializeCleverTap(this, false);
        AnalyticsManager.initializeGoogleAnalytics(this);
        AnalyticsManager.initializeFirebaseAnalytics(this);
        StethoUtil.initStetho(this);
    }

    public String getCurrentActivityName() {
        return mCurrentActivityName;
    }

    public void setCurrentActivityName(String mCurrentActivity) {
        this.mCurrentActivityName = mCurrentActivity;
    }

    public void notifyIfAppInBackground() {
        try {
            if (getCurrentActivityName() == null) { // App is sent to background to perform a background operation
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            LogUtils.error(TAG, AppConstants.ERROR_OCCUR, e);
        }
    }

    public static SharedPreferences getAppSharedPrefs() {
        if (mContext == null) {
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
}
