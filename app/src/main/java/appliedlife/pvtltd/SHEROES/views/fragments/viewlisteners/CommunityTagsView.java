package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTags;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public interface CommunityTagsView extends BaseMvpView {
    void getCommunityTagsList(List<CommunityTags> data);
    void showNwError();


}
