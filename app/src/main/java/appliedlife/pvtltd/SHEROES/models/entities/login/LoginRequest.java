
package appliedlife.pvtltd.SHEROES.models.entities.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("advertisementid")
    @Expose
    private String advertisementid;
    @SerializedName("deviceid")
    @Expose
    private String deviceid;
    @SerializedName("devicetype")
    @Expose
    private String devicetype;
    @SerializedName("gcmorapnsid")
    @Expose
    private String gcmorapnsid;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;

    public String getAdvertisementid() {
        return advertisementid;
    }

    public void setAdvertisementid(String advertisementid) {
        this.advertisementid = advertisementid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getGcmorapnsid() {
        return gcmorapnsid;
    }

    public void setGcmorapnsid(String gcmorapnsid) {
        this.gcmorapnsid = gcmorapnsid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
