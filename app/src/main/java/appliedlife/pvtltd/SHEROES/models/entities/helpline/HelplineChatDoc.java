
package appliedlife.pvtltd.SHEROES.models.entities.helpline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class HelplineChatDoc extends BaseResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("search_text")
    @Expose
    private String searchText;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("question_or_answer_id")
    @Expose
    private int questionOrAnswerId;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("attendantid")
    @Expose
    private int attendantid;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("participant_user_id")
    @Expose
    private long participantUserId;
    @SerializedName("participant_attendantid")
    @Expose
    private long participantAttendantid;
    @SerializedName("solr_ignore_participant_user_name")
    @Expose
    private String solrIgnoreParticipantUserName;
    @SerializedName("solr_ignore_participant_attendant_name")
    @Expose
    private String solrIgnoreParticipantAttendantName;
    @SerializedName("solr_ignore_participant_user_image_url")
    @Expose
    private String solrIgnoreParticipantUserImageUrl;
    @SerializedName("solr_ignore_participant_attendant_image_url")
    @Expose
    private String solrIgnoreParticipantAttendantImageUrl;
    @SerializedName("solr_ignore_created_on")
    @Expose
    private String formatedDate;
    @SerializedName(value = "thumbnail_image_url")
    private String thumbnailImageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getQuestionOrAnswerId() {
        return questionOrAnswerId;
    }

    public void setQuestionOrAnswerId(int questionOrAnswerId) {
        this.questionOrAnswerId = questionOrAnswerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAttendantid() {
        return attendantid;
    }

    public void setAttendantid(int attendantid) {
        this.attendantid = attendantid;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public long getParticipantUserId() {
        return participantUserId;
    }

    public String getSolrIgnoreParticipantUserImageUrl() {
        return solrIgnoreParticipantUserImageUrl;
    }

    public void setSolrIgnoreParticipantUserImageUrl(String solrIgnoreParticipantUserImageUrl) {
        this.solrIgnoreParticipantUserImageUrl = solrIgnoreParticipantUserImageUrl;
    }

    public String getSolrIgnoreParticipantUserName() {

        return solrIgnoreParticipantUserName;
    }

    public void setSolrIgnoreParticipantUserName(String solrIgnoreParticipantUserName) {
        this.solrIgnoreParticipantUserName = solrIgnoreParticipantUserName;
    }

    public long getParticipantAttendantid() {

        return participantAttendantid;
    }

    public void setParticipantAttendantid(long participantAttendantid) {
        this.participantAttendantid = participantAttendantid;
    }

    public void setParticipantUserId(long participantUserId) {
        this.participantUserId = participantUserId;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;

    }

    public void setParticipantAttendantid(int participantAttendantid) {
        this.participantAttendantid = participantAttendantid;
    }


    public String getSolrIgnoreParticipantAttendantName() {
        return solrIgnoreParticipantAttendantName;
    }

    public void setSolrIgnoreParticipantAttendantName(String solrIgnoreParticipantAttendantName) {
        this.solrIgnoreParticipantAttendantName = solrIgnoreParticipantAttendantName;
    }


    public String getSolrIgnoreParticipantAttendantImageUrl() {
        return solrIgnoreParticipantAttendantImageUrl;
    }

    public void setSolrIgnoreParticipantAttendantImageUrl(String solrIgnoreParticipantAttendantImageUrl) {
        this.solrIgnoreParticipantAttendantImageUrl = solrIgnoreParticipantAttendantImageUrl;
    }

    public String getFormatedDate() {
        return formatedDate;
    }

    public void setFormatedDate(String formatedDate) {
        this.formatedDate = formatedDate;
    }

    /*public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String solrIgnoreParticipantAttendantImageUrl) {
        this.solrIgnoreParticipantAttendantImageUrl = solrIgnoreParticipantAttendantImageUrl;
    }*/
}
