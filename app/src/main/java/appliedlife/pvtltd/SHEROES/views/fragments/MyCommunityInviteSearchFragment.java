package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 06-03-2017.
 */

public class MyCommunityInviteSearchFragment extends BaseFragment implements HomeView{
    private final String TAG = LogUtils.makeLogTag(CommunityInviteSearchFragment.class);
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.rv_invite_search_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_invite_search_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.li_invite_member)
    LinearLayout liInviteMember;
    @Bind(R.id.tv_invite_text)
    TextView tvInviteText;
    @Bind(R.id.tv_invite_user_only)
    TextView tvInviteUserOnly;
    @Bind(R.id.et_search_edit_text)
    public EditText mSearchEditText;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private FragmentIntractionWithActivityListner mHomeSearchActivityFragmentIntractionWithActivityListner;
    private FragmentListRefreshData mFragmentListRefreshData;
    private Handler mHandler = new Handler();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof FragmentIntractionWithActivityListner) {
                mHomeSearchActivityFragmentIntractionWithActivityListner = (FragmentIntractionWithActivityListner) getActivity();
            }
        } catch (Fragment.InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.mycommunity_invite_search, container, false);
        ButterKnife.bind(this,view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ALL_SEARCH, AppConstants.EMPTY_STRING);
        mHomePresenter.attachView(this);
        tvInviteText.setText(getString(R.string.ID_SEARCH));
        editTextWatcher();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (HomeActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        liInviteMember.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if(StringUtil.isNotEmptyCollection(feedDetailList)&&mAdapter!=null) {
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            mAdapter.setSheroesGenericListData(feedDetailList);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            liInviteMember.setVisibility(View.VISIBLE);
            tvInviteText.setText(getString(R.string.ID_INVITE_MEMBER_COMMUNITY));
            tvInviteUserOnly.setText(getString(R.string.ID_INVITE_USER_TEXT));
        }
    }

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {

    }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {
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
    public void showError(String errorMsg) {
        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog();
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
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
             /*   mSearchDataName = inputSearch.toString();
                if (!((HomeSearchActivity) getActivity()).mIsDestroyed) {
                    mAdapter.getFilter().filter(mSearchDataName);
                }*/

                if (StringUtil.isNotNullOrEmptyString(inputSearch.toString())&&inputSearch.toString().length()>AppConstants.THREE_CONSTANT)
                {
                    liInviteMember.setVisibility(View.GONE);
                    mSearchDataName = inputSearch.toString();
                    /**hitting the servers to get data if length is greater than threshold defined **/
                    mHandler.removeCallbacks(mFilterTask);
                    mHandler.postDelayed(mFilterTask, AppConstants.SEARCH_CONSTANT_DELAY);
                }
            }
        };
    }
    /**
     * Runnable use to make network call on every character change while search for city name.
     */
    Runnable mFilterTask = new Runnable()
    {
        @Override
        public void run()
        {
            if (!isDetached())
            {
                mSearchDataName = mSearchDataName.trim().replaceAll(AppConstants.SPACE, AppConstants.EMPTY_STRING);
                mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.USER_SUB_TYPE,mSearchDataName ,mFragmentListRefreshData.getPageNo(),AppConstants.ALL_SEARCH));
            }
        }
    };
    @OnClick(R.id.tv_back_community)
    public void inviteSearchBack()
    {
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
