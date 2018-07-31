package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.SerializedName;

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
    public  int beginnerStartIndex = 2;

    @SerializedName("intermediate_tick_index")
    public  int intermediateStartIndex = 5;

    @SerializedName("promoCardUrl")
    public String mPromoCardUrl = "Quiz";

    @SerializedName("articleGuideline")
    public String articleGuideline;

    @SerializedName("herStoryHintText")
    public String mHerStoryHintText = "Begin your story here...";

    @SerializedName("badgeShareMsg")
    public String mBadgeShareMsg = "I won the exciting new Super SHEROES badge on the SHEROES Community. It\'s a women only app where you can share anything without hesitation and win these badges. Check out mine here:" ;

    @SerializedName("superSheroesCriteriaMsg")
    public String superSheroesCriteriaMsg = "<p>The Super SHEROES Leaderboard in every community is a way of recognizing and gratifying the top users from the community who are posting the highest quality content and are engaging the maximum number of users on their post.</p>\n" +
            "<p><strong>How to become a Super SHEROES?</strong></p>\n" +
            "<p>The simplest way to become a Super SHEROES is by posting and sharing valuable content in the community which engages other users. The users who get maximum likes and comments on their posts every week will get the title of Super SHEROES and are reflected on the leaderboard tab.</p>\n" +
            "<p><strong>When is the Super SHEROES Leaderboard updated?</strong></p>\n" +
            "<p>The Super SHEROES Leaderboard is updated every Monday morning. The list denotes the top 10 users from previous week who got maximum engagement on their posts in this community.</p>\n" +
            "<p><strong>What does the Super SHEROES get?</strong></p>\n" +
            "<p>Super SHEROES are gratified with their name on leaderboard and unique community badge. Soon, their profiles and profile picture will reflect all the badges that they earned.</p>";

    @SerializedName("maleUserShareText")
    public String mMaleErrorText = "Hi, I found this women only app for you. Its name is SHEROES (Women Heroes). You can talk about and ask anything (even anonymously) without hesitation and help other women by giving them good advice. https://shrs.me/xtap573vXM";

    @SerializedName("showInviteFriendTab")
    public boolean showInviteFriendTab;
}
