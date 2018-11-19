package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.community.BadgeDetails;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {UserSolrObj.class, FeedDetail.class})
public class UserSolrObj extends FeedDetail {
    public int currentItemPosition;
    private boolean isSuggested;
    private boolean isCompactView;

    @SerializedName(value = "id_city_l")
    private long cityId;

    @SerializedName(value = "city_name_s")
    private String cityName;

    @SerializedName(value = "search_text_can_help_in")
    private List<String> canHelpIns;

    @SerializedName(value = "profile_id_l")
    private long profileId;

    @SerializedName(value = "currently_s")
    private String currently;

    @SerializedName(value = "s_disp_emailid")
    private String emailId;

    @SerializedName(value = "s_disp_mobile")
    private String mobileNo;

    @SerializedName(value = "p_is_company_admin_b")
    private boolean isCompanyAdmin;

    private int userFollowing;

    @SerializedName("solr_ignore_mentor_community_id")
    private long solrIgnoreMentorCommunityId;

    @SerializedName("solr_ignore_is_mentor_followed")
    private boolean solrIgnoreIsMentorFollowed;

    @SerializedName("solr_ignore_is_user_followed")
    private boolean solrIgnoreIsUserFollowed;

    @SerializedName("solr_ignore_no_of_following")
    private int followingCount;

    @SerializedName("solr_ignore_total_no_of_post_created")
    private int postCount;

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    @SerializedName("solr_ignore_no_of_followers")
    private int followerCount;

    @SerializedName("solr_ignore_profile_weight_count")
    private float profileCompletionWeight;

    @SerializedName("solr_ignore_profile_filled_fields_name")
    private String filledProfileFields;

    @SerializedName("solr_ignore_profile_unfilled_fields_name")
    private String unfilledProfileFields;

    @SerializedName("solr_ignore_show_profile_badge")
    private boolean isSheBadgeActive;

    @SerializedName("solr_ignore_profile_badge_url")
    private String profileBadgeUrl;

    @SerializedName("solr_ignore_leaderboard_community_name")
    private String mSolarIgnoreCommunityName;

    @SerializedName("solr_ignore_user_badges_list")
    @Expose
    private List<BadgeDetails> userBadgesList = null;

    public List<BadgeDetails> getUserBadgesList() {
        return userBadgesList;
    }

    public void setUserBadgesList(List<BadgeDetails> userBadgesList) {
        this.userBadgesList = userBadgesList;
    }

    public String getCityName() {
        return cityName;
    }

    public List<String> getCanHelpIns() {
        return canHelpIns;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

    @Override
    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getCurrently() {
        return currently;
    }

    public void setCurrently(String currently) {
        this.currently = currently;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public boolean getCompanyAdmin() {
        return isCompanyAdmin;
    }

    public void setCompanyAdmin(boolean companyAdmin) {
        isCompanyAdmin = companyAdmin;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public long getSolrIgnoreMentorCommunityId() {
        return solrIgnoreMentorCommunityId;
    }

    public void setSolrIgnoreMentorCommunityId(long solrIgnoreMentorCommunityId) {
        this.solrIgnoreMentorCommunityId = solrIgnoreMentorCommunityId;
    }

    public boolean isSolrIgnoreIsMentorFollowed() {
        return solrIgnoreIsMentorFollowed;
    }

    public void setSolrIgnoreIsMentorFollowed(boolean solrIgnoreIsMentorFollowed) {
        this.solrIgnoreIsMentorFollowed = solrIgnoreIsMentorFollowed;
    }

    public boolean isSuggested() {
        return isSuggested;
    }

    public void setSuggested(boolean suggested) {
        isSuggested = suggested;
    }

    public boolean isSolrIgnoreIsUserFollowed() {
        return solrIgnoreIsUserFollowed;
    }

    public void setSolrIgnoreIsUserFollowed(boolean solrIgnoreIsUserFollowed) {
        this.solrIgnoreIsUserFollowed = solrIgnoreIsUserFollowed;
    }

    public int getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(int userFollowing) {
        this.userFollowing = userFollowing;
    }

    public boolean isCompactView() {
        return isCompactView;
    }

    public void setCompactView(boolean compactView) {
        isCompactView = compactView;
    }

    public float getProfileCompletionWeight() {
        return profileCompletionWeight;
    }

    public void setProfileCompletionWeight(float profileCompletionWeight) {
        this.profileCompletionWeight = profileCompletionWeight;
    }

    public String getUnfilledProfileFields() {
        return unfilledProfileFields;
    }

    public void setUnfilledProfileFields(String unfilledProfileFields) {
        this.unfilledProfileFields = unfilledProfileFields;
    }

    public String getFilledProfileFields() {
        return filledProfileFields;
    }

    public void setFilledProfileFields(String filledProfileFields) {
        this.filledProfileFields = filledProfileFields;
    }

    public boolean isSheBadgeActive() {
        return isSheBadgeActive;
    }

    public void setSheBadgeActive(boolean sheBadgeActive) {
        isSheBadgeActive = sheBadgeActive;
    }

    public String getProfileBadgeUrl() {
        return profileBadgeUrl;
    }

    public void setProfileBadgeUrl(String profileBadgeUrl) {
        this.profileBadgeUrl = profileBadgeUrl;
    }

    public String getmSolarIgnoreCommunityName() {
        return mSolarIgnoreCommunityName;
    }

    public void setmSolarIgnoreCommunityName(String mSolarIgnoreCommunityName) {
        this.mSolarIgnoreCommunityName = mSolarIgnoreCommunityName;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }
}
