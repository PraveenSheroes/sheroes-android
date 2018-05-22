package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ravi on 21/05/18.
 */

public class DeactivateReasonsResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deactivationReason")
    @Expose
    private List<DeactivationReason> deactivationReason = null;
    @SerializedName("screen_name")
    @Expose
    private Object screenName;
    @SerializedName("next_token")
    @Expose
    private Object nextToken;
    @SerializedName("set_order_key")
    @Expose
    private Object setOrderKey;
    @SerializedName("server_feed_config_version")
    @Expose
    private Object serverFeedConfigVersion;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DeactivationReason> getDeactivationReason() {
        return deactivationReason;
    }

    public void setDeactivationReason(List<DeactivationReason> deactivationReason) {
        this.deactivationReason = deactivationReason;
    }

    public Object getScreenName() {
        return screenName;
    }

    public void setScreenName(Object screenName) {
        this.screenName = screenName;
    }

    public Object getNextToken() {
        return nextToken;
    }

    public void setNextToken(Object nextToken) {
        this.nextToken = nextToken;
    }

    public Object getSetOrderKey() {
        return setOrderKey;
    }

    public void setSetOrderKey(Object setOrderKey) {
        this.setOrderKey = setOrderKey;
    }

    public Object getServerFeedConfigVersion() {
        return serverFeedConfigVersion;
    }

    public void setServerFeedConfigVersion(Object serverFeedConfigVersion) {
        this.serverFeedConfigVersion = serverFeedConfigVersion;
    }

}
