package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {FeedResponsePojo.class,BaseResponse.class})
public class FeedResponsePojo extends BaseResponse {
    @SerializedName("solr_ignore_featured_docs")
    @Expose
    private List<FeedDetail> featuredDocs;

    @SerializedName("docs")
    @Expose
    private List<FeedDetail> feedDetails = null;

    @SerializedName("next_token")
    private String nextToken;

    public List<FeedDetail> getFeaturedDocs() {
        return featuredDocs;
    }

    public void setFeaturedDocs(List<FeedDetail> featuredDocs) {
        this.featuredDocs = featuredDocs;
    }

    public List<FeedDetail> getFeedDetails() {
        return feedDetails;
    }

    public void setFeedDetails(List<FeedDetail> feedDetails) {
        this.feedDetails = feedDetails;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }
}
