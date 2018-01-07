package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import java.util.List;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ravi on 07/01/18.
 */
@Parcel(analyze = {ProfileCommunitiesResponsePojo.class,BaseResponse.class})
public class ProfileCommunitiesResponsePojo extends BaseResponse {

    @SerializedName("solr_ignore_featured_docs")
    @Expose
    private List<ProfileCommunity> mutualCommunities;

    @SerializedName("docs")
    @Expose
    private List<ProfileCommunity> otherCommunities = null;

    public List<ProfileCommunity> getMutualCommunities() {
        return mutualCommunities;
    }

    public void setMutualCommunities(List<ProfileCommunity> mutualCommunities) {
        this.mutualCommunities = mutualCommunities;
    }

    public List<ProfileCommunity> getOtherCommunities() {
        return otherCommunities;
    }

    public void setOtherCommunities(List<ProfileCommunity> otherCommunities) {
        this.otherCommunities = otherCommunities;
    }

}
