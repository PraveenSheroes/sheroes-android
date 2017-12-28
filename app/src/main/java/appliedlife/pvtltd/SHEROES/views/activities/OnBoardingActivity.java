package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
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
import butterknife.OnClick;


public class OnBoardingActivity extends BaseActivity {
    private static final String SCREEN_LABEL = "OnBoarding Screen";
    private final String TAG = LogUtils.makeLogTag(OnBoardingActivity.class);
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    private CurrentStatusDialog mCurrentStatusDialog;
    private OnBoardingSearchDialogFragment mOnBoardingSearchDialogFragment;
    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    private boolean isFirstTimeUser;
    @Bind(R.id.tv_on_boarding_finish)
    public TextView tvOnBoardingFinish;
    @Bind(R.id.tv_name_user)
    public TextView tvNameUser;
    @Bind(R.id.tv_on_boarding_description)
    public TextView tvDescription;
    public boolean isJoin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        if (null != mUserPreferenceMasterData && mUserPreferenceMasterData.isSet() && null != mUserPreferenceMasterData.get() && null != mUserPreferenceMasterData.get().getData()) {
            mMasterDataResult = mUserPreferenceMasterData.get().getData();
        }
        setPagerAndLayouts();
        DrawerViewHolder.selectedOptionName = null;
    }


    public void setPagerAndLayouts() {
        supportPostponeEnterTransition();
        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && StringUtil.isNotNullOrEmptyString(userPreference.get().getNextScreen())) {
            isFirstTimeUser = true;
            if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.CURRENT_STATUS_SCREEN)) {
                ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_ONBOARDING_WELCOME));
                tellUsAboutFragment();
            } else {
                isFirstTimeUser = false;
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        } else {
            isFirstTimeUser = false;
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }

    public void tellUsAboutFragment() {
        tvNameUser.setText(userPreference.get().getUserSummary().getFirstName());
        String description = getString(R.string.ID_BOARDING_COMMUNITIES);

        SpannableString spannableString = new SpannableString(description);
        if (StringUtil.isNotNullOrEmptyString(description)) {
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.feed_article_label)), 27, 42, 0);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), 27, 42, 0);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.feed_article_label)), description.length()-14, description.length(), 0);
            spannableString.setSpan(new StyleSpan(Typeface.BOLD), description.length()-14, description.length(), 0);
            tvDescription.setMovementMethod(LinkMovementMethod.getInstance());
            tvDescription.setText(spannableString, TextView.BufferType.SPANNABLE);
            tvDescription.setSelected(true);
        }
        OnBoardingTellUsAboutFragment onBoardingTellUsAboutFragment = new OnBoardingTellUsAboutFragment();
        Bundle bundleArticle = new Bundle();
        onBoardingTellUsAboutFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_onboarding_fragment, onBoardingTellUsAboutFragment, OnBoardingTellUsAboutFragment.class.getName()).commitAllowingStateLoss();

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        int id = view.getId();
        if (baseResponse instanceof CommunityFeedSolrObj) {
            CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) baseResponse;
            switch (id) {
                case R.id.tv_boarding_communities_join:
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(OnBoardingTellUsAboutFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((OnBoardingTellUsAboutFragment) fragment).joinRequestForOpenCommunity(communityFeedSolrObj);
                    }
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
            }

       /* if (baseResponse instanceof LookingForLabelValues) {
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
        }*/

        }
    }


  /*  @Override
    public void onSheroesHelpYouFragmentOpen(HashMap<String, HashMap<String, ArrayList<LabelValue>>> masterDataResult, OnBoardingEnum onBoardingEnum) {
        switch (onBoardingEnum) {
            case TELL_US_ABOUT:
                long timeSpent = System.currentTimeMillis() - launchTime;
                payloadBuilder.putAttrLong(MoEngageConstants.TIME_SPENT, timeSpent);
                mMoEHelper.trackEvent(MoEngageEvent.EVENT_VIEW_CURRENT_STATUS.value, payloadBuilder.build());
                LoginResponse loginResponse = mUserPreference.get();
                loginResponse.setNextScreen(AppConstants.HOW_CAN_SHEROES_AKA_LOOKING_FOR_SCREEN);
                mUserPreference.set(loginResponse);
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
    }*/

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

        OnBoardingLookingForFragment onBoardingLookingForFragment = new OnBoardingLookingForFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.HOW_SHEROES_CAN_HELP, mMasterDataResult);
        onBoardingLookingForFragment.setArguments(bundle);
    }

    public void onLookingForHowCanSheroesNextClick(LookingForLabelValues lookingForLabelValues) {
        long currentTime = System.currentTimeMillis();
        mMoEHelper.trackEvent(MoEngageEvent.EVENT_COMPLETED_ON_BOARDING.value, payloadBuilder.build());
        HashMap hashMap = new HashMap<String, Object>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(lookingForLabelValues.getLabel());
        hashMap.put(MoEngageConstants.LOOKING_FOR, stringBuilder);
        moEngageUtills.entityMoEngageLookingFor(this, mMoEHelper, payloadBuilder, hashMap);

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

   /* @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        if (isFirstTimeUser) {
            return true;
        } else {
            return false;
        }
    }*/

    @OnClick(R.id.tv_on_boarding_finish)
    public void onFinishButtonClick() {
        if(isJoin) {
            LoginResponse loginResponse = userPreference.get();
            loginResponse.setNextScreen(AppConstants.FEED_SCREEN);
            userPreference.set(loginResponse);
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
        }else
        {
            Toast.makeText(this,"Please JOIN at least one community",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public String getScreenName() {
        return null;
    }

}
