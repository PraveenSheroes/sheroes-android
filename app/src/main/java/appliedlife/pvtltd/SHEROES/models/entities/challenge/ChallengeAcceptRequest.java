package appliedlife.pvtltd.SHEROES.models.entities.challenge;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 25-05-2017.
 */

public class ChallengeAcceptRequest extends BaseRequest  {


    private String userComment;

    //NotNull
    private Long challengeId;

    //NotNull
    private Boolean isActive;

    //NotNull
    private Boolean isDeleted;

    //NotNull
    private Integer completionPercent;

    private String proofImageUrl;

    private String proofVideoLink;

    //NotNull
    private Boolean isAccepted;

    //NotNull
    private Boolean isUpdated;


    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getCompletionPercent() {
        return completionPercent;
    }

    public void setCompletionPercent(Integer completionPercent) {
        this.completionPercent = completionPercent;
    }

    public String getProofImageUrl() {
        return proofImageUrl;
    }

    public void setProofImageUrl(String proofImageUrl) {
        this.proofImageUrl = proofImageUrl;
    }

    public String getProofVideoLink() {
        return proofVideoLink;
    }

    public void setProofVideoLink(String proofVideoLink) {
        this.proofVideoLink = proofVideoLink;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Boolean getUpdated() {
        return isUpdated;
    }

    public void setUpdated(Boolean updated) {
        isUpdated = updated;
    }

}
