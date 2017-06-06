package appliedlife.pvtltd.SHEROES.models.entities.login.googleplus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Praveen_Singh on 05-06-2017.
 */

public class Device {
    @SerializedName("uid")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("gmc_apns_id")
    @Expose
    private String gmcApnsId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGmcApnsId() {
        return gmcApnsId;
    }

    public void setGmcApnsId(String gmcApnsId) {
        this.gmcApnsId = gmcApnsId;
    }
}
