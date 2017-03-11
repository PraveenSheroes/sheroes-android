package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingDeActivateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingFeedbackResponce;
import appliedlife.pvtltd.SHEROES.models.entities.setting.SettingRatingResponse;
import appliedlife.pvtltd.SHEROES.models.entities.setting.UserpreferenseResponse;

/**
 * Created by sheroes on 24/02/17.
 */

public interface SettingFeedbackView extends BaseMvpView {

    void getFeedbackResponse(SettingFeedbackResponce feedbackResponce);

    void getUserRatingResponse(SettingRatingResponse ratingResponse);

    void getUserDeactiveResponse(SettingDeActivateResponse deActivateResponse);

    void getUserPreferenceResponse(UserpreferenseResponse userpreferenseResponse);

    void showNwError();






}
