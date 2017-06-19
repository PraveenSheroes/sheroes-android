package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 16-06-2017.
 */

public class EventDetailPojo extends BaseResponse {

    @SerializedName("created_by_l")
    @Expose
    private int createdByL;
    @SerializedName("display_text_end_minute")
    @Expose
    private List<String> displayTextEndMinute = null;
    @SerializedName("entity_or_participant_type_id_i")
    @Expose
    private int entityOrParticipantTypeIdI;
    @SerializedName("posting_date_only_dt")
    @Expose
    private String postingDateOnlyDt;
    @SerializedName("p_last_modified_on")
    @Expose
    private String pLastModifiedOn;
    @SerializedName("id_of_entity_or_participant")
    @Expose
    private int idOfEntityOrParticipant;
    @SerializedName("city_name_s")
    @Expose
    private String cityNameS;
    @SerializedName("similarContents")
    @Expose
    private List<String> similarContents = null;
    @SerializedName("p_is_active")
    @Expose
    private boolean pIsActive;
    @SerializedName("display_text_start_minute")
    @Expose
    private List<String> displayTextStartMinute = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("display_text_entity_name")
    @Expose
    private List<String> displayTextEntityName = null;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("end_date_dt")
    @Expose
    private String endDateDt;
    @SerializedName("posting_date_dt")
    @Expose
    private String postingDateDt;
    @SerializedName("display_id_event_id")
    @Expose
    private List<Integer> displayIdEventId = null;
    @SerializedName("entity_or_participant_id")
    @Expose
    private int entityOrParticipantId;
    @SerializedName("display_text_end_hour")
    @Expose
    private List<String> displayTextEndHour = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("start_date_dt")
    @Expose
    private String startDateDt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("list_description")
    @Expose
    private String listDescription;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("display_text_start_hour")
    @Expose
    private List<String> displayTextStartHour = null;
    @SerializedName("p_crdt")
    @Expose
    private String pCrdt;
    @SerializedName("display_text_speaker_name")
    @Expose
    private List<String> displayTextSpeakerName = null;
    @SerializedName("display_id_speaker_id")
    @Expose
    private List<Integer> displayIdSpeakerId = null;
    @SerializedName("display_text_speaker_image_url")
    @Expose
    private List<String> displayTextSpeakerImageUrl = null;
    @SerializedName("display_text_speaker_desc")
    @Expose
    private List<String> displayTextSpeakerDesc = null;
    @SerializedName("display_text_speaker_designation")
    @Expose
    private List<String> displayTextSpeakerDesignation = null;
    @SerializedName("display_id_sponser_id")
    @Expose
    private List<Integer> displayIdSponserId = null;
    @SerializedName("display_text_sponser_url")
    @Expose
    private List<String> displayTextSponserUrl = null;
    @SerializedName("display_text_sponser_name")
    @Expose
    private List<String> displayTextSponserName = null;
    @SerializedName("display_text_sponser_logo_url")
    @Expose
    private List<String> displayTextSponserLogoUrl = null;
    @SerializedName("display_id_partner_id")
    @Expose
    private List<Integer> displayIdPartnerId = null;
    @SerializedName("display_text_partner_desc")
    @Expose
    private List<String> displayTextPartnerDesc = null;
    @SerializedName("display_text_partner_image_url")
    @Expose
    private List<String> displayTextPartnerImageUrl = null;
    @SerializedName("display_text_partner_name")
    @Expose
    private List<String> displayTextPartnerName = null;
    @SerializedName("display_id_service_id")
    @Expose
    private List<Integer> displayIdServiceId = null;
    @SerializedName("display_text_service_amount_desc")
    @Expose
    private List<String> displayTextServiceAmountDesc = null;
    @SerializedName("display_text_service_desc")
    @Expose
    private List<String> displayTextServiceDesc = null;
    @SerializedName("display_id_service_cost")
    @Expose
    private List<Integer> displayIdServiceCost = null;
    @SerializedName("display_text_service_name")
    @Expose
    private List<String> displayTextServiceName = null;
    @SerializedName("_version_")
    @Expose
    private long version;
    @SerializedName("is_on")
    @Expose
    private boolean isOn;
    @SerializedName("is_featured")
    @Expose
    private boolean isFeatured;
    @SerializedName("author_participant_type")
    @Expose
    private String authorParticipantType;
    @SerializedName("is_author_confidential")
    @Expose
    private boolean isAuthorConfidential;


    public int getCreatedByL() {
        return createdByL;
    }

