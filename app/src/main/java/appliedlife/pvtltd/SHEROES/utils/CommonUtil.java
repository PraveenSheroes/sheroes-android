package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.crashlytics.android.Crashlytics;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.squareup.pollexor.ThumborUrlBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.x500.X500Principal;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CommonUtil {

    private static final String TAG = "CommonUtil";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static boolean isUIThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * Capitalizes first letter of each word in the string
     *
     * @param string
     * @return
     */
    public static String capitalizeString(String string) {
        if (string == null)
            return null;
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String camelCaseString(String input) {
        if (input == null)
            return null;
        String[] strArray = input.split(AppConstants.SPACE);
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            if (s.length() > 0) {
                String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                builder.append(cap).append(" ");
            }
        }
        return builder.toString();
    }

    public static boolean deepLinkingRedirection(JSONObject referringParams) {
        if(referringParams.has("+is_first_session")) {
            try {
                boolean isFirstSession = referringParams.getBoolean("+is_first_session");

                 if(isFirstSession) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }

        }
        return false;
    }

    public static ContestStatus getContestStatus(Date startAt, Date endAt) {
        Date currentDate = new Date();
        if (currentDate.before(startAt)) {
            return ContestStatus.UPCOMING;
        } else if (currentDate.after(startAt) && currentDate.before(endAt)) {
            return ContestStatus.ONGOING;
        } else if (currentDate.after(startAt) && currentDate.after(endAt)) {
            return ContestStatus.COMPLETED;
        } else {
            return ContestStatus.UPCOMING;
        }
    }

    public static String ellipsize(String input, int maxLength) {
        String ellip = "...";
        if (input == null || input.length() <= maxLength
                || input.length() < ellip.length()) {
            return input;
        }
        return input.substring(0, maxLength - ellip.length()).concat(ellip);
    }


    // int ceil = (numerator - 1) / denominator + 1;
    public static int getMathCeil(int numerator, int denominator) {
        return (numerator - 1) / denominator + 1;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static float convertPixelToDp(float pixel, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = pixel / (metrics.densityDpi / 160f);
        return dp;
    }

    private static String[] suffix = new String[]{"", "k", "m", "b", "t"};
    private static int MAX_LENGTH = 4;

    public static String formatMoney(double number) {
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return r;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getAndroidVersion() {
        String version = Build.VERSION.RELEASE;
        if (TextUtils.isEmpty(version))
            return "";
        else
            return version;

    }

    public static void hideKeyboard(Activity activity) {
//		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Check if no view has focus:
        //   http://stackoverflow.com/a/7696791/559680
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Compares two version strings.
     * <p/>
     * Use this instead of String.compareTo() for a non-lexicographical
     * comparison that works for version strings. e.g. "1.10".compareTo("1.6").
     *
     * @param str1 a string of ordinal numbers separated by decimal points.
     * @param str2 a string of ordinal numbers separated by decimal points.
     * @return The result is a negative integer if str1 is _numerically_ less than str2.
     * The result is a positive integer if str1 is _numerically_ greater than str2.
     * The result is zero if the strings are _numerically_ equal.
     * @note It does not work if "1.10" is supposed to be equal to "1.10.0".
     */
    public static Integer versionCompare(String str1, String str2) {
        try {
            String[] vals1 = str1.split("\\.");
            String[] vals2 = str2.split("\\.");
            int i = 0;
            // set index to first non-equal ordinal or length of shortest version string
            while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
                i++;
            }
            // compare first non-equal ordinal number
            if (i < vals1.length && i < vals2.length) {
                int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
                return Integer.signum(diff);
            }
            // the strings are equal or one string is a substring of the other
            // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
            else {
                return Integer.signum(vals1.length - vals2.length);
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            return 0;
        }
    }

    public static boolean versionCompare(int ver1, int ver2) {
        return ver1 < ver2;
    }

    private static final X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");

    /**
     * From http://stackoverflow.com/questions/7085644/how-to-check-if-apk-is-signed-or-debug-build
     *
     * @return
     */
    /*public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }*/
    public static boolean isAppInstalled(Context context, String packageName) {
        boolean installed = false;
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                installed = true;
            } catch (PackageManager.NameNotFoundException e) {
                installed = false;
            }
        }
        return installed;
    }

    public static String getVersionName(Context context, String packageName) {

        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                return packageInfo.versionName;

            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        }
        return null;
    }

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @NonNull
    public static Intent getCallIntent(String phoneNumber) {
        String uri = "tel:" + phoneNumber;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    public static final String SAMSUNG_APP_PACKAGE_NAME = "com.sec.print.mobileprint";
    public static final String HP_APP_PACKAGE_NAME = "com.hp.android.print";
    public static final String PRINTHAND_APP_PACKAGE_NAME = "com.dynamixsoftware.printhand";

    /**
     * Opens play store screen for the given app package name
     *
     * @param context
     * @param appPackageName
     */
    public static void openPlayStore(Context context, String appPackageName) {

        try {
            Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
            playIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(playIntent);

        } catch (android.content.ActivityNotFoundException anfe) {
            Intent playIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            playIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(playIntent);
        }
    }

    public static boolean isSheoresAppLink(Uri url) {
        if (url == null || url.getScheme() == null) {
            return false;
        }
        if (((url.getScheme().equalsIgnoreCase("http") || url.getScheme().equalsIgnoreCase("https")) && (url.getHost().equalsIgnoreCase("sheroes.com") || url.getHost().equalsIgnoreCase("sheroes.in")))) {
            if (url.getPath().startsWith("/jobs") || url.getPath().startsWith("/articles") || url.getPath().startsWith("/champions") || url.getPath().startsWith("/communities") || url.getPath().startsWith("/event") || url.getPath().startsWith("/helpline") || url.getPath().startsWith("/feed")
                    || url.getPath().startsWith("/users/edit_profile") || url.getPath().startsWith("/my-challenge") || url.getPath().startsWith("/faq") || url.getPath().startsWith("/icc-members") || url.getPath().startsWith("/invite-friends") || url.getPath().startsWith("/sheroes-challenge")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSheroesValidLink(Uri url) {
        if (url == null || url.getScheme() == null) {
            return false;
        }
        return ((url.getScheme().equalsIgnoreCase("http") || url.getScheme().equalsIgnoreCase("https")) && (url.getHost().equalsIgnoreCase("sheroes.com") || url.getHost().equalsIgnoreCase("sheroes.in")));
    }

    public static boolean isBranchLink(Uri url) {
        if (url == null || url.getScheme() == null) {
            return false;
        }
        return ((url.getScheme().equalsIgnoreCase("http") || url.getScheme().equalsIgnoreCase("https")) && (url.getHost().equalsIgnoreCase("shrs.me")));
    }

    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static String getThumborUriWithUpscale(String image, int width, int height) {
        String uri = image;
        try {
            uri = SheroesThumbor.getInstance().buildImage(URLEncoder.encode(image, "UTF-8"))
                    .resize(width, height)
                    .filter(ThumborUrlBuilder.format(ThumborUrlBuilder.ImageFormat.WEBP))
                    .smart()
                    .toUrl();
        } catch (UnsupportedEncodingException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return uri;
    }

    public static String getThumborUriWithoutSmart(String image, int width, int height){
        String uri = image;
        try {
            uri = SheroesThumbor.getInstance().buildImage(URLEncoder.encode(image, "UTF-8"))
                    .resize(width, height)
                    .filter(ThumborUrlBuilder.format(ThumborUrlBuilder.ImageFormat.WEBP))
                    .toUrl();
        } catch (UnsupportedEncodingException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return uri;
    }

    public static String getThumborUriWithFit(String image, int width, int height){
        String uri = image;
        try {
            uri = SheroesThumbor.getInstance().buildImage(URLEncoder.encode(image, "UTF-8"))
                    .resize(width, height)
                    .filter(ThumborUrlBuilder.format(ThumborUrlBuilder.ImageFormat.WEBP))
                    .fitIn(ThumborUrlBuilder.FitInStyle.NORMAL)
                    .toUrl();
        } catch (UnsupportedEncodingException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return uri;
    }

    public static String getPrettyString(ArrayList<String> list) {
        if (list == null || list.size() == 0) return "";

        return list.toString().substring(1, list.toString().length() - 1);
    }

    public static String getPrettyStringVertical(ArrayList<String> list, boolean html) {
        String newline = "\n";
        if (html) {
            newline = "<br>";
        }
        String str = getPrettyString(list);
        return (str != null) ? str.replaceAll(", ", newline) : "";
    }

/*    public static void enableStrictMode(Context context) {
        if (isDebuggable()) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects()
//                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());
        }
    }*/

  /*  public static void assertBackgroundThreadOnDebug() {
        if (CommonUtil.isDebuggable() && CommonUtil.isUIThread()) {
            throw new IllegalStateException("Long running operation called from Main thread. Use background thread instead.");
        }
    }*/

    public static <T> boolean isEmpty(Collection<T> collection) {
        return (collection == null) || collection.isEmpty();
    }

    public static <S, T> boolean isEmpty(Map<S, T> collection) {
        return (collection == null) || collection.isEmpty();
    }

    public static String toString(Object object) {
        if (object == null)
            return null;
        return object.toString();
    }


    public static int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    public static SpannableString combineStringsStyles(Context context, String firstString, int firstStyle, String secondString, int secondStyle, String separator) {

        firstString = TextUtils.isEmpty(secondString) ? firstString : (firstString + separator);
        firstString = TextUtils.isEmpty(firstString) ? "" : firstString;
        secondString = TextUtils.isEmpty(secondString) ? "" : secondString;

        String finalString = firstString + secondString;
        SpannableString styledText = new SpannableString(finalString);
        styledText.setSpan(new TextAppearanceSpan(context, firstStyle), 0, firstString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, secondStyle), firstString.length(), finalString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return styledText;
    }

    public static int[] getWindowSize(Context context) {
        int screenWidth, screenHeight;
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Point point = new Point();
                display.getSize(point);
                screenWidth = point.x;
                screenHeight = point.y;
            } else {
                screenWidth = display.getWidth();
                screenHeight = display.getHeight();
            }
            return new int[]{screenWidth, screenHeight};
    }

    public static int getWindowWidth(Context context) {
        try {
            int[] size = getWindowSize(context);
            return size[0];
            } catch (NullPointerException e) {
            Crashlytics.getInstance().core.logException(e);
             }
        return 400;
    }

    public static int getWindowHeight(Context context) {
        try {
            int[] size = getWindowSize(context);
            return size[1];
        } catch (NullPointerException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return 600;
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public static boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                activity.finish();
            }
            return false;
        }
        return true;
    }

    public static void shareImageWhatsApp(final Context context, final String imageShareText, final String url, final String sourceScreen, final boolean trackEvent, final Event eventName, final HashMap<String, Object> properties) {
        CompressImageUtil.createBitmap(SheroesApplication.mContext, url, 816, 816)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Bitmap>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bmp) {
                        Event event = trackEvent ? eventName : null;
                        shareBitmapWhatsApp(context, bmp, sourceScreen, url, imageShareText, event, properties);
                    }
                });

    }

    public static void shareImageChooser(final Context context, final String imageShareText, final String url) {
        CompressImageUtil.createBitmap(SheroesApplication.mContext, url, 816, 816)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Bitmap>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bmp) {
                        shareCardViaSocial(context, bmp, imageShareText);
                    }
                });

    }

    public static String getPathFromURI(Context context, Uri contentURI) {
        String result = null;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            if (idx != -1) {
                result = cursor.getString(idx);
            }
            cursor.close();
        }
        return result;
    }

    public static File getFilFromBitmap(Context context, Bitmap bmp) {
        File cachePath = new File(context.getCacheDir(), "images");
        cachePath.mkdirs();
        FileOutputStream stream = null;
        String fileName = "IMG_" + new Date().getTime() + ".png";
        String imagePath = cachePath + "/" + fileName;
        try {
            stream = new FileOutputStream(imagePath);
        } catch (FileNotFoundException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        try {
            stream.close();
        } catch (IOException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return new File(cachePath, fileName);
    }

    //Used for fetch the Uri from other apps
    public static String getImagePathFromInputStreamUri(Context context, Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri); // context needed
                File photoFile = createTemporalFileFrom(context, inputStream);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                // log
            } catch (IOException e) {
                // log
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    private static File createTemporalFileFrom(Context context, InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile(context);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private static File createTemporalFile(Context context) {
        String tempImage = "IMG_" + new Date().getTime() + ".png";
        return new File(context.getExternalCacheDir(), tempImage);
    }
    //end

    public static void shareBitmapWhatsApp(Context context, Bitmap bmp, String sourceScreen, String url, String imageShareText, Event eventName, HashMap<String, Object> eventProperties) {
        Uri contentUri = CommonUtil.getContentUriFromBitmap(context, bmp);
        if (contentUri != null) {
            Intent sharingIntent = new Intent((Intent.ACTION_SEND));
            sharingIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            sharingIntent.setType("image/jpeg");
            if (CommonUtil.isNotEmpty(imageShareText)) {
                sharingIntent.putExtra(Intent.EXTRA_TEXT, imageShareText);
            }
            if (CommonUtil.isAppInstalled(context, "com.whatsapp")) {
                sharingIntent.setPackage("com.whatsapp");
                context.startActivity(sharingIntent);
            } else {
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
            if (eventName == null) {
                return;
            }
            if (StringUtil.isNotNullOrEmptyString(sourceScreen) && sourceScreen.equalsIgnoreCase(AppConstants.CHALLENGE_GRATIFICATION_SCREEN)) {
                HashMap<String, Object> properties = new EventProperty.Builder().sharedTo("Whatsapp").build();
                properties.put(EventProperty.URL.getString(), url);
                AnalyticsManager.trackEvent(Event.CHALLENGE_SHARED, sourceScreen, properties);
            } else {
                EventProperty.Builder builder = new EventProperty.Builder().sharedTo("Whatsapp");
                final HashMap<String, Object> properties = builder.build();
                properties.put(EventProperty.SOURCE.getString(), sourceScreen);
                if (eventName.equals(Event.IMAGE_SHARED)) {
                    properties.put(EventProperty.URL.getString(), url);
                }
                eventProperties.put(EventProperty.SHARED_TO.getString(), "Whatsapp");
                AnalyticsManager.trackEvent(eventName, sourceScreen, eventProperties);
            }

        }
    }

    public static Bitmap getViewBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.draw(canvas);
        return bitmap;
    }

    public static String encode(String featureImage) {
        URL url = null;
        URI uri = null;
        try {
            url = new URL(featureImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri.toASCIIString();
    }


    public static String getThumborUri(@NonNull String image, int width, int height) {
        String uri = image;
        if (!CommonUtil.isNotEmpty(uri)) {
            return "";
        }
        try {
            uri = SheroesThumbor.getInstance().buildImage(URLEncoder.encode(image, "UTF-8"))
                    .resize(width, height)
                    .filter(ThumborUrlBuilder.format(ThumborUrlBuilder.ImageFormat.WEBP))
                    .filter(ThumborUrlBuilder.noUpscale())
                    .smart()
                    .toUrl();
        } catch (UnsupportedEncodingException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return uri;
    }

    public static String getThumborUri(@NonNull String image, int width) {
        String uri = image;
        try {
            uri = SheroesThumbor.getInstance().buildImage(URLEncoder.encode(image, "UTF-8"))
                    .resize(width, 0)
                    .filter(ThumborUrlBuilder.format(ThumborUrlBuilder.ImageFormat.WEBP))
                    .filter(ThumborUrlBuilder.noUpscale())
                    .smart()
                    .toUrl();
        } catch (UnsupportedEncodingException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        return uri;
    }

    public static String getYoutubeURL(String videoId) {
        return "https://www.youtube.com/watch?v=" + videoId;
    }

    public static Uri getContentUriFromBitmap(Context context, Bitmap bmp) {
        File cachePath = new File(context.getCacheDir(), "images");
        cachePath.mkdirs(); // don't forget to make the directory
        FileOutputStream stream = null; // overwrites this image every time
        String fileName = "IMG_" + new Date().getTime() + ".png";
        String imagePath = cachePath + "/" + fileName;
        try {
            stream = new FileOutputStream(imagePath);
        } catch (FileNotFoundException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        try {
            stream.close();
        } catch (IOException e) {
            Crashlytics.getInstance().core.logException(e);
        }

        File newFile = new File(cachePath, fileName);
        Uri contentUri = FileProvider.getUriForFile(context, "appliedlife.pvtltd.SHEROES.provider", newFile);
        return contentUri;
    }

    public static void shareLinkToWhatsApp(Context context, String mShareText) {
        if (CommonUtil.isAppInstalled(context, "com.whatsapp")) {
            Intent sharingIntent = new Intent((Intent.ACTION_SEND))
                    .setType("text/plain");
            sharingIntent
                    .putExtra(Intent.EXTRA_TEXT, mShareText);
            sharingIntent.setPackage("com.whatsapp");
            context.startActivity(sharingIntent);
        }
    }

    public static void shareLinkToFaceBook(Context context, String mShareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mShareText);
        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith(AppConstants.FACEBOOK_SHARE)) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }
        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = AppConstants.FACEBOOK_SHARE_VIA_BROSWER + mShareText;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }
        context.startActivity(intent);
    }

    public static void shareLinkToTwitter(Context context, String mShareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mShareText);
        boolean twitterAppFound = false; // See if official twitter app is found
        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith(AppConstants.TWITTER_SHARE)) {
                intent.setPackage(info.activityInfo.packageName);
                twitterAppFound = true;
                break;
            }
        }

        if (!twitterAppFound) { //if twitter app not installed
            String sharerUrl = AppConstants.TWITTER_SHARE_VIA_BROWSER + mShareText;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }
        context.startActivity(intent);
    }


    public static class Dimension {
        public int width;
        public int height;

        Dimension(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public Dimension(float width, float height) {
            this.width = (int) width;
            this.height = (int) height;
        }
    }


    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

    public static InputStream getFileFromAssets(Context context, String pathRelativeToAssetsFolder) {
        AssetManager am = context.getAssets();
        try {
            return am.open(pathRelativeToAssetsFolder);
        } catch (IOException e) {
            return null;
        }
    }

    public static void navigateToActivitySimple(Activity fromActivity, Class toActivity) {
        if (fromActivity != null && !fromActivity.isFinishing()) {
            Intent intent = new Intent(fromActivity, toActivity);
            ActivityCompat.startActivity(fromActivity, intent, new Bundle());
        }
    }

    public static void navigateToActivityForResult(Activity fromActivity, Class toActivity, int requestCode) {
        Intent intent = new Intent(fromActivity, toActivity);
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, new Bundle());
    }

    public static boolean containsIgnoreCase(List<String> stringList, String string) {
        for (String element : stringList) {
            if (element.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    public static <T, U> int contains(Collection<T> objectList, U object, ContainsComparator<T, U> comparator) {
        int position = -1;
        for (T element : objectList) {
            ++position;
            if (comparator.equalsObject(element, object)) {
                return position;
            }
        }
        return -1;
    }

    public interface ContainsComparator<T, U> {
        boolean equalsObject(T t, U u);
    }

    public static boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isNotEmpty(String s) {
        if (s == null) {
            return false;
        }
        return !TextUtils.isEmpty(s);
    }

    public static class OnKeyboardListener {
        private float oneInchPixels;

        private void setListenerToRootView(Activity activity) {
            final View activityRootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();

                    if (heightDiff > oneInchPixels) {
                        onKeyboardVisible(true);
                    } else {
                        onKeyboardVisible(false);
                    }
                }
            });
        }

        public void onKeyboardVisible(boolean visible) {

        }

        public void init(Activity activity) {
            oneInchPixels = convertDpToPixel(160, activity);
            setListenerToRootView(activity);
        }

    }

/*    public static int getCurrentAppVersion() {
        int currentVersion = 0;
        try {
            currentVersion = CareApplication.getAppContext().getPackageManager().getPackageInfo(CareApplication.getAppContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersion;
    }*/


    public static boolean matchesWebsiteURLPattern(String sentence) {
        final Pattern pattern = Pattern.compile("\\b(https?|Https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = pattern.matcher(sentence);
        return m.find();
    }

    public static boolean isBabygogoLink(Uri url) {
        return ((url.getScheme().equalsIgnoreCase("http") || url.getScheme().equalsIgnoreCase("https")) && (url.getHost().equalsIgnoreCase("bbgo.co") || url.getHost().equalsIgnoreCase("bnc.lt")));
    }


    //returns empty if not youtube url
    public static String getYoutubeId(String url) {
        final Pattern pattern = Pattern.compile("(?<=youtube.com\\/watch\\?v=|\\/videos\\/|embed\\/|youtu.be\\/|\\/v\\/|watch\\?v%3D..%2Fvideos%2F|embed%..F|youtu.be%2F|%2Fv%..F)[^#\\&\\?\\n]*",
                Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

/*    public static void openChromeTab(Activity activity, Uri url) {
        CustomTabsIntent customTabsIntent =
                new CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.primary))
                        .setShowTitle(true)
                        .enableUrlBarHiding()
                        .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            customTabsIntent.intent.putExtra(Intent.EXTRA_REFERRER,
                    Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + activity.getPackageName()));
        }
        customTabsIntent.launchUrl(activity, url);
    }*/

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "K");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "B");
        suffixes.put(1_000_000_000_000L, "T");
    }

    public static String getRoundedMetricFormat(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return getRoundedMetricFormat(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + getRoundedMetricFormat(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public interface Callback {
        void callBack(boolean isShown);
    }


    public static synchronized boolean ensureFirstTime(String key) {
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        if (prefs == null) {
            return false;
        }
        boolean shown = prefs.getBoolean(key, false);
        if (!shown) {
            prefs.edit().putBoolean(key, true).commit();
        }
        return !shown;
    }

    public static String getPref(String key) {
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        if (prefs == null) {
            return "";
        }
        return prefs.getString(key, "");
    }

    public static void setTimeForContacts(String key, long contactSyncTime) {
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        if (null == prefs) {
            return;
        }
        prefs.edit().putLong(key, contactSyncTime).apply();
    }

    public static long getTimeForContacts(String key) {
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        if (null == prefs) {
            return 0;
        }
        return prefs.getLong(key, 0);
    }

    public static int forGivenCountOnly(String key, int n) {
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        if (prefs == null) {
            return 0;
        }
        int count = prefs.getInt(key, 1);
        if ((count >= n)) {
            if (count <= n) {
                prefs.edit().putInt(key, (count + 1)).apply();
            }
            return count;
        } else {
            if (count < n) {
                prefs.edit().putInt(key, (count + 1)).apply();
            }
            return count;
        }
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }
    }

    public static String trimBranchIdQuery(String url) {
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            return url;
        }
        if (!CommonUtil.isNotEmpty(uri.getQuery())) {
            return url;
        }
        if (uri.getQuery().contains("branch_match_id")) {
            try {
                return new URI(uri.getScheme(),
                        uri.getAuthority(),
                        uri.getPath(),
                        null, // Ignore the query part of the input url
                        uri.getFragment()).toString();
            } catch (URISyntaxException e) {
                return url;
            }
        } else {
            return url;
        }
    }

    public static void shareCardViaSocial(Context context, String deepLinkUrl) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
        intent.putExtra(AppConstants.SHARED_EXTRA_SUBJECT + Intent.EXTRA_TEXT, deepLinkUrl);
        context.startActivity(Intent.createChooser(intent, AppConstants.SHARE));
    }


    public static void shareCardViaSocial(Context context, Bitmap bmp, String deepLinkUrl) {
        Uri contentUri = CommonUtil.getContentUriFromBitmap(context, bmp);
        if (contentUri != null) {
            Intent intent = new Intent((Intent.ACTION_SEND));
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
            intent.putExtra(AppConstants.SHARED_EXTRA_SUBJECT + Intent.EXTRA_TEXT, deepLinkUrl);
            context.startActivity(Intent.createChooser(intent, AppConstants.SHARE));
        }
    }

    public static void facebookImageShare(final Activity fromActivity, String shareImageUrl) {
        CompressImageUtil.createBitmap(SheroesApplication.mContext, shareImageUrl, 816, 816)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Bitmap>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bmp) {
                        SharePhoto photo = new SharePhoto.Builder()
                                .setBitmap(bmp)
                                .build();
                        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                                .addPhoto(photo)
                                .build();
                        ShareDialog shareDialog = new ShareDialog(fromActivity);
                        shareDialog.show(sharePhotoContent, ShareDialog.Mode.AUTOMATIC);
                    }
                });
    }

    public static void shareFacebookLink(Activity fromActivity, String shareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = fromActivity.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith(AppConstants.FACEBOOK_SHARE)) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }
        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = AppConstants.FACEBOOK_SHARE_VIA_BROSWER + shareText;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }
        fromActivity.startActivity(intent);
    }

    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }

    public static void createDialog(Context context, String title, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_success);
        dialog.setTitle(title);

        TextView titleText = dialog.findViewById(R.id.title);
        titleText.setText(title);

        ImageView cross = dialog.findViewById(R.id.cross);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView messageText = dialog.findViewById(R.id.message);
        messageText.setText(message);

        dialog.show();
    }
}
