package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.JobFeedSolrObj;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 23-01-2017.
 */

public class FeedJobHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(FeedJobHolder.class);
    private static final String LEFT_NEW = "<font color='#50e3c2'>";
    private static final String RIGHT_NEW = "</font>";
    @Bind(R.id.iv_feed_job_icon)
    RoundedImageView ivFeedJobCircleIcon;
    @Bind(R.id.tv_feed_job_user_bookmark)
    TextView tvFeedJobUserBookmark;
    @Bind(R.id.tv_feed_job_user_menu)
    TextView tvFeedJobUserMenu;
    @Bind(R.id.tv_feed_job_applied)
    TextView tvFeedJobApplied;
    @Bind(R.id.tv_feed_job_card_title)
    TextView tvFeedJobCardTitle;
    @Bind(R.id.tv_feed_job_group_name)
    TextView tvFeedJobGroupName;
    @Bind(R.id.tv_feed_job_type)
    TextView tvFeedJobType;
    @Bind(R.id.tv_feed_job_name)
    TextView tvFeedJobName;
    @Bind(R.id.tv_feed_job_date_time)
    TextView tvFeedJobDateTime;
    @Bind(R.id.tv_feed_job_location)
    TextView tvFeedJobLocation;
    @Bind(R.id.iv_feed_job_menu)
    ImageView ivFeedJobMenu;
    @Bind(R.id.li_feed_job_card)
    LinearLayout liFeedJobCard;
    @Bind(R.id.tv_feed_job_user_share)
    TextView tvFeedJobUserShare;
    BaseHolderInterface viewInterface;
    private JobFeedSolrObj jobFeedObj;
    @Inject
    DateUtil mDateUtil;
    Context mContext;

    public FeedJobHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.jobFeedObj = (JobFeedSolrObj) item;
        this.mContext = context;
        jobFeedObj.setItemPosition(position);
        tvFeedJobUserBookmark.setEnabled(true);
        if (!jobFeedObj.isTrending()) {
            imageOperations(context);
        }
        allTextViewStringOperations(context);
        onBookMarkClick();
    }
    private void onBookMarkClick() {
        if (jobFeedObj.isBookmarked()) {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }

    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = jobFeedObj.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            Glide.with(context)
                    .load(feedCircleIconUrl)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true))
                    .into(ivFeedJobCircleIcon);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getNameOrTitle())) {
            StringBuilder stringBuilder=new StringBuilder();
             if (!jobFeedObj.isApplied() && !jobFeedObj.isViewed()) {
                stringBuilder.append(jobFeedObj.getNameOrTitle()).append(AppConstants.SPACE).append(LEFT_NEW).append(mContext.getString(R.string.ID_NEW)).append(RIGHT_NEW);
            } else {
                stringBuilder.append(jobFeedObj.getNameOrTitle());
            }
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedJobCardTitle.setText(Html.fromHtml(stringBuilder.toString(), 0)); // for 24 api and more
            } else {
                tvFeedJobCardTitle.setText(Html.fromHtml(stringBuilder.toString()));// or for older api
            }
        }
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getAuthorName())) {
            tvFeedJobGroupName.setText(jobFeedObj.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getFormattedSolrStartDate())) {
            tvFeedJobDateTime.setVisibility(View.VISIBLE);
            tvFeedJobDateTime.setText(jobFeedObj.getFormattedSolrStartDate());
        }
        else
        {
            tvFeedJobDateTime.setVisibility(View.GONE);
        }
        if (StringUtil.isNotEmptyCollection(jobFeedObj.getSearchTextJobEmpTypes())) {
            List<String> jobTypes = jobFeedObj.getSearchTextJobEmpTypes();
            String mergeJobTypes = AppConstants.EMPTY_STRING;
            for (String jobType : jobTypes) {
                mergeJobTypes += jobType + AppConstants.PIPE;
            }
            tvFeedJobType.setText(mergeJobTypes.substring(0, mergeJobTypes.length() - 1));
        }

        if (StringUtil.isNotEmptyCollection(jobFeedObj.getSearchTextJobSkills())) {
            List<String> jobSkills = jobFeedObj.getSearchTextJobSkills();
            StringBuilder mergeJobSkills = new StringBuilder();
            for (String skill : jobSkills) {
                mergeJobSkills.append(skill);
                mergeJobSkills.append(AppConstants.COMMA);
                mergeJobSkills.append(AppConstants.SPACE);
            }
            String skills = mergeJobSkills.toString();
            if(skills.length()>2) {
                tvFeedJobName.setText(skills.substring(0, skills.length() - 2));
            }
        }
        if (StringUtil.isNotNullOrEmptyString(jobFeedObj.getAuthorCityName())) {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(jobFeedObj.getAuthorCityName()).append(AppConstants.COMMA).append(AppConstants.COUNTRY_NAME);
            tvFeedJobLocation.setText(stringBuilder.toString());
        }else
        {
            tvFeedJobLocation.setText(mContext.getString(R.string.ID_REMOTE));
        }
        if (jobFeedObj.isApplied()) {
            tvFeedJobApplied.setVisibility(View.VISIBLE);
        } else {
            tvFeedJobApplied.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick(R.id.tv_feed_job_user_bookmark)
    public void isBookMarkClick() {
        jobFeedObj.setTrending(true);
        tvFeedJobUserBookmark.setEnabled(false);
        if (jobFeedObj.isBookmarked()) {
            viewInterface.handleOnClick(jobFeedObj, tvFeedJobUserBookmark);
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_UN_BOOKMARK, GoogleAnalyticsEventActions.UN_BOOKMARK_ON_JOB, AppConstants.EMPTY_STRING);
        } else {
            viewInterface.handleOnClick(jobFeedObj, tvFeedJobUserBookmark);
            ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_BOOKMARK, GoogleAnalyticsEventActions.BOOKMARK_ON_JOB, AppConstants.EMPTY_STRING);
        }
        if (!jobFeedObj.isBookmarked()) {
            jobFeedObj.setBookmarked(true);
        } else {
            jobFeedObj.setBookmarked(false);
        }
        onBookMarkClick();

    }

    @OnClick(R.id.tv_feed_job_user_menu)
    public void userMenuClick() {
        jobFeedObj.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(jobFeedObj, tvFeedJobUserMenu);
    }

    @OnClick(R.id.li_feed_job_card)
    public void feedJobClick() {
        jobFeedObj.setItemPosition(getAdapterPosition());
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback)viewInterface).onJobPostClicked(jobFeedObj);
        }else {
            viewInterface.handleOnClick(jobFeedObj, liFeedJobCard);
        }
    }
    @OnClick(R.id.tv_feed_job_user_share)
    public void tvFeedJobShare() {
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback)viewInterface).onPostShared(jobFeedObj);
        }else {
            viewInterface.handleOnClick(jobFeedObj, tvFeedJobUserShare);
        }
        ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_EXTERNAL_SHARE, GoogleAnalyticsEventActions.SHARED_JOB, AppConstants.EMPTY_STRING);
        ((SheroesApplication)((BaseActivity) mContext).getApplication()).trackScreenView(mContext.getString(R.string.ID_REFER_SHARE_JOB));
    }
    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }

}