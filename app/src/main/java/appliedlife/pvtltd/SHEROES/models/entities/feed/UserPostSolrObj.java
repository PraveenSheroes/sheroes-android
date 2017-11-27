package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {UserPostSolrObj.class,BaseEntityOrParticipantModel.class})
public class UserPostSolrObj extends BaseEntityOrParticipantModel {
    @SerializedName(value = "search_id_post_image")
    private List<Long> imagesIds;

    @SerializedName(value = "display_text_image_url")
    private List<String> imageUrls;

    @SerializedName("community_i")
    private Long communityId;

    @SerializedName("is_commumity_post_b")
    private boolean isCommunityPost;

    @SerializedName("is_anonymous_b")
    private boolean isAnonymous;

    @SerializedName(value = "solr_ignore_post_community_name")
    private String postCommunityName;

    @SerializedName(value = "solr_ignore_is_closed")
    private boolean postCommunityClosed;

    @SerializedName(value = "solr_ignore_post_community_logo")
    private String postCommunityLogo;

    @SerializedName(value = "community_participant_id_l")
    private Long communityParticipantId;

    @SerializedName(value = "is_closed_b")
    private boolean isClosedCommunity;

    @SerializedName(value = "s_disp_third_party_unique_id")
    private String sourceEntityId;

    @SerializedName(value = "start_date_dt")
    private Date entityStartDate;

    @SerializedName(value = "rating_i")
    private Integer rating;

    @SerializedName(value = "is_comment_allowed_b")
    private Boolean isCommentAllowed;

    @SerializedName(value = "solr_ignore_community_type_id")
    private Long communityTypeId;


    @SerializedName(value="solr_ignore_is_community_owner")
    private Boolean isCommunityOwner;

    @SerializedName(value = "post_image_width_is")
    private List<Integer> imageWidth;
    @SerializedName(value = "post_image_height_is")
    private List<Integer> imageHeight;
    @SerializedName(value = "post_image_dimention_ratio_ds")
    private List<Double>  imageRatio;

    @SerializedName(value="community_type_id_l")
    private Long commTypeId;

    @SerializedName(value="source_type_s")
    private String sourceType;

    @SerializedName(value="user_post_source_entity_id_l")
    private Long userPostSourceEntityId;

    @SerializedName("challenge_accept_post_text_s")
    private String challengeAcceptPostText;

    public List<Long> getImagesIds() {
        return imagesIds;
    }

    public void setImagesIds(List<Long> imagesIds) {
        this.imagesIds = imagesIds;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public boolean isCommunityPost() {
        return isCommunityPost;
    }

    public void setCommunityPost(boolean communityPost) {
        isCommunityPost = communityPost;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getPostCommunityName() {
        return postCommunityName;
    }

    public void setPostCommunityName(String postCommunityName) {
        this.postCommunityName = postCommunityName;
    }

    public boolean isPostCommunityClosed() {
        return postCommunityClosed;
    }

    public void setPostCommunityClosed(boolean postCommunityClosed) {
        this.postCommunityClosed = postCommunityClosed;
    }

    public String getPostCommunityLogo() {
        return postCommunityLogo;
    }

    public void setPostCommunityLogo(String postCommunityLogo) {
        this.postCommunityLogo = postCommunityLogo;
    }

    public Long getCommunityParticipantId() {
        return communityParticipantId;
    }

    public void setCommunityParticipantId(Long communityParticipantId) {
        this.communityParticipantId = communityParticipantId;
    }

    public boolean isClosedCommunity() {
        return isClosedCommunity;
    }

    public void setClosedCommunity(boolean closedCommunity) {
        isClosedCommunity = closedCommunity;
    }

    public String getSourceEntityId() {
        return sourceEntityId;
    }

    public void setSourceEntityId(String sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    public Date getEntityStartDate() {
        return entityStartDate;
    }

    public void setEntityStartDate(Date entityStartDate) {
        this.entityStartDate = entityStartDate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getCommentAllowed() {
        return isCommentAllowed;
    }

    public void setCommentAllowed(Boolean commentAllowed) {
        isCommentAllowed = commentAllowed;
    }

    public Long getCommunityTypeId() {
        return communityTypeId;
    }

    public void setCommunityTypeId(Long communityTypeId) {
        this.communityTypeId = communityTypeId;
    }

    public Boolean getCommunityOwner() {
        return isCommunityOwner;
    }

    public void setCommunityOwner(Boolean communityOwner) {
        isCommunityOwner = communityOwner;
    }

    public List<Integer> getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(List<Integer> imageWidth) {
        this.imageWidth = imageWidth;
    }

    public List<Integer> getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(List<Integer> imageHeight) {
        this.imageHeight = imageHeight;
    }

    public List<Double> getImageRatio() {
        return imageRatio;
    }

    public void setImageRatio(List<Double> imageRatio) {
        this.imageRatio = imageRatio;
    }

    public Long getCommTypeId() {
        return commTypeId;
    }

    public void setCommTypeId(Long commTypeId) {
        this.commTypeId = commTypeId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getUserPostSourceEntityId() {
        return userPostSourceEntityId;
    }

    public void setUserPostSourceEntityId(Long userPostSourceEntityId) {
        this.userPostSourceEntityId = userPostSourceEntityId;
    }

    public String getChallengeAcceptPostText() {
        return challengeAcceptPostText;
    }

    public void setChallengeAcceptPostText(String challengeAcceptPostText) {
        this.challengeAcceptPostText = challengeAcceptPostText;
    }
}
