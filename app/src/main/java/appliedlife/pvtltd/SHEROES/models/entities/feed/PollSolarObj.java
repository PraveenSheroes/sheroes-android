package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionModel;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollType;

@Parcel(analyze = {PollSolarObj.class, FeedDetail.class})
public class PollSolarObj extends FeedDetail {
    public static final String POLL_OBJ = "POLL_OBJ";
    @SerializedName("is_anonymous_b")
    private boolean isAnonymous;

    @SerializedName("community_i")
    public long communityId;

    private int isEditOrDelete;

    @SerializedName("poll_type_s")
    private PollType pollType;

    @SerializedName("survey_id_l")
    private long surveyId;

    @SerializedName("show_results_b")
    private boolean showResults;

    @SerializedName("starts_at_dt")
    private String startsAt;

    @SerializedName("ends_at_dt")
    private String endsAt;

    @SerializedName("priority_i")
    private int priority;

    @SerializedName("solr_ignore_poll_options")
    private List<PollOptionModel> pollOptions;

    @SerializedName(value = "likes_count_l")
    private long likesCount = 0L;

    @SerializedName(value = "comments_count_l")
    private long commentsCount = 0L;

    @SerializedName("is_quiz")
    private boolean isQuiz;

    @SerializedName("quiz_duration_seconds")
    private boolean quizDurationInSeconds;

    @SerializedName("solr_ignore_total_no_of_responses")
    private long totalNumberOfResponsesOnPoll;

    @SerializedName("solr_ignore_is_responed_on_poll")
    private boolean isRespondedOnPoll = false;

    @SerializedName("solr_ignore_is_in_survey")
    private boolean isInSurvey = false;

    @SerializedName(value = "solr_ignore_poll_community_name")
    private String pollCommunityName;

    @SerializedName("is_commumity_poll")
    private boolean isCommunityPoll;

    @SerializedName(value = "community_participant_id_l")
    private long communityParticipantId;

    @SerializedName(value = "solr_ignore_is_community_owner")
    private boolean isCommunityOwner;

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public int getIsEditOrDelete() {
        return isEditOrDelete;
    }

    public void setIsEditOrDelete(int isEditOrDelete) {
        this.isEditOrDelete = isEditOrDelete;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<PollOptionModel> getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(List<PollOptionModel> pollOptions) {
        this.pollOptions = pollOptions;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean getQuiz() {
        return isQuiz;
    }

    public void setQuiz(boolean quiz) {
        isQuiz = quiz;
    }

    public boolean getQuizDurationInSeconds() {
        return quizDurationInSeconds;
    }

    public void setQuizDurationInSeconds(boolean quizDurationInSeconds) {
        this.quizDurationInSeconds = quizDurationInSeconds;
    }

    public long getTotalNumberOfResponsesOnPoll() {
        return totalNumberOfResponsesOnPoll;
    }

    public void setTotalNumberOfResponsesOnPoll(long totalNumberOfResponsesOnPoll) {
        this.totalNumberOfResponsesOnPoll = totalNumberOfResponsesOnPoll;
    }

    public boolean getRespondedOnPoll() {
        return isRespondedOnPoll;
    }

    public void setRespondedOnPoll(boolean respondedOnPoll) {
        isRespondedOnPoll = respondedOnPoll;
    }

    public boolean getInSurvey() {
        return isInSurvey;
    }

    public void setInSurvey(boolean inSurvey) {
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

    public long getCommunityParticipantId() {
        return communityParticipantId;
    }

    public void setCommunityParticipantId(long communityParticipantId) {
        this.communityParticipantId = communityParticipantId;
    }

    public boolean getCommunityOwner() {
        return isCommunityOwner;
    }

    public void setCommunityOwner(boolean communityOwner) {
        isCommunityOwner = communityOwner;
    }

    public PollType getPollType() {
        return pollType;
    }

    public void setPollType(PollType pollType) {
        this.pollType = pollType;
    }
}
