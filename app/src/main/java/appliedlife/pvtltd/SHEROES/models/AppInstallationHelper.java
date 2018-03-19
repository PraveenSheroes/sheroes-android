package appliedlife.pvtltd.SHEROES.models;

import android.content.Context;
import android.content.pm.PackageInfo;
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

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
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

    private AppInstallation mAppInstallation;

    public AppInstallationHelper(AppInstallation appInstallation){
        mAppInstallation = appInstallation;
    }

    public void saveInBackground(Context context) {
        SheroesApplication.getAppComponent(context).inject(this);
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
                        mAppInstallation.advertisingId = id;
                        fillDefaults();
                        saveInstallationAsync();
                    }
                });
    }

    private void fillDefaults() {
        if(!CommonUtil.isNotEmpty(mAppInstallation.guid)){
            String uUId = UUID.randomUUID().toString();
            mAppInstallation.guid  = uUId;
        }
        PackageInfo packageInfo = CommonUtil.getPackageInfo(SheroesApplication.mContext);
        if (packageInfo != null) {
            mAppInstallation.appVersion = packageInfo.versionName;
            mAppInstallation.appVersionCode = packageInfo.versionCode;
        }
        mAppInstallation.androidId = getDeviceId();
        mAppInstallation.androidVersion = CommonUtil.getAndroidVersion();
        mAppInstallation.timeZone = TimeZone.getDefault().getID();
        mAppInstallation.deviceName = CommonUtil.getDeviceName();
        mAppInstallation.platform = "android";
        mAppInstallation.locale = SheroesApplication.mContext.getResources().getConfiguration().locale.toString();
        if(mLoginResponse != null && mLoginResponse.isSet() && mLoginResponse.get().getUserSummary()!=null && CommonUtil.isNotEmpty(Long.toString(mLoginResponse.get().getUserSummary().getUserId()))) {
            String currentUserId = Long.toString(mLoginResponse.get().getUserSummary().getUserId());
            // If user has re-install the app with different user create new uuid
            if(CommonUtil.isNotEmpty(mAppInstallation.userId) && !mAppInstallation.userId.equalsIgnoreCase(currentUserId)){
                mAppInstallation.guid = UUID.randomUUID().toString();
            }
            mAppInstallation.userId = currentUserId;
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

    private void saveInstallationAsync() {
        appInstallationModel.getAppInstallation(mAppInstallation)
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
                    }

                    @Override
                    public void onNext(AppInstallation appInstallation) {
                        if (appInstallation != null) {
                            //Save to shared prefs
                            mAppInstallationPref.set(appInstallation);
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
}
