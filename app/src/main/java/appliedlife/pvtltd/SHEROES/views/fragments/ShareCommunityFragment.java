package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CreateCommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 30-01-2017.
 */

public class ShareCommunityFragment extends BaseFragment implements CreateCommunityView, EditNameDialogListener {
    private final String TAG = LogUtils.makeLogTag(ShareCommunityFragment.class);
    @Bind(R.id.tv_close_community)
    TextView mTvCloseCommunity;
    @Bind(R.id.tv_share_via_social_media)
    TextView mTvShareviasocialmedia;
    @Bind(R.id.tv_share_community_title)
    TextView mTvShareCommunityTitle;
    @Bind(R.id.tv_community_title)
    TextView mTvCommunityTitle;
    @Bind(R.id.et_share_community_via_email)
    EditText mEtShareViaEmail;
    private FeedDetail mFeedDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.community_share_fragment, container, false);
        ButterKnife.bind(this, view);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.SHARE);
        }
        if (null != mFeedDetail && StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
            mTvShareCommunityTitle.setText(mFeedDetail.getNameOrTitle());
        }
        mTvCommunityTitle.setText(getString(R.string.ID_SHARE));
        //Fabric.with(getActivity(), new Crashlytics());

        return view;
    }

    @OnClick(R.id.tv_share_via_social_media)
    public void socialShareFunction() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mFeedDetail.getDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
    }
    @OnClick(R.id.tv_share)
    public void shareViaEmail() {
        if (StringUtil.isNotNullOrEmptyString(mEtShareViaEmail.getText().toString())) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType(AppConstants.SHARE_MENU_TYPE);
            intent.putExtra(Intent.EXTRA_TEXT, mFeedDetail.getDeepLinkUrl());
            startActivity(Intent.createChooser(intent, AppConstants.SHARE));
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(AppConstants.MAIL_TO, mEtShareViaEmail.getText().toString(), null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.ID_COMM_SHARE_EMAIL_LINK));
            emailIntent.putExtra(Intent.EXTRA_TEXT, mFeedDetail.getDeepLinkUrl());
            startActivity(Intent.createChooser(emailIntent, AppConstants.EMAIL_OPTION));
        } else {
            Toast.makeText(getContext(), getString(R.string.ID_EMAIL_HINT_TEXT), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_close_community)
    public void onCloseClick() {
        ((CommunitiesDetailActivity) getActivity()).onShareClose();
    }

    @Override
    public void getCreateCommunityResponse(LoginResponse loginResponse) {

    }

    public interface ShareCommunityActivityIntractionListner {
        void onErrorOccurence();

        void onShareClose();
    }

    @Override
    public void dialogValue(String dilogval) {

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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void onFinishEditDialog(String inputText) {
        LogUtils.info("value", inputText);
    }

}
