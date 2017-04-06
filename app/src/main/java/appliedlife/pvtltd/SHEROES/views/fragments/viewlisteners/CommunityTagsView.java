package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;

/**
 * Created by Ajit Kumar on 07-02-2017.
 */

public interface CommunityTagsView extends BaseMvpView {
    void getTagListSuccess(GetTagData getAllData);
}
