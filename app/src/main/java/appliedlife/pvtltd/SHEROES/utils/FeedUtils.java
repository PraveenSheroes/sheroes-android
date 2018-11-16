package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.f2prateek.rx.preferences2.Preference;

import org.parceler.Parcels;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
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
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
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
import appliedlife.pvtltd.SHEROES.views.fragments.UserPostFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunityOptionJoinDialog;

import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.FEED_CARD_MENU;
import static appliedlife.pvtltd.SHEROES.enums.MenuEnum.USER_REACTION_COMMENT_MENU;

public class FeedUtils {

    //region constant variables
    private static final int ASK_QUESTION_POST = 3;
    //endregion

    //region member variables
    private static FeedUtils mInstance;
    private FeedDetail mFeedDetail;
    private Fragment mFragment;
    private boolean mIsWhatsAppShare;
    private long mUserId;
    public PopupWindow popupWindow;
    private boolean mIsDestroyed = false;
    SheroesApplication mSheroesApplication;
    //endregion

    //region injected variables
    @Inject
    Preference<LoginResponse> mUserPreference;
    //endregion

    public static synchronized FeedUtils getInstance() {
        if (mInstance == null) {
            mInstance = new FeedUtils();
        }
        return mInstance;
    }

    @Inject
    public FeedUtils() {
        SheroesApplication.getAppComponent(SheroesApplication.mContext).inject(this);
    }

    public void feedCardsHandled(View view, BaseResponse baseResponse, Activity activity, String screenName) {
        mFeedDetail = (FeedDetail) baseResponse;
        int id = view.getId();
        switch (id) {
            case R.id.tv_featured_community_join:
                ((SheroesApplication) activity.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_COMMUNITY_MEMBERSHIP, GoogleAnalyticsEventActions.REQUEST_JOIN_OPEN_COMMUNITY, AppConstants.EMPTY_STRING);
                break;
            case R.id.tv_feed_article_user_bookmark:
                bookmarkCall(activity);
                break;
            case R.id.tv_event_going_btn:
                bookmarkCall(activity);
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
            case R.id.tv_feed_review_post_user_share_ic:
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
            case R.id.tv_event_share_btn:
                shareWithMultipleOption(baseResponse, activity, screenName);
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
                ProfileActivity.navigateTo(activity, mFeedDetail.getProfileId(), mFeedDetail.isAuthorMentor(), AppConstants.PROFILE_NOTIFICATION_ID, AppConstants.FEED_SCREEN, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL);
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
                        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
                            mUserId = mUserPreference.get().getUserSummary().getUserId();
                            openGenericCardInWebView(mFeedDetail, activity);
                        }
                    }
                } else {
                    if (mFeedDetail instanceof UserPostSolrObj) {
                        if (((UserPostSolrObj) mFeedDetail).getCommunityId() == 0) {
                            ContestActivity.navigateTo(activity, Long.toString(((UserPostSolrObj) mFeedDetail).getUserPostSourceEntityId()), mFeedDetail.getScreenName(), null);
                        } else {
                            CommunityDetailActivity.navigateTo(activity, ((UserPostSolrObj) mFeedDetail).getCommunityId(), screenName, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);
                        }
                    } else {
                        CommunityDetailActivity.navigateTo(activity, ((UserPostSolrObj) mFeedDetail).getCommunityId(), screenName, null, AppConstants.REQUEST_CODE_FOR_COMMUNITY_DETAIL);

                    }
                }
                break;
            case R.id.tv_feed_review_card_title:
                if (null != mFeedDetail) {
                    if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get().getUserSummary()) {
                        mUserId = mUserPreference.get().getUserSummary().getUserId();
                        openGenericCardInWebView(mFeedDetail, activity);
                    }
                }
                break;
            default:
