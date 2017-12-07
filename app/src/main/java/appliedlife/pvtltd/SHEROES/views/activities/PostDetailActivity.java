package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.presenters.PostDetailViewImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.PostDetailAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IPostDetailView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 28/04/17.
 */

public class PostDetailActivity extends BaseActivity implements IPostDetailView {
    public static final String SCREEN_LABEL = "Contest List";
    public static final int CONTEST_LIST_ACTIVITY = 10;

    @Inject
    AppUtils mAppUtils;

    @Inject
    PostDetailViewImpl mPostDetailPresenter;

    //region binding view variables
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.user_comment_list)
    RecyclerView mRecyclerView;

    @Bind(R.id.title_toolbar)
    TextView mTitleToolbar;

    @Bind(R.id.empty_view)
    View emptyView;

    //endregion

    //region presenter region
    private List<BaseResponse> mBaseResponseList;
    private PostDetailAdapter mPostDetailListAdapter;
    private UserPostSolrObj mUserPostObj;
    private String mUserPostId;
    //endregion

    //region activity methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        mPostDetailPresenter.attachView(this);

        Parcelable parcelable = getIntent().getParcelableExtra(UserPostSolrObj.USER_POST_OBJ);
        if (parcelable != null) {
            mUserPostObj = Parcels.unwrap(parcelable);
        } else {
            mUserPostId = getIntent().getStringExtra(UserPostSolrObj.USER_POST_ID);
            if (!CommonUtil.isNotEmpty(mUserPostId)) {
                return;
            }
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        initAdapter();

        mPostDetailPresenter.setUserPost(mUserPostObj, mUserPostId);
        mBaseResponseList = new ArrayList<>();
        mPostDetailPresenter.fetchUserPost();
       /* FeedRequestPojo feedRequestPojo =mAppUtils.makeFeedChallengeListRequest(AppConstants.CHALLENGE_SUB_TYPE_NEW, 1);
        feedRequestPojo.setPageSize(100);
        mPostDetailPresenter.fetchContests(feedRequestPojo);*/

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Snackbar.make(mRecyclerView, stringRes, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showUserPost(List<BaseResponse> baseResponseList) {
        mRecyclerView.setAdapter(mPostDetailListAdapter);

        mPostDetailListAdapter.setData(baseResponseList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      /*  if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Parcelable parcelable = data.getParcelableExtra(Contest.CONTEST_OBJ);
            if (parcelable != null) {
                Contest contest = (Contest) Parcels.unwrap(parcelable);
                int position = findPositionById(mContestList, contest.remote_id);
                if (position != -1) {
                    mContestList.set(position, contest);
                    mPostDetailListAdapter.setItem(contest, position);
                }
            }
        }*/
    }

    //endregion

    //region static methodsus
    public static void navigateTo(Activity fromActivity, String sourceScreen, UserPostSolrObj userPostSolrObj, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, PostDetailActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        Parcelable parcelable = Parcels.wrap(userPostSolrObj);
        intent.putExtra(UserPostSolrObj.USER_POST_OBJ, parcelable);
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
        mPostDetailListAdapter = new PostDetailAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View item) {
                int position = mRecyclerView.getChildAdapterPosition(item);
                BaseResponse baseResponse = mBaseResponseList.get(position);
            /*    if (CommonUtil.getContestStatus(contest.getStartAt(), contest.getEndAt()) == ContestStatus.UPCOMING) {
                   *//* ContestPreviewActivity.navigateTo(ContestListActivity.this, contest, getScreenName(), null, CONTEST_LIST_ACTIVITY);*//*
                } else {
                    ContestActivity.navigateTo(PostDetailActivity.this, contest, getScreenName(), null, PostDetailActivity.CONTEST_LIST_ACTIVITY, -1, 1);
                }*/
            }
        });
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
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
    //endregion
}
