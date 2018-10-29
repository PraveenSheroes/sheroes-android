package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by ujjwal on 19/02/18.
 */

public class ConfigData {

    //For Profile Share
    @SerializedName("profileShareText")
    public String mProfileSharedText = "I found like-minded women on SHEROES to share my thoughts without hesitation.";

    @SerializedName("commentHolderText")
    public String mCommentHolderText = "Comment here, Use @ to tag people...";

    @SerializedName("createPostText")
    public String mCreatePostText = "Type here, Use @ to tag people...";

    @SerializedName("feedHeaderPostText")
    public String mFeedHeaderPostText = "Ask/ Share without hesitation";

    @SerializedName("showArticleViews")
    public boolean showArticleViews;

    @SerializedName("thumborKey")
    public String thumborKey;

    @SerializedName("spam_reasons")
    public SpamReasons reasonOfSpamCategory;

    @SerializedName("deactivation_reasons")
    public DeactivationReasons deactivationReasons;

    @SerializedName("update_title")
    public String updateTitle;

    @SerializedName("update_description")
    public String updateDescription;

    @SerializedName("update_version")
    public Integer updateVersion;

    @SerializedName("userTagCreatePostInfoText")
    public String mUserTagCreatePostInfoText = "You can tag community owners & your followers";

    @SerializedName("userTagCommentInfoText")
    public String mUserTagCommentInfoText = "You can tag community owners, your followers or people who engaged on this post";

    @SerializedName("progressbar_max_dashes")
    public int maxDash = 8;

    @SerializedName("beginner_tick_index")
    public int beginnerStartIndex = 2;

    @SerializedName("intermediate_tick_index")
    public int intermediateStartIndex = 5;

    @SerializedName("leaderboard_top_user_count")
    public int leaderBoardTopUserCount = 10;

    @SerializedName("leaderboard_user_rank_threshold")
    public int leaderBoardUserRankThreshold = 999;

    @SerializedName("leaderboard_exceed_user_rank_")
    public String userRankExceedLimitText = "1K+";

    @SerializedName("promoCardUrl")
    public String mPromoCardUrl = "Quiz";

    @SerializedName("articleGuideline")
    public String articleGuideline= AppConstants.ARTICLE_GUIDELINE;

    @SerializedName("herStoryHintText")
    public String mHerStoryHintText = "Begin your story here...";

    @SerializedName("badgeShareMsg")
    public String mBadgeShareMsg = "I won the exciting new Super SHEROES badge on the SHEROES Community. It\'s a women only app where you can share anything without hesitation and win these badges. Check out mine here:";

    @SerializedName("superSheroesCriteriaMsg")
    public String superSheroesCriteriaMsg = "<p>The Super SHEROES Leaderboard in every community is a way of recognizing and gratifying the top users from the community who are posting the highest quality content and are engaging the maximum number of users on their post.</p>\n" +
            "<p><strong>How to become a Super SHEROES?</strong></p>\n" +
            "<p>The simplest way to become a Super SHEROES is by posting and sharing valuable content in the community which engages other users. The users who get maximum likes and comments on their posts every week will get the title of Super SHEROES and are reflected on the leaderboard tab.</p>\n" +
            "<p><strong>When is the Super SHEROES Leaderboard updated?</strong></p>\n" +
            "<p>The Super SHEROES Leaderboard is updated every Monday morning. The list denotes the top 10 users from previous week who got maximum engagement on their posts in this community.</p>\n" +
            "<p><strong>What does the Super SHEROES get?</strong></p>\n" +
            "<p>Super SHEROES are gratified with their name on leaderboard and unique community badge. Soon, their profiles and profile picture will reflect all the badges that they earned.</p>";

    @SerializedName("maleUserShareText")
    public String mMaleShareText = "Hi, I found this women only app for you. Its name is SHEROES (Women Heroes). You can talk about and ask anything (even anonymously) without hesitation and help other women by giving them good advice. https://shrs.me/xtap573vXM";

    @SerializedName("maleUserErrorText")
    public String mMaleErrorText = "<html>" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +"</head>" +
            "<body style=\"text-align: center;font-weight:bold;margin:0px;padding:0px;\">" +
            "<div style=\"text-align: center;font-weight:bold;margin: 0px 0px 0px 0px;\"> <b >Oops, You are a gentleman!</b></div>" +
            "<div style=\"text-align: center\">" +
            " Your email id says you are a man. <br>" +
            "    <span style=\"color: #dc4541\">SHEROES is a women only platform.</span> Why not <span style=\"color: #dc4541\">share this app</span> with the women in your life?" +
            "</div>" +
            "</body>" +
            "</html>";

    @SerializedName("write_story_tags")
    public String mWriteStoryTag = "Add up to 5 tags";

    @SerializedName("showInviteFriendTab")
    public boolean showInviteFriendTab;

    @SerializedName("challengeWinnerDailogMessage")
    public String challengeWinnerDialogMassage = "You have won the Invitation to SHEROES Summit 2018. We loved your response to our Challenge.";

    @SerializedName("view_visibility")
    public int visibilityPercentage = 20;

    @SerializedName("frequency_batch_request")
    public int frequencyBatchRequest = 10;

    @SerializedName("min_engagement_time")
    public int minEngagementTime = 250;

    @SerializedName("impressionFrequency")
    public int impressionFrequency = 60000;

    @SerializedName("impressionMaxTimeout")
    public int impressionMaxTimeout = 180000;

}
