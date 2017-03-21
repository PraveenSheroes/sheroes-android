package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ajit Kumar on 19-03-2017.
 */

public class GetAllData {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fieldErrorMessageMap")
    @Expose
    private FieldErrorMessageMap fieldErrorMessageMap;
    @SerializedName("numFound")
    @Expose
    private Integer numFound;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;
    @SerializedName("screen_name")
    @Expose
    private Object screenName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FieldErrorMessageMap getFieldErrorMessageMap() {
        return fieldErrorMessageMap;
    }

    public void setFieldErrorMessageMap(FieldErrorMessageMap fieldErrorMessageMap) {
        this.fieldErrorMessageMap = fieldErrorMessageMap;
    }

    public Integer getNumFound() {
        return numFound;
    }

    public void setNumFound(Integer numFound) {
        this.numFound = numFound;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public Object getScreenName() {
        return screenName;
    }

    public void setScreenName(Object screenName) {
        this.screenName = screenName;
    }

}
