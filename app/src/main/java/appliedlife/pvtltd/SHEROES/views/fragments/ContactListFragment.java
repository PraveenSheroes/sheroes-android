package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.AllContactListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.invitecontact.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.InviteFriendViewPresenterImp;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.EndlessRecyclerViewScrollListener;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.InviteFriendActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.InviteFriendAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IInviteFriendView;
import butterknife.Bind;
import butterknife.ButterKnife;
import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * Created by Praveen on 13/02/18.
 */

public class ContactListFragment extends BaseFragment implements ContactDetailCallBack, IInviteFriendView {
    private static final String SCREEN_LABEL = "Invite Friends Screen";
    private final String TAG = LogUtils.makeLogTag(ContactListFragment.class);
    private final String CONTACT_LIST_URL = "http://testservicesconf.sheroes.in/participant/user/app_user_contacts_details?fetch_type=NON_SHEROES";

    //region Static variables
    @Inject
    Preference<LoginResponse> mUserPreference;
    public InviteFriendAdapter mInviteFriendAdapter;
    @Inject
    InviteFriendViewPresenterImp mInviteFriendViewPresenterImp;
    @Inject
    AppUtils mAppUtils;
    //endregion

   //region View variables
    @Bind(R.id.swipe_contact_friend)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.rv_contact_friend_list)
    EmptyRecyclerView mFeedRecyclerView;
    private EndlessRecyclerViewScrollListener mEndlessRecyclerViewScrollListener;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.empty_view)
    View emptyView;
    //endregion

    private boolean hasFeedEnded;

    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;

    //region Member variables
    private String mSmsShareLink;
    private boolean syncContact;
    //endregion

    public static ContactListFragment createInstance(String name) {
        ContactListFragment contactListFragment = new ContactListFragment();
        Bundle bundle = new Bundle();
        contactListFragment.setArguments(bundle);
        return contactListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.contact_list_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        mInviteFriendViewPresenterImp.attachView(this);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        Bundle bundle = getArguments();
        initViews();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            AnalyticsManager.trackScreenView(getScreenName(), getExtraProperties());
        }
    }

    //region Private methods
    private void initViews() {
        if (CommonUtil.getTimeForContacts(AppConstants.CONTACT_SYNC_TIME_PREF) > 0) {
            long daysDifference = System.currentTimeMillis() - CommonUtil.getTimeForContacts(AppConstants.CONTACT_SYNC_TIME_PREF);
            if (daysDifference >= AppConstants.SAVED_DAYS_TIME) {
                syncContact = true;
                long syncTime = System.currentTimeMillis();
                CommonUtil.setTimeForContacts(AppConstants.CONTACT_SYNC_TIME_PREF, syncTime);
            }
        } else {
            syncContact = true;
            if(getActivity()!=null&&getActivity() instanceof InviteFriendActivity)
            {
                ((InviteFriendActivity)getActivity()).mViewPager.setCurrentItem(1);
            }
        }
        mInviteFriendViewPresenterImp.setEndpointUrl(CONTACT_LIST_URL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFeedRecyclerView.setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) mFeedRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mInviteFriendAdapter = new InviteFriendAdapter(getContext(), this);
        mFeedRecyclerView.setAdapter(mInviteFriendAdapter);
        mFeedRecyclerView.setEmptyViewWithImage(emptyView, getActivity().getResources().getString(R.string.contact_list_blank), R.drawable.ic_suggested_blank, "");
        mEndlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (!syncContact) {
                    if (mInviteFriendViewPresenterImp.isContactLoading() || hasFeedEnded) {
                        return;
                    }
                    mInviteFriendAdapter.contactStartedLoading();
                    mInviteFriendViewPresenterImp.fetchUserDetailFromServer(InviteFriendViewPresenterImp.LOAD_MORE_REQUEST);
                }
            }
        };
        mFeedRecyclerView.addOnScrollListener(mEndlessRecyclerViewScrollListener);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!syncContact) {
                    mInviteFriendViewPresenterImp.fetchUserDetailFromServer(InviteFriendViewPresenterImp.NORMAL_REQUEST);
                }
            }
        });
        if (!syncContact) {
            mInviteFriendViewPresenterImp.fetchUserDetailFromServer(InviteFriendViewPresenterImp.NORMAL_REQUEST);
        } else {
            getUserContacts(getContext());
        }
        if (null != mUserPreference && mUserPreference.isSet()) {
            BranchUniversalObject mSmsBranchUniversalObject = new BranchUniversalObject()
                    .setCanonicalIdentifier("invite/sms")
                    .setTitle(getString(R.string.invite_friend_url_title))
                    .setContentDescription(getString(R.string.invite_friend_url_description))
                    .addContentMetadata("userId", String.valueOf(mUserPreference.get().getUserSummary().getUserId()));

            LinkProperties mSmsLinkProperties = new LinkProperties()
                    .setChannel("sms")
                    .setFeature("sharing");

            mSmsBranchUniversalObject.generateShortUrl(getActivity(), mSmsLinkProperties, new Branch.BranchLinkCreateListener() {
                @Override
                public void onLinkCreate(String url, BranchError error) {
                    if (error == null) {
                        mSmsShareLink = url;
                    }
                }
            });
            if (StringUtil.isNotNullOrEmptyString(mSmsShareLink)&&StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getAppShareUrl()) && !mSmsShareLink.equalsIgnoreCase(mUserPreference.get().getUserSummary().getAppShareUrl())) {
                mInviteFriendViewPresenterImp.updateInviteUrlFromPresenter(mSmsShareLink);
            }
        }
    }


    private void getUserContacts(Context context) {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                long syncTime = System.currentTimeMillis();
                CommonUtil.setTimeForContacts(AppConstants.CONTACT_SYNC_TIME_PREF, syncTime);
                mInviteFriendViewPresenterImp.getContactsFromMobile(getContext());
            }
        }
    }

    private boolean whatsAppInstalledOrNot(String uri) {
        PackageManager pm = getContext().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    //endregion

    //region Public methods
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    long syncTime = System.currentTimeMillis();
                    CommonUtil.setTimeForContacts(AppConstants.CONTACT_SYNC_TIME_PREF, syncTime);
                    mInviteFriendViewPresenterImp.getContactsFromMobile(getContext());
                    allowedContactSynEvent();
                }else
                {
                    deniedContactSyncEvent();
                }
                break;
            }
        }
    }

    private void allowedContactSynEvent() {
        Event event =  Event.CONTACT_SYNC_ALLOWED ;
        AnalyticsManager.trackEvent(event,getScreenName(), null);
    }
    private void deniedContactSyncEvent() {
        Event event = Event.CONTACT_SYNC_DENIED;
        AnalyticsManager.trackEvent(event,getScreenName(), null);
    }
    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void onContactClicked(UserContactDetail contactDetail, View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_invite_friend:
                if (contactDetail.getPhoneNumber().size() > 0) {
                    String whatsAppNumber = contactDetail.getPhoneNumber().get(0);
                    if (!whatsAppNumber.contains("91")) {
                        whatsAppNumber = "91" + whatsAppNumber;
                    }
                    whatsAppNumber = whatsAppNumber.replace("+", "").replace(" ", "");
                    boolean isWhatsappInstalled = whatsAppInstalledOrNot(AppConstants.WHATS_APP);
                    if (isWhatsappInstalled) {
                        Intent sendIntent = new Intent("android.intent.action.MAIN");
                        //when user want only conversation
                        // sendIntent.setComponent(new ComponentName(AppConstants.WHATS_APP, "com.whatsapp.Conversation"));
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.setPackage(AppConstants.WHATS_APP);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(whatsAppNumber) + "@s.whatsapp.net");//phone number without "+" prefix
                        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_friend_request_to_join) + mSmsShareLink);
                        startActivity(sendIntent);
                         moEngageUtills.entityMoEngageShareCard(mMoEHelper, payloadBuilder, "Invite friend", "Invite Friend", Long.parseLong(whatsAppNumber), contactDetail.getName(), getScreenName(), "", contactDetail.getName(), getScreenName(), contactDetail.getItemPosition());
                       //  AnalyticsManager.trackPostAction(Event.POST_SHARED, mFeedDetail, getScreenName());
                    } else {
                        Uri uri = Uri.parse("market://details?id=com.whatsapp");
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        Toast.makeText(getContext(), getString(R.string.whats_app_not_installed), Toast.LENGTH_SHORT).show();
                        startActivity(goToMarket);
                    }
                }
                break;
            default:
        }
    }

    @Override
    public void onSuggestedContactClicked(UserSolrObj userSolrObj, View view) {

    }


    public void searchContactInList(String contactName) {
        mInviteFriendAdapter.getFilter().filter(contactName);
    }

    @Override
    public void showContacts(List<UserContactDetail> userContactDetailList) {
        if (StringUtil.isNotEmptyCollection(userContactDetailList)) {
            emptyView.setVisibility(View.GONE);
            mInviteFriendAdapter.setData(userContactDetailList);
            mInviteFriendAdapter.notifyDataSetChanged();
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
        if (syncContact) {
            syncContact = false;
            mInviteFriendViewPresenterImp.syncFriendsToServer(userContactDetailList);
        }
    }

    @Override
    public void showUserDetail(List<UserSolrObj> userSolrObjList) {

    }

    @Override
    public void setContactUserListEnded(boolean contactUserListEnded) {
        this.hasFeedEnded = contactUserListEnded;
    }

    @Override
    public void addAllUserData(List<UserSolrObj> userSolrObjList) {

    }

    @Override
    public void addAllUserContactData(List<UserContactDetail> userContactDetailList) {
        mInviteFriendAdapter.addAll(userContactDetailList);
    }

    @Override
    public void contactsFromServerAfterSyncFromPhoneData(AllContactListResponse allContactListResponse) {
        mInviteFriendViewPresenterImp.fetchUserDetailFromServer(InviteFriendViewPresenterImp.NORMAL_REQUEST);
        if (null != getActivity() && getActivity() instanceof InviteFriendActivity) {
            ((InviteFriendActivity) getActivity()).dataRequestForFragment(allContactListResponse);
        }
    }

    @Override
    public void getFollowUnfollowResponse(UserSolrObj userSolrObj) {

    }

    @Override
    public void startProgressBar() {
        if (mSwipeRefresh == null) {
            return;
        }
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                if (!syncContact) {
                    if (mSwipeRefresh != null) {
                        mSwipeRefresh.setRefreshing(true);
                        mSwipeRefresh.setColorSchemeResources(R.color.mentor_green, R.color.link_color, R.color.email);
                    }
                }
            }
        });
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public void stopProgressBar() {
        mEndlessRecyclerViewScrollListener.finishLoading();
        if (mSwipeRefresh == null) {
            return;
        }
        mSwipeRefresh.setRefreshing(false);
        mInviteFriendAdapter.contactsFinishedLoading();
    }
    //endregion
    @Override
    protected SheroesPresenter getPresenter() {
        return mInviteFriendViewPresenterImp;
    }
}
