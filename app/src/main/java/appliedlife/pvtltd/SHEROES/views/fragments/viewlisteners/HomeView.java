package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavMenuItem;

public interface HomeView extends BaseMvpView {
    void getLogInResponse(LoginResponse loginResponse);
    void getFeedListSuccess(FeedResponsePojo feedResponsePojo);
    void showHomeFeedList(List<FeedDetail> feedDetailList);
    void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum);

    void getNotificationListSuccess(BelNotificationListResponse bellNotificationResponse);
    void getNotificationReadCountSuccess(BaseResponse baseResponse,FeedParticipationEnum feedParticipationEnum);

    void invalidateLikeUnlike(Comment comment);

    interface NavigationView {
        void getNavigationDrawerItemsSuccess(List<NavMenuItem> navigationItems);
        void getNavigationDrawerItemsFailed();
    }

}
