package appliedlife.pvtltd.SHEROES.models.entities.feed;

import java.util.List;

import appliedlife.pvtltd.SHEROES.datamanager.ImpressionData;

public class UserEventsContainer {

    //@TypeConverters(RoomJsonConverter.class)
    //@SerializedName("userEvents")
    //@Expose
    private List<ImpressionData> userEvent = null;

    public List<ImpressionData> getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(List<ImpressionData> userEvent) {
        this.userEvent = userEvent;
    }

}
