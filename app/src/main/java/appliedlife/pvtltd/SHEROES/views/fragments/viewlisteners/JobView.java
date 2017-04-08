package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 11-03-2017.
 */

public interface JobView extends BaseMvpView {
    void getJobApplySuccess(BaseResponse baseResponse);
}