//                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    private void bookmarkCall(Activity activity) {
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof UserPostFragment) {
                ((UserPostFragment) mFragment).bookMarkForCard(mFeedDetail);
            }
        }
        if (activity instanceof ContestActivity) {
            ((ContestActivity) activity).bookmarkPost(mFeedDetail);
        }
    }

    public void openCommentReactionFragment(Context context, FeedDetail feedDetail, String screenName) {
        if (feedDetail instanceof UserPostSolrObj) {
            PostDetailActivity.navigateTo((Activity) context, screenName, feedDetail, AppConstants.REQUEST_CODE_FOR_POST_DETAIL, null, false);
        } else if (feedDetail instanceof ArticleSolrObj) {
            ArticleSolrObj articleSolrObj = (ArticleSolrObj) feedDetail;
            ArticleActivity.navigateTo((Activity) context, feedDetail, screenName, null, AppConstants.REQUEST_CODE_FOR_ARTICLE_DETAIL, articleSolrObj.isUserStory());
        }
    }

    private void openGenericCardInWebView(FeedDetail feedDetail, Context context) {
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getDeepLinkUrl())) {
            Uri url = Uri.parse(feedDetail.getDeepLinkUrl());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(url);
            context.startActivity(intent);
        }
    }

    public DialogFragment showCommunityJoinReason(FeedDetail feedDetail, Activity context) {
        CommunityOptionJoinDialog fragment = (CommunityOptionJoinDialog) context.getFragmentManager().findFragmentByTag(CommunityOptionJoinDialog.class.getName());
        if (fragment == null) {
            fragment = new CommunityOptionJoinDialog();
            Bundle b = new Bundle();
            Parcelable parcelable = Parcels.wrap(feedDetail);
            b.putParcelable(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, parcelable);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !context.isFinishing() && !mIsDestroyed) {
            fragment.show(context.getFragmentManager(), CommunityOptionJoinDialog.class.getName());
        }
        return fragment;
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
//        moEngageUtills.entityMoEngageCardShareVia(getApplicationContext(), mMoEHelper, payloadBuilder, feedDetail, MoEngageConstants.SHARE_VIA_SOCIAL);
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
//        moEngageUtills.entityMoEngageCardShareVia(getApplicationContext(), mMoEHelper, payloadBuilder, feedDetail, MoEngageConstants.SHARE_VIA_SOCIAL);
        HashMap<String, Object> properties = MixpanelHelper.getPostProperties(feedDetail, screenName);
        AnalyticsManager.trackEvent(Event.POST_SHARED, screenName, properties);
    }

    public void setConfigurableShareOption(boolean mIsWhatsAppShare) {
        this.mIsWhatsAppShare = mIsWhatsAppShare;
    }

    //Open profile from last comment user profile or name click
    public void openUserProfileLastComment(View view, BaseResponse baseResponse, Context context) {
        Comment comment = (Comment) baseResponse;
        if (!comment.isAnonymous() && comment.getParticipantUserId() != null) {
            CommunityFeedSolrObj communityFeedSolrObj = new CommunityFeedSolrObj();
            communityFeedSolrObj.setIdOfEntityOrParticipant(comment.getParticipantUserId());
            communityFeedSolrObj.setCallFromName(AppConstants.GROWTH_PUBLIC_PROFILE);
            ProfileActivity.navigateTo((Activity) context, communityFeedSolrObj, comment.getParticipantUserId(), comment.isVerifiedMentor(), 0, AppConstants.COMMUNITY_POST_FRAGMENT, null, AppConstants.REQUEST_CODE_FOR_MENTOR_PROFILE_DETAIL);
        }
    }

    private void bookMarkTrending(Activity context) {
        Fragment articleFragment = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
        if (AppUtils.isFragmentUIActive(articleFragment)) {
            ((ArticlesFragment) articleFragment).bookMarkForCard(mFeedDetail);
        }
    }

    public void clickMenuItem(View view, final BaseResponse baseResponse, final MenuEnum menuEnum, final Context context, final String screenName) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.menu_option_layout, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });
        final LinearLayout liFeedMenu = popupView.findViewById(R.id.li_feed_menu);
        final TextView tvEdit = popupView.findViewById(R.id.tv_article_menu_edit);
        final TextView tvDelete = popupView.findViewById(R.id.tv_article_menu_delete);
        final TextView tvShare = popupView.findViewById(R.id.tv_article_menu_share);
        final TextView tvReport = popupView.findViewById(R.id.tv_article_menu_report);

        // final Fragment fragmentCommentReaction = getSupportFragmentManager().findFragmentByTag(CommentReactionFragment.class.getName());
        popupWindow.showAsDropDown(view, -150, -10);

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOperationOnMenu(menuEnum, baseResponse, null, context, screenName);
                popupWindow.dismiss();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOperationOnMenu(menuEnum, baseResponse, null, screenName, context);
                popupWindow.dismiss();
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareWithMultipleOption(baseResponse, context, screenName);
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
        setMenuOptionVisibility(view, tvEdit, tvDelete, tvShare, tvReport, baseResponse, liFeedMenu, context);
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
                        UserPostSolrObj userPostSolrObj = (UserPostSolrObj) mFeedDetail;
                        if (userPostSolrObj.getCommTypeId() == ASK_QUESTION_POST) {
                            userPostSolrObj.askQuestionFromMentor = AppConstants.MENTOR_CREATE_QUESTION;
                            CommunityPostActivity.navigateTo((Activity) context, userPostSolrObj, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, null);
                        } else {
                            CommunityPostActivity.navigateTo((Activity) context, mFeedDetail, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, null);
                        }
                    }
                }
                break;
        }
    }


    public void deleteOperationOnMenu(MenuEnum menuEnum, BaseResponse baseResponse, Fragment fragmentCommentReaction, String screenName, Context context) {
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
                    if (mFragment instanceof UserPostFragment) {
                        if (AppUtils.isFragmentUIActive(mFragment)) {
                            ((UserPostFragment) mFragment).deleteCommunityPost(mFeedDetail);
                        }
                    }
                    ((SheroesApplication) context.getApplicationContext()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_DELETED_CONTENT, GoogleAnalyticsEventActions.DELETED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
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
                if (null != mFeedDetail) {

                }
                break;

        }
    }

    public void dataOperationOnClick(Context context, BaseResponse baseResponse) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            openImageFullViewFragment(context, feedDetail);
        }
    }

    public void openImageFullViewFragment(Context context, FeedDetail feedDetail) {
        AlbumActivity.navigateTo((Activity) context, feedDetail, "BASE", null);
    }


    public void onDestroy() {
        mIsDestroyed = true;
    }

    public void dismissWindow() {
        popupWindow.dismiss();
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

}
