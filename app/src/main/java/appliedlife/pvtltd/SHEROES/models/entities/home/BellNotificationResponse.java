package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;


/**
 * Created by priyanka on 24/04/17.
 */
@Parcel(analyze = {BellNotificationResponse.class, BaseResponse.class})
public class BellNotificationResponse extends BaseResponse {
    @SerializedName("notification")
    private BellNotification notification;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("solr_ignore_author_or_entity_image_url")
    @Expose
    private String solrIgnoreAuthorOrEntityImageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public BellNotification getNotification() {
        return notification;
    }

    public void setNotification(BellNotification notification) {
        this.notification = notification;
    }
}
