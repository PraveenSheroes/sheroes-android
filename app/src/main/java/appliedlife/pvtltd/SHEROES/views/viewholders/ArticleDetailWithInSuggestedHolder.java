package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleDetailSuggestion;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 09-02-2017.
 */

public class ArticleDetailWithInSuggestedHolder extends BaseViewHolder<ArticleDetailSuggestion> {
    private final String TAG = LogUtils.makeLogTag(ArticleDetailWithInSuggestedHolder.class);
    BaseHolderInterface viewInterface;
    private ArticleDetailSuggestion dataItem;
    @Bind(R.id.iv_article_detail_suggested_single_image)
    ImageView iv_article_suggested_single_image;
    @Bind(R.id.tv_article_detail_suggested_time_label)
    TextView tvArticleSuggestedTime;
    @Bind(R.id.tv_article_detail_suggested_total_views)
    TextView tvSuggestedArticleTotalViews;

    public ArticleDetailWithInSuggestedHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ArticleDetailSuggestion item, final Context context, int position) {
        this.dataItem = item;
        String imageUrl = item.getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(iv_article_suggested_single_image);
    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
