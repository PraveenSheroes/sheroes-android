package appliedlife.pvtltd.SHEROES.models;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.provider.Settings;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppModule;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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
    @SerializedName("device_guid")
    public String guid; //done

    @SerializedName("device_id")
    public String androidId; //done

    @SerializedName("advertising_id")
    public String advertisingId; //done


    @SerializedName("os_version")
    public String androidVersion; //done


    @SerializedName("app_version")
    public String appVersion;

    @SerializedName("app_version_code")
    public Integer appVersionCode;

    @SerializedName("push_token")
    public String gcmId;

    @SerializedName("device_name")
    public String deviceName; //done

    @SerializedName("Device_token")
    public String deviceToken;

    @SerializedName("device_type")
    public String deviceType;

    @SerializedName("is_logged_out")
    public boolean isLoggedOut;

    @SerializedName("Platform")
    public String platform; //done

    @SerializedName("time_zone")
    public String timeZone; //done

    @SerializedName("locale")
    public String locale;

    @SerializedName("user_id")
    public String userId;   //done

    @SerializedName("appsflyer_id")
    public String appsFlyerId;

    @SerializedName("branch_install_url")
    public String branchInstallUrl;

    @SerializedName("referrer")
    public String referrer;

    @SerializedName("utm_source")
    public String utmSource;

    @SerializedName("utm_medium")
    public String utmMedium;

    @SerializedName("utm_campaign")
    public String utmCampaign;

    @SerializedName("utm_content")
    public String utmContent;

    @SerializedName("utm_term")
    public String utmTerm;

    @SerializedName("installed_packages")
    public String installedPackages;
}
