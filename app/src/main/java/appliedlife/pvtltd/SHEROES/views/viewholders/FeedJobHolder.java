package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
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
    ImageView tvFeedJobMenu;
    @Bind(R.id.li_feed_job_card)
    ImageView liFeedJobCard;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;

    public FeedJobHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        tvFeedJobUserBookmark.setOnClickListener(this);
        tvFeedJobUserMenu.setOnClickListener(this);
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
            //  tvFeedJobDateTime.setText(dataItem.getCreatedDate());
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getOpportunityTypes())) {
            List<String> jobTypes = dataItem.getOpportunityTypes();
            String mergeJobTypes = AppConstants.EMPTY_STRING;
            for (String jobType : jobTypes) {
                mergeJobTypes += jobType + AppConstants.PIPE;
            }
            tvFeedJobType.setText(mergeJobTypes);
        }
        if (StringUtil.isNotEmptyCollection(dataItem.getSkills())) {
            List<String> jobSkills = dataItem.getSkills();
            String mergeJobSkills = AppConstants.EMPTY_STRING;
            for (String skill : jobSkills) {
                mergeJobSkills += skill + AppConstants.COMMA;
            }
            tvFeedJobName.setText(mergeJobSkills);
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCityName())) {
            tvFeedJobLocation.setText(dataItem.getCityName());
        }
        if (dataItem.isActive()) {
            tvFeedJobApplied.setText(context.getString(R.string.ID_APPLIED));
        } else {
            tvFeedJobApplied.setText(AppConstants.SPACE);
        }

        if (dataItem.isBookmarked()) {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        } else {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_in_active, 0);
        }


    }

    @OnClick(R.id.tv_feed_job_user_bookmark)
    public void isBookMarkClick() {
        if (dataItem.isBookmarked()) {
            viewInterface.handleOnClick(dataItem, tvFeedJobUserBookmark);
        } else {
            viewInterface.handleOnClick(dataItem, tvFeedJobUserBookmark);
        }
    }

    @OnClick(R.id.tv_feed_job_user_menu)
    public void userMenuClick() {
        viewInterface.handleOnClick(dataItem, tvFeedJobUserMenu);
    }

    @OnClick(R.id.li_feed_job_card)
    public void feedJobClick() {
        viewInterface.handleOnClick(dataItem, liFeedJobCard);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }

}