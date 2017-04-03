package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingInterestJobSearch;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.OnBoardingData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.fragments.CurrentStatusDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingDailogHeySuccess;
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
    @Bind(R.id.li_how_sheroes_strip_for_add_item)
    public LinearLayout mLiStripForAddItem;
    @Bind(R.id.tv_job_at_strip_for_add_item)
    public LinearLayout mLiJobAtStripForAddItem;
    @Bind(R.id.tv_interest_strip_for_add_item)
    public LinearLayout mLiInterestStripForAddItem;
    @Bind(R.id.iv_how_can_help_next)
    public ImageView mIvHowCanSheroesNext;
    @Bind(R.id.iv_job_at_next)
    ImageView mIvJobAtNext;
    @Bind(R.id.iv_interest_next)
    ImageView mIvInterestNext;
    @Bind(R.id.tv_interest_search_box)
    TextView mTvInterestSearchBox;
    @Bind(R.id.tv_good_at_search_box)
    TextView mTvGoodAtSearchBox;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    private FragmentOpen mFragmentOpen;
    private CurrentStatusDialog mCurrentStatusDialog;
    private OnBoardingSearchDialogFragment mOnBoardingSearchDialogFragment;
    int first, second, third, fourth;
    int mCurrentIndex = 0;
    private List<LabelValue> mSelectedTag = new ArrayList<>();
    OnBoardingDailogHeySuccess onBoardingDailogHeySuccess;
    @Inject
    Preference<LoginResponse> userPreference;
    View mView;

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
        mCustomCollapsingToolbarLayout.setExpandedSubTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCustomCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        mCustomCollapsingToolbarLayout.setExpandedTitleMarginStart(200);
        // mCustomCollapsingToolbarLayout.setTitle(mFeedDetail.getNameOrTitle());
        //  mCustomCollapsingToolbarLayout.setSubtitle(mFeedDetail.getAuthorName());
        if (null != userPreference && userPreference.isSet() && null != userPreference.get()) {
            if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.CURRENT_STATUS_SCREEN)) {
                tellUsAboutFragment();
            } else if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.HOW_CAN_SHEROES_AKA_LOOKING_FOR_SCREEN)) {
                mHowCanSheroes.setVisibility(View.VISIBLE);
                mInterest.setVisibility(View.GONE);
                mJobAt.setVisibility(View.GONE);
            } else if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.INTEREST_SCREEN)) {
                mInterest.setVisibility(View.VISIBLE);
                mHowCanSheroes.setVisibility(View.GONE);
                mJobAt.setVisibility(View.GONE);
            } else if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.GOOD_AT_SCREEN)) {
                mInterest.setVisibility(View.GONE);
                mHowCanSheroes.setVisibility(View.GONE);
                mJobAt.setVisibility(View.VISIBLE);
            } else if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.TOTAL_WORK_EXPERIENCE_SCREEN)) {
                mInterest.setVisibility(View.GONE);
                mHowCanSheroes.setVisibility(View.GONE);
                mJobAt.setVisibility(View.GONE);
                setOnWorkExperienceFragment();
            } else {
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        } else {
            tellUsAboutFragment();
        }
    }

    private void tellUsAboutFragment() {
        OnBoardingTellUsAboutFragment onBoardingTellUsAboutFragment = new OnBoardingTellUsAboutFragment();
        Bundle bundleArticle = new Bundle();
        onBoardingTellUsAboutFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in_dialog, 0, 0, R.anim.fade_out_dialog)
                .replace(R.id.fl_onboarding_fragment, onBoardingTellUsAboutFragment, OnBoardingTellUsAboutFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof OnBoardingData) {
            OnBoardingData boardingData = (OnBoardingData) baseResponse;
            setTagsForFragment(boardingData, view);
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
        } else if (baseResponse instanceof BoardingInterestJobSearch) {
            BoardingInterestJobSearch boardingInterestJobSearch = (BoardingInterestJobSearch) baseResponse;
            int id = view.getId();
            switch (id) {
                case R.id.tv_interest_job_tag:
                    OnBoardingEnum onBoardingEnum = boardingInterestJobSearch.getOnBoardingEnum();
                    switch (onBoardingEnum) {
                        case INTEREST_SEARCH:
                            if (mIvInterestNext.getVisibility() == View.GONE) {
                                mIvInterestNext.setVisibility(View.VISIBLE);
                            }
                            mLiInterestStripForAddItem.removeAllViews();
                            mLiInterestStripForAddItem.removeAllViewsInLayout();
                            selectTagOnClick(view);
                            renderSelectedAddedItem(mLiInterestStripForAddItem, mSelectedTag);
                            break;
                        case JOB_AT_SEARCH:
                            if (mIvJobAtNext.getVisibility() == View.GONE) {
                                mIvJobAtNext.setVisibility(View.VISIBLE);
                            }
                            mLiJobAtStripForAddItem.removeAllViews();
                            mLiJobAtStripForAddItem.removeAllViewsInLayout();
                            selectTagOnClick(view);
                            renderSelectedAddedItem(mLiJobAtStripForAddItem, mSelectedTag);
                            break;
                        default:
                            LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + onBoardingEnum);
                    }
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
            }

        }

    }


    private void setTagsForFragment(OnBoardingData boardingData, View view) {
        switch (boardingData.getFragmentName()) {
            case AppConstants.HOW_SHEROES_CAN_HELP:
                if (mIvHowCanSheroesNext.getVisibility() == View.GONE) {
                    mIvHowCanSheroesNext.setVisibility(View.VISIBLE);
                }
                mLiStripForAddItem.removeAllViews();
                mLiStripForAddItem.removeAllViewsInLayout();
                mView = view;
                selectTagOnClick(view);
                renderSelectedAddedItem(mLiStripForAddItem, mSelectedTag);
                break;
            case AppConstants.YOUR_INTEREST:
                if (mIvInterestNext.getVisibility() == View.GONE) {
                    mIvInterestNext.setVisibility(View.VISIBLE);
                }
                mLiInterestStripForAddItem.removeAllViews();
                mLiInterestStripForAddItem.removeAllViewsInLayout();
                selectTagOnClick(view);
                renderSelectedAddedItem(mLiInterestStripForAddItem, mSelectedTag);
                break;
            case AppConstants.JOB_AT:
                if (mIvJobAtNext.getVisibility() == View.GONE) {
                    mIvJobAtNext.setVisibility(View.VISIBLE);
                }
                mLiJobAtStripForAddItem.removeAllViews();
                mLiJobAtStripForAddItem.removeAllViewsInLayout();
                selectTagOnClick(view);
                renderSelectedAddedItem(mLiJobAtStripForAddItem, mSelectedTag);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + boardingData.getFragmentName());
        }
    }

    private void selectTagOnClick(View view) {

        LabelValue labelValue = (LabelValue) view.getTag();
        boolean flag = true;
        mCurrentIndex = 0;
        if (StringUtil.isNotEmptyCollection(mSelectedTag)) {
            for (LabelValue listValue : mSelectedTag) {
                if (listValue.getValue() == labelValue.getValue()) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                mSelectedTag.add(labelValue);
            } else {
                Toast.makeText(this, "Already selected", Toast.LENGTH_SHORT).show();
            }
        } else {
            mSelectedTag.add(labelValue);
        }
    }

    @Override
    public void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult, OnBoardingEnum onBoardingEnum) {
        switch (onBoardingEnum) {
            case TELL_US_ABOUT:
                mMasterDataResult = masterDataResult;
                mHowCanSheroes.setVisibility(View.VISIBLE);
                mInterest.setVisibility(View.GONE);
                mJobAt.setVisibility(View.GONE);
                setHowSheroesHelpFragment();
                break;
            case CURRENT_STATUS:
                showCurrentStatusDialog(masterDataResult);
                break;
            case LOCATION:
                mHowCanSheroes.setVisibility(View.GONE);
                mInterest.setVisibility(View.GONE);
                mJobAt.setVisibility(View.GONE);
                searchDataInBoarding(AppConstants.LOCATION_CITY_GET_ALL_DATA_KEY, OnBoardingEnum.LOCATION);
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + onBoardingEnum);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isLookingForHowCanOpen()) {
            mFragmentOpen.setLookingForHowCanOpen(false);
            mFlOnBoardingFragment.setVisibility(View.VISIBLE);
            mHowCanSheroes.setVisibility(View.GONE);
        } else if (mFragmentOpen.isInterestOpen()) {
            mFragmentOpen.setInterestOpen(false);
            mHowCanSheroes.setVisibility(View.VISIBLE);
            mInterest.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStack();
        } else if (mFragmentOpen.isJobAtOpen()) {
            mFragmentOpen.setJobAtOpen(false);
            mFragmentOpen.setLookingForHowCanOpen(true);
            mHowCanSheroes.setVisibility(View.VISIBLE);
            mJobAt.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStack();
        } else if (mFragmentOpen.isWorkingExpOpen()) {
            mFragmentOpen.setWorkingExpOpen(false);
            mFragmentOpen.setJobAtOpen(true);
            mJobAt.setVisibility(View.VISIBLE);
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

        mSelectedTag.clear();
        mLiStripForAddItem.removeAllViews();
        mLiStripForAddItem.removeAllViewsInLayout();
        renderSelectedAddedItem(mLiStripForAddItem, mSelectedTag);
    }

    private void setHowSheroesHelpFragment() {
        mFragmentOpen.setLookingForHowCanOpen(true);
        mFlOnBoardingFragment.setVisibility(View.GONE);
        OnBoardingHowCanSheroesHelpYouFragment onBoardingHowCanSheroesHelpYouFragment = new OnBoardingHowCanSheroesHelpYouFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.HOW_SHEROES_CAN_HELP, mMasterDataResult);
        onBoardingHowCanSheroesHelpYouFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_how_sheroes_can_help, onBoardingHowCanSheroesHelpYouFragment, OnBoardingHowCanSheroesHelpYouFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void setOnBoardingInterestFragment() {
        mFragmentOpen.setInterestOpen(true);
        mFlOnBoardingFragment.setVisibility(View.GONE);
        mSelectedTag.clear();
        OnBoardingShareYourInterestFragment onBoardingShareYourInterestFragment = new OnBoardingShareYourInterestFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.YOUR_INTEREST, mMasterDataResult);
        onBoardingShareYourInterestFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_your_interest, onBoardingShareYourInterestFragment, OnBoardingShareYourInterestFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void setOnJobAtFragment() {
        mFragmentOpen.setJobAtOpen(true);
        mFlOnBoardingFragment.setVisibility(View.GONE);
        mSelectedTag.clear();
        OnBoardingJobAtFragment onBoardingJobAtFragment = new OnBoardingJobAtFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.JOB_AT, mMasterDataResult);
        onBoardingJobAtFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_i_am_job_at, onBoardingJobAtFragment, OnBoardingJobAtFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    private void setOnWorkExperienceFragment() {
        mFragmentOpen.setWorkingExpOpen(true);
        mFlOnBoardingFragment.setVisibility(View.GONE);
        OnBoardingWorkExperienceFragment onBoardingWorkExperienceFragment = new OnBoardingWorkExperienceFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_work_exp_fragment, onBoardingWorkExperienceFragment, OnBoardingWorkExperienceFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
    }

    @OnClick(R.id.iv_how_can_help_next)
    public void onLookingNextClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(OnBoardingHowCanSheroesHelpYouFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((OnBoardingHowCanSheroesHelpYouFragment) fragment).onLookingForHowCanSheroesRequestClick(mSelectedTag);
        }
    }

    public void onLookingForHowCanSheroesNextClick() {
        if (StringUtil.isNotEmptyCollection(mSelectedTag)) {
            boolean flag = false;
            for (LabelValue labelValue : mSelectedTag) {
                String value = labelValue.getLabel();
                if (value.equalsIgnoreCase(AppConstants.WORK_FROM_HOME) || value.equalsIgnoreCase(AppConstants.FREELANCING) || value.equalsIgnoreCase(AppConstants.OFFICE_JOB) || value.equalsIgnoreCase(AppConstants.INTERNSHIP) || value.equalsIgnoreCase(AppConstants.RETURN_FROM_BREAK)) {
                    flag = true;
                }
            }
            mHowCanSheroes.setVisibility(View.GONE);
            if (flag) {
                mJobAt.setVisibility(View.VISIBLE);
                mFragmentOpen.setLookingForHowCanOpen(false);
                setOnJobAtFragment();
            } else {
                mInterest.setVisibility(View.VISIBLE);
                mFragmentOpen.setLookingForHowCanOpen(false);
                setOnBoardingInterestFragment();
            }
        }

    }

    @OnClick(R.id.iv_interest_next)
    public void nextInterestClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(OnBoardingShareYourInterestFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((OnBoardingShareYourInterestFragment) fragment).onInterestNextClick(mSelectedTag);
        }
    }

    public void setInterestNextClick() {
        mHowCanSheroes.setVisibility(View.GONE);
        mInterest.setVisibility(View.VISIBLE);
        mJobAt.setVisibility(View.GONE);
        showHeySuccessDialog();
    }

    @OnClick(R.id.iv_job_at_next)
    public void nextJobAtClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(OnBoardingJobAtFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((OnBoardingJobAtFragment) fragment).onJobAtNextClick(mSelectedTag);
        }
    }

    public void setJobAtNextClick() {
        mHowCanSheroes.setVisibility(View.VISIBLE);
        mInterest.setVisibility(View.GONE);
        mJobAt.setVisibility(View.GONE);
        mFragmentOpen.setJobAtOpen(false);
        mFragmentOpen.setLookingForHowCanOpen(false);
        setOnWorkExperienceFragment();
    }

    @OnClick(R.id.tv_interest_search_box)
    public void onInterestSearchBox() {
        mHowCanSheroes.setVisibility(View.GONE);
        mJobAt.setVisibility(View.GONE);
        mInterest.setVisibility(View.VISIBLE);
        searchDataInBoarding(AppConstants.INTEREST_GET_ALL_DATA_KEY, OnBoardingEnum.INTEREST_SEARCH);
    }

    @OnClick(R.id.tv_good_at_search_box)
    public void onTvGoodAtSearchBox() {
        mInterest.setVisibility(View.GONE);
        mHowCanSheroes.setVisibility(View.GONE);
        mJobAt.setVisibility(View.VISIBLE);
        searchDataInBoarding(AppConstants.JOB_AT_GET_ALL_DATA_KEY, OnBoardingEnum.JOB_AT_SEARCH);
    }

    public DialogFragment showHeySuccessDialog() {
        onBoardingDailogHeySuccess = (OnBoardingDailogHeySuccess) getFragmentManager().findFragmentByTag(OnBoardingDailogHeySuccess.class.getName());
        if (onBoardingDailogHeySuccess == null) {
            onBoardingDailogHeySuccess = new OnBoardingDailogHeySuccess();
        }
        if (!onBoardingDailogHeySuccess.isVisible() && !onBoardingDailogHeySuccess.isAdded() && !isFinishing() && !mIsDestroyed) {
            onBoardingDailogHeySuccess.show(getFragmentManager(), OnBoardingDailogHeySuccess.class.getName());
        }
        return onBoardingDailogHeySuccess;
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

    public void renderSelectedAddedItem(LinearLayout liStripForAddItem, List<LabelValue> labelValues) {

        if (StringUtil.isNotEmptyCollection(labelValues)) {
            int row = 0;
            for (int index = 0; index <= row; index++) {
                first = second = third = fourth = 0;
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout liRow = (LinearLayout) layoutInflater.inflate(R.layout.tags_onboarding_ui_layout, null);
                int column = 3;
                row = cloumnViewTwo(liRow, row, column, labelValues);
                liStripForAddItem.addView(liRow);
            }
        }
    }

    private int cloumnViewTwo(LinearLayout liRow, int passedRow, int column, List<LabelValue> labelValueList) {

        if (mCurrentIndex < labelValueList.size()) {
            int lengthString = labelValueList.get(mCurrentIndex).getLabel().length();
            if (first == 1 && second == 1) {
                passedRow += 1;
                return passedRow;
            } else if (second == 2 || third == 2) {
                passedRow += 1;
                return passedRow;
            } else if (second == 1 && third == 1) {
                passedRow += 1;
                return passedRow;
            } else if (fourth == 1 && second == 1) {
                passedRow += 1;
                return passedRow;
            } else if (fourth >= 1 && lengthString > 30) {
                passedRow += 1;
                return passedRow;
            }
            if (lengthString > 30) {
                if (column < 3) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    first++;
                    inflateTagData(liRow, labelValueList);
                    passedRow += 1;
                    mCurrentIndex++;
                }

            } else if (lengthString <= 30 && lengthString > 15) {

                if (column < 2) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    second++;
                    inflateTagData(liRow, labelValueList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, labelValueList);
                }

            } else if (lengthString >= 10 && lengthString <= 15) {

                if (column < 1) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    third++;
                    inflateTagData(liRow, labelValueList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, labelValueList);
                }

            } else if (lengthString >= 5 && lengthString < 10) {
                if (column < 1) {
                    passedRow += 1;
                    return passedRow;
                } else {
                    fourth++;
                    inflateTagData(liRow, labelValueList);
                    mCurrentIndex++;
                    passedRow = cloumnViewTwo(liRow, passedRow, column - 1, labelValueList);
                }
            }

        }
        return passedRow;
    }

    private void inflateTagData(LinearLayout liRow, List<LabelValue> stringList) {
        LayoutInflater columnInflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout liTagLable = (LinearLayout) columnInflate.inflate(R.layout.tag_selected_item_ui, null);
        final TextView mTvTagData = (TextView) liTagLable.findViewById(R.id.tv_selected);
        mTvTagData.setText(stringList.get(mCurrentIndex).getLabel());
        mTvTagData.setTag(stringList.get(mCurrentIndex));
        mTvTagData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LabelValue labelValue = (LabelValue) mTvTagData.getTag();
                mLiStripForAddItem.removeAllViews();
                mLiStripForAddItem.removeAllViewsInLayout();
                check(labelValue);
            }
        });
        liRow.addView(liTagLable);
    }

    private void check(LabelValue labelValue) {
        List<LabelValue> temp = mSelectedTag;
        mCurrentIndex = 0;
        if (StringUtil.isNotEmptyCollection(temp)) {
            temp.remove(labelValue);
        }
        renderSelectedAddedItem(mLiStripForAddItem, temp);
    }

    public void onWorkExpSuccess() {
        mHowCanSheroes.setVisibility(View.GONE);
        mInterest.setVisibility(View.GONE);
        mJobAt.setVisibility(View.GONE);
        mFragmentOpen.setJobAtOpen(false);
        mFragmentOpen.setLookingForHowCanOpen(false);
        mFragmentOpen.setWorkingExpOpen(false);
        setOnBoardingInterestFragment();
    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        switch (errorReason) {
            case AppConstants.CHECK_NETWORK_CONNECTION:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                break;
            case AppConstants.HTTP_401_UNAUTHORIZED:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_UN_AUTHORIZE));
                break;
            default:
                showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }
}
