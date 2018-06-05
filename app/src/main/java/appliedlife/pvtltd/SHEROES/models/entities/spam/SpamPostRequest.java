package appliedlife.pvtltd.SHEROES.models.entities.spam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.SpamContentType;

/**
 * Created by ravi on 09/04/18.
 * Request to report post , profile & comment
 */

public class SpamPostRequest {

    @SerializedName("model_id")
    @Expose
    private Long modelId;

    @SerializedName("model_type")
    @Expose
    private String modelType;

    @SerializedName("reason")
    @Expose
    private String spamReason;

    @SerializedName("reported_by_user_id")
    @Expose
    private Long spamReportedBy;

    @SerializedName("reported_on_user_id")
    @Expose
    private Long spamReportedOn;

    @SerializedName("community_id")
    @Expose
    private Long communityId;

    @SerializedName("score")
    @Expose
    private int score;


    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getSpamReason() {
        return spamReason;
    }

    public void setSpamReason(String spamReason) {
        this.spamReason = spamReason;
    }

    public Long getSpamReportedBy() {
        return spamReportedBy;
    }

    public void setSpamReportedBy(Long spamReportedBy) {
        this.spamReportedBy = spamReportedBy;
    }

    public Long getSpamReportedOn() {
        return spamReportedOn;
    }

    public void setSpamReportedOn(Long spamReportedOn) {
        this.spamReportedOn = spamReportedOn;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
