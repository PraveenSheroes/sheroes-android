package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;

/**
 * Created by Ajit Kumar on 31-01-2017.
 */

public interface CommunityView extends BaseMvpView {
    void getityCommunityListSuccess(List<CommunityList> data);
    void getOwnerListSuccess(List<OwnerList> data);
    void showNwError();
}
