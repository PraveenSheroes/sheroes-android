package appliedlife.pvtltd.SHEROES.views.cutomeviews.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BlurTask {

  public interface Callback {

    void done(BitmapDrawable drawable);
  }

  private Resources res;
  private WeakReference<Context> contextWeakRef;
  private BlurFactor factor;
  private Bitmap bitmap;
  private Callback callback;
  private static ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

  public BlurTask(View target, BlurFactor factor, Callback callback) {
    this.res = target.getResources();
    this.factor = factor;
    this.callback = callback;
    this.contextWeakRef = new WeakReference<>(target.getContext());

    target.setDrawingCacheEnabled(true);
    target.destroyDrawingCache();
    target.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
    bitmap = target.getDrawingCache();
  }

  public BlurTask(Context context, Bitmap bitmap, BlurFactor factor, Callback callback) {
    this.res = context.getResources();
    this.factor = factor;
    this.callback = callback;
    this.contextWeakRef = new WeakReference<>(context);

    this.bitmap = bitmap;
  }

  public void execute() {
    THREAD_POOL.execute(new Runnable() {
      @Override public void run() {
        Context context = contextWeakRef.get();
        final BitmapDrawable bitmapDrawable =
            new BitmapDrawable(res, Blur.of(context, bitmap, factor));

        if (callback != null) {
          new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override public void run() {
              callback.done(bitmapDrawable);
            }
          });
        }
      }
    });
  }
}
