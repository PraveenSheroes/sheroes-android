package appliedlife.pvtltd.SHEROES.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.UserTagSuggestionsAdapter;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.interfaces.QueryTokenReceiver;
import appliedlife.pvtltd.SHEROES.usertagging.ui.RichEditorView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityPostView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ujjwal on 24/04/18.
 */
public class RxSearchObservable {

    public static Observable<String> fromView(RichEditorView richEditorView, final ICommunityPostView mvpView) {

        final PublishSubject<String> subject = PublishSubject.create();

        richEditorView.setQueryTokenReceiver(new QueryTokenReceiver() {
            @Override
            public List<String> onQueryReceived(@NonNull QueryToken queryToken) {
                String searchText=queryToken.getTokenString();
                subject.onNext(searchText);
                return mvpView.onQueryReceived(queryToken);
            }

            @Override
            public List<MentionSpan> onMentionReceived(@NonNull List<MentionSpan> mentionSpanList, String allText) {
                return mvpView.onMentionReceived(mentionSpanList, allText);
            }

            @Override
            public UserTagSuggestionsAdapter onSuggestedList(@NonNull UserTagSuggestionsAdapter userTagSuggestionsAdapter) {
                return mvpView.onSuggestedList(userTagSuggestionsAdapter);
            }

            @Override
            public Suggestible onMentionUserClick(@NonNull Suggestible suggestible, View view) {
                return mvpView.onMentionUserClick(suggestible, view);
            }

            @Override
            public void textChangeListner(Editable s) {
                mvpView.textChangeListner(s);
            }
        });

        return subject;
    }

    public static Observable<String> fromView(RichEditorView richEditorView, final IPostDetailView mvpView) {

        final PublishSubject<String> subject = PublishSubject.create();

        richEditorView.setQueryTokenReceiver(new QueryTokenReceiver() {
            @Override
            public List<String> onQueryReceived(@NonNull QueryToken queryToken) {
                String searchText=queryToken.getTokenString();
                subject.onNext(searchText);
                return mvpView.onQueryReceived(queryToken);
            }

            @Override
            public List<MentionSpan> onMentionReceived(@NonNull List<MentionSpan> mentionSpanList, String allText) {
                return mvpView.onMentionReceived(mentionSpanList, allText);
            }

            @Override
            public UserTagSuggestionsAdapter onSuggestedList(@NonNull UserTagSuggestionsAdapter userTagSuggestionsAdapter) {
                return mvpView.onSuggestedList(userTagSuggestionsAdapter);
            }

            @Override
            public Suggestible onMentionUserClick(@NonNull Suggestible suggestible, View view) {
                return mvpView.onMentionUserClick(suggestible, view);
            }

            @Override
            public void textChangeListner(Editable s) {
                mvpView.textChangeListner(s);
            }
        });

        return subject;
    }
}
