package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 09-02-2017.
 */

public class CommunityJoinRegionDialogFragment extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(CommunityJoinRegionDialogFragment.class);
    private FeedDetail mFeedDetail;
    private CloseListener mHomeActivityIntractionListner;
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    AppUtils mAppUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.join_community_region_dialog, container, false);
        ButterKnife.bind(this, view);
        mFeedDetail = getArguments().getParcelable(DISMISS_PARENT_ON_OK_OR_BACK);
        setCancelable(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                getDialog().dismiss();
            }
        };
    }

    @OnClick(R.id.tv_higly_intreseted)
    public void highlyOnClick() {
        Toast.makeText(getActivity(), "highly ", Toast.LENGTH_SHORT).show();
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add((long) userPreference.get().getUserSummary().getUserId());
            mHomePresenter.communityJoinFromPresenter(mAppUtils.communityRequestBuilder(userIdList, mFeedDetail.getIdOfEntityOrParticipant()));
        }
    }

    @OnClick(R.id.tv_already_member)
    public void alreadyOnClick() {
        Toast.makeText(getActivity(), "Already", Toast.LENGTH_SHORT).show();
        // getDialog().cancel();
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(List<FeedDetail> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(String success, int successFrom) {

    }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, int errorFor) {

    }



    public interface CloseListener {
        void onErrorOccurence();

        void onClose();
    }
}
