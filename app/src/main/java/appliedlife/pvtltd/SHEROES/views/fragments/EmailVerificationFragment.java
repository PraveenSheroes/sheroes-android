package appliedlife.pvtltd.SHEROES.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.ResponseStatus;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.BelNotificationListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.login.EmailVerificationResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ForgotPasswordResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.googleplus.ExpireInResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.LoginActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.LoginView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link .} interface
 * to handle interaction events.
 * Use the {@link EmailVerificationFragment} factory method to
 * create an instance of this fragment.
 */
public class EmailVerificationFragment extends BaseFragment implements LoginView {
    private static final String SCREEN_LABEL = "Email Verification Screen";
    private final String TAG = LogUtils.makeLogTag(EmailVerificationFragment.class);

    @Inject
    LoginPresenter mLoginPresenter;

    @Bind(R.id.pb_verify_email)
    ProgressBar pbVerifyEmail;

    @Inject
    Preference<LoginResponse> userPreference;

    @Bind(R.id.tv_email_verification_text)
    TextView tvEmailVerification;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_email_verification, container, false);
        ButterKnife.bind(this, view);
        mLoginPresenter.attachView(this);
        setProgressBar(pbVerifyEmail);
        mLoginPresenter.getEmailVerificationResponseInPresenter(new EmailVerificationRequest());

        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && userPreference.get().getUserSummary() != null && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getEmailId())) {
            int index = userPreference.get().getUserSummary().getEmailId().indexOf(AppConstants.AT_THE_RATE_OF);
            if (index < userPreference.get().getUserSummary().getEmailId().length()) {
                String domain = userPreference.get().getUserSummary().getEmailId().substring(index);
                tvEmailVerification.setText(getString(R.string.ID_EMAIL_VERIFICATION_TEXT_PART_1) + domain + getString(R.string.ID_EMAIL_VERIFICATION_TEXT_PART_2));
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoginPresenter.detachView();
    }

    @OnClick(R.id.tv_email_verify_link)
    public void sendEmailVerifyLink() {
        mLoginPresenter.getEmailVerificationResponseInPresenter(new EmailVerificationRequest());
    }

    @Override
    public void sendVerificationEmailSuccess(EmailVerificationResponse emailVerificationResponse) {
        if (emailVerificationResponse != null) {
            if (emailVerificationResponse.getStatus().equalsIgnoreCase(ResponseStatus.SUCCESS.toString())) {
                Toast.makeText(getActivity(), getString(R.string.ID_EMAIL_VERIFICATION_SUCCESS_TEXT), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.ID_EMAIL_VERIFICATION_FAILURE_TEXT), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mLoginPresenter;
    }

    @OnClick(R.id.iv_login_back)
    public void backOnClick() {
        getActivity().getSupportFragmentManager().popBackStack();
        ((LoginActivity) getActivity()).renderLoginFragmentView();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void getLogInResponse(LoginResponse loginResponse) {

    }

    @Override
    public void getGoogleExpireInResponse(ExpireInResponse expireInResponse) {

    }

    @Override
    public void sendForgotPasswordEmail(ForgotPasswordResponse forgotPasswordResponse) {

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

    @Override
    public void startNextScreen() {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
}