    public void setCreatedByL(int createdByL) {
        this.createdByL = createdByL;
    }

    public List<String> getDisplayTextEndMinute() {
        return displayTextEndMinute;
    }

    public void setDisplayTextEndMinute(List<String> displayTextEndMinute) {
        this.displayTextEndMinute = displayTextEndMinute;
    }

    public int getEntityOrParticipantTypeIdI() {
        return entityOrParticipantTypeIdI;
    }

    public void setEntityOrParticipantTypeIdI(int entityOrParticipantTypeIdI) {
        this.entityOrParticipantTypeIdI = entityOrParticipantTypeIdI;
    }

    public String getPostingDateOnlyDt() {
        return postingDateOnlyDt;
    }

    public void setPostingDateOnlyDt(String postingDateOnlyDt) {
        this.postingDateOnlyDt = postingDateOnlyDt;
    }

    public String getpLastModifiedOn() {
        return pLastModifiedOn;
    }

    public void setpLastModifiedOn(String pLastModifiedOn) {
        this.pLastModifiedOn = pLastModifiedOn;
    }

    public int getIdOfEntityOrParticipant() {
        return idOfEntityOrParticipant;
    }

    public void setIdOfEntityOrParticipant(int idOfEntityOrParticipant) {
        this.idOfEntityOrParticipant = idOfEntityOrParticipant;
    }

    public String getCityNameS() {
        return cityNameS;
    }

    public void setCityNameS(String cityNameS) {
        this.cityNameS = cityNameS;
    }

    public List<String> getSimilarContents() {
        return similarContents;
    }

    public void setSimilarContents(List<String> similarContents) {
        this.similarContents = similarContents;
    }

    public boolean ispIsActive() {
        return pIsActive;
    }

    public void setpIsActive(boolean pIsActive) {
        this.pIsActive = pIsActive;
    }

    public List<String> getDisplayTextStartMinute() {
        return displayTextStartMinute;
    }

