package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ListOfFeed;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
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
        String images = dataItem.getImageUrl();
        ivFeedJobCircleIcon.setCircularImage(true);
        ivFeedJobCircleIcon.bindImage(images);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        viewInterface.handleOnClick(this.dataItem, view);
        int id = view.getId();
        switch (id) {
         //   case R.id.iv_dashboard:
         //       break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}