package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by ravi on 01/01/18.
 */

@Parcel(analyze = {UserFollowedMentorsResponse.class,BaseResponse.class})
public class UserFollowedMentorsResponse extends BaseResponse {
    @SerializedName("solr_ignore_featured_docs")
    @Expose
    private List<UserSolrObj> featuredDocs;
    @SerializedName("docs")
    @Expose
    private List<UserSolrObj> feedDetails = null;

    public List<UserSolrObj> getFeaturedDocs() {
        return featuredDocs;
    }

    public void setFeaturedDocs(List<UserSolrObj> featuredDocs) {
        this.featuredDocs = featuredDocs;
    }

    public List<UserSolrObj> getFeedDetails() {
        return feedDetails;
    }

    public void setFeedDetails(List<UserSolrObj> feedDetails) {
        this.feedDetails = feedDetails;
    }
}