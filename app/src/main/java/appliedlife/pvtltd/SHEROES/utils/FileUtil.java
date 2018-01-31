package appliedlife.pvtltd.SHEROES.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.webkit.MimeTypeMap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by Sowrabh on 2/22/14.
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    public static final String TEMPLATES_DIR = "templates";
    private static final String STOCK_DIR = "stock";

    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory() + File.separator +
                AppConstants.EXTERNAL_STORAGE_FOLDER_NAME + File.separator + AppConstants.PRIVATE_FOLDER_NAME;
    }

    public static Uri getUriFromFile(Context context, String filePath) {
        return FileProvider.getUriForFile(context,
                "com.addodoc.care.fileprovider",
                new File(filePath));
    }

    public static Uri getUriFromFile(Context context, File originalPic) {
        return FileProvider.getUriForFile(context,
                "com.addodoc.care.fileprovider",
                originalPic);
    }

    public static boolean createDirIfNotExists(String dir) {
        File folder = new File(dir);
        return folder.mkdirs();
    }

    // FILE OPERATION - DO NOT USE THIS METHOD ON THE MAIN THREAD
    public static boolean saveImageToJPGFile(Bitmap bitmap, String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            Crashlytics.getInstance().core.logException(new NullPointerException("Path isEmpty() returned - " + TextUtils.isEmpty(imagePath)));
            return false;
        }

        File image = null;
        try {
            image = new File(imagePath);
            createDirIfNotExists(image.getParent());
        } catch (NullPointerException e) {
            String message = " Path isEmpty() returned - " + TextUtils.isEmpty(imagePath) + " Bitmap isNull() returned - " + (bitmap == null);
            Crashlytics.getInstance().core.logException(new NullPointerException(message));
            return false;
            // throw e;
        }

        boolean success = false;

        // Encode the file as a PNG image.
        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
        /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (IOException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return success;
    }

    public static File createImageFile(String path, String imageFileName) throws IOException {
        // Create an image file name
        Log.v(TAG, "createImageFile() " + path + "/" + imageFileName);
        File storageDir = new File(path);
        boolean mkdirs = createDirIfNotExists(storageDir.getAbsolutePath());
        File image = new File(storageDir, imageFileName);
        return image;
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * Writes byte array to file.
     * Overwrites, if the file already exists.
     *
     * @param path
     * @param data
     * @throws IOException
     */
    public static void writeToFile(String path, byte[] data) throws IOException {

        File file = new File(path);
        FileUtil.createDirIfNotExists(file.getParent());

        if (file.exists()) { //Overwrite any existing one
            file.delete();
        }

        FileOutputStream fos = new FileOutputStream(file.getPath());
        fos.write(data);
        fos.close();
    }

    /**
     * Deletes file, if exists
     *
     * @param path
     */
    public static void deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static FilenameFilter getJpgFilter() {
        return new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                return lowercaseName.endsWith(".jpg");
            }
        };
    }

    public static FilenameFilter getHTMLFilter() {
        return new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".html");
            }
        };
    }

    public static void deleteDirectory(String path, boolean deleteContainingFolder) {
        File file = new File(path);
        if (file.exists()) {
            try {
                if (file.isDirectory() && file.list().length == 0 && !deleteContainingFolder) {
                    return;
                } else {
                    deleteFileOrDirectoryInternal(file, deleteContainingFolder);
                }
            } catch (IOException e) {
                Crashlytics.getInstance().core.logException(e);
            }
        }
    }

    // @Warning : Be careful - this is a recursive function.

    private static void deleteFileOrDirectoryInternal(File file, boolean deleteContainingFolder) throws IOException {

        if (file.isDirectory()) {
            //directory is empty, then delete it
            if (file.list().length == 0) {
                file.delete();
            } else {

                //list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    //construct the file structure
                    File fileDelete = new File(file, temp);

                    //recursive delete
                    deleteFileOrDirectoryInternal(fileDelete, deleteContainingFolder);
                }

                //check the directory again, if empty then delete it
                if (file.list().length == 0 && deleteContainingFolder) {
                    file.delete();
                }
            }
        } else {
            //if file, then delete it
            file.delete();
        }
    }


    public static String readFile(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        return getConvertedString(bufferedReader);
    }

    public static String readStream(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

        return getConvertedString(bufferedReader);
    }

    private static String getConvertedString(BufferedReader bufferedReader) throws IOException {

        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line).append("\n");
        }

        bufferedReader.close();

        return stringBuffer.toString();
    }

    public static Pair<ArrayList<String>, ArrayList<float[]>> readGrowthData(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;

        ArrayList<String> labels = new ArrayList<String>();
        ArrayList<float[]> datasets = new ArrayList<float[]>();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String values[] = line.split(",");
                String label = values[0];
                labels.add(label);

                float[] entries = new float[values.length - 1];
                for (int i = 1; i < values.length; i++) {
                    entries[i - 1] = Float.parseFloat(values[i]);
                }
                datasets.add(entries);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error reading file " + file + " - " + e.getMessage());
            Crashlytics.getInstance().core.logException(e);
        } finally {
            bufferedReader.close();
        }
        return new Pair<ArrayList<String>, ArrayList<float[]>>(labels, datasets);
    }

    // url = file path or whatever suitable URL you want.
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    public static Observable<Boolean> saveImageToGallery(final Context context, final String fullImagePath, final String newPath, final int size) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    Bitmap bmp = Glide.with(context)
                            .asBitmap()
                            .load(fullImagePath)
                            .apply(new RequestOptions().skipMemoryCache(true))
                            .into(-1, -1)
                            .get();

                    boolean success = FileUtil.saveImageToJPGFile(bmp, newPath, context);

                    // Free the bitmap
                    bmp.recycle();

                    subscriber.onNext(success);
                    subscriber.onCompleted();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static boolean saveImageToJPGFile(Bitmap bitmap, String imagePath, Context context) {
        if (TextUtils.isEmpty(imagePath)) {
            Crashlytics.getInstance().core.logException(new NullPointerException("Path isEmpty() returned - " + TextUtils.isEmpty(imagePath)));
            return false;
        }

        File image = null;
        try {
            image = new File(imagePath);
            createDirIfNotExists(image.getParent());
        } catch (NullPointerException e) {
            String message = " Path isEmpty() returned - " + TextUtils.isEmpty(imagePath) + " Bitmap isNull() returned - " + (bitmap == null);
            Crashlytics.getInstance().core.logException(new NullPointerException(message));
            return false;
            // throw e;
        }

        boolean success = false;

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, imagePath);

        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Encode the file as a PNG image.
        OutputStream outStream;
        try {

            outStream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
            outStream.flush();
            outStream.close();
            success = true;
        } catch (IOException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return success;
    }


}


