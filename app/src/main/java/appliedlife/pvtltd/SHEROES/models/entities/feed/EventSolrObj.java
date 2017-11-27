package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {EventSolrObj.class,BaseEntityOrParticipantModel.class})
public class EventSolrObj extends BaseEntityOrParticipantModel {
    @SerializedName(value = "start_date_dt")
    private Date startDate;

    @SerializedName(value = "end_date_dt")
    private Date endDate;

    @SerializedName(value = "s_disp_start_hour")
    private String startHour;

    @SerializedName(value = "s_disp_start_minute")
    private String startMinute;

    @SerializedName(value = "s_disp_end_hour")
    private String endHour;

    @SerializedName(value = "s_disp_end_minute")
    private String endMinute;

    @SerializedName(value = "display_text_speaker_name")
    private List<String> speakerName;

    @SerializedName(value = "display_text_speaker_designation")
    private List<String> speakerDesignation;

    @SerializedName(value = "display_text_speaker_desc")
    private List<String> speakerDesc;

    @SerializedName(value = "display_text_speaker_image_url")
    private List<String> speakerImageUrl;

    @SerializedName(value = "display_id_sponser_id")
    private List<Long> sponserId;

    @SerializedName(value = "display_text_sponser_name")
    private List<String> sponserName;

    @SerializedName(value = "display_text_sponser_url")
    private List<String> sponserUrl;

    @SerializedName(value = "display_text_sponser_logo_url")
    private List<String> sponserLogoUrl;

    @SerializedName(value = "display_text_partner_name")
    private List<String> partnerName;

    @SerializedName(value = "display_text_partner_image_url")
    private List<String> partnerImageUrl;

    @SerializedName(value = "display_text_partner_website_url")
    private List<String> partnerWebsiteUrl;

    @SerializedName(value = "display_text_partner_desc")
    private List<String> partnerDesc;

    @SerializedName(value = "display_text_program_title")
    private List<String> programTitle;

    @SerializedName(value = "display_text_program_desc")
    private List<String> programDesc;

    @SerializedName(value = "display_id_service_cost")
    private List<String> serviceCost;

    @SerializedName(value = "display_text_service_amount_desc")
    private List<String> serviceAmountDesc;

    @SerializedName(value = "display_text_service_desc")
    private List<String> serviceDesc;

    @SerializedName(value = "display_text_service_name")
    private List<String> serviceName;

    @SerializedName(value = "s_disp_event_venue")
    private String venue;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public List<String> getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(List<String> speakerName) {
        this.speakerName = speakerName;
    }

    public List<String> getSpeakerDesignation() {
        return speakerDesignation;
    }

    public void setSpeakerDesignation(List<String> speakerDesignation) {
        this.speakerDesignation = speakerDesignation;
    }

    public List<String> getSpeakerDesc() {
        return speakerDesc;
    }

    public void setSpeakerDesc(List<String> speakerDesc) {
        this.speakerDesc = speakerDesc;
    }

    public List<String> getSpeakerImageUrl() {
        return speakerImageUrl;
    }

    public void setSpeakerImageUrl(List<String> speakerImageUrl) {
        this.speakerImageUrl = speakerImageUrl;
    }

    public List<Long> getSponserId() {
        return sponserId;
    }

    public void setSponserId(List<Long> sponserId) {
        this.sponserId = sponserId;
    }

    public List<String> getSponserName() {
        return sponserName;
    }

    public void setSponserName(List<String> sponserName) {
        this.sponserName = sponserName;
    }

    public List<String> getSponserUrl() {
        return sponserUrl;
    }

    public void setSponserUrl(List<String> sponserUrl) {
        this.sponserUrl = sponserUrl;
    }

    public List<String> getSponserLogoUrl() {
        return sponserLogoUrl;
    }

    public void setSponserLogoUrl(List<String> sponserLogoUrl) {
        this.sponserLogoUrl = sponserLogoUrl;
    }

    public List<String> getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(List<String> partnerName) {
        this.partnerName = partnerName;
    }

    public List<String> getPartnerImageUrl() {
        return partnerImageUrl;
    }

    public void setPartnerImageUrl(List<String> partnerImageUrl) {
        this.partnerImageUrl = partnerImageUrl;
    }

    public List<String> getPartnerWebsiteUrl() {
        return partnerWebsiteUrl;
    }

    public void setPartnerWebsiteUrl(List<String> partnerWebsiteUrl) {
        this.partnerWebsiteUrl = partnerWebsiteUrl;
    }

    public List<String> getPartnerDesc() {
        return partnerDesc;
    }

    public void setPartnerDesc(List<String> partnerDesc) {
        this.partnerDesc = partnerDesc;
    }

    public List<String> getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(List<String> programTitle) {
        this.programTitle = programTitle;
    }

    public List<String> getProgramDesc() {
        return programDesc;
    }

    public void setProgramDesc(List<String> programDesc) {
        this.programDesc = programDesc;
    }

    public List<String> getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(List<String> serviceCost) {
        this.serviceCost = serviceCost;
    }

    public List<String> getServiceAmountDesc() {
        return serviceAmountDesc;
    }

    public void setServiceAmountDesc(List<String> serviceAmountDesc) {
        this.serviceAmountDesc = serviceAmountDesc;
    }

    public List<String> getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(List<String> serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public List<String> getServiceName() {
        return serviceName;
    }

    public void setServiceName(List<String> serviceName) {
        this.serviceName = serviceName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
