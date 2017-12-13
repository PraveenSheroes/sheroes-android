package appliedlife.pvtltd.SHEROES.models.entities.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;

/**
 * Created by ujjwal on 04/05/17.
 */

public class WinnerResponse extends BaseResponse {
    @SerializedName("winner_response")
    public List<Winner> winners;
}
