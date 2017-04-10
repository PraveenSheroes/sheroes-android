package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.DeviceInfo;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.TrackingParams;

/**
 * Created by priyanka on 02/04/17.
 */

public class AddSkillRequest extends BaseRequest{

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("skill_master_id")
    @Expose
    private long skillMasterId;

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public long getSkillMasterId() {

        return skillMasterId;
    }

    public void setSkillMasterId(long skillMasterId) {

        this.skillMasterId = skillMasterId;
    }

}
