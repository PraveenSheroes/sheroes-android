package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 09-05-2017.
 */

public class NotificationReadCountResponse extends BaseResponse {
        @SerializedName("unread_notification_count")
        @Expose
        int unread_notification_count;

    public int getUnread_notification_count() {
        return unread_notification_count;
    }

    public void setUnread_notification_count(int unread_notification_count) {
        this.unread_notification_count = unread_notification_count;
    }
}
