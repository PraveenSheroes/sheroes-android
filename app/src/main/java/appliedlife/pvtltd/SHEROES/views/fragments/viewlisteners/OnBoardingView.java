package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.ArrayList;
import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;

/**
 * Created by Praveen_Singh on 19-03-2017.
 */

public interface OnBoardingView extends BaseMvpView {
    void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult);
}

