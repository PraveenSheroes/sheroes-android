package appliedlife.pvtltd.SHEROES.models.entities.poll;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

public class CreatePollRequest extends BaseRequest {
    @SerializedName("description")
    private String description;

    @SerializedName("title")
    private String title;

    @SerializedName("image_url")
    private String imageUrl;

    //Not null
    @SerializedName("type")
    private PollType pollType;

    //Not null
    @SerializedName("creator_type")
    private String pollCreatorType;

    @SerializedName("survey_id")
    private Long surveyId;

    @SerializedName("show_results")
    private Boolean showResults=false;

    @SerializedName("is_anonymous")
    private Boolean isAnonymous;

    //Not null
    @SerializedName("community_id")
    private Long communityId;

    @SerializedName("is_active")
    private Boolean isActive;

    @SerializedName("starts_at")
    private String startsAt;

    @SerializedName("ends_at")
    private String endsAt;

    @SerializedName("priority")
    private Integer priority=0;

    @SerializedName("poll_options")
    private List<PollOptionRequestModel> pollOptions;

    @SerializedName("is_quiz")
    private Boolean isQuiz;

    @SerializedName("quiz_duration_seconds")
    private Boolean quizDurationInSeconds;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPollCreatorType() {
        return pollCreatorType;
    }

    public void setPollCreatorType(String pollCreatorType) {
        this.pollCreatorType = pollCreatorType;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Boolean getShowResults() {
        return showResults;
    }

    public void setShowResults(Boolean showResults) {
        this.showResults = showResults;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getQuiz() {
        return isQuiz;
    }

    public void setQuiz(Boolean quiz) {
        isQuiz = quiz;
    }

    public Boolean getQuizDurationInSeconds() {
        return quizDurationInSeconds;
    }

    public void setQuizDurationInSeconds(Boolean quizDurationInSeconds) {
        this.quizDurationInSeconds = quizDurationInSeconds;
    }

    public List<PollOptionRequestModel> getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(List<PollOptionRequestModel> pollOptions) {
        this.pollOptions = pollOptions;
    }

    public PollType getPollType() {
        return pollType;
    }

    public void setPollType(PollType pollType) {
        this.pollType = pollType;
    }
}
