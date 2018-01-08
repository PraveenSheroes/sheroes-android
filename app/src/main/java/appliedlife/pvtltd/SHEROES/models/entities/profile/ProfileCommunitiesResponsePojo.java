package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;
import java.util.List;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;

/**
 * Created by Ravi on 07/01/18.
 */
@Parcel(analyze = {ProfileCommunitiesResponsePojo.class,BaseResponse.class})
public class ProfileCommunitiesResponsePojo extends BaseResponse {

    @SerializedName("solr_ignore_featured_docs")
    @Expose
    private List<CommunityFeedSolrObj> mutualCommunities;

    @SerializedName("docs")
    @Expose
    private List<CommunityFeedSolrObj> otherCommunities = null;

    public List<CommunityFeedSolrObj> getMutualCommunities() {
        return mutualCommunities;
    }

    public void setMutualCommunities(List<CommunityFeedSolrObj> mutualCommunities) {
        this.mutualCommunities = mutualCommunities;
    }

    public List<CommunityFeedSolrObj> getOtherCommunities() {
        return otherCommunities;
    }

    public void setOtherCommunities(List<CommunityFeedSolrObj> otherCommunities) {
        this.otherCommunities = otherCommunities;
    }

}
