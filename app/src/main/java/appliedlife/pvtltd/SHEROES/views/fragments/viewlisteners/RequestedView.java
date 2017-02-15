package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedList;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public interface RequestedView extends BaseMvpView {
    void getAllRequest(List<RequestedList> data);
    void showNwError();
}