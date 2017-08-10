package appliedlife.pvtltd.SHEROES.models.entities.publicprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 03-08-2017.
 */

public class PublicProfileListResponse extends BaseResponse {
    @SerializedName("mentor_details_list")
    @Expose
    private List<MentorDetailItem> mentorDetailItems = null;

    public List<MentorDetailItem> getMentorDetailItems() {
        return mentorDetailItems;
    }

    public void setMentorDetailItems(List<MentorDetailItem> mentorDetailItems) {
        this.mentorDetailItems = mentorDetailItems;
    }


}
