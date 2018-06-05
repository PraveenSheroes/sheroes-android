package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import android.support.annotation.StringRes;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;

/**
 * Created by ravi on 03/01/18.
 */

public interface IEditProfileView extends BaseMvpView {

    void showError(@StringRes int error);

    void errorMessage(String message);

    void getUserData(UserProfileResponse userProfileResponse);

    void getUserSummaryResponse(BoardingDataResponse boardingDataResponse);

    void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse);


}
