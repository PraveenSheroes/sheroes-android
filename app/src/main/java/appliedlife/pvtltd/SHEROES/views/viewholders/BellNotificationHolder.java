package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotification;
import appliedlife.pvtltd.SHEROES.models.entities.home.BellNotificationResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by SHEROES-TECH on 27-04-2017.
 */

public class BellNotificationHolder extends BaseViewHolder<BellNotificationResponse> {

    @Bind(R.id.tv_bell_noti_title)
    TextView mTvNotificationTitle;
    @Bind(R.id.tv_bell_noti_time)
    TextView mTvNotificationDate;
    @Bind(R.id.iv_bell_noti_circle_icon)
    CircleImageView mIvNotificationImage;
    @Bind(R.id.cl_notification)
    ConstraintLayout mClNotification;
    @Bind(R.id.iv_bell_reaction)
    ImageView mIvNotificationType;
    @Bind(R.id.iv_bell_noti_image)
    ImageView mIvBellNotiImage;
    BaseHolderInterface mViewInterface;
    private Context mContext;
    @BindDimen(R.dimen.dp_size_40)
    int authorPicSizeFourty;
    private BellNotification mBellNotification;
    private BellNotificationResponse mBelNotificationListResponse;

    public BellNotificationHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        this.mViewInterface = baseHolderInterface;
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(BellNotificationResponse belNotificationListResponse, Context context, int position) {
        mBelNotificationListResponse=belNotificationListResponse;
        mBellNotification = belNotificationListResponse.getNotification();

        mContext = context;
        if (null != mBellNotification) {
            if (StringUtil.isNotNullOrEmptyString(mBellNotification.getTitle())) {
                String title=mBellNotification.getTitle();
                if(StringUtil.isNotNullOrEmptyString(mBellNotification.getMessage()))
                {
                    title=title+" "+mBellNotification.getMessage();
                }else {
                    title = title;
                }
                if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                    mTvNotificationTitle.setText(Html.fromHtml(title, 0)); // for 24 api and more
                } else {
                    mTvNotificationTitle.setText(Html.fromHtml(title));// or for older api
                }
            }
            if (StringUtil.isNotNullOrEmptyString(mBellNotification.getSolrIgnorePostingDateDt())) {
                mTvNotificationDate.setVisibility(View.VISIBLE);
                mTvNotificationDate.setText(mBellNotification.getSolrIgnorePostingDateDt());
            } else {
                mTvNotificationDate.setVisibility(View.INVISIBLE);
            }
            if (StringUtil.isNotNullOrEmptyString(mBellNotification.getLeftImageIcon())) {
                mIvNotificationImage.setCircularImage(true);
                String authorThumborUrl = CommonUtil.getThumborUri(mBellNotification.getLeftImageIcon(), authorPicSizeFourty, authorPicSizeFourty);
                mIvNotificationImage.bindImage(authorThumborUrl);
            }

            if (StringUtil.isNotNullOrEmptyString(mBellNotification.getIcon())) {
                Glide.with(mContext)
                        .load(mBellNotification.getIcon())
                        .into(mIvNotificationType);
            }
            if (StringUtil.isNotNullOrEmptyString(mBellNotification.getRightImageIcon())) {
                Glide.with(context)
                        .load(mBellNotification.getRightImageIcon())
                        .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                        .into(mIvBellNotiImage);
            }

        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_bell_noti_title)
    public void onNotificationTitleClick() {
        mViewInterface.handleOnClick(mBelNotificationListResponse, mClNotification);
    }

    @OnClick(R.id.cl_notification)
    public void onViewClick() {
        mViewInterface.handleOnClick(mBelNotificationListResponse, mClNotification);
    }

    @Override
    public void onClick(View v) {

    }
}
