package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 21/03/17.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;

    // region Butterknife Bindings
    @Bind(R.id.image)
    ImageView mImage;

    @Bind(R.id.image_container)
    RelativeLayout mImageContainer;
    //endregion

    public PhotoViewHolder(View view, Context context, View.OnClickListener mOnItemClickListener) {
        super(view);
        ButterKnife.bind(this, view);
        mContext = context;
        mImageContainer.setOnClickListener(mOnItemClickListener);
    }

    public void bindData(Photo photo) {
        if (CommonUtil.isNotEmpty(photo.url)) {
            int width = CommonUtil.convertDpToPixel(68, mContext);
            int height = CommonUtil.convertDpToPixel(68, mContext);
             String imageThumbor = CommonUtil.getImgKitUri(photo.url, width, height);
            Glide.with(mContext)
                    .load(imageThumbor)
                    .into(mImage);
        }

    }

}