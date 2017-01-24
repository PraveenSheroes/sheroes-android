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

    public BaseRequest() {
    }

    public BaseRequest(DeviceInfo deviceInfo, TrackingParams trackingParams) {
        this.deviceInfo = deviceInfo;
        this.trackingParams = trackingParams;
    }


    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public TrackingParams getTrackingParams() {
        return trackingParams;
    }

    public void setTrackingParams(TrackingParams trackingParams) {
        this.trackingParams = trackingParams;
    }
}

