package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerList;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;

/**
 * Created by Ajit Kumar on 31-01-2017.
 */

public interface CommunityView extends BaseMvpView {
    void getityCommunityListSuccess(List<CommunityList> data);
    void getOwnerListSuccess(List<Member> ownerListResponse);
    void postCreateCommunitySuccess(CreateCommunityResponse createCommunityResponse);
    void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse);
    void postCreateCommunityOwnerSuccess(CreateCommunityOwnerResponse createCommunityOwnerResponse);
    void showNwError();
}
