package appliedlife.pvtltd.SHEROES.datamanager;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class to pass impression to backend
 */
public class UserEvents {

    @SerializedName("userEvents")
    private List<ImpressionData> userEvent = null;

    public List<ImpressionData> getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(List<ImpressionData> userEvent) {
        this.userEvent = userEvent;
    }
}
