package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;
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
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.errorview.OnBoardingMsgDialog;
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OnBoardingActivity extends BaseActivity {
    private final String TAG = LogUtils.makeLogTag(OnBoardingActivity.class);
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;

    @Inject
    Preference<LoginResponse> userPreference;
    @Inject
    Preference<MasterDataResponse> mUserPreferenceMasterData;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    @Bind(R.id.card)
    CardView card;
    @Bind(R.id.tv_on_boarding_finish)
    public TextView tvOnBoardingFinish;
    @Bind(R.id.tv_name_user)
    public TextView tvNameUser;
    @Bind(R.id.tv_on_boarding_description)
    public TextView tvDescription;
    private boolean doubleBackToExitPressedOnce = false;
    public static int isJoinCount = 0;

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
            if (userPreference.get().getNextScreen().equalsIgnoreCase(AppConstants.COMMUNITIES_ONBOARDING_SCREEN)) {
                onBoardingFragment();
            } else {
                Intent homeIntent = new Intent(this, HomeActivity.class);
                Bundle bundle = new Bundle();
                homeIntent.putExtras(bundle);
                startActivity(homeIntent);
                finish();
            }
        } else {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            homeIntent.putExtras(bundle);
            startActivity(homeIntent);
            finish();
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    public void onBoardingFragment() {
        tvNameUser.setText(getString(R.string.welcome,userPreference.get().getUserSummary().getFirstName()));
        isJoinCount = 0;
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvDescription.setText(Html.fromHtml(getString(R.string.ID_BOARDING_COMMUNITIES), 0)); // for 24 api and more
        } else {
            tvDescription.setText(Html.fromHtml(getString(R.string.ID_BOARDING_COMMUNITIES)));// or for older api
        }
        OnBoardingFragment onBoardingFragment = new OnBoardingFragment();
        Bundle bundleArticle = new Bundle();
        onBoardingFragment.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_onboarding_fragment, onBoardingFragment, OnBoardingFragment.class.getName()).commitAllowingStateLoss();

    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        int id = view.getId();
        if (baseResponse instanceof CommunityFeedSolrObj) {
            CommunityFeedSolrObj communityFeedSolrObj = (CommunityFeedSolrObj) baseResponse;
            switch (id) {
                case R.id.tv_boarding_communities_join:
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(OnBoardingFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        if (communityFeedSolrObj.isMember()) {
                            ((OnBoardingFragment) fragment).unJoinCommunity(communityFeedSolrObj);
                        } else {
                            ((OnBoardingFragment) fragment).joinRequestForOpenCommunity(communityFeedSolrObj);
                        }

                    }
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            return;
        }
        doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
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


    @Override
    public void onShowErrorDialog(String s, FeedParticipationEnum feedParticipationEnum) {
        super.onShowErrorDialog(s, feedParticipationEnum);
    }

    @OnClick(R.id.tv_on_boarding_finish)
    public void onFinishButtonClick() {
        if (isJoinCount > 0) {
            HashMap<String, Object> properties = new EventProperty.Builder().build();
            AnalyticsManager.trackEvent(Event.ONBOARDING_COMPLETED, getScreenName(), properties);
            LoginResponse loginResponse = userPreference.get();
            loginResponse.setNextScreen(AppConstants.FEED_SCREEN);
            userPreference.set(loginResponse);
            Intent homeIntent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            homeIntent.putExtras(bundle);
            startActivity(homeIntent);
        } else {
            OnBoardingMsgDialog fragment = (OnBoardingMsgDialog) getFragmentManager().findFragmentByTag(OnBoardingMsgDialog.class.getName());
            if (fragment == null) {
                fragment = new OnBoardingMsgDialog();
                Bundle b = new Bundle();
                // b.putString(AppConstants.SHEROES_AUTH_TOKEN, message);
                fragment.setArguments(b);
            }
            if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
                fragment.show(getFragmentManager(), OnBoardingMsgDialog.class.getName());
            }
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
