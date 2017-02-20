package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.ListOfInviteSearch;

/**
 * Created by SHEROES-TECH on 08-02-2017.
 */

public interface InviteSearchView extends BaseMvpView {
    void getSearchListSuccess(List<ListOfInviteSearch> listOfSearches);
    void showNwError();
}
