
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class FeedResponsePojo extends BaseResponse implements Parcelable {

    @SerializedName("solr_ignore_featured_docs")
    @Expose
    private List<FeedDetail> featuredDocs;
    @SerializedName("docs")
    @Expose
    private List<FeedDetail> feedDetails = null;

    public List<FeedDetail> getFeedDetails() {
        return feedDetails;
    }

    public void setFeedDetails(List<FeedDetail> feedDetails) {
        this.feedDetails = feedDetails;
    }


    public FeedResponsePojo() {
    }

    public List<FeedDetail> getFeaturedDocs() {
        return featuredDocs;
    }

    public void setFeaturedDocs(List<FeedDetail> featuredDocs) {
        this.featuredDocs = featuredDocs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.featuredDocs);
        dest.writeTypedList(this.feedDetails);
    }

    protected FeedResponsePojo(Parcel in) {
        this.featuredDocs = in.createTypedArrayList(FeedDetail.CREATOR);
        this.feedDetails = in.createTypedArrayList(FeedDetail.CREATOR);
    }

    public static final Creator<FeedResponsePojo> CREATOR = new Creator<FeedResponsePojo>() {
        @Override
        public FeedResponsePojo createFromParcel(Parcel source) {
            return new FeedResponsePojo(source);
        }

        @Override
        public FeedResponsePojo[] newArray(int size) {
            return new FeedResponsePojo[size];
        }
    };
}
