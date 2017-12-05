package appliedlife.pvtltd.SHEROES.models.entities.publicprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.WorkExpListResponse;

/**
 * Created by Praveen_Singh on 03-08-2017.
 */
@Parcel(analyze = {MentorDetailItem.class, BaseResponse.class})
public class MentorDetailItem extends BaseResponse {
    private int itemPosition;
    @SerializedName("mentor_name")
    @Expose
    private String mentorName;
    @SerializedName("experties_in")
    @Expose
    private String expertiesIn;
    @SerializedName("is_followed")
    @Expose
    private boolean isFollowed;
    @SerializedName("entity_or_participant_id")
    @Expose
    private long entityOrParticipantId;
    @SerializedName("mentor_image_url")
    @Expose
    private String mentorImageUrl;
    @SerializedName("solr_ignor_deep_link_url")
    @Expose
    private String solarIgnoreDeepLink;


    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getExpertiesIn() {
        return expertiesIn;
    }

    public void setExpertiesIn(String expertiesIn) {
        this.expertiesIn = expertiesIn;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setFollowed(boolean followed) {
        isFollowed = followed;
    }


    public String getMentorImageUrl() {
        return mentorImageUrl;
    }

    public void setMentorImageUrl(String mentorImageUrl) {
        this.mentorImageUrl = mentorImageUrl;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public MentorDetailItem() {
    }

    public long getEntityOrParticipantId() {
        return entityOrParticipantId;
    }

    public void setEntityOrParticipantId(long entityOrParticipantId) {
        this.entityOrParticipantId = entityOrParticipantId;
    }

    public String getSolarIgnoreDeepLink() {
        return solarIgnoreDeepLink;
    }

    public void setSolarIgnoreDeepLink(String solarIgnoreDeepLink) {
        this.solarIgnoreDeepLink = solarIgnoreDeepLink;
    }
}
