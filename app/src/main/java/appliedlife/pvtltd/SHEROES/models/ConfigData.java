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

}
