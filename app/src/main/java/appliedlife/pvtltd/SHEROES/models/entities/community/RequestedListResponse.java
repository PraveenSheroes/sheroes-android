package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class RequestedListResponse {
    @SerializedName("data")
    @Expose
    private List<RequestedList> data = new ArrayList<RequestedList>();
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private int status;

    /**
     *
     * @return
     * The data
     */
    public List<RequestedList> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<RequestedList> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The status
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
