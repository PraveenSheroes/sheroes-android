package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Sowrabh on 7/30/2015.
 */
public class CompressImageUtil {
    private static final String TAG = "CompressImageUtil";

    public static Observable<Boolean> compressImage(final Context context, final Uri fullImagePath, final String newPath, final int size){

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    Bitmap bmp = Glide.with(context)
                            .load(fullImagePath)
                            .asBitmap()
                            .skipMemoryCache(true)
                            .into(size, size)
                            .get();

                    boolean success = FileUtil.saveImageToJPGFile(bmp, newPath);

                    // Free the bitmap
                    bmp.recycle();

                    subscriber.onNext(success);
                    subscriber.onCompleted();
                }
                catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Observable<Bitmap> createBitmap(final Context context, final String fullImagePath, final int sizeWidth, final  int sizeHeight){

        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                try {
                    Bitmap bmp = Glide.with(context)
                            .load(fullImagePath)
                            .asBitmap()
                            .skipMemoryCache(true)
                            .into(sizeWidth, sizeHeight)
                            .get();

                    subscriber.onNext(bmp);
                    subscriber.onCompleted();
                }
                catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


