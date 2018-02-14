package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.InviteFriendViewPresenterImp;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.InviteFriendAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IInviteFriendView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.PERMISSIONS_REQUEST_READ_CONTACTS;

/**
 * Created by Praveen on 13/02/18.
 */

public class ContactListFragment extends BaseFragment implements ContactDetailCallBack,IInviteFriendView{
    private static final String SCREEN_LABEL = "Contact List Screen";
    private final String TAG = LogUtils.makeLogTag(ContactListFragment.class);

    //region Static variables
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    AppUtils mAppUtils;
    //endregion

    @Bind(R.id.rv_suggested_friend_list)
    EmptyRecyclerView recyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    private InviteFriendAdapter inviteFriendAdapter;
    @Inject
    InviteFriendViewPresenterImp mInviteFriendViewPresenterImp;

    public static ContactListFragment createInstance( String name) {
        ContactListFragment contactListFragment = new ContactListFragment();
        Bundle bundle = new Bundle();

        contactListFragment.setArguments(bundle);
        return contactListFragment;
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
    private void initViews()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        inviteFriendAdapter = new InviteFriendAdapter(getContext(), this);
        recyclerView.setAdapter(inviteFriendAdapter);
         getUserContacts(getContext());
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
    public void onContactClicked(UserContactDetail contactDetail) {

    }

    @Override
    public void showContacts(List<UserContactDetail> userContactDetailList) {
        progressBar.setVisibility(View.GONE);
        inviteFriendAdapter.setData(userContactDetailList);
        inviteFriendAdapter.notifyDataSetChanged();
    }
}
