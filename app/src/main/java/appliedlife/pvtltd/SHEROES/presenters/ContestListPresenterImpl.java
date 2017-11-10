package appliedlife.pvtltd.SHEROES.presenters;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BasePresenter;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestListView;


/**
 * Created by ujjwal on 28/04/17.
 */

public class ContestListPresenterImpl extends BasePresenter<IContestListView> {
    private IContestListView mContestListView;

    @Inject
    public ContestListPresenterImpl() {

    }

    //region Presenter methods

    public void fetchContests() {
      /*  mContestListView.showProgressBar();
        CareServiceHelper.getCareServiceInstance().getContestsList()
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
                });*/

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

    }
    //endregion
}
