package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;

/**
 * Created by Ajit Kumar on 11-01-2017.
 */

public interface CreateCommunityView extends BaseMvpView {
    void getCreateCommunityResponse(LoginResponse loginResponse);
    void dialogValue(String dilogval);

}