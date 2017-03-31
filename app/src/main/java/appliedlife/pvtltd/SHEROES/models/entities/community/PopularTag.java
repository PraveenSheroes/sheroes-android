package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 24-03-2017.
 */



public class PopularTag extends BaseResponse {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    private List<String> tagDataList;
    private List<Integer> tagDataPostion;
    private List<String> boardingDataList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<String> getBoardingDataList() {
        return boardingDataList;
    }

    public void setBoardingDataList(List<String> boardingDataList) {
        this.boardingDataList = boardingDataList;
    }
    public List<String> getTagList() {
        return tagDataList;
    }

    public void setTagList(List<String> boardingDataList) {
        this.tagDataList = tagDataList;
    }
    public List<Integer> getTagPosition() {
        return tagDataPostion;
    }

    public void setTagPosition(List<Integer> tagDataPostion) {
        this.tagDataPostion = tagDataPostion;
    }
}
