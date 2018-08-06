package appliedlife.pvtltd.SHEROES.basecomponents;

import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.PollType;

public interface PollTypeCallBack {
    void onPollTypeClicked(PollType pollType);

}
