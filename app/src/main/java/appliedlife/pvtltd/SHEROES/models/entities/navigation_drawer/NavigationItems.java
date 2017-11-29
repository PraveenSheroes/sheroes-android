
package appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class NavigationItems extends BaseResponse {

    @SerializedName("menu_items")
    @Expose
    private List<NavMenuItem> menuItems = null;

    public List<NavMenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<NavMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

}
