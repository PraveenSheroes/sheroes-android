package appliedlife.pvtltd.SHEROES.models.entities.feed;

import java.util.List;

import appliedlife.pvtltd.SHEROES.datamanger.ImpressionDataSample;

public class UserEventsContainer {

    //@TypeConverters(RoomJsonConverter.class)
    //@SerializedName("userEvents")
    //@Expose
    private List<ImpressionDataSample> userEvent = null;

    public List<ImpressionDataSample> getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(List<ImpressionDataSample> userEvent) {
        this.userEvent = userEvent;
    }

}
