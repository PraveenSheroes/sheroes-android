package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionModel;

@Parcel(analyze = {PollSolarObj.class, FeedDetail.class})
public class PollSolarObj extends FeedDetail {
    public static final String POLL_OBJ = "POLL_OBJ";
    @SerializedName("is_anonymous_b")
    private boolean isAnonymous;

    @SerializedName("community_i")
    public Long communityId;

    private int isEditOrDelete;

    @SerializedName("poll_type_s")
    private String pollType;

    @SerializedName("survey_id_l")
    private Long surveyId;

    @SerializedName("show_results_b")
    private boolean showResults;

    @SerializedName("starts_at_dt")
    private String startsAt;

    @SerializedName("ends_at_dt")
    private String endsAt;

    @SerializedName("priority_i")
    private Integer priority;

    @SerializedName("solr_ignore_poll_options")
    private List<PollOptionModel> pollOptions;

    @SerializedName(value = "likes_count_l")
    private Long likesCount = 0L;

    @SerializedName(value = "comments_count_l")
    private Long commentsCount = 0L;

    @SerializedName("is_quiz")
    private Boolean isQuiz;

    @SerializedName("quiz_duration_seconds")
    private Boolean quizDurationInSeconds;

    @SerializedName("solr_ignore_total_no_of_responses")
    private Long totalNumberOfResponsesOnPoll;

    @SerializedName("solr_ignore_is_responed_on_poll")
    private Boolean isRespondedOnPoll = false;

    @SerializedName("solr_ignore_is_in_survey")
    private Boolean isInSurvey = false;

    @SerializedName(value = "solr_ignore_poll_community_name")
    private String pollCommunityName;

    @SerializedName("is_commumity_poll")
    private boolean isCommunityPoll;

    @SerializedName(value = "community_participant_id_l")
    private Long communityParticipantId;

    @SerializedName(value = "solr_ignore_is_community_owner")
    private Boolean isCommunityOwner;

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public int getIsEditOrDelete() {
        return isEditOrDelete;
    }

    public void setIsEditOrDelete(int isEditOrDelete) {
        this.isEditOrDelete = isEditOrDelete;
    }


    public String getPollType() {
        return pollType;
    }

    public void setPollType(String pollType) {
        this.pollType = pollType;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public boolean isShowResults() {
        return showResults;
    }

    public void setShowResults(boolean showResults) {
        this.showResults = showResults;
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

    public List<PollOptionModel> getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(List<PollOptionModel> pollOptions) {
        this.pollOptions = pollOptions;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public Long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Long commentsCount) {
        this.commentsCount = commentsCount;
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

    public Long getTotalNumberOfResponsesOnPoll() {
        return totalNumberOfResponsesOnPoll;
    }

    public void setTotalNumberOfResponsesOnPoll(Long totalNumberOfResponsesOnPoll) {
        this.totalNumberOfResponsesOnPoll = totalNumberOfResponsesOnPoll;
    }

    public Boolean getRespondedOnPoll() {
        return isRespondedOnPoll;
    }

    public void setRespondedOnPoll(Boolean respondedOnPoll) {
        isRespondedOnPoll = respondedOnPoll;
    }

    public Boolean getInSurvey() {
        return isInSurvey;
    }

    public void setInSurvey(Boolean inSurvey) {
        isInSurvey = inSurvey;
    }

    public String getPollCommunityName() {
        return pollCommunityName;
    }

    public void setPollCommunityName(String pollCommunityName) {
        this.pollCommunityName = pollCommunityName;
    }

    public boolean isCommunityPoll() {
        return isCommunityPoll;
    }

    public void setCommunityPoll(boolean communityPoll) {
        isCommunityPoll = communityPoll;
    }

    public Long getCommunityParticipantId() {
        return communityParticipantId;
    }

    public void setCommunityParticipantId(Long communityParticipantId) {
        this.communityParticipantId = communityParticipantId;
    }

    public Boolean getCommunityOwner() {
        return isCommunityOwner;
    }

    public void setCommunityOwner(Boolean communityOwner) {
        isCommunityOwner = communityOwner;
    }
}
