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

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
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
  /*  private Observable<Void> getUserPhoneContactsAndSend() {

        return Observable.create(new Observable.OnSubscribe<Void>() {

            @Override
            public void call(Subscriber<? super Void> subscriber) {
                UserPhoneContactsListRequest userPhoneContactsListRequest = new UserPhoneContactsListRequest();
                List<UserContactDetail> userContactDetailsList = new ArrayList<>();
                UserContactDetail userContactDetail = null;
                Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                Cursor cursor = ((HomeActivity) getActivity()).getContentResolver().query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID}, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                if (cursor != null && cursor.getCount() > 0) {
                    try {
                        cursor.moveToFirst();
                        while (cursor.isAfterLast() == false) {
                            String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                            userContactDetail = new UserContactDetail(contactName, contactNumber);
                            if (userContactDetail != null) {
                                userContactDetailsList.add(userContactDetail);
                            }
                            userContactDetail = null;
                            cursor.moveToNext();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    } finally {
                        cursor.close();
                    }
                }
                userPhoneContactsListRequest.setContactDetailList(userContactDetailsList);
                subscriber.onCompleted();
            }
        });
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mInviteFriendViewPresenterImp.getContactsFromMobile(getContext());
                }
                return;
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
        inviteFriendAdapter.setData(userContactDetailList);
        inviteFriendAdapter.notifyDataSetChanged();
    }
}
