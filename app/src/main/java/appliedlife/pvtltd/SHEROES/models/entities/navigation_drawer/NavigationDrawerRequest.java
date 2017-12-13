package appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ravi on 28/11/17.
 * Request class for navigation drawer items
 */

public class NavigationDrawerRequest extends BaseRequest {

    @SerializedName("display_default_on_failure")
    @Expose
    private boolean displayDefault;

    public boolean isDisplayDefault() {
        return displayDefault;
    }

    public void setDisplayDefault(boolean displayDefault) {
        this.displayDefault = displayDefault;
    }

}
