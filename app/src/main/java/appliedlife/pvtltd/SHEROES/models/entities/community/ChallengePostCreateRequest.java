package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;

/**
 * Created by SHEROES-TECH on 22-03-2017.
 */

public class ChallengePostCreateRequest extends BaseRequest implements Parcelable {

    @SerializedName("entity_start_date")
    @Expose
    private String entityStartDate;

    @SerializedName("is_og_video_link_b")
    @Expose
    private boolean isOgVideoLinkB;

    @SerializedName("is_accepted")
    @Expose
    private boolean isAccepted;

    @SerializedName("is_deleted")
    @Expose
    private boolean isDeleted;

    @SerializedName("is_updated")
    @Expose
    private boolean isUpdated;


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

    @SerializedName("completion_percent")
    @Expose
    private int mCompletionPercent;

    @SerializedName("challenge_id")
    @Expose
    private int mChallengeId;

    /* Mention fields*/

    @SerializedName("has_mentions")
    @Expose
    private boolean hasMentions;

    @SerializedName("user_mentions")
    @Expose
    private List<MentionSpan> userMentionList;


    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public int getmCompletionPercent() {
        return mCompletionPercent;
    }

    public void setmCompletionPercent(int mCompletionPercent) {
        this.mCompletionPercent = mCompletionPercent;
    }

    public int getmChallengeId() {
        return mChallengeId;
    }

    public void setmChallengeId(int mChallengeId) {
        this.mChallengeId = mChallengeId;
    }

    @SerializedName("id")
    @Expose
    private Long id=null;
    @SerializedName("community_id")
    @Expose
    private Long communityId=null;
    @SerializedName("creator_type")
    @Expose
    private String creatorType;
    @SerializedName("response_description")
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

    public ChallengePostCreateRequest() {
    }

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }

    public boolean isHasMentions() {
        return hasMentions;
    }

    public void setHasMentions(boolean hasMentions) {
        this.hasMentions = hasMentions;
    }

    public List<MentionSpan> getUserMentionList() {
        return userMentionList;
    }

    public void setUserMentionList(List<MentionSpan> userMentionList) {
        this.userMentionList = userMentionList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.entityStartDate);
        dest.writeByte(this.isOgVideoLinkB ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAccepted ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDeleted ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isUpdated ? (byte) 1 : (byte) 0);
        dest.writeString(this.ogDescriptionS);
        dest.writeString(this.ogImageUrlS);
        dest.writeString(this.ogRequestedUrlS);
        dest.writeString(this.ogTitleS);
        dest.writeValue(this.sourceEntityId);
        dest.writeByte(this.videoLink ? (byte) 1 : (byte) 0);
        dest.writeString(this.mSourceType);
        dest.writeInt(this.mCompletionPercent);
        dest.writeInt(this.mChallengeId);
        dest.writeByte(this.hasMentions ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.userMentionList);
        dest.writeValue(this.id);
        dest.writeValue(this.communityId);
        dest.writeString(this.creatorType);
        dest.writeString(this.description);
        dest.writeStringList(this.images);
        dest.writeList(this.deleteImagesIds);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSpam ? (byte) 1 : (byte) 0);
    }

    protected ChallengePostCreateRequest(Parcel in) {
        this.entityStartDate = in.readString();
        this.isOgVideoLinkB = in.readByte() != 0;
        this.isAccepted = in.readByte() != 0;
        this.isDeleted = in.readByte() != 0;
        this.isUpdated = in.readByte() != 0;
        this.ogDescriptionS = in.readString();
        this.ogImageUrlS = in.readString();
        this.ogRequestedUrlS = in.readString();
        this.ogTitleS = in.readString();
        this.sourceEntityId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.videoLink = in.readByte() != 0;
        this.mSourceType = in.readString();
        this.mCompletionPercent = in.readInt();
        this.mChallengeId = in.readInt();
        this.hasMentions = in.readByte() != 0;
        this.userMentionList = in.createTypedArrayList(MentionSpan.CREATOR);
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.communityId = (Long) in.readValue(Long.class.getClassLoader());
        this.creatorType = in.readString();
        this.description = in.readString();
        this.images = in.createStringArrayList();
        this.deleteImagesIds = new ArrayList<Long>();
        in.readList(this.deleteImagesIds, Long.class.getClassLoader());
        this.isActive = in.readByte() != 0;
        this.isSpam = in.readByte() != 0;
    }

    public static final Creator<ChallengePostCreateRequest> CREATOR = new Creator<ChallengePostCreateRequest>() {
        @Override
        public ChallengePostCreateRequest createFromParcel(Parcel source) {
            return new ChallengePostCreateRequest(source);
        }

        @Override
        public ChallengePostCreateRequest[] newArray(int size) {
            return new ChallengePostCreateRequest[size];
        }
    };
}
