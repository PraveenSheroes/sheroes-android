
package appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class NavMenuItem extends BaseResponse{

    @SerializedName("menu_id")
    @Expose
    private int menuId;
    @SerializedName("display_order")
    @Expose
    private int displayOrder;
    @SerializedName("menu_name")
    @Expose
    private String menuName;
    @SerializedName("menu_desc")
    @Expose
    private String menuDesc;
    @SerializedName("menu_url")
    @Expose
    private String menuUrl;
    @SerializedName("menu_item_icon_url")
    @Expose
    private String menuItemIconUrl;
    @SerializedName("is_native")
    @Expose
    private boolean isNative;
    @SerializedName("menu_type")
    @Expose
    private int menuType;

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public boolean isNative() {
        return isNative;
    }

    public void setNative(boolean aNative) {
        isNative = aNative;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(String menuDesc) {
        this.menuDesc = menuDesc;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuItemIconUrl() {
        return menuItemIconUrl;
    }

    public void setMenuItemIconUrl(String menuItemIconUrl) {
        this.menuItemIconUrl = menuItemIconUrl;
    }

}
