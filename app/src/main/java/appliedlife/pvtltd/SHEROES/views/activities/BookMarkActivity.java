package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.adapters.ImageFullViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageFullViewFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 09-03-2017.
 */

public class BookMarkActivity extends BaseActivity implements FragmentIntractionWithActivityListner, ImageFullViewAdapter.HomeActivityIntraction, CommentReactionFragment.HomeActivityIntractionListner {
    private final String TAG = LogUtils.makeLogTag(BookMarkActivity.class);
    @Bind(R.id.tv_setting)
    TextView mTvSetting;
    @Bind(R.id.tv_search_box)
    TextView mTvSearchBox;
    @Bind(R.id.iv_home_notification_icon)
    ImageView mIvHomeNotification;
    private int mClickValue;
    private FragmentOpen mFragmentOpen;
    private FeedDetail mFeedDetail;
    public View mArticlePopUp, mCommunityPopUp, mCommunityPostPopUp;
    @Bind(R.id.fl_home_footer_list)
    public FrameLayout mFlHomeFooterList;
    @Bind(R.id.tv_home)
    TextView mTvHome;
    @Bind(R.id.tv_communities)
    TextView mTvCommunities;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mClickValue = getIntent().getExtras().getInt(AppConstants.HOME_FRAGMENT);
        }
        mFragmentOpen = new FragmentOpen();
        mFragmentOpen.setBookmarkFragment(true);
        setAllValues(mFragmentOpen);
        renderLoginFragmentView();
    }

    public void renderLoginFragmentView() {
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);
        mTvSetting.setText(getString(R.string.ID_BOOKMARKS));
        mTvSetting.setVisibility(View.VISIBLE);
        mTvSearchBox.setVisibility(View.GONE);
        mIvHomeNotification.setVisibility(View.INVISIBLE);
        mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
        mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
        mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
        mTvCommunities.setText(AppConstants.EMPTY_STRING);
        mTvHome.setText(AppConstants.EMPTY_STRING);
        BookmarksFragment bookmarksFragment = new BookmarksFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_bookmark, bookmarksFragment, BookmarksFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    @OnClick(R.id.tv_drawer_navigation)
    public void drawerNavigationClick() {
        mClickValue = AppConstants.TWO_CONSTANT;
        bookMarkResultToHome();
    }

    @OnClick(R.id.tv_home)
    public void homeOnClick() {
        mClickValue = AppConstants.NO_REACTION_CONSTANT;
        bookMarkResultToHome();
    }

    @OnClick(R.id.tv_communities)
    public void communityOnClick() {
        mClickValue = AppConstants.ONE_CONSTANT;
        bookMarkResultToHome();
    }

    private void bookMarkResultToHome() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.BOOKMARKS, mClickValue);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onShowErrorDialog() {
        Toast.makeText(this, "Please check your email id, password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivityFromHolder(Intent intent) {

    }

    @Override
    public void setAllValues(FragmentOpen fragmentOpen) {
        super.setAllValues(fragmentOpen);
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            mFeedDetail = (FeedDetail) baseResponse;
            int id = view.getId();
            if (id == R.id.tv_feed_article_user_bookmark) {
                bookmarkCall();
            } else if (id == R.id.tv_feed_community_post_user_bookmark) {
                bookmarkCall();
            } else if (id == R.id.tv_feed_job_user_bookmark) {
                bookmarkCall();
            } else if (id == R.id.tv_feed_article_total_reactions) {
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
            } else if (id == R.id.tv_feed_community_total_reactions) {
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
            } else if (id == R.id.tv_feed_community_post_total_reactions) {
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
            } else if (id == R.id.li_feed_article_join_conversation) {
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
            } else if (id == R.id.li_feed_community_join_conversation) {
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
            } else if (id == R.id.li_feed_community_post_join_conversation) {
                mFragmentOpen.setCommentList(false);
                mFragmentOpen.setReactionList(true);
                openCommentReactionFragment(mFeedDetail);
            } else {
                super.feedCardsHandled(view, baseResponse);
            }
        } else if (baseResponse instanceof CommentReactionDoc) {
            super.clickMenuItem(view, baseResponse, true);
        }
    }

    protected void openCommentReactionFragment(FeedDetail feedDetail) {
        CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
        Bundle bundleArticle = new Bundle();
        bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
        bundleArticle.putParcelable(AppConstants.COMMENTS, feedDetail);
        commentReactionFragmentForArticle.setArguments(bundleArticle);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                .replace(R.id.fl_feed_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

    }

    @Override
    public void onErrorOccurence() {
        getSupportFragmentManager().popBackStack();
        //showNetworkTimeoutDoalog(true);
    }

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        onBackPressed();
    }

    private void bookmarkCall() {
        Fragment fragmentBookMark = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
        if (AppUtils.isFragmentUIActive(fragmentBookMark)) {
            ((BookmarksFragment) fragmentBookMark).bookMarkForCard(mFeedDetail);
        }
    }

    @Override
    public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
        super.userCommentLikeRequest(baseResponse, reactionValue, position);
    }

    @Override
    public void dataOperationOnClick(BaseResponse baseResponse) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            openImageFullViewFragment(feedDetail);
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

    @Override
    public void onDialogDissmiss(FragmentOpen isFragmentOpen) {

    }

    @Override
    public void onBackPressed() {
        if (mFragmentOpen.isOpen()) {
            getSupportFragmentManager().popBackStackImmediate();
            mFragmentOpen.setOpen(false);
        } else if (mFragmentOpen.isCommentList()) {
            getSupportFragmentManager().popBackStackImmediate();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(BookmarksFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((BookmarksFragment) fragment).commentListRefresh(mFeedDetail);
            }
            mFragmentOpen.setCommentList(false);
        } else if (mFragmentOpen.isReactionList()) {
            getSupportFragmentManager().popBackStack();
            mFragmentOpen.setReactionList(false);
            mFragmentOpen.setCommentList(true);
        } else {
            finish();
        }
    }

    @Override
    public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
        mFragmentOpen = isFragmentOpen;
        mFeedDetail = feedDetail;
        if (mFragmentOpen.isReactionList()) {
            openCommentReactionFragment(mFeedDetail);
        }
    }
}

