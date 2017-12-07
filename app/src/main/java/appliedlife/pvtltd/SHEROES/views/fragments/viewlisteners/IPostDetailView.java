package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import android.support.annotation.StringRes;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;

/**
 * Created by ujjwal on 28/04/17.
 */

public interface IPostDetailView extends BaseMvpView {
    void showProgressBar();

    void hideProgressBar();

    void showError(@StringRes int error);

    void showUserPost(List<BaseResponse> baseResponse);
}
