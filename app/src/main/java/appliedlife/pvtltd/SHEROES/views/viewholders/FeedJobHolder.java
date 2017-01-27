package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 23-01-2017.
 */

public class FeedJobHolder extends BaseViewHolder<ListOfFeed> {
    private final String TAG = LogUtils.makeLogTag(FeedJobHolder.class);
    @Bind(R.id.iv_feed_job_icon)
    CircleImageView ivFeedJobCircleIcon;
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
    BaseHolderInterface viewInterface;
    private ListOfFeed dataItem;
    public FeedJobHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ListOfFeed item, final Context context, int position) {
        this.dataItem = item;
        tvFeedJobUserBookmark.setOnClickListener(this);
        tvFeedJobUserMenu.setOnClickListener(this);
        imageOperations(context);
        allTextViewStringOperations(context);
    }
    private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getFeedCircleIconUrl();
        if(StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            ivFeedJobCircleIcon.setCircularImage(true);
            ivFeedJobCircleIcon.bindImage(feedCircleIconUrl);
        }

    }
    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getFeedTitle())) {
            tvFeedJobCardTitle.setText(dataItem.getFeedTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getGroupName())) {
            tvFeedJobGroupName.setText(dataItem.getGroupName());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCreatedDateTime())) {
            tvFeedJobDateTime.setText(dataItem.getCreatedDateTime());
        }
        if(null!=dataItem.getJobDetail()) {
            if (StringUtil.isNotNullOrEmptyString(dataItem.getJobDetail().getTypeOfJob())) {
                tvFeedJobType.setText(dataItem.getJobDetail().getTypeOfJob());
            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getJobDetail().getJobDescription())) {
                tvFeedJobName.setText(dataItem.getJobDetail().getJobDescription());
            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getJobDetail().getLocation())) {
                tvFeedJobLocation.setText(dataItem.getJobDetail().getLocation());
            }
            if(dataItem.getJobDetail().getIsApplied()) {
                tvFeedJobApplied.setText(context.getString(R.string.ID_APPLIED));
            }
            else
            {
                tvFeedJobApplied.setText(AppConstants.SPACE);
            }
        }
        if(dataItem.getBookmarked()) {
            tvFeedJobUserBookmark.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_bookmark_active, 0);
        }



    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        viewInterface.handleOnClick(this.dataItem, view);
        int id = view.getId();
        switch (id) {
            case R.id.tv_feed_job_user_bookmark:
                viewInterface.handleOnClick(dataItem, tvFeedJobUserBookmark);
                break;
            case R.id.tv_feed_job_user_menu:
                viewInterface.handleOnClick(dataItem, tvFeedJobUserMenu);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}