package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;

import java.io.File;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
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
    SheroesAppComponent mSheroesAppComponent;
    public static volatile SheroesApplication mContext;
    private String mCurrentActivityName;
    public static SheroesAppComponent getAppComponent(Context context) {
        return ((SheroesApplication) context.getApplicationContext()).mSheroesAppComponent;
    }

    protected void setAppComponent(SheroesAppComponent sheroesAppComponent) {
        this.mSheroesAppComponent = sheroesAppComponent;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Crashlytics crashlytics = new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build();
        Fabric.with(this,crashlytics);
        File cacheFile = new File(getCacheDir(), "responses");
        mSheroesAppComponent = DaggerSheroesAppComponent.builder().sheroesAppModule(new SheroesAppModule(cacheFile,this)).build();
        setAppComponent(mSheroesAppComponent);
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
            LogUtils.error(TAG, AppConstants.ERROR_OCCUR, e);
        }
    }
}
