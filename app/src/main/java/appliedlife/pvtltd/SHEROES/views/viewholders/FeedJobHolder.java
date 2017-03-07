package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
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
        imageOperations(context);
        allTextViewStringOperations(context);
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

    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvFeedJobCardTitle.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getAuthorName())) {
            tvFeedJobGroupName.setText(dataItem.getAuthorName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDate())) {
            long createdDate = mDateUtil.getTimeInMillis(dataItem.getCreatedDate(), AppConstants.DATE_FORMAT);
            long minuts = mDateUtil.getRoundedDifferenceInHours(System.currentTimeMillis(), createdDate);
            if (minuts < 60) {
                tvFeedJobDateTime.setText(String.valueOf((int) minuts) + AppConstants.SPACE + mContext.getString(R.string.ID_MINUTS));
            } else {
                int hour = (int) minuts / 60;
                tvFeedJobDateTime.setText(String.valueOf(hour) + AppConstants.SPACE + mContext.getString(R.string.ID_HOURS));
            }
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getSearchTextJobEmpTypes())) {
            List<String> jobTypes = dataItem.getSearchTextJobEmpTypes();
            String mergeJobTypes = AppConstants.EMPTY_STRING;
            for (String jobType : jobTypes) {
                mergeJobTypes += jobType + AppConstants.PIPE;
            }
            tvFeedJobType.setText(mergeJobTypes.substring(0,mergeJobTypes.length()-1));
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getSearchTextSkills())) {
            List<String> jobSkills = dataItem.getSearchTextSkills();
            String mergeJobSkills = AppConstants.EMPTY_STRING;
            for (String skill : jobSkills) {
                mergeJobSkills += skill + AppConstants.COMMA;
            }
            tvFeedJobName.setText(mergeJobSkills);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCityName())) {
            tvFeedJobLocation.setText(dataItem.getCityName());
        }
        if (dataItem.isApplied()) {
            tvFeedJobApplied.setVisibility(View.VISIBLE);
        }
        if (dataItem.isBookmarked()) {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }


    }

    @OnClick(R.id.tv_feed_job_user_bookmark)
    public void isBookMarkClick() {
        dataItem.setItemPosition(getAdapterPosition());
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvFeedJobUserBookmark);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedJobUserBookmark);
        }
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

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }

}