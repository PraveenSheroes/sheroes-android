package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class ArticleListResponse {

    @SerializedName("data")
    @Expose
    private List<ArticleRequest> data = new ArrayList<ArticleRequest>();
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private int status;

    /**`
     *
     * @return
     * The data
     */
    public List<ArticleRequest> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<ArticleRequest> data) {
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