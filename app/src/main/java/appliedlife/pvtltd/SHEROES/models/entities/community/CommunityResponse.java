package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FieldErrorMessageMap;

/**
 * Created by Praveen_Singh on 10-03-2017.
 */

public class CommunityResponse extends BaseResponse {

    @SerializedName("members")
    @Expose
    private List<Member> members;


}
