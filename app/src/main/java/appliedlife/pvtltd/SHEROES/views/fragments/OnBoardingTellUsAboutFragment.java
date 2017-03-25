package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnBoardingTellUsAboutFragment extends BaseFragment  implements OnBoardingView {
    private final String mTAG = LogUtils.makeLogTag(OnBoardingTellUsAboutFragment.class);
    @Bind(R.id.pb_boarding_progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_location)
    TextView mLocation;
    @Bind(R.id.tv_mobile_number)
    TextView mMobileNumber;
    @Bind(R.id.tv_current_status_spinner)
    TextView mCurrentStatus;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private OnBoardingActivityIntractionListner mOnboardingIntractionListner;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;

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
        if(null!=mUserPreferenceMasterData&&mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get()&&null!=mUserPreferenceMasterData.get().getData()) {
            mOnBoardingPresenter.getMasterDataToPresenter();
        }else
        {
            mCurrentStatus.setEnabled(true);
            mLocation.setEnabled(true);
            mMobileNumber.setEnabled(true);
        }
        return view;
    }


    public void setCurrentStaus(LabelValue labelValue)
    {
        Toast.makeText(getContext(),"*******"+labelValue.getLabel(),Toast.LENGTH_SHORT).show();
        mCurrentStatus.setText(labelValue.getLabel());
    }
    public void setLocationData(  GetAllDataDocument getAllDataDocument)
    {
        Toast.makeText(getContext(),"*******"+getAllDataDocument.getTitle(),Toast.LENGTH_SHORT).show();
        mLocation.setText(getAllDataDocument.getTitle());
    }
    @OnClick(R.id.fab_next)
    public void nextClick()
    {
        mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult,AppConstants.ONE_CONSTANT);
    }
    @OnClick(R.id.tv_current_status_spinner)
    public void onCurrentStatusClick()
    {
        mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult,AppConstants.TWO_CONSTANT);
    }
    @OnClick(R.id.tv_location)
    public void onLocationClick()
    {
        mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult,AppConstants.THREE_CONSTANT);
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {
        if(null!=masterDataResult) {
            mCurrentStatus.setEnabled(true);
            mLocation.setEnabled(true);
            mMobileNumber.setEnabled(true);
            mMasterDataResult = masterDataResult;
        }
    }

    @Override
    public void getAllDataResponse(GetAllData getAllData) {

    }


    public interface OnBoardingActivityIntractionListner {
        void close();
        void onErrorOccurence();
        void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult,int callFor);
    }
}
