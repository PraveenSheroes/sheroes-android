package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 27-04-2017.
 */

public class BellNotificationHolder extends BaseViewHolder<BellNotificationResponse> {

    @Bind(R.id.tv_notification_tittle)
    TextView mTvNotificationTitle;
    @Bind(R.id.tv_notification_date)
    TextView mTvNotificationDate;
    @Bind(R.id.iv_notification_image)
    ImageView mIvNotificationImage;
    @Bind(R.id.lnr_notification)
    FrameLayout mLnrNotification;
    @Bind(R.id.iv_notification_type)
    ImageView mIvNotificationType;
    BaseHolderInterface mViewInterface;
    private BellNotificationResponse mDataItem;
    private Context mContext;
    public BellNotificationHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        this.mViewInterface = baseHolderInterface;
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(BellNotificationResponse belNotificationListResponse, Context context, int position) {
        this.mDataItem = belNotificationListResponse;
        mContext = context;
            if(null != mDataItem) {
                if(StringUtil.isNotNullOrEmptyString(mDataItem.getTitle())) {
                    if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                        mTvNotificationTitle.setText(Html.fromHtml(mDataItem.getTitle(), 0)); // for 24 api and more
                    } else {
                        mTvNotificationTitle.setText(Html.fromHtml(mDataItem.getTitle()));// or for older api
                    }
                }
                if(StringUtil.isNotNullOrEmptyString(mDataItem.getLastActivityDate())) {
                    mTvNotificationDate.setText(mDataItem.getLastActivityDate());
                }
                if(StringUtil.isNotNullOrEmptyString(mDataItem.getSolrIgnoreAuthorOrEntityImageUrl())) {
                    Glide.with(mContext)
                            .load(mDataItem.getSolrIgnoreAuthorOrEntityImageUrl())
                            .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mContext)).diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true))
                            .into(mIvNotificationImage);
                }
                if(StringUtil.isNotNullOrEmptyString(mDataItem.getSolrIgnoreIconImageUrl())) {
                    Glide.with(mContext)
                            .load(mDataItem.getSolrIgnoreIconImageUrl())
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true))
                            .into(mIvNotificationType);
                }

            }
    }

    @Override
    public void viewRecycled() {

    }
    @OnClick(R.id.tv_notification_tittle)
    public void onNotificationTitleClick()
    {
        mViewInterface.handleOnClick(mDataItem, mLnrNotification);
    }
    @OnClick(R.id.lnr_notification)
    public void onViewClick()
    {
        mViewInterface.handleOnClick(mDataItem, mLnrNotification);
    }

    @Override
    public void onClick(View v) {

    }
}
