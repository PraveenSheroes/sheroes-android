package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentListRefreshData;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.InviteFragmentListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 11-03-2017.
 */

public class InviteCommunityMember extends BaseFragment {
    private final String TAG = LogUtils.makeLogTag(MyCommunityInviteMemberFragment.class);
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
    @Bind(R.id.tv_invite_post_submit)
    TextView mtv_invite_post_submit;
    private String mSearchDataName = AppConstants.EMPTY_STRING;
    private GenericRecyclerViewAdapter mAdapter;
    private InviteFragmentListener mHomeSearchActivityFragmentIntractionWithActivityListner;
    private FragmentListRefreshData mFragmentListRefreshData;
    private Handler mHandler = new Handler();
    private List<Long> mUserIdForAddMember = new ArrayList<>();
    private FeedDetail mFeedDetail;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof InviteFragmentListener) {
                mHomeSearchActivityFragmentIntractionWithActivityListner = (InviteFragmentListener) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.my_community_invite_member, container, false);
        ButterKnife.bind(this, view);
        mFragmentListRefreshData = new FragmentListRefreshData(AppConstants.ONE_CONSTANT, AppConstants.ALL_SEARCH, AppConstants.EMPTY_STRING);
        mHomePresenter.attachView(this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFeedDetail = bundle.getParcelable(AppConstants.COMMUNITIES_DETAIL);
        }
        mSearchEditText.setHint(R.string.ID_SEARCH_USER);
        editTextWatcher();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GenericRecyclerViewAdapter(getContext(), (CommunitiesDetailActivity) getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        liInviteMember.setVisibility(View.VISIBLE);
        super.setAllInitializationForFeeds(mFragmentListRefreshData, mAdapter, manager, mRecyclerView, mHomePresenter, mAppUtils, mProgressBar);
        return view;
    }
    public void callAddMember(List<Long> muser_ids,Long community_id)
    {
        List<Long> muser_id = new ArrayList<>();
        for(int i=0;i<muser_ids.size()-1;i++)
        {
            muser_id.add(i,muser_ids.get(i+1));
        }
        CommunityRequest communityRequest=new CommunityRequest();
        communityRequest.setUserId(muser_id);
        communityRequest.setDeviceUniqueId("String");
        communityRequest.setScreenName("String");
        communityRequest.setLastScreenName("String");
        communityRequest.setCommunityId(community_id);
        communityRequest.setAppVersion("String");
        communityRequest.setCloudMessagingId("String");

      /*  int communityid=(int)mFeedDetail.getIdOfEntityOrParticipant();
        CreateCommunityOwnerRequest createCommunityOwnerRequest=new CreateCommunityOwnerRequest();
        createCommunityOwnerRequest.setAppVersion("string");
        createCommunityOwnerRequest.setCloudMessagingId("String");
        createCommunityOwnerRequest.setCommunityId(communityid);
        createCommunityOwnerRequest.setDeviceUniqueId("String");
        createCommunityOwnerRequest.setLastScreenName("String");
        createCommunityOwnerRequest.setScreenName("String");
        createCommunityOwnerRequest.setUserId(userid);
        createCommunityPresenter.postCreateCommunityOwner(createCommunityOwnerRequest);*/
        mHomePresenter.communityJoinFromPresenter(communityRequest);
    }
    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {
        if (StringUtil.isNotEmptyCollection(feedDetailList) && mAdapter != null) {
            liInviteMember.setVisibility(View.GONE);
            mAdapter.setCallForRecycler(AppConstants.FEED_SUB_TYPE);
            mAdapter.setSheroesGenericListData(feedDetailList);
            mAdapter.notifyDataSetChanged();
        } else {
            liInviteMember.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showError(String errorMsg,int i) {
        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog();
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
                mSearchDataName = mSearchDataName.trim().replaceAll(AppConstants.SPACE, AppConstants.EMPTY_STRING);
                mHomePresenter.getFeedFromPresenter(mAppUtils.searchRequestBuilder(AppConstants.USER_SUB_TYPE, mSearchDataName, mFragmentListRefreshData.getPageNo(), AppConstants.ALL_SEARCH));
            }
        }
    };

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {
        switch (successFrom) {
            case AppConstants.ONE_CONSTANT:
                //ToDO:; need to verify dialog;
                Toast.makeText(getContext(), "Add members", Toast.LENGTH_SHORT).show();
                inviteSearchBack();
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + successFrom);
        }
    }

    @OnClick(R.id.tv_back_community)
    public void inviteSearchBack() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @OnClick(R.id.tv_invite_post_submit)
    public void inviteSubmit() {
        mHomeSearchActivityFragmentIntractionWithActivityListner.callInviteMembers();
       /* if (StringUtil.isNotEmptyCollection(mUserIdForAddMember) && null != mFeedDetail) {
            mHomePresenter.communityJoinFromPresenter(mAppUtils.communityRequestBuilder(mUserIdForAddMember, mFeedDetail.getIdOfEntityOrParticipant()));
        }*/

    }

    public void onAddMemberClick(FeedDetail feedDetail) {
        if (feedDetail.isLongPress()) {
            mUserIdForAddMember.add(feedDetail.getIdOfEntityOrParticipant());
        } else {
            mUserIdForAddMember.remove(feedDetail.getIdOfEntityOrParticipant());
        }
        switch (mUserIdForAddMember.size()) {
            case AppConstants.NO_REACTION_CONSTANT:
                tvAddedMember.setText(AppConstants.SPACE);
                break;
            case AppConstants.ONE_CONSTANT:
                tvAddedMember.setText(getString(R.string.ID_ADDED) + AppConstants.SPACE + +mUserIdForAddMember.size() + AppConstants.SPACE + getString(R.string.ID_MEMBERS).substring(0, getString(R.string.ID_MEMBERS).length() - 1));
                break;
            default:
                tvAddedMember.setText(getString(R.string.ID_ADDED) + AppConstants.SPACE + mUserIdForAddMember.size() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
        }
        if (mUserIdForAddMember.size() == AppConstants.ONE_CONSTANT) {
            tvAddedMember.setText(getString(R.string.ID_ADDED) + AppConstants.SPACE + +mUserIdForAddMember.size() + AppConstants.SPACE + getString(R.string.ID_MEMBERS).substring(0, getString(R.string.ID_MEMBERS).length() - 1));
        } else {
            tvAddedMember.setText(getString(R.string.ID_ADDED) + AppConstants.SPACE + mUserIdForAddMember.size() + AppConstants.SPACE + getString(R.string.ID_MEMBERS));
        }
    }
}