package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by priyanka on 20/02/17.
 */

public class SettingDeActivateRequest {

    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("cloudMessagingId")
    @Expose
    private String cloudMessagingId;
    @SerializedName("deviceUniqueId")
    @Expose
    private String deviceUniqueId;
    @SerializedName("reason_for_inactive")
    @Expose
    private String reasonForInactive;

    @SerializedName("last_screen_name")
    @Expose
    private String lastScreenName;

    @SerializedName("screen_name")
    @Expose
    private String screenName;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCloudMessagingId() {
        return cloudMessagingId;
    }

    public void setCloudMessagingId(String cloudMessagingId) {
        this.cloudMessagingId = cloudMessagingId;
    }

    public String getDeviceUniqueId() {
        return deviceUniqueId;
    }

    public void setDeviceUniqueId(String deviceUniqueId) {
        this.deviceUniqueId = deviceUniqueId;
    }

    public String getReasonForInactive() {
        return reasonForInactive;
    }

    public void setReasonForInactive(String reasonForInactive) {
        this.reasonForInactive = reasonForInactive;
    }


    public String getLastScreenName() {
        return lastScreenName;
    }

    public void setLastScreenName(String LastScreenName) {
        this.lastScreenName = LastScreenName;}

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;}
}
