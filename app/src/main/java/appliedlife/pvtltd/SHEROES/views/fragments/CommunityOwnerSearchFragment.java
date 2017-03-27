package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Docs;
import appliedlife.pvtltd.SHEROES.models.entities.community.ListOfInviteSearch;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.InvitePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.InviteSearchView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 15-02-2017.
 */

public class CommunityOwnerSearchFragment extends BaseFragment implements CommunityView,HomeView,InviteSearchView {
    private final String TAG = LogUtils.makeLogTag(AllSearchFragment.class);
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    CreateCommunityPresenter createCommunityPresenter;
    @Inject
    InvitePresenter mSearchModPresenter;
    @Inject
    HomePresenter mHomePresenter;
    private AppUtils mAppUtils;
    FeedDetail mFeedDetail;
    private FragmentListRefreshData mFragmentListRefreshData;
    @Bind(R.id.rv_search_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_search_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.et_search_edit_text)
    public EditText mSearchEditText;
    @Bind(R.id.tv_back_community_tag)
    TextView mBackCommunityTag;
    @Bind(R.id.lnr_invite_member)
    LinearLayout mLnr_invite_member;

    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private InviteOwnerActivityIntractionListner mHomeSearchActivityIntractionListner;
    private SwipPullRefreshList mPullRefreshList;
    boolean listLoad = true;
    private LinearLayoutManager mLayoutManager;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof InviteOwnerActivityIntractionListner) {
                mHomeSearchActivityIntractionListner = (InviteOwnerActivityIntractionListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.ownersearch, container, false);
        ButterKnife.bind(this, view);
        if(null!=getArguments())
        {
            mFeedDetail =getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL);
        }
        mSearchModPresenter.attachView(this);
        mHomePresenter.attachView(this);
        createCommunityPresenter.attachView(this);
        mAppUtils = AppUtils.getInstance();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.JOB_FRAGMENT, AppConstants.EMPTY_STRING);
        mSearchEditText.setHint("Search Users");
        editTextWatcher();
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mHomePresenter.getFeedFromPresenter(mAppUtils.feedRequestBuilder(AppConstants.USER_SUB_TYPE, mFragmentListRefreshData.getPageNo()));
        return view;
    }

    @Override
    public void getSearchListSuccess(List<ListOfInviteSearch> listOfSearches) {
        if(mAdapter!=null) {
            mAdapter.setSheroesGenericListData(listOfSearches);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getityCommunityListSuccess(List<CommunityList> data) {

    }

    @Override
    public void getSelectedCommunityListSuccess(List<Docs> selected_community_response) {

    }






    @Override
    public void getOwnerListSuccess(List<Member> ownerListResponse) {

    }

    @Override
    public void postCreateCommunitySuccess(CreateCommunityResponse createCommunityResponse) {

    }

    @Override
    public void addPostCreateCommunitySuccess(CommunityPostCreateResponse createCommunityResponse) {

    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse) {

    }

    @Override
    public void postCreateCommunityOwnerSuccess(CreateCommunityOwnerResponse createCommunityOwnerResponse) {
        String status=createCommunityOwnerResponse.getStatus();
        Toast.makeText(getActivity(),status,Toast.LENGTH_LONG).show();

    }

    @Override
    public void showNwError() {
        mHomeSearchActivityIntractionListner.onErrorOccurence();
    }
    @OnClick(R.id.tv_back_community_tag)
    public void communityTagBack()
    {
        mHomeSearchActivityIntractionListner.closeOwner();
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMsg,int errorFor) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSearchModPresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * When user type city name it works for each character.
     */
    protected void editTextWatcher() {
        mSearchEditText.addTextChangedListener(dataSearchTextWatcher());
        mSearchEditText.setFocusableInTouchMode(true);
        mSearchEditText.requestFocus();
    }

    /**
     * Text watcher workes on every character change and make hit for server accordingly.
     */
    private TextWatcher dataSearchTextWatcher() {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable inputSearch) {
                /**As soon as user starts typing take the scroll to top **/
                mSearchDataName = inputSearch.toString();
                mLnr_invite_member.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                if (!((CommunitiesDetailActivity) getActivity()).mIsDestroyed) {
                    mAdapter.getFilter().filter(mSearchDataName);
                }
            }
        };
    }

    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPullRefreshList.allListData(feedDetailList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            mAdapter.notifyDataSetChanged();
            if (!mPullRefreshList.isPullToRefresh()) {
                mLayoutManager.scrollToPositionWithOffset(mPullRefreshList.getFeedResponses().size() - feedDetailList.size(), 0);
            } else {
                mLayoutManager.scrollToPositionWithOffset(0, 0);
            }
        }
    }
    public void callAddOwner(int userid)
    {

        int communityid=(int)mFeedDetail.getIdOfEntityOrParticipant();
        CreateCommunityOwnerRequest createCommunityOwnerRequest=new CreateCommunityOwnerRequest();
        createCommunityOwnerRequest.setAppVersion("string");
        createCommunityOwnerRequest.setCloudMessagingId("String");
        createCommunityOwnerRequest.setCommunityId(communityid);
        createCommunityOwnerRequest.setDeviceUniqueId("String");
        createCommunityOwnerRequest.setLastScreenName("String");
        createCommunityOwnerRequest.setScreenName("String");
        createCommunityOwnerRequest.setUserId(userid);
        createCommunityPresenter.postCreateCommunityOwner(createCommunityOwnerRequest);
    }
    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {

    }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

    }



    public interface InviteOwnerActivityIntractionListner {
        void onErrorOccurence();
        void closeOwner();
    }



}