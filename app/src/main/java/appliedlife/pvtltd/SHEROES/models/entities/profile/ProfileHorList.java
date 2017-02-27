package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 16-02-2017.
 */

public class ProfileHorList extends BaseResponse {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tag")
    @Expose
    private String tag;



    @SerializedName("city")
    @Expose
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
