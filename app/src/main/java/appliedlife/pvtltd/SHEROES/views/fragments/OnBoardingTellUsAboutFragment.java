package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.CURRENT_STATUS;
import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.LOCATION;
import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.TELL_US_ABOUT;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.boardingTellUsFormDataRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.communityRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.makeFeedRequest;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnBoardingTellUsAboutFragment extends BaseFragment implements OnBoardingView {
    private static final String SCREEN_LABEL = "OnBoarding Tell Us Screen";
    private final String TAG = LogUtils.makeLogTag(OnBoardingTellUsAboutFragment.class);
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    @Inject
    Preference<LoginResponse> userPreference;

    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    @Bind(R.id.swipe_view_boarding)
    SwipeRefreshLayout mSwipeView;
    @Bind(R.id.pb_boarding_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.rv_on_boarding_communities_icon)
    EmptyRecyclerView mRecyclerView;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;

    private GenericRecyclerViewAdapter mAdapter;
    private GridLayoutManager mGridManager;
    private SwipPullRefreshList mPullRefreshList;
    private FragmentListRefreshData mFragmentListRefreshData;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    @Inject
    AppUtils mAppUtils;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.on_boarding_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        mOnBoardingPresenter.attachView(this);
        initializeAllOnBoarding();
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_ONBOARDING_CURRENT_STATUS));
        return view;
    }
    private void initializeAllOnBoarding() {
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ON_BOARDING_COMMUNITIES, AppConstants.NO_REACTION_CONSTANT, true);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mGridManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridManager);
        mAdapter = new GenericRecyclerViewAdapter(getActivity(), (OnBoardingActivity)getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mOnBoardingPresenter, mRecyclerView, mGridManager, mFragmentListRefreshData) {
            @Override
            public void onHide() {
                ((OnBoardingActivity)getActivity()).tvOnBoardingFinish.setVisibility(View.GONE);

            }

            @Override
            public void onShow() {
                ((OnBoardingActivity)getActivity()). tvOnBoardingFinish.setVisibility(View.VISIBLE);

            }

            @Override
            public void dismissReactions() {

            }

        });
        setProgressBar(mProgressBar);
        refreshCommunitiesMethod();
        mSwipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCommunitiesMethod();
            }
        });
    }

    private void refreshCommunitiesMethod() {
        mFragmentListRefreshData.setPageNo(AppConstants.ONE_CONSTANT);
        mPullRefreshList = new SwipPullRefreshList();
        mFragmentListRefreshData.setSwipeToRefresh(AppConstants.ONE_CONSTANT);
        FeedRequestPojo feedRequestPojo = makeFeedRequest(AppConstants.FEED_COMMUNITY, mFragmentListRefreshData.getPageNo());
        feedRequestPojo.setOnBoardingCommunities(true);
        feedRequestPojo.setPageSize(AppConstants.FEED_FIRST_TIME);
        mOnBoardingPresenter.getFeedFromPresenter(feedRequestPojo);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {

    }

    @Override
    public void getAllDataResponse(GetAllData getAllData) {

    }

    @Override
    public void getIntersetJobResponse(GetInterestJobResponse getInterestJobResponse) {

    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getBoardingJobResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void showDataList(List<FeedDetail> feedDetailList) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(0, 0);
        mProgressBar.setLayoutParams(params );
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            List<FeedDetail> data = null;
            FeedDetail feedProgressBar = new FeedDetail();
            feedProgressBar.setSubType(AppConstants.FEED_PROGRESS_BAR);
            data = mPullRefreshList.getFeedResponses();
            int position = data.size() - feedDetailList.size();
            if (position > 0) {
                data.remove(position - 1);
            }
            data.add(feedProgressBar);
            mAdapter.setSheroesGenericListData(data);
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            if (mPageNo == AppConstants.TWO_CONSTANT) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyItemRangeChanged(position + 1, feedDetailList.size());
            }
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {

        } else {
            List<FeedDetail> data = mPullRefreshList.getFeedResponses();
            data.remove(data.size() - 1);
            mAdapter.notifyDataSetChanged();
        }
        mSwipeView.setRefreshing(false);
    }


    @Override
    public void joinUnJoinResponse(CommunityFeedSolrObj communityFeedSolrObj) {
        if(communityFeedSolrObj.isMember())
        {
            ((OnBoardingActivity)getActivity()).isJoin=true;
        }
        mAdapter.notifyItemChanged(communityFeedSolrObj.getItemPosition(), communityFeedSolrObj);
    }
    public void joinRequestForOpenCommunity(CommunityFeedSolrObj communityFeedSolrObj) {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add((long) userPreference.get().getUserSummary().getUserId());
            mOnBoardingPresenter.communityJoinFromPresenter(communityRequestBuilder(userIdList, communityFeedSolrObj.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY), communityFeedSolrObj);
        }
    }
    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

}
