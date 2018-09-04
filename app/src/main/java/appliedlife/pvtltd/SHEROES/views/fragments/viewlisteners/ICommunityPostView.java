package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.UserTagSuggestionsAdapter;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;

/**
 * Created by ujjwal on 28/10/17.
 */

public interface ICommunityPostView extends BaseMvpView {
    void onPostSend(FeedDetail feedDetail);

    void linkRenderResponse(LinkRenderResponse linkRenderResponse);

    void showUserMentionSuggestionResponse(SearchUserDataResponse searchUserDataResponse, QueryToken queryToken);

    void finishActivity();

    List<String> onQueryReceived(final @NonNull QueryToken queryToken);



    Suggestible onMentionUserSuggestionClick(final @NonNull Suggestible suggestible, View view);

    void textChangeListner(final Editable s);

    void showImage(String finalImageUrl);
}
