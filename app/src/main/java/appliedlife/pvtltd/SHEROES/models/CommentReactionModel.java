package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.Gson;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
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
    @Inject
    public CommentReactionModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<CommentReactionResponsePojo> getAllCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo,boolean isReaction){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(commentReactionRequestPojo));
        if(!isReaction) {
            return sheroesAppServiceApi.getCommentFromApi(commentReactionRequestPojo)
                    .map(new Func1<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                        @Override
                        public CommentReactionResponsePojo call(CommentReactionResponsePojo commentReactionResponsePojo) {
                            return commentReactionResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        else
        {
            return sheroesAppServiceApi.getAllReactionFromApi(commentReactionRequestPojo)
                    .map(new Func1<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                        @Override
                        public CommentReactionResponsePojo call(CommentReactionResponsePojo commentReactionResponsePojo) {
                            return commentReactionResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public Observable<CommentReactionResponsePojo> addCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo){
        return sheroesAppServiceApi.addCommentFromApi(commentReactionRequestPojo)
                .map(new Func1<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                    @Override
                    public CommentReactionResponsePojo call(CommentReactionResponsePojo commentReactionResponsePojo) {
                        return commentReactionResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<CommentReactionResponsePojo> editCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo){
        return sheroesAppServiceApi.editCommentFromApi(commentReactionRequestPojo)
                .map(new Func1<CommentReactionResponsePojo, CommentReactionResponsePojo>() {
                    @Override
                    public CommentReactionResponsePojo call(CommentReactionResponsePojo commentReactionResponsePojo) {
                        return commentReactionResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    }

