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

public class FeedArticleHolder extends BaseViewHolder<ListOfFeed> {
    private final String TAG = LogUtils.makeLogTag(FeedArticleHolder.class);
    @Bind(R.id.li_feed_article_images)
    LinearLayout liFeedArticleImages;
    @Bind(R.id.li_feed_article_join_conversation)
    LinearLayout liFeedArticleJoinConversation;
    @Bind(R.id.iv_article_icon)
    CircleImageView ivFeedCircleIcon;
    BaseHolderInterface viewInterface;
    private ListOfFeed dataItem;
    public FeedArticleHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ListOfFeed item, final Context context, int position) {
        this.dataItem = item;
        liFeedArticleJoinConversation.setOnClickListener(this);
        liFeedArticleJoinConversation.setTag(AppConstants.FEED_ARTICLE);
        String images = dataItem.getImageUrl();
        ivFeedCircleIcon.setCircularImage(true);
        ivFeedCircleIcon.bindImage(images);
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.feed_article_single_image, null);
        ImageView ivFirstLandscape=(ImageView)child.findViewById(R.id.iv_article_single_image);
        Glide.with(context)
                .load(images)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivFirstLandscape);
        liFeedArticleImages.addView(child);

    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_feed_article_join_conversation:
                viewInterface.handleOnClick(dataItem, liFeedArticleJoinConversation);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}