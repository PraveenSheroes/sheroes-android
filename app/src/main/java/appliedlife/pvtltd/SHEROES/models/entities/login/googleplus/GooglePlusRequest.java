package appliedlife.pvtltd.SHEROES.models.entities.login.googleplus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen_Singh on 05-06-2017.
 */

public class GooglePlusRequest {
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("Device")
    @Expose
    private Device device;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
