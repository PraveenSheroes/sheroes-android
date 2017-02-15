
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class FeedResponsePojo extends BaseResponse implements Parcelable {

    @SerializedName("numFound")
    @Expose
    private int numFound;
    @SerializedName("start")
    @Expose
    private int start;
    @SerializedName("docs")
    @Expose
    private List<FeedDetail> feedDetails = null;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<FeedDetail> getFeedDetails() {
        return feedDetails;
    }

    public void setFeedDetails(List<FeedDetail> feedDetails) {
        this.feedDetails = feedDetails;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.numFound);
        dest.writeInt(this.start);
        dest.writeTypedList(this.feedDetails);
    }

    public FeedResponsePojo() {
    }

    protected FeedResponsePojo(Parcel in) {
        this.numFound = in.readInt();
        this.start = in.readInt();
        this.feedDetails = in.createTypedArrayList(FeedDetail.CREATOR);
    }

    public static final Parcelable.Creator<FeedResponsePojo> CREATOR = new Parcelable.Creator<FeedResponsePojo>() {
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
