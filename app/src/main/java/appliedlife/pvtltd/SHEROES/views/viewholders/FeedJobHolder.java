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

import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
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
    private FeedDetail dataItem;
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
        this.dataItem = item;
        this.mContext = context;
        dataItem.setItemPosition(position);
        tvFeedJobUserBookmark.setEnabled(true);
        if (!dataItem.isTrending()) {
            imageOperations(context);
        }
        allTextViewStringOperations(context);
        onBookMarkClick();
    }
    private void onBookMarkClick() {
        if (dataItem.isBookmarked()) {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }

    }

    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            Glide.with(context)
                    .load(feedCircleIconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFeedJobCircleIcon);
        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            StringBuilder stringBuilder=new StringBuilder();
            if (!dataItem.isApplied() && !dataItem.isViewed()) {
                stringBuilder.append(dataItem.getNameOrTitle()).append(AppConstants.SPACE).append(LEFT_NEW).append(mContext.getString(R.string.ID_NEW)).append(RIGHT_NEW);
            } else {
                stringBuilder.append(dataItem.getNameOrTitle());
            }
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvFeedJobCardTitle.setText(Html.fromHtml(stringBuilder.toString(), 0)); // for 24 api and more
            } else {
                tvFeedJobCardTitle.setText(Html.fromHtml(stringBuilder.toString()));// or for older api
            }
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            tvFeedJobGroupName.setText(dataItem.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getStartDate())) {
            tvFeedJobDateTime.setVisibility(View.VISIBLE);
            tvFeedJobDateTime.setText(dataItem.getStartDate());
        }
        else
        {
            tvFeedJobDateTime.setVisibility(View.GONE);
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getSearchTextJobEmpTypes())) {
            List<String> jobTypes = dataItem.getSearchTextJobEmpTypes();
            String mergeJobTypes = AppConstants.EMPTY_STRING;
            for (String jobType : jobTypes) {
                mergeJobTypes += jobType + AppConstants.PIPE;
            }
            tvFeedJobType.setText(mergeJobTypes.substring(0, mergeJobTypes.length() - 1));
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getSearchTextJobSkills())) {
            List<String> jobSkills = dataItem.getSearchTextJobSkills();
            String mergeJobSkills = AppConstants.EMPTY_STRING;
            for (String skill : jobSkills) {
                mergeJobSkills += skill + AppConstants.COMMA;
            }
            tvFeedJobName.setText(mergeJobSkills);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorCityName())) {
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(dataItem.getAuthorCityName()).append(AppConstants.COMMA).append(AppConstants.COUNTRY_NAME);
            tvFeedJobLocation.setText(stringBuilder.toString());
        }else
        {
            tvFeedJobLocation.setText(mContext.getString(R.string.ID_REMOTE));
        }
        if (dataItem.isApplied()) {
            tvFeedJobApplied.setVisibility(View.VISIBLE);
        } else {
            tvFeedJobApplied.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick(R.id.tv_feed_job_user_bookmark)
    public void isBookMarkClick() {
        dataItem.setTrending(true);
        tvFeedJobUserBookmark.setEnabled(false);
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvFeedJobUserBookmark);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedJobUserBookmark);
        }
        if (!dataItem.isBookmarked()) {
            dataItem.setBookmarked(true);
        } else {
            dataItem.setBookmarked(false);
        }
        onBookMarkClick();
    }

    @OnClick(R.id.tv_feed_job_user_menu)
    public void userMenuClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, tvFeedJobUserMenu);
    }

    @OnClick(R.id.li_feed_job_card)
    public void feedJobClick() {
        dataItem.setItemPosition(getAdapterPosition());
        viewInterface.handleOnClick(dataItem, liFeedJobCard);
    }
    @OnClick(R.id.tv_feed_job_user_share)
    public void tvFeedJobShare() {
        viewInterface.handleOnClick(dataItem, tvFeedJobUserShare);
    }
    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }

}