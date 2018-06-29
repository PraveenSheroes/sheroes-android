package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen on 26/06/18.
 */
@Parcel(analyze = {BellNotification.class})
public class BellNotification{
    @SerializedName("crdt")
    private String crdt;
    @SerializedName("icon")
    private String icon;
    @SerializedName("title")
    private String title;
    @SerializedName("message_summary")
    private String messageSummary;
    @SerializedName("message")
    private String message;
    @SerializedName("left_image_icon")
    private String leftImageIcon;
    @SerializedName("right_image_icon")
    private String rightImageIcon;
    @SerializedName("deep_link_url")
    private String deepLinkUrl;
    @SerializedName("branch_url")
    private String branchUrl;
    @SerializedName("type")
    private String type;
    @SerializedName("category")
    private String category;
    @SerializedName("lastModifiedOn")
    private String lastModifiedOn;
    @SerializedName("solr_ignore_posting_date_dt")
    private String solrIgnorePostingDateDt;
    @SerializedName("id")
    private String id;
    @SerializedName("is_read")
    private boolean isRead;
    @SerializedName("is_anonymous_participation")
    private boolean isAnonymousParticipation;

    public String getCrdt() {
        return crdt;
    }

    public void setCrdt(String crdt) {
        this.crdt = crdt;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageSummary() {
        return messageSummary;
    }

    public void setMessageSummary(String messageSummary) {
        this.messageSummary = messageSummary;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLeftImageIcon() {
        return leftImageIcon;
    }

    public void setLeftImageIcon(String leftImageIcon) {
        this.leftImageIcon = leftImageIcon;
    }

    public String getRightImageIcon() {
        return rightImageIcon;
    }

    public void setRightImageIcon(String rightImageIcon) {
        this.rightImageIcon = rightImageIcon;
    }

    public String getDeepLinkUrl() {
        return deepLinkUrl;
    }

    public void setDeepLinkUrl(String deepLinkUrl) {
        this.deepLinkUrl = deepLinkUrl;
    }

    public String getBranchUrl() {
        return branchUrl;
    }

    public void setBranchUrl(String branchUrl) {
        this.branchUrl = branchUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getSolrIgnorePostingDateDt() {
        return solrIgnorePostingDateDt;
    }

    public void setSolrIgnorePostingDateDt(String solrIgnorePostingDateDt) {
        this.solrIgnorePostingDateDt = solrIgnorePostingDateDt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isAnonymousParticipation() {
        return isAnonymousParticipation;
    }

    public void setAnonymousParticipation(boolean anonymousParticipation) {
        isAnonymousParticipation = anonymousParticipation;
    }
}
