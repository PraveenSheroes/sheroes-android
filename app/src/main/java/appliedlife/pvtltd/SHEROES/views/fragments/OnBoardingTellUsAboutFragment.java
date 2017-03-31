package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnBoardingTellUsAboutFragment extends BaseFragment implements OnBoardingView {
    private final String mTAG = LogUtils.makeLogTag(OnBoardingTellUsAboutFragment.class);
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
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private OnBoardingActivityIntractionListner mOnboardingIntractionListner;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    @Inject
    Preference<LoginResponse> userPreference;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof OnBoardingActivityIntractionListner) {
                mOnboardingIntractionListner = (OnBoardingActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(mTAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + mTAG + AppConstants.SPACE + exception.getMessage());
        }
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
                    mCurrentStatus.setText(userPreference.get().getUserSummary().getUserBO().getJobTitle());
                }
                if (StringUtil.isNotNullOrEmptyString(userPreference.get().getUserSummary().getMobile())) {
                    mMobileNumber.setText(userPreference.get().getUserSummary().getMobile());
                }


            }
            mMasterDataResult=mUserPreferenceMasterData.get().getData();
            mCurrentStatus.setEnabled(true);
            mLocation.setEnabled(true);
            mMobileNumber.setEnabled(true);
        } else {
            mOnBoardingPresenter.getMasterDataToPresenter();
        }
        return view;
    }


    public void setCurrentStaus(LabelValue labelValue) {
        if (null != labelValue && StringUtil.isNotNullOrEmptyString(labelValue.getLabel())) {
            mCurrentStatus.setText(labelValue.getLabel());
        }
    }

    public void setLocationData(GetAllDataDocument getAllDataDocument) {
        if (null != getAllDataDocument && StringUtil.isNotNullOrEmptyString(getAllDataDocument.getTitle())) {
            mLocation.setText(getAllDataDocument.getTitle());
        }
    }

    @OnClick(R.id.iv_tell_us_next)
    public void nextClick() {
        if (StringUtil.isNotNullOrEmptyString(mCurrentStatus.getText().toString()) && StringUtil.isNotNullOrEmptyString(mLocation.getText().toString()) && StringUtil.isNotNullOrEmptyString(mMobileNumber.getText().toString())) {
            if (mMobileNumber.getText().toString().length() == 10) {
                ArrayList<String> data=new ArrayList<>();
                data.add(mCurrentStatus.getText().toString());
                data.add(mLocation.getText().toString());
                data.add(mMobileNumber.getText().toString());
                mOnboardingIntractionListner.onCollectUserData(data);
                mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult, AppConstants.ONE_CONSTANT);
            } else {
                Toast.makeText(getContext(), "Enter valid mobile number", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Pleas select Data", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tv_current_status_spinner)
    public void onCurrentStatusClick() {
        if (null != mMasterDataResult) {
            mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult, AppConstants.TWO_CONSTANT);
        }
    }

    @OnClick(R.id.tv_location)
    public void onLocationClick() {
        if (null != mMasterDataResult) {
            mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult, AppConstants.THREE_CONSTANT);
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


    public interface OnBoardingActivityIntractionListner {

        void onCollectUserData(ArrayList<String> stringArrayList);
        void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult, int callFor);
    }
}
