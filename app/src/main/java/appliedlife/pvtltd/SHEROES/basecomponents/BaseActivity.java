package appliedlife.pvtltd.SHEROES.basecomponents;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.errorview.NetworkTimeoutDialog;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunitiesDetailFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityJoinRegionDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageFullViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFragment;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: Base activity for all activities.
 */
public class BaseActivity extends AppCompatActivity implements BaseHolderInterface, FragmentIntractionWithActivityListner, View.OnTouchListener, View.OnClickListener {
    private final String TAG = LogUtils.makeLogTag(BaseActivity.class);
    protected boolean mIsSavedInstance;
    public boolean mIsDestroyed;
    protected SheroesApplication mSheroesApplication;
    private FragmentOpen mFragmentOpen;
    private FeedDetail mFeedDetail;
    public View mArticlePopUp, mCommunityPopUp, mCommunityPostPopUp;
    private Fragment mFragment;
    private View popupView;
    private PopupWindow popupWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSheroesApplication = (SheroesApplication) this.getApplicationContext();
    }

    public void setAllValues(FragmentOpen fragmentOpen) {
        this.mFragmentOpen = fragmentOpen;
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
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsSavedInstance && !mIsDestroyed) {
            fragment.show(getFragmentManager(), AppConstants.NETWORK_TIMEOUT);
        }
        return fragment;
    }

    @Override
    protected void onDestroy() {
        mIsDestroyed = true;
        clearReferences();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mIsSavedInstance = true;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSheroesApplication.setCurrentActivityName(this.getClass().getSimpleName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mSheroesApplication != null) {
            mSheroesApplication.notifyIfAppInBackground();
        }
        clearReferences();
    }

    private void clearReferences() {
        String currActivityName = mSheroesApplication.getCurrentActivityName();
        if (this.getClass().getSimpleName().equals(currActivityName))
            mSheroesApplication.setCurrentActivityName(null);
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

    protected DialogFragment showCommunityJoinReason(FeedDetail feedDetail) {
        CommunityJoinRegionDialogFragment fragment = (CommunityJoinRegionDialogFragment) getFragmentManager().findFragmentByTag(AppConstants.FEATURED_COMMUNITY);
        if (fragment == null) {
            fragment = new CommunityJoinRegionDialogFragment();
            Bundle b = new Bundle();
            b.putParcelable(BaseDialogFragment.DISMISS_PARENT_ON_OK_OR_BACK, feedDetail);
            fragment.setArguments(b);
        }
        if (!fragment.isVisible() && !fragment.isAdded() && !isFinishing() && !mIsSavedInstance && !mIsDestroyed) {
            fragment.show(getFragmentManager(), AppConstants.FEATURED_COMMUNITY);
        }
        return fragment;
    }

    protected void feedCardsHandled(View view, BaseResponse baseResponse) {
        mFeedDetail = (FeedDetail) baseResponse;
        int id = view.getId();
        switch (id) {
            case R.id.tv_featured_community_join:
                showCommunityJoinReason(mFeedDetail);
                break;
            case R.id.tv_feed_community_user_menu:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_feed_community_post_user_menu:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_feed_article_user_bookmark:
                bookmarkCall();
                break;
            case R.id.tv_feed_community_post_user_bookmark:
                bookmarkCall();
                break;
            case R.id.tv_feed_job_user_bookmark:
                bookmarkCall();
                break;
            case R.id.tv_article_bookmark:
                bookMarkTrending();
                break;
            case R.id.tv_feed_article_user_menu:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_feed_job_user_menu:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_feed_community_post_user_comment_post_menu:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_feed_community_user_comment_post_menu:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_article_menu:
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_feed_article_user_comment_post_menu:
               /* if(popupView.getVisibility()==View.VISIBLE)
                {
                    popupWindow.dismiss();
                }
                else {

                }*/
                clickMenuItem(view, baseResponse, false);
                break;
            case R.id.tv_feed_article_total_reactions:
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.tv_feed_community_total_reactions:
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
            case R.id.li_feed_community_join_conversation:
                mFragmentOpen.setCommentList(true);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.li_feed_community_post_join_conversation:
                mFragmentOpen.setCommentList(true);
                openCommentReactionFragment(mFeedDetail);
                break;
            case R.id.tv_feed_article_user_reaction:
                articleUserReaction();
                break;
            case R.id.tv_feed_community_user_reaction:
                communityUserReaction();
                break;
            case R.id.tv_feed_community_post_user_reaction:
                communityPostUserReaction();
                break;
            case R.id.li_feed_article_images:
                ArticleDetailActivity.navigateFromArticle(this, view, mFeedDetail);
                finish();
                break;
            case R.id.li_feed_community_images:
                CommunitiesDetailActivity.navigate(this, view, mFeedDetail);
                break;
            case R.id.li_feed_community_user_post_images:
                CommunitiesDetailActivity.navigate(this, view, mFeedDetail);
                break;
            case R.id.li_feed_job_card:
              //  JobDetailActivity.navigateFromJob(this, view, mFeedDetail);
                break;
            case R.id.li_article_cover_image:
                ArticleDetailActivity.navigateFromArticle(this, view, mFeedDetail);
                finish();
                break;
            case R.id.li_community_images:
                CommunitiesDetailActivity.navigate(this, view, mFeedDetail);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    private void communityPostUserReaction() {
        mCommunityPostPopUp = findViewById(R.id.li_feed_community_user_post_emoji_pop_up);
        if (mCommunityPostPopUp.getVisibility() == View.VISIBLE) {
            mCommunityPostPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPostPopUp);
        } else {
            mCommunityPostPopUp = findViewById(R.id.li_feed_community_user_post_emoji_pop_up);
            TextView tvCommunityPostReaction = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction);
            TextView tvCommunityPostReaction1 = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction1);
            TextView tvCommunityPostReaction2 = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction2);
            TextView tvCommunityPostReaction3 = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction3);
            TextView tvCommunityPostReaction4 = (TextView) mCommunityPostPopUp.findViewById(R.id.tv_reaction4);
            mCommunityPostPopUp.setOnTouchListener(this);
            tvCommunityPostReaction.setOnClickListener(this);
            tvCommunityPostReaction1.setOnClickListener(this);
            tvCommunityPostReaction2.setOnClickListener(this);
            tvCommunityPostReaction3.setOnClickListener(this);
            tvCommunityPostReaction4.setOnClickListener(this);
            mCommunityPostPopUp.setVisibility(View.VISIBLE);
            showUserReactionOption(mCommunityPostPopUp);
        }
    }

    private void communityUserReaction() {
        mCommunityPopUp = findViewById(R.id.li_feed_community_emoji_pop_up);
        if (mCommunityPopUp.getVisibility() == View.VISIBLE) {
            mCommunityPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPopUp);
        } else {
            TextView tvCommunityReaction = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction);
            TextView tvCommunityReaction1 = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction1);
            TextView tvCommunityReaction2 = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction2);
            TextView tvCommunityReaction3 = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction3);
            TextView tvCommunityReaction4 = (TextView) mCommunityPopUp.findViewById(R.id.tv_reaction4);
            mCommunityPopUp.setOnTouchListener(this);
            tvCommunityReaction.setOnClickListener(this);
            tvCommunityReaction1.setOnClickListener(this);
            tvCommunityReaction2.setOnClickListener(this);
            tvCommunityReaction3.setOnClickListener(this);
            tvCommunityReaction4.setOnClickListener(this);
            mCommunityPopUp.setVisibility(View.VISIBLE);
            showUserReactionOption(mCommunityPopUp);
        }
    }

    private void articleUserReaction() {
        mArticlePopUp = findViewById(R.id.li_feed_article_card_emoji_pop_up);
        if (mArticlePopUp.getVisibility() == View.VISIBLE) {
            mArticlePopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mArticlePopUp);
        } else {
            TextView tvArticleReaction = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction);
            TextView tvArticleReaction1 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction1);
            TextView tvArticleReaction2 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction2);
            TextView tvArticleReaction3 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction3);
            TextView tvArticleReaction4 = (TextView) mArticlePopUp.findViewById(R.id.tv_reaction4);
            mArticlePopUp.setOnTouchListener(this);
            tvArticleReaction.setOnClickListener(this);
            tvArticleReaction1.setOnClickListener(this);
            tvArticleReaction2.setOnClickListener(this);
            tvArticleReaction3.setOnClickListener(this);
            tvArticleReaction4.setOnClickListener(this);
            mArticlePopUp.setVisibility(View.VISIBLE);
            showUserReactionOption(mArticlePopUp);
        }
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
                    ((BookmarksFragment) fragmentBookMark).bookMarkForCard(mFeedDetail);
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

    protected void clickMenuItem(View view, final BaseResponse baseResponse, final boolean isCommentReaction) {
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
        final TextView tvEdit = (TextView) popupView.findViewById(R.id.tv_article_menu_edit);
        final TextView tvDelete = (TextView) popupView.findViewById(R.id.tv_article_menu_delete);
        final TextView tvShare = (TextView) popupView.findViewById(R.id.tv_article_menu_share);
        final TextView tvReport = (TextView) popupView.findViewById(R.id.tv_article_menu_report);
        final Fragment fragmentCommentReaction = getSupportFragmentManager().findFragmentByTag(CommentReactionFragment.class.getName());
        popupWindow.showAsDropDown(view, -140, 0);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCommentReaction) {
                    CommentReactionDoc commentReactionDoc = (CommentReactionDoc) baseResponse;
                    if (null != commentReactionDoc) {
                        if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                            commentReactionDoc.setActive(true);
                            commentReactionDoc.setEdit(true);
                            ((CommentReactionFragment) fragmentCommentReaction).editCommentInList(commentReactionDoc);
                        }
                    }
                } else {
                    if (null != mFeedDetail) {
                        mFragmentOpen.setCommentList(true);
                        mFeedDetail.setTrending(true);
                        mFeedDetail.setExperienceFromI(AppConstants.ONE_CONSTANT);
                        openCommentReactionFragment(mFeedDetail);
                    }
                }
                popupWindow.dismiss();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCommentReaction) {
                    CommentReactionDoc commentReactionDoc = (CommentReactionDoc) baseResponse;
                    if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                        commentReactionDoc.setActive(false);
                        commentReactionDoc.setEdit(false);
                        ((CommentReactionFragment) fragmentCommentReaction).deleteCommentFromList(commentReactionDoc);
                    }
                } else {
                    if (null != mFeedDetail) {
                        mFragmentOpen.setCommentList(true);
                        mFragmentOpen.setCommentList(true);
                        mFeedDetail.setTrending(true);
                        mFeedDetail.setExperienceFromI(AppConstants.TWO_CONSTANT);
                        openCommentReactionFragment(mFeedDetail);
                    }
                }
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
                FeedDetail feedDetail = (FeedDetail) baseResponse;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType(AppConstants.SHARE_MENU_TYPE);
                intent.putExtra(Intent.EXTRA_TEXT, feedDetail.getSlugS());
                intent.putExtra(Intent.EXTRA_SUBJECT, feedDetail.getDescription());
                startActivity(Intent.createChooser(intent, AppConstants.SHARE));
                popupWindow.dismiss();
            }
        });
        tvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You Clicked : " + tvReport.getText(), Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
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
                tvShare.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_user_comment_list_menu:
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_community_post_user_comment_post_menu:
              /*  //if owner
                tvDelete.setVisibility(View.VISIBLE);
                //if commenter*/
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_community_user_comment_post_menu:
              /*  //if owner
                tvDelete.setVisibility(View.VISIBLE);
                //if commenter*/
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_article_menu:
                tvShare.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_feed_community_post_user_menu:
                FeedDetail feedPostDetail = (FeedDetail) baseResponse;
                tvShare.setVisibility(View.VISIBLE);
              /*  if(null!=feedPostDetail)
                {
                    //If creator then
                    tvEdit.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.VISIBLE);
                    tvShare.setVisibility(View.VISIBLE);

                    //if owner then
                    tvDelete.setVisibility(View.VISIBLE);
                    tvShare.setVisibility(View.VISIBLE);
                    tvReport.setVisibility(View.VISIBLE);
                    //if Other then
                    tvShare.setVisibility(View.VISIBLE);
                    tvReport.setVisibility(View.VISIBLE);
                }*/
                break;
            case R.id.tv_feed_community_user_menu:
                FeedDetail feedCommunityDetail = (FeedDetail) baseResponse;
                if (null != feedCommunityDetail) {
                    //If creator then
                    tvEdit.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.VISIBLE);
                    tvShare.setVisibility(View.VISIBLE);

                    //if owner then
                    tvDelete.setVisibility(View.VISIBLE);
                    tvShare.setVisibility(View.VISIBLE);
                    tvReport.setVisibility(View.VISIBLE);
                    //if Other then
                    tvShare.setVisibility(View.VISIBLE);
                    tvReport.setVisibility(View.VISIBLE);
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

    private void openImageFullViewFragment(FeedDetail feedDetail) {
        ImageFullViewFragment imageFullViewFragment = new ImageFullViewFragment();
        Bundle bundle = new Bundle();
        mFragmentOpen.setCommentList(true);
        bundle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
        bundle.putParcelable(AppConstants.IMAGE_FULL_VIEW, feedDetail);
        imageFullViewFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.fl_feed_comments, imageFullViewFragment, ImageFullViewFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

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
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                        .replace(R.id.about_community_container, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

            }
        } else {
            CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
            Bundle bundleArticle = new Bundle();
            bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
            bundleArticle.putParcelable(AppConstants.COMMENTS, feedDetail);
            commentReactionFragmentForArticle.setArguments(bundleArticle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                    .replace(R.id.fl_feed_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    private void showUserReactionOption(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    private void dismissUserReactionOption(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        viewToAnimate.clearAnimation();
        animation.setFillAfter(false);
    }


    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        if (AppUtils.isFragmentUIActive(mFragment)) {
            if (mFragment instanceof CommunitiesDetailFragment) {
                ((CommunitiesDetailFragment) mFragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
            }
        } else {
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
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_article_card_emoji_pop_up:
                if (null != mArticlePopUp) {
                    mArticlePopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mArticlePopUp);
                }
                break;
            case R.id.li_feed_community_emoji_pop_up:
                if (null != mCommunityPopUp) {
                    mCommunityPopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mCommunityPopUp);
                }
                break;
            case R.id.li_feed_community_user_post_emoji_pop_up:
                if (null != mCommunityPostPopUp) {
                    mCommunityPostPopUp.setVisibility(View.GONE);
                    dismissUserReactionOption(mCommunityPostPopUp);
                }
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_reaction:
                heartEmojiSelection();
                break;
            case R.id.tv_reaction1:
                emojiFirstSelection();
                break;
            case R.id.tv_reaction2:
                emojiSecondSelection();

                break;
            case R.id.tv_reaction3:
                emojiThirdSelection();

                break;
            case R.id.tv_reaction4:
                emojiFourthSelection();
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + "  " + TAG + " " + id);
        }
    }

    private void emojiFourthSelection() {
        if (null != mArticlePopUp) {
            mArticlePopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mArticlePopUp);
        }
        if (null != mCommunityPopUp) {
            mCommunityPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPopUp);
        }
        if (null != mCommunityPostPopUp) {
            mCommunityPostPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPostPopUp);
        }
        userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_FOURTH_REACTION_CONSTANT, mFeedDetail.getItemPosition());
    }

    private void emojiThirdSelection() {
        if (null != mArticlePopUp) {
            mArticlePopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mArticlePopUp);
        }
        if (null != mCommunityPopUp) {
            mCommunityPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPopUp);
        }
        if (null != mCommunityPostPopUp) {
            mCommunityPostPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPostPopUp);
        }
        userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_THIRD_REACTION_CONSTANT, mFeedDetail.getItemPosition());
    }

    private void emojiSecondSelection() {
        if (null != mArticlePopUp) {
            mArticlePopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mArticlePopUp);
        }
        if (null != mCommunityPopUp) {
            mCommunityPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPopUp);
        }
        if (null != mCommunityPostPopUp) {
            mCommunityPostPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPostPopUp);
        }
        userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_SECOND_REACTION_CONSTANT, mFeedDetail.getItemPosition());
    }

    public void heartEmojiSelection() {
        if (null != mArticlePopUp) {
            mArticlePopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mArticlePopUp);
        }

        if (null != mCommunityPopUp) {
            mCommunityPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPopUp);
        }
        if (null != mCommunityPostPopUp) {
            mCommunityPostPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPostPopUp);
        }
        userCommentLikeRequest(mFeedDetail, AppConstants.HEART_REACTION_CONSTANT, mFeedDetail.getItemPosition());
    }

    public void emojiFirstSelection() {
        if (null != mArticlePopUp) {
            mArticlePopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mArticlePopUp);
        }
        if (null != mCommunityPopUp) {
            mCommunityPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPopUp);
        }
        if (null != mCommunityPostPopUp) {
            mCommunityPostPopUp.setVisibility(View.GONE);
            dismissUserReactionOption(mCommunityPostPopUp);
        }
        userCommentLikeRequest(mFeedDetail, AppConstants.EMOJI_FIRST_REACTION_CONSTANT, mFeedDetail.getItemPosition());
    }

    @Override
    public void onShowErrorDialog(String errorReason, int errorFor) {

    }
}
