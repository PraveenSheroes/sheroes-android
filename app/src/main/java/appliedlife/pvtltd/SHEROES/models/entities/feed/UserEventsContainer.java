package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.datamanger.ImpressionData;

public class UserEventsContainer {

    @SerializedName("userEvents")
    @Expose
    private List<ImpressionData> userEvent = null;

    public List<ImpressionData> getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(List<ImpressionData> userEvent) {
        this.userEvent = userEvent;
    }

}
