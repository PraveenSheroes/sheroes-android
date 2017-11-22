package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import android.support.annotation.StringRes;


import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;

/**
 * Created by ujjwal on 28/04/17.
 */

public interface IContestListView extends BaseMvpView {
    void showProgressBar();

    void hideProgressBar();

    void showError(@StringRes int error);

    void showContests(List<Contest> contests);
}
