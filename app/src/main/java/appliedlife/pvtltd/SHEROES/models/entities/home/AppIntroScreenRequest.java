package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by Praveen_Singh on 26-07-2017.
 */

public class AppIntroScreenRequest extends BaseRequest {
    @SerializedName("sheroes_page_id")
    @Expose
    private Integer sheroesPageId= AppConstants.APP_INTRO;

    public Integer getSheroesPageId() {
        return sheroesPageId;
    }

    public void setSheroesPageId(Integer sheroesPageId) {
        this.sheroesPageId = sheroesPageId;
    }
}

