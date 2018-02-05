
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

    @SerializedName("solr_ignore_list_of_base_or_participant_model")
    @Expose
    private List<FeedDetail> feedDetails = null;

    public List<FeedDetail> getFeedDetails() {
        return feedDetails;
    }

    public void setFeedDetails(List<FeedDetail> feedDetails) {
        this.feedDetails = feedDetails;
    }

}
