package appliedlife.pvtltd.SHEROES.models.entities.home;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 17-06-2017.
 */

public class EventSpeakerData extends BaseResponse {
    String speakerName;
    int speakerId;
    String speakerImageUrl;
    String speakerDescription;
    String speakerDesignation;

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public int getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(int speakerId) {
        this.speakerId = speakerId;
    }

    public String getSpeakerImageUrl() {
        return speakerImageUrl;
    }

    public void setSpeakerImageUrl(String speakerImageUrl) {
        this.speakerImageUrl = speakerImageUrl;
    }

    public String getSpeakerDescription() {
        return speakerDescription;
    }

    public void setSpeakerDescription(String speakerDescription) {
        this.speakerDescription = speakerDescription;
    }

    public String getSpeakerDesignation() {
        return speakerDesignation;
    }

    public void setSpeakerDesignation(String speakerDesignation) {
        this.speakerDesignation = speakerDesignation;
    }
}
