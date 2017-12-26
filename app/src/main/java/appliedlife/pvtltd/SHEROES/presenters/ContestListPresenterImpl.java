package appliedlife.pvtltd.SHEROES.presenters;


import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
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
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestListView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by ujjwal on 28/04/17.
 */

public class ContestListPresenterImpl extends BasePresenter<IContestListView> {
    private IContestListView mContestListView;
    SheroesAppServiceApi sheroesAppServiceApi;

    @Inject
    public ContestListPresenterImpl(SheroesAppServiceApi sheroesAppServiceApi) {
        this.sheroesAppServiceApi = sheroesAppServiceApi;
    }

    //region Presenter methods

  /*  public void fetchContests() {
        mContestListView.showProgressBar();
        *//*CareServiceHelper.getCareServiceInstance().getContestsList()
                .compose(this.<List<Contest>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Contest>>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mContestListView.hideProgressBar();
                        mContestListView.showError(R.string.snackbar_network_error);
                    }

                    @Override
                    public void onNext(List<Contest> contests) {
                        mContestListView.hideProgressBar();
                        mContestListView.showContests(contests);
                    }
                });*//*

        Contest contest = new Contest();
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        contest.startAt = today;
        contest.endAt = tomorrow;
        contest.body = "Win a chance to get a professional photo shoot done with your baby!!!\n" +
                "Hi all,\n" +
                "Share a STORY of your baby’s growth milestone which is close to your heart. What were your first thoughts? How did you feel? Were you surprised, shocked or both? Was it late or was it early? How did your baby react? \n" +
                "\n" +
                "Best stories on baby’s growth milestones (like smiling, crawling, sitting, standing, speaking) stand the chance to win the prizes. You can also share a photo of such moment.\n" +
                "\n" +
                "So go for it. Tap the answer button to share your baby’s milestone story and a photo\n" +
                "RULE\n" +
                "Johnson’s Baby bring to you a contest!\n" +
                "The contest will run for 7 days (from 3rd April to 9th April).\n" +
                "• This is day 3 of the contest \n" +
                "• Those who didn’t win on Day 1 & Day 2 can post their answers again. \n" +
                "• Check original article for daily winner list and rules and regulations.\n" +
                "PRIZE-\n" +
                "Everyday 3 lucky winners will win the NEW Johnson’s Baby Care Collection which has a special baby record book.\n" +
                "\n" +
                "Everyday 10 winners will win a 15% discount on the NEW Johnson’s Baby Care Collection.\n" +
                "\n" +
                "Grand Prize: One lucky winner stands a chance to get a professional photo shoot done with their baby!!!";
        contest.hasStarted = true;
        contest.title = "Showcase your workspace. Share your workspace photo that motivate you to work daily.";
        contest.totalViews = 235;
        contest.submissionCount = 198;

        List<Contest> contests = new ArrayList<>();
        contests.add(contest);
        getMvpView().showContests(contests);

    }*/

    public void fetchContests(FeedRequestPojo feedRequestPojo) {
        if (!NetworkUtil.isConnected(SheroesApplication.mContext)) {
            getMvpView().showError(AppConstants.CHECK_NETWORK_CONNECTION, null);
            return;
        }
        getMvpView().startProgressBar();
        Subscription subscription = getFeedFromModel(feedRequestPojo).subscribe(new Subscriber<FeedResponsePojo>() {
            @Override
            public void onCompleted() {
                //getMvpView().stopProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                Crashlytics.getInstance().core.logException(e);
                getMvpView().stopProgressBar();
                getMvpView().showError(SheroesApplication.mContext.getString(R.string.ID_GENERIC_ERROR), null);

            }

            @Override
            public void onNext(FeedResponsePojo feedResponsePojo) {
                getMvpView().stopProgressBar();
                List<Contest> contests  = new ArrayList<>();
                if(!CommonUtil.isEmpty(feedResponsePojo.getFeedDetails())){
                    for (FeedDetail feedDetail : feedResponsePojo.getFeedDetails()){
                        if(feedDetail.getSubType().equalsIgnoreCase(AppConstants.CHALLENGE_SUB_TYPE_NEW)){
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
                            contest.authorType = challengeSolrObj.getChallengeAuthorTypeS();
                            contest.authorImageUrl = challengeSolrObj.getAuthorImageUrl();
                            contest.submissionCount = challengeSolrObj.getNoOfChallengeAccepted();
                            contest.hasMyPost = challengeSolrObj.isChallengeAccepted();
                            contest.tag = challengeSolrObj.getChallengeAcceptPostText();
                            contest.thumbImage = challengeSolrObj.getThumbnailImageUrl();
                            contest.shortUrl = feedDetail.getDeepLinkUrl();
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
        registerSubscription(subscription);
    }

    public Observable<FeedResponsePojo> getFeedFromModel(FeedRequestPojo feedRequestPojo) {
        return sheroesAppServiceApi.getFeedFromApi(feedRequestPojo)
                .map(new Func1<FeedResponsePojo, FeedResponsePojo>() {
                    @Override
                    public FeedResponsePojo call(FeedResponsePojo feedResponsePojo) {
                        return feedResponsePojo;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    //endregion
}
