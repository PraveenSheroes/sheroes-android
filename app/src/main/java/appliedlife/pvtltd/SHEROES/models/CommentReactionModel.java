package appliedlife.pvtltd.SHEROES.models;

import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentRequest;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentResponse;
import appliedlife.pvtltd.SHEROES.preferences.SessionUser;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentReactionModel {
    private final String TAG = LogUtils.makeLogTag(CommentReactionModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    Preference<SessionUser> userPreference;
    @Inject
    public CommentReactionModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<CommentResponse> getAllCommentListFromModel(CommentRequest commentRequest){
        return sheroesAppServiceApi.getCommentFromApi(commentRequest)
                .map(new Func1<CommentResponse, CommentResponse>() {
                    @Override
                    public CommentResponse call(CommentResponse commentResponse) {
                        return commentResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<CommentResponse> getAllReactionListFromModel(CommentRequest commentRequest){
        return sheroesAppServiceApi.getReactionFromApi(commentRequest)
                .map(new Func1<CommentResponse, CommentResponse>() {
                    @Override
                    public CommentResponse call(CommentResponse commentResponse) {
                        return commentResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    }

