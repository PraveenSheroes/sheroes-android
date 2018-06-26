package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.LeaderBoardUserDetail;

/**
 * Created by Ravi on 25/06/18.
 */

public interface ICommunityLeaderBoardUser extends BaseMvpView {

    void showUsersInLeaderBoard(List<LeaderBoardUserDetail> leaderBoardUserDetails);
}
