package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ListOfSearch;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class InviteSearchResponse {
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("listOfSearch")
    @Expose
    private List<ListOfInviteSearch> listOfFeed = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListOfInviteSearch> getListOfFeed() {
        return listOfFeed;
    }

    public void setListOfFeed(List<ListOfInviteSearch> listOfFeed) {
        this.listOfFeed = listOfFeed;
    }
}
