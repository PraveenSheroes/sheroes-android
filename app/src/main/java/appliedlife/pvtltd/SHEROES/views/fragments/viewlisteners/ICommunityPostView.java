package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;

/**
 * Created by ujjwal on 28/10/17.
 */

public interface ICommunityPostView extends BaseMvpView {
    void onPostSend(FeedDetail feedDetail);

    void linkRenderResponse(LinkRenderResponse linkRenderResponse);

    void showUserMentionSuggestionResponse(SearchUserDataResponse searchUserDataResponse, QueryToken queryToken);

    void finishActivity();
}
