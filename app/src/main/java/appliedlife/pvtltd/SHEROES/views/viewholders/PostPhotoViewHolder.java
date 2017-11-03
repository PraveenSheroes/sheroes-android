package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.fragments.CreateCommunityPostFragment.getRoundedCroppedBitmap;

/**
 * Created by ujjwal on 21/03/17.
 */

public class PostPhotoViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;

    // region Butterknife Bindings
    @Bind(R.id.image)
    RoundedImageView mImage;

    @Bind(R.id.image_container)
    RelativeLayout mImageContainer;

    @Bind(R.id.image_close)
    ImageView mCloseView;

    View.OnClickListener mOnCloseClickListener;
    //endregion

    public PostPhotoViewHolder(View view, Context context, View.OnClickListener onCloseClickListener) {
        super(view);
        ButterKnife.bind(this, view);
        mContext = context;
        mOnCloseClickListener = onCloseClickListener;
    }

    public void bindData(Photo photo) {
        if (photo != null) {
            if (photo.file != null) {
                Bitmap bitmap = decodeFile(photo.file);
                mImage.setImageBitmap(bitmap);
            } else {
                if (CommonUtil.isNotEmpty(photo.url)) {
                    Glide.with(mContext)
                            .load(photo.url)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .skipMemoryCache(true)
                            .into(mImage);
                }
            }
        }
        mCloseView.setOnClickListener(mOnCloseClickListener);
    }

    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

}