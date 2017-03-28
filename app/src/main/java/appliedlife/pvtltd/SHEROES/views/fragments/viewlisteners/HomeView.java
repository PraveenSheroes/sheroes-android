package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;

public interface HomeView extends BaseMvpView {
    void getLogInResponse(LoginResponse loginResponse);
    void getFeedListSuccess(List<FeedDetail> feedDetailList);
    void getTagListSuccess(List<Doc> feedDetailList);
    void getSuccessForAllResponse(String success, FeedParticipationEnum feedParticipationEnum);
    void getDB(List<RecentSearchData> recentSearchDatas);
}
