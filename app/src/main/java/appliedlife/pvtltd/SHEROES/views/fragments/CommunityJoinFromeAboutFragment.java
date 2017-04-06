package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_JOIN_INVITE;

/**
 * Created by SHEROES-TECH on 01-04-2017.
 */

public class CommunityJoinFromeAboutFragment extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(CommunityJoinRegionDialogFragment.class);
    @Inject
    Preference<LoginResponse> userPreference;
    private FeedDetail mFeedDetail;
    @Inject
    HomePresenter mHomePresenter;
    @Inject
    AppUtils mAppUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.join_community_region_dialog, container, false);
        ButterKnife.bind(this, view);
        mHomePresenter.attachView(this);
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
                dismiss();
            }
        };
    }

    @OnClick(R.id.tv_higly_intreseted)
    public void highlyOnClick() {
        joinNewUser(getString(R.string.ID_COMMUNITY_JOIN_REGION1));
    }

    @OnClick(R.id.tv_already_member)
    public void alreadyOnClick() {
        joinNewUser(getString(R.string.ID_COMMUNITY_JOIN_REGION2));
    }

    private void joinNewUser(String reasonToJoin) {
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
            List<Long> userIdList = new ArrayList();
            userIdList.add((long) userPreference.get().getUserSummary().getUserId());
            mHomePresenter.communityJoinFromPresenter(mAppUtils.communityRequestBuilder(userIdList, mFeedDetail.getIdOfEntityOrParticipant(), reasonToJoin));
            getDialog().cancel();

        }
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {

    }


    @Override
    public void getTagListSuccess(List<Doc> feedDetailList) {

    }

    @Override
    public void getSuccessForAllResponse(String success, FeedParticipationEnum feedParticipationEnum) {
        switch (success) {
            case AppConstants.SUCCESS:
                mFeedDetail.setRequestPending(true);
                mHomeSearchActivityFragmentIntractionWithActivityListner.onSuccessResult(success, mFeedDetail);
                break;
            case AppConstants.FAILED:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_JOIN_INVITE);
                break;
            default:
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.HTTP_401_UNAUTHORIZED, ERROR_JOIN_INVITE);
        }

    }

    @Override
    public void getDB(List<RecentSearchData> recentSearchDatas) {

    }
}

