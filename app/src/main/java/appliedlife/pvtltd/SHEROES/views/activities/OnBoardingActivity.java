package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LookingForLabelValues;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageEvent;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingLookingForFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingTellUsAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CurrentStatusDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.OnBoardingSearchDialogFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;


public class OnBoardingActivity extends BaseActivity implements OnBoardingTellUsAboutFragment.OnBoardingActivityIntractionListner {
    private static final String SCREEN_LABEL = "OnBoarding Screen";
    private final String TAG = LogUtils.makeLogTag(OnBoardingActivity.class);
    @Bind(R.id.app_bar_layout)
   public   AppBarLayout mAppbarLayout;
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    private FragmentOpen mFragmentOpen;
    private CurrentStatusDialog mCurrentStatusDialog;
    private OnBoardingSearchDialogFragment mOnBoardingSearchDialogFragment;
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private int position;
    private MoEHelper mMoEHelper;
    private  PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    private long launchTime;
    private long startedTime;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private boolean isFirstTimeUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime=System.currentTimeMillis();
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        setPagerAndLayouts();
        DrawerViewHolder.selectedOptionName = null;
    }

    public void setPagerAndLayouts() {
        mFragmentOpen = new FragmentOpen();
        supportPostponeEnterTransition();
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && StringUtil.isNotNullOrEmptyString(userPreference.get().getNextScreen())) {
            isFirstTimeUser=true;
            if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.CURRENT_STATUS_SCREEN)) {
                ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_ONBOARDING_WELCOME));
                tellUsAboutFragment();
            } /*else if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.HOW_CAN_SHEROES_AKA_LOOKING_FOR_SCREEN)) {
                ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_ONBOARDING_WELCOME));
                position = 1;
                setLookingForFragment();
            }*/ else {
                isFirstTimeUser=false;
               Intent homeIntent = new Intent(this, HomeActivity.class);
               startActivity(homeIntent);
                finish();
            }
        } else {
            isFirstTimeUser=false;
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }

    public void tellUsAboutFragment() {
        launchTime=System.currentTimeMillis();
        LoginResponse loginResponse = userPreference.get();
        loginResponse.setNextScreen(AppConstants.CURRENT_STATUS_SCREEN);
        userPreference.set(loginResponse);
        OnBoardingTellUsAboutFragment onBoardingTellUsAboutFragment = new OnBoardingTellUsAboutFragment();
        Bundle bundleArticle = new Bundle();
        onBoardingTellUsAboutFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_onboarding_fragment, onBoardingTellUsAboutFragment, OnBoardingTellUsAboutFragment.class.getName()).commitAllowingStateLoss();

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof LookingForLabelValues) {
            LookingForLabelValues lookingForLabelValues = (LookingForLabelValues) baseResponse;
            setTagsForFragment(lookingForLabelValues, view);
        } else if (baseResponse instanceof LabelValue) {
            LabelValue labelValue = (LabelValue) baseResponse;
            if (null != mCurrentStatusDialog) {
                mCurrentStatusDialog.dismiss();
            }
            Fragment tellUsFragment = getSupportFragmentManager().findFragmentByTag(OnBoardingTellUsAboutFragment.class.getName());
            if (AppUtils.isFragmentUIActive(tellUsFragment)) {
                ((OnBoardingTellUsAboutFragment) tellUsFragment).setCurrentStaus(labelValue);
            }
        } else if (baseResponse instanceof GetAllDataDocument) {
            GetAllDataDocument getAllDataDocument = (GetAllDataDocument) baseResponse;
            if (null != mOnBoardingSearchDialogFragment) {
                mOnBoardingSearchDialogFragment.dismiss();
            }
            Fragment tellUsFragment = getSupportFragmentManager().findFragmentByTag(OnBoardingTellUsAboutFragment.class.getName());
            if (AppUtils.isFragmentUIActive(tellUsFragment)) {
                ((OnBoardingTellUsAboutFragment) tellUsFragment).setLocationData(getAllDataDocument);
            }
        }

    }
    private void setTagsForFragment(LookingForLabelValues lookingForLabelValues, View view) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(OnBoardingLookingForFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((OnBoardingLookingForFragment) fragment).onLookingForRequestClick(lookingForLabelValues);
        }
    }

    @Override
    public void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult, OnBoardingEnum onBoardingEnum) {
        switch (onBoardingEnum) {
            case TELL_US_ABOUT:
                long timeSpent=System.currentTimeMillis()-launchTime;
                payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT,timeSpent);
                mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEW_CURRENT_STATUS.value, payloadBuilder.build());
                LoginResponse loginResponse = userPreference.get();
                loginResponse.setNextScreen(AppConstants.HOW_CAN_SHEROES_AKA_LOOKING_FOR_SCREEN);
                userPreference.set(loginResponse);
                mMasterDataResult = masterDataResult;
              //  mFragmentOpen.setLookingForHowCanOpen(true);
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
              //  setLookingForFragment();
                break;
            case CURRENT_STATUS:
                showCurrentStatusDialog(masterDataResult);
                break;
            case LOCATION:

                searchDataInBoarding(AppConstants.LOCATION_CITY_GET_ALL_DATA_KEY, OnBoardingEnum.LOCATION);
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + onBoardingEnum);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (mFragmentOpen.isLookingForHowCanOpen()) {
            mFragmentOpen.setLookingForHowCanOpen(false);
            mAppbarLayout.setVisibility(View.GONE);
            getSupportFragmentManager().popBackStackImmediate();
        }else {
            finish();
        }*/
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
    private void setLookingForFragment() {
        launchTime=System.currentTimeMillis();
        OnBoardingLookingForFragment onBoardingLookingForFragment = new OnBoardingLookingForFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.HOW_SHEROES_CAN_HELP, mMasterDataResult);
        onBoardingLookingForFragment.setArguments(bundle);
        if(position==1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_onboarding_fragment, onBoardingLookingForFragment, OnBoardingLookingForFragment.class.getName()).commitAllowingStateLoss();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_onboarding_fragment, onBoardingLookingForFragment, OnBoardingLookingForFragment.class.getName()).addToBackStack(OnBoardingLookingForFragment.class.getName()).commitAllowingStateLoss();

        }
        }

    public void onLookingForHowCanSheroesNextClick(LookingForLabelValues lookingForLabelValues) {
        long currentTime=System.currentTimeMillis();
        long timeSpent=currentTime-launchTime;
        long totalTime=currentTime-startedTime;
        payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT,timeSpent);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEW_HOW_CAN_LOOKING_FOR.value, payloadBuilder.build());
        payloadBuilder.putAttrLong(MoEngageConstants.COMPLETION_TIME,totalTime);
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_COMPLETED_ON_BOARDING.value, payloadBuilder.build());
        HashMap hashMap=new HashMap<String,Object>();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(lookingForLabelValues.getLabel());
        hashMap.put(MoEngageConstants.LOOKING_FOR,stringBuilder);
        moEngageUtills.entityMoEngageLookingFor(this, mMoEHelper, payloadBuilder,hashMap);

        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .positionInList(lookingForLabelValues.getPosition())
                        .id(String.valueOf(lookingForLabelValues.getValue()))
                        .lookingForName(lookingForLabelValues.getLabel())
                        .build();
        AnalyticsManager.trackEvent(Event.LOOKING_FOR, getScreenName(), properties);

        LoginResponse loginResponse = userPreference.get();
        loginResponse.setNextScreen(AppConstants.FEED_SCREEN);
        userPreference.set(loginResponse);
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
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

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        if(isFirstTimeUser) {
            return true;
        }else {
            return false;
        }
    }
}
