package appliedlife.pvtltd.SHEROES.basecomponents;

import java.util.ArrayList;
import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base class for all common methods in the views and presenter.
 * this interface will be implemented in all child classes.
 */
public interface BaseMvpView {
    void startProgressBar();
    void stopProgressBar();
    void startNextScreen();
    void showError(String s, FeedParticipationEnum feedParticipationEnum);
    void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult);
}
