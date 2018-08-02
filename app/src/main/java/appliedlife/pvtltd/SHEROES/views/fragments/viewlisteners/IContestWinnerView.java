package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.post.Winner;
import appliedlife.pvtltd.SHEROES.models.entities.post.WinnerResponse;

/**
 * Created by ujjwal on 04/05/17.
 */

public interface IContestWinnerView extends BaseMvpView {
    void showPrizes(List<Winner> winners);

    void showChallengeWinnerPostResponse(FeedDetail feedDetail);
}
