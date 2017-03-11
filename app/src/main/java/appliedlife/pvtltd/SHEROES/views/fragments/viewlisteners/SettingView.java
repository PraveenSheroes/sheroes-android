package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Segments;

/**
 * Created by sheroes on 31/01/17.
 */

public interface SettingView extends BaseMvpView {

    void showNwError();

    void backListener(int id, Segments segments);




}
