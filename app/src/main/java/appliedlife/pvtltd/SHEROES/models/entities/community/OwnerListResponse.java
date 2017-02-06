package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHEROES-TECH on 03-02-2017.
 */

public class OwnerListResponse {

    @SerializedName("data")
    @Expose
    private List<OwnerList> data = new ArrayList<OwnerList>();
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
    public List<OwnerList> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<OwnerList> data) {
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

