package appliedlife.pvtltd.SHEROES.models.entities.searchmodule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class SearchResponse extends BaseResponse {

    @SerializedName("count")
    @Expose
    private int count;


}