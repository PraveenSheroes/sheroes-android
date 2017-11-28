package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 04-05-2017.
 */

@Parcel(analyze = {ExperienceBOdetail.class, BaseResponse.class})
public class ExperienceBOdetail extends BaseResponse {
    @SerializedName("experiences")
    @Expose
    private HashMap<Long,ExprienceEntity>exprienceEntityHashMap=new HashMap<>();

    public HashMap<Long, ExprienceEntity> getExprienceEntityHashMap() {
        return exprienceEntityHashMap;
    }

    public void setExprienceEntityHashMap(HashMap<Long, ExprienceEntity> exprienceEntityHashMap) {
        this.exprienceEntityHashMap = exprienceEntityHashMap;
    }
}
