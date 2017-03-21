package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.setting.Section;

/**
 * Created by sheroes on 31/01/17.
 */

public interface SettingView extends BaseMvpView {

    void showNwError();

    void backListener(int id);

    void settingpreference(int id, List<Section> sections);




}
