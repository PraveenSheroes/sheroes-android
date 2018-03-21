package appliedlife.pvtltd.SHEROES.models;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.StrictMode;
import android.provider.Settings;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;
import java.util.TimeZone;
import java.util.UUID;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.service.GCMClientManager;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ujjwal on 19/03/18.
 */

public class AppInstallationHelper {
    @Inject
    Preference<AppInstallation> mAppInstallationPref;

    @Inject
    Preference<LoginResponse> mLoginResponse;

    @Inject
    AppInstallationModel appInstallationModel;

    private AppInstallation mAppInstallationLocal;
    private Context mContext;

    public AppInstallationHelper(Context context){
        SheroesApplication.getAppComponent(context).inject(this);
        mContext = context;
    }

    public void saveInBackground(Context context, final CommonUtil.Callback callback) {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> subscriber) {
                        String id = getAdvertisementID();
                        subscriber.onNext(id);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String id) {
                        mAppInstallationLocal.advertisingId = id;
                        fillDefaults();
                        saveInstallationAsync(callback);
                    }
                });
    }

    private void fillDefaults() {
        if(!CommonUtil.isNotEmpty(mAppInstallationLocal.guid)){
            String uUId = UUID.randomUUID().toString();
            mAppInstallationLocal.guid  = uUId;
        }
        PackageInfo packageInfo = CommonUtil.getPackageInfo(SheroesApplication.mContext);
        if (packageInfo != null) {
            mAppInstallationLocal.appVersion = packageInfo.versionName;
            mAppInstallationLocal.appVersionCode = packageInfo.versionCode;
        }
        mAppInstallationLocal.androidId = getDeviceId();
        mAppInstallationLocal.androidVersion = CommonUtil.getAndroidVersion();
        mAppInstallationLocal.timeZone = TimeZone.getDefault().getID();
        mAppInstallationLocal.deviceName = CommonUtil.getDeviceName();
        mAppInstallationLocal.platform = "android";
        mAppInstallationLocal.deviceType = "android";

        //fetch referrer params from sharedPref
        mAppInstallationLocal.referrer = CommonUtil.getPref(AppConstants.REFERRER);
        mAppInstallationLocal.utmSource = CommonUtil.getPref(AppConstants.UTM_SOURCE);
        mAppInstallationLocal.utmMedium = CommonUtil.getPref(AppConstants.UTM_MEDIUM);
        mAppInstallationLocal.utmCampaign = CommonUtil.getPref(AppConstants.UTM_CAMPAIGN);
        mAppInstallationLocal.utmContent = CommonUtil.getPref(AppConstants.UTM_CONTENT);
        mAppInstallationLocal.utmTerm = CommonUtil.getPref(AppConstants.UTM_TERM);

        mAppInstallationLocal.locale = SheroesApplication.mContext.getResources().getConfiguration().locale.toString();
        if(mLoginResponse != null && mLoginResponse.isSet() && mLoginResponse.get().getUserSummary()!=null && CommonUtil.isNotEmpty(Long.toString(mLoginResponse.get().getUserSummary().getUserId()))) {
            String currentUserId = Long.toString(mLoginResponse.get().getUserSummary().getUserId());
            // If user has re-install the app with different user create new uuid
            if(CommonUtil.isNotEmpty(mAppInstallationLocal.userId) && !mAppInstallationLocal.userId.equalsIgnoreCase(currentUserId)){
                mAppInstallationLocal.guid = UUID.randomUUID().toString();
            }
            mAppInstallationLocal.userId = currentUserId;
        }
    }

    public String getDeviceId() {
        String deviceId = "";
        try {
            if (!CommonUtil.isNotEmpty(deviceId)) {
                deviceId = Settings.Secure.getString(SheroesApplication.mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception ex) {
            return "";
        }
        return deviceId;
    }

    private void saveInstallationAsync(final CommonUtil.Callback callback) {
        appInstallationModel.getAppInstallation(mAppInstallationLocal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AppInstallation>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //  Bamboo.e(TAG, "Error saving installation: " + e);
                        Crashlytics.getInstance().core.logException(e);
                        callback.callBack(false);
                    }

                    @Override
                    public void onNext(AppInstallation appInstallation) {
                        if (appInstallation != null) {
                            //Save to shared prefs
                            mAppInstallationPref.set(appInstallation);
                            callback.callBack(true);
                        }
                    }
                });
    }

    public String getAdvertisementID() {
        AdvertisingIdClient.Info idInfo = null;
        try {
            idInfo = AdvertisingIdClient.getAdvertisingIdInfo(SheroesApplication.mContext);
        } catch (GooglePlayServicesNotAvailableException e) {
            Crashlytics.getInstance().core.logException(e);
        } catch (GooglePlayServicesRepairableException e) {
            Crashlytics.getInstance().core.logException(e);
        } catch (IOException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        String advertId = null;
        if (idInfo != null){
            advertId = idInfo.getId();
        }

        return advertId;
    }

    public void setupInstallation(final boolean hasLoggedIn) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager((Activity) mContext, mContext.getString(R.string.ID_PROJECT_ID));
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                fillAndSaveInstallation(registrationId, hasLoggedIn);
            }

            @Override
            public void onFailure(String ex) {
                fillAndSaveInstallation("", hasLoggedIn);
            }
        });
    }

    private void fillAndSaveInstallation(String registrationId, boolean hasLoggedIn) {
        if(mAppInstallationPref == null || !mAppInstallationPref.isSet()){
            mAppInstallationLocal = new AppInstallation();
        }else {
            mAppInstallationLocal = mAppInstallationPref.get();
        }
        if(hasLoggedIn){
            mAppInstallationLocal.isLoggedOut = false;
        }
        mAppInstallationLocal.gcmId = registrationId;
        saveInBackground(mContext, new CommonUtil.Callback() {
            @Override
            public void callBack(boolean isShown) {
            }
        });
    }

    public void setAppInstallation(AppInstallation appInstallation){
        mAppInstallationLocal = appInstallation;
    }
}
