package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactCallBackListener;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.ChampionUserProfile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.AllContactListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.InviteFriendViewPresenterImp;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.EndlessRecyclerViewScrollListener;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.AllContactActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.InviteFriendSuggestedAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IInviteFriendView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 13/02/18.
 */

public class SuggestedFriendFragment extends BaseFragment implements ContactDetailCallBack, IInviteFriendView {
    private static final String SCREEN_LABEL = "Suggested Friends Screen";
    private final String TAG = LogUtils.makeLogTag(SuggestedFriendFragment.class);
    private final String SUGGESTED_LIST_URL = "participant/user/app_user_contacts_details?fetch_type=SHEROES";

    //region Singleton variables
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    InviteFriendViewPresenterImp mInviteFriendViewPresenterImp;
    @Inject
    AppUtils mAppUtils;
    //endregion

    //region View variables
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
    //endregion

    //region Member variables
    private boolean hasFeedEnded;
    private String msg;
    private boolean isUserList;
    private ContactCallBackListener mContactCallBackListener;
    //endregion

    //region Static methods
    public static SuggestedFriendFragment createInstance(String name) {
        SuggestedFriendFragment suggestedFriendFragment = new SuggestedFriendFragment();
        Bundle bundle = new Bundle();
        suggestedFriendFragment.setArguments(bundle);
        return suggestedFriendFragment;
    }
    //endregion

    //region Public methods
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (getActivity() instanceof ContactCallBackListener)
            mContactCallBackListener = (ContactCallBackListener) getActivity();
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
        msg=getString(R.string.suggested_list_blank);
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (mInviteFriendViewPresenterImp.isSuggestedLoading() || hasFeedEnded) {
                    return;
                }
                // mInviteFriendSuggestedAdapter.contactStartedLoading();
                // mInviteFriendViewPresenterImp.fetchSuggestedUserDetailFromServer(InviteFriendViewPresenterImp.LOAD_MORE_REQUEST);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            AnalyticsManager.trackScreenView(getScreenName(), getExtraProperties());
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public void onContactClicked(UserContactDetail contactDetail, View view) {
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
                    mSwipeRefresh.setRefreshing(false);
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
    public void showEmptyScreen(String s) {
    }

    @Override
    public void onSuggestedContactClicked(UserSolrObj userSolrObj, View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_follow_friend:
                followUnFollowRequest(userSolrObj);
                break;
            case R.id.ll_suggested_contact:
                openMentorProfileDetail(userSolrObj);
                break;
            default:
        }
    }

    @Override
    public void showMsgOnSearch(String string) {
        if (StringUtil.isNotNullOrEmptyString(string)) {
            emptyView.setVisibility(View.VISIBLE);
            if (null != getActivity() && getActivity() instanceof AllContactActivity) {
                if(isUserList)
                {
                    msg=getString(R.string.no_search_contact);
                }else
                {
                    msg=getString(R.string.suggested_list_blank);
                }
            }
            mFeedRecyclerView.setEmptyViewWithImage(emptyView, msg, R.drawable.vector_suggested_blank, "");
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    private void openMentorProfileDetail(UserSolrObj userSolrObj) {
        ProfileActivity.navigateTo(getActivity(), userSolrObj, userSolrObj.getIdOfEntityOrParticipant(), userSolrObj.isAuthorMentor(), SCREEN_LABEL, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
    }

    public void followUnFollowRequest(UserSolrObj userSolrObj) {
        PublicProfileListRequest publicProfileListRequest = mAppUtils.pubicProfileRequestBuilder(1);
        publicProfileListRequest.setIdOfEntityParticipant(userSolrObj.getIdOfEntityOrParticipant());
        if (userSolrObj.isSolrIgnoreIsUserFollowed()) {
            mInviteFriendViewPresenterImp.getUnFollowFromPresenter(publicProfileListRequest, userSolrObj);
            unFollowUserEvent(userSolrObj);
        } else {
            mInviteFriendViewPresenterImp.getFollowFromPresenter(publicProfileListRequest, userSolrObj);
            followUserEvent(userSolrObj);
        }
    }

    private void followUserEvent(UserSolrObj mUserSolarObject) {
        Event event = Event.PROFILE_FOLLOWED;
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor((mUserSolarObject.getUserSubType()!=null && mUserSolarObject.getUserSubType().equalsIgnoreCase(AppConstants.CHAMPION_SUBTYPE)) || mUserSolarObject.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    private void unFollowUserEvent(UserSolrObj mUserSolarObject) {
        Event event = Event.PROFILE_UNFOLLOWED;
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserSolarObject.getIdOfEntityOrParticipant()))
                        .name(mUserSolarObject.getNameOrTitle())
                        .isMentor((mUserSolarObject.getUserSubType()!=null && mUserSolarObject.getUserSubType().equalsIgnoreCase(AppConstants.CHAMPION_SUBTYPE)) || mUserSolarObject.isAuthorMentor())
                        .build();
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    @Override
    public void showContacts(List<UserContactDetail> userContactDetailList) {
    }

    public void getAllSuggestedContacts() {
           mInviteFriendViewPresenterImp.fetchSuggestedUserDetailFromServer(InviteFriendViewPresenterImp.NORMAL_REQUEST);
    }

    public void searchSuggestedContactInList(String contactName) {
        mInviteFriendSuggestedAdapter.getFilter().filter(contactName);
    }

    public void refreshSuggestedList(UserSolrObj userSolrObj) {
        mInviteFriendSuggestedAdapter.setDataOnItemPosition(userSolrObj);
    }

    @Override
    public void showUserDetail(List<UserSolrObj> userSolrObjList) {
        if (StringUtil.isNotEmptyCollection(userSolrObjList)) {
            emptyView.setVisibility(View.GONE);
            mInviteFriendSuggestedAdapter.setData(userSolrObjList);
            mInviteFriendSuggestedAdapter.notifyDataSetChanged();

            if (mContactCallBackListener != null) {
                mContactCallBackListener.onUserDetailsCallBack();
            }
            isUserList=true;
        } else {
            mFeedRecyclerView.setEmptyViewWithImage(emptyView,msg , R.drawable.vector_suggested_blank, "");
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

    //endregion
    @Override
    protected SheroesPresenter getPresenter() {
        return mInviteFriendViewPresenterImp;
    }


    @Override
    public void getLogInResponse(LoginResponse loginResponse) {
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
    }

    @Override
    public void showNotificationList(BelNotificationListResponse bellNotificationResponse) {
    }

    @Override
    public void getNotificationReadCountSuccess(BaseResponse baseResponse, FeedParticipationEnum feedParticipationEnum) {
    }

    @Override
    public void onConfigFetched() {
    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {
    }
}