    public void setDisplayTextStartMinute(List<String> displayTextStartMinute) {
        this.displayTextStartMinute = displayTextStartMinute;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDisplayTextEntityName() {
        return displayTextEntityName;
    }

    public void setDisplayTextEntityName(List<String> displayTextEntityName) {
        this.displayTextEntityName = displayTextEntityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndDateDt() {
        return endDateDt;
    }

    public void setEndDateDt(String endDateDt) {
        this.endDateDt = endDateDt;
    }

    public String getPostingDateDt() {
        return postingDateDt;
    }

    public void setPostingDateDt(String postingDateDt) {
        this.postingDateDt = postingDateDt;
    }

    public List<Integer> getDisplayIdEventId() {
        return displayIdEventId;
    }

    public void setDisplayIdEventId(List<Integer> displayIdEventId) {
        this.displayIdEventId = displayIdEventId;
    }

    public int getEntityOrParticipantId() {
        return entityOrParticipantId;
    }

    public void setEntityOrParticipantId(int entityOrParticipantId) {
        this.entityOrParticipantId = entityOrParticipantId;
    }

    public List<String> getDisplayTextEndHour() {
        return displayTextEndHour;
    }

    public void setDisplayTextEndHour(List<String> displayTextEndHour) {
        this.displayTextEndHour = displayTextEndHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStartDateDt() {
        return startDateDt;
    }

    public void setStartDateDt(String startDateDt) {
        this.startDateDt = startDateDt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public List<String> getDisplayTextStartHour() {
        return displayTextStartHour;
    }

    public void setDisplayTextStartHour(List<String> displayTextStartHour) {
        this.displayTextStartHour = displayTextStartHour;
    }

    public String getpCrdt() {
        return pCrdt;
    }

    public void setpCrdt(String pCrdt) {
        this.pCrdt = pCrdt;
    }

    public List<String> getDisplayTextSpeakerName() {
        return displayTextSpeakerName;
    }

    public void setDisplayTextSpeakerName(List<String> displayTextSpeakerName) {
        this.displayTextSpeakerName = displayTextSpeakerName;
    }

    public List<Integer> getDisplayIdSpeakerId() {
        return displayIdSpeakerId;
    }

    public void setDisplayIdSpeakerId(List<Integer> displayIdSpeakerId) {
        this.displayIdSpeakerId = displayIdSpeakerId;
    }

    public List<String> getDisplayTextSpeakerImageUrl() {
        return displayTextSpeakerImageUrl;
    }

    public void setDisplayTextSpeakerImageUrl(List<String> displayTextSpeakerImageUrl) {
        this.displayTextSpeakerImageUrl = displayTextSpeakerImageUrl;
    }

    public List<String> getDisplayTextSpeakerDesc() {
        return displayTextSpeakerDesc;
    }

    public void setDisplayTextSpeakerDesc(List<String> displayTextSpeakerDesc) {
        this.displayTextSpeakerDesc = displayTextSpeakerDesc;
    }

    public List<String> getDisplayTextSpeakerDesignation() {
        return displayTextSpeakerDesignation;
    }

    public void setDisplayTextSpeakerDesignation(List<String> displayTextSpeakerDesignation) {
        this.displayTextSpeakerDesignation = displayTextSpeakerDesignation;
    }

    public List<Integer> getDisplayIdSponserId() {
        return displayIdSponserId;
    }

    public void setDisplayIdSponserId(List<Integer> displayIdSponserId) {
        this.displayIdSponserId = displayIdSponserId;
    }

    public List<String> getDisplayTextSponserUrl() {
        return displayTextSponserUrl;
    }

    public void setDisplayTextSponserUrl(List<String> displayTextSponserUrl) {
        this.displayTextSponserUrl = displayTextSponserUrl;
    }

    public List<String> getDisplayTextSponserName() {
        return displayTextSponserName;
    }

    public void setDisplayTextSponserName(List<String> displayTextSponserName) {
        this.displayTextSponserName = displayTextSponserName;
    }

    public List<String> getDisplayTextSponserLogoUrl() {
        return displayTextSponserLogoUrl;
    }

    public void setDisplayTextSponserLogoUrl(List<String> displayTextSponserLogoUrl) {
        this.displayTextSponserLogoUrl = displayTextSponserLogoUrl;
    }

    public List<Integer> getDisplayIdPartnerId() {
        return displayIdPartnerId;
    }

    public void setDisplayIdPartnerId(List<Integer> displayIdPartnerId) {
        this.displayIdPartnerId = displayIdPartnerId;
    }

    public List<String> getDisplayTextPartnerDesc() {
        return displayTextPartnerDesc;
    }

    public void setDisplayTextPartnerDesc(List<String> displayTextPartnerDesc) {
        this.displayTextPartnerDesc = displayTextPartnerDesc;
    }

    public List<String> getDisplayTextPartnerImageUrl() {
        return displayTextPartnerImageUrl;
    }

    public void setDisplayTextPartnerImageUrl(List<String> displayTextPartnerImageUrl) {
        this.displayTextPartnerImageUrl = displayTextPartnerImageUrl;
    }

    public List<String> getDisplayTextPartnerName() {
        return displayTextPartnerName;
    }

    public void setDisplayTextPartnerName(List<String> displayTextPartnerName) {
        this.displayTextPartnerName = displayTextPartnerName;
    }

    public List<Integer> getDisplayIdServiceId() {
        return displayIdServiceId;
    }

    public void setDisplayIdServiceId(List<Integer> displayIdServiceId) {
        this.displayIdServiceId = displayIdServiceId;
    }

    public List<String> getDisplayTextServiceAmountDesc() {
        return displayTextServiceAmountDesc;
    }

    public void setDisplayTextServiceAmountDesc(List<String> displayTextServiceAmountDesc) {
        this.displayTextServiceAmountDesc = displayTextServiceAmountDesc;
    }

    public List<String> getDisplayTextServiceDesc() {
        return displayTextServiceDesc;
    }

    public void setDisplayTextServiceDesc(List<String> displayTextServiceDesc) {
        this.displayTextServiceDesc = displayTextServiceDesc;
    }

    public List<Integer> getDisplayIdServiceCost() {
        return displayIdServiceCost;
    }

    public void setDisplayIdServiceCost(List<Integer> displayIdServiceCost) {
        this.displayIdServiceCost = displayIdServiceCost;
    }

    public List<String> getDisplayTextServiceName() {
        return displayTextServiceName;
    }

    public void setDisplayTextServiceName(List<String> displayTextServiceName) {
        this.displayTextServiceName = displayTextServiceName;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public String getAuthorParticipantType() {
        return authorParticipantType;
    }

    public void setAuthorParticipantType(String authorParticipantType) {
        this.authorParticipantType = authorParticipantType;
    }

    public boolean isAuthorConfidential() {
        return isAuthorConfidential;
    }

    public void setAuthorConfidential(boolean authorConfidential) {
        isAuthorConfidential = authorConfidential;
    }
}