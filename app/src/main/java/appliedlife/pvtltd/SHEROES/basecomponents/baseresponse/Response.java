package appliedlife.pvtltd.SHEROES.basecomponents.baseresponse;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response<T> {
    @SerializedName("listOfMaster")
    @Expose
    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
