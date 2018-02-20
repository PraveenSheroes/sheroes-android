package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FollowingEnum;
import appliedlife.pvtltd.SHEROES.models.entities.MentorUserprofile.PublicProfileListRequest;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.BellNotificationRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.ChallengePostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityTopPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.RemoveMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.WinnerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MyCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplineGetChatThreadRequest;
import appliedlife.pvtltd.SHEROES.models.entities.helpline.HelplinePostQuestionRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.AppIntroScreenRequest;
import appliedlife.pvtltd.SHEROES.models.entities.home.NotificationReadCount;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.ApproveSpamPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.navigation_drawer.NavigationDrawerRequest;
import appliedlife.pvtltd.SHEROES.models.entities.postdelete.DeleteCommunityPostRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.FollowersFollowingRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileTopCountRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileUsersCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.models.entities.sharemail.ShareViaMail;
import appliedlife.pvtltd.SHEROES.models.entities.she.FAQSRequest;
import appliedlife.pvtltd.SHEROES.models.entities.she.ICCMemberRequest;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataRequest;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;


public class AppUtils {
    private static final String TAG = LogUtils.makeLogTag(AppUtils.class);
    private static final String NOTAVAILABLE = " ";
    private static AppUtils sInstance;
    private long sInitTime = 0;

    private static final int MIN_AMOUNT_FOR_WALLET_CARD = 200;
    private static final int WALLET_CARD_EARN_UPTO = 4000;
    public static final String OTHER = "other";
    public static final String DEVICE_SCREEN_RESOLUTION_LDPI = "ldpi";
    public static final String DEVICE_SCREEN_RESOLUTION_MDPI = "mdpi";
    public static final String DEVICE_SCREEN_RESOLUTION_HDPI = "hdpi";
    public static final String DEVICE_SCREEN_RESOLUTION_XHDPI = "xhdpi";
    public static final String DEVICE_SCREEN_RESOLUTION_XXHDPI = "xxhdpi";
    public static final String DEVICE_SCREEN_RESOLUTION_XXXHDPI = "xxxhdpi";

    public static final String DEVICE_SCREEN_RESOLUTION_XDPI_FOR_LOGO = "xdpi";
    public static final String DEVICE_SCREEN_RESOLUTION_XXDPI_FOR_LOGO = "xxdpi";
    public static final String DEVICE_SCREEN_RESOLUTION_XXXDPI_FOR_LOGO = "xxxdpi";

    public final Pattern URL_WITH_HTTP_ADDRESS_PATTERN = Pattern.compile("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
    public final Pattern URL_START_WWW_ADDRESS_PATTERN = Pattern.compile("([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
            + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    public final Pattern MOBILE_NUMBER_PATTERN = Pattern.compile("^((\\+91?)|0|)?[0-9]{10}$");

    public final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("^[^!@#=_()?<>%$*-+!/\\.\":;,0123456789]*$");


    private static final Pattern DEVICE_ACCOUNT_EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
    private Set<String> mCompAppSet;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private String mCpuMaxFreq;
    private String mTotalRAM;
    private String mTotalExtMemory;
    private String mDeviceId;

    private AppUtils() {
    }

    /**
     * This function instantiate the initTime value.
     */
    public void initTime() {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        sInitTime = System.currentTimeMillis();
        LogUtils.exit(TAG, LogUtils.getMethodName());
    }

    public static synchronized AppUtils getInstance() {
        if (sInstance == null) {
            sInstance = new AppUtils();
        }
        return sInstance;
    }

    /**
     * Use this function to get application mContext in any class
     *
     * @return Application mContext
     */
    public Context getApplicationContext() {
        return SheroesApplication.mContext;
    }

    /**
     * This function is used to set application mContext from SheroesApplication class.
     * It is called during onCreate of application.
     *
     * @param context
     */
    public void setApplicationContext(Context context) {

    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static int getWindowHeight(Context context) {
        int[] size = getWindowSize(context);
        return size[1];
    }

    public static int getWindowWidth(Context context) {
        int[] size = getWindowSize(context);
        return size[0];
    }

    public static int[] getWindowSize(Context context) {
        int screenWidth, screenHeight;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
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

    /**
     * This method shows the softkeyboard
     *
     * @param view
     */
    public static void keyboardToggle(View view, String TAG) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }
    }

    public static void hideKeyboard(View view, String TAG) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }
    }

