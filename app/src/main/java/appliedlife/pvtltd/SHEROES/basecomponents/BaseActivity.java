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
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.AppInstallationHelper;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.FeedUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.ReferrerBus;
import appliedlife.pvtltd.SHEROES.utils.ShareUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.BranchDeepLink;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.activities.VideoPlayActivity;
import appliedlife.pvtltd.SHEROES.views.errorview.NetworkAndApiErrorDialog;
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
public abstract class BaseActivity extends AppCompatActivity implements EventInterface, BaseHolderInterface, FragmentIntractionWithActivityListner, View.OnTouchListener, View.OnClickListener {

    //region constant variables
    private final String TAG = LogUtils.makeLogTag(BaseActivity.class);
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
    ShareUtils shareUtils;
    @Inject
    FeedUtils feedUtils;
    //endregion

    //region member variables
    public boolean mIsDestroyed;
    public PopupWindow popupWindow;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    private long mUserId;
    private HashMap<String, Object> mPreviousScreenProperties;
    private String mPreviousScreen;
    private boolean isWhatsAppShare;
    protected SheroesApplication mSheroesApplication;
    private FragmentOpen mFragmentOpen;
    //endregion



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        mSheroesApplication = (SheroesApplication) this.getApplicationContext();

        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getInt(AppConstants.FROM_PUSH_NOTIFICATION, 0) == 1) {
                String notificationId = getIntent().getExtras().getString(AppConstants.NOTIFICATION_ID, "");
                String deepLink = getIntent().getExtras().getString(AppConstants.DEEP_LINK_URL);
                boolean isFromMoengage = getIntent().getExtras().getBoolean(AppConstants.IS_MOENGAGE, false);
                String title = getIntent().getExtras().getString(AppConstants.TITLE);
                boolean isFromPushNotification = getIntent().getExtras().getBoolean(AppConstants.IS_FROM_PUSH, false);
                if (isFromPushNotification) {
                    HashMap<String, Object> properties = new EventProperty.Builder().id(notificationId).url(deepLink).isMonengage(isFromMoengage).title(title).build();
                    trackEvent(Event.PUSH_NOTIFICATION_CLICKED, properties);
                }
            }
            mPreviousScreen = getIntent().getStringExtra(SOURCE_SCREEN);
            mPreviousScreenProperties = (HashMap<String, Object>) getIntent().getSerializableExtra(SOURCE_PROPERTIES);

            boolean isShareDeeplink = getIntent().getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK);
            if (isShareDeeplink) {
                shareUtils.initShare(this, getIntent(), getScreenName());
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

    public void setSource(String source) {
        String mSourceScreen = source;
    }

    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ReferrerBus.getInstance().register(this).add(ReferrerBus.getInstance().toObserveable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object event) {
                if (event instanceof Boolean) {
                    onReferrerReceived((Boolean) event);
                }
            }
        }));
        if (null != mMoEHelper) {
            mMoEHelper.onStart(this);
        }
        if (getPresenter() != null) {
            getPresenter().onStart();
        }
    }

    public void setAllValues(FragmentOpen fragmentOpen) {
        this.mFragmentOpen = fragmentOpen;
    }

    public void callFirstFragment(int layout, Fragment fragment) {
        if (!mIsDestroyed) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(layout, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    /**
     * @param finishParentOnBackOrTryagain pass true:- if desired result is to finish the page on press of tryagain or press of back key else
     *                                     pass false:- to just dismiss the dialog on try again and or press of back key in case you want to handle it your self say a retry
     * @return
     */
    public void showNetworkTimeoutDialog(boolean finishParentOnBackOrTryagain, boolean isCancellable, String errorMessage) {
        showErrorDialogOnUserAction(finishParentOnBackOrTryagain, isCancellable, errorMessage, "");
    }

    public void showErrorDialogOnUserAction(boolean finishParentOnBackOrTryagain, boolean isCancellable, String errorMessage, String isDeactivated) {
        NetworkAndApiErrorDialog fragment = (NetworkAndApiErrorDialog) getFragmentManager().findFragmentByTag(AppConstants.NETWORK_TIMEOUT);
        if (fragment == null) {
            fragment = new NetworkAndApiErrorDialog();
            Bundle b = new Bundle();
            b.putBoolean(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, finishParentOnBackOrTryagain);
            b.putBoolean(BaseDialogFragment.IS_CANCELABLE, isCancellable);
            b.putString(BaseDialogFragment.ERROR_MESSAGE, errorMessage);
            b.putString(BaseDialogFragment.USER_DEACTIVATED, isDeactivated);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), AppConstants.NETWORK_TIMEOUT);
        }
    }


    public void trackEvent(final Event event, final Map<String, Object> properties) {
        AnalyticsManager.trackEvent(event, getScreenName(), properties);
    }

    // endregion
    public String getPreviousScreenName() {
        if (StringUtil.isNotNullOrEmptyString(mPreviousScreen)) {
            return mPreviousScreen;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        try {
            mIsDestroyed = true;
            feedUtils.onDestroy();
            clearReferences();
            super.onDestroy();
        } catch (Exception e) {

        }

        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMoEHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMoEHelper.onResume(this);
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
        // "Remember to also call the unregister method when
        // appropriate."
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
            mMoEHelper.onStop(this);
            mSheroesApplication.notifyIfAppInBackground();
        }
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
        clearReferences();
    }

    private void clearReferences() {
        if (null != mSheroesApplication) {
            String currActivityName = mSheroesApplication.getCurrentActivityName();
            if (StringUtil.isNotNullOrEmptyString(currActivityName)) {
                if (this.getClass().getSimpleName().equals(currActivityName))
                    mSheroesApplication.setCurrentActivityName(null);
            }
        }
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {

    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            openImageFullViewFragment(feedDetail);
        }
    }

    @Override
    public List getListData() {
        return null;
    }

    @Override
    public void setListData(BaseResponse data, boolean isCheked) {

    }

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
                    shareUtils.initShare(this, intent, getScreenName());
                }
            }
        }
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

    protected Map<String, Object> getExtraPropertiesToTrack() {
        return new HashMap<>();
    }


    public void openImageFullViewFragment(FeedDetail feedDetail) {
        AlbumActivity.navigateTo(this, feedDetail, "BASE", null);
    }

    protected void openCommentReactionFragment(FeedDetail feedDetail) {
        feedUtils.openCommentReactionFragment(this, feedDetail, getScreenName());
    }


    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
    }

    @Override
    public void navigateToProfileView(BaseResponse baseResponse, int mValue) {

    }

    @Override
    public void contestOnClick(Contest mContest, CardView mCardChallenge) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        popupWindow.dismiss();
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showNetworkTimeoutDialog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                case AppConstants.MARK_AS_SPAM:
                    showNetworkTimeoutDialog(true, false, errorReason);
                    break;
                case AppConstants.HTTP_401_UNAUTHORIZED_ERROR:
                case AppConstants.HTTP_401_UNAUTHORIZED:
                    showNetworkTimeoutDialog(true, false, getString(R.string.IDS_UN_AUTHORIZE));
                    break;
                default: {
                    showNetworkTimeoutDialog(true, false, getString(R.string.ID_GENERIC_ERROR));
                }
            }
        } else {
            showNetworkTimeoutDialog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }

    }

    @Override
    public void onSuccessResult(String result, FeedDetail feedDetail) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void invalidateLikeUnlike(Comment comment) {

    }

    public void onConfigFetched() {

    }

    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    public String screenName() {
        String sourceScreen = "";
        if (getSupportFragmentManager() != null && !CommonUtil.isEmpty(getSupportFragmentManager().getFragments())) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> fragments = fragmentManager.getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment != null && fragment.isVisible()) {
                        sourceScreen = ((BaseFragment) fragment).getScreenName();
                        break;
                    }
                }
            }
        }
        return sourceScreen;
    }

    protected abstract SheroesPresenter getPresenter();

    public void onReferrerReceived(Boolean isReceived) {
        if (isReceived != null && isReceived) {
            AppInstallationHelper appInstallationHelper = new AppInstallationHelper(this);
            appInstallationHelper.setupAndSaveInstallation(false);
        }
    }
}
