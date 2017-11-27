package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.NewBaseResponse;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {NewFeedResponsePojo.class,NewBaseResponse.class})
public class NewFeedResponsePojo extends NewBaseResponse {
    @SerializedName("solr_ignore_featured_docs")
    @Expose
    private List<BaseEntityOrParticipantModel> featuredDocs;
    @SerializedName("docs")
    @Expose
    private List<BaseEntityOrParticipantModel> BaseEntityOrParticipantModels = null;

    public List<BaseEntityOrParticipantModel> getFeaturedDocs() {
        return featuredDocs;
    }

    public void setFeaturedDocs(List<BaseEntityOrParticipantModel> featuredDocs) {
        this.featuredDocs = featuredDocs;
    }

    public List<BaseEntityOrParticipantModel> getBaseEntityOrParticipantModels() {
        return BaseEntityOrParticipantModels;
    }

    public void setBaseEntityOrParticipantModels(List<BaseEntityOrParticipantModel> BaseEntityOrParticipantModels) {
        this.BaseEntityOrParticipantModels = BaseEntityOrParticipantModels;
    }
}
