package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

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

}
