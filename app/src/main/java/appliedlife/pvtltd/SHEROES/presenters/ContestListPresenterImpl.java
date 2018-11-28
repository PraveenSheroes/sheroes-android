package appliedlife.pvtltd.SHEROES.presenters;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppServiceApi;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ChallengeSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.networkutills.NetworkUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestListView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ujjwal on 28/04/17.
 */

public class ContestListPresenterImpl extends BasePresenter<IContestListView> {

    //region member variable
    private SheroesAppServiceApi mSheroesAppServiceApi;
    //endregion member variable

    //region constructor
    @Inject
    public ContestListPresenterImpl(SheroesAppServiceApi mSheroesAppServiceApi) {
        this.mSheroesAppServiceApi = mSheroesAppServiceApi;
    }
    //endregion constructor

    //region Presenter methods
    public void fetchContests(FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();
        mSheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .subscribeOn(Schedulers.io())
                .compose(this.<FeedResponsePojo>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FeedResponsePojo>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Crashlytics.getInstance().core.logException(e);
                        getMvpView().stopProgressBar();
                        getMvpView().showError(e.getMessage(), null);
                    }

                    @Override
                    public void onNext(FeedResponsePojo feedResponsePojo) {
                        getMvpView().stopProgressBar();
                        List<Contest> contests = new ArrayList<>();
                        if (!CommonUtil.isEmpty(feedResponsePojo.getFeedDetails())) {
                            for (FeedDetail feedDetail : feedResponsePojo.getFeedDetails()) {
                                if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.CHALLENGE_SUB_TYPE_NEW)) {
                                    ChallengeSolrObj challengeSolrObj = (ChallengeSolrObj) feedDetail;
                                    Contest contest = new Contest();
                                    contest.title = challengeSolrObj.getChallengeTitle();
                                    contest.remote_id = (int) challengeSolrObj.getIdOfEntityOrParticipant();
                                    contest.body = challengeSolrObj.getListDescription();
                                    contest.createdDateString = challengeSolrObj.getChallengeStartDate();
                                    contest.endDateString = challengeSolrObj.getChallengeEndDate();
                                    contest.hasWinner = challengeSolrObj.isChallengeHasWinner();
                                    contest.isWinner = challengeSolrObj.isChallengeIsWinner();
                                    contest.authorName = challengeSolrObj.getAuthorName();
                                    contest.authorId = challengeSolrObj.getAuthorId();
                                    contest.isAuthorMentor = challengeSolrObj.isAuthorMentor();
                                    contest.authorType = challengeSolrObj.getChallengeAuthorTypeS();
                                    contest.authorImageUrl = challengeSolrObj.getAuthorImageUrl();
                                    contest.submissionCount = challengeSolrObj.getNoOfChallengeAccepted();
                                    contest.hasMyPost = challengeSolrObj.isChallengeAccepted();
                                    contest.tag = challengeSolrObj.getChallengeAcceptPostText();
                                    contest.thumbImage = challengeSolrObj.getThumbnailImageUrl();
                                    contest.createdBy = challengeSolrObj.getCreatedBy();
                                    if (StringUtil.isNotNullOrEmptyString(challengeSolrObj.getPostShortBranchUrls())) {
                                        contest.shortUrl = challengeSolrObj.getPostShortBranchUrls();
                                    } else {
                                        contest.shortUrl = challengeSolrObj.getDeepLinkUrl();
                                    }
                                    contest.mWinnerAddress = challengeSolrObj.getWinnerAddress();
                                    contest.winnerAddressUpdated = challengeSolrObj.winnerAddressUpdated;
                                    contest.winnerAnnouncementDate = challengeSolrObj.getChallengeAnnouncementDate(); //Fix for winner announcement
                                    contests.add(contest);
                                }
                            }
                        }
                        getMvpView().showContests(contests);
                    }
                });
    }
    //endregion
}