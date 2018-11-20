package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ResetPasswordSuccessDialogFragment extends BaseDialogFragment {
    // region Constants
    private static final String SCREEN_LABEL = "Reset Password Success Screen";
    //endregion

    // region View variables
    @Bind(R.id.reset_pwd_success_text)
    TextView tvResetPwd;
    //endregion

    // region lifecycle override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_reset_password_success, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.getString(AppConstants.EMAIL) != null) {
            int index = bundle.getString(AppConstants.EMAIL).indexOf(AppConstants.AT_THE_RATE_OF);
            if (index < bundle.getString(AppConstants.EMAIL).length()) {
                String domain = bundle.getString(AppConstants.EMAIL).substring(index);
                tvResetPwd.setText(getString(R.string.ID_RESET_PASSWORD_SUCCESS_TEXT_PART_1, domain) + getString(R.string.ID_RESET_PASSWORD_SUCCESS_TEXT_PART_2));
            }
        }
        AnalyticsManager.timeScreenView(SCREEN_LABEL);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                backOnClick();
            }
        };
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.back_login)
    public void onLoginLinkClick() {
        backOnClick();
        ((LoginActivity) getActivity()).closeResetPasswordFragment();
    }

    @OnClick(R.id.iv_login_back)
    public void backOnClick() {
        dismiss();
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
    //endregion
}
