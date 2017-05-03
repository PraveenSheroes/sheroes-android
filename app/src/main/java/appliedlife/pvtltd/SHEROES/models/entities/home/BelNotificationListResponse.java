package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 27-04-2017.
 */

public class BelNotificationListResponse extends BaseResponse{
    @SerializedName("solr_ignore_notification_responses")
    @Expose
    private List<BellNotificationResponse> bellNotificationResponses;

    public List<BellNotificationResponse> getBellNotificationResponses() {
        return bellNotificationResponses;
    }

    public void setBellNotificationResponses(List<BellNotificationResponse> bellNotificationResponses) {
        this.bellNotificationResponses = bellNotificationResponses;
    }

    public BelNotificationListResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.bellNotificationResponses);
    }

    protected BelNotificationListResponse(Parcel in) {
        super(in);
        this.bellNotificationResponses = in.createTypedArrayList(BellNotificationResponse.CREATOR);
    }

    public static final Creator<BelNotificationListResponse> CREATOR = new Creator<BelNotificationListResponse>() {
        @Override
        public BelNotificationListResponse createFromParcel(Parcel source) {
            return new BelNotificationListResponse(source);
        }

        @Override
        public BelNotificationListResponse[] newArray(int size) {
            return new BelNotificationListResponse[size];
        }
    };
}
