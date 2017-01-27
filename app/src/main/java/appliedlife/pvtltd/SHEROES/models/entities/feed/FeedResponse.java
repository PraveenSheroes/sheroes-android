
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class FeedResponse extends BaseResponse{

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("listOfFeed")
    @Expose
    private List<ListOfFeed> listOfFeed = null;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public List<ListOfFeed> getListOfFeed() {
        return listOfFeed;
    }

    public void setListOfFeed(List<ListOfFeed> listOfFeed) {
        this.listOfFeed = listOfFeed;
    }



}
