package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 22-03-2017.
 */

public class CommunityPostCreateRequest extends BaseRequest implements Parcelable {

    @SerializedName("entity_start_date")
    @Expose
    private String entityStartDate;

    @SerializedName("is_og_video_link_b")
    @Expose
    private boolean isOgVideoLinkB;
    @SerializedName("og_description_s")
    @Expose
    private String ogDescriptionS;
    @SerializedName("og_image_url_s")
    @Expose
    private String ogImageUrlS;
    @SerializedName("og_requested_url_s")
    @Expose
    private String ogRequestedUrlS;
    @SerializedName("og_title_s")
    @Expose
    private String ogTitleS;
    @SerializedName("source_entity_id")
    @Expose
    private Integer sourceEntityId;
    @SerializedName("videoLink")
    @Expose
    private boolean videoLink;

    @SerializedName("source_type")
    @Expose
    private String mSourceType;

    @SerializedName("id")
    @Expose
    private Long id=null;
    @SerializedName("community_id")
    @Expose
    private Long communityId=null;
    @SerializedName("creator_type")
    @Expose
    private String creatorType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("delete_image_id")
    @Expose
    private List<Long> deleteImagesIds = null;
    @SerializedName("is_active")
    @Expose
    private boolean isActive =true;
    @SerializedName("is_spam")
    @Expose
    private boolean isSpam;

    @SerializedName("is_top_post_b")
    @Expose
    private boolean isTopPost;

    @SerializedName("is_post_to_facebook")
    @Expose
    private boolean isPostToFacebook;

    @SerializedName("user_fb_access_token")
    @Expose
    private String userFbAccessToken;


    public String getmSourceType() {
        return mSourceType;
    }

    public void setmSourceType(String mSourceType) {
        this.mSourceType = mSourceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(String creatorType) {
        this.creatorType = creatorType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Long> getDeleteImagesIds() {
        return deleteImagesIds;
    }

    public void setDeleteImagesIds(List<Long> deleteImagesIds) {
        this.deleteImagesIds = deleteImagesIds;
    }

    public String getEntityStartDate() {
        return entityStartDate;
    }

    public void setEntityStartDate(String entityStartDate) {
        this.entityStartDate = entityStartDate;
    }

    public boolean isOgVideoLinkB() {
        return isOgVideoLinkB;
    }

    public void setOgVideoLinkB(boolean ogVideoLinkB) {
        isOgVideoLinkB = ogVideoLinkB;
    }


    public String getOgDescriptionS() {
        return ogDescriptionS;
    }

    public void setOgDescriptionS(String ogDescriptionS) {
        this.ogDescriptionS = ogDescriptionS;
    }

    public String getOgImageUrlS() {
        return ogImageUrlS;
    }

    public void setOgImageUrlS(String ogImageUrlS) {
        this.ogImageUrlS = ogImageUrlS;
    }

    public String getOgRequestedUrlS() {
        return ogRequestedUrlS;
    }

    public void setOgRequestedUrlS(String ogRequestedUrlS) {
        this.ogRequestedUrlS = ogRequestedUrlS;
    }

    public String getOgTitleS() {
        return ogTitleS;
    }

    public void setOgTitleS(String ogTitleS) {
        this.ogTitleS = ogTitleS;
    }


    public Integer getSourceEntityId() {
        return sourceEntityId;
    }

    public void setSourceEntityId(Integer sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    public boolean isVideoLink() {
        return videoLink;
    }

    public void setVideoLink(boolean videoLink) {
        this.videoLink = videoLink;
    }

    public CommunityPostCreateRequest() {
    }

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }

    public boolean isTopPost() {
        return isTopPost;
    }

    public void setTopPost(boolean topPost) {
        isTopPost = topPost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.entityStartDate);
        dest.writeByte(this.isOgVideoLinkB ? (byte) 1 : (byte) 0);
        dest.writeString(this.ogDescriptionS);
        dest.writeString(this.ogImageUrlS);
        dest.writeString(this.ogRequestedUrlS);
        dest.writeString(this.ogTitleS);
        dest.writeValue(this.sourceEntityId);
        dest.writeByte(this.videoLink ? (byte) 1 : (byte) 0);
        dest.writeString(this.mSourceType);
        dest.writeValue(this.id);
        dest.writeValue(this.communityId);
        dest.writeString(this.creatorType);
        dest.writeString(this.description);
        dest.writeStringList(this.images);
        dest.writeList(this.deleteImagesIds);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSpam ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isTopPost ? (byte) 1 : (byte) 0);
    }

    protected CommunityPostCreateRequest(Parcel in) {
        this.entityStartDate = in.readString();
        this.isOgVideoLinkB = in.readByte() != 0;
        this.ogDescriptionS = in.readString();
        this.ogImageUrlS = in.readString();
        this.ogRequestedUrlS = in.readString();
        this.ogTitleS = in.readString();
        this.sourceEntityId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.videoLink = in.readByte() != 0;
        this.mSourceType = in.readString();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.communityId = (Long) in.readValue(Long.class.getClassLoader());
        this.creatorType = in.readString();
        this.description = in.readString();
        this.images = in.createStringArrayList();
        this.deleteImagesIds = new ArrayList<Long>();
        in.readList(this.deleteImagesIds, Long.class.getClassLoader());
        this.isActive = in.readByte() != 0;
        this.isSpam = in.readByte() != 0;
        this.isTopPost = in.readByte() != 0;
    }

    public static final Creator<CommunityPostCreateRequest> CREATOR = new Creator<CommunityPostCreateRequest>() {
        @Override
        public CommunityPostCreateRequest createFromParcel(Parcel source) {
            return new CommunityPostCreateRequest(source);
        }

        @Override
        public CommunityPostCreateRequest[] newArray(int size) {
            return new CommunityPostCreateRequest[size];
        }
    };

    public boolean isPostToFacebook() {
        return isPostToFacebook;
    }

    public void setPostToFacebook(boolean postToFacebook) {
        isPostToFacebook = postToFacebook;
    }

    public String isUserFbAccessToken() {
        return userFbAccessToken;
    }

    public void setUserFbAccessToken(String userFbAccessToken) {
        this.userFbAccessToken = userFbAccessToken;
    }
}
