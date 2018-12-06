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

    @SerializedName("set_order_key")
    private String setOrderKey;

    @SerializedName("search_text")
    private String searchText;

    @SerializedName("server_feed_config_version")
    private Integer serverFeedConfigVersion;

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

    public String getSetOrderKey() {
        return setOrderKey;
    }

    public void setSetOrderKey(String setOrderKey) {
        this.setOrderKey = setOrderKey;
    }

    public Integer getServerFeedConfigVersion() {
        return serverFeedConfigVersion;
    }

    public void setServerFeedConfigVersion(Integer serverFeedConfigVersion) {
        this.serverFeedConfigVersion = serverFeedConfigVersion;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
