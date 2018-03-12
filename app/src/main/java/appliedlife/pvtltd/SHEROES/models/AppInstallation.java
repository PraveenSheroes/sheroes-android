package appliedlife.pvtltd.SHEROES.models;

import android.content.pm.PackageInfo;
import android.provider.Settings;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.TimeZone;
import java.util.UUID;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ujjwal on 11/03/18.
 */

public class AppInstallation {

    @Inject
    Preference<AppInstallation> mAppInstallation;

    @Inject
    Preference<LoginResponse> mLoginResponse;

    @SerializedName("device_GUID")
    public String guid; //done

    @SerializedName("android_id")
    public String androidId; //done

    @SerializedName("advertising_id")
    public String advertisingId; //done


    @SerializedName("android_version")
    public String androidVersion; //done


    @SerializedName("app_version")
    public String appVersion;

    @SerializedName("App_version_code")
    public int appVersionCode;

    @SerializedName("gcm_id")
    public String gcmId;

    @SerializedName("device_name")
    public String deviceName; //done

    @SerializedName("Device_token")
    public String deviceToken;

    @SerializedName("device_type")
    public String deviceType;

    @SerializedName("logged_out")
    public String loggedOut;

    @SerializedName("Platform")
    public String platform; //done

    @SerializedName("Timezone")
    public String timeZone; //done

    @SerializedName("Locale")
    public String locale;

    @SerializedName("UserId")
    public String userId;   //done

    @SerializedName("appsflyer")
    public String appsFlyerId;

    @SerializedName("branchId")
    public String branchId;


    /**
     * Saves the current installation object to our server
     * Updates or inserts a new one.
     * Also saves to local shared prefs.
     */
    public void saveInBackground() {
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
                        advertisingId = id;
                        fillDefaults();
                        saveInstallationAsync();
                    }
                });
    }

    private void fillDefaults() {
        if(!CommonUtil.isNotEmpty(this.guid)){
            String uUId = UUID.randomUUID().toString();
            this.guid  = uUId;
        }
        PackageInfo packageInfo = CommonUtil.getPackageInfo(SheroesApplication.mContext);
        if (packageInfo != null) {
            this.appVersion = packageInfo.versionName;
            this.appVersionCode = packageInfo.versionCode;
        }
        this.androidId = getDeviceId();
        this.androidVersion = CommonUtil.getAndroidVersion();
        this.timeZone = TimeZone.getDefault().getID();
        this.deviceName = CommonUtil.getDeviceName();
        this.platform = "android";
        if(mLoginResponse != null && mLoginResponse.isSet() && mLoginResponse.get().getUserSummary()!=null && CommonUtil.isNotEmpty(Long.toString(mLoginResponse.get().getUserSummary().getUserId()))) {
            String currentUserId = Long.toString(mLoginResponse.get().getUserSummary().getUserId());
            // If user has re-install the app with different user create new uuid
            if(CommonUtil.isNotEmpty(userId) && !userId.equalsIgnoreCase(currentUserId)){
                this.guid = UUID.randomUUID().toString();
            }
            this.userId = currentUserId;
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
        /*CareServiceHelper.getCareServiceInstance().saveInstallation(this)
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
                            mAppInstallation.set(appInstallation);
                        }
                    }
                });*/
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
