package appliedlife.pvtltd.SHEROES.basecomponents;

import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.MenuEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.BranchDeepLink;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.errorview.NetworkTimeoutDialog;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.UserPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.MentorQADetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunityOptionJoinDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.FEED_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_REACTION_COMMENT_MENU;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base activity for all activities.
 */
public abstract class BaseActivity extends AppCompatActivity implements EventInterface, BaseHolderInterface, FragmentIntractionWithActivityListner, View.OnTouchListener, View.OnClickListener {
    public static final String SOURCE_SCREEN = "SOURCE_SCREEN";
    public static final String SOURCE_PROPERTIES = "SOURCE_PROPERTIES";
    public static final String SHARE_WHATSAPP = "Whatsapp";
    public static final String SHARE_FACEBOOK = "Facebook";
    public static final String ANDROID_DEFAULT = "Android Default";
    public static final String BOTTOM_SHEET = "Bottom Sheet";

    public static final int BRANCH_REQUEST_CODE = 1290;
    private final String TAG = LogUtils.makeLogTag(BaseActivity.class);
    public boolean mIsDestroyed;
    protected SheroesApplication mSheroesApplication;
    private FragmentOpen mFragmentOpen;
    private FeedDetail mFeedDetail;
    private Fragment mFragment;
    public View popupView;
    public PopupWindow popupWindow;
    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    @Inject
    Preference<LoginResponse> userPreference;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    private long mUserId;
    private HashMap<String, Object> mPreviousScreenProperties;
    private String mPreviousScreen;
    private boolean isWhatsAppShare;

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
                if(isFromPushNotification){
                    HashMap<String, Object> properties = new EventProperty.Builder().id(notificationId).url(deepLink).isMonengage(isFromMoengage).title(title).build();
                    trackEvent(Event.PUSH_NOTIFICATION_CLICKED, properties);
                }
            }
            mPreviousScreen = getIntent().getStringExtra(SOURCE_SCREEN);
            mPreviousScreenProperties = (HashMap<String, Object>) getIntent().getSerializableExtra(SOURCE_PROPERTIES);

            boolean isShareDeeplink = getIntent().getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK);
            if(isShareDeeplink){
                initShare(getIntent());
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

    private void initShare(Intent intent) {
        String shareText = intent.getExtras().getString(AppConstants.SHARE_TEXT);
        String shareImage = intent.getExtras().getString(AppConstants.SHARE_IMAGE);
        String shareDeeplLink = intent.getExtras().getString(AppConstants.SHARE_DEEP_LINK_URL);
        String shareDialog = intent.getExtras().getString(AppConstants.SHARE_DIALOG_TITLE);
        String shareChannel = intent.getExtras().getString(AppConstants.SHARE_CHANNEL);
        Boolean isShareImage = false;
        if(CommonUtil.isNotEmpty(shareImage)){
            isShareImage = true;
        }
        if(CommonUtil.isNotEmpty(shareChannel)){
            if(shareChannel.equalsIgnoreCase(ANDROID_DEFAULT)){
                if(isShareImage){
                    CommonUtil.shareImageChooser(this, shareText, shareImage);
                }else {
                    CommonUtil.shareCardViaSocial(this, shareDeeplLink);
                }
            }else if(shareChannel.equalsIgnoreCase(SHARE_WHATSAPP)){
                if (isShareImage) {
                    CommonUtil.shareImageWhatsApp(this, shareText, shareImage, getScreenName(), true, null, null);
                }else {
                    CommonUtil.shareLinkToWhatsApp(this, shareText);
                }
            }else if(shareChannel.equalsIgnoreCase(SHARE_FACEBOOK)){
                if(isShareImage){
                    CommonUtil.facebookImageShare(this, shareImage);
                }else {
                    CommonUtil.shareFacebookLink(this, shareText);
                }
            }else {
                ShareBottomSheetFragment.showDialog(this, shareText, shareImage, shareDeeplLink, "", isShareImage, shareDeeplLink, false, false, false, shareDialog);
            }
        }else {
            ShareBottomSheetFragment.showDialog(this, shareText, shareImage, shareDeeplLink, "", isShareImage, shareDeeplLink, false, false, false, shareDialog);
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

    public void setConfigurableShareOption(boolean isWhatsAppShare) {
        this.isWhatsAppShare = isWhatsAppShare;
    }

    public void setViewPagerAndViewAdapter(ViewPagerAdapter viewPagerAdapter, ViewPager viewPager) {
        mViewPagerAdapter = viewPagerAdapter;
        mViewPager = viewPager;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public void startNewActivity(Class<?> activityClass, View transitionImage, String tag) {
        Intent myIntent = new Intent(this, activityClass);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitionImage, tag);
        ActivityCompat.startActivity(this, myIntent, options.toBundle());
        startActivity(myIntent);
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
    public DialogFragment showNetworkTimeoutDoalog(boolean finishParentOnBackOrTryagain, boolean isCancellable, String errorMessage) {
        NetworkTimeoutDialog fragment = (NetworkTimeoutDialog) getFragmentManager().findFragmentByTag(AppConstants.NETWORK_TIMEOUT);
        if (fragment == null) {
            fragment = new NetworkTimeoutDialog();
            Bundle b = new Bundle();
            b.putBoolean(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, finishParentOnBackOrTryagain);
            b.putBoolean(BaseDialogFragment.IS_CANCELABLE, isCancellable);
            b.putString(BaseDialogFragment.ERROR_MESSAGE, errorMessage);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), AppConstants.NETWORK_TIMEOUT);
        }
        return fragment;
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
    public void startActivityFromHolder(Intent intent) {

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

    public DialogFragment showCommunityJoinReason(FeedDetail feedDetail) {
        CommunityOptionJoinDialog fragment = (CommunityOptionJoinDialog) getFragmentManager().findFragmentByTag(CommunityOptionJoinDialog.class.getName());
        if (fragment == null) {
            fragment = new CommunityOptionJoinDialog();
            Bundle b = new Bundle();
            Parcelable parcelable = Parcels.wrap(feedDetail);
            b.putParcelable(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, parcelable);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            fragment.show(getFragmentManager(), CommunityOptionJoinDialog.class.getName());
        }
        return fragment;
    }


    protected boolean trackScreenTime() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == BRANCH_REQUEST_CODE){
            if(intent!=null && intent.getExtras()!=null && intent.getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK)){
                boolean isShareDeeplink = intent.getExtras().getBoolean(AppConstants.IS_SHARE_DEEP_LINK);
                if(isShareDeeplink){
                    initShare(intent);
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
                Uri url = Uri.parse(intent.getDataString());
                AppUtils.openChromeTab(this, url);
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

    protected void feedCardsHandled(View view, BaseResponse baseResponse) {
        mFeedDetail = (FeedDetail) baseResponse;
        int id = view.getId();
        switch (id) {
            case R.id.tv_featured_community_join:
                if (((CommunityFeedSolrObj) mFeedDetail).isClosedCommunity()) {
                    mFeedDetail.setFromHome(true);
                    showCommunityJoinReason(mFeedDetail);
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_CLOSE_COMMUNITY, AppConstants.EMPTY_STRING);
                } else {
                    if (((CommunityFeedSolrObj) mFeedDetail).isRequestPending()) {
                        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.UNDO_REQUEST_JOIN_CLOSE_COMMUNITY, AppConstants.EMPTY_STRING);
                    } else {
                        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_OPEN_COMMUNITY, AppConstants.EMPTY_STRING);
                    }
                }
                break;

            case R.id.tv_feed_article_user_bookmark:
                bookmarkCall();
                break;
            case R.id.tv_event_going_btn:
                bookmarkCall();
            case R.id.tv_feed_community_post_user_bookmark:
                bookmarkCall();
                break;
            case R.id.tv_feed_job_user_bookmark:
                bookmarkCall();
                break;
            case R.id.tv_article_bookmark:
                bookMarkTrending();
                break;
            case R.id.tv_feed_community_post_user_share:
                if (isWhatsAppShare) {
                    shareCardViaSocial(baseResponse);
                } else {
                    shareWithMultipleOption(baseResponse);
                }
                break;
            case R.id.tv_feed_review_post_user_share_ic:
                if (isWhatsAppShare) {
                    shareCardViaSocial(baseResponse);
                } else {
                    shareWithMultipleOption(baseResponse);
                }
                break;
            case R.id.tv_feed_article_user_share:
                if (isWhatsAppShare) {
                    shareCardViaSocial(baseResponse);
                } else {
                    shareWithMultipleOption(baseResponse);
                }
                break;
            case R.id.tv_feed_job_user_share:
                if (isWhatsAppShare) {
                    shareCardViaSocial(baseResponse);
                } else {
                    shareWithMultipleOption(baseResponse);
                }
                break;
            case R.id.tv_article_share:
                if (isWhatsAppShare) {
                    shareCardViaSocial(baseResponse);
                } else {
                    shareWithMultipleOption(baseResponse);
                }
                break;
            case R.id.tv_event_share_btn:
                shareWithMultipleOption(baseResponse);
                break;
            /*Card menu option depend on Feed type like post,article etc */

            case R.id.iv_feed_community_post_user_pic:
            case R.id.tv_feed_community_post_user_name:
                openUserProfileLastComment(view, baseResponse);
                break;

            case R.id.tv_feed_community_post_user_menu:
                clickMenuItem(view, baseResponse, FEED_CARD_MENU);
                break;
            case R.id.tv_spam_post_menu:
                clickMenuItem(view, baseResponse, FEED_CARD_MENU);
                break;
            case R.id.tv_feed_article_user_menu:
                clickMenuItem(view, baseResponse, FEED_CARD_MENU);
                break;
            case R.id.tv_feed_job_user_menu:
                clickMenuItem(view, baseResponse, FEED_CARD_MENU);
                break;
            /* All user comment menu option edit,delete */
            case R.id.tv_feed_community_post_user_comment_post_menu:
                clickMenuItem(view, baseResponse, USER_REACTION_COMMENT_MENU);
                break;
            case R.id.tv_feed_article_user_comment_post_menu:
                clickMenuItem(view, baseResponse, USER_REACTION_COMMENT_MENU);
                break;
            case R.id.tv_feed_article_total_reactions:
                /*mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);*/
                // openCommentReactionFragment(mFeedDetail);
                LikeListBottomSheetFragment.showDialog(this, "", mFeedDetail.getEntityOrParticipantId());
                break;
            case R.id.tv_feed_community_post_total_reactions:
                LikeListBottomSheetFragment.showDialog(this, "", mFeedDetail.getEntityOrParticipantId());
                /*mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);*/
                break;
            case R.id.tv_feed_article_user_comment:
                // mFragmentOpen.setCommentList(true);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.tv_feed_community_post_user_comment:
                //mFragmentOpen.setCommentList(true);
                openCommentReactionFragment(mFeedDetail);
                break;

            case R.id.tv_join_conversation:
                if (mFeedDetail instanceof UserPostSolrObj) {
                    PostDetailActivity.navigateTo(this, getScreenName(), (UserPostSolrObj) mFeedDetail, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, null, true);
                } else if (mFeedDetail instanceof ArticleSolrObj) {
                    ArticleActivity.navigateTo(this, mFeedDetail, getScreenName(), null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
                }
                break;

            /**
             * //Todo - article hv id issue, as no profile for article
             * case R.id.tv_article_card_title :
             case R.id.iv_article_circle_icon:
             // ProfileActivity.navigateTo(this, mFeedDetail.getEntityOrParticipantId(), mFeedDetail.isAuthorMentor(), AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
             break;**/

            case R.id.iv_feed_community_post_login_user_pic:
            case R.id.fl_login_user:
            case R.id.tv_feed_community_post_login_user_name:
            case R.id.feed_img:
                ProfileActivity.navigateTo(this, mFeedDetail.getProfileId(), mFeedDetail.isAuthorMentor(), AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
                break;

            case R.id.li_feed_article_images:
                ArticleActivity.navigateTo(this, mFeedDetail, "Feed", null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
                break;

            case R.id.li_article_cover_image:
                String sourceScreen = "";
                ArticleActivity.navigateTo(this, mFeedDetail, screenName(), null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);

                break;
            case R.id.li_featured_community_images:
                CommunityDetailActivity.navigateTo(this, (CommunityFeedSolrObj) mFeedDetail, getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                /*Intent intetFeature = new Intent(this, CommunitiesDetailActivity.class);
                Bundle bundleFeature = new Bundle();
                Parcelable parcelabless = Parcels.wrap(mFeedDetail);
                bundleFeature.putParcelable(AppConstants.COMMUNITY_DETAIL, parcelabless);
                bundleFeature.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.FEATURE_COMMUNITY);
                intetFeature.putExtras(bundleFeature);
                startActivityForResult(intetFeature, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);*/
                break;
            case R.id.tv_feed_community_post_card_title:
                if (((UserPostSolrObj) mFeedDetail).getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID) {
                    if (null != mFeedDetail) {
                        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                            mUserId = userPreference.get().getUserSummary().getUserId();
                            openGenericCardInWebView(mFeedDetail);
                        }
                    }
                } else if (((UserPostSolrObj) mFeedDetail).getCommunityTypeId() == AppConstants.ASKED_QUESTION_TO_MENTOR) {
                    if (null != mFeedDetail) {

                    }
                } else {
                    if (mFeedDetail instanceof UserPostSolrObj) {
                        if (((UserPostSolrObj) mFeedDetail).getCommunityId() == 0) {
                            ContestActivity.navigateTo(this, Long.toString(((UserPostSolrObj) mFeedDetail).getUserPostSourceEntityId()), mFeedDetail.getScreenName(), null);
                        } else {
                            CommunityDetailActivity.navigateTo(this, ((UserPostSolrObj) mFeedDetail).getCommunityId(), getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);

                        }
                    } else {
                        CommunityDetailActivity.navigateTo(this, ((UserPostSolrObj) mFeedDetail).getCommunityId(), getScreenName(), null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);

                    }
                }
                break;
            case R.id.tv_feed_review_card_title:
                if (null != mFeedDetail) {
                    if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                        mUserId = userPreference.get().getUserSummary().getUserId();
                        openGenericCardInWebView(mFeedDetail);
                    }
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

    private void openGenericCardInWebView(FeedDetail feedDetail) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getDeepLinkUrl())) {
            Uri url = Uri.parse(feedDetail.getDeepLinkUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(url);
            startActivity(intent);
        }
    }


    //Open profile from last comment user profile or name click
    public void openUserProfileLastComment(View view, BaseResponse baseResponse) {
        Comment comment = (Comment) baseResponse;
        if (!comment.isAnonymous() && comment.getParticipantUserId() != null) {
            CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
            communityFeedSolrObj.setIdOfEntityOrParticipant(comment.getParticipantUserId());
            communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
            ProfileActivity.navigateTo(this, communityFeedSolrObj, comment.getParticipantUserId(), comment.isVerifiedMentor(), 0, AppConstants.COMMUNITY_POST_FRAGMENT, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    private void bookmarkCall() {
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof UserPostFragment) {
                ((UserPostFragment) mFragment).bookMarkForCard(mFeedDetail);
            } else {
                ((MentorQADetailFragment) mFragment).bookMarkForCard(mFeedDetail);
            }
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((HomeFragment) fragment).bookMarkForCard(mFeedDetail);
            }
        }

        if (this instanceof ContestActivity) {
            ((ContestActivity) this).bookmarkPost(mFeedDetail);
        }
    }

    private void bookMarkTrending() {
        Fragment articleFragment = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
        if (AppUtils.isFragmentUIActive(articleFragment)) {
            ((ArticlesFragment) articleFragment).bookMarkForCard(mFeedDetail);
        }
    }

    private void shareCardViaSocial(BaseResponse baseResponse) {
        FeedDetail feedDetail = (FeedDetail) baseResponse;
        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getPostShortBranchUrls())) {
            deepLinkUrl = feedDetail.getPostShortBranchUrls();
        } else {
            deepLinkUrl = feedDetail.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.setPackage(AppConstants.WHATS_APP);
        intent.putExtra(Intent.EXTRA_TEXT, AppConstants.SHARED_EXTRA_SUBJECT + deepLinkUrl);
        startActivity(intent);
        moEngageUtills.entityMoEngageCardShareVia(getApplicationContext(), mMoEHelper, payloadBuilder, feedDetail, MoEngageConstants.SHARE_VIA_SOCIAL);
        if (feedDetail.getSubType().equals(AppConstants.FEED_JOB)) {
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant()))
                            .title(mFeedDetail.getNameOrTitle())
                            .companyId(Long.toString(((JobFeedSolrObj) mFeedDetail).getCompanyMasterId()))
                            .sharedTo(AppConstants.WHATSAPP_ICON)
                            .location(mFeedDetail.getAuthorCityName())
                            .build();
            trackEvent(Event.JOBS_SHARED, properties);
        } else {
            AnalyticsManager.trackPostAction(Event.POST_SHARED, mFeedDetail, getScreenName());
        }

    }

    protected void clickMenuItem(View view, final BaseResponse baseResponse, final MenuEnum menuEnum) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.menu_option_layout, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        final LinearLayout liFeedMenu = (LinearLayout) popupView.findViewById(R.id.li_feed_menu);
        final TextView tvEdit = (TextView) popupView.findViewById(R.id.tv_article_menu_edit);
        final TextView tvDelete = (TextView) popupView.findViewById(R.id.tv_article_menu_delete);
        final TextView tvShare = (TextView) popupView.findViewById(R.id.tv_article_menu_share);
        final TextView tvReport = (TextView) popupView.findViewById(R.id.tv_article_menu_report);
        // final Fragment fragmentCommentReaction = getSupportFragmentManager().findFragmentByTag(CommentReactionFragment.class.getName());
        popupWindow.showAsDropDown(view, -150, -10);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOperationOnMenu(menuEnum, baseResponse, null);
                popupWindow.dismiss();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOperationOnMenu(menuEnum, baseResponse, null);
                popupWindow.dismiss();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWithMultipleOption(baseResponse);
                popupWindow.dismiss();
            }
        });
        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAsSpam(menuEnum, baseResponse, null);
                popupWindow.dismiss();
            }
        });
        setMenuOptionVisibility(view, tvEdit, tvDelete, tvShare, tvReport, baseResponse, liFeedMenu);
    }

    private void shareWithMultipleOption(BaseResponse baseResponse) {
        FeedDetail feedDetail = (FeedDetail) baseResponse;
        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getPostShortBranchUrls())) {
            deepLinkUrl = feedDetail.getPostShortBranchUrls();
        } else {
            deepLinkUrl = feedDetail.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
        intent.putExtra(AppConstants.SHARED_EXTRA_SUBJECT + Intent.EXTRA_TEXT, deepLinkUrl);
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        moEngageUtills.entityMoEngageCardShareVia(getApplicationContext(), mMoEHelper, payloadBuilder, feedDetail, MoEngageConstants.SHARE_VIA_SOCIAL);
        HashMap<String, Object> properties = MixpanelHelper.getPostProperties(feedDetail, getScreenName());
        properties.put(EventProperty.SHARED_TO.getString(), AppConstants.SHARE_CHOOSER);
        AnalyticsManager.trackEvent(Event.POST_SHARED, getScreenName(), properties);
    }

    private void setMenuOptionVisibility(View view, TextView tvEdit, TextView tvDelete, TextView tvShare, TextView tvReport, BaseResponse baseResponse, LinearLayout liFeedMenu) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_feed_article_user_comment_post_menu:
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_article_user_menu:
                tvShare.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_job_user_menu:

                break;
            case R.id.tv_user_comment_list_menu:
                if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                    int adminId = 0;
                    if (null != userPreference.get().getUserSummary().getUserBO()) {
                        adminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
                    }
                    if (adminId == AppConstants.TWO_CONSTANT) {
                        tvEdit.setVisibility(View.GONE);
                    } else {
                        tvEdit.setVisibility(View.VISIBLE);
                    }
                }
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_community_post_user_comment_post_menu:
              /*  //if owner
                tvDelete.setVisibility(View.VISIBLE);
                //if commenter*/
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_spam_post_menu:
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_community_post_user_menu:
                mFeedDetail = (FeedDetail) baseResponse;
                if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                    int adminId = 0;
                    Long userId = userPreference.get().getUserSummary().getUserId();
                    if (null != userPreference.get().getUserSummary().getUserBO()) {
                        adminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
                    }
                    if (mFeedDetail.getAuthorId() == userId || ((UserPostSolrObj) mFeedDetail).isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
                        tvDelete.setVisibility(View.VISIBLE);
                        if (((UserPostSolrObj) mFeedDetail).isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
                            if (mFeedDetail.getAuthorId() == userId) {
                                tvEdit.setVisibility(View.VISIBLE);
                            } else {
                                tvEdit.setVisibility(View.GONE);
                            }
                        } else {
                            tvEdit.setVisibility(View.VISIBLE);
                        }

                    } else {
                        if (mFeedDetail.isFromHome()) {
                            tvReport.setText(getString(R.string.ID_REPORTED_AS_SPAM));
                            tvReport.setEnabled(false);
                        }
                        tvReport.setVisibility(View.GONE);
                    }
                    if (((UserPostSolrObj) mFeedDetail).communityId == 0) {
                        tvDelete.setVisibility(View.GONE);
                    }
                    tvShare.setVisibility(View.VISIBLE);
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

    private void markAsSpam(MenuEnum menuEnum, BaseResponse baseResponse, Fragment fragmentCommentReaction) {
        switch (menuEnum) {
            case FEED_CARD_MENU:
                if (null != mFeedDetail) {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((HomeFragment) fragment).markAsSpamCommunityPost(mFeedDetail);
                    }
                }
                break;

        }
    }

    private void editOperationOnMenu(MenuEnum menuEnum, BaseResponse baseResponse, Fragment fragmentCommentReaction) {
        switch (menuEnum) {
            case USER_COMMENT_ON_CARD_MENU:
                Comment comment = (Comment) baseResponse;
                if (null != comment) {
                    if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                        comment.setActive(true);
                        comment.setEdit(true);
                        //  ((CommentReactionFragment) fragmentCommentReaction).editCommentInList(comment);
                    }
                }
                break;
            case USER_REACTION_COMMENT_MENU:
                if (null != mFeedDetail) {
                    // mFragmentOpen.setCommentList(true);
                    mFeedDetail.setTrending(true);
                    ((UserPostSolrObj) mFeedDetail).setIsEditOrDelete(AppConstants.ONE_CONSTANT);
                    openCommentReactionFragment(mFeedDetail);
                }
                break;
            case FEED_CARD_MENU:
                if (null != mFeedDetail) {
                    CommunityPostActivity.navigateTo(this, mFeedDetail, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, null);
                }
                break;

        }
    }

    private void deleteOperationOnMenu(MenuEnum menuEnum, BaseResponse baseResponse, Fragment fragmentCommentReaction) {
        switch (menuEnum) {
            case USER_COMMENT_ON_CARD_MENU:
                Comment comment = (Comment) baseResponse;
                if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                    comment.setActive(false);
                    comment.setEdit(false);
                    //  ((CommentReactionFragment) fragmentCommentReaction).deleteCommentFromList(comment);
                }
                break;
            case USER_REACTION_COMMENT_MENU:
                if (null != mFeedDetail) {
                    //  mFragmentOpen.setCommentList(true);
                    // mFragmentOpen.setCommentList(true);
                    mFeedDetail.setTrending(true);
                    ((UserPostSolrObj) mFeedDetail).setIsEditOrDelete(AppConstants.TWO_CONSTANT);
                    openCommentReactionFragment(mFeedDetail);
                }
                break;
            case FEED_CARD_MENU:
                if (null != mFeedDetail) {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((HomeFragment) fragment).deleteCommunityPost(mFeedDetail);
                    } else {
                        if (mFragment instanceof UserPostFragment) {
                            if (AppUtils.isFragmentUIActive(mFragment)) {
                                ((UserPostFragment) mFragment).deleteCommunityPost(mFeedDetail);
                            }
                        } else {
                            if (AppUtils.isFragmentUIActive(mFragment)) {
                                ((MentorQADetailFragment) mFragment).deleteCommunityPost(mFeedDetail);
                            }
                        }

                    }
                    ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DELETED_CONTENT, GoogleAnalyticsEventActions.DELETED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
                }
                break;

        }
    }

    public void openImageFullViewFragment(FeedDetail feedDetail) {
        AlbumActivity.navigateTo(this, feedDetail, "BASE", null);
    }

    protected void openCommentReactionFragment(FeedDetail feedDetail) {
        clickCommentReactionFragment(feedDetail);
    }

    private void clickCommentReactionFragment(FeedDetail feedDetail) {
        if (feedDetail instanceof UserPostSolrObj) {
            PostDetailActivity.navigateTo(this, getScreenName(), (UserPostSolrObj) feedDetail, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, null, false);
        } else if (feedDetail instanceof ArticleSolrObj) {
            ArticleActivity.navigateTo(this, feedDetail, getScreenName(), null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
        }
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
      /*  if (mFragmentOpen.isBookmarkFragment()) {
            Fragment fragmentBookMark = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragmentBookMark)) {
                ((BookmarksFragment) fragmentBookMark).likeAndUnlikeRequest(baseResponse, reactionValue, position);
            }
        }
            else {*/
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((HomeFragment) fragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
        }
        // }
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
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                case AppConstants.MARK_AS_SPAM:
                    showNetworkTimeoutDoalog(true, false, errorReason);
                    break;
                default: {
                    if (AppConstants.BAD_RQUEST.contains(errorReason)) {
                        showNetworkTimeoutDoalog(true, false, getString(R.string.ID_BAD_RQUEST));
                    } else if (AppConstants.HTTP_401_UNAUTHORIZED.contains(errorReason)) {
                        showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_UN_AUTHORIZE));
                        if (this instanceof HomeActivity) {
                            ((HomeActivity) this).logOut();
                        }
                    } else {
                        showNetworkTimeoutDoalog(true, false, errorReason);
                    }
                }
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
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
}
