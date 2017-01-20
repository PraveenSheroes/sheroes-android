package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.home.CityListData;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;

public interface HomeView extends BaseMvpView {
    void getCityListSuccess(List<CityListData> data);
    void getHomeSpinnerListSuccess(List<HomeSpinnerItem> data);
    void showNwError();
}
