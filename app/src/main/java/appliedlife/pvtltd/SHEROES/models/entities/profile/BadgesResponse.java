package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class BadgesResponse extends BaseResponse{

    @SerializedName("user_badges_list")
    @Expose
    private List<UserBadgesList> userBadgesList = null;

    public List<UserBadgesList> getUserBadgesList() {
        return userBadgesList;
    }

    public void setUserBadgesList(List<UserBadgesList> userBadgesList) {
        this.userBadgesList = userBadgesList;
    }
}
