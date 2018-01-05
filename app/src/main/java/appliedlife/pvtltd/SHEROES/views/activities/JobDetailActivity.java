package appliedlife.pvtltd.SHEROES.views.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CustomeCollapsableToolBar.CustomCollapsingToolbarLayout;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.JobDetailFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 20-02-2017.
 */

public class JobDetailActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String SCREEN_LABEL = "Job Details Activity";
    private final String TAG = LogUtils.makeLogTag(JobDetailActivity.class);
    private static final String LEFT_NEW = "<font color='#50e3c2'>";
    private static final String RIGHT_NEW = "</font>";

    //region Butterknife bindings
    @Bind(R.id.app_bar_job_detail)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.iv_job_detail)
    ImageView ivJobDetail;
    @Bind(R.id.tv_job_detail_bookmark)
    public TextView mTvJobDetailBookmark;
    @Bind(R.id.view_pager_job_detail)
    ViewPager mViewPagerJobDetail;
    @Bind(R.id.toolbar_job_detail)
    Toolbar mToolbarJobDetail;
    @Bind(R.id.tv_job_title)
    TextView mTv_job_title;
    @Bind(R.id.tv_job_detail_title)
    TextView mTvJobDetailTitle;
    @Bind(R.id.tv_job_detail_subtitle)
    TextView mTvJobDetailSubTitle;
    @Bind(R.id.tv_job_detail_share)
    TextView mTv_job_detail_share;
    @Bind(R.id.collapsing_toolbar_job_detail)
    public CustomCollapsingToolbarLayout mCustomCollapsingToolbarLayout;
    @Bind(R.id.tv_job_comp_nm)
    TextView mTv_job_comp_nm;
    @Bind(R.id.iv_job_comp_logo)
    RoundedImageView mIv_job_comp_logo;
    @Bind(R.id.li_header)
    public LinearLayout mLiHeader;
    @Bind(R.id.tv_apply_job)
    public TextView mtv_apply_job;
    //endregion

    ViewPagerAdapter mViewPagerAdapter;
    private JobFeedSolrObj jobFeedObj;
    int mlogoflag = 0;
    long mJobId = 0;
    int feedDetailPosition;

    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    private long startedTime;
    private int mFromNotification;

    //region Activity Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        initActivityTransitions();
        setContentView(R.layout.activity_job_details);
        ButterKnife.bind(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        startedTime = System.currentTimeMillis();
        mAppBarLayout.addOnOffsetChangedListener(this);
        if (null != getIntent() && null != getIntent().getExtras()) {
            mFromNotification = getIntent().getExtras().getInt(AppConstants.BELL_NOTIFICATION);
            mJobId = getIntent().getExtras().getLong(AppConstants.JOB_ID);
            if (mJobId > 0) {
                jobFeedObj = new JobFeedSolrObj();
                jobFeedObj.setIdOfEntityOrParticipant(mJobId);
            } else {
                jobFeedObj = Parcels.unwrap(getIntent().getParcelableExtra(AppConstants.JOB_DETAIL));
            }
        }
        setPagerAndLayouts();
        if (null != jobFeedObj) {
            moEngageData(jobFeedObj);
        }
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_VIEW_JOBS_DETAIL));
    }

    @Override
    public void onBackPressed() {
        onBackClick();
    }
    //endregion

    //region OnClick Methods
    @OnClick(R.id.iv_job_detail_back)
    public void onBackClick() {
        if (mJobId > 0) {
            if (mFromNotification == AppConstants.NO_REACTION_CONSTANT) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                deepLinkBackPress();
            }
        } else {
            deepLinkBackPress();
        }
        finish();
    }
    @OnClick(R.id.tv_apply_job)
    public void onClickApplyButton() {
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPagerJobDetail, 0);
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((JobDetailFragment) fragment).clickApplyButton();
        }
           }
    @OnClick(R.id.tv_job_detail_share)
    public void onJobDetailShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, jobFeedObj.getDeepLinkUrl());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        moEngageUtills.entityMoEngageCardShareVia(getApplicationContext(), mMoEHelper, payloadBuilder, jobFeedObj, MoEngageConstants.SHARE_VIA_SOCIAL);
        ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_JOB, AppConstants.EMPTY_STRING);
        ((SheroesApplication) this.getApplication()).trackScreenView(getString(R.string.ID_REFER_SHARE_JOB));

        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(jobFeedObj.getIdOfEntityOrParticipant()))
                        .title(jobFeedObj.getNameOrTitle())
                        //.companyId(Long.toString(jobFeedObj.getCompanyMasterId()))
                        .location(jobFeedObj.getAuthorCityName())
                        .build();
        trackEvent(Event.JOBS_SHARED, properties);
    }

    @OnClick(R.id.tv_job_detail_bookmark)
    public void onBookMarkClick() {
        mTvJobDetailBookmark.setEnabled(false);
        bookmarkCall();
        if (!jobFeedObj.isBookmarked()) {
            jobFeedObj.setBookmarked(true);
            ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARK_ON_JOB, AppConstants.EMPTY_STRING);
        } else {
            jobFeedObj.setBookmarked(false);
            ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARK_ON_JOB, AppConstants.EMPTY_STRING);
        }
        setBookMarkImage();

    }
    //endregion

    //region Public Methods
    @TargetApi(AppConstants.ANDROID_SDK_24)
    public void setBackGroundImage(FeedDetail feedDetail) {
        jobFeedObj = (JobFeedSolrObj) feedDetail;
        mTv_job_comp_nm.setText(jobFeedObj.getAuthorName());
        mTvJobDetailSubTitle.setText(jobFeedObj.getAuthorName());
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getNameOrTitle())) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!jobFeedObj.isApplied() && !jobFeedObj.isViewed()) {
                stringBuilder.append(jobFeedObj.getNameOrTitle()).append(AppConstants.SPACE).append(LEFT_NEW).append(getString(R.string.ID_NEW)).append(RIGHT_NEW);
            } else {
                stringBuilder.append(jobFeedObj.getNameOrTitle());
            }
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                mTvJobDetailTitle.setText(Html.fromHtml(stringBuilder.toString(), 0)); // for 24 api and more
                mTv_job_title.setText(Html.fromHtml(stringBuilder.toString(), 0));
            } else {
                mTvJobDetailTitle.setText(Html.fromHtml(stringBuilder.toString()));// or for older api
                mTv_job_title.setText(Html.fromHtml(stringBuilder.toString()));
            }
        }

        setBookMarkImage();
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getAuthorImageUrl())) {
            mlogoflag = 1;
            Glide.with(this)
                    .load(jobFeedObj.getAuthorImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(mIv_job_comp_logo);
        }
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getImageUrl())) {
            Glide.with(this)
                    .load(jobFeedObj.getImageUrl()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                            ivJobDetail.setImageBitmap(resource);
                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    applyPalette(palette);
                                }
                            });
                        }
                    });
        } else {
            ivJobDetail.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.job_default_cover));
            mTv_job_title.setText(jobFeedObj.getNameOrTitle());
            if (mlogoflag == 0)
                mIv_job_comp_logo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_createc_ommunity_icon));
            supportStartPostponedEnterTransition();
        }
    }

    public void onJobBookmarkClick(FeedDetail feedDetail) {
        mTvJobDetailBookmark.setEnabled(true);
        if (!feedDetail.isBookmarked()) {
            feedDetail.setBookmarked(true);
            mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
        } else {
            feedDetail.setBookmarked(false);
            mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_detail_white, 0, 0, 0);
        }
        jobFeedObj = (JobFeedSolrObj)feedDetail;
    }
    //endregion

    //region BaseActivity Methods
    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof FeedDetail) {
            FeedDetail feedDetail = (FeedDetail) baseResponse;
            jobDetailHandled(view, feedDetail);
        }
    }

    @Override
    public void onShowErrorDialog(String errorReason, FeedParticipationEnum feedParticipationEnum) {
        if (StringUtil.isNotNullOrEmptyString(errorReason)) {
            switch (errorReason) {
                case AppConstants.CHECK_NETWORK_CONNECTION:
                    showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                    break;
                default:
                    showNetworkTimeoutDoalog(true, false, errorReason);
            }
        } else {
            showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset >= AppConstants.NO_REACTION_CONSTANT) {
            mCustomCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
            mCustomCollapsingToolbarLayout.setSubtitle(AppConstants.EMPTY_STRING);
            mLiHeader.setVisibility(View.INVISIBLE);
        } else {
            mLiHeader.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        HashMap<String, Object> properties = new
                EventProperty.Builder()
                .id(Long.toString(jobFeedObj.getIdOfEntityOrParticipant()))
                .build();
        return properties;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }
    //endregion

    //region Private Methods
    private void deepLinkBackPress() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Parcelable parcelable = Parcels.wrap(jobFeedObj);
        bundle.putParcelable(AppConstants.JOB_FRAGMENT, parcelable);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }

    private void moEngageData(FeedDetail feedDetail) {
        StringBuilder mergeJobTypes = new StringBuilder();
        if (StringUtil.isNotEmptyCollection(((JobFeedSolrObj)feedDetail).getSearchTextJobEmpTypes())) {
            List<String> jobTypes = ((JobFeedSolrObj)feedDetail).getSearchTextJobEmpTypes();
            for (String jobType : jobTypes) {
                mergeJobTypes.append(jobType).append(AppConstants.PIPE);
            }
        }
        StringBuilder mergeJobSkills = new StringBuilder();
        if (StringUtil.isNotEmptyCollection(((JobFeedSolrObj)feedDetail).getSearchTextJobSkills())) {
            List<String> jobSkills = ((JobFeedSolrObj)feedDetail).getSearchTextJobSkills();

            for (String skill : jobSkills) {
                mergeJobSkills.append(skill);
                mergeJobSkills.append(AppConstants.COMMA);
            }
        }
        long timeSpent = System.currentTimeMillis() - startedTime;
        moEngageUtills.entityMoEngageJobDetail(this, mMoEHelper, payloadBuilder, timeSpent, feedDetail.getNameOrTitle(), feedDetail.getIdOfEntityOrParticipant(), feedDetail.getAuthorName(), feedDetail.getAuthorCityName(), mergeJobTypes.toString(), mergeJobTypes.toString(), AppConstants.EMPTY_STRING, AppConstants.EMPTY_STRING, mergeJobSkills.toString());

    }

    private void setPagerAndLayouts() {
        setSupportActionBar(mToolbarJobDetail);
        mCustomCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getApplication(), android.R.color.transparent));
        if (null != jobFeedObj) {
            feedDetailPosition = jobFeedObj.getItemPosition();
            mCustomCollapsingToolbarLayout.setTitle(AppConstants.EMPTY_STRING);
            mCustomCollapsingToolbarLayout.setSubtitle(AppConstants.EMPTY_STRING);
            mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            mViewPagerAdapter.addFragment(JobDetailFragment.createInstance(jobFeedObj), getString(R.string.ID_JOB));
            mViewPagerJobDetail.setAdapter(mViewPagerAdapter);
            setBackGroundImage(jobFeedObj);
        }
    }

    private void jobDetailHandled(View view, FeedDetail feedDetail) {
      /*  int id = view.getId();
        switch (id) {
            case R.id.tv_article_detail_user_comment:
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }*/
    }

    private void bookmarkCall() {
        Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPagerJobDetail, 0);
        if (AppUtils.isFragmentUIActive(fragment)) {
            ((JobDetailFragment) fragment).bookMarkForDetailCard(jobFeedObj);
        }
    }

    private void setBookMarkImage() {
        if (jobFeedObj.isBookmarked()) {
            mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_active, 0, 0, 0);
        } else {
            mTvJobDetailBookmark.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bookmark_detail_white, 0, 0, 0);
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void applyPalette(Palette palette) {
        supportStartPostponedEnterTransition();
    }
    //endregion

}