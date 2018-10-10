package appliedlife.pvtltd.SHEROES.datamanger;

import java.util.List;

public class UserEvents {

    private List<ImpressionDataSample> userEvent = null;

    public List<ImpressionDataSample> getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(List<ImpressionDataSample> userEvent) {
        this.userEvent = userEvent;
    }
}
