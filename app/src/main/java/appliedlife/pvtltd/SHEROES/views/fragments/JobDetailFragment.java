package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobApplyRequest;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobDetailPojo;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.JobPresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
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

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_BOOKMARK_UNBOOKMARK;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.jobApplyRequestBuilder;

/**
 * Created by Ajit Kumar on 20-02-2017.
 */

public class JobDetailFragment extends BaseFragment implements HomeView, JobView {
    private static final String SCREEN_LABEL = "Job Details Screen";
    private final String TAG = LogUtils.makeLogTag(JobDetailFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    JobPresenter mJobpresenter;
    @Bind(R.id.rv_job_detail_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_job_progress_bar)
    ProgressBar mProgressBar;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private JobFeedSolrObj jobFeedObj;
    private FragmentListRefreshData mFragmentListRefreshData;
    private AppUtils mAppUtils;
    private JobDetailPojo jobDetailPojo;
    private List<JobDetailPojo> joblist;
    private int mItemPosition;
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    private  long startedTime;
    public static JobDetailFragment createInstance(FeedDetail feedDetail) {
        JobDetailFragment jobDetailFragment = new JobDetailFragment();
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(feedDetail);
        bundle.putParcelable(AppConstants.JOB_DETAIL, parcelable);
        jobDetailFragment.setArguments(bundle);
        return jobDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            jobFeedObj = Parcels.unwrap(getArguments().getParcelable(AppConstants.JOB_DETAIL));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_job_details, container, false);
        ButterKnife.bind(this, view);
        mAppUtils = AppUtils.getInstance();
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime=System.currentTimeMillis();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.JOB_DETAIL, jobFeedObj.getIdOfEntityOrParticipant());
        mItemPosition = jobFeedObj.getItemPosition();
        mHomePresenter.attachView(this);
        mJobpresenter.attachView(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (JobDetailActivity) getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mAdapter, mLayoutManager, jobFeedObj, mRecyclerView, 0, 0, false, mHomePresenter, mAppUtils, mProgressBar);
        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_JOB, mFragmentListRefreshData.getPageNo(), mFragmentListRefreshData.getIdFeedDetail()));
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_VIEW_JOBS_DETAIL));
        return view;
    }
    public void clickApplyButton() {
        JobApplyRequest jobApplyRequest = jobApplyRequestBuilder(jobFeedObj.getIdOfEntityOrParticipant(), AppConstants.JOB_DETAIL);
        mJobpresenter.getJobApply(jobApplyRequest);
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_JOB_APPLICATION, GoogleAnalyticsEventActions.APPLIED_JOB, AppConstants.EMPTY_STRING);
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            joblist = new ArrayList<>();
            jobDetailPojo = new JobDetailPojo();
            jobDetailPojo.setId(AppConstants.ONE_CONSTANT);
            jobFeedObj = (JobFeedSolrObj) feedDetailList.get(0);
            jobFeedObj.setItemPosition(mItemPosition);
            ((JobDetailActivity) getActivity()).setBackGroundImage(jobFeedObj);
            jobDetailPojo.setFeedDetail(jobFeedObj);
            joblist.add(jobDetailPojo);
            if (jobFeedObj.isApplied()) {
                ((JobDetailActivity)getActivity()).mtv_apply_job.setText(getString(R.string.ID_APPLIED));
                ((JobDetailActivity)getActivity()).mtv_apply_job.setEnabled(false);
            } else {
                ((JobDetailActivity)getActivity()).mtv_apply_job.setText(getString(R.string.ID_APPLY));
                ((JobDetailActivity)getActivity()).mtv_apply_job.setEnabled(true);
            }
            mAdapter.notifyDataSetChanged();
            mAdapter.setSheroesGenericListData(joblist);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
        mJobpresenter.detachView();
        if(jobFeedObj.isApplied()) {
            long timeSpent = System.currentTimeMillis() - startedTime;
            moEngageUtills.entityMoEngageViewAppliedJob(getActivity(), mMoEHelper, payloadBuilder, timeSpent);
        }
    }

    public void bookMarkForDetailCard(FeedDetail feedDetail) {
        super.bookMarkForCard(feedDetail);
    }

    @Override
    public void getJobApplySuccess(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                ((JobDetailActivity)getActivity()).mtv_apply_job.setText(getString(R.string.ID_APPLIED));
                ((JobDetailActivity)getActivity()).mtv_apply_job.setEnabled(false);
                jobFeedObj.setApplied(true);
                ((JobDetailActivity) getActivity()).setBackGroundImage(jobFeedObj);
                Toast.makeText(getActivity(), getString(R.string.ID_APPLIED) + AppConstants.SPACE + getString(R.string.ID_SUCCESSFULLY), Toast.LENGTH_SHORT).show();
                HashMap<String, Object> properties =
                        new EventProperty.Builder()
                        .id(Long.toString(jobFeedObj.getIdOfEntityOrParticipant()))
                        .title(jobFeedObj.getNameOrTitle())
                                .companyId(Long.toString(jobFeedObj.getCompanyMasterId()))
                        .location(jobFeedObj.getAuthorCityName())
                        .build();
                trackEvent(Event.JOBS_APPLIED, properties);
                moEngageData(jobFeedObj);
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);
        }

    }

    private void moEngageData(JobFeedSolrObj jobFeedObj) {

        StringBuilder mergeJobTypes=new StringBuilder();
        if (StringUtil.isNotEmptyCollection(jobFeedObj.getSearchTextJobEmpTypes())) {
            List<String> jobTypes = jobFeedObj.getSearchTextJobEmpTypes();
            for (String jobType : jobTypes) {
                mergeJobTypes.append(jobType).append(AppConstants.PIPE);
            }
        }
        StringBuilder mergeJobSkills = new StringBuilder();
        if (StringUtil.isNotEmptyCollection(jobFeedObj.getSearchTextJobSkills())) {
            List<String> jobSkills = jobFeedObj.getSearchTextJobSkills();

            for (String skill : jobSkills) {
                mergeJobSkills.append(skill);
                mergeJobSkills.append(AppConstants.COMMA);
            }
        }

        moEngageUtills.entityMoEngageAppliedJob(getActivity(), mMoEHelper, payloadBuilder, jobFeedObj.getNameOrTitle(), jobFeedObj.getIdOfEntityOrParticipant(), jobFeedObj.getAuthorName(), jobFeedObj.getAuthorCityName(),mergeJobTypes.toString(),mergeJobTypes.toString(),AppConstants.EMPTY_STRING,AppConstants.EMPTY_STRING,mergeJobSkills.toString());

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case BOOKMARK_UNBOOKMARK:
                jobDetailBookMarkSuccess(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    protected void jobDetailBookMarkSuccess(BaseResponse baseResponse) {
        if (null != jobFeedObj) {
            if (baseResponse instanceof BookmarkResponsePojo) {
                switch (baseResponse.getStatus()) {
                    case AppConstants.SUCCESS:
                        HashMap<String, Object> properties =
                                new EventProperty.Builder()
                                        .id(Long.toString(jobFeedObj.getIdOfEntityOrParticipant()))
                                        .title(jobFeedObj.getNameOrTitle())
                                        .companyId(Long.toString(jobFeedObj.getCompanyMasterId()))
                                        .location(jobFeedObj.getAuthorCityName())
                                        .build();
                        trackEvent(Event.JOBS_BOOKMARKED, properties);
                        ((JobDetailActivity) getActivity()).mTvJobDetailBookmark.setEnabled(true);
                        moEngageUtills.entityMoEngageBookMarkData(getActivity(), mMoEHelper, payloadBuilder, jobFeedObj);
                        break;
                    case AppConstants.FAILED:
                        if (!jobFeedObj.isBookmarked()) {
                            jobFeedObj.setBookmarked(true);
                            jobDetailPojo.setFeedDetail(jobFeedObj);
                        } else {
                            jobFeedObj.setBookmarked(false);
                            jobDetailPojo.setFeedDetail(jobFeedObj);
                        }
                        joblist.clear();
                        joblist.add(jobDetailPojo);
                        mAdapter.notifyDataSetChanged();
                        ((JobDetailActivity) getActivity()).onJobBookmarkClick(jobFeedObj);
                        showError(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_BOOKMARK_UNBOOKMARK);
                        break;
                    default:
                        showError(getString(R.string.ID_GENERIC_ERROR), ERROR_BOOKMARK_UNBOOKMARK);
                }
            }
        }

    }

    @Override
    protected Map<String, Object> getExtraProperties() {
        HashMap<String, Object> properties = new
                EventProperty.Builder()
                .id(Long.toString(jobFeedObj.getIdOfEntityOrParticipant()))
                .build();
        return properties;
    }
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}