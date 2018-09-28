package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserEventsContainer {

    @SerializedName("userEvents")
    @Expose
    private List<UserEvent> userEvent = null;

    public List<UserEvent> getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(List<UserEvent> userEvent) {
        this.userEvent = userEvent;
    }

}
