package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Docs;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;

/**
 * Created by Ajit Kumar on 31-01-2017.
 */

public interface CommunityView extends BaseMvpView {
    void getSelectedCommunityListSuccess(List<Docs> selected_community_response);
    void getOwnerListSuccess(OwnerListResponse ownerListResponse);
    void createCommunitySuccess(CreateCommunityResponse createCommunityResponse);
   void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse);
    void postCreateCommunityOwner(CreateCommunityOwnerResponse createCommunityOwnerResponse);
}
