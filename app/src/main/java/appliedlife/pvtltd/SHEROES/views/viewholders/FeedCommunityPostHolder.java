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
 * Created by Praveen_Singh on 22-01-2017.
 */

public class FeedCommunityPostHolder extends BaseViewHolder<ListOfFeed> {
    private final String TAG = LogUtils.makeLogTag(FeedCommunityPostHolder.class);
    @Bind(R.id.li_feed_community_user_post_images)
    LinearLayout liFeedCommunityUserPostImages;
    @Bind(R.id.li_feed_community_post_join_conversation)
    LinearLayout liFeedCommunityPostJoinConversation;
    @Bind(R.id.iv_community_post_icon)
    CircleImageView ivFeedCommunityPostCircleIcon;
    BaseHolderInterface viewInterface;
    private ListOfFeed dataItem;
    public FeedCommunityPostHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ListOfFeed item, final Context context, int position) {
        this.dataItem = item;
        liFeedCommunityPostJoinConversation.setOnClickListener(this);
        liFeedCommunityPostJoinConversation.setTag(AppConstants.FEED_COMMUNITY_POST);
        String images = dataItem.getImageUrl();
        ivFeedCommunityPostCircleIcon.setCircularImage(true);
        ivFeedCommunityPostCircleIcon.bindImage(images);
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_community_post_first_landscape_with_multiple, null);
            ImageView ivFirstLandscape=(ImageView)child.findViewById(R.id.iv_first_landscape);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            ImageView ivSecond=(ImageView)child.findViewById(R.id.iv_second_image_landscape);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivSecond);
            ImageView ivThird=(ImageView)child.findViewById(R.id.iv_third_image_landscape);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivThird);
            ImageView ivFourth=(ImageView)child.findViewById(R.id.iv_fourth_image_landscape);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFourth);
            liFeedCommunityUserPostImages.addView(child);
        /*else if(item.getId().equalsIgnoreCase("2"))
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_community_post_first_portrait_with_multiple, null);
            ImageView ivFirstLandscape=(ImageView)child.findViewById(R.id.iv_first_portrait);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            ImageView ivSecond=(ImageView)child.findViewById(R.id.iv_second_portrait);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivSecond);
            ImageView ivThird=(ImageView)child.findViewById(R.id.iv_third_portrait);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivThird);
            ImageView ivFourth=(ImageView)child.findViewById(R.id.iv_fourth_portrait);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFourth);
            liFeedCommunityUserPostImages.addView(child);
        }
        else if(item.getId().equalsIgnoreCase("3"))
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_community_post_first_landscape_with_multiple, null);
            ImageView ivFirstLandscape=(ImageView)child.findViewById(R.id.iv_first_landscape);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            ImageView ivSecond=(ImageView)child.findViewById(R.id.iv_second_image_landscape);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivSecond);
            ImageView ivThird=(ImageView)child.findViewById(R.id.iv_third_image_landscape);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivThird);
            ImageView ivFourth=(ImageView)child.findViewById(R.id.iv_fourth_image_landscape);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFourth);
            liFeedCommunityUserPostImages.addView(child);
        }else if(item.getId().equalsIgnoreCase("4"))
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_community_post_single_image, null);
            ImageView ivFirstLandscape=(ImageView)child.findViewById(R.id.iv_cominity_post_single);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirstLandscape);
            liFeedCommunityUserPostImages.addView(child);
        }
        else if(item.getId().equalsIgnoreCase("4"))
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.feed_community_post_two_images, null);
            ImageView ivFirst=(ImageView)child.findViewById(R.id.iv_comunity_post_first);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFirst);
            ImageView ivSecond=(ImageView)child.findViewById(R.id.iv_comunity_post_second);
            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivSecond);
            liFeedCommunityUserPostImages.addView(child);
        }*/
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        viewInterface.handleOnClick(dataItem, view);
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_community_post_join_conversation:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}