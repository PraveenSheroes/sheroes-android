package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.MemberListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.PandingMember;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public interface RequestedView extends BaseMvpView {
    void getAllRequest(List<PandingMember> data);
    void removeApprovePandingMember(MemberListResponse memberListResponse, CommunityEnum communityEnum);
}
