package appliedlife.pvtltd.SHEROES.basecomponents.baserequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;

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
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("last_screen_name")
    @Expose
    private String lastScreenName;
    @SerializedName("page_no")
    @Expose
    private int pageNo=1;
    @SerializedName("page_size")
    @Expose
    private int pageSize=10;
    @SerializedName("source")
    @Expose
    private String source= AppConstants.SOURCE_NAME;

    public BaseRequest(TrackingParams trackingParams, DeviceInfo deviceInfo, String appVersion, String cloudMessagingId, String deviceUniqueId, String screenName, String lastScreenName, int pageNo, int pageSize) {
        this.trackingParams = trackingParams;
        this.deviceInfo = deviceInfo;
        this.appVersion = appVersion;
        this.cloudMessagingId = cloudMessagingId;
        this.deviceUniqueId = deviceUniqueId;
        this.screenName = screenName;
        this.lastScreenName = lastScreenName;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
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


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

