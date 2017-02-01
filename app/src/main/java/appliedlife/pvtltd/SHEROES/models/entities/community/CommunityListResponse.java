package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHEROES-TECH on 23-01-2017.
 */

public class CommunityListResponse {
    @SerializedName("data")
    @Expose
    private List<CommunityList> data = new ArrayList<CommunityList>();
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
    public List<CommunityList> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<CommunityList> data) {
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
