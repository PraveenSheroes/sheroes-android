package appliedlife.pvtltd.SHEROES.basecomponents;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.enums.MenuEnum;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.JobDetailActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.errorview.NetworkTimeoutDialog;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.GenericWebViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunityOptionJoinDialog;

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
    private GenericWebViewFragment genericWebViewFragment;
    private long mUserId;
    private HashMap<String, Object> mPreviousScreenProperties;
    private String mPreviousScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        mSheroesApplication = (SheroesApplication) this.getApplicationContext();

/*        if (getIntent() != null && getIntent().getExtras() != null) {
            String notificationId = getIntent().getExtras().getString("notificationId");
            logEventToAnalytics(notificationId);
            mPreviousScreen = getIntent().getStringExtra(SOURCE_SCREEN);
            mPreviousScreenProperties = (HashMap<String, Object>) getIntent().getSerializableExtra(SOURCE_PROPERTIES);
        }*/

        if (!trackScreenTime() && shouldTrackScreen()) {
            Map<String, Object> properties = getExtraPropertiesToTrack();
          /*  if (!AppUtils.isEmpty(mPreviousScreenProperties)) {
                properties.putAll(mPreviousScreenProperties);
            }*/
            AnalyticsManager.trackScreenView(getScreenName(), getPreviousScreenName(), properties);
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
    }

    public void setAllValues(FragmentOpen fragmentOpen) {
        this.mFragmentOpen = fragmentOpen;
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

    /**
     * Replace Fragment
     *
     * @param fragment       Fragment Object
     * @param bundle         Bundle to pass to the Fragment
     * @param addToBackStack boolean
     */
    public void replaceFragment(Fragment fragment, int resId, Bundle bundle, boolean addToBackStack) {
        if (fragment != null && !fragment.isAdded()) {
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (addToBackStack) ft.addToBackStack(fragment.getClass().getSimpleName());
            LogUtils.info(TAG, "Fragment TAG given->" + fragment.getClass().getSimpleName());
            if (resId == 0 && findViewById(R.id.container) != null)
                ft.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
            else ft.add(resId, fragment, fragment.getClass().getSimpleName());
            ft.commitAllowingStateLoss();
        }
    }

    private void logEventToAnalytics(String notificationId) {
        if (StringUtil.isNotNullOrEmptyString(notificationId)) {

            HashMap<String, Object> properties = new EventProperty.Builder().id(notificationId).type("type"/*getNotificationType()*/).build();
            trackEvent(Event.PUSH_NOTIFICATION_CLICKED, properties);

         /*   if (currentUser != null) {
                Answers.getInstance().logCustom(new CustomEvent("Push Notification")
                        .putCustomAttribute("User", currentUser.remote_id)
                        .putCustomAttribute("Action", "clicked")
                        .putCustomAttribute("Notification Type", getNotificationType())
                        .putCustomAttribute("Notification ID", notificationId));
            }*/
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

    public void replaceFragment(Fragment fragment) {
        replaceFragment(fragment, 0, null, false);
    }

    @Override
    protected void onDestroy() {
        try {
            mIsDestroyed = true;
            clearReferences();
            super.onDestroy();
        } catch (Exception e) {

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
    }

    @Override
    protected void onPause() {
        // "Remember to also call the unregister method when
        // appropriate."
        if (trackScreenTime() && shouldTrackScreen()) {
          /*  Map<String, Object> properties = getExtraPropertiesToTrack();
            if (!CommonUtil.isEmpty(mPreviousScreenProperties)) {
                properties.putAll(mPreviousScreenProperties);
            }*/
            AnalyticsManager.trackScreenView(getScreenName(), getPreviousScreenName(), null);
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
            b.putParcelable(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, feedDetail);
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
    public void startActivity(Intent intent) {
        boolean handled = false;
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_VIEW)) {
            if (AppUtils.matchesWebsiteURLPattern(intent.getDataString())) {
                Uri url = Uri.parse(intent.getDataString());
                AppUtils.openChromeTab(this, url);
                handled = true;
            }
            }
            if(!handled){
                super.startActivity(intent);
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
                if (mFeedDetail.isClosedCommunity()) {
                    mFeedDetail.setFromHome(true);
                    showCommunityJoinReason(mFeedDetail);
                    ((SheroesApplication)((BaseActivity)this).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_CLOSE_COMMUNITY, AppConstants.EMPTY_STRING);
                } else {
                    if(mFeedDetail.isRequestPending())
                    {
                        ((SheroesApplication)((BaseActivity)this).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.UNDO_REQUEST_JOIN_CLOSE_COMMUNITY, AppConstants.EMPTY_STRING);
                    }else
                    {
                        ((SheroesApplication)((BaseActivity)this).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_OPEN_COMMUNITY, AppConstants.EMPTY_STRING);
                    }
                    if (null != mViewPagerAdapter) {
                        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                        if (AppUtils.isFragmentUIActive(fragment)) {
                            mFeedDetail.setScreenName(AppConstants.FEATURE_FRAGMENT);
                            ((FeaturedFragment) fragment).joinRequestForOpenCommunity(mFeedDetail);
                        }
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
                shareCardViaSocial(baseResponse);
                break;
            case R.id.tv_feed_review_post_user_share_ic:
                shareCardViaSocial(baseResponse);
                break;
            case R.id.tv_feed_article_user_share:
                shareCardViaSocial(baseResponse);
                break;
            case R.id.tv_feed_job_user_share:
                shareCardViaSocial(baseResponse);
                break;
            case R.id.tv_article_share:
                shareCardViaSocial(baseResponse);
                break;
            case R.id.tv_event_share_btn:
                shareCardViaSocial(baseResponse);
                break;
            /*Card menu option depend on Feed type like post,article etc */
            case R.id.tv_feed_community_post_user_menu:
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
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.tv_feed_community_post_total_reactions:
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.li_feed_article_join_conversation:
                mFragmentOpen.setCommentList(true);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.li_feed_community_post_join_conversation:
                mFragmentOpen.setCommentList(true);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.tv_feed_article_user_reaction:
                userReactionDialogLongPress(view);
                break;
            case R.id.tv_feed_community_post_user_reaction:
                userReactionDialogLongPress(view);
                break;
            case R.id.li_feed_article_images:
                ArticleActivity.navigateTo(this, mFeedDetail, "da", null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
                /*Intent intent = new Intent(this, ArticleDetailActivity.class);
                intent.putExtra(AppConstants.ARTICLE_DETAIL, mFeedDetail);
                startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);*/
                break;
            case R.id.li_feed_job_card:
                Intent intentJob = new Intent(this, JobDetailActivity.class);
                intentJob.putExtra(AppConstants.JOB_DETAIL, mFeedDetail);
                startActivityForResult(intentJob, AppConstants.REQUEST_CODE_FOR_JOB_DETAIL);
                break;
            case R.id.li_article_cover_image:
                ArticleActivity.navigateTo(this, mFeedDetail, "da", null,  AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);
                /*Intent intentArticle = new Intent(this, ArticleDetailActivity.class);
                intentArticle.putExtra(AppConstants.ARTICLE_DETAIL, mFeedDetail);
                startActivityForResult(intentArticle, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL);*/
                break;
            case R.id.li_community_images:
                Intent intentMyCommunity = new Intent(this, CommunitiesDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);
                bundle.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.MY_COMMUNITY);
                intentMyCommunity.putExtras(bundle);
                startActivityForResult(intentMyCommunity, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                break;
            case R.id.li_featured_community_images:
                Intent intetFeature = new Intent(this, CommunitiesDetailActivity.class);
                Bundle bundleFeature = new Bundle();
                bundleFeature.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);
                bundleFeature.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.FEATURE_COMMUNITY);
                intetFeature.putExtras(bundleFeature);
                startActivityForResult(intetFeature, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                break;
            case R.id.tv_feed_community_post_card_title:
                if(mFeedDetail.getCommunityTypeId() == AppConstants.ORGANISATION_COMMUNITY_TYPE_ID){
                    if(null!=mFeedDetail) {
                        if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                            mUserId = userPreference.get().getUserSummary().getUserId();
                            openGenericCardInWebView(mFeedDetail);
                        }
                    }
                }else {
                    Intent intentFromCommunityPost = new Intent(this, CommunitiesDetailActivity.class);
                    Bundle bundleFromPost = new Bundle();
                    bundleFromPost.putBoolean(AppConstants.COMMUNITY_POST_ID, true);
                    bundleFromPost.putParcelable(AppConstants.COMMUNITY_DETAIL, mFeedDetail);
                    bundleFromPost.putSerializable(AppConstants.MY_COMMUNITIES_FRAGMENT, CommunityEnum.MY_COMMUNITY);
                    intentFromCommunityPost.putExtras(bundleFromPost);
                    startActivityForResult(intentFromCommunityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                }
                break;
            case R.id.tv_feed_review_card_title:
                if(null!=mFeedDetail) {
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

    private DialogFragment openGenericCardInWebView(FeedDetail feedDetail){
       if(StringUtil.isNotNullOrEmptyString(feedDetail.getDeepLinkUrl())) {
           genericWebViewFragment = (GenericWebViewFragment) getFragmentManager().findFragmentByTag(GenericWebViewFragment.class.getName());
           if (genericWebViewFragment == null) {
               genericWebViewFragment = new GenericWebViewFragment();
               Bundle bundle = new Bundle();
               bundle.putString(AppConstants.WEB_URL, feedDetail.getDeepLinkUrl());
               bundle.putString(AppConstants.WEB_TITLE, feedDetail.getPostCommunityName());
               genericWebViewFragment.setArguments(bundle);
           }
           if (!genericWebViewFragment.isVisible() && !genericWebViewFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
               genericWebViewFragment.show(getFragmentManager(), GenericWebViewFragment.class.getName());
           }
           return genericWebViewFragment;
       }else {
           return null;
       }
    }


    private void userReactionDialogLongPress(View view) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = layoutInflater.inflate(R.layout.emoji_reaction_layout, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        TextView tvCommunityReaction = (TextView) popupView.findViewById(R.id.tv_reaction);
        TextView tvCommunityReaction1 = (TextView) popupView.findViewById(R.id.tv_reaction1);
        TextView tvCommunityReaction2 = (TextView) popupView.findViewById(R.id.tv_reaction2);
        TextView tvCommunityReaction3 = (TextView) popupView.findViewById(R.id.tv_reaction3);
        TextView tvCommunityReaction4 = (TextView) popupView.findViewById(R.id.tv_reaction4);
        tvCommunityReaction.setOnClickListener(this);
        tvCommunityReaction1.setOnClickListener(this);
        tvCommunityReaction2.setOnClickListener(this);
        tvCommunityReaction3.setOnClickListener(this);
        tvCommunityReaction4.setOnClickListener(this);
        popupWindow.showAsDropDown(view, 0, -200);
        popupView.setOnTouchListener(this);
    }

    private void bookmarkCall() {
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof CommunitiesDetailFragment) {
                ((CommunitiesDetailFragment) mFragment).bookMarkForCard(mFeedDetail);
            }
        } else {
            if (mFragmentOpen.isBookmarkFragment()) {
                Fragment fragmentBookMark = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragmentBookMark)) {
                    ((BookmarksFragment) fragmentBookMark).bookMarkForCard(mFeedDetail, mFragmentOpen);
                }
            } else if (mFragmentOpen.isJobFragment()) {
                Fragment fragmentBookMark = getSupportFragmentManager().findFragmentByTag(JobFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragmentBookMark)) {
                    ((JobFragment) fragmentBookMark).bookMarkForCard(mFeedDetail);
                }
            } else {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).bookMarkForCard(mFeedDetail);
                }
            }
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
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, feedDetail.getDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        moEngageUtills.entityMoEngageCardShareVia(getApplicationContext(), mMoEHelper, payloadBuilder, feedDetail, MoEngageConstants.SHARE_VIA_SOCIAL);
        if(feedDetail.getSubType().equals(AppConstants.FEED_JOB)){
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(mFeedDetail.getIdOfEntityOrParticipant()))
                            .title(mFeedDetail.getNameOrTitle())
                            .companyId(Long.toString(mFeedDetail.getCompanyMasterId()))
                            .location(mFeedDetail.getAuthorCityName())
                            .build();
            trackEvent(Event.JOBS_SHARED, properties);
        }else {
            AnalyticsManager.trackPostAction(Event.POST_SHARED, mFeedDetail);
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
        final Fragment fragmentCommentReaction = getSupportFragmentManager().findFragmentByTag(CommentReactionFragment.class.getName());
        popupWindow.showAsDropDown(view, -150, -10);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOperationOnMenu(menuEnum, baseResponse, fragmentCommentReaction);
                popupWindow.dismiss();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOperationOnMenu(menuEnum, baseResponse, fragmentCommentReaction);
                popupWindow.dismiss();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:: reveiw for share
               /* if (mFragmentOpen.isBookmarkFragment()) {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((BookmarksFragment) fragment).commentListRefresh(mFeedDetail);
                    }
                } else {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((HomeFragment) fragment).commentListRefresh(mFeedDetail);
                    }
                }*/
                popupWindow.dismiss();
            }
        });
        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAsSpam(menuEnum, baseResponse, fragmentCommentReaction);
                popupWindow.dismiss();
            }
        });
        setMenuOptionVisibility(view, tvEdit, tvDelete, tvShare, tvReport, baseResponse, liFeedMenu);
    }


    private void setMenuOptionVisibility(View view, TextView tvEdit, TextView tvDelete, TextView tvShare, TextView tvReport, BaseResponse baseResponse, LinearLayout liFeedMenu) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_feed_article_user_comment_post_menu:
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_article_user_menu:
                // tvShare.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_job_user_menu:
                //  tvShare.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_user_comment_list_menu:
                if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                    int adminId = 0;
                    if (null != userPreference.get().getUserSummary().getUserBO()) {
                        adminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
                    }
                    if(adminId==AppConstants.TWO_CONSTANT)
                    {
                        tvEdit.setVisibility(View.GONE);
                    }else {
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
            case R.id.tv_feed_community_post_user_menu:
                mFeedDetail = (FeedDetail) baseResponse;
                if (null != userPreference && userPreference.isSet() && null != userPreference.get() && null != userPreference.get().getUserSummary()) {
                    int adminId=0;
                    Long userId=userPreference.get().getUserSummary().getUserId();
                    if(null != userPreference.get().getUserSummary().getUserBO()) {
                        adminId = userPreference.get().getUserSummary().getUserBO().getUserTypeId();
                    }
                    if (mFeedDetail.getAuthorId() == userId|| mFragmentOpen.isOwner()||adminId==AppConstants.TWO_CONSTANT) {
                        tvDelete.setVisibility(View.VISIBLE);
                        if(mFeedDetail.isCommunityOwner()||adminId==AppConstants.TWO_CONSTANT)
                        {
                            if(mFeedDetail.getAuthorId() == userId)
                            {
                                tvEdit.setVisibility(View.VISIBLE);
                            }else {
                                tvEdit.setVisibility(View.GONE);
                            }
                        }else
                        {
                            tvEdit.setVisibility(View.VISIBLE);
                        }

                    } else {
                        if (mFeedDetail.isFromHome()) {
                            tvReport.setText(getString(R.string.ID_REPORTED_AS_SPAM));
                            tvReport.setEnabled(false);
                        }
                        tvReport.setVisibility(View.GONE);
                    }
                    //  tvShare.setVisibility(View.VISIBLE);
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
                    if (mFragmentOpen.isBookmarkFragment()) {
                        Fragment fragmentBookMark = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
                        if (AppUtils.isFragmentUIActive(fragmentBookMark)) {
                            ((BookmarksFragment) fragmentBookMark).markAsSpamCommunityPost(mFeedDetail);
                        }
                    } else {
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                        if (AppUtils.isFragmentUIActive(fragment)) {
                            ((HomeFragment) fragment).markAsSpamCommunityPost(mFeedDetail);
                        }
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
                        ((CommentReactionFragment) fragmentCommentReaction).editCommentInList(comment);
                    }
                }
                break;
            case USER_REACTION_COMMENT_MENU:
                if (null != mFeedDetail) {
                    mFragmentOpen.setCommentList(true);
                    mFeedDetail.setTrending(true);
                    mFeedDetail.setExperienceFromI(AppConstants.ONE_CONSTANT);
                    openCommentReactionFragment(mFeedDetail);
                }
                break;
            case FEED_CARD_MENU:
                if (null != mFeedDetail) {
                    CommunityPostActivity.navigateTo(this, mFeedDetail, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST);
                   /* Intent intetFeature = new Intent(this, CreateCommunityPostActivity.class);
                    Bundle bundle = new Bundle();
                    mFeedDetail.setCallFromName(AppConstants.FEED_COMMUNITY_POST);
                    bundle.putParcelable(AppConstants.COMMUNITY_POST_FRAGMENT, mFeedDetail);
                    intetFeature.putExtras(bundle);
                    startActivityForResult(intetFeature, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST);
                    ((SheroesApplication)this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EDITED_CONTENT, GoogleAnalyticsEventActions.EDITED_COMMUNITY_POST, AppConstants.EMPTY_STRING);*/
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
                    ((CommentReactionFragment) fragmentCommentReaction).deleteCommentFromList(comment);
                }
                break;
            case USER_REACTION_COMMENT_MENU:
                if (null != mFeedDetail) {
                    mFragmentOpen.setCommentList(true);
                    mFragmentOpen.setCommentList(true);
                    mFeedDetail.setTrending(true);
                    mFeedDetail.setExperienceFromI(AppConstants.TWO_CONSTANT);
                    openCommentReactionFragment(mFeedDetail);
                }
                break;
            case FEED_CARD_MENU:
                if (null != mFeedDetail) {
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                    if (AppUtils.isFragmentUIActive(fragment)) {
                        ((HomeFragment) fragment).deleteCommunityPost(mFeedDetail);
                    } else {
                        if (AppUtils.isFragmentUIActive(mFragment)) {
                            ((CommunitiesDetailFragment) mFragment).deleteCommunityPost(mFeedDetail);
                        }
                    }

                   /* if (mFragmentOpen.isBookmarkFragment()) {
                        Fragment fragmentBookMark = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
                        if (AppUtils.isFragmentUIActive(fragmentBookMark)) {
                            ((BookmarksFragment) fragmentBookMark).deleteCommunityPost(mFeedDetail);
                        }
                    } else {
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                        if (AppUtils.isFragmentUIActive(fragment)) {
                            ((HomeFragment) fragment).deleteCommunityPost(mFeedDetail);
                        }
                    }*/
                    ((SheroesApplication)this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DELETED_CONTENT, GoogleAnalyticsEventActions.DELETED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
                }
                break;

        }
    }
    public void openImageFullViewFragment(FeedDetail feedDetail) {
        /*if (StringUtil.isNotEmptyCollection(feedDetail.getImageUrls())) {
            ImageFullViewDialogFragment imageFullViewDialogFragment = (ImageFullViewDialogFragment) getFragmentManager().findFragmentByTag(ImageFullViewDialogFragment.class.getName());
            if (imageFullViewDialogFragment == null) {
                imageFullViewDialogFragment = new ImageFullViewDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
                bundle.putParcelable(AppConstants.IMAGE_FULL_VIEW, feedDetail);
                imageFullViewDialogFragment.setArguments(bundle);
            }
            if (!imageFullViewDialogFragment.isVisible() && !imageFullViewDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
                imageFullViewDialogFragment.show(getFragmentManager(), ImageFullViewDialogFragment.class.getName());
            }
        }*/
        AlbumActivity.navigateTo(this, feedDetail, "BASE", null);
    }

    protected void openCommentReactionFragment(FeedDetail feedDetail) {
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof CommunitiesDetailFragment) {
                clickCommentReactionFragment(feedDetail);
            }
        } else {
            if (mFragmentOpen.isBookmarkFragment()) {
                Fragment fragmentBookMark = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragmentBookMark)) {
                    clickCommentReactionFragment(feedDetail);
                }
            } else {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    clickCommentReactionFragment(feedDetail);
                }
            }
        }
    }

    private void clickCommentReactionFragment(FeedDetail feedDetail) {
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof CommunitiesDetailFragment) {
                CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
                Bundle bundleArticle = new Bundle();
                bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
                bundleArticle.putParcelable(AppConstants.COMMENTS, feedDetail);
                commentReactionFragmentForArticle.setArguments(bundleArticle);
                getSupportFragmentManager().beginTransaction().replace(R.id.about_community_container, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

            }
        } else {
            CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
            Bundle bundleArticle = new Bundle();
            bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
            bundleArticle.putParcelable(AppConstants.COMMENTS, feedDetail);
            commentReactionFragmentForArticle.setArguments(bundleArticle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_feed_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        if (mFragmentOpen.isBookmarkFragment()) {
            Fragment fragmentBookMark = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragmentBookMark)) {
                ((BookmarksFragment) fragmentBookMark).likeAndUnlikeRequest(baseResponse, reactionValue, position);
            }
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((HomeFragment) fragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
            }
        }
    }

    @Override
    public void championProfile(BaseResponse baseResponse,int championValue) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        popupWindow.dismiss();
        return true;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_reaction:
                userCommentLikeRequest(mFeedDetail, AppConstants.HEART_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_reaction1:
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_FIRST_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_reaction2:
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_SECOND_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_reaction3:
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_THIRD_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_reaction4:
                userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_FOURTH_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                popupWindow.dismiss();
                break;
            case R.id.tv_review_upvote_reacted:
                userCommentLikeRequest(mFeedDetail, AppConstants.HEART_REACTION_CONSTANT, mFeedDetail.getItemPosition());
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);

        }
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
                            ((HomeActivity)this).logOut();
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
}
