package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.SerializedName;

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
    public String fcmId;

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
}
