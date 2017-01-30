package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;

/**
 * Created by Praveen_Singh on 18-01-2017.
 */

public interface SearchModuleView extends BaseMvpView {
    void getFeedListSuccess(List<ListOfFeed> data);
    void showNwError();
}
