package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;

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

public class CollectionHolder extends BaseViewHolder<ListOfFeed> {
    private final String TAG = LogUtils.makeLogTag(CollectionHolder.class);
    private static final String LEFT_HTML_TAG_FOR_COLOR = "<b><font color='#3b99fc'>";
    private static final String RIGHT_HTML_TAG_FOR_COLOR = "</font></b>";
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
    CircleImageView ivIcon;
    @Bind(R.id.tv_views)
    TextView tvViews;
    BaseHolderInterface viewInterface;
    private ListOfFeed dataItem;
    private int position;


    public CollectionHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ListOfFeed item, final Context context, int position) {
        this.dataItem = item;
        String str = item.getDescription();
        if (str.length() > 80) {
            str = str.substring(0, 88);
        }
        String changeDate = LEFT_HTML_TAG_FOR_COLOR + context.getString(R.string.ID_VIEW_MORE) + RIGHT_HTML_TAG_FOR_COLOR;
        if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
            tvDescription.setText(Html.fromHtml(str + AppConstants.SPACE + changeDate, 0)); // for 24 api and more
        } else {
            tvDescription.setText(Html.fromHtml(str + AppConstants.SPACE + changeDate));// or for older api
        }
        background.setOnClickListener(this);

      //  tvDashboardTitle.setText(item.getName());
       // tvTime.setText(item.getCreatedDate());
      //  tvHeader.setText(item.getTitle());

        String images = dataItem.getFeedCircleIconUrl();
        ivIcon.setCircularImage(true);
        ivIcon.bindImage(images);
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
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("collection id", dataItem.getId());
        viewInterface.handleOnClick(this.dataItem, view);
        int id = view.getId();
        switch (id) {
            case R.id.iv_dashboard:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
