package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;

public interface ISearchView extends BaseMvpView {
    void onHashTagsResponse(List<String> hashtagList);
}
