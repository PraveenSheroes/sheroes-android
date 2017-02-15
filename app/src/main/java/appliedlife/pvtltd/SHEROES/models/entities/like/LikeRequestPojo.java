package appliedlife.pvtltd.SHEROES.models.entities.like;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 14-02-2017.
 */

public class LikeRequestPojo extends BaseRequest {

    @SerializedName("value")
    @Expose
    private int reactionValue;

    public int getReactionValue() {
        return reactionValue;
    }

    public void setReactionValue(int reactionValue) {
        this.reactionValue = reactionValue;
    }
}
