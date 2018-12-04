package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
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

import com.bumptech.glide.Glide;
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
import java.util.Calendar;
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
import appliedlife.pvtltd.SHEROES.vernacular.LanguageType;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class CommonUtil {

    private static final String TAG = "CommonUtil";

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
        if (referringParams.has("+is_first_session")) {
            try {
                boolean isFirstSession = referringParams.getBoolean("+is_first_session");

                if (isFirstSession) {
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

    public static int navHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    //Hide SoftKeyBoard From The View
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static boolean versionCompare(int ver1, int ver2) {
        return ver1 < ver2;
    }

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

    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

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
                    || url.getPath().startsWith("/users/edit_profile") || url.getPath().startsWith("/users") || url.getPath().startsWith("/my-challenge") || url.getPath().startsWith("/faq") || url.getPath().startsWith("/icc-members") || url.getPath().startsWith("/invite-friends") || url.getPath().startsWith("/sheroes-challenge") || url.getPath().startsWith("/write_story") || url.getPath().startsWith("/my_story") || url.getPath().startsWith("/stories")|| url.getPath().startsWith("/language_selection")) {
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

    public static String getThumborUriWithFit(String image, int width, int height) {
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

    public static <T> boolean isEmpty(Collection<T> collection) {
        return (collection == null) || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() == 0;
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
                    if (inputStream != null)
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

        // As fallback, launch sharer.php in a browser
        if (isPackageInstalled(context, AppConstants.FACEBOOK_SHARE)) {
            String sharerUrl = AppConstants.FACEBOOK_SHARE_VIA_BROSWER + mShareText;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }
        context.startActivity(intent);
    }

    public static void shareLinkToTwitter(Context context, String mShareText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, mShareText);

        if (isPackageInstalled(context, AppConstants.TWITTER_SHARE)) { //if twitter app not installed
            String sharerUrl = AppConstants.TWITTER_SHARE_VIA_BROWSER + mShareText;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }
        context.startActivity(intent);
    }

    // See if official app is found
    private static boolean isPackageInstalled(Context c, String targetPackage) {
        PackageManager pm = c.getPackageManager();
        try {
            pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
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

    public static boolean isNotEmpty(String s) {
        if (s == null) {
            return false;
        }
        return !TextUtils.isEmpty(s);
    }

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

    public static synchronized boolean getPrefValue(String key) {
        try {
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
            if (prefs != null && prefs.contains(key)) {
                return prefs.getBoolean(key, false);
            }
        } catch (Exception e) {
            LogUtils.error(TAG, e.getMessage());
        }
        return false;
    }

    public static synchronized void setPrefValue(String key) {
        try {
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
            if (prefs != null) {
                prefs.edit().putBoolean(key, true).apply();
            }
        } catch (Exception e) {
            LogUtils.error(TAG, e.toString());
        }
    }

    public static synchronized void setPrefValue(String key, boolean value) {
        try {
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
            if (prefs != null) {
                prefs.edit().putBoolean(key, value).apply();
            }
        } catch (Exception e) {
            LogUtils.error(TAG, e.toString());
        }
    }

    public static synchronized void setPrefStringValue(String key,String value) {
        try {
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
            if (prefs != null) {
                prefs.edit().putString(key, value).apply();
            }
        } catch (Exception e) {
            LogUtils.error(TAG, e.toString());
        }
    }
    public static synchronized String getPrefStringValue(String key) {
        try {
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
            if (prefs != null && prefs.contains(key)) {
                return prefs.getString(key, LanguageType.ENGLISH.toString());
            }
        } catch (Exception e) {
            LogUtils.error(TAG, e.getMessage());
        }
        return LanguageType.ENGLISH.toString();
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
        intent.putExtra(R.string.check_out_share_msg + Intent.EXTRA_TEXT, deepLinkUrl);
        context.startActivity(Intent.createChooser(intent, AppConstants.SHARE));
    }


    public static void shareCardViaSocial(Context context, Bitmap bmp, String deepLinkUrl) {
        Uri contentUri = CommonUtil.getContentUriFromBitmap(context, bmp);
        if (contentUri != null) {
            Intent intent = new Intent((Intent.ACTION_SEND));
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_TEXT, deepLinkUrl);
            intent.putExtra(R.string.check_out_share_msg + Intent.EXTRA_TEXT, deepLinkUrl);
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
            return !activity.isFinishing();
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

    public static int getCurrentAppVersion() {
        int currentVersion = 0;
        try {
            currentVersion = SheroesApplication.mContext.getPackageManager().getPackageInfo(SheroesApplication.mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVersion;
    }

    public static boolean isLaterClicked() {
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        if (prefs == null) {
            return false;
        }
        String reminderDate = prefs.getString(AppConstants.NEXT_DAY_DATE, DateUtil.toDateOnlyString(DateUtil.getCurrentDate()));
        if (DateUtil.isToday(DateUtil.parseOnlyDate(reminderDate))) {
            return false;
        } else {
            return true;
        }
    }

    public static void saveReminderForTomorrow() {
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        if (prefs == null) {
            return;
        }
        prefs.edit().putString(AppConstants.NEXT_DAY_DATE, DateUtil.toDateOnlyString(DateUtil.addDays(DateUtil.getCurrentDate(), 1))).apply();
    }

    //Show or hide the badge icon from user pic
    public static void showHideUserBadge(Context context, boolean isAnonymous, ImageView userPic, boolean isBadgeShown, String badgeUrl) {
        if (context == null) return;
        if (!isAnonymous && isBadgeShown && !TextUtils.isEmpty(badgeUrl)) {
            userPic.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(badgeUrl)
                    .into(userPic);
        } else {
            userPic.setVisibility(View.GONE);
        }
    }

    public static Map<String, Integer> initFonts() {
        Map<String, Integer> fontMap = new HashMap<>();
        fontMap.put("regular", R.font.noto_sans_regular);
        fontMap.put("light", R.font.noto_sans_regular);
        fontMap.put("medium", R.font.noto_sans_bold);
        fontMap.put("bold", R.font.noto_sans_bold);
        return fontMap;
    }
}
