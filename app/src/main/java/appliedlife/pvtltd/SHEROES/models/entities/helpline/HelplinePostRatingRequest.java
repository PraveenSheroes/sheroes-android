package appliedlife.pvtltd.SHEROES.models.entities.helpline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

public class HelplinePostRatingRequest extends BaseRequest {

    @SerializedName("need_rating_b")
    @Expose
    private Boolean rating;

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

}
