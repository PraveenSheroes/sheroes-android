package appliedlife.pvtltd.SHEROES.models.entities.home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;

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
}
