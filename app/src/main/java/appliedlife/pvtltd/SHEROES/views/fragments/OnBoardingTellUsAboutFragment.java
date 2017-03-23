package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
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
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
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
        mOnBoardingPresenter.attachView(this);
        super.setProgressBar(mProgressBar);
        mOnBoardingPresenter.getOnBoardingMasterDataToPresenter();
        return view;
    }
    @OnClick(R.id.fab_next)
    public void nextClick()
    {
        mOnboardingIntractionListner.onSheroesHelpYouFragmentOpen(mMasterDataResult);
    }


    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {
        mMasterDataResult=masterDataResult;
    }


    public interface OnBoardingActivityIntractionListner {
        void close();
        void onErrorOccurence();
        void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult);
    }
}
