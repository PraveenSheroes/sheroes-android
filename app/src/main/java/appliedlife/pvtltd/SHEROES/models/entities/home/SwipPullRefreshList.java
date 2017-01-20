package appliedlife.pvtltd.SHEROES.models.entities.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public class SwipPullRefreshList {
    @SerializedName("cityListDatas")
    @Expose
    private List<CityListData> cityListDatas = new ArrayList<CityListData>();
    @SerializedName("isPullToRefresh")
    @Expose
    private boolean isPullToRefresh;
    public List<CityListData> getCityListDatas() {
        return cityListDatas;
    }

    public void setCityListDatas(List<CityListData> cityListDatas) {
        this.cityListDatas = cityListDatas;
    }

    public void allListData(List<CityListData> cityListDatas) {
        this.cityListDatas.addAll(cityListDatas);
    }

    public boolean isPullToRefresh() {
        return isPullToRefresh;
    }

    public void setPullToRefresh(boolean pullToRefresh) {
        isPullToRefresh = pullToRefresh;
    }
}
