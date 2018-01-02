package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;


import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;

/**
 * Created by ujjwal on 27/12/17.
 */

public interface ICommunityDetailView extends BaseMvpView {
    void onCommunityJoined();

    void onCommunityLeft();
}
