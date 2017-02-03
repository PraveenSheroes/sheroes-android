package appliedlife.pvtltd.SHEROES.views.cutomeviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;

import appliedlife.pvtltd.SHEROES.R;

public class CircleImageView extends ImageView {
  int measuredHeight;
  int measuredWidth;
  boolean isImageLoadede;
  int numberOfTimesImageLoaded;
  int numberOfTimesOnMeasureCalled;
  private int errorPlaceHolderDrawableId;
  private int placeHolderDrawableId;
  private boolean isCircularImage = false;
  private ImageView imageView;
  private boolean scaleTypeDefault = true;
  private ImageLoadListener imageLoadListener;

  public CircleImageView(Context context) {
    super(context);
    imageView = this;
  }

  public CircleImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    imageView = this;
  }

  public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    imageView = this;
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }
   @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    numberOfTimesOnMeasureCalled++;
     numberOfTimesImageLoaded++;
     if(measuredWidth ==0  && measuredHeight ==0)
     {
       heightMeasureSpec = getMeasuredHeight();
       widthMeasureSpec = getMeasuredWidth();
       measuredHeight = View.MeasureSpec.getSize(heightMeasureSpec);
       measuredWidth = View.MeasureSpec.getSize(widthMeasureSpec);
       numberOfTimesImageLoaded++;
       isImageLoadede = false;
     }
  }
  public void bindImage(String imageUrl){
    if(scaleTypeDefault) {
      this.setScaleType(ImageView.ScaleType.FIT_XY);
    }else{
      this.setScaleType(getScaleType());
    }
    if(!isCircularImage) {
      if (imageUrl != null && !imageUrl.isEmpty()) {
        if(errorPlaceHolderDrawableId == 0){
          errorPlaceHolderDrawableId = R.drawable.ic_footer_button_icon;
        }
        if(placeHolderDrawableId == 0){
          placeHolderDrawableId = R.drawable.ic_footer_button_icon;
        }
        Glide.with(this.getContext()).load(imageUrl)
            .dontAnimate()
            .placeholder(placeHolderDrawableId)
            .listener(requestListener)
            .error(errorPlaceHolderDrawableId)
            .into(this);
      } else {
        if(errorPlaceHolderDrawableId == 0){
          errorPlaceHolderDrawableId = R.drawable.ic_footer_button_icon;
        }
        Glide.with(this.getContext())
            .load(errorPlaceHolderDrawableId)
            .into(this);
      }
    }else{
      if (imageUrl != null && !imageUrl.isEmpty()) {
        if(errorPlaceHolderDrawableId == 0){
          errorPlaceHolderDrawableId = R.drawable.ic_footer_button_icon;
        }
        if(placeHolderDrawableId == 0){
          placeHolderDrawableId = R.drawable.ic_footer_button_icon;
        }
          Glide.with(this.getContext())
                  .load(imageUrl)
                  .asBitmap()
                  .error(errorPlaceHolderDrawableId)
                  .placeholder(placeHolderDrawableId)
                  .into(new BitmapImageViewTarget(this) {
                    @Override
                    protected void setResource(Bitmap resource) {
                      RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(
                              getContext().getResources(), resource);
                      circularBitmapDrawable.setCircular(true);
                      imageView.setImageDrawable(circularBitmapDrawable);
                    }
                  });
      } else {
        if(placeHolderDrawableId==0){
          placeHolderDrawableId = R.drawable.ic_footer_button_icon;
        }
        if(errorPlaceHolderDrawableId == 0){
          errorPlaceHolderDrawableId = R.drawable.ic_footer_button_icon;
        }
        Glide.with(this.getContext()).load(placeHolderDrawableId).error(errorPlaceHolderDrawableId)
            .into(this);
      }
    }
  }

  public int getCustomMeasuredHeight() {
    return measuredHeight;
  }

  public int getCustomMeasuredWidth() {
    return measuredWidth;
  }

  public void setErrorPlaceHolderId(int drawable) {
    errorPlaceHolderDrawableId = drawable;
  }
  public void setPlaceHolderId(int drawable) {
    placeHolderDrawableId = drawable;
  }

  public void setScaleTypeDefault(boolean scaleTypeDefault){
    this.scaleTypeDefault = scaleTypeDefault;
  }

  public void setCircularImage(boolean circularImage) {
    isCircularImage = circularImage;
  }

  public ImageLoadListener getImageLoadListener() {
    return imageLoadListener;
  }

  public void setImageLoadListener(
      ImageLoadListener imageLoadListener) {
    this.imageLoadListener = imageLoadListener;
  }

  public interface ImageLoadListener{
    void onImageLoaded();
    void onImageLoadFailed();
  }

  private RequestListener requestListener = new RequestListener<String, GlideDrawable>() {
    @Override
    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                               boolean isFirstResource) {
      if(imageLoadListener!=null){
        imageLoadListener.onImageLoadFailed();
      }
      return false;
    }
    @Override
    public boolean onResourceReady(GlideDrawable resource, String model,
        Target<GlideDrawable> target,
        boolean isFromMemoryCache, boolean isFirstResource) {
      setImageDrawable(resource);
      if(imageLoadListener!=null){
        imageLoadListener.onImageLoaded();
      }
      return false;
    }
  };


}
