package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;

/**
 * Created by ujjwal on 11/05/17.
 */

public interface IContestView extends BaseMvpView {
    void populateContest(Contest contest);

}
