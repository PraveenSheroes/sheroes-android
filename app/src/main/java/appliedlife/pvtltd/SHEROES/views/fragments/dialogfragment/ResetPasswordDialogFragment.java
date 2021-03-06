package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.ResponseStatus;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ResetPasswordDialogFragment extends BaseDialogFragment implements LoginView {
    // region Constants
    private static final String SCREEN_LABEL = "Forgot Password Screen";
    // endregion

    // region Inject
    @Inject
    LoginPresenter mLogInPresenter;
    @Inject
    AppUtils mAppUtils;
    // endregion

    // region view
    @Bind(R.id.input_email_id)
    EditText eTInputEmail;

    @Bind(R.id.reset_pwd_status_text)
    TextView tvPwdStatus;

    @Bind(R.id.pb_reset_pwd)
    ProgressBar pbResetPwd;
    // endregion

    // region lifecycle override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        mLogInPresenter.attachView(this);
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
        mLogInPresenter.detachView();
    }
    // endregion

    // region onClick methods
    @OnClick(R.id.tv_forgot_password_submit)
    public void sendForgotPassword() {
        if (eTInputEmail.getText() == null || !StringUtil.isNotNullOrEmptyString(eTInputEmail.getText().toString())) {
            eTInputEmail.setError(getString(R.string.ID_ERROR_NO_EMAIL));
            eTInputEmail.requestFocus();
        } else if (!mAppUtils.checkEmail(eTInputEmail.getText().toString())) {
            eTInputEmail.setError(getString(R.string.ID_ERROR_INVALID_EMAIL));
            eTInputEmail.requestFocus();
        } else {
            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
            forgotPasswordRequest.setEmailid(eTInputEmail.getText().toString());
            mLogInPresenter.getForgetPasswordResponseInPresenter(forgotPasswordRequest);
        }

    }

    @OnClick(R.id.iv_login_back)
    public void backOnClick() {
        dismiss();
    }
    // endregion

    // region public methods
    @Override
    public void sendForgotPasswordEmail(ForgotPasswordResponse forgotPasswordResponse) {
        if (forgotPasswordResponse != null) {
            if (forgotPasswordResponse.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESS.toString())) {
                if (eTInputEmail != null && StringUtil.isNotNullOrEmptyString(eTInputEmail.getText().toString())) {
                    ((LoginActivity) getActivity()).showResetPasswordSuccessFragment(eTInputEmail.getText().toString());
                }
            } else {
                if (null != getActivity() && isAdded()) {
                    tvPwdStatus.setText(getString(R.string.ID_RESET_PASSWORD_FAILURE_TEXT));
                }
            }
        }
    }

    @Override
    public void sendVerificationEmailSuccess(EmailVerificationResponse emailVerificationResponse) {

    }


    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {

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
    // endregion
}
