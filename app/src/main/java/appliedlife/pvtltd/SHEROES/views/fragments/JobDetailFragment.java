package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyRequest;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyResponse;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobDetailPojo;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.JobPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.JobDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.JobView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 20-02-2017.
 */

public class JobDetailFragment extends BaseFragment implements HomeView, JobView {
    private final String TAG = LogUtils.makeLogTag(JobDetailFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    JobPresenter mJobpresenter;
    @Bind(R.id.rv_job_detail_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_job_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_apply_job)
    TextView mtv_apply_job;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FeedDetail mFeedDetail;
    private FragmentListRefreshData mFragmentListRefreshData;
    private JobDetailActivityIntractionListner mJobDetailActivityIntractionListner;
    private AppUtils mAppUtils;

    public static JobDetailFragment createInstance(FeedDetail feedDetail) {
        JobDetailFragment jobDetailFragment = new JobDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.JOB_DETAIL, feedDetail);
        jobDetailFragment.setArguments(bundle);
        return jobDetailFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof JobDetailActivityIntractionListner) {
                mJobDetailActivityIntractionListner = (JobDetailActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.JOB_DETAIL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);
        ButterKnife.bind(this, view);
        mAppUtils = AppUtils.getInstance();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.JOB_DETAIL, mFeedDetail.getId());
        mHomePresenter.attachView(this);
        mJobpresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (JobDetailActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mAdapter, mLayoutManager, mFeedDetail, mRecyclerView, 0, 0, false, mHomePresenter, mAppUtils, mProgressBar);
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_JOB, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getIdFeedDetail()));
        return view;
    }

    @OnClick(R.id.tv_apply_job)
    public void clickApplyButton() {
        Long jobid = mFeedDetail.getIdOfEntityOrParticipant();
        JobApplyRequest jobApplyRequest = new JobApplyRequest();
        jobApplyRequest.setAppVersion("String");
        jobApplyRequest.setCloudMessagingId("String");
        jobApplyRequest.setLastScreenName("String");
        jobApplyRequest.setCoverNote("String");
        /*jobApplyRequest.setId(mFeedDetail.getId());*/
        jobApplyRequest.setScreenName("String");
        jobApplyRequest.setDeviceUniqueId("String");
        jobApplyRequest.setJobId(jobid);
        mJobpresenter.getJobApply(jobApplyRequest);
    }

    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            List<JobDetailPojo> joblist = new ArrayList<>();
            JobDetailPojo jobDetailPojo = new JobDetailPojo();
            jobDetailPojo.setId(AppConstants.ONE_CONSTANT);
            jobDetailPojo.setFeedDetail(feedDetailList.get(0));
            jobDetailPojo.setFeedDetail(mFeedDetail);
            joblist.add(jobDetailPojo);
            mAdapter.setSheroesGenericListData(joblist);
            mAdapter.notifyDataSetChanged();

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }

    public void bookMarkForDetailCard(FeedDetail feedDetail) {
        super.bookMarkForCard(feedDetail);
    }

    @Override
    public void getJobApplySuccess(JobApplyResponse jobApplyRequests) {

    }

    @Override
    public void showNwError() {

    }


    public interface JobDetailActivityIntractionListner {
        void onJobBookmarkClick(FeedDetail feedDetail);
    }

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {
        switch (successFrom) {
            case AppConstants.THREE_CONSTANT:
                jobDetailBookMarkSuccess(success);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + successFrom);
        }
    }

    protected void jobDetailBookMarkSuccess(String success) {
        if (null != mFeedDetail) {
            switch (success) {
                case AppConstants.SUCCESS:
                    mJobDetailActivityIntractionListner.onJobBookmarkClick(mFeedDetail);
                    break;
                case AppConstants.FAILED:
                    showError(getString(R.string.ID_ALREADY_BOOKMARK), AppConstants.TWO_CONSTANT);
                    break;
                default:
                    showError(AppConstants.HTTP_401_UNAUTHORIZED, AppConstants.TWO_CONSTANT);
            }
        }
    }
}