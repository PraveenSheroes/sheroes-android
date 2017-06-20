package appliedlife.pvtltd.SHEROES.models.entities.home;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 19-06-2017.
 */

public class EventSponsorData extends BaseResponse {
    String sponsorName;
    int sponsorId;
    String sponsorImageUrl;
    String sponsorDescription;
    String sponsorDesignation;

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorImageUrl() {
        return sponsorImageUrl;
    }

    public void setSponsorImageUrl(String sponsorImageUrl) {
        this.sponsorImageUrl = sponsorImageUrl;
    }

    public String getSponsorDescription() {
        return sponsorDescription;
    }

    public void setSponsorDescription(String sponsorDescription) {
        this.sponsorDescription = sponsorDescription;
    }

    public String getSponsorDesignation() {
        return sponsorDesignation;
    }

    public void setSponsorDesignation(String sponsorDesignation) {
        this.sponsorDesignation = sponsorDesignation;
    }
}
