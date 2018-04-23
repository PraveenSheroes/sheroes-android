package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import android.support.annotation.StringRes;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;

/**
 * Created by ujjwal on 28/04/17.
 */

public interface IPostDetailView extends BaseMvpView {
    void showProgressBar();

    void hideProgressBar();

    void showError(@StringRes int error);


    void setHasMoreComments(boolean b);

    void smoothScrollToBottom();

    void commentStartedLoading();

    void commentFinishedLoading();

    void addAllPost(int headerCount, List<Comment> commentList);

    void addData(BaseResponse baseResponse);

    void addData(int index, BaseResponse baseResponse);

    void removeData(int index);

    void setData(int index, BaseResponse baseResponse);

    void updateComment(Comment comment);

    void onPostDeleted();

    void editLastComment();

    void deleteLastComment();


    String getStreamType();

    void onSpamPostOrCommentReported(SpamResponse spamResponse, UserPostSolrObj userPostSolrObj, Comment comment);

    void userTagResponse(SearchUserDataResponse searchUserDataResponse, QueryToken queryToken);
}
