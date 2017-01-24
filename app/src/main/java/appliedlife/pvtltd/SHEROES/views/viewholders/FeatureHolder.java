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
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.FeatResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 19-01-2017.
 */

public class FeatureHolder extends BaseViewHolder<FeatResponse> {
    private final String TAG = LogUtils.makeLogTag(FeatureHolder.class);
    @Bind(R.id.iv_dashboard)
    ImageView background;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.tv_dashboard_title)
    TextView tvDashboardTitle;
    @Bind(R.id.tv_header)
    TextView tvHeader;
    @Bind(R.id.tv_dashboard_time)
    TextView tvTime;
    @Bind(R.id.iv_dashboard_icon)
    ImageView ivIcon;
    @Bind(R.id.tv_views)
    TextView tvViews;
    BaseHolderInterface viewInterface;
    private FeatResponse dataItem;
    private int position;
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#3b99fc'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";

    public FeatureHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeatResponse item, Context context, int position) {
        this.dataItem = item;
        // itemView.setOnClickListener(this);
        String str = item.getDescription();
            tvDescription.setText(str); // for 24 api and more
        background.setOnClickListener(this);

        tvHeader.setText(item.getName());

        String images = dataItem.getBackground();

        Glide.with(context)
                .load(images)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(background);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
        //    case R.id.iv_dashboard:
        //        break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
