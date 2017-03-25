package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 07-03-2017.
 */

public class UserHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(UserHolder.class);
    @Bind(R.id.iv_user)
    CircleImageView ivFeedUserCircleIcon;
    @Bind(R.id.tv_owner_title)
    TextView tvowOer_title;
    @Bind(R.id.tv_owner_city)
    TextView tv_owner_city;
    @Bind(R.id.tv_owner_cross)
    TextView mTvownerclose;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    Context mContext;

    public UserHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;
       // imageOperations(context);
        mTvownerclose.setOnClickListener(this);
        allTextViewStringOperations(context);
    }

  /*  private void imageOperations(Context context) {
        String feedCircleIconUrl = dataItem.getAuthorImageUrl();
        if (StringUtil.isNotNullOrEmptyString(feedCircleIconUrl)) {
            Glide.with(context)
                    .load(feedCircleIconUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivFeedUserCircleIcon);
        }

    }*/

    private void allTextViewStringOperations(Context context) {
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvowOer_title.setText(dataItem.getNameOrTitle());

        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCityName())) {
            tv_owner_city.setText(dataItem.getCityName());
        }
        String images = dataItem.getImageUrl();

        ivFeedUserCircleIcon.setCircularImage(true);
        ivFeedUserCircleIcon.bindImage(images);
    }



    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.tv_owner_cross:
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("collection id",dataItem.getId());
                mTvownerclose.setBackgroundResource(R.drawable.unselected_add_btn_shap);
                mTvownerclose.setTextColor(Color.WHITE);
                mTvownerclose.setText(R.string.ID_ADDED);
                viewInterface.handleOnClick(this.dataItem,view);
                break;
            default:
                LogUtils.error("", AppConstants.CASE_NOT_HANDLED + " " + "" + " " + id);
        }



    }

}