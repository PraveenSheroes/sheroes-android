package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by priyanka on 24/03/17.
 */

public class ProfessionalBasicDetailsRequest {


    @SerializedName("subType")
    @Expose
    private String subType;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("deviceUniqueId")
    @Expose
    private String deviceUniqueId;
    @SerializedName("cloudMessagingId")
    @Expose
    private String cloudMessagingId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("last_screen_name")
    @Expose
    private String lastScreenName;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("job_tag_id")
    @Expose
    private Long jobTagId;
    @SerializedName("total_exp_year")
    @Expose
    private int totalExpYear;
    @SerializedName("total_exp_month")
    @Expose
    private int totalExpMonth;
    @SerializedName("sector_id")
    @Expose
    private Long sectorId;
    @SerializedName("sector")
    @Expose
    private String sector;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceUniqueId() {
        return deviceUniqueId;
    }

    public void setDeviceUniqueId(String deviceUniqueId) {
        this.deviceUniqueId = deviceUniqueId;
    }

    public String getCloudMessagingId() {
        return cloudMessagingId;
    }

    public void setCloudMessagingId(String cloudMessagingId) {
        this.cloudMessagingId = cloudMessagingId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getLastScreenName() {
        return lastScreenName;
    }

    public void setLastScreenName(String lastScreenName) {
        this.lastScreenName = lastScreenName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getJobTagId() {
        return jobTagId;
    }

    public void setJobTagId(Long jobTagId) {
        this.jobTagId = jobTagId;
    }

    public int getTotalExpYear() {
        return totalExpYear;
    }

    public void setTotalExpYear(int totalExpYear) {
        this.totalExpYear = totalExpYear;
    }

    public int getTotalExpMonth() {
        return totalExpMonth;
    }

    public void setTotalExpMonth(int totalExpMonth) {
        this.totalExpMonth = totalExpMonth;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
