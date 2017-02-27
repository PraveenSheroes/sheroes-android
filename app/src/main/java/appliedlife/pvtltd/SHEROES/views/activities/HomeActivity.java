package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.models.entities.communities.CommunitySuggestion;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.DrawerItems;
import appliedlife.pvtltd.SHEROES.models.entities.home.FragmentOpen;
import appliedlife.pvtltd.SHEROES.models.entities.home.HomeSpinnerItem;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.FilterList;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobLocationList;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.ArticleCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.MyCommunities;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.CustomeDataList;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.ImageFullViewAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.BlurrImage;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustiomActionBarToggle;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragmentlistner.FragmentIntractionWithActivityListner;
import appliedlife.pvtltd.SHEROES.views.fragments.ArticlesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.BookmarksFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.CommentReactionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeaturedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.HomeSpinnerFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.ImageFullViewFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFilterFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFilterFragment.HomeActivityJobFilterIntractionListner;
import appliedlife.pvtltd.SHEROES.views.fragments.JobFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.JobLocationFilter;
import appliedlife.pvtltd.SHEROES.views.fragments.MyCommunitiesFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingAboutFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFeedbackFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.SettingTermsAndConditionFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.SettingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
public class HomeActivity extends BaseActivity implements  SettingView, BaseHolderInterface, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, CommentReactionFragment.HomeActivityIntractionListner, View.OnTouchListener, View.OnClickListener, ImageFullViewAdapter.HomeActivityIntraction {
*/
    public class HomeActivity extends BaseActivity implements JobFragment.HomeActivityIntractionListner,FragmentIntractionWithActivityListner, SettingView, BaseHolderInterface, CustiomActionBarToggle.DrawerStateListener, NavigationView.OnNavigationItemSelectedListener, CommentReactionFragment.HomeActivityIntractionListner, View.OnTouchListener, View.OnClickListener, ImageFullViewAdapter.HomeActivityIntraction {
        private final String TAG = LogUtils.makeLogTag(HomeActivity.class);

        @Bind(R.id.iv_drawer_profile_circle_icon)
        RoundedImageView ivDrawerProfileCircleIcon;
        @Bind(R.id.tv_user_name)
        TextView mTvUserName;
        @Bind(R.id.tv_user_location)
        TextView mTvUserLocation;
        @Bind(R.id.cl_main_layout)
        View mCLMainLayout;
        @Bind(R.id.home_toolbar)
        Toolbar mToolbar;
        @Bind(R.id.drawer_layout)
        DrawerLayout mDrawer;
        @Bind(R.id.nav_view)
        NavigationView mNavigationView;
        @Bind(R.id.rv_drawer)
        RecyclerView mRecyclerView;
        @Bind(R.id.home_view_pager)
        ViewPager mViewPager;
        @Bind(R.id.tab_community_view)
        TabLayout mTabLayout;
        @Bind(R.id.fl_home_footer_list)
        public FrameLayout mFlHomeFooterList;
        @Bind(R.id.iv_footer_button_icon)
        ImageView mIvFooterButtonIcon;
        @Bind(R.id.tv_search_box)
        TextView mTvSearchBox;
        @Bind(R.id.tv_setting)
        TextView mTvSetting;
        @Bind(R.id.tv_home)
        TextView mTvHome;
        @Bind(R.id.tv_communities)
        TextView mTvCommunities;
        @Bind(R.id.tv_spinner_icon)
        public TextView mTvSpinnerIcon;
        @Bind(R.id.fl_feed_full_view)
        public FrameLayout flFeedFullView;
        @Bind(R.id.iv_side_drawer_profile_blur_background)
        ImageView mIvSideDrawerProfileBlurBackground;
        @Bind(R.id.iv_home_notification_icon)
        ImageView mIvHomeNotification;
        @Bind(R.id.fab_add_community)
        FloatingActionButton mFloatingActionButton;
        @Bind(R.id.li_home_community_button_layout)
        LinearLayout liHomeCommunityButtonLayout;
        @Bind(R.id.rl_search_box)
        RelativeLayout mRlSearchBox;
        GenericRecyclerViewAdapter mAdapter;
        private List<HomeSpinnerItem> mHomeSpinnerItemList = new ArrayList<>();
        private HomeSpinnerFragment mHomeSpinnerFragment;
        private FragmentOpen mFragmentOpen;
        private CustiomActionBarToggle mCustiomActionBarToggle;
        public View mArticlePopUp, mCommunityPopUp, mCommunityPostPopUp;
        private FeedDetail mFeedDetail;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            SheroesApplication.getAppComponent(this).inject(this);
            renderHomeFragmentView();
            assignNavigationRecyclerListView();
        }

