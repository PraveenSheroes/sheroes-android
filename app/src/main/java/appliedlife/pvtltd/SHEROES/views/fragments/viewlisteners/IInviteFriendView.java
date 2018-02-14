package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;

/**
 * Created by Praveen on 14/02/18.
 */

public interface IInviteFriendView extends BaseMvpView {
    void showContacts(List<UserContactDetail> contactDetails);
}
