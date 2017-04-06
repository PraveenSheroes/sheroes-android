package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CreateCommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 30-01-2017.
 */

public class ShareCommunityFragment extends BaseFragment implements CreateCommunityView,EditNameDialogListener {

    private ShareCommunityActivityIntractionListner mShareCommunityIntractionListner;
    private final String TAG = LogUtils.makeLogTag(ShareCommunityFragment.class);
    @Bind(R.id.tv_close_community)
    TextView mTvCloseCommunity;
    @Bind(R.id.tv_share_via_social_media)
    TextView mTvShareviasocialmedia;
    @Bind(R.id.tv_community_title)
    TextView mTvCreatecommunitypost;

    public ShareCommunityFragment() {

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof ShareCommunityActivityIntractionListner) {
                mShareCommunityIntractionListner = (ShareCommunityActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.community_share_fragment, container, false);
        ButterKnife.bind(this, view);
        mTvCreatecommunitypost.setText(R.string.ID_SHARE_COMMUNITY);
        //Fabric.with(getActivity(), new Crashlytics());

        return view;
    }
    @OnClick(R.id.tv_share_via_social_media)
    public void socialShareFunction()
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Testing Text From SHEROES2.0");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this site!");
        startActivity(Intent.createChooser(intent, "Share"));
    }
    @Override
    public void onResume() {
        LogUtils.info("DEBUG", "onResume of LoginFragment");
        super.onResume();

    }


    @Override
    public void onPause() {
        LogUtils.info("DEBUG", "OnPause of loginFragment");
        super.onPause();
    }
    @Override
    public void onStop()
    {
        LogUtils.info("DEBUG", "OnPause of loginFragment");
        super.onStop();
    }
  /*  @OnClick(R.id.txt_community_type)
    public void spnOnClick()
    {
        // showSelectCommunityDilog(true);
        showDialog();
    }*/


    @OnClick(R.id.tv_close_community)
    public void onCloseClick()
    {
        mShareCommunityIntractionListner.onShareClose();
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

   /* @Override
    public void dialogValue(String dilogval) {
        etcommunityname.setText(dilogval);

    }*/


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
        LogUtils.info("value",inputText);
    }

    public interface CreateCommunityActivityPostIntractionListner {
        void onErrorOccurence();
    }



}
