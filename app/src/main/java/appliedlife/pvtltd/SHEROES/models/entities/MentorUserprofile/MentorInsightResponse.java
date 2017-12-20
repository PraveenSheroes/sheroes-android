package appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by Praveen on 14/12/17.
 */
@Parcel(analyze = {MentorInsightResponse.class, BaseResponse.class})
public class MentorInsightResponse extends BaseResponse {
    @SerializedName("user_doc")
    @Expose
    private UserSolrObj userDoc;
    @SerializedName("no_of_followers_7_days")
    @Expose
    private int noOfFollowers7Days;
    @SerializedName("total_no_of_post_created")
    @Expose
    private int totalNoOfPostCreated;
    @SerializedName("total_no_of_post_7_days")
    @Expose
    private int totalNoOfPost7Days;
    @SerializedName("total_no_of_likes")
    @Expose
    private int totalNoOfLikes;
    @SerializedName("total_no_of_comments_on_user_post")
    @Expose
    private int totalNoOfCommentsOnUserPost;
    @SerializedName("total_no_of_questions")
    @Expose
    private int totalNoOfQuestions;
    @SerializedName("total_no_of_questions_7_days")
    @Expose
    private int totalNoOfQuestions7Days;
    @SerializedName("total_no_of_answers_7_days")
    @Expose
    private int totalNoOfAnswers7Days;
    @SerializedName("total_no_of_impressions")
    @Expose
    private int totalNoOfImpressions;
    @SerializedName("total_no_of_impressions_7_days")
    @Expose
    private int totalNoOfImpressions7Days;
    @SerializedName("total_profile_visitors_in_7_days")
    @Expose
    private int totalProfileVisitorsIn7Days;

    public UserSolrObj getUserDoc() {
        return userDoc;
    }

    public void setUserDoc(UserSolrObj userDoc) {
        this.userDoc = userDoc;
    }

    public int getNoOfFollowers7Days() {
        return noOfFollowers7Days;
    }

    public void setNoOfFollowers7Days(int noOfFollowers7Days) {
        this.noOfFollowers7Days = noOfFollowers7Days;
    }

    public int getTotalNoOfPostCreated() {
        return totalNoOfPostCreated;
    }

    public void setTotalNoOfPostCreated(int totalNoOfPostCreated) {
        this.totalNoOfPostCreated = totalNoOfPostCreated;
    }

    public int getTotalNoOfPost7Days() {
        return totalNoOfPost7Days;
    }

    public void setTotalNoOfPost7Days(int totalNoOfPost7Days) {
        this.totalNoOfPost7Days = totalNoOfPost7Days;
    }

    public int getTotalNoOfLikes() {
        return totalNoOfLikes;
    }

    public void setTotalNoOfLikes(int totalNoOfLikes) {
        this.totalNoOfLikes = totalNoOfLikes;
    }

    public int getTotalNoOfCommentsOnUserPost() {
        return totalNoOfCommentsOnUserPost;
    }

    public void setTotalNoOfCommentsOnUserPost(int totalNoOfCommentsOnUserPost) {
        this.totalNoOfCommentsOnUserPost = totalNoOfCommentsOnUserPost;
    }

    public int getTotalNoOfQuestions() {
        return totalNoOfQuestions;
    }

    public void setTotalNoOfQuestions(int totalNoOfQuestions) {
        this.totalNoOfQuestions = totalNoOfQuestions;
    }

    public int getTotalNoOfQuestions7Days() {
        return totalNoOfQuestions7Days;
    }

    public void setTotalNoOfQuestions7Days(int totalNoOfQuestions7Days) {
        this.totalNoOfQuestions7Days = totalNoOfQuestions7Days;
    }

    public int getTotalNoOfAnswers7Days() {
        return totalNoOfAnswers7Days;
    }

    public void setTotalNoOfAnswers7Days(int totalNoOfAnswers7Days) {
        this.totalNoOfAnswers7Days = totalNoOfAnswers7Days;
    }

    public int getTotalNoOfImpressions() {
        return totalNoOfImpressions;
    }

    public void setTotalNoOfImpressions(int totalNoOfImpressions) {
        this.totalNoOfImpressions = totalNoOfImpressions;
    }

    public int getTotalNoOfImpressions7Days() {
        return totalNoOfImpressions7Days;
    }

    public void setTotalNoOfImpressions7Days(int totalNoOfImpressions7Days) {
        this.totalNoOfImpressions7Days = totalNoOfImpressions7Days;
    }

    public int getTotalProfileVisitorsIn7Days() {
        return totalProfileVisitorsIn7Days;
    }

    public void setTotalProfileVisitorsIn7Days(int totalProfileVisitorsIn7Days) {
        this.totalProfileVisitorsIn7Days = totalProfileVisitorsIn7Days;
    }
}
