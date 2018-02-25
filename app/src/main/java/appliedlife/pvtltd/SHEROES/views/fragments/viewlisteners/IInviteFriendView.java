package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.AllContactListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;

/**
 * Created by Praveen on 14/02/18.
 */

public interface IInviteFriendView extends BaseMvpView {
    void showContacts(List<UserContactDetail> contactDetails);
    void showUserDetail(List<UserSolrObj> userSolrObjList);
    void setContactUserListEnded(boolean contactUserListEnded);
    void addAllUserData(List<UserSolrObj> userSolrObjList);
    void addAllUserContactData(List<UserContactDetail> userContactDetailList);
    void contactsFromServerAfterSyncFromPhoneData(AllContactListResponse allContactListResponse);
    void getFollowUnfollowResponse(UserSolrObj userSolrObj);
}
