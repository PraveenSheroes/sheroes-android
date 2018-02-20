package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.AllContactListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.InviteFriendViewPresenterImp;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.EndlessRecyclerViewScrollListener;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.InviteFriendSuggestedAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IInviteFriendView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 13/02/18.
 */

public class SuggestedFriendFragment extends BaseFragment implements ContactDetailCallBack, IInviteFriendView {
    private static final String SCREEN_LABEL = "Suggested Friend Screen";
    private final String TAG = LogUtils.makeLogTag(SuggestedFriendFragment.class);
    private final String SUGGESTED_LIST_URL ="http://testservicesconf.sheroes.in/participant/user/app_user_contacts_details?fetch_type=SHEROES";

    //region Static variables
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;
    //endregion
    @Bind(R.id.swipe_suggested_friend)
    SwipeRefreshLayout mSwipeRefresh;
    private EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    @Bind(R.id.rv_suggested_friend_list)
    EmptyRecyclerView mFeedRecyclerView;
    @Bind(R.id.empty_view)
    View emptyView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    private InviteFriendSuggestedAdapter mInviteFriendSuggestedAdapter;
    @Inject
    InviteFriendViewPresenterImp mInviteFriendViewPresenterImp;

    private boolean hasFeedEnded;

    public static SuggestedFriendFragment createInstance(String name) {
        SuggestedFriendFragment suggestedFriendFragment = new SuggestedFriendFragment();
        Bundle bundle = new Bundle();
        suggestedFriendFragment.setArguments(bundle);
        return suggestedFriendFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.suggested_friend_layout, container, false);
        ButterKnife.bind(this, view);
        mInviteFriendViewPresenterImp.attachView(this);
        Bundle bundle = getArguments();
        initViews();
        return view;
    }

    //region Private methods
    private void initViews() {
        mInviteFriendViewPresenterImp.setEndpointUrl(SUGGESTED_LIST_URL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFeedRecyclerView.setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) mFeedRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mInviteFriendSuggestedAdapter = new InviteFriendSuggestedAdapter(getContext(), this);
        mFeedRecyclerView.setAdapter(mInviteFriendSuggestedAdapter);
        mFeedRecyclerView.setEmptyViewWithImage(emptyView, getActivity().getResources().getString(R.string.contact_list_blank), R.drawable.ic_suggested_blank, "");
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (mInviteFriendViewPresenterImp.isSuggestedLoading() || hasFeedEnded) {
                    return;
                }
                mInviteFriendSuggestedAdapter.contactStartedLoading();
                mInviteFriendViewPresenterImp.fetchSuggestedUserDetailFromServer(InviteFriendViewPresenterImp.LOAD_MORE_REQUEST);
            }

        };
        mFeedRecyclerView.addOnScrollListener(mEndlessRecyclerViewScrollListener);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mInviteFriendViewPresenterImp.fetchSuggestedUserDetailFromServer(InviteFriendViewPresenterImp.NORMAL_REQUEST);
            }
        });
       getAllSuggestedContacts();
    }

    //endregion

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void onContactClicked(UserContactDetail contactDetail, View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_invite_friend:

                break;
            default:
        }
    }
    @Override
    public void startProgressBar() {
        if (mSwipeRefresh == null) {
            return;
        }
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                    if (mSwipeRefresh != null) {
                        mSwipeRefresh.setRefreshing(true);
                        mSwipeRefresh.setColorSchemeResources(R.color.mentor_green, R.color.link_color, R.color.email);
                    }
            }
        });
    }

    @Override
    public void stopProgressBar() {
        mEndlessRecyclerViewScrollListener.finishLoading();
        if (mSwipeRefresh == null) {
            return;
        }
        mSwipeRefresh.setRefreshing(false);
        mInviteFriendSuggestedAdapter.contactsFinishedLoading();
    }
    @Override
    public void onSuggestedContactClicked(UserSolrObj userSolrObj, View view) {
        int id = view.getId();
            switch (id) {
                case R.id.btn_follow_friend:
                    followUnFollowRequest(userSolrObj);
                    break;
            }
    }
    public void followUnFollowRequest(UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        if (userSolrObj.isSolrIgnoreIsUserFollowed()) {
            mInviteFriendViewPresenterImp.getUnFollowFromPresenter(publicProfileListRequest,userSolrObj);
        } else {
            mInviteFriendViewPresenterImp.getFollowFromPresenter(publicProfileListRequest,userSolrObj);
        }
    }
    @Override
    public void showContacts(List<UserContactDetail> userContactDetailList) {

    }
    public void getAllSuggestedContacts()
    {
        mInviteFriendViewPresenterImp.fetchSuggestedUserDetailFromServer(InviteFriendViewPresenterImp.NORMAL_REQUEST);
    }
    @Override
    public void showUserDetail(List<UserSolrObj> userSolrObjList) {
        if (StringUtil.isNotEmptyCollection(userSolrObjList)) {
            emptyView.setVisibility(View.GONE);
            mInviteFriendSuggestedAdapter.setData(userSolrObjList);
            mInviteFriendSuggestedAdapter.notifyDataSetChanged();
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setContactUserListEnded(boolean contactUserListEnded) {
        this.hasFeedEnded = contactUserListEnded;
    }

    @Override
    public void addAllUserData(List<UserSolrObj> userSolrObjList) {
        mInviteFriendSuggestedAdapter.addAll(userSolrObjList);
    }

    @Override
    public void addAllUserContactData(List<UserContactDetail> userContactDetailList) {

    }

    @Override
    public void contactsFromServerAfterSyncFromPhoneData(AllContactListResponse allContactListResponse) {

    }

    @Override
    public void getFollowUnfollowResponse(UserSolrObj userSolrObj) {
        mInviteFriendSuggestedAdapter.notifyItemChanged(userSolrObj.getItemPosition(), userSolrObj);
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mInviteFriendViewPresenterImp;
    }
}