        @Override
        protected void onResume() {
            super.onResume();
        }

        public void renderHomeFragmentView() {
            setContentView(R.layout.activity_home);
            ButterKnife.bind(this);

            mCustiomActionBarToggle = new CustiomActionBarToggle(this, mDrawer, mToolbar, R.string.ID_NAVIGATION_DRAWER_OPEN, R.string.ID_NAVIGATION_DRAWER_CLOSE, this);

            mDrawer.addDrawerListener(mCustiomActionBarToggle);
            //TODO: this data to be removed
            String profile = "https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAhNAAAAJDYwZWIyZTg5LWFmOTItNGIwYS05YjQ5LTM2YTRkNGQ2M2JlNw.jpg";
            Glide.with(this)
                    .load(profile)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivDrawerProfileCircleIcon);
            mTvUserName.setText("Praveen Singh");
            mTvUserLocation.setText("Delhi, India");
            mCustiomActionBarToggle.syncState();
            mNavigationView.setNavigationItemSelectedListener(this);
            mFragmentOpen = new FragmentOpen();
            initHomeViewPagerAndTabs();
            mHomeSpinnerItemList = CustomeDataList.makeSpinnerListRequest();
            Glide.with(this)
                    .load(profile).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {

                            Bitmap blurred = BlurrImage.blurRenderScript(HomeActivity.this, profileImage, 10);
                            mIvSideDrawerProfileBlurBackground.setImageBitmap(blurred);
                        }
                    });
            //  HomeSpinnerFragment frag = new HomeSpinnerFragment();
            //  callFirstFragment(R.id.fl_fragment_container, frag);
            //   new Handler().postDelayed(openDrawerRunnable(), 200);
        }

        private Runnable openDrawerRunnable() {
            return new Runnable() {

                @Override
                public void run() {
                    //  mDrawer.openDrawer(Gravity.LEFT);
                }
            };
        }

        @Override
        public void onErrorOccurence() {
            getSupportFragmentManager().popBackStack();
            //showNetworkTimeoutDoalog(true);
        }

        @Override
        public void openFilter() {
            Intent intent = new Intent(getApplicationContext(), JobFilterActivity.class);
            startActivity(intent);
        }


        @Override
        public void startActivityFromHolder(Intent intent) {

        }

        @Override
        public void handleOnClick(BaseResponse baseResponse, View view) {
            if (baseResponse instanceof FeedDetail) {
                feedCardsHandled(view, baseResponse);
            } else if (baseResponse instanceof HomeSpinnerItem) {
                String spinnerHeaderName = ((HomeSpinnerItem) baseResponse).getName();
                if (StringUtil.isNotNullOrEmptyString(spinnerHeaderName)) {
                    if (spinnerHeaderName.equalsIgnoreCase(AppConstants.FEED_ARTICLE)) {
                        mFragmentOpen.setOpen(true);
                    } else {
                        for (int i = 0; i < mHomeSpinnerItemList.size(); i++) {
                            mHomeSpinnerItemList.get(i).setDone(true);
                        }
                        mTvSpinnerIcon.setText(spinnerHeaderName);
                        mFragmentOpen.setOpen(true);
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ArticlesFragment.class.getName());
                        if (AppUtils.isFragmentUIActive(fragment)) {
                            ((ArticlesFragment) fragment).categorySearchInArticle(mHomeSpinnerItemList);
                        }
                    }
                } else {
                    mHomeSpinnerItemList.clear();
                    mHomeSpinnerItemList = CustomeDataList.makeSpinnerListRequest();
                }
                onBackPressed();
            } else if (baseResponse instanceof DrawerItems) {
                int drawerItem = ((DrawerItems) baseResponse).getId();
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                }
                switch (drawerItem) {
                    case 1:
                        String profile = "https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAhNAAAAJDYwZWIyZTg5LWFmOTItNGIwYS05YjQ5LTM2YTRkNGQ2M2JlNw.jpg";
                        ProfileActicity.navigate(this, view, profile);
                        break;
                    case 2:
                        checkForAllOpenFragments();
                        openArticleFragment();
                        break;
                    case 4:
                        checkForAllOpenFragments();
                        openBookMarkFragment();
                        break;
                    case 3:
                        openJobFragment();
                        break;
                    case 5:
                        checkForAllOpenFragments();
                        openSettingFragment();
                        break;
                    default:
                        LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + drawerItem);
                }
            } else if (baseResponse instanceof CommunitySuggestion) {

            } else if (baseResponse instanceof CommentReactionDoc) {
                clickMenuItem(view, baseResponse);
            }
        }

        @Override
        public void dataOperationOnClick(BaseResponse baseResponse) {
            if (baseResponse instanceof FeedDetail) {
                FeedDetail feedDetail = (FeedDetail) baseResponse;
                openImageFullViewFragment(feedDetail);
            }
        }


        private void feedCardsHandled(View view, BaseResponse baseResponse) {
            mFeedDetail = (FeedDetail) baseResponse;
            int id = view.getId();
            switch (id) {
                case R.id.tv_feed_community_user_menu:
                    clickMenuItem(view, baseResponse);
                    break;
                case R.id.tv_feed_community_post_user_menu:
                    clickMenuItem(view, baseResponse);
                    break;
                case R.id.tv_feed_article_user_bookmark:
                    bookmarkCall();
                    break;
                case R.id.tv_feed_article_user_menu:
                    clickMenuItem(view, baseResponse);
                    break;
                case R.id.tv_feed_community_post_user_comment_post_menu:
                    clickMenuItem(view, baseResponse);
                    break;
                case R.id.tv_feed_community_user_comment_post_menu:
                    clickMenuItem(view, baseResponse);
                    break;
                case R.id.tv_article_menu:
                    clickMenuItem(view, baseResponse);
                    break;
                case R.id.tv_feed_article_total_reactions:
                    mFragmentOpen.setCommentList(false);
                    mFragmentOpen.setReactionList(true);
                    openCommentReactionFragment(mFeedDetail);
                    break;
                case R.id.tv_feed_community_post_total_replies:
                    mFragmentOpen.setCommentList(true);
                    openCommentReactionFragment(mFeedDetail);
                    break;
                case R.id.tv_feed_community_total_replies:
                    mFragmentOpen.setCommentList(true);
                    openCommentReactionFragment(mFeedDetail);
                    break;
                case R.id.tv_feed_article_total_replies:
                    mFragmentOpen.setCommentList(true);
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
                case R.id.tv_feed_article_user_comment:
                    mFragmentOpen.setCommentList(true);
                    openCommentReactionFragment(mFeedDetail);
                    break;
                case R.id.tv_feed_community_user_comment:
                    mFragmentOpen.setCommentList(true);
                    openCommentReactionFragment(mFeedDetail);
                    break;
                case R.id.tv_feed_community_post_user_comment:
                    mFragmentOpen.setCommentList(true);
                    openCommentReactionFragment(mFeedDetail);
                    break;
                case R.id.li_feed_article_images:
                    ArticleDetailActivity.navigateFromArticle(this, view, mFeedDetail);
                    break;
                case R.id.li_feed_community_images:
                    CommunitiesDetailActivity.navigate(this, view, mFeedDetail);
                    break;
                case R.id.li_feed_community_user_post_images:
                    CommunitiesDetailActivity.navigate(this, view, mFeedDetail);
                    break;
                case R.id.tv_article_detail_user_reaction:

                    break;
                case R.id.tv_article_detail_user_comment:
                    break;
                case R.id.tv_article_detail_total_replies:
                    break;
                case R.id.tv_article_detail_view_more:
                    break;
                case R.id.tv_article_detail_user_comment_post_menu:
                    break;
                case R.id.tv_article_detail_user_comment_post_menu_second:
                    break;
                case R.id.tv_article_detail_user_comment_post_menu_third:
                    break;
                case R.id.li_feed_job_card:
                    JobDetailActivity.navigateFromJob(this, view, mFeedDetail);
                    break;
                case R.id.li_article_cover_image:
                    ArticleDetailActivity.navigateFromArticle(this, view, mFeedDetail);
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
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((HomeFragment) fragment).bookMarkForCard(mFeedDetail);
            }
        }

        private void clickMenuItem(View view, final BaseResponse baseResponse) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = layoutInflater.inflate(R.layout.menu_option_layout, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
            popupWindow.showAsDropDown(view);
            tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentReactionDoc commentReactionDoc = (CommentReactionDoc) baseResponse;
                    if (null != commentReactionDoc) {
                        if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                            commentReactionDoc.setActive(true);
                            commentReactionDoc.setEdit(true);
                            ((CommentReactionFragment) fragmentCommentReaction).editCommentInList(commentReactionDoc);
                        }
                    }
                    popupWindow.dismiss();
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommentReactionDoc commentReactionDoc = (CommentReactionDoc) baseResponse;
                    if (AppUtils.isFragmentUIActive(fragmentCommentReaction)) {
                        commentReactionDoc.setActive(false);
                        commentReactionDoc.setEdit(false);
                        ((CommentReactionFragment) fragmentCommentReaction).editCommentInList(commentReactionDoc);
                    }
                    popupWindow.dismiss();
                }
            });
            tvShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "You Clicked : " + tvShare.getText(), Toast.LENGTH_SHORT).show();
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
                case R.id.tv_feed_article_user_menu:
                    tvShare.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_user_comment_list_menu:
                    tvEdit.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_feed_community_post_user_comment_post_menu:
                    //if owner
                    tvDelete.setVisibility(View.VISIBLE);
                    //if commenter
                    tvEdit.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.VISIBLE);
                    break;
                case R.id.tv_feed_community_user_comment_post_menu:
                    //if owner
                    tvDelete.setVisibility(View.VISIBLE);
                    //if commenter
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

        private void openCommentReactionFragment(FeedDetail feedDetail) {
            CommentReactionFragment commentReactionFragmentForArticle = new CommentReactionFragment();
            Bundle bundleArticle = new Bundle();
            bundleArticle.putParcelable(AppConstants.FRAGMENT_FLAG_CHECK, mFragmentOpen);
            bundleArticle.putParcelable(AppConstants.COMMENTS, feedDetail);
            commentReactionFragmentForArticle.setArguments(bundleArticle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.bottom_to_top_slide_anim, 0, 0, R.anim.bottom_to_top_slide_reverse_anim)
                    .replace(R.id.fl_feed_comments, commentReactionFragmentForArticle, CommentReactionFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

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
        public void setListData(BaseResponse data, boolean isCheked) {
            List<HomeSpinnerItem> localList = new ArrayList<>();
            if (StringUtil.isNotEmptyCollection(mHomeSpinnerItemList)) {
                HomeSpinnerItem passedHomeItem = (HomeSpinnerItem) data;
                for (HomeSpinnerItem homeSpinnerItem : mHomeSpinnerItemList) {
                    if (homeSpinnerItem.getId().equalsIgnoreCase(passedHomeItem.getId())) {
                        homeSpinnerItem.setChecked(passedHomeItem.isChecked());
                        localList.add(homeSpinnerItem);
                    } else {
                        localList.add(homeSpinnerItem);
                    }
                }
            }
            mHomeSpinnerItemList.clear();
            mHomeSpinnerItemList.addAll(localList);
        }

        @Override
        public void userCommentLikeRequest(BaseResponse baseResponse, int reactionValue, int position) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
            if (AppUtils.isFragmentUIActive(fragment)) {
                ((HomeFragment) fragment).likeAndUnlikeRequest(baseResponse, reactionValue, position);
            }
        }


        @Override
        public List getListData() {
            return mHomeSpinnerItemList;
        }


        @Override
        public void onDrawerOpened() {
       /* if (!mFragmentOpen.isImageBlur()) {
            BlurrImage.with(HomeActivity.this)
                    .radius(25)
                    .sampling(2)
                    .async()
                    .capture(mIvSideDrawerProfileBlurBackground)
                    .into(mIvSideDrawerProfileBlurBackground);
            mFragmentOpen.setImageBlur(true);
        }*/
        }

        @Override
        public void onDrawerClosed() {
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return false;
        }

        private void assignNavigationRecyclerListView() {

            mAdapter = new GenericRecyclerViewAdapter(this, this);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setSheroesGenericListData(CustomeDataList.makeDrawerItemList());
        }

        private void initHomeViewPagerAndTabs() {
            mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
            mFragmentOpen.setFeedOpen(true);
            mTabLayout.setVisibility(View.GONE);
            mTvCommunities.setText(AppConstants.EMPTY_STRING);
            mTvHome.setText(getString(R.string.ID_FEED));
            HomeFragment homeFragment = new HomeFragment();
            Bundle bundle = new Bundle();
            homeFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_feed_full_view, homeFragment, HomeFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();

        }

        private void initCommunityViewPagerAndTabs() {
            mFragmentOpen.setCommunityOpen(true);
            mTabLayout.setVisibility(View.VISIBLE);
            mTvCommunities.setText(getString(R.string.ID_COMMUNITIES));
            mTvHome.setText(AppConstants.EMPTY_STRING);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(FeaturedFragment.createInstance(), getString(R.string.ID_FEATURED));
            viewPagerAdapter.addFragment(MyCommunitiesFragment.createInstance(), getString(R.string.ID_MY_COMMUNITIES));
            mViewPager.setAdapter(viewPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }

        @OnClick(R.id.rl_search_box)
        public void searchButtonClick() {
            Intent intent = new Intent(this, HomeSearchActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.ALL_SEARCH, mFragmentOpen);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_dialog, R.anim.fade_out_dialog);
            //Snackbar.make(mCLMainLayout, "Work in progress", Snackbar.LENGTH_SHORT).show();
        }

        @OnClick(R.id.tv_spinner_icon)
        public void openSpinnerOnClick() {
            if (!mFragmentOpen.isOpen()) {
                mHomeSpinnerFragment = new HomeSpinnerFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(AppConstants.HOME_SPINNER_FRAGMENT, (ArrayList<? extends Parcelable>) mHomeSpinnerItemList);
                mHomeSpinnerFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                        .replace(R.id.fragment_home_spinner, mHomeSpinnerFragment, HomeSpinnerFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
                mFragmentOpen.setOpen(true);
            } else {
                onBackPressed();
            }
        }

        private void checkForAllOpenFragments() {
            if (mFragmentOpen.isArticleFragment()) {
                onBackPressed();
                mFragmentOpen.setArticleFragment(false);
            }
            if (mFragmentOpen.isBookmarkFragment()) {
                onBackPressed();
                mFragmentOpen.setBookmarkFragment(false);
            }
            if (mFragmentOpen.isSettingFragment()) {
                onBackPressed();
                mFragmentOpen.setSettingFragment(false);
            }
        }

        @OnClick(R.id.tv_home)
        public void homeOnClick() {
            checkForAllOpenFragments();
            liHomeCommunityButtonLayout.setVisibility(View.GONE);
            mFragmentOpen.setFeedOpen(true);
            mFragmentOpen.setCommunityOpen(false);
            flFeedFullView.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
            mTabLayout.removeAllTabs();
            mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
            mTvSpinnerIcon.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            mTvCommunities.setText(AppConstants.EMPTY_STRING);
            mTvHome.setText(getString(R.string.ID_FEED));
            mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_FEED));
        }

        @OnClick(R.id.tv_communities)
        public void communityOnClick() {
            liHomeCommunityButtonLayout.setVisibility(View.VISIBLE);
            mTvSearchBox.setText(getString(R.string.ID_SEARCH_IN_COMMUNITIES));
            checkForAllOpenFragments();
            mFragmentOpen.setCommunityOpen(true);
            mFragmentOpen.setFeedOpen(false);
            flFeedFullView.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
            mTabLayout.removeAllTabs();
            mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
            mTvSpinnerIcon.setVisibility(View.GONE);
            initCommunityViewPagerAndTabs();
        }

        @OnClick(R.id.iv_footer_button_icon)
        public void commingOnClick() {
            // Snackbar.make(mCLMainLayout, "Comming soon", Snackbar.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), CreateCommunityPostActivity.class);
            startActivity(intent);
        }


        private void openArticleFragment() {
            mFlHomeFooterList.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.VISIBLE);
            mFragmentOpen.setArticleFragment(true);
            mViewPager.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            flFeedFullView.setVisibility(View.GONE);
            mTvSpinnerIcon.setVisibility(View.GONE);
            mTvHome.setText(AppConstants.EMPTY_STRING);
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
            mTvCommunities.setText(AppConstants.EMPTY_STRING);
            ArticlesFragment articlesFragment = new ArticlesFragment();
            Bundle bundleArticle = new Bundle();
            articlesFragment.setArguments(bundleArticle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fl_article_card_view, articlesFragment, ArticlesFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
            mTvSpinnerIcon.setVisibility(View.VISIBLE);
        }


        private void openSettingFragment() {

            mFlHomeFooterList.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.VISIBLE);
            mFragmentOpen.setSettingFragment(true);
            mViewPager.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            flFeedFullView.setVisibility(View.GONE);
            mTvSpinnerIcon.setVisibility(View.GONE);
            mTvHome.setText(AppConstants.EMPTY_STRING);
            mTvSearchBox.setVisibility(View.GONE);
            mTvSetting.setVisibility(View.VISIBLE);
            mTvSetting.setText(R.string.ID_SETTINGS);
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
            mTvCommunities.setText(AppConstants.EMPTY_STRING);
            SettingFragment articlesFragment = new SettingFragment();
            Bundle bundle = new Bundle();
            articlesFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fl_article_card_view, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
            mTvSpinnerIcon.setVisibility(View.GONE);
        }

        private void openBookMarkFragment() {

            mFragmentOpen.setBookmarkFragment(true);
            mViewPager.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            flFeedFullView.setVisibility(View.GONE);
            mTvSpinnerIcon.setVisibility(View.GONE);
            mTvHome.setText(AppConstants.EMPTY_STRING);
            mTvSearchBox.setVisibility(View.GONE);
            mTvSetting.setVisibility(View.VISIBLE);
            mTvSetting.setText(R.string.ID_BOOKMARKS);
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
            mTvCommunities.setText(AppConstants.EMPTY_STRING);
            BookmarksFragment bookmarksFragment = new BookmarksFragment();
            Bundle bundleBookMarks = new Bundle();
            bundleBookMarks.putParcelable(AppConstants.BOOKMARKS, mFeedDetail);
            bookmarksFragment.setArguments(bundleBookMarks);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fl_article_card_view, bookmarksFragment, BookmarksFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
            mTvSpinnerIcon.setVisibility(View.GONE);
        }

        public void openJobFragment() {


            mFlHomeFooterList.setVisibility(View.VISIBLE);
            mToolbar.setVisibility(View.VISIBLE);
            mFragmentOpen.setArticleFragment(true);
            mViewPager.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            flFeedFullView.setVisibility(View.GONE);
            mTvSpinnerIcon.setVisibility(View.GONE);
            mTvHome.setText(AppConstants.EMPTY_STRING);
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
            mTvCommunities.setText(AppConstants.EMPTY_STRING);
            JobFragment articlesFragment = new JobFragment();
            Bundle bundleArticle = new Bundle();
            articlesFragment.setArguments(bundleArticle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fl_article_card_view, articlesFragment, JobFragment.class.getName()).addToBackStack(null).commitAllowingStateLoss();
            mTvSpinnerIcon.setVisibility(View.GONE);




       /* Intent intent = new Intent(getApplicationContext(), JobFilterActivity.class);
        startActivity(intent);*/
        }

        public void openJobLocationFragment() {
            mFragmentOpen.setSettingFragment(true);
            mViewPager.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.GONE);
            flFeedFullView.setVisibility(View.GONE);
            mTvSpinnerIcon.setVisibility(View.GONE);
            mTvHome.setText(AppConstants.EMPTY_STRING);
            mTvSearchBox.setVisibility(View.GONE);
            mTvSetting.setVisibility(View.GONE);
            mTvSetting.setText(R.string.ID_JOBS);

            mFlHomeFooterList.setVisibility(View.GONE);
            mToolbar.setVisibility(View.GONE);
            mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_unselected_icon), null, null);
            mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_unselected_icon), null, null);
            mTvCommunities.setText(AppConstants.EMPTY_STRING);
            JobLocationFilter articlesFragment = new JobLocationFilter();
            Bundle bundle = new Bundle();
            articlesFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                    .replace(R.id.fl_Job_card_view, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
            mTvSpinnerIcon.setVisibility(View.GONE);
        }

        @Override
        public void onBackPressed() {
            if (mFragmentOpen.isOpen()) {
                getSupportFragmentManager().popBackStack();
                mFragmentOpen.setOpen(false);
            } else if (mFragmentOpen.isCommentList()) {
                getSupportFragmentManager().popBackStackImmediate();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getName());
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((HomeFragment) fragment).commentListRefresh(mFeedDetail);
                }
                mFragmentOpen.setCommentList(false);
            } else if (mFragmentOpen.isReactionList()) {
                getSupportFragmentManager().popBackStack();
                mFragmentOpen.setReactionList(false);
                mFragmentOpen.setCommentList(true);
            } else if (mFragmentOpen.isArticleFragment()) {
                getSupportFragmentManager().popBackStackImmediate();
                if (mFragmentOpen.isFeedOpen()) {
                    flFeedFullView.setVisibility(View.VISIBLE);
                    mTvHome.setText(getString(R.string.ID_FEED));
                    mTvSpinnerIcon.setVisibility(View.GONE);
                    mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
                    mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
                } else {
                    mViewPager.setVisibility(View.VISIBLE);
                    mTabLayout.setVisibility(View.VISIBLE);
                    mTvSpinnerIcon.setVisibility(View.GONE);
                    mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
                    mTvCommunities.setText(getString(R.string.ID_COMMUNITIY));
                    mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
                }
                mFragmentOpen.setArticleFragment(false);


            } else if (mFragmentOpen.isSettingFragment()) {
                getSupportFragmentManager().popBackStackImmediate();
                setHomeFeedCommunityData();
                mFragmentOpen.setSettingFragment(false);

            } else if (mFragmentOpen.isBookmarkFragment()) {
                getSupportFragmentManager().popBackStackImmediate();
                setHomeFeedCommunityData();
                mFragmentOpen.setBookmarkFragment(false);
            } else {
                finish();
            }
        }

        private void setHomeFeedCommunityData() {
            if (mFragmentOpen.isFeedOpen()) {
                flFeedFullView.setVisibility(View.VISIBLE);
                mTvHome.setText(getString(R.string.ID_FEED));
                mTvSpinnerIcon.setVisibility(View.GONE);
                mTvHome.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_home_selected_icon), null, null);
                mTvHome.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
            } else {
                mViewPager.setVisibility(View.VISIBLE);
                mTabLayout.setVisibility(View.VISIBLE);
                mTvSpinnerIcon.setVisibility(View.GONE);
                mTvCommunities.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(getApplication(), R.drawable.ic_community_selected_icon), null, null);
                mTvCommunities.setText(getString(R.string.ID_COMMUNITIY));
                mTvCommunities.setTextColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text));
            }
            mTvSearchBox.setVisibility(View.VISIBLE);
            mTvSetting.setVisibility(View.GONE);
        }

        @Override
        public void onDialogDissmiss(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
            mFragmentOpen = isFragmentOpen;
            mFeedDetail = feedDetail;
            onBackPressed();
        }


        @Override
        public void onClickReactionList(FragmentOpen isFragmentOpen, FeedDetail feedDetail) {
            mFragmentOpen = isFragmentOpen;
            mFeedDetail = feedDetail;
            if (mFragmentOpen.isReactionList()) {
                openCommentReactionFragment(mFeedDetail);
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
        public void showNwError() {

        }

        @Override
        public void backListener(int id) {

            switch (id) {
                case R.id.id_setting_feedback:
                    SettingFeedbackFragment articlesFragment = new SettingFeedbackFragment();
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.fl_feed_comments, articlesFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.id_setting_preferences:
                    Intent intent = new Intent(getApplicationContext(), SettingPreferencesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.id_setting_terms_and_condition:

                    SettingTermsAndConditionFragment settingTermsAndConditionFragment = new SettingTermsAndConditionFragment();
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.fl_feed_comments, settingTermsAndConditionFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;
                case R.id.id_setting_about:

                    SettingAboutFragment settingAboutFragmentFragment = new SettingAboutFragment();
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_to_bottom_enter, 0, 0, R.anim.top_to_bottom_exit)
                            .replace(R.id.fl_feed_comments, settingAboutFragmentFragment).addToBackStack(null).commitAllowingStateLoss();
                    break;

                default:
                    getSupportFragmentManager().popBackStack();

            }
        }

        @Override
        public void startProgressBar() {

        }

        @Override
        public void stopProgressBar() {

        }

        @Override
        public void startNextScreen() {

        }

        @Override
        public void showError(String s) {

        }

        @OnClick(R.id.fab_add_community)
        public void createCommunityButton() {
            Intent intent = new Intent(getApplicationContext(), CreateCommunityActivity.class);
            startActivity(intent);
        }

        @OnClick(R.id.iv_home_notification_icon)
        public void notificationClick() {
            mDrawer.openDrawer(Gravity.LEFT);
        }

        @Override
        public void onShowErrorDialog() {
            onBackPressed();
        }

        @Override
        public void onDialogDissmiss(FragmentOpen isFragmentOpen) {

        }
    }

