
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Praveen on 4/12/17.
 */
@Parcel(analyze = {CarouselDataObj.class,FeedDetail.class})
public class CarouselDataObj extends FeedDetail {

    @SerializedName("endPointUrl")
    private String endPointUrl;

    @SerializedName("screenTitle")
    private String screenTitle;

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("iconUrl")
    private String iconUrl;

    @SerializedName("solr_ignore_list_of_base_or_participant_model")
    @Expose
    private List<FeedDetail> feedDetails = null;

    public List<FeedDetail> getFeedDetails() {
        return feedDetails;
    }

    public void setFeedDetails(List<FeedDetail> feedDetails) {
        this.feedDetails = feedDetails;
    }

    public String getEndPointUrl() {
        return endPointUrl;
    }

    public void setEndPointUrl(String endPointUrl) {
        this.endPointUrl = endPointUrl;
    }

    public String getScreenTitle() {
        return screenTitle;
    }

    public void setScreenTitle(String screenTitle) {
        this.screenTitle = screenTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
