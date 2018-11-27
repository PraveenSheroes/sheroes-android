package appliedlife.pvtltd.SHEROES.basecomponents;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ErrorUtil;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.utils.ReferrerBus;
import appliedlife.pvtltd.SHEROES.utils.ShareUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.BranchDeepLink;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.VideoPlayActivity;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import io.reactivex.functions.Consumer;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base activity for all activities.
 */
public abstract class BaseActivity extends AppCompatActivity implements FragmentIntractionWithActivityListner, EventInterface, View.OnTouchListener {
    //region constant variables
    public static final String SOURCE_SCREEN = "SOURCE_SCREEN";
    public static final String SOURCE_PROPERTIES = "SOURCE_PROPERTIES";
    public static final String STORIES_TAB = "write a story";
    public static final String USER_STORY = "USER_STORY";
    public static final String KEY_FOR_DEEPLINK_DETAIL = "post_detail_deep_link";
    public static final String BRANCH_FIRST_SESSION = "is_branch_first_session";
    public static final String DEEP_LINK_URL = "deep_link_url";
    public static final int BRANCH_REQUEST_CODE = 1290;
    //endregion

    //region injected variables
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    Preference<AppInstallation> mAppInstallation;
    @Inject
    ShareUtils mShareUtils;
    @Inject
    FeedUtils mFeedUtils;
    @Inject
    ErrorUtil mErrorUtil;
    //endregion

    //region member variables
    public boolean mIsDestroyed;
    private HashMap<String, Object> mPreviousScreenProperties;
    private String mPreviousScreen;
    protected SheroesApplication mSheroesApplication;
    //endregion

