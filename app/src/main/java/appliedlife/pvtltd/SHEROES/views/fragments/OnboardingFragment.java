package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;

import java.io.File;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Ajit Kumar on 22-02-2017.
 */

public class OnboardingFragment extends BaseFragment {

    @Bind(R.id.fab_next)
    FloatingActionButton fabnext;
    private OnBoardingActivityIntractionListner mOnboardingIntractionListner;
    private final String mTAG = LogUtils.makeLogTag(OnboardingFragment.class);
    View view;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        view = inflater.inflate(R.layout.onboarding_tell_us_about_yourself, container, false);
        ButterKnife.bind(this, view);


        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        Fabric.with(getActivity(), new Crashlytics());
        return view;
    }
    @OnClick(R.id.fab_next)
    public void nextClick()
    {
        mOnboardingIntractionListner.callSheroesHelpYouTagPage();
    }
    public interface OnBoardingActivityIntractionListner {
        void close();
        void onErrorOccurence();
        void callSheroesHelpYouTagPage();
    }
}
