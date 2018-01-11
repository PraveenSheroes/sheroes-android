package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 04-05-2017.
 */
@Parcel(analyze = {WorkExpListResponse.class})
public class WorkExpListResponse extends BaseResponse{
    @SerializedName("exprienceBO")
    @Expose
    private ExperienceBOdetail experienceBOdetail;

    @SuppressWarnings("unused")
    public ExperienceBOdetail getExperienceBOdetail() {
        return experienceBOdetail;
    }

    @SuppressWarnings("unused")
    public void setExperienceBOdetail(ExperienceBOdetail experienceBOdetail) {
        this.experienceBOdetail = experienceBOdetail;
    }
}
