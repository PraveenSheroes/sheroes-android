package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by priyanka on 24/04/17.
 */

public class BellNotificationResponse extends BaseResponse{
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("record_id")
    @Expose
    private String recordId;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("solr_ignore_last_activity_date")
    @Expose
    private String lastActivityDate;
    @SerializedName("solr_ignore_deep_link_url")
    @Expose
    private String solrIgnoreDeepLinkUrl;
    @SerializedName("solr_ignore_icon_image_url")
    @Expose
    private String solrIgnoreIconImageUrl;
    @SerializedName("solr_ignore_author_or_entity_image_url")
    @Expose
    private String solrIgnoreAuthorOrEntityImageUrl;
    @SerializedName("solr_ignore_community_participant_id")
    @Expose
    private Long solrIgnoreAuthorCommunityParticipantId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public String getSolrIgnoreDeepLinkUrl() {
        return solrIgnoreDeepLinkUrl;
    }

    public void setSolrIgnoreDeepLinkUrl(String solrIgnoreDeepLinkUrl) {
        this.solrIgnoreDeepLinkUrl = solrIgnoreDeepLinkUrl;
    }

    public String getSolrIgnoreAuthorOrEntityImageUrl() {
        return solrIgnoreAuthorOrEntityImageUrl;
    }

    public void setSolrIgnoreAuthorOrEntityImageUrl(String solrIgnoreAuthorOrEntityImageUrl) {
        this.solrIgnoreAuthorOrEntityImageUrl = solrIgnoreAuthorOrEntityImageUrl;
    }

    public Long getSolrIgnoreAuthorCommunityParticipantId() {
        return solrIgnoreAuthorCommunityParticipantId;
    }

    public void setSolrIgnoreAuthorCommunityParticipantId(Long solrIgnoreAuthorCommunityParticipantId) {
        this.solrIgnoreAuthorCommunityParticipantId = solrIgnoreAuthorCommunityParticipantId;
    }
    public String getSolrIgnoreIconImageUrl() {
        return solrIgnoreIconImageUrl;
    }

    public void setSolrIgnoreIconImageUrl(String solrIgnoreIconImageUrl) {
        this.solrIgnoreIconImageUrl = solrIgnoreIconImageUrl;
    }

    public BellNotificationResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.recordId);
        dest.writeInt(this.priority);
        dest.writeString(this.lastActivityDate);
        dest.writeString(this.solrIgnoreDeepLinkUrl);
        dest.writeString(this.solrIgnoreIconImageUrl);
        dest.writeString(this.solrIgnoreAuthorOrEntityImageUrl);
        dest.writeValue(this.solrIgnoreAuthorCommunityParticipantId);
    }

    protected BellNotificationResponse(Parcel in) {
        super(in);
        this.title = in.readString();
        this.type = in.readString();
        this.recordId = in.readString();
        this.priority = in.readInt();
        this.lastActivityDate = in.readString();
        this.solrIgnoreDeepLinkUrl = in.readString();
        this.solrIgnoreIconImageUrl = in.readString();
        this.solrIgnoreAuthorOrEntityImageUrl = in.readString();
        this.solrIgnoreAuthorCommunityParticipantId = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<BellNotificationResponse> CREATOR = new Creator<BellNotificationResponse>() {
        @Override
        public BellNotificationResponse createFromParcel(Parcel source) {
            return new BellNotificationResponse(source);
        }

        @Override
        public BellNotificationResponse[] newArray(int size) {
            return new BellNotificationResponse[size];
        }
    };
}
