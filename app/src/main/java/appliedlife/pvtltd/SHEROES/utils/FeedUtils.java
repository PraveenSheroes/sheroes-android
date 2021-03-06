package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.MenuEnum;
import appliedlife.pvtltd.SHEROES.models.AppInstallationHelper;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CommunityFeedSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.AlbumActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ContestActivity;
import appliedlife.pvtltd.SHEROES.views.activities.PostDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.LikeListBottomSheetFragment;

import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.FEED_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_REACTION_COMMENT_MENU;

public class FeedUtils {

    //region constant variables
    private static final int ASK_QUESTION_POST = 3;
    //endregion

    //region member variables
    private static FeedUtils sInstance;
    private FeedDetail mFeedDetail;
    private Fragment mFragment;
    private boolean mIsWhatsAppShare;
    public PopupWindow mPopupWindow;
    SheroesApplication mSheroesApplication;
    //endregion

    //region injected variables
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    public FeedUtils() {
        SheroesApplication.getAppComponent(SheroesApplication.mContext).inject(this);
    }
    //endregion

    //region public methods
    public static synchronized FeedUtils getInstance() {
        if (sInstance == null) {
            sInstance = new FeedUtils();
        }
        return sInstance;
    }

    public void feedCardsHandled(View view, BaseResponse baseResponse, Activity activity, String screenName) {
        mFeedDetail = (FeedDetail) baseResponse;
        int id = view.getId();
        switch (id) {
            case R.id.tv_feed_article_user_bookmark:
                bookmarkCall(activity);
                break;
            case R.id.tv_article_bookmark:
                bookMarkTrending(activity);
                break;
            case R.id.tv_feed_community_post_user_share:
                if (mIsWhatsAppShare) {
                    shareCardViaSocial(baseResponse, activity, screenName, mFeedDetail);
                } else {
                    shareWithMultipleOption(baseResponse, activity, screenName);
                }
                break;
            case R.id.tv_feed_article_user_share:
                if (mIsWhatsAppShare) {
                    shareCardViaSocial(baseResponse, activity, screenName, mFeedDetail);
                } else {
                    shareWithMultipleOption(baseResponse, activity, screenName);
                }
                break;

            case R.id.tv_article_share:
                if (mIsWhatsAppShare) {
                    shareCardViaSocial(baseResponse, activity, screenName, mFeedDetail);
                } else {
                    shareWithMultipleOption(baseResponse, activity, screenName);
                }
                break;
            /*Card menu option depend on Feed type like post,article etc */

            case R.id.iv_feed_community_post_user_pic:
            case R.id.tv_feed_community_post_user_name:
                openUserProfileLastComment(view, baseResponse, activity);
                break;
            case R.id.tv_feed_community_post_user_menu:
                clickMenuItem(view, baseResponse, FEED_CARD_MENU, activity, screenName);
                break;
            case R.id.tv_spam_post_menu:
                clickMenuItem(view, baseResponse, FEED_CARD_MENU, activity, screenName);
                break;
            case R.id.tv_feed_article_user_menu:
                clickMenuItem(view, baseResponse, FEED_CARD_MENU, activity, screenName);
                break;
            /* All user comment menu option edit,delete */
            case R.id.tv_feed_community_post_user_comment_post_menu:
                clickMenuItem(view, baseResponse, USER_REACTION_COMMENT_MENU, activity, screenName);
                break;
            case R.id.tv_feed_article_user_comment_post_menu:
                clickMenuItem(view, baseResponse, USER_REACTION_COMMENT_MENU, activity, screenName);
                break;
            case R.id.tv_feed_article_total_reactions:
                /*mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);*/
                // openCommentReactionFragment(mFeedDetail);
                LikeListBottomSheetFragment.showDialog((AppCompatActivity) activity, "", mFeedDetail.getEntityOrParticipantId());
                break;
            case R.id.tv_feed_community_post_total_reactions:
                LikeListBottomSheetFragment.showDialog((AppCompatActivity) activity, "", mFeedDetail.getEntityOrParticipantId());
                /*mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);*/
                break;
            case R.id.tv_feed_article_user_comment:
                // mFragmentOpen.setCommentList(true);
                openCommentReactionFragment(activity, mFeedDetail, screenName);
                break;
            case R.id.tv_feed_community_post_user_comment:
                //mFragmentOpen.setCommentList(true);
                openCommentReactionFragment(activity, mFeedDetail, screenName);
                break;
            case R.id.tv_join_conversation:
                if (mFeedDetail instanceof UserPostSolrObj) {
                    PostDetailActivity.navigateTo(activity, screenName, mFeedDetail, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, null, true);
                } else if (mFeedDetail instanceof ArticleSolrObj) {
                    ArticleSolrObj articleSolrObj = (ArticleSolrObj) mFeedDetail;
                    ArticleActivity.navigateTo(activity, mFeedDetail, screenName, null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL, articleSolrObj.isUserStory());
                }
                break;
            case R.id.iv_feed_community_post_login_user_pic:
            case R.id.fl_login_user:
            case R.id.tv_feed_community_post_login_user_name:
            case R.id.feed_img:
                ProfileActivity.navigateTo(activity, mFeedDetail.getProfileId(), mFeedDetail.isAuthorMentor(),
                        AppConstants.PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, false);
                break;
            case R.id.li_feed_article_images:
                ArticleSolrObj articleSolrObj = (ArticleSolrObj) mFeedDetail;
                ArticleActivity.navigateTo(activity, mFeedDetail, "Feed", null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL, articleSolrObj.isUserStory());
                break;
            case R.id.li_article_cover_image:
                String sourceScreen = "";
                ArticleSolrObj articleObj = (ArticleSolrObj) mFeedDetail;
                ArticleActivity.navigateTo(activity, mFeedDetail, screenName, null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL, articleObj.isUserStory());
                break;
            case R.id.li_featured_community_images:
                CommunityDetailActivity.navigateTo(activity, (CommunityFeedSolrObj) mFeedDetail, screenName, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                break;
            case R.id.tv_feed_community_post_card_title:
                if (mFeedDetail instanceof UserPostSolrObj) {
                    if (((UserPostSolrObj) mFeedDetail).getCommunityId() == 0) {
                        ContestActivity.navigateTo(activity, Long.toString(((UserPostSolrObj) mFeedDetail).getUserPostSourceEntityId()), mFeedDetail.getScreenName(), null);
                    } else {
                        CommunityDetailActivity.navigateTo(activity, ((UserPostSolrObj) mFeedDetail).getCommunityId(), screenName, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                    }
                } else {
                    CommunityDetailActivity.navigateTo(activity, ((UserPostSolrObj) mFeedDetail).getCommunityId(), screenName, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                }
                break;
            default:
        }
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public void setConfigurableShareOption(boolean mIsWhatsAppShare) {
        this.mIsWhatsAppShare = mIsWhatsAppShare;
    }

    public void clickMenuItem(View view, final BaseResponse baseResponse, final MenuEnum menuEnum, final Context context, final String screenName) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.menu_option_layout, null);
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopupWindow.dismiss();
            }
        });
        final LinearLayout liFeedMenu = popupView.findViewById(R.id.li_feed_menu);
        final TextView tvEdit = popupView.findViewById(R.id.tv_article_menu_edit);
        final TextView tvDelete = popupView.findViewById(R.id.tv_article_menu_delete);
        final TextView tvShare = popupView.findViewById(R.id.tv_article_menu_share);
        final TextView tvReport = popupView.findViewById(R.id.tv_article_menu_report);

