package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class SearchResponse extends BaseResponse {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("listOfSearch")
    @Expose
    private List<ListOfSearch> listOfFeed = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListOfSearch> getListOfFeed() {
        return listOfFeed;
    }

    public void setListOfFeed(List<ListOfSearch> listOfFeed) {
        this.listOfFeed = listOfFeed;
    }
}