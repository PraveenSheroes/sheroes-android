package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;

/**
 * Created by Ajit Kumar on 31-01-2017.
 */

public interface CommunityView extends BaseMvpView {
    void getSelectedCommunityListSuccess(List<CommunityPostResponse> selected_community_response);

    void getOwnerListSuccess(OwnerListResponse ownerListResponse);

    void createCommunitySuccess(BaseResponse baseResponse);

    void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse);

    void postCreateCommunityOwner(CreateCommunityOwnerResponse createCommunityOwnerResponse);

    void getSuccessForAllResponse(BaseResponse baseResponse, CommunityEnum communityEnum);
}
