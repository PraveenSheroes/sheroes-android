package appliedlife.pvtltd.SHEROES.utils;

import androidx.annotation.NonNull;
import android.text.Editable;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.interfaces.IQueryTokenReceiver;
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

        richEditorView.setQueryTokenReceiver(new IQueryTokenReceiver() {
            @Override
            public List<String> onQueryReceived(@NonNull QueryToken queryToken) {
                String searchText=queryToken.getTokenString();
                if(searchText.contains("@")) {
                    subject.onNext(searchText);
                }
                return mvpView.onQueryReceived(queryToken);
            }

            @Override
            public Suggestible onMentionUserSuggestionClick(@NonNull Suggestible suggestible, View view) {
                return mvpView.onMentionUserSuggestionClick(suggestible, view);
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

        richEditorView.setQueryTokenReceiver(new IQueryTokenReceiver() {
            @Override
            public List<String> onQueryReceived(@NonNull QueryToken queryToken) {
                String searchText=queryToken.getTokenString();
                if(searchText.contains("@")) {
                    subject.onNext(searchText);
                }
                return mvpView.onQueryReceived(queryToken);
            }

            @Override
            public Suggestible onMentionUserSuggestionClick(@NonNull Suggestible suggestible, View view) {
                return mvpView.onMentionUserSuggestionClick(suggestible, view);
            }

            @Override
            public void textChangeListner(Editable s) {
                mvpView.textChangeListner(s);
            }
        });

        return subject;
    }
}
