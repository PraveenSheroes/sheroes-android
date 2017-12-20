
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Praveen on 4/12/17.
 */
@Parcel(analyze = {MentorDataObj.class,FeedDetail.class})
public class MentorDataObj extends FeedDetail {

    @SerializedName("solr_ignore_list_of_base_or_participant_model")
    @Expose
    private List<UserSolrObj> mentorParticipantModel = null;

    public List<UserSolrObj> getMentorParticipantModel() {
        return mentorParticipantModel;
    }

    public void setMentorParticipantModel(List<UserSolrObj> mentorParticipantModel) {
        this.mentorParticipantModel = mentorParticipantModel;
    }

}
