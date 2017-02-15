package appliedlife.pvtltd.SHEROES.basecomponents.baserequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen.Singh on 23/01/2017.
 *
 * @author Praveen Singh
 * @version 1.0
 * @since 23/01/2017.
 */
public abstract class BaseRequest {
    @SerializedName("tracking_params")
    @Expose
    protected TrackingParams trackingParams;
    @SerializedName("device_info")
    @Expose
    protected DeviceInfo deviceInfo;
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
    private int pageNo;
    @SerializedName("page_size")
    @Expose
    private int pageSize;
    @SerializedName("entity_id")
    @Expose
    private long entityId;

    public BaseRequest(TrackingParams trackingParams, DeviceInfo deviceInfo, String appVersion, String cloudMessagingId, String deviceUniqueId, int pageNo, int pageSize, long entityId) {
        this.trackingParams = trackingParams;
        this.deviceInfo = deviceInfo;
        this.appVersion = appVersion;
        this.cloudMessagingId = cloudMessagingId;
        this.deviceUniqueId = deviceUniqueId;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.entityId = entityId;
    }

    public BaseRequest() {
    }

    public TrackingParams getTrackingParams() {
        return trackingParams;
    }

    public void setTrackingParams(TrackingParams trackingParams) {
        this.trackingParams = trackingParams;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }
}

