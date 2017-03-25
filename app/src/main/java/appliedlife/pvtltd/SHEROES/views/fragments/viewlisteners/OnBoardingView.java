package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;

/**
 * Created by Praveen_Singh on 19-03-2017.
 */

public interface OnBoardingView extends BaseMvpView {
    void getAllDataResponse(GetAllData getAllData);
}

