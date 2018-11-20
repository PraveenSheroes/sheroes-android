package appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 08-08-2017.
 */

public class ChampionFollowedResponse extends BaseResponse {

    @SerializedName("is_already_followed")
    private boolean isAlreadyFollowed;

    public boolean isAlreadyFollowed() {
        return isAlreadyFollowed;
    }
}