        // final Fragment fragmentCommentReaction = getSupportFragmentManager().findFragmentByTag(CommentReactionFragment.class.getName());
        mPopupWindow.showAsDropDown(view, -150, -10);

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOperationOnMenu(menuEnum, baseResponse, null, context, screenName);
                mPopupWindow.dismiss();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOperationOnMenu(menuEnum, baseResponse, null, screenName, context);
                mPopupWindow.dismiss();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWithMultipleOption(baseResponse, context, screenName);
                mPopupWindow.dismiss();
            }
        });
        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAsSpam(menuEnum, baseResponse, null);
                mPopupWindow.dismiss();
            }
        });
        setMenuOptionVisibility(view, tvEdit, tvDelete, tvShare, tvReport, baseResponse, liFeedMenu, context);
    }

    public void dismissWindow() {
        mPopupWindow.dismiss();
    }

    public void onReferrerReceived(Context context, Boolean isReceived) {
        if (isReceived != null && isReceived) {
            AppInstallationHelper appInstallationHelper = new AppInstallationHelper(context);
            appInstallationHelper.setupAndSaveInstallation(false);
        }
    }

    public void clearReferences() {
        if (null != mSheroesApplication) {
            String currActivityName = mSheroesApplication.getCurrentActivityName();
            if (StringUtil.isNotNullOrEmptyString(currActivityName)) {
                if (this.getClass().getSimpleName().equals(currActivityName))
                    mSheroesApplication.setCurrentActivityName(null);
            }
        }
    }
    //endregion

    //region private methods
    //Open profile from last comment user profile or name click
    private void openUserProfileLastComment(View view, BaseResponse baseResponse, Context context) {
        Comment comment = (Comment) baseResponse;
        if (!comment.isAnonymous() && comment.getParticipantUserId() != null) {
            CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
            communityFeedSolrObj.setIdOfEntityOrParticipant(comment.getParticipantUserId());
            communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
            ProfileActivity.navigateTo((Activity) context, communityFeedSolrObj, comment.getParticipantUserId(), comment.isVerifiedMentor(), 0, AppConstants.COMMUNITY_POST_FRAGMENT, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    private void shareCardViaSocial(BaseResponse baseResponse, Context context, String screenName, FeedDetail mFeedDetail) {
        FeedDetail feedDetail = (FeedDetail) baseResponse;
        String deepLinkUrl;
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getPostShortBranchUrls())) {
            deepLinkUrl = feedDetail.getPostShortBranchUrls();
        } else {
            deepLinkUrl = feedDetail.getDeepLinkUrl();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.setPackage(AppConstants.WHATS_APP_URI);
        intent.putExtra(Intent.EXTRA_TEXT, R.string.check_out_share_msg + deepLinkUrl);
        context.startActivity(intent);
        AnalyticsManager.trackPostAction(Event.POST_SHARED, mFeedDetail, screenName);
    }

    private void shareWithMultipleOption(BaseResponse baseResponse, Context context, String screenName) {
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
        intent.putExtra(R.string.check_out_share_msg + Intent.EXTRA_TEXT, deepLinkUrl);
        context.startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        HashMap<String, Object> properties = MixpanelHelper.getPostProperties(feedDetail, screenName);
        AnalyticsManager.trackEvent(Event.POST_SHARED, screenName, properties);
    }

    private void bookmarkCall(Activity activity) {
        if (activity instanceof ContestActivity) {
            ((ContestActivity) activity).bookmarkPost(mFeedDetail);
        }
    }

    private void openCommentReactionFragment(Context context, FeedDetail feedDetail, String screenName) {
        if (feedDetail instanceof UserPostSolrObj) {
            PostDetailActivity.navigateTo((Activity) context, screenName, feedDetail, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, null, false);
        } else if (feedDetail instanceof ArticleSolrObj) {
            ArticleSolrObj articleSolrObj = (ArticleSolrObj) feedDetail;
            ArticleActivity.navigateTo((Activity) context, feedDetail, screenName, null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL, articleSolrObj.isUserStory());
        }
    }

    private void bookMarkTrending(Activity context) {
        Fragment articleFragment = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
        if (AppUtils.isFragmentUIActive(articleFragment)) {
            ((ArticlesFragment) articleFragment).bookMarkForCard(mFeedDetail);
        }
    }

    private void editOperationOnMenu(MenuEnum menuEnum, BaseResponse baseResponse, Fragment fragmentCommentReaction, Context context, String screenName) {
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
                    mFeedDetail.setTrending(true);
                    if (mFeedDetail instanceof UserPostSolrObj) {
                        ((UserPostSolrObj) mFeedDetail).setIsEditOrDelete(AppConstants.COMMENT_DELETE);
                    } else if (mFeedDetail instanceof PollSolarObj) {
                        ((PollSolarObj) mFeedDetail).setIsEditOrDelete(AppConstants.COMMENT_DELETE);
                    }
                    openCommentReactionFragment(context, mFeedDetail, screenName);
                }
                break;
            case FEED_CARD_MENU:
                if (null != mFeedDetail) {
                    if (mFeedDetail instanceof UserPostSolrObj) {
                        CommunityPostActivity.navigateTo((Activity) context, mFeedDetail, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, null);
                    }
                }
                break;
        }
    }

    private void deleteOperationOnMenu(MenuEnum menuEnum, BaseResponse baseResponse, Fragment fragmentCommentReaction, String screenName, Context context) {
        switch (menuEnum) {
            case USER_COMMENT_ON_CARD_MENU:
                Comment comment = (Comment) baseResponse;
                if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                    comment.setActive(false);
                    comment.setEdit(false);
                }
                break;
            case USER_REACTION_COMMENT_MENU:
                if (null != mFeedDetail) {
                    mFeedDetail.setTrending(true);
                    if (mFeedDetail instanceof UserPostSolrObj) {
                        ((UserPostSolrObj) mFeedDetail).setIsEditOrDelete(AppConstants.COMMENT_DELETE);
                    } else if (mFeedDetail instanceof PollSolarObj) {
                        ((PollSolarObj) mFeedDetail).setIsEditOrDelete(AppConstants.COMMENT_DELETE);
                    }
                    openCommentReactionFragment(context, mFeedDetail, screenName);
                }
                break;
        }
    }

    private void setMenuOptionVisibility(View view, TextView tvEdit, TextView tvDelete, TextView tvShare, TextView tvReport, BaseResponse baseResponse, LinearLayout liFeedMenu, Context context) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_feed_article_user_comment_post_menu:
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_article_user_menu:
                tvShare.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_user_comment_list_menu:
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                    int adminId = 0;
                    if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                        adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
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
                if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
                    int adminId = 0;
                    Long userId = mUserPreference.get().getUserSummary().getUserId();
                    if (null != mUserPreference.get().getUserSummary().getUserBO()) {
                        adminId = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId();
                    }
                    if (mFeedDetail.getAuthorId() == userId || ((UserPostSolrObj) mFeedDetail).isCommunityOwner() || adminId == AppConstants.TWO_CONSTANT) {
                        tvDelete.setVisibility(View.VISIBLE);
                        if (mFeedDetail.getAuthorId() == userId && mFeedDetail instanceof UserPostSolrObj && ((UserPostSolrObj) mFeedDetail).getCommunityId() == 0) {
                            tvEdit.setVisibility(View.GONE);
                        } else {
                            tvEdit.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (mFeedDetail.isFromHome()) {
                            tvReport.setText(context.getString(R.string.ID_REPORTED_AS_SPAM));
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
//                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

    private void markAsSpam(MenuEnum menuEnum, BaseResponse baseResponse, Fragment fragmentCommentReaction) {
        switch (menuEnum) {
            case FEED_CARD_MENU:
                break;
        }
    }

    public void dataOperationOnClick(Context context, BaseResponse baseResponse) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            openImageFullViewFragment(context, feedDetail);
        }
    }

    private void openImageFullViewFragment(Context context, FeedDetail feedDetail) {
        AlbumActivity.navigateTo((Activity) context, feedDetail, "BASE", null);
    }
    //endregion
}
