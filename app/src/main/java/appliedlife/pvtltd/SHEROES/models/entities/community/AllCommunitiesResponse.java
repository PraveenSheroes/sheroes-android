package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by ujjwal on 02/11/17.
 */

public class AllCommunitiesResponse extends BaseResponse implements Parcelable {
    @SerializedName("docs")
    @Expose
    public List<FeedDetail> feedDetails = null;


    protected AllCommunitiesResponse(Parcel in) {
        this.feedDetails = in.createTypedArrayList(FeedDetail.CREATOR);
    }

    public static final Creator<AllCommunitiesResponse> CREATOR = new Creator<AllCommunitiesResponse>() {
        @Override
        public AllCommunitiesResponse createFromParcel(Parcel source) {
            return new AllCommunitiesResponse(source);
        }
        @Override
        public AllCommunitiesResponse[] newArray(int size) {
            return new AllCommunitiesResponse[size];
        }
    };
}
