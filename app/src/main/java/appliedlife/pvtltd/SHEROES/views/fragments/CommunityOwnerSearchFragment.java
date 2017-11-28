package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.f2prateek.rx.preferences.Preference;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.models.entities.home.SwipPullRefreshList;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 15-02-2017.
 */

public class CommunityOwnerSearchFragment extends BaseFragment implements HomeView{
    private static final String SCREEN_LABEL = "Community Owner Search Screen";
    private final String TAG = LogUtils.makeLogTag(CommunityOwnerSearchFragment.class);
    @Inject
    Preference<LoginResponse> mUserPreference;
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
    @Bind(R.id.lnr_invite_member)
    LinearLayout mLnr_invite_member;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private SwipPullRefreshList mPullRefreshList;
    private LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.owner_search_layout, container, false);
        ButterKnife.bind(this, view);
        if(null!=getArguments())
        {
            mFeedDetail = Parcels.unwrap(getArguments().getParcelable(AppConstants.COMMUNITY_DETAIL));
        }
        mHomePresenter.attachView(this);
        mAppUtils = AppUtils.getInstance();
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.JOB_FRAGMENT, AppConstants.NO_REACTION_CONSTANT);
        mSearchEditText.setHint(getString(R.string.ID_SEARCH_USER));
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
    @OnClick(R.id.tv_back_owner_search)
    public void communityTagBack()
    {
        ((CommunitiesDetailActivity)getActivity()).closeOwner();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
    }
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
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList=feedResponsePojo.getFeedDetails();
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

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}