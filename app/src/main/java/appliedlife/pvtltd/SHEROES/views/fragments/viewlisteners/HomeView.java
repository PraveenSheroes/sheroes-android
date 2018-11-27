package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;

public interface HomeView extends BaseMvpView {
    void getLogInResponse(LoginResponse loginResponse);

    void getFeedListSuccess(FeedResponsePojo feedResponsePojo);

    void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum);

    void showNotificationList(BelNotificationListResponse bellNotificationResponse);

    void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum);

    void onConfigFetched();

    void getUserSummaryResponse(BoardingDataResponse boardingDataResponse);
}
