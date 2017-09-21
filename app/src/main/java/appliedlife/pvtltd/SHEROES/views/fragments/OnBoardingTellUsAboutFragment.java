package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.CURRENT_STATUS;
import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.LOCATION;
import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.TELL_US_ABOUT;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.boardingTellUsFormDataRequestBuilder;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnBoardingTellUsAboutFragment extends BaseFragment implements OnBoardingView {
    private final String TAG = LogUtils.makeLogTag(OnBoardingTellUsAboutFragment.class);
    @Bind(R.id.pb_boarding_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_location)
    TextView mLocation;
    @Bind(R.id.tv_mobile_number)
    EditText mMobileNumber;
    @Bind(R.id.tv_current_status_spinner)
    TextView mCurrentStatus;
    @Bind(R.id.iv_tell_us_next)
    ImageView mIvNext;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Inject
    AppUtils mAppUtils;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private OnBoardingActivityIntractionListner mOnboardingIntractionListner;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    @Inject
    Preference<LoginResponse> userPreference;
    private LabelValue labelValue;
    private GetAllDataDocument getAllDataDocument;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof OnBoardingActivityIntractionListner) {
                mOnboardingIntractionListner = (OnBoardingActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.onboarding_tell_us_about_yourself, container, false);
        ButterKnife.bind(this, view);
        mCurrentStatus.setEnabled(false);
        mLocation.setEnabled(false);
        mMobileNumber.setEnabled(false);
        mOnBoardingPresenter.attachView(this);
        super.setProgressBar(mProgressBar);
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {

                if (null != userPreference.get().getUserSummary().getUserBO() && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getUserBO().getJobTitle())) {
                    mCurrentStatus.setText(userPreference.get().getUserSummary().getUserBO().getJobTag());
                }
                if (null != userPreference.get().getUserSummary().getUserBO() && StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getUserBO().getJobTitle())) {
                    mLocation.setText(userPreference.get().getUserSummary().getUserBO().getCityMaster());
                }
                if (StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getMobile())) {
                    mMobileNumber.setText(userPreference.get().getUserSummary().getMobile());
                    mMobileNumber.setSelection(mMobileNumber.getText().toString().length());
                }
            }
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
            mCurrentStatus.setEnabled(true);
            mLocation.setEnabled(true);
            mMobileNumber.setEnabled(true);
        } else {
            mOnBoardingPresenter.getMasterDataToPresenter();
        }
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_ONBOARDING_CURRENT_STATUS));
        return view;
    }


    public void setCurrentStaus(LabelValue labelValue) {
        if (null != labelValue && StringUtil.isNotNullOrEmptyString(labelValue.getLabel())) {
            this.labelValue = labelValue;
            mCurrentStatus.setText(labelValue.getLabel());
        }
    }

    public void setLocationData(GetAllDataDocument getAllDataDocument) {
        if (null != getAllDataDocument && StringUtil.isNotNullOrEmptyString(getAllDataDocument.getTitle())) {
            this.getAllDataDocument = getAllDataDocument;
            mLocation.setText(getAllDataDocument.getTitle());
        }
    }

    @OnClick(R.id.iv_tell_us_next)
    public void nextClick() {
        // if (StringUtil.isNotNullOrEmptyString(mCurrentStatus.getText().toString()) && StringUtil.isNotNullOrEmptyString(mLocation.getText().toString()) && StringUtil.isNotNullOrEmptyString(mMobileNumber.getText().toString())) {
        if (!StringUtil.isNotNullOrEmptyString(mCurrentStatus.getText().toString())) {
            Toast.makeText(getContext(), getString(R.string.ID_TELL_US_FORM_VALIDATION), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isNotNullOrEmptyString(mLocation.getText().toString())) {
            Toast.makeText(getContext(), getString(R.string.ID_TELL_US_FORM_VALIDATION), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isNotNullOrEmptyString(mMobileNumber.getText().toString()) || mMobileNumber.getText().toString().length() < 10) {
            Toast.makeText(getContext(), getString(R.string.ID_MOBILE_NUMBER), Toast.LENGTH_SHORT).show();
        } else {
            if (null != labelValue && null != getAllDataDocument) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mMobileNumber.getWindowToken(), 0);
                mOnBoardingPresenter.getCurrentDataStatusToPresenter(boardingTellUsFormDataRequestBuilder(AppConstants.CURRENT_STATUS, AppConstants.CURRENT_STATUS_TYPE, labelValue, getAllDataDocument, mMobileNumber.getText().toString()));
                MoEHelper mMoEHelper = MoEHelper.getInstance(getActivity());
                mMoEHelper.setNumber(mMobileNumber.getText().toString());
                moEngageUtills.entityMoEngageCurrentStatus(getActivity(), mMoEHelper, payloadBuilder,mCurrentStatus.getText().toString(),mLocation.getText().toString());
                if (null != userPreference && userPreference.isSet() && null != userPreference.get()) {
                    LoginResponse loginResponse = userPreference.get();
                    if (null != loginResponse && null != loginResponse.getUserSummary()) {
                        if (!StringUtil.isNotNullOrEmptyString(loginResponse.getUserSummary().getMobile())) {
                            loginResponse.getUserSummary().setMobile(mMobileNumber.getText().toString());
                            userPreference.set(loginResponse);
                        }
                    }
                }
            }
        }
    }
    // }

    @OnClick(R.id.tv_current_status_spinner)
    public void onCurrentStatusClick() {
        if (null != mMasterDataResult) {
            mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult, CURRENT_STATUS);
        }
    }

    @OnClick(R.id.tv_location)
    public void onLocationClick() {
        if (null != mMasterDataResult) {
            mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult, LOCATION);
        }
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {
        if (null != masterDataResult) {
            mCurrentStatus.setEnabled(true);
            mLocation.setEnabled(true);
            mMobileNumber.setEnabled(true);
            mMasterDataResult = masterDataResult;
        }
    }

    @Override
    public void getAllDataResponse(GetAllData getAllData) {

    }

    @Override
    public void getIntersetJobResponse(GetInterestJobResponse getInterestJobResponse) {

    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(errorMsg, feedParticipationEnum);
    }

    @Override
    public void getBoardingJobResponse(BoardingDataResponse boardingDataResponse) {
        if (null != boardingDataResponse) {
            switch (boardingDataResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult, TELL_US_ABOUT);
                    break;
                case AppConstants.FAILED:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), FeedParticipationEnum.ERROR_ON_ONBOARDING);
                    break;

            }
        }
    }


    public interface OnBoardingActivityIntractionListner {

        void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult, OnBoardingEnum onBoardingEnum);
    }
}
