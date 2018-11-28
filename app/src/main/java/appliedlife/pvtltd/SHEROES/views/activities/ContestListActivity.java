package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.ContestListCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.ContestListPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ContestsListAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IContestListView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 28/04/17.
 */

public class ContestListActivity extends BaseActivity implements IContestListView, ContestListCallBack {
    public static final String SCREEN_LABEL = "Contest List";
    public static final int CONTEST_LIST_ACTIVITY = 10;

    @Inject
    AppUtils mAppUtils;

    @Inject
    ContestListPresenterImpl mContestListPresenter;

    //region binding view variables
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.contest_list)
    EmptyRecyclerView mContestListView;

    @Bind(R.id.title_toolbar)
    TextView mTitleToolbar;

    @Bind(R.id.empty_view)
    View emptyView;
    //endregion

    //region presenter region
    private List<Contest> mContestList;
    private ContestsListAdapter mContestsListAdapter;

    private int mFromNotification;
    //endregion

    //region activity methods

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_contest_list);
        ButterKnife.bind(this);
        mContestListPresenter.attachView(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mFromNotification = getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION);
        }
        setupToolbarItemsColor();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mContestListView.setLayoutManager(mLayoutManager);

        mContestListView.setEmptyViewWithImage(emptyView, R.string.empty_challenge_text, R.drawable.vector_emoty_challenge, R.string.empty_challenge_sub_text);

        initAdapter();

        mContestList = new ArrayList<>();
        FeedRequestPojo feedRequestPojo = AppUtils.makeFeedChallengeListRequest(AppConstants.CHALLENGE_SUB_TYPE_NEW, 1);
        feedRequestPojo.setPageSize(100);
        mContestListPresenter.fetchContests(feedRequestPojo);
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mContestListPresenter;
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mTitleToolbar.setText(R.string.title_contest_list);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(@StringRes int stringRes) {
        Snackbar.make(mContestListView, stringRes, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showContests(final List<Contest> contests) {
        mContestListView.setAdapter(mContestsListAdapter);
        mContestList = contests;
        mContestsListAdapter.setData(contests);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Parcelable parcelable = data.getParcelableExtra(Contest.CONTEST_OBJ);
            if (parcelable != null) {
                Contest contest = Parcels.unwrap(parcelable);
                int position = findPositionById(mContestList, contest.remote_id);
                if (position != -1) {
                    mContestList.set(position, contest);
                    mContestsListAdapter.setItem(contest, position);
                }
            }
        }
    }

    //endregion

    //region static methods
    public static void navigateTo(Activity fromActivity, String sourceScreen, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, ContestListActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivity(fromActivity, intent, null);
    }
    //endregion

    //region private methods
    public static int findPositionById(List<Contest> contests, int id) {
        if (CommonUtil.isEmpty(contests)) {
            return -1;
        }

        for (int i = 0; i < contests.size(); ++i) {
            Contest contest = contests.get(i);
            if (contest != null && contest.remote_id == id) {
                return i;
            }
        }

        return -1;
    }

    private void initAdapter() {
        mContestsListAdapter = new ContestsListAdapter(this, this);
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void onContactClicked(Contest contest, View view) {
        ContestActivity.navigateTo(ContestListActivity.this, contest, getScreenName(), null, ContestListActivity.CONTEST_LIST_ACTIVITY, -1, 1);
    }
    //endregion
}
