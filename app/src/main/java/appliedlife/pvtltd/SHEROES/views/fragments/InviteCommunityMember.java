package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
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
import appliedlife.pvtltd.SHEROES.views.cutomeviews.HidingScrollListener;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;

/**
 * Created by Ajit Kumar on 11-03-2017.
 */

public class InviteCommunityMember extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(InviteCommunityMember.class);
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.rv_invite_member_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.pb_invite_member_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.li_invite_member)
    LinearLayout liInviteMember;
    @Bind(R.id.tv_invite_member_text)
    TextView tvInviteText;
    @Bind(R.id.tv_invite_user_only)
    TextView tvInviteUserOnly;
    @Bind(R.id.et_search_edit_text)
    public EditText mSearchEditText;
    @Bind(R.id.tv_added_member)
    TextView tvAddedMember;
    private SwipPullRefreshList mPullRefreshList;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private FragmentListRefreshData mFragmentListRefreshData;
    private Handler mHandler = new Handler();
    private List<Long> mUserIdForAddMember = new ArrayList<>();
    private FeedDetail mFeedDetail;
    private int mPageNo = AppConstants.ONE_CONSTANT;
    private LinearLayoutManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.my_community_invite_member, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
        mPullRefreshList = new SwipPullRefreshList();
        mPullRefreshList.setPullToRefresh(false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFeedDetail = bundle.getParcelable(AppConstants.COMMUNITIES_DETAIL);
        }
        tvInviteText.setText(getString(R.string.ID_INVITE_MEMBER_COMMUNITY));
        mSearchEditText.setHint(getString(R.string.ID_SEARCH_USER));

        editTextWatcher();
        liInviteMember.setVisibility(View.VISIBLE);
        return view;
    }
    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        liInviteMember.setVisibility(View.GONE);
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            mPageNo = mFragmentListRefreshData.getPageNo();
            mFragmentListRefreshData.setPageNo(++mPageNo);
            mPullRefreshList.allListData(feedDetailList);
            mAdapter.setSheroesGenericListData(mPullRefreshList.getFeedResponses());
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            mAdapter.notifyDataSetChanged();
            if (!mPullRefreshList.isPullToRefresh()) {
                manager.scrollToPosition(mPullRefreshList.getFeedResponses().size() - feedDetailList.size() - 1);
            } else {
                manager.scrollToPositionWithOffset(0, 0);
            }
        } else if (!StringUtil.isNotEmptyCollection(mPullRefreshList.getFeedResponses())) {
            //mLiNoResult.setVisibility(View.VISIBLE);
        } else {
            liInviteMember.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(errorMsg, feedParticipationEnum);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomePresenter.detachView();
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

                if (StringUtil.isNotNullOrEmptyString(inputSearch.toString()) && inputSearch.toString().length() > AppConstants.THREE_CONSTANT) {
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
    Runnable mFilterTask = new Runnable() {
        @Override
        public void run() {
            if (!isDetached()) {
                mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT,AppConstants.INVITE_MEMBER, mFeedDetail.getIdOfEntityOrParticipant(),mSearchDataName);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter = new GenericRecyclerViewAdapter(getActivity(), (CommunitiesDetailActivity) getActivity());
                manager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addOnScrollListener(new HidingScrollListener(mHomePresenter, mRecyclerView, manager, mFragmentListRefreshData) {
                    @Override
                    public void onHide() {
                    }

                    @Override
                    public void onShow() {
                    }

                    @Override
                    public void dismissReactions() {
                    }
                });
                mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.USER_SUB_TYPE, mSearchDataName, mFragmentListRefreshData.getPageNo(), AppConstants.INVITE_MEMBER,mFeedDetail.getIdOfEntityOrParticipant(),AppConstants.INVITE_PAGE_SIZE));
            }
        }
    };
    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
        switch (feedParticipationEnum) {
            case JOIN_INVITE:
                joinSuccess(baseResponse);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + feedParticipationEnum);
        }
    }

    private void joinSuccess(BaseResponse baseResponse) {
        switch (baseResponse.getStatus()) {
            case AppConstants.SUCCESS:
                Toast.makeText(getActivity(),getString(R.string.ID_ADDED), Toast.LENGTH_SHORT).show();
                if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mUserIdForAddMember)) {
                    int count = mFeedDetail.getNoOfMembers();
                    count += mUserIdForAddMember.size();
                     mFeedDetail.setNoOfMembers(count);
                    ((CommunitiesDetailActivity) getActivity()).updateOpenAboutFragment(mFeedDetail);
                }
                break;
            case AppConstants.FAILED:
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(baseResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_JOIN_INVITE);
                break;
            default:
                ((CommunitiesDetailActivity)getActivity()).onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), ERROR_JOIN_INVITE);

        }
    }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

    }

    @OnClick(R.id.tv_back_community)
    public void inviteSearchBack() {
        dismiss();
    }

    @OnClick(R.id.tv_invite_post_submit)
    public void inviteSubmit() {
        if (StringUtil.isNotEmptyCollection(mUserIdForAddMember) && null != mFeedDetail) {
            mHomePresenter.communityJoinFromPresenter(mAppUtils.communityRequestBuilder(mUserIdForAddMember, mFeedDetail.getIdOfEntityOrParticipant(), AppConstants.OPEN_COMMUNITY));
        }
    }

    public void onAddMemberClick(FeedDetail feedDetail) {
        if (feedDetail.isLongPress()) {
            mUserIdForAddMember.add(feedDetail.getIdOfEntityOrParticipant());
        } else {
            mUserIdForAddMember.remove(feedDetail.getIdOfEntityOrParticipant());
        }
        switch (mUserIdForAddMember.size())
        {
            case AppConstants.NO_REACTION_CONSTANT:
                tvAddedMember.setText(AppConstants.SPACE);
                break;
            case AppConstants.ONE_CONSTANT:
                tvAddedMember.setText(getString(R.string.ID_ADDED) + AppConstants.SPACE + +mUserIdForAddMember.size() + AppConstants.SPACE + getString(R.string.ID_MEMBERS).substring(0, getString(R.string.ID_MEMBERS).length() - 1));
                break;
            default:
                tvAddedMember.setText(getString(R.string.ID_ADDED) + AppConstants.SPACE + mUserIdForAddMember.size() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }
}
