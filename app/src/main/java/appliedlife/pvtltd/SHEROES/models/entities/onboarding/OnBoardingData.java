package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 22-02-2017.
 */

public class OnBoardingData extends BaseResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    private List<LabelValue> boardingDataList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LabelValue> getBoardingDataList() {
        return boardingDataList;
    }

    public void setBoardingDataList(List<LabelValue> boardingDataList) {
        this.boardingDataList = boardingDataList;
    }
}
