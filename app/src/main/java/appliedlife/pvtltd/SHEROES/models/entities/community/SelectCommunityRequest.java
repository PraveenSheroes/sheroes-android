package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SHEROES-TECH on 21-03-2017.
 */

public class SelectCommunityRequest {
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("cloudMessagingId")
    @Expose
    private String cloudMessagingId;
    @SerializedName("deviceUniqueId")
    @Expose
    private String deviceUniqueId;
    @SerializedName("page_no")
    @Expose
    private Integer pageNo;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("master_data_type")
    @Expose
    private String masterDataType;

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

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getMasterDataType() {
        return masterDataType;
    }

    public void setMasterDataType(String masterDataType) {
        this.masterDataType = masterDataType;
    }
}
