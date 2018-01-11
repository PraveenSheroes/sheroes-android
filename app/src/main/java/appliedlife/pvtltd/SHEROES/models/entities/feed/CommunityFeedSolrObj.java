package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {CommunityFeedSolrObj.class,FeedDetail.class})
public class CommunityFeedSolrObj extends FeedDetail implements Cloneable {
    public static String COMMUNITY_OBJ = "Community Object";

    @SerializedName("community_type_s")
    public String communityType;

    @SerializedName("community_type_l")
    public Long communityTypeId;

    @SerializedName("is_closed_b")
    public boolean isClosedCommunity;

    @SerializedName(value = "solr_ignore_no_of_members")
    private int noOfMembers = 0;

    @SerializedName(value = "solr_ignore_no_of_pending_requests")
    private int noOfPendingRequest = 0;

    @SerializedName(value = "solr_ignore_is_owner")
    private boolean isOwner;

    @SerializedName(value = "solr_ignore_is_member")
    private boolean isMember;

    @SerializedName(value = "solr_ignore_is_request_pending")
    private boolean isRequestPending;

    @SerializedName("community_primary_color_s")
    public String communityPrimaryColor;

    @SerializedName("community_secondary_color_s")
    public String communitySecondaryColor;

    @SerializedName("default_tab_joined_key_s")
    public String defaultTabJoinedKey;

    @SerializedName("default_tab_key_s")
    public String defaultTabKey;

    @SerializedName("new_post_hint_s")
    public String newPostHint;

    @SerializedName("solr_ignore_community_tabs")
    public List<CommunityTab> communityTabs;

    @SerializedName("title_text_color_s")
    public String titleTextColor;

    private boolean isMutualCommunityFirstItem ;

    private boolean isOtherCommunityFirstItem;

    private int mutualCommunityCount;

    private boolean showHeader;

    public boolean isMutualCommunityFirstItem() {
        return isMutualCommunityFirstItem;
    }

    public void setMutualCommunityFirstItem(boolean mutualCommunityFirstItem) {
        isMutualCommunityFirstItem = mutualCommunityFirstItem;
    }

    public boolean isOtherCommunityFirstItem() {
        return isOtherCommunityFirstItem;
    }

    public void setOtherCommunityFirstItem(boolean otherCommunityFirstItem) {
        isOtherCommunityFirstItem = otherCommunityFirstItem;
    }

    public int getMutualCommunityCount() {
        return mutualCommunityCount;
    }

    public void setMutualCommunityCount(int mutualCommunityCount) {
        this.mutualCommunityCount = mutualCommunityCount;
    }

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public String getCommunityType() {
        return communityType;
    }

    public void setCommunityType(String communityType) {
        this.communityType = communityType;
    }

    public Long getCommunityTypeId() {
        return communityTypeId;
    }

    public void setCommunityTypeId(Long communityTypeId) {
        this.communityTypeId = communityTypeId;
    }

    public boolean isClosedCommunity() {
        return isClosedCommunity;
    }

    public void setClosedCommunity(boolean closedCommunity) {
        isClosedCommunity = closedCommunity;
    }

    public int getNoOfMembers() {
        return noOfMembers;
    }

    public void setNoOfMembers(int noOfMembers) {
        this.noOfMembers = noOfMembers;
    }

    public int getNoOfPendingRequest() {
        return noOfPendingRequest;
    }

    public void setNoOfPendingRequest(int noOfPendingRequest) {
        this.noOfPendingRequest = noOfPendingRequest;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public boolean isRequestPending() {
        return isRequestPending;
    }

    public void setRequestPending(boolean requestPending) {
        isRequestPending = requestPending;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
