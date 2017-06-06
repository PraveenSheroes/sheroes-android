package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;

/**
 * Created by Praveen_Singh on 04-01-2017.
 */

public interface LoginView extends BaseMvpView {
    void getLogInResponse(LoginResponse loginResponse);
    void getGoogleExpireInResponse(ExpireInResponse expireInResponse);
}