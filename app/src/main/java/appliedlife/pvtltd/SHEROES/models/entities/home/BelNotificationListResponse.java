package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 27-04-2017.
 */

@Parcel(analyze = {BelNotificationListResponse.class,BaseResponse.class})
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
