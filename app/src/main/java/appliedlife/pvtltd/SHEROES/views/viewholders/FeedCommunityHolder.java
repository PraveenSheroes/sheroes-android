package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

public class FeedCommunityHolder extends BaseViewHolder<ListOfFeed> {
    private final String TAG = LogUtils.makeLogTag(FeedCommunityHolder.class);
    @Bind(R.id.li_feed_community_images)
    LinearLayout liFeedCommunityImages;
    @Bind(R.id.li_feed_community_join_conversation)
    LinearLayout liFeedCommunityJoinConversation;
    @Bind(R.id.iv_community_icon)
    CircleImageView ivFeedCommunityCircleIcon;
    BaseHolderInterface viewInterface;
    private ListOfFeed dataItem;
    public FeedCommunityHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ListOfFeed item, final Context context, int position) {
        this.dataItem = item;
        liFeedCommunityJoinConversation.setOnClickListener(this);
        liFeedCommunityJoinConversation.setTag(AppConstants.FEED_COMMUNITY);
        String images = dataItem.getImageUrl();
        ivFeedCommunityCircleIcon.setCircularImage(true);
        ivFeedCommunityCircleIcon.bindImage(images);
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_article_single_image, null);
            ImageView ivFirstLandscape=(ImageView)child.findViewById(R.id.iv_article_single_image);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            liFeedCommunityImages.addView(child);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        viewInterface.handleOnClick(dataItem, view);
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_community_join_conversation:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}