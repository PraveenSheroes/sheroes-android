package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.communities.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 03-02-2017.
 */

public class ChallengeItemCardHolder extends BaseViewHolder<ChallengeDataItem> {
    private final String TAG = LogUtils.makeLogTag(ChallengeItemCardHolder.class);
    BaseHolderInterface viewInterface;
    private ChallengeDataItem dataItem;
    @Bind(R.id.iv_challenge_icon)
    RoundedImageView ivChallengeIcon;
    public ChallengeItemCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ChallengeDataItem item, final Context context, int position) {
        this.dataItem = item;
        String imageUrl = item.getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(ivChallengeIcon);
    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }

}
