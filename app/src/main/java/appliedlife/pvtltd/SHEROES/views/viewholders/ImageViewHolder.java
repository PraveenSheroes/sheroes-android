package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.FeedItemCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ImageSolrObj;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 21/03/17.
 */

public class ImageViewHolder extends BaseViewHolder<FeedDetail> {
    // region Butterknife Bindings
    @Bind(R.id.feature_image)
    ImageView mImage;

    BaseHolderInterface viewInterface;
    private Context mContext;
    private ImageSolrObj mImageSolrObj;
    //endregion

    public ImageViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail feedDetail, Context context, int position) {
        mContext = context;
        mImageSolrObj = (ImageSolrObj) feedDetail;
        if (CommonUtil.isNotEmpty(mImageSolrObj.getImageUrl())) {
            int featureImageHeight = (CommonUtil.getWindowWidth(mContext) / 2);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, featureImageHeight);
            mImage.setLayoutParams(params);
            String imageKitUrl = CommonUtil.getThumborUri(mImageSolrObj.getImageUrl(), CommonUtil.getWindowWidth(mContext), featureImageHeight);
            if (CommonUtil.isNotEmpty(imageKitUrl)) {
                Glide.with(mContext)
                        .load(imageKitUrl)
                        .into(mImage);
            }

        }
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }

    @OnClick(R.id.feature_image)
    public void onImageClicked(){
        if(viewInterface instanceof FeedItemCallback){
            ((FeedItemCallback)viewInterface).onImagePostClicked(mImageSolrObj);
        }
    }

    @OnClick(R.id.share_card)
    protected void shareCard() {
        if (CommonUtil.isNotEmpty(mImageSolrObj.getImageUrl())) {
            CommonUtil.shareImageChooser(mContext, "Sheroes" , mImageSolrObj.getImageUrl());
        }
    }
}