package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCountResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;

public interface HomeView extends BaseMvpView {
    void getLogInResponse(LoginResponse loginResponse);
    void getFeedListSuccess(FeedResponsePojo feedResponsePojo);
    void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum);
    void getDB(List<RecentSearchData> recentSearchDatas);
    void getNotificationListSuccess(BelNotificationListResponse bellNotificationResponse);
    void getNotificationReadCountSuccess(NotificationReadCountResponse notificationReadCountResponse);
}
