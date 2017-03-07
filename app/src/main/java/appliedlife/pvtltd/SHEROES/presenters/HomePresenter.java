package appliedlife.pvtltd.SHEROES.presenters;


import com.f2prateek.rx.preferences.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.HomeModel;
import appliedlife.pvtltd.SHEROES.models.RecentSearchDataModel;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Hotel presenter perform required response data for Home activity.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private final String TAG = LogUtils.makeLogTag(HomePresenter.class);
    HomeModel mHomeModel;
    SheroesApplication mSheroesApplication;
    @Inject
    Preference<LoginResponse> mUserPreference;
    RecentSearchDataModel mRecentSearchDataModel;

    @Inject
    public HomePresenter(HomeModel homeModel, SheroesApplication sheroesApplication, Preference<LoginResponse> userPreference, RecentSearchDataModel recentSearchDataModel) {
        this.mHomeModel = homeModel;
        this.mSheroesApplication = sheroesApplication;
        this.mUserPreference = userPreference;
        this.mRecentSearchDataModel = recentSearchDataModel;

    }

    @Override
    public void detachView() {
        super.detachView();
    }

    @Override
    public boolean isViewAttached() {
        return super.isViewAttached();
    }


    public void getFeedFromPresenter(final FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getFeedFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                getMvpView().stopProgressBar();
                if(null!=feedRequestPojo&& StringUtil.isNotEmptyCollection(feedResponsePojo.getFeaturedDocs())&&feedResponsePojo.getFeaturedDocs().size()>AppConstants.ONE_CONSTANT)
                {
                    getMvpView().getFeedListSuccess(feedResponsePojo.getFeaturedDocs());
                }else
                {
                    getMvpView().getFeedListSuccess(feedResponsePojo.getFeedDetails());
                }
            }
        });
        registerSubscription(subscription);
    }

    public void getBookMarkFromPresenter(FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getBookMarkFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getFeedListSuccess(feedResponsePojo.getFeedDetails());
            }
        });
        registerSubscription(subscription);
    }

    public void getLikesFromPresenter(LikeRequestPojo likeRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(AppConstants.FAILED,AppConstants.ONE_CONSTANT);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
               getMvpView().getSuccessForAllResponse(likeResponse.getStatus(),AppConstants.ONE_CONSTANT);
            }
        });
        registerSubscription(subscription);
    }
    public void getUnLikesFromPresenter(LikeRequestPojo likeRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.getUnLikesFromModel(likeRequestPojo).subscribe(new Subscriber<LikeResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(AppConstants.FAILED,AppConstants.ONE_CONSTANT);
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            }

            @Override
            public void onNext(LikeResponse likeResponse) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(likeResponse.getStatus(),AppConstants.ONE_CONSTANT);
            }
        });
        registerSubscription(subscription);
    }

    public void addBookMarkFromPresenter(BookmarkRequestPojo bookmarkRequestPojo,boolean isBookmarked) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.addBookmarkFromModel(bookmarkRequestPojo,isBookmarked).subscribe(new Subscriber<BookmarkResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(AppConstants.FAILED,AppConstants.THREE_CONSTANT);
            }

            @Override
            public void onNext(BookmarkResponsePojo bookmarkResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(bookmarkResponsePojo.getStatus(),AppConstants.THREE_CONSTANT);
            }
        });
        registerSubscription(subscription);
    }


    public void editCommentListFromPresenter(CommentReactionRequestPojo commentReactionRequestPojo) {
        if (!NetworkUtil.isConnected(mSheroesApplication)) {
            getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = mHomeModel.editCommentListFromModel(commentReactionRequestPojo).subscribe(new Subscriber<CommentReactionResponsePojo>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }
            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            }
            @Override
            public void onNext(CommentReactionResponsePojo commentResponsePojo) {
                getMvpView().stopProgressBar();
                getMvpView().getSuccessForAllResponse(commentResponsePojo.getStatus(),AppConstants.TWO_CONSTANT);
            }
        });
        registerSubscription(subscription);
    }


    public void saveMasterDataTypes(List<RecentSearchData> recentSearchData, long entitiyOrParticipantID) {
        getMvpView().startProgressBar();
        Subscription subscription = mRecentSearchDataModel.saveRecentSearchTypes(recentSearchData,entitiyOrParticipantID).subscribe(new Subscriber<List<RecentSearchData>>() {
            @Override
            public void onCompleted() {
                getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().stopProgressBar();
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            }

            @Override
            public void onNext(List<RecentSearchData> masterDatas) {
                getMvpView().stopProgressBar();
               // getMvpView().getDB(masterDatas);
            }
        });
        registerSubscription(subscription);
    }


    public void fetchMasterDataTypes() {
        Subscription subscribe = mRecentSearchDataModel.getAllRecentSearch().subscribe(new Subscriber<List<RecentSearchData>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

                getMvpView().stopProgressBar();
                getMvpView().showError(AppConstants.ERROR_IN_RESPONSE);
            }

            @Override
            public void onNext(List<RecentSearchData> masterDatas) {
                getMvpView().stopProgressBar();
                getMvpView().getDB(masterDatas);
            }
        });
        registerSubscription(subscribe);
    }

    public void onStop() {
        detachView();
    }
}
