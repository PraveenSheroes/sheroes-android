package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.spam.SpamResponse;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.UserTagSuggestionsAdapter;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
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


    String getmStreamType();

    void onSpamPostOrCommentReported(SpamResponse spamResponse, UserPostSolrObj userPostSolrObj, Comment comment);

    void userMentionSuggestionResponse(SearchUserDataResponse searchUserDataResponse, QueryToken queryToken);


    List<String> onQueryReceived(final @NonNull QueryToken queryToken);

    List<MentionSpan> onMentionReceived(final @NonNull List<MentionSpan> mentionSpanList, String allText);

    UserTagSuggestionsAdapter onSuggestedList(final @NonNull UserTagSuggestionsAdapter userTagSuggestionsAdapter);

    Suggestible onMentionUserClick(final @NonNull Suggestible suggestible, View view);

    void textChangeListner(final Editable s);
}
