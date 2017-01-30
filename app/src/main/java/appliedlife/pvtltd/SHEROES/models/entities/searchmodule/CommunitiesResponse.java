package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 19-01-2017.
 */

public class CommunitiesResponse extends BaseResponse {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("featureCardResponses")
    @Expose
    private List<Feature> featureCardResponses = new ArrayList<Feature>();
    @SerializedName("myCommunitiesCardResponses")
    @Expose
    private List<MyCommunities> myCommunitiesCardResponses = new ArrayList<MyCommunities>();


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Feature> getFeatureCardResponses() {
        return featureCardResponses;
    }

    public void setFeatureCardResponses(List<Feature> featureCardResponses) {
        this.featureCardResponses = featureCardResponses;
    }

    public List<MyCommunities> getMyCommunitiesCardResponses() {
        return myCommunitiesCardResponses;
    }

    public void setMyCommunitiesCardResponses(List<MyCommunities> myCommunitiesCardResponses) {
        this.myCommunitiesCardResponses = myCommunitiesCardResponses;
    }
}