package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationResponse;

/**
 * Created by sheroes on 07/03/17.
 */

public interface ProfileView extends BaseMvpView {
    void backListener(int id);
    void callFragment(int id);
    void getEducationResponse(EducationResponse educationResponse);


}
