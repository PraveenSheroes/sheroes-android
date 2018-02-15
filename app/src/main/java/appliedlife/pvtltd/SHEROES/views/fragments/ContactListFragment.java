package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.InviteFriendViewPresenterImp;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
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
    private static final String SCREEN_LABEL = "Contact List Screen";
    private final String TAG = LogUtils.makeLogTag(ContactListFragment.class);

    //region Static variables
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;
    //endregion

    @Bind(R.id.rv_contact_friend_list)
    EmptyRecyclerView recyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.empty_view)
    View emptyView;
    private InviteFriendAdapter inviteFriendAdapter;
    @Inject
    InviteFriendViewPresenterImp mInviteFriendViewPresenterImp;

    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;

    //region Member variables
    private String mSmsShareLink;
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

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        inviteFriendAdapter = new InviteFriendAdapter(getContext(), this);
        recyclerView.setAdapter(inviteFriendAdapter);
        recyclerView.setEmptyViewWithImage(emptyView, getActivity().getResources().getString(R.string.contact_list_blank), R.drawable.ic_suggested_blank, "");
        getUserContacts(getContext());

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
        }
        setProgressBar(progressBar);
    }

    private void getUserContacts(Context context) {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                mInviteFriendViewPresenterImp.getContactsFromMobile(getContext());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mInviteFriendViewPresenterImp.getContactsFromMobile(getContext());
                }
                break;
            }
        }
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
                        // moEngageUtills.entityMoEngageShareCard(mMoEHelper, payloadBuilder, "Invite friend", MoEngageConstants.COMMUNITY, feedDetail.getEntityOrParticipantId(), feedDetail.getNameOrTitle(), MoEngageConstants.COMMUNITY_CATEGORY, getCardTag(feedDetail), feedDetail.getAuthorName(), AppConstants.FEED_SCREEN, feedDetail.getItemPosition());
                        // AnalyticsManager.trackPostAction(Event.POST_SHARED, mFeedDetail, getScreenName());
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

    @Override
    public void showContacts(List<UserContactDetail> userContactDetailList) {
        if (StringUtil.isNotEmptyCollection(userContactDetailList)) {
            emptyView.setVisibility(View.GONE);
            inviteFriendAdapter.setData(userContactDetailList);
            inviteFriendAdapter.notifyDataSetChanged();
        }else
        {
            emptyView.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }
    @Override
    protected SheroesPresenter getPresenter() {
        return mInviteFriendViewPresenterImp;
    }
}
