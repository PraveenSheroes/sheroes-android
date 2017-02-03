package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.communities.CommunitySuggestion;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class CommunitySggestedByHolder extends BaseViewHolder<CommunitySuggestion> {
    private final String TAG = LogUtils.makeLogTag(CommunitySggestedByHolder.class);
    BaseHolderInterface viewInterface;
    private CommunitySuggestion dataItem;
    @Bind(R.id.iv_suggested_community_image)
    ImageView ivSuggestedCommunityImage;
    @Bind(R.id.tv_community_suggested_name)
    TextView tvCommunitySuggestedName;
    @Bind(R.id.tv_suggested_community_member)
    TextView tvSuggestedCommunityMember;
    @Bind(R.id.tv_suggested_community_group)
    TextView tvSuggestedCommunityGroup;
    @Bind(R.id.tv_suggested_community_join)
    TextView tvSuggestedCommunityJoin;
    public CommunitySggestedByHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(CommunitySuggestion item, final Context context, int position) {
        tvSuggestedCommunityJoin.setOnClickListener(this);
        this.dataItem = item;
        String imageUrl = item.getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivSuggestedCommunityImage);
    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_suggested_community_join:
                tvSuggestedCommunityJoin.setBackgroundResource(R.drawable.rectangle_feed_community_joined_active);
                tvSuggestedCommunityJoin.setTextColor(Color.parseColor("#ffffff"));
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
