package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.CurrentStatusDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingHowCanSheroesHelpYouFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingJobAtFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingSearchDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingShareYourInterestFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingTellUsAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingWorkExperienceFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OnBoardingActivity extends BaseActivity implements OnBoardingTellUsAboutFragment.OnBoardingActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(OnBoardingActivity.class);
    @Bind(R.id.fl_onboarding_fragment)
    FrameLayout mFlOnBoardingFragment;
    @Bind(R.id.app_bar_onboarding)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_onboarding)
    ImageView ivOnBoarding;
    @Bind(R.id.collapsing_toolbar_onboarding)
    public CustomCollapsingToolbarLayout mCustomCollapsingToolbarLayout;
    @Bind(R.id.how_can_sheroes)
    CoordinatorLayout mHowCanSheroes;
    @Bind(R.id.interest)
    CoordinatorLayout mInterest;
    @Bind(R.id.job_at)
    CoordinatorLayout mJobAt;
    @Bind(R.id.tv_strip_for_add_item)
    public LinearLayout mLiStripForAddItem;
    @Bind(R.id.tv_how_can_sheroes_text)
    public TextView mTvHowCanSheroesText;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    private FragmentOpen mFragmentOpen;
    private CurrentStatusDialog mCurrentStatusDialog;
    private OnBoardingSearchDialogFragment mOnBoardingSearchDialogFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        setPagerAndLayouts();
    }

    private void setPagerAndLayouts() {
        mFragmentOpen = new FragmentOpen();
        supportPostponeEnterTransition();
        mHowCanSheroes.setVisibility(View.VISIBLE);
        mInterest.setVisibility(View.GONE);
        mJobAt.setVisibility(View.GONE);
        mCustomCollapsingToolbarLayout.setExpandedSubTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCustomCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCustomCollapsingToolbarLayout.setExpandedTitleMarginStart(200);
        // mCustomCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
        //  mCustomCollapsingToolbarLayout.setSubtitle(mFeedDetail.getAuthorName());
        OnBoardingTellUsAboutFragment onBoardingTellUsAboutFragment = new OnBoardingTellUsAboutFragment();
        Bundle bundleArticle = new Bundle();
        onBoardingTellUsAboutFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in_dialog, 0, 0, R.anim.fade_out_dialog)
                .replace(R.id.fl_onboarding_fragment, onBoardingTellUsAboutFragment, OnBoardingTellUsAboutFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof OnBoardingData) {
            OnBoardingData onBoardingData = (OnBoardingData) baseResponse;
        } else if (baseResponse instanceof LabelValue) {
            LabelValue labelValue = (LabelValue) baseResponse;
            mCurrentStatusDialog.dismiss();
            Fragment tellUsFragment = getSupportFragmentManager().findFragmentByTag(OnBoardingTellUsAboutFragment.class.getName());
            if (AppUtils.isFragmentUIActive(tellUsFragment)) {
                ((OnBoardingTellUsAboutFragment) tellUsFragment).setCurrentStaus(labelValue);
            }
        } else if (baseResponse instanceof GetAllDataDocument) {
            GetAllDataDocument getAllDataDocument = (GetAllDataDocument) baseResponse;
            mOnBoardingSearchDialogFragment.dismiss();
            Fragment tellUsFragment = getSupportFragmentManager().findFragmentByTag(OnBoardingTellUsAboutFragment.class.getName());
            if (AppUtils.isFragmentUIActive(tellUsFragment)) {
                ((OnBoardingTellUsAboutFragment) tellUsFragment).setLocationData(getAllDataDocument);
            }
        }

    }

    @Override
    public void close() {

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult, int callFor) {
        switch (callFor) {
            case AppConstants.ONE_CONSTANT:
                mMasterDataResult = masterDataResult;
                setHowSheroesHelpFragment();
                break;
            case AppConstants.TWO_CONSTANT:
                showCurrentStatusDialog(masterDataResult);
                break;
            case AppConstants.THREE_CONSTANT:
                mHowCanSheroes.setVisibility(View.GONE);
                mInterest.setVisibility(View.GONE);
                mJobAt.setVisibility(View.GONE);
                searchDataInBoarding(AppConstants.LOCATION_CITY_GET_ALL_DATA_KEY, OnBoardingEnum.LOCATION);
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + callFor);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isOpen()) {
            mFragmentOpen.setOpen(false);
            mFlOnBoardingFragment.setVisibility(View.VISIBLE);
        } else if (mFragmentOpen.isFeedOpen()) {
            mFragmentOpen.setFeedOpen(false);
            mHowCanSheroes.setVisibility(View.VISIBLE);
            mInterest.setVisibility(View.GONE);
        } else {
            finish();
        }
    }

    private void setHowSheroesHelpFragment() {
        mFragmentOpen.setOpen(true);
        mFlOnBoardingFragment.setVisibility(View.GONE);
        OnBoardingHowCanSheroesHelpYouFragment onBoardingHowCanSheroesHelpYouFragment = new OnBoardingHowCanSheroesHelpYouFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.HOW_SHEROES_CAN_HELP, mMasterDataResult);
        onBoardingHowCanSheroesHelpYouFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_how_sheroes_can_help, onBoardingHowCanSheroesHelpYouFragment, OnBoardingHowCanSheroesHelpYouFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void setOnBoardingInterestFragment() {
        mFragmentOpen.setFeedOpen(true);
        mFlOnBoardingFragment.setVisibility(View.GONE);
        OnBoardingShareYourInterestFragment onBoardingShareYourInterestFragment = new OnBoardingShareYourInterestFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.YOUR_INTEREST, mMasterDataResult);
        onBoardingShareYourInterestFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_your_interest, onBoardingShareYourInterestFragment, OnBoardingShareYourInterestFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void setOnJobAtFragment() {
        mFragmentOpen.setFeedOpen(true);
        mFlOnBoardingFragment.setVisibility(View.GONE);
        OnBoardingJobAtFragment onBoardingJobAtFragment = new OnBoardingJobAtFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.JOB_AT, mMasterDataResult);
        onBoardingJobAtFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_i_am_job_at, onBoardingJobAtFragment, OnBoardingJobAtFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void setOnWorkExperienceFragment() {
        mFragmentOpen.setFeedOpen(true);
        mFlOnBoardingFragment.setVisibility(View.GONE);
        OnBoardingWorkExperienceFragment onBoardingJobAtFragment = new OnBoardingWorkExperienceFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_i_am_job_at, onBoardingJobAtFragment, OnBoardingWorkExperienceFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    @OnClick(R.id.iv_how_can_help_next)
    public void nextClick() {
        mHowCanSheroes.setVisibility(View.GONE);
        mInterest.setVisibility(View.VISIBLE);
        mJobAt.setVisibility(View.GONE);
        setOnJobAtFragment();
    }

    @OnClick(R.id.iv_help_interest_next)
    public void nextInterestClick() {
        mHowCanSheroes.setVisibility(View.VISIBLE);
        mInterest.setVisibility(View.GONE);
        mJobAt.setVisibility(View.GONE);

        setOnBoardingInterestFragment();
    }

    @OnClick(R.id.iv_job_at_next)
    public void nextJobAtClick() {
        mHowCanSheroes.setVisibility(View.VISIBLE);
        mInterest.setVisibility(View.GONE);
        mJobAt.setVisibility(View.GONE);
        setOnBoardingInterestFragment();
    }

    public DialogFragment showCurrentStatusDialog(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult) {
        mCurrentStatusDialog = (CurrentStatusDialog) getFragmentManager().findFragmentByTag(CurrentStatusDialog.class.getName());
        if (mCurrentStatusDialog == null) {
            mCurrentStatusDialog = new CurrentStatusDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.TAG_LIST, masterDataResult);
            mCurrentStatusDialog.setArguments(bundle);
        }
        if (!mCurrentStatusDialog.isVisible() && !mCurrentStatusDialog.isAdded() && !isFinishing() && !mIsDestroyed) {
            mCurrentStatusDialog.show(getFragmentManager(), CurrentStatusDialog.class.getName());
        }
        return mCurrentStatusDialog;
    }

    public DialogFragment searchDataInBoarding(String masterDataSkill, OnBoardingEnum onBoardingEnum) {
        mOnBoardingSearchDialogFragment = (OnBoardingSearchDialogFragment) getFragmentManager().findFragmentByTag(OnBoardingSearchDialogFragment.class.getName());
        if (mOnBoardingSearchDialogFragment == null) {
            mOnBoardingSearchDialogFragment = new OnBoardingSearchDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AppConstants.BOARDING_SEARCH, onBoardingEnum);
            bundle.putString(AppConstants.MASTER_SKILL, masterDataSkill);
            mOnBoardingSearchDialogFragment.setArguments(bundle);
        }
        if (!mOnBoardingSearchDialogFragment.isVisible() && !mOnBoardingSearchDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            mOnBoardingSearchDialogFragment.show(getFragmentManager(), OnBoardingSearchDialogFragment.class.getName());
        }
        return mOnBoardingSearchDialogFragment;
    }
}
