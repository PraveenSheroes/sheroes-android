package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

public interface HomeView extends BaseMvpView {
    void getFeedListSuccess(List<FeedDetail> feedDetailList);
    void getLikesSuccess(String success);
    void getDB(List<MasterData> masterDatas);
    void showNwError();
}
