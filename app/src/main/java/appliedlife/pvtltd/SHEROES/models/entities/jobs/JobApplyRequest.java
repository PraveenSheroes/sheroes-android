package appliedlife.pvtltd.SHEROES.models.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ajit Kumar on 10-03-2017.
 */

public class JobApplyRequest {
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("cloudMessagingId")
    @Expose
    private String cloudMessagingId;
    @SerializedName("cover_note")
    @Expose
    private String coverNote;
    @SerializedName("deviceUniqueId")
    @Expose
    private String deviceUniqueId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("job_id")
    @Expose
    private Long jobId;
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

    public String getCoverNote() {
        return coverNote;
    }

    public void setCoverNote(String coverNote) {
        this.coverNote = coverNote;
    }

    public String getDeviceUniqueId() {
        return deviceUniqueId;
    }

    public void setDeviceUniqueId(String deviceUniqueId) {
        this.deviceUniqueId = deviceUniqueId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getLastScreenName() {
        return lastScreenName;
    }

    public void setLastScreenName(String lastScreenName) {
        this.lastScreenName = lastScreenName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
}
