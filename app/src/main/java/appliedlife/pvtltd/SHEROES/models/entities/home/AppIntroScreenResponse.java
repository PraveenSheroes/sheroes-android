package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 26-07-2017.
 */

public class AppIntroScreenResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private List<AppIntroData> data = null;

    public List<AppIntroData> getData() {
        return data;
    }

    public void setData(List<AppIntroData> data) {
        this.data = data;
    }
}
