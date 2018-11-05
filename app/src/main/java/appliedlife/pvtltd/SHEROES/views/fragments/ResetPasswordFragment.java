package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.ResponseStatus;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ResetPasswordFragment extends BaseFragment implements LoginView {
    private static final String SCREEN_LABEL = "Forgot Password Screen";
    @Inject
    LoginPresenter mLogInPresenter;

    @Bind(R.id.input_email_id)
    EditText eTInputEmail;

    @Bind(R.id.reset_pwd_status_text)
    TextView tvPwdStatus;

    @Bind(R.id.pb_reset_pwd)
    ProgressBar pbResetPwd;


    @Inject
    AppUtils mAppUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        setProgressBar(pbResetPwd);
        mLogInPresenter.attachView(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLogInPresenter.detachView();
    }

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

    @Override
    public void sendForgotPasswordEmail(ForgotPasswordResponse forgotPasswordResponse) {
        if (forgotPasswordResponse != null) {
            if (forgotPasswordResponse.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESS.toString())) {

                ResetPasswordSuccessFragment resetPasswordSuccessFragment = new ResetPasswordSuccessFragment();

                if (eTInputEmail != null && StringUtil.isNotNullOrEmptyString(eTInputEmail.getText().toString())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.EMAIL, eTInputEmail.getText().toString());
                    resetPasswordSuccessFragment.setArguments(bundle);
                }

                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_login, resetPasswordSuccessFragment, ResetPasswordSuccessFragment.class.getName())
                            .addToBackStack(null)
                            .commitAllowingStateLoss();
                }

            } else {
                if (null != getActivity() && isAdded()) {
                    tvPwdStatus.setText(getString(R.string.ID_RESET_PASSWORD_FAILURE_TEXT));
                }
            }
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mLogInPresenter;
    }

    @OnClick(R.id.iv_login_back)
    public void backOnClick() {
        if (getActivity() == null || getActivity().isFinishing()) return;
        ((LoginActivity) getActivity()).renderLoginFragmentView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}
