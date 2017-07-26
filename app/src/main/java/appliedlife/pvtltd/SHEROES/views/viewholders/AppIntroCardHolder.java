package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroData;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 26-07-2017.
 */

public class AppIntroCardHolder extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(AppIntroCardHolder.class);
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    Context mContext;
    @Bind(R.id.tv_app_intro_title)
    TextView tvAppIntroTitle;
    @Bind(R.id.tv_app_intro_description)
    TextView tvAppIntroDescription;
    @Bind(R.id.iv_app_intro_image)
    ImageView ivAppIntroImage;

    public AppIntroCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @TargetApi(AppConstants.ANDROID_SDK_24)
    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        dataItem.setItemPosition(position);
        mContext = context;
        AppIntroData appIntroData = dataItem.getAppIntroDataItems();

        if (StringUtil.isNotNullOrEmptyString(appIntroData.getLabel())) {
            tvAppIntroTitle.setText(appIntroData.getLabel());
        }
        if (StringUtil.isNotNullOrEmptyString(appIntroData.getDescription())) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                tvAppIntroDescription.setText(Html.fromHtml(appIntroData.getDescription(), 0)); // for 24 api and more
            } else {
                tvAppIntroDescription.setText(Html.fromHtml(appIntroData.getDescription()));// or for older api
            }
        }

        if (StringUtil.isNotNullOrEmptyString(appIntroData.getCategory())) {
            Glide.with(mContext)
                    .load(appIntroData.getCategory()).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                            ivAppIntroImage.setImageBitmap(profileImage);
                        }
                    });
        }

    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {
    }

}

