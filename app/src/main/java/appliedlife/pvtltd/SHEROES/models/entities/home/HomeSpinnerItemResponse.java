
package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeSpinnerItemResponse {

    @SerializedName("HomeSpinnerItem")
    @Expose
    private List<HomeSpinnerItem> homeSpinnerItem = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<HomeSpinnerItem> getHomeSpinnerItem() {
        return homeSpinnerItem;
    }

    public void setHomeSpinnerItem(List<HomeSpinnerItem> homeSpinnerItem) {
        this.homeSpinnerItem = homeSpinnerItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
