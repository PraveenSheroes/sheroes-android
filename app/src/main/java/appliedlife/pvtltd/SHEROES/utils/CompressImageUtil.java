package appliedlife.pvtltd.SHEROES.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    public static Bitmap decodeFile(File f) {
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

    public static String setImageOnHolder(Bitmap photo) {
        byte[] buffer = new byte[4096];
        if (null != photo) {
            buffer = getBytesFromBitmap(photo);
            if (null != buffer) {
                return Base64.encodeToString(buffer, Base64.DEFAULT);
            }
        }
        return null;
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }
}


