package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public interface AllMembersView extends BaseMvpView {
    void getAllMembers(List<MembersList> data);
    void removeMember(String data);
}