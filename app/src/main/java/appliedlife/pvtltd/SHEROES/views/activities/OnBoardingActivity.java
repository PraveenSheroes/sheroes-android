package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
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
import appliedlife.pvtltd.SHEROES.views.fragments.OnBoardingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CurrentStatusDialog;
import appliedlife.pvtltd.SHEROES.views.viewholders.DrawerViewHolder;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OnBoardingActivity extends BaseActivity {
    private final String TAG = LogUtils.makeLogTag(OnBoardingActivity.class);
    private HashMap<String, HashMap<String, ArrayList<LabelValue>>> mMasterDataResult;
    private CurrentStatusDialog mCurrentStatusDialog;
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
    public static int isJoinCount=0;
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
                startActivity(homeIntent);
                finish();
            }
        } else {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }

    public void onBoardingFragment() {
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
                        if(communityFeedSolrObj.isMember())
                        {
                            ((OnBoardingFragment) fragment).unJoinCommunity(communityFeedSolrObj);
                        }else
                        {
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
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
      //  Snackbar.make(card, getString(R.string.ID_BACK_PRESS), Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

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

    @OnClick(R.id.tv_on_boarding_finish)
    public void onFinishButtonClick() {
        if(isJoinCount>0) {
            LoginResponse loginResponse = userPreference.get();
            loginResponse.setNextScreen(AppConstants.FEED_SCREEN);
            userPreference.set(loginResponse);
            Intent homeIntent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.ON_BOARDING_COMMUNITIES, AppConstants.ON_BOARDING_COMMUNITIES);
            homeIntent.putExtras(bundle);
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
