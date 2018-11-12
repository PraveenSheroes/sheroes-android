package appliedlife.pvtltd.SHEROES.models.entities.helpline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

public class HelplinePostRatingRequest extends BaseRequest {

    @SerializedName("need_rating")
    @Expose
    private Boolean rating;

    @SerializedName("answer_id")
    @Expose
    private Integer answerId;

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }
}