    public static void showKeyboard(View view, String TAG) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }
    }

    /**
     * The version number of this package, as specified by the manifest tag_item_ui_for_onboarding's
     * attribute.
     */
    public String getAppVersionName() {
        if (getPackageInfo(0) != null) {
            return getPackageInfo(0).versionName;
        } else {
            return "";
        }
    }

    public String getCloudMessaging() {
        if (getPackageInfo(0) != null) {
            return getPackageInfo(0).versionName;
        } else {
            return "";
        }
    }


    public String getApplicationDisplayName() {
        return SheroesApplication.mContext.getResources().getString(R.string.ID_APP_NAME);
    }

    /**
     * The version name of this package, as specified by the manifest
     * tag_item_ui_for_onboarding's attribute.
     */
    public int getAppVersionCode() {
        if (getPackageInfo(0) != null) {
            return getPackageInfo(0).versionCode;
        } else {
            return 0;
        }
    }

    /**
     * Check for the internet availability.
     *
     * @return true if internet connection is available.
     */
    public boolean isNetworkAvailable() {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        boolean isNetworkAvailable = false;
        ConnectivityManager connMgr = (ConnectivityManager) SheroesApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null) {
            isNetworkAvailable = networkInfo.isConnected();
            if (isNetworkAvailable) {
                LogUtils.info(TAG, "Network Type: " + networkInfo.getTypeName());
            } else {
                LogUtils.info(TAG, "Network State Reason: " + networkInfo.getReason());
            }
        }
        LogUtils.info(TAG, "Network Status: " + isNetworkAvailable);
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return isNetworkAvailable;
    }

    /**
     * This function provides the unique device id(IMEI, MEID or ESN).
     *
     * @return device id string
     */
    public String getDeviceId() {
        try {
            if (mDeviceId == null) {
                mDeviceId = Settings.Secure.getString(SheroesApplication.mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception ex) {
            LogUtils.error(TAG, ex);
            return " ";
        }
        return mDeviceId;
    }


    /**
     * Retrieve overall information about an application package that is
     * installed on the system.
     *
     * @param flags Additional option flags. Use any combination to
     *              modify the data returned.
     * @return Returns a PackageInfo object containing information about the
     * package.
     */
    public PackageInfo getPackageInfo(int flags) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        try {
            PackageManager packageManager = SheroesApplication.mContext.getPackageManager();
            return packageManager.getPackageInfo(SheroesApplication.mContext.getPackageName(), flags);
        } catch (PackageManager.NameNotFoundException ex) {
            LogUtils.error(TAG, ex.toString(), ex);
        } catch (Exception ex) {
            LogUtils.error(TAG, ex.toString(), ex);
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return null;
    }

    public String formatAmount(double amount) {
        try {
            NumberFormat _formatter = getNumberFormator();
            return _formatter.format(amount);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * @param pActivity
     * @param pAirlineIconBitmap
     * @return resized Bitmap (if resizing needed for display and resized
     * successfully) or pAirlineIconBitmap
     */
    public Bitmap resizeAirlineIcon(Activity pActivity, Bitmap pAirlineIconBitmap) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        try {
            int _neededDimension = 30;
            if (SheroesApplication.mContext.getResources().getDisplayMetrics().widthPixels > 320) {
                _neededDimension = 80;
            }

            if (pAirlineIconBitmap.getWidth() != _neededDimension) {
                pAirlineIconBitmap = resizeBitmap(pAirlineIconBitmap, _neededDimension, _neededDimension);
                LogUtils.info("Airline", "Icon re-sized: " + _neededDimension + " x " + _neededDimension);
            }
        } catch (Exception e) {
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return pAirlineIconBitmap;
    }

    /**
     * @param pCodeNameKeys
     * @param pCode
     * @param pName
     * @return
     */
    public String getAirlineKey(Collection<String> pCodeNameKeys, String pCode, String pName) {
        try {
            if (pCodeNameKeys != null && pCodeNameKeys.contains(pCode + pName)) {
                return pCode + pName;
            }

            if (pCodeNameKeys == null) {
                return "";
            }
            for (String _tempKey : pCodeNameKeys) {
                if (_tempKey.equalsIgnoreCase(pCode + pName)) {
                    return _tempKey;
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /*
     * Get Number Formatter instance
     */
    public NumberFormat getNumberFormator() {
        return new DecimalFormat("###,##,###", new DecimalFormatSymbols(Locale.ENGLISH));
    }

    public boolean checkUrl(String url) {
        return URL_WITH_HTTP_ADDRESS_PATTERN.matcher(url).matches();
    }

    public boolean checkWWWUrl(String url) {
        return URL_START_WWW_ADDRESS_PATTERN.matcher(url).matches();
    }

    public boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public boolean checkMobileNumber(String mobileNumber) {
        return MOBILE_NUMBER_PATTERN.matcher(mobileNumber).matches();
    }

    public void dial(String number) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        try {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number + ""));
            dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(dialIntent);
        } catch (Exception e) {
            LogUtils.error(TAG, new Exception("dialing number failed:: " + number + e));
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
    }


    public void email(String email, String subject, String text) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(Intent.createChooser(intent, "Send email"));
        LogUtils.exit(TAG, LogUtils.getMethodName());
    }

    public String convertStreamToString(InputStream is) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        /*
         * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        StringBuffer sb = new StringBuffer();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            LogUtils.info("Memory Exception", "Out of memory");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        LogUtils.info("Converted.", "" + new Date());
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return sb.toString();
    }

    public byte[] decompressStreamToBytes(InputStream is) throws IOException {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        /** read count */
        int read = 0;
        /** data will be read in chunks */
        byte[] data = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPInputStream zis = new GZIPInputStream(is);

        try {
            while ((read = zis.read(data)) != -1) {
                baos.write(data, 0, read);
            }
            LogUtils.exit(TAG, LogUtils.getMethodName());
            return baos.toByteArray();
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        } finally {
            baos.close();
            zis.close();
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return null;
    }

    /**
     * Converts InputStream to array of bytes
     *
     * @param is
     * @return byte array from given stream
     */
    public byte[] convertStreamtoBytes(InputStream is) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        /** read count */
        int read = 0;
        /** data will be read in chunks */
        byte[] data = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while ((read = is.read(data)) != -1) {
                baos.write(data, 0, read);
            }
            LogUtils.exit(TAG, LogUtils.getMethodName());
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                    baos = null;
                }
            } catch (Exception ex) {
                LogUtils.error(TAG, ex.toString(), ex);
            }
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return null;
    }

    public Bitmap resizeBitmap(Bitmap pLogoBitmap, int newWidth, int newHeight) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        if (null != pLogoBitmap) {
            int width = pLogoBitmap.getWidth();
            int height = pLogoBitmap.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // create the new Bitmap object

            try {
                Bitmap resizedBitmap = Bitmap.createBitmap(pLogoBitmap, 0, 0, width, height, matrix, false);
                if (resizedBitmap != pLogoBitmap) {
                    pLogoBitmap.recycle();
                    pLogoBitmap = null;
                    pLogoBitmap = resizedBitmap;
                    resizedBitmap = null;
                }

//                System.gc();
                LogUtils.exit(TAG, LogUtils.getMethodName());
                return pLogoBitmap;
            } catch (Exception e) {
                LogUtils.error(LogUtils.getClassName(), e.toString(), e);
            }
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return null;
    }

    public String getPriceString(int amount) {
        String str = "" + amount;
        if (str.length() > 3) {
            str = str.substring(0, str.length() - 3) + "," + str.substring(str.length() - 3);
        }
        return str;
    }


    public <T> T parseUsingGSON(InputStream is, String classPath) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        T queryResult = null;

        if (is != null) {

            Reader reader = new InputStreamReader(is, Charset.forName("UTF-8"));

            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();

            try {

                try {
                    queryResult = (T) gson.fromJson(reader, Class.forName(classPath));
                } catch (ClassNotFoundException e) {
                    LogUtils.error("G-PAR", " ClassNotFoundException " + e.toString(), e);
                }
            } catch (JsonSyntaxException e) {
                LogUtils.error("G-PAR", " JsonSyntaxException " + e.toString(), e);
                return null;
            } catch (JsonIOException e) {
                LogUtils.error("G-PAR", "JsonIOException " + e.toString(), e);
                return null;
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return queryResult;
    }

    // Nikhil
    public static <T> T parseUsingGSONFromJSON(String is, String classPath) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        T queryResult = null;

        if (is != null) {

            // Reader reader = new InputStreamReader(is);

            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();

            try {

                try {
                    queryResult = (T) gson.fromJson(is, Class.forName(classPath));
                    // queryResult = (T)
                    // gson.fromJson(reader,Class.forName(classPath));
                } catch (ClassNotFoundException e) {
                    LogUtils.error("G-PAR", " ClassNotFoundException " + e.toString(), e);
                }
            } catch (JsonSyntaxException e) {
                LogUtils.error("G-PAR", " JsonSyntaxException " + e.toString(), e);
                return null;
            } catch (JsonIOException e) {
                LogUtils.error("G-PAR", "JsonIOException " + e.toString(), e);
                return null;
            }

        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return queryResult;
    }

    public String convertToJsonUsingGson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public String convertToJsonUsingGsonDisableHTMLEscape(Object object) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(object);
    }

    public float convertDpToPixel(float dp) {
        Resources resources = SheroesApplication.mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public String getPaybackPointsForFlights(Double pTotalAmount) {
        long numberOfPoints = (long) Math.floor(pTotalAmount / 100);
        return String.valueOf(numberOfPoints);
    }

    public String getPaybackPointsForHotels(float pTotalAmount) {
        long numberOfPoints = (long) Math.floor((pTotalAmount * 3) / 100);
        return String.valueOf(numberOfPoints);
    }

    public String getRailFormatedDateString(Calendar calendar) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        int day;
        int month;
        int year;
        String returnString = "";

        if (calendar != null) {
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((month < 10) ? ("0" + (month + 1)) : (month + 1)).append("/")
                    .append((day < 10) ? ("0" + day) : day).append("/")
                    .append(year);
            returnString = stringBuilder.toString();
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return returnString;
    }


    public void launchDialer(String number) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        try {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number + ""));
            dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SheroesApplication.mContext.startActivity(dialIntent);
        } catch (ActivityNotFoundException activityException) {
            LogUtils.error(TAG, "Call failed", activityException);
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
    }

    public void launchEmailClient(String email, String subject, String text) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SheroesApplication.mContext.startActivity(Intent.createChooser(intent, "Send email"));
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
    }

    /**
     * returns the string representation of pixel density of the current device
     *
     * @return
     */
    public String getScreenDensityName() {
        String screenDensityName = null;
        double density = AppUtils.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_XXXHDPI;
        } else if (density >= 3.0) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_XXHDPI;
        } else if (density >= 2.0) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_XHDPI;
        } else if (density >= 1.5) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_HDPI;
        } else if (density >= 1.0) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_MDPI;
        } else {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_LDPI;
        }
        return screenDensityName;
    }

    public String getScreenDensityNameForAirlineLogo() {
        String screenDensityName = null;
        double density = AppUtils.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_XXXDPI_FOR_LOGO;
        } else if (density >= 3.0) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_XXDPI_FOR_LOGO;
        } else if (density >= 2.0) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_XDPI_FOR_LOGO;
        } else if (density >= 1.5) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_HDPI;
        } else if (density >= 1.0) {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_MDPI;
        } else {
            screenDensityName = DEVICE_SCREEN_RESOLUTION_LDPI;
        }
        return screenDensityName;
    }

    //Return the total RAM of the Device.
    public String getTotalRAMinGB() {
        try {
            if (mTotalRAM == null) {
                ActivityManager activityManager = (ActivityManager) getApplicationContext().
                        getSystemService(getApplicationContext().ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
                activityManager.getMemoryInfo(memInfo);
                long ramMemory = memInfo.totalMem / AppConstants.ONEGIGABYTE;
                mTotalRAM = String.valueOf(ramMemory);
            }
        } catch (Exception ex) {
            LogUtils.error(TAG, ex);
            mTotalRAM = NOTAVAILABLE;
        }
        return mTotalRAM;
    }

    //Return the processor max Fequency of the Device.
    public String getProcessorMaxFequency() {
        try {
            if (mCpuMaxFreq == null) {
                RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
                if (reader != null) {
                    mCpuMaxFreq = reader.readLine();
                    reader.close();
                } else {
                    mCpuMaxFreq = NOTAVAILABLE;
                }
            }
        } catch (Exception ex) {
            LogUtils.error(TAG, ex);
            mCpuMaxFreq = NOTAVAILABLE;
        }
        return mCpuMaxFreq;
    }


    //Return the total external memory of the Device.
    public String getTotalExternalMemoryinGB() {
        try {
            if (mTotalExtMemory == null) {
                int totalExMemory;
                StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
                long sdAvailSize = stat.getBlockCount()
                        * stat.getBlockSize();
                totalExMemory = Math.round(sdAvailSize / AppConstants.ONEGIGABYTE);
                mTotalExtMemory = String.valueOf(totalExMemory);
            }
        } catch (Exception ex) {
            LogUtils.error(TAG, ex);
            mTotalExtMemory = NOTAVAILABLE;
        }
        return mTotalExtMemory;
    }


    /**
     * Save a screenshot in internal file storage.
     *
     * @param view Instance of activity which will be shared
     *             fileName Screenshot file name
     */
    public String saveScreenShot(View view, String fileName) throws IOException {
        String path = null;
        FileOutputStream fileOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);

            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
            path = Environment.getExternalStorageDirectory() + File.separator + fileName + ".png";
            File imageFile = new File(path);
            imageFile.createNewFile();
            fileOutputStream = new FileOutputStream(imageFile);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());

            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        } catch (Throwable throwable) {
            LogUtils.error(TAG, throwable);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }
        return path;
    }

    /**
     * Share a screenshot using this method.
     *
     * @param activity: Activity context
     *                  filePath: Path of Screenshot
     *                  title: title for the chooser dialog
     *                  description : extra text with screenshot.
     * @return void
     */
    public void shareScreenShot(Activity activity, String filePath, String title, String
            description) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse("file://" + filePath);
        intent.setType("*/*");
        if (description != null) {
            intent.putExtra(Intent.EXTRA_TEXT, description);
        }
        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        activity.startActivity(Intent.createChooser(intent, title));
    }


    public BitmapFactory.Options getRGB565ConfigOption() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return options;
    }

    public BitmapFactory.Options getRGB565ConfigOptionBelowM() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        else
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return options;
    }

    public Bitmap getBlurBitmap(int drawabeId, Bitmap bitmap, int blur, boolean lowCompress) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                RenderScript renderScript = RenderScript.create(getApplicationContext());
                Bitmap output = null;
                if (lowCompress) {
                    output = getLowCompressedBackground(drawabeId, bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig(), lowCompress);
                } else {
                    output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                }
                ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
                Allocation inAlloc = Allocation.createFromBitmap(renderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_GRAPHICS_TEXTURE);
                Allocation outAlloc = Allocation.createFromBitmap(renderScript, output);
                script.setRadius(blur);
                script.setInput(inAlloc);
                script.forEach(outAlloc);
                outAlloc.copyTo(output);
                inAlloc.destroy();
                outAlloc.destroy();
                renderScript.destroy();
                return output;
            }
        } catch (Throwable e) {
            LogUtils.error(TAG, e.toString(), e);
        }
        return bitmap;
    }


    public String getConnectionTypeAndApproxSpeed(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return "WiFi";
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return "1xRTT_50-100kbps"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "CDMA_14-64kbps"; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "EDGE_50-100kbps"; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return "EVDO_0_400-1000kbps"; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return "EVDO_A_600-1400kbps"; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "GPRS_100kbps"; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return "HSDPA_2-14Mbps"; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return "HSPA_700-1700kbps"; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return "HSUPA_1-23Mbps"; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return "UMTS_400-7000kbps"; // ~ 400-7000 kbps
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return "EHRPD_1-2Mbps"; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return "EVDO_B_5Mbps"; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return "HSPAP_10-20Mbps"; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return "IDEN_25kbps"; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return "LTE_10+Mbps"; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return "UNKNOWN";
            }
        } else {
            return "Not Connected";
        }
    }

    private boolean isPackageInstalled(String packageName) {
        PackageManager pm = SheroesApplication.mContext.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.info(TAG, "Package not found");
            return false;
        } catch (Exception e) {
            LogUtils.error(TAG, "Exception in checking package installation", e);
            return false;
        }
    }

    public boolean isCompAppInstalled(Set<String> compAppSet) {
        try {
            if (compAppSet != null) {
                for (String compApp : compAppSet) {
                    if (isPackageInstalled(compApp)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }
        return false;
    }

    public void setCompAppSet(Set<String> compAppSet) {
        mCompAppSet = compAppSet;
    }

    public Set<String> getmCompAppSet() {
        return mCompAppSet;
    }


    public static Bitmap getCompressedBackground(int resId, int width, int height, Bitmap.Config config) {
        Bitmap bitmap = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inPreferredConfig = config;
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(AppUtils.getInstance().getApplicationContext().getResources(),
                resId, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image.
        int scaleFactor = (int) Math.max(1.0, Math.min((double) photoW / (double) width, (double) photoH / (double) height));
        scaleFactor = (int) Math.pow(2.0, Math.floor(Math.log((double) scaleFactor) / Math.log(2.0)));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        do {
            try {
                scaleFactor *= 2;
                bitmap = BitmapFactory.decodeResource(AppUtils.getInstance().getApplicationContext().getResources(), resId, bmOptions);
            } catch (OutOfMemoryError e) {
                bmOptions.inSampleSize = scaleFactor;
                LogUtils.error(TAG, "OutOfMemoryError: trying to resize image " + scaleFactor, e);
            }
        }
        while (bitmap == null && scaleFactor <= 256);

        return bitmap;

    }

    /**
     * this scales bitmap to next power of 2
     * Be carefull to use
     */

    public static Bitmap getLowCompressedBackground(int resId, int width, int height, Bitmap.Config config, boolean mutable) {
        Bitmap bitmap = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inPreferredConfig = config;
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inMutable = mutable;
        BitmapFactory.decodeResource(AppUtils.getInstance().getApplicationContext().getResources(),
                resId, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image.
        int scaleFactor = (int) Math.max(1.0, Math.min((double) photoW / (double) width, (double) photoH / (double) height));
        scaleFactor = (int) Math.pow(2.0, Math.ceil(Math.log((double) scaleFactor) / Math.log(2.0)));
        scaleFactor = Math.max(2, scaleFactor);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        do {
            try {
                scaleFactor *= 2;
                bitmap = BitmapFactory.decodeResource(AppUtils.getInstance().getApplicationContext().getResources(), resId, bmOptions);
            } catch (OutOfMemoryError e) {
                bmOptions.inSampleSize = scaleFactor;
                LogUtils.error(TAG, "OutOfMemoryError: trying to resize image " + scaleFactor, e);
            }
        }
        while (bitmap == null && scaleFactor <= 256);

        return bitmap;

    }

    public String makeDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static String capitalize(String s) {
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

    public String getScreenResoluction() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
    }

    public Object convertViewToDrawable(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        view.destroyDrawingCache();
        return new BitmapDrawable(getApplicationContext().getResources(), viewBmp);

    }

    public DisplayMetrics getDefaultDisplay() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        Display display = ((WindowManager) AppUtils.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getMetrics(mDisplayMetrics);
        return mDisplayMetrics;
    }

    /**
     * Request for Navigation drawer items
     */
    public NavigationDrawerRequest navigationOptionsRequestBuilder() {
        AppUtils appUtils = AppUtils.getInstance();
        NavigationDrawerRequest navigationDrawerRequest = new NavigationDrawerRequest();
        navigationDrawerRequest.setDisplayDefault(false);
        navigationDrawerRequest.setSource(AppConstants.SOURCE_NAME);
        navigationDrawerRequest.setAppVersion(appUtils.getAppVersionName());
        return navigationDrawerRequest;
    }

    public static String getStringContent(String fileName) {
        AssetManager assetManager = AppUtils.getInstance().getApplicationContext().getAssets();
        InputStream input;
        String content = "";

        try {
            input = assetManager.open(fileName);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            content = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * @param imageURL      : the original image url
     * @param splitArgument : the substring which needs to be replaced with device density e.g : "appupgrade"
     * @return imageUrl :corresponding to the device density
     */
    public String getImageUrlForDensity(String imageURL, String splitArgument) {
        try {
            if (!StringUtil.isNotNullOrEmptyString(imageURL)) {
                String split[] = imageURL.split(splitArgument + "/");
                StringBuilder builder = new StringBuilder();
                if ((split.length >= 2) && (split[0] != null) && (split[1] != null)) {
                    builder.append(split[0]);
                    builder.append(splitArgument).append("/");
                    builder.append(getScreenDensityName()).append("/");
                    builder.append(split[1]);
                    return builder.toString();
                }
                return imageURL;
            }
        } catch (Exception ex) {
            LogUtils.error(TAG, ex.toString(), ex);
        }
        return imageURL;
    }

    public static int getIntFromString(String s) {
        if (s == null) {
            return 0;
        }
        int value;
        try {
            value = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            value = 0;
        }
        // only got here if we didn't return false
        return value;
    }

    public static double getDoubleFromString(String s) {
        if (s == null) {
            return 0;
        }
        double value;
        try {
            value = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            value = 0;
        }
        // only got here if we didn't return false
        return value;
    }


    public static InputStream getStreamFromAsset(String fileName) {
        AssetManager assManager = AppUtils.getInstance().getApplicationContext().getAssets();
        InputStream is = null;
        try {
            is = assManager.open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new BufferedInputStream(is);
    }


    /**
     * @return should return device's end user name as per API
     * may differ and return model number in case of other Vendors than google
     */
    public String getDeviceModel() {
        String brand = Build.BRAND;
        if (!StringUtil.isNotNullOrEmptyString(brand)) {
            brand = Build.MANUFACTURER;
        }
        String model = Build.MODEL;
        if (model.startsWith(brand)) {
            return model;
        }
        return brand + " " + model;
    }

    /**
     * @return manufacturer of the device for tracking
     * if not available then Brand
     */
    public String getDeviceManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        if (!StringUtil.isNotNullOrEmptyString(manufacturer)) {
            manufacturer = Build.BRAND;
        }
        return manufacturer;
    }


    /**
     * it will use to get the list of all apps installed in the system from the list provided at the time of calling..
     *
     * @param compAppSet
     * @return list of competitor app list with "|" as a saperator
     */
    public String listOfAllCompAppInstalled(Set<String> compAppSet) {
        String appList = "";
        try {
            if (compAppSet != null) {
                for (String compApp : compAppSet) {
                    if (AppUtils.getInstance().isPackageInstalled(compApp)) {
                        appList = appList + compApp + "|";
                    }
                }
                appList = appList.substring(0, appList.lastIndexOf("|"));
                appList = (!StringUtil.isNotNullOrEmptyString(appList) ? appList : "null");
            }
        } catch (Exception e) {
            LogUtils.enter(TAG, e.toString());
            appList = "null";
        }
        return appList;
    }


    public boolean hasNotificationExpired(String campaign) {
        try {
            if (!StringUtil.isNotNullOrEmptyString(campaign)) {
                String[] ntfData = campaign.split("_");
                String ntfExpDate = "";
                int len = ntfData.length;
                if (len > 1) {
                    ntfExpDate = ntfData[len - 1];
                }
                if (!StringUtil.isNotNullOrEmptyString(ntfExpDate)) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Date expDate = sdf.parse(ntfExpDate);
                        Date curDate = sdf.parse(sdf.format(new Date()));
                        return expDate.before(curDate);
                    } catch (ParseException e) {
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            LogUtils.error(TAG, e.getMessage(), e);
            return false;
        }
    }

    /* Function to check if fragment UI is active*/
    public static boolean isFragmentUIActive(Fragment frag) {
        return frag != null && frag.getActivity() != null && frag.isAdded() && !frag.isDetached() && !frag.isRemoving();
    }

    /* Function to check if support fragment UI is active*/
    public static boolean isFragmentUIActive(android.support.v4.app.Fragment frag) {
        return frag != null && frag.getActivity() != null && frag.isAdded() && !frag.isDetached() && !frag.isRemoving();
    }


    public String getIMEI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) SheroesApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                if (telephonyManager.getDeviceId() != null) {
                    return telephonyManager.getDeviceId();
                }
            }
        } catch (Exception ex) {
            LogUtils.error(TAG, "Error : " + ex, ex);
            return " ";
        }
        return " ";
    }


    public static int findNthIndexOf(String str, String needle, int occurence) throws IndexOutOfBoundsException {
        int index = 0;
        Pattern p = Pattern.compile(needle, Pattern.MULTILINE);
        Matcher m = p.matcher(str);
        while (m.find()) {
            if (--occurence == 0) {
                index = m.start();
                break;
            }
        }
        return index;
    }

    public static LoginRequest loginRequestBuilder() {
        LoginRequest loginRequest = new LoginRequest();
        AppUtils appUtils = AppUtils.getInstance();
        //TODO:: check real data
        loginRequest.setAppVersion(appUtils.getAppVersionName());
        loginRequest.setAdvertisementid(appUtils.getDeviceManufacturer());
        loginRequest.setDeviceid(appUtils.getDeviceId());
        loginRequest.setDevicetype(AppConstants.SOURCE_NAME);
        return loginRequest;
    }

    public static RemoveMemberRequest removeMemberRequestBuilder(Long communityId, Long userid) {
        RemoveMemberRequest removeMemberRequest = new RemoveMemberRequest();
        AppUtils appUtils = AppUtils.getInstance();
        removeMemberRequest.setCommunityId(communityId);
        removeMemberRequest.setUserId(userid);
        removeMemberRequest.setDeviceUniqueId(appUtils.getDeviceId());
        removeMemberRequest.setCommunityId(communityId);
        removeMemberRequest.setAppVersion(appUtils.getAppVersionName());
        removeMemberRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        return removeMemberRequest;

    }


    public static FeedRequestPojo userCommunityPostRequestBuilder(String typeOfFeed, int pageNo, long communityId) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setCommunityId(communityId);
        return feedRequestPojo;
    }

    public FeedRequestPojo userCommunityDetailRequestBuilder(String typeOfFeed, int pageNo, long communityId) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setIdForFeedDetail(communityId);
        return feedRequestPojo;
    }

    public ProfileUsersCommunityRequest userCommunitiesRequestBuilder(int pageNo, long userId) {
        ProfileUsersCommunityRequest profileUsersCommunityRequest = new ProfileUsersCommunityRequest();
        profileUsersCommunityRequest.setAppVersion(getAppVersionName());
        profileUsersCommunityRequest.setDeviceUniqueId(getDeviceId());
        profileUsersCommunityRequest.setCloudMessagingId(getCloudMessaging());
        profileUsersCommunityRequest.setPageNo(pageNo);
        profileUsersCommunityRequest.setPageSize(AppConstants.PAGE_SIZE);
        profileUsersCommunityRequest.setUserId(userId);

        return profileUsersCommunityRequest;
    }

    public static ShareViaMail shareRequestBuilder(String deepLinkUrl, Long communityId, String emailIds, String subject) {
        AppUtils appUtils = AppUtils.getInstance();
        ShareViaMail shareViaMail = new ShareViaMail();
        shareViaMail.setAppVersion(appUtils.getAppVersionName());
        shareViaMail.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        shareViaMail.setCloudMessagingId(appUtils.getCloudMessaging());
        shareViaMail.setCommunityId(communityId);
        shareViaMail.setDeepLinkUrl(deepLinkUrl);
        shareViaMail.setEmailId(emailIds);
        shareViaMail.setSubject(subject);
        return shareViaMail;
    }

    public static NotificationReadCount notificationReadCountRequestBuilder(String screenName) {
        AppUtils appUtils = AppUtils.getInstance();
        NotificationReadCount notificationReadCount = new NotificationReadCount();
        notificationReadCount.setAppVersion(appUtils.getAppVersionName());
        notificationReadCount.setDeviceUniqueId(appUtils.getDeviceId());
        notificationReadCount.setLastScreenName(screenName);
        notificationReadCount.setScreenName(screenName);
        return notificationReadCount;
    }

    public AppIntroScreenRequest appIntroRequestBuilder(int pageId) {
        AppIntroScreenRequest appIntroScreenRequest = new AppIntroScreenRequest();
        appIntroScreenRequest.setSheroesPageId(pageId);
        return appIntroScreenRequest;
    }

    /**
     * Request for feed api
     */
    public FeedRequestPojo feedRequestBuilder(String typeOfFeed, int pageNo) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        return feedRequestPojo;
    }

    public static MyCommunityRequest myCommunityRequestBuilder(String typeOfFeed, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        MyCommunityRequest myCommunityRequest = new MyCommunityRequest();
        myCommunityRequest.setAppVersion(appUtils.getAppVersionName());
        myCommunityRequest.setSource(AppConstants.SOURCE_NAME);
        myCommunityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        myCommunityRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        myCommunityRequest.setPageNo(pageNo);
        myCommunityRequest.setPageSize(AppConstants.PAGE_SIZE);
        myCommunityRequest.setSubType(typeOfFeed);
        return myCommunityRequest;
    }

    public static MyCommunityRequest myCommunityRequestBuilder(String typeOfFeed, int pageNo, int pageSize) {
        AppUtils appUtils = AppUtils.getInstance();
        MyCommunityRequest myCommunityRequest = new MyCommunityRequest();
        myCommunityRequest.setAppVersion(appUtils.getAppVersionName());
        myCommunityRequest.setSource(AppConstants.SOURCE_NAME);
        myCommunityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        myCommunityRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        myCommunityRequest.setPageNo(pageNo);
        myCommunityRequest.setPageSize(pageSize);
        myCommunityRequest.setSubType(typeOfFeed);
        return myCommunityRequest;
    }

    public PublicProfileListRequest pubicProfileRequestBuilder(int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        PublicProfileListRequest publicProfileListRequest = new PublicProfileListRequest();
        publicProfileListRequest.setAppVersion(appUtils.getAppVersionName());
        publicProfileListRequest.setPageNo(pageNo);
        publicProfileListRequest.setPageSize(AppConstants.PAGE_SIZE);
        return publicProfileListRequest;
    }

    public FollowersFollowingRequest followerFollowingRequest(int pageNo, long userId, String followerFollowingType) {
        switch (followerFollowingType) {
            case AppConstants.FOLLOWED_CHAMPION:
               return followerFollowingRequestBuilder(pageNo, userId, false, false);
            case AppConstants.FOLLOWERS:
                return followerFollowingRequestBuilder(pageNo, userId, false, true);
            case AppConstants.FOLLOWING:
                return followerFollowingRequestBuilder(pageNo, userId, true, true);
             default:
                 return null;
        }
    }

    private FollowersFollowingRequest followerFollowingRequestBuilder(int pageNo, long userId, boolean is_user, boolean is_listing) {
        AppUtils appUtils = AppUtils.getInstance();
        FollowersFollowingRequest followersFollowingRequest = new FollowersFollowingRequest();
        followersFollowingRequest.setPageNo(pageNo);
        followersFollowingRequest.setAppVersion(appUtils.getAppVersionName());
        followersFollowingRequest.setUserId(userId);
        followersFollowingRequest.setIsListing(is_listing);
        followersFollowingRequest.setIsUser(is_user);
        followersFollowingRequest.setPageSize(AppConstants.PAGE_SIZE);
        return followersFollowingRequest;
    }

    public ProfileTopCountRequest profileTopSectionCount(long id) {
        AppUtils appUtils = AppUtils.getInstance();
        ProfileTopCountRequest profileTopCountRequest = new ProfileTopCountRequest();
        profileTopCountRequest.setPageNo(AppConstants.ONE_CONSTANT);
        profileTopCountRequest.setAppVersion(appUtils.getAppVersionName());
        profileTopCountRequest.setUserId(id);
        profileTopCountRequest.setMentorId(id);
        profileTopCountRequest.setUsersFollower(true);
        profileTopCountRequest.setUsersFollowing(true);
        profileTopCountRequest.setPageSize(AppConstants.PAGE_SIZE);
        return profileTopCountRequest;
    }

    public FeedRequestPojo articleCategoryRequestBuilder(String typeOfFeed, int pageNo, List<Long> categoryIds) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setCategoryIds(categoryIds);
        return feedRequestPojo;
    }

    public static FeedRequestPojo jobCategoryRequestBuilder(String typeOfFeed, int pageNo, List<String> cities, Integer experienceFrom, Integer experienceTo, List<String> functionalAreas, List<String> opportunityTypes, List<String> skills) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setCities(cities);
        feedRequestPojo.setExperienceFrom(experienceFrom);
        feedRequestPojo.setExperienceTo(experienceTo);
        feedRequestPojo.setFunctionalAreas(functionalAreas);
        feedRequestPojo.setOpportunityTypes(opportunityTypes);
        feedRequestPojo.setSkills(skills);
        return feedRequestPojo;
    }

    public FeedRequestPojo usersFeedDetailRequestBuilder(String typeOfFeed, int pageNo, long idForDetail, boolean hideAnnonymousPost) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setAutherId((int) idForDetail);
        feedRequestPojo.setAnonymousPostHide(hideAnnonymousPost);
        return feedRequestPojo;
    }

    public FeedRequestPojo feedDetailRequestBuilder(String typeOfFeed, int pageNo, long idForDetail) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setIdForFeedDetail(idForDetail);
        return feedRequestPojo;
    }

    public WinnerRequest winnerRequestBuilder(String contestId) {
        AppUtils appUtils = AppUtils.getInstance();
        WinnerRequest winnerRequest = new WinnerRequest();
        winnerRequest.setAppVersion(appUtils.getAppVersionName());
        winnerRequest.setDeviceUniqueId(appUtils.getDeviceId());
        winnerRequest.setScreenName("Feed");
        winnerRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        winnerRequest.challengeId = contestId;
        return winnerRequest;
    }

    public static FeedRequestPojo makeFeedRequest(String typeOfFeed, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        feedRequestPojo.setScreenName("Feed");
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        feedRequestPojo.setSubType(typeOfFeed);
        return feedRequestPojo;
    }
    public  SearchUserDataRequest searchUserDataRequest(String query,String listType,long entityOrParticipantId) {
        AppUtils appUtils = AppUtils.getInstance();
        SearchUserDataRequest searchUserDataRequest = new SearchUserDataRequest();
        searchUserDataRequest.setParticipatingEntityOrParticipantId(entityOrParticipantId);
        searchUserDataRequest.setAppVersion(appUtils.getAppVersionName());
        searchUserDataRequest.setDeviceUniqueId(appUtils.getDeviceId());
        searchUserDataRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        searchUserDataRequest.setListTypeForUserTagging(listType);
        searchUserDataRequest.setSearchNameOfUserForTagging(query);
        return searchUserDataRequest;
    }

    public static FeedRequestPojo makeFeedChallengeListRequest(String typeOfFeed, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        feedRequestPojo.setScreenName("Feed");
        feedRequestPojo.setOnlyActive(true);
        feedRequestPojo.setAcceptedOrActive(true);
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        feedRequestPojo.setSubType(typeOfFeed);
        return feedRequestPojo;
    }

    public static FeedRequestPojo makeChallengeResponseRequest(String typeOfFeed, int challengeId, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        feedRequestPojo.setSourceEntityId(challengeId);
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        feedRequestPojo.setSubType(typeOfFeed);
        return feedRequestPojo;
    }

    public static DeleteCommunityPostRequest deleteCommunityPostRequest(long idOfEntityParticipant) {
        AppUtils appUtils = AppUtils.getInstance();
        DeleteCommunityPostRequest deleteCommunityPostRequest = new DeleteCommunityPostRequest();
        deleteCommunityPostRequest.setAppVersion(appUtils.getAppVersionName());
        deleteCommunityPostRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        deleteCommunityPostRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        deleteCommunityPostRequest.setIdOfEntityOrParticipant(idOfEntityParticipant);
        return deleteCommunityPostRequest;
    }

    public static FeedRequestPojo getBookMarks(int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        return feedRequestPojo;
    }

    public UserSummaryRequest getUserProfileRequestBuilder(String subType, String type, String imageUrl) {
        AppUtils appUtils = AppUtils.getInstance();
        UserSummaryRequest userSummaryRequest = new UserSummaryRequest();
        userSummaryRequest.setAppVersion(appUtils.getAppVersionName());
        userSummaryRequest.setSource(AppConstants.SOURCE_NAME);
        userSummaryRequest.setType(type);
        userSummaryRequest.setSubType(subType);
        userSummaryRequest.setImageString(imageUrl);
        return userSummaryRequest;
    }

    /**
     * Request for feed api
     */
    public static GetAllDataRequest onBoardingSearchRequestBuilder(String queryName, String masterDataTypeSkill) {
        AppUtils appUtils = AppUtils.getInstance();
        GetAllDataRequest getAllDataRequest = new GetAllDataRequest();
        getAllDataRequest.setAppVersion(appUtils.getAppVersionName());
        getAllDataRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        getAllDataRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        getAllDataRequest.setQ(queryName);
        getAllDataRequest.setMasterDataType(masterDataTypeSkill);
        //TODO:: change rquest data
        getAllDataRequest.setSource(AppConstants.SOURCE_NAME);
        return getAllDataRequest;
    }

    /**
     * Request for feed api
     */
    public FeedRequestPojo searchRequestBuilder(String typeOfFeed, String queryName, int pageNo, String screenName, Long communityId, int pageSize) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(pageSize);
        feedRequestPojo.setSubType(typeOfFeed);
        feedRequestPojo.setQuestion(queryName);
        feedRequestPojo.setCommunityId(communityId);
        feedRequestPojo.setScreenName(screenName);
        return feedRequestPojo;
    }

    public static GetAllDataRequest getAllDataRequestBuilder(String typeOfData, String queryName, String screenName) {
        AppUtils appUtils = AppUtils.getInstance();
        GetAllDataRequest getAllDataRequest = new GetAllDataRequest();
        getAllDataRequest.setAppVersion(appUtils.getAppVersionName());
        getAllDataRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        getAllDataRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        getAllDataRequest.setMasterDataType(typeOfData);
        getAllDataRequest.setQ(queryName);
        getAllDataRequest.setScreenName(screenName);
        getAllDataRequest.setSource(AppConstants.SOURCE_NAME);
        getAllDataRequest.setLastScreenName("string");
        return getAllDataRequest;
    }


    /**
     * Request for feed api
     */
    public LikeRequestPojo likeRequestBuilder(long entityId, int reactionValue) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setEntityId(entityId);
        likeRequestPojo.setReactionValue(reactionValue);
        return likeRequestPojo;
    }

    public LikeRequestPojo likeRequestBuilder(long entityId, int reactionValue, long commentId) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        likeRequestPojo.commentId = commentId;
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setEntityId(entityId);
        likeRequestPojo.setReactionValue(reactionValue);
        return likeRequestPojo;
    }


    /**
     * Request for feed api
     */
    public LikeRequestPojo unLikeRequestBuilder(long entityId) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setEntityId(entityId);
        return likeRequestPojo;
    }

    public LikeRequestPojo unLikeRequestBuilder(long entityId, long commentId) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        likeRequestPojo.commentId = commentId;
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        likeRequestPojo.setEntityId(entityId);
        return likeRequestPojo;
    }

    public static CommentReactionRequestPojo getCommentRequestBuilder(long entityId, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        commentReactionRequestPojo.setPageNo(pageNo);
        //Page size for comment list
        commentReactionRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        commentReactionRequestPojo.setEntityId(entityId);
        return commentReactionRequestPojo;
    }

    public static CommentReactionRequestPojo getCommentRequestBuilder(long entityId, int pageNo, int pageSize) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        commentReactionRequestPojo.setPageNo(pageNo);
        //Page size for comment list
        commentReactionRequestPojo.setPageSize(pageSize);
        commentReactionRequestPojo.setEntityId(entityId);
        return commentReactionRequestPojo;
    }

    public BellNotificationRequest getBellNotificationRequest() {
        AppUtils appUtils = AppUtils.getInstance();

        BellNotificationRequest bellNotificationRequest = new BellNotificationRequest();
        bellNotificationRequest.setAppVersion(appUtils.getAppVersionName());
        bellNotificationRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        bellNotificationRequest.setDeviceUniqueId(appUtils.getDeviceId());
        return bellNotificationRequest;
    }

    public static CommunityPostCreateRequest createCommunityPostRequestBuilder(Long communityId, String createType, String description, List<String> imag, Long mIdForEditPost, LinkRenderResponse linkRenderResponse, boolean hasPermission, String accessToken, boolean isAdmin, Date schedulePostTime) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityPostCreateRequest communityPostCreateRequest = new CommunityPostCreateRequest();
        communityPostCreateRequest.setAppVersion(appUtils.getAppVersionName());
        communityPostCreateRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        communityPostCreateRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityPostCreateRequest.setCommunityId(communityId);
        communityPostCreateRequest.setCreatorType(createType);
        communityPostCreateRequest.setDescription(description);
        communityPostCreateRequest.setImages(imag);
        communityPostCreateRequest.setPostToFacebook(hasPermission);
        communityPostCreateRequest.setUserFbAccessToken(accessToken);
        communityPostCreateRequest.setId(mIdForEditPost);
        communityPostCreateRequest.setSchedulePost(schedulePostTime);
        if (null != linkRenderResponse) {
            communityPostCreateRequest.setOgTitleS(linkRenderResponse.getOgTitleS());
            communityPostCreateRequest.setOgDescriptionS(linkRenderResponse.getOgDescriptionS());
            communityPostCreateRequest.setOgImageUrlS(linkRenderResponse.getOgImageUrlS());
            communityPostCreateRequest.setOgVideoLinkB(linkRenderResponse.isOgVideoLinkB());
            communityPostCreateRequest.setOgRequestedUrlS(linkRenderResponse.getOgRequestedUrlS());
        } else {
            communityPostCreateRequest.setOgTitleS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgDescriptionS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgImageUrlS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgVideoLinkB(false);
            communityPostCreateRequest.setOgRequestedUrlS(AppConstants.EMPTY_STRING);
        }
        return communityPostCreateRequest;
    }


    public static ChallengePostCreateRequest createChallengePostRequestBuilder(String createType, int challengeId, String sourceType, String description, List<String> imag, LinkRenderResponse linkRenderResponse) {
        AppUtils appUtils = AppUtils.getInstance();
        ChallengePostCreateRequest challengePostCreateRequest = new ChallengePostCreateRequest();
        challengePostCreateRequest.setAppVersion(appUtils.getAppVersionName());
        challengePostCreateRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        challengePostCreateRequest.setDeviceUniqueId(appUtils.getDeviceId());
        challengePostCreateRequest.setSourceEntityId(challengeId);
        challengePostCreateRequest.setmChallengeId(challengeId);
        challengePostCreateRequest.setmSourceType(sourceType);
        challengePostCreateRequest.setCommunityId(0L);
        challengePostCreateRequest.setAccepted(true);
        challengePostCreateRequest.setUpdated(true);
        challengePostCreateRequest.setActive(true);
        challengePostCreateRequest.setmCompletionPercent(100);
        challengePostCreateRequest.setCreatorType(createType);
        challengePostCreateRequest.setDescription(description);
        challengePostCreateRequest.setImages(imag);
        if (null != linkRenderResponse) {
            challengePostCreateRequest.setOgTitleS(linkRenderResponse.getOgTitleS());
            challengePostCreateRequest.setOgDescriptionS(linkRenderResponse.getOgDescriptionS());
            challengePostCreateRequest.setOgImageUrlS(linkRenderResponse.getOgImageUrlS());
            challengePostCreateRequest.setOgVideoLinkB(linkRenderResponse.isOgVideoLinkB());
            challengePostCreateRequest.setOgRequestedUrlS(linkRenderResponse.getOgRequestedUrlS());
        } else {
            challengePostCreateRequest.setOgTitleS(AppConstants.EMPTY_STRING);
            challengePostCreateRequest.setOgDescriptionS(AppConstants.EMPTY_STRING);
            challengePostCreateRequest.setOgImageUrlS(AppConstants.EMPTY_STRING);
            challengePostCreateRequest.setOgVideoLinkB(false);
            challengePostCreateRequest.setOgRequestedUrlS(AppConstants.EMPTY_STRING);
        }
        return challengePostCreateRequest;
    }

    public LinkRequest linkRequestBuilder(String linkData) {
        AppUtils appUtils = AppUtils.getInstance();
        LinkRequest linkRequest = new LinkRequest();
        linkRequest.setAppVersion(appUtils.getAppVersionName());
        linkRequest.setSource(AppConstants.SOURCE_NAME);
        linkRequest.setLinkUrl(linkData);
        return linkRequest;
    }

    public static CommunityPostCreateRequest editCommunityPostRequestBuilder(Long communityId, String createType, String description, List<String> imag, Long mIdForEditPost, List<Long> deletedImageId, LinkRenderResponse linkRenderResponse) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityPostCreateRequest communityPostCreateRequest = new CommunityPostCreateRequest();
        communityPostCreateRequest.setAppVersion(appUtils.getAppVersionName());
        communityPostCreateRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        communityPostCreateRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityPostCreateRequest.setCommunityId(communityId);
        communityPostCreateRequest.setCreatorType(createType);
        communityPostCreateRequest.setDescription(description);
        communityPostCreateRequest.setImages(imag);
        communityPostCreateRequest.setId(mIdForEditPost);
        communityPostCreateRequest.setDeleteImagesIds(deletedImageId);
        if (null != linkRenderResponse) {
            communityPostCreateRequest.setOgTitleS(linkRenderResponse.getOgTitleS());
            communityPostCreateRequest.setOgDescriptionS(linkRenderResponse.getOgDescriptionS());
            communityPostCreateRequest.setOgImageUrlS(linkRenderResponse.getOgImageUrlS());
            communityPostCreateRequest.setOgVideoLinkB(linkRenderResponse.isOgVideoLinkB());
            communityPostCreateRequest.setOgRequestedUrlS(linkRenderResponse.getOgRequestedUrlS());
        } else {
            communityPostCreateRequest.setOgTitleS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgDescriptionS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgImageUrlS(AppConstants.EMPTY_STRING);
            communityPostCreateRequest.setOgVideoLinkB(false);
            communityPostCreateRequest.setOgRequestedUrlS(AppConstants.EMPTY_STRING);
        }
        return communityPostCreateRequest;
    }

    public static CommunityTopPostRequest topCommunityPostRequestBuilder(Long communityId, String createType, String description, long id, boolean topPost) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityTopPostRequest communityTopPostRequest = new CommunityTopPostRequest();
        communityTopPostRequest.setAppVersion(appUtils.getAppVersionName());
        communityTopPostRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        communityTopPostRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityTopPostRequest.setCommunityId(communityId);
        communityTopPostRequest.setCreatorType(createType);
        communityTopPostRequest.setDescription(description);
        communityTopPostRequest.setTopPost(topPost);
        communityTopPostRequest.setId(id);
        return communityTopPostRequest;
    }

    public ApproveSpamPostRequest spamPostApprovedRequestBuilder(FeedDetail feedDetail, boolean isActive, boolean isSpam, boolean isApproved) {
        AppUtils appUtils = AppUtils.getInstance();
        ApproveSpamPostRequest approveSpamPostRequest = new ApproveSpamPostRequest();
        approveSpamPostRequest.setAppVersion(appUtils.getAppVersionName());
        approveSpamPostRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        approveSpamPostRequest.setDeviceUniqueId(appUtils.getDeviceId());
        approveSpamPostRequest.setApproved(isApproved);
        approveSpamPostRequest.setId(feedDetail.getIdOfEntityOrParticipant());
        approveSpamPostRequest.setActive(isActive);
        approveSpamPostRequest.setSpam(isSpam);
        return approveSpamPostRequest;
    }

    public SelectCommunityRequest selectCommunityRequestBuilder() {
        AppUtils appUtils = AppUtils.getInstance();
        SelectCommunityRequest selectCommunityRequest = new SelectCommunityRequest();
        selectCommunityRequest.setAppVersion(appUtils.getAppVersionName());
        selectCommunityRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        selectCommunityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        selectCommunityRequest.setMasterDataType(AppConstants.JOB_AT_GET_ALL_DATA_KEY);
        return selectCommunityRequest;
    }

    public BookmarkRequestPojo bookMarkRequestBuilder(long entityId) {
        AppUtils appUtils = AppUtils.getInstance();
        BookmarkRequestPojo bookmarkRequestPojo = new BookmarkRequestPojo();
        bookmarkRequestPojo.setAppVersion(appUtils.getAppVersionName());
        bookmarkRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        bookmarkRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        bookmarkRequestPojo.setEntityId(entityId);
        return bookmarkRequestPojo;
    }

    public static CommentReactionRequestPojo postCommentRequestBuilder(long entityId, String userComment, boolean isAnonymous) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        commentReactionRequestPojo.setUserComment(userComment);
        commentReactionRequestPojo.setIsAnonymous(isAnonymous);
        commentReactionRequestPojo.setEntityId(entityId);
        return commentReactionRequestPojo;
    }

    public static CommentReactionRequestPojo editCommentRequestBuilder(long entityId, String userComment, boolean isAnonymous, boolean isActive, long participationId) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(appUtils.getCloudMessaging());
        commentReactionRequestPojo.setUserComment(userComment);
        commentReactionRequestPojo.setIsAnonymous(isAnonymous);
        commentReactionRequestPojo.setIsActive(isActive);
        commentReactionRequestPojo.setEntityId(entityId);
        commentReactionRequestPojo.setParticipationId(participationId);
        return commentReactionRequestPojo;
    }


    public static CommunityRequest communityRequestBuilder(List<Long> userId, long idOfEntityParticipant, String reasonToJoin) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityRequest communityRequest = new CommunityRequest();
        communityRequest.setUserId(userId);
        communityRequest.setCommunityId(idOfEntityParticipant);
        communityRequest.setAppVersion(appUtils.getAppVersionName());
        communityRequest.setCloudMessagingId(appUtils.getCloudMessaging());
        communityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityRequest.setLastScreenName(AppConstants.COMMUNITY_DETAIL);
        communityRequest.setScreenName(AppConstants.COMMUNITY_DETAIL);
        communityRequest.setReasonToJoin(reasonToJoin);
        return communityRequest;
    }


    public static HelplinePostQuestionRequest helplineQuestionBuilder(String chatQueryText) {
        HelplinePostQuestionRequest helplinePostQuestionRequest = new HelplinePostQuestionRequest();
        helplinePostQuestionRequest.setQuestion(chatQueryText);
        helplinePostQuestionRequest.setSource(AppConstants.SOURCE_NAME);
        return helplinePostQuestionRequest;
    }

    public static HelplineGetChatThreadRequest helplineGetChatThreadRequestBuilder(int pageNo) {
        HelplineGetChatThreadRequest helplineGetChatThreadRequest = new HelplineGetChatThreadRequest();
        helplineGetChatThreadRequest.setPageNo(pageNo);
        helplineGetChatThreadRequest.setPageSize(AppConstants.PAGE_SIZE_CHAT);
        return helplineGetChatThreadRequest;
    }

    public static FAQSRequest sheFAQSRequestBuilder() {
        FAQSRequest faqsRequest = new FAQSRequest();
        return faqsRequest;
    }

    public static ICCMemberRequest sheICCMemberListRequestBuilder() {
        ICCMemberRequest iccMemberRequest = new ICCMemberRequest();
        return iccMemberRequest;
    }

    public static void openChromeTab(Activity activity, Uri url) {
        CustomTabsIntent customTabsIntent =
                new CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .setShowTitle(true)
                        .enableUrlBarHiding()
                        .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            customTabsIntent.intent.putExtra(Intent.EXTRA_REFERRER,
                    Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + activity.getPackageName()));
        }
        customTabsIntent.launchUrl(activity, url);
    }

    public static void openChromeTabForce(Activity activity, Uri url) {
        String PACKAGE_NAME = "com.android.chrome";
        CustomTabsIntent customTabsIntent =
                new CustomTabsIntent.Builder()
                        .setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
                        .setShowTitle(true)
                        .enableUrlBarHiding()
                        .build();

        PackageManager packageManager = activity.getPackageManager();
        List<ApplicationInfo> resolveInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo applicationInfo : resolveInfoList) {
            String packageName = applicationInfo.packageName;
            if (TextUtils.equals(packageName, PACKAGE_NAME)) {
                customTabsIntent.intent.setPackage(PACKAGE_NAME);
                break;
            }
        }
        customTabsIntent.launchUrl(activity, url);
    }

    public static boolean matchesWebsiteURLPattern(String sentence) {
        final Pattern pattern = Pattern.compile("\\b(https?|Https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                Pattern.MULTILINE | Pattern.DOTALL);
        Matcher m = pattern.matcher(sentence);
        return m.find();
    }
}
