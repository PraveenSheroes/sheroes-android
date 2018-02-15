package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.ContactDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.contactdetail.UserContactDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.InviteFriendViewPresenterImp;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.InviteFriendAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.EmptyRecyclerView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IInviteFriendView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by Praveen on 13/02/18.
 */

public class SuggestedFriendFragment extends BaseFragment implements ContactDetailCallBack, IInviteFriendView {
    private static final String SCREEN_LABEL = "Suggested Friend Screen";
    private final String TAG = LogUtils.makeLogTag(SuggestedFriendFragment.class);

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

    public static SuggestedFriendFragment createInstance( String name) {
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

        Bundle bundle = getArguments();

        return view;
    }

    //region Private methods
    private void initViews()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        inviteFriendAdapter = new InviteFriendAdapter(getContext(), this);
        recyclerView.setAdapter(inviteFriendAdapter);
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
    public void showContacts(List<UserContactDetail> contactDetails) {

    }
}