    //region lifecycle override methods
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSheroesApplication = (SheroesApplication) this.getApplicationContext();
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION, 0) == 1) {
                String notificationId = getIntent().getExtras().getString(AppConstants.NOTIFICATION_ID, "");
                String deepLink = getIntent().getExtras().getString(AppConstants.DEEP_LINK_URL);
                String title = getIntent().getExtras().getString(AppConstants.TITLE);
                boolean isFromPushNotification = getIntent().getExtras().getBoolean(AppConstants.IS_FROM_PUSH, false);
                if (isFromPushNotification) {
                    HashMap<String, Object> properties = new EventProperty.Builder().id(notificationId).url(deepLink).title(title).build();
                    trackEvent(Event.PUSH_NOTIFICATION_CLICKED, properties);
                }
            }
            mPreviousScreen = getIntent().getStringExtra(SOURCE_SCREEN);
            mPreviousScreenProperties = (HashMap<String, Object>) getIntent().getSerializableExtra(SOURCE_PROPERTIES);
            boolean isShareDeeplink = getIntent().getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK);
            if (isShareDeeplink) {
                mShareUtils.initShare(this, getIntent(), getScreenName());
            }
        }
        if (!trackScreenTime() && shouldTrackScreen()) {
            Map<String, Object> properties = getExtraPropertiesToTrack();
            if (!CommonUtil.isEmpty(mPreviousScreenProperties)) {
                properties.putAll(mPreviousScreenProperties);
            }
            AnalyticsManager.trackScreenView(getScreenName(), getPreviousScreenName(), properties);
        }
        if (getPresenter() != null) {
            getPresenter().onCreate();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        mFeedUtils.dismissWindow();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ReferrerBus.getInstance().register(this).add(ReferrerBus.getInstance().toObserveable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object event) {
                if (event instanceof Boolean) {
                    mFeedUtils.onReferrerReceived(BaseActivity.this, (Boolean) event);
                }
            }
        }));

        if (getPresenter() != null) {
            getPresenter().onStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mIsDestroyed = true;
            mFeedUtils.clearReferences();
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
        }
        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSheroesApplication.setCurrentActivityName(this.getClass().getSimpleName());
        if (trackScreenTime()) {
            AnalyticsManager.timeScreenView(getScreenName());
        }
        if (getPresenter() != null) {
            getPresenter().onResume();
        }
    }

    @Override
    protected void onPause() {
        // "Remember to also call the unregister method when appropriate."
        if (trackScreenTime() && shouldTrackScreen()) {
            Map<String, Object> properties = getExtraPropertiesToTrack();
            if (!CommonUtil.isEmpty(mPreviousScreenProperties)) {
                properties.putAll(mPreviousScreenProperties);
            }
            AnalyticsManager.trackScreenView(getScreenName(), getPreviousScreenName(), properties);
        }
        if (getPresenter() != null) {
            getPresenter().onPause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ReferrerBus.getInstance().unregister(this);
        if (mSheroesApplication != null) {
            mSheroesApplication.notifyIfAppInBackground();
        }
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
        if (mFeedUtils != null)
            mFeedUtils.clearReferences();
    }
    //endregion

    //region public methods
    public FragmentManager addNewFragment(Fragment fragmentName, int layoutName, String fragmentTag, String addBackStackTag, boolean isAddBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutName, fragmentName, fragmentTag);
        if (isAddBackStack) {
            fragmentTransaction.addToBackStack(addBackStackTag);
        }
        fragmentTransaction.commitAllowingStateLoss();
        return fragmentManager;
    }

    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        mErrorUtil.onShowErrorDialog(this, errorReason, feedParticipationEnum);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * @param finishParentOnBackOrTryagain pass true:- if desired result is to finish the page on press of tryagain or press of back key else
     *                                     pass false:- to just dismiss the dialog on try again and or press of back key in case you want to handle it your self say a retry
     * @return
     */
    public void showNetworkTimeoutDialog(boolean finishParentOnBackOrTryagain, boolean isCancellable, String errorMessage) {
        mErrorUtil.showErrorDialogOnUserAction(this, finishParentOnBackOrTryagain, isCancellable, errorMessage, "");
    }

    public void trackEvent(final Event event, final Map<String, Object> properties) {
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    public String getPreviousScreenName() {
        if (StringUtil.isNotNullOrEmptyString(mPreviousScreen)) {
            return mPreviousScreen;
        }
        return null;
    }

    @Override
    public void startActivity(Intent intent) {
        boolean handled = false;
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_VIEW)) {
            if (CommonUtil.isSheoresAppLink(Uri.parse(intent.getDataString()))) {
                intent.setClass(this, SheroesDeepLinkingActivity.class);
                super.startActivity(intent);
                return;
            }
            if (CommonUtil.isBranchLink(Uri.parse(intent.getDataString()))) {
                intent.setClass(this, BranchDeepLink.class);
                super.startActivityForResult(intent, BRANCH_REQUEST_CODE);
                return;
            }
            if (AppUtils.matchesWebsiteURLPattern(intent.getDataString())) {
                String urlString = intent.getDataString();
                if (StringUtil.isNotNullOrEmptyString(urlString)) {
                    try {
                        URI uri = new URI(urlString);
                        String domain = uri.getHost();
                        if (StringUtil.isNotNullOrEmptyString(domain) && (domain.contains(AppConstants.YOUTUBE_VIDEO_CODE) || domain.contains(AppConstants.MOBILE_YOUTUBE_VIDEO_CODE) || domain.contains("youtube"))) {
                            Intent youTube = new Intent(this, VideoPlayActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString(AppConstants.YOUTUBE_VIDEO_CODE, urlString);
                            youTube.putExtras(bundle);
                            startActivity(youTube);
                        } else {
                            Uri url = Uri.parse(urlString);
                            AppUtils.openChromeTab(this, url);
                        }
                    } catch (URISyntaxException e) {
                        Crashlytics.getInstance().core.logException(e);
                    }
                }
                handled = true;
            }
        }
        if (!handled) {
            try {
                super.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Crashlytics.getInstance().core.logException(e);
            }

        }
    }
    //endregion

    //region public methods
    protected boolean trackScreenTime() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == BRANCH_REQUEST_CODE) {
            if (intent != null && intent.getExtras() != null && intent.getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK)) {
                boolean isShareDeeplink = intent.getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK);
                if (isShareDeeplink) {
                    mShareUtils.initShare(this, intent, getScreenName());
                }
            }
        }
    }

    protected Map<String, Object> getExtraPropertiesToTrack() {
        return new HashMap<>();
    }

    protected abstract SheroesPresenter getPresenter();
    //endregion

}
