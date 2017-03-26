package appliedlife.pvtltd.SHEROES.models;


import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Hotel model class interact with Hotel presenter.
 * required response data for Home activity.
 */
@Singleton
public class HomeModel {
    private final String TAG = LogUtils.makeLogTag(HomeModel.class);
    private final SheroesAppServiceApi sheroesAppServiceApi;
    Gson gson;
    @Inject
    public HomeModel(SheroesAppServiceApi sheroesAppServiceApi,Gson gson) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
        this.gson= gson;
    }
    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo  feedRequestPojo){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        LogUtils.info(TAG,"************feed model*******");
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<FeedResponsePojo> getMyCommunityFromModel(FeedRequestPojo  feedRequestPojo){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getMyCommunityFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<GetAllData> getTagFromModel(GetAllDataRequest getAllDataRequest){
        LogUtils.info(TAG,"TAG FRom*******************"+new Gson().toJson(getAllDataRequest));
        return sheroesAppServiceApi.getTagFromApi(getAllDataRequest)
                .map(new Func1<GetAllData, GetAllData>() {
                    @Override
                    public GetAllData call(GetAllData getAllData) {
                        return getAllData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<FeedResponsePojo> getBookMarkFromModel(FeedRequestPojo feedRequestPojo){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(feedRequestPojo));
        return sheroesAppServiceApi.getBookMarkFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<BookmarkResponsePojo> addBookmarkFromModel(BookmarkRequestPojo bookmarkRequestPojo,boolean isBookmarked){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(bookmarkRequestPojo));
        if(!isBookmarked) {
            return sheroesAppServiceApi.addBookMarkToApi(bookmarkRequestPojo)
                    .map(new Func1<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo call(BookmarkResponsePojo bookmarkResponsePojo) {
                            return bookmarkResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        else
        {
            return sheroesAppServiceApi.UnBookMarkToApi(bookmarkRequestPojo)
                    .map(new Func1<BookmarkResponsePojo, BookmarkResponsePojo>() {
                        @Override
                        public BookmarkResponsePojo call(BookmarkResponsePojo bookmarkResponsePojo) {
                            return bookmarkResponsePojo;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    public Observable<LikeResponse> getLikesFromModel(LikeRequestPojo  likeRequestPojo){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(likeRequestPojo));
        return sheroesAppServiceApi.getLikesFromApi(likeRequestPojo)
                .map(new Func1<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse call(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<LikeResponse> getUnLikesFromModel(LikeRequestPojo  likeRequestPojo){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(likeRequestPojo));
        return sheroesAppServiceApi.getUnLikesFromApi(likeRequestPojo)
                .map(new Func1<LikeResponse, LikeResponse>() {
                    @Override
                    public LikeResponse call(LikeResponse likeResponse) {
                        return likeResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<CommentReactionResponsePojo> editCommentListFromModel(CommentReactionRequestPojo commentReactionRequestPojo){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(commentReactionRequestPojo));
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
    public Observable<CommunityResponse> communityJoinFromModel(CommunityRequest communityRequest){
        LogUtils.info(TAG,"*******************"+new Gson().toJson(communityRequest));
        return sheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .map(new Func1<CommunityResponse, CommunityResponse>() {
                    @Override
                    public CommunityResponse call(CommunityResponse communityResponse) {
                        return communityResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<CommunityResponse> communityOwnerFromModel(CommunityRequest communityRequest){
        LogUtils.error("Community Join req: ",gson.toJson(communityRequest));

        return sheroesAppServiceApi.getCommunityJoinResponse(communityRequest)
                .map(new Func1<CommunityResponse, CommunityResponse>() {
                    @Override
                    public CommunityResponse call(CommunityResponse communityResponse) {
                        LogUtils.error("Community Join res: ",gson.toJson(communityResponse));
                        return communityResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
     public Observable<LoginResponse> getAuthTokenRefreshFromModel() {
        return sheroesAppServiceApi.getRefreshToken()
                .map(new Func1<LoginResponse, LoginResponse>() {
                    @Override
                    public LoginResponse call(LoginResponse loginResponse) {
                        return loginResponse;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
