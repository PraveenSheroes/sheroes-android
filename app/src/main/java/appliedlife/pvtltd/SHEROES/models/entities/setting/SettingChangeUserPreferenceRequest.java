package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sheroes on 20/02/17.
 */

public class SettingChangeUserPreferenceRequest {

    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("cloudMessagingId")
    @Expose
    private String cloudMessagingId;
    @SerializedName("deviceUniqueId")
    @Expose
    private String deviceUniqueId;
    @SerializedName("setting_action_id")
    @Expose
    private Integer settingActionId;
    @SerializedName("setting_privilege_id1")
    @Expose
    private Integer settingPrivilegeId;

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

    public Integer getSettingActionId() {
        return settingActionId;
    }

    public void setSettingActionId(Integer settingActionId) {
        this.settingActionId = settingActionId;
    }

    public Integer getSettingPrivilegeId() {
        return settingPrivilegeId;
    }

    public void setSettingPrivilegeId(Integer settingPrivilegeId) {
        this.settingPrivilegeId = settingPrivilegeId;
    }

}