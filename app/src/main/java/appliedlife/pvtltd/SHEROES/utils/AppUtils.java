package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
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
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.bookmark.BookmarkRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.ProfileItems;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeRequestPojo;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginRequest;
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

    /**
     * This method shows the softkeyboard
     *
     * @param view
     */
    public static void showKeyboard(View view, String TAG) {
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
     * Return device information string which contains Device id, Phone type, Network operator name,
     * network type, device manufacturer, device model and device's SDK version
     *
     * @return device information
     */
    public String getDeviceInformation() {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        String deviceInfo = "";

        TelephonyManager telephonyManager = (TelephonyManager) SheroesApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null) {
            deviceInfo += "DeviceID : " + telephonyManager.getDeviceId() + "\n\r";
            deviceInfo += "PhoneType : " + telephonyManager.getPhoneType() + "\n\r";
            deviceInfo += "NetworkOperatorName : " + telephonyManager.getNetworkOperatorName() + "\n\r";
            deviceInfo += "NetworkType : " + telephonyManager.getNetworkType() + "\n\r";
            deviceInfo += "SimOperatorName : " + telephonyManager.getSimOperatorName() + "\n\r";
        }

        deviceInfo += "Manufacturer : " + Build.MANUFACTURER + "\n\r";
        deviceInfo += "Model : " + Build.MODEL + "\n\r";
        deviceInfo += "SDK Version : " + Build.VERSION.SDK_INT + "\n\r";

        LogUtils.exit(TAG, LogUtils.getMethodName());
        return deviceInfo;
    }

    public String getNetworkOperatorName() {
        TelephonyManager telephonyManager = ((TelephonyManager) SheroesApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE));
        String operatorName = telephonyManager.getNetworkOperatorName();
        return operatorName;
    }

    public String getNetworkType() {
        TelephonyManager telephonyManager = ((TelephonyManager) SheroesApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE));
        String networkTtype = "" + telephonyManager.getNetworkType();
        return networkTtype;
    }

    /**
     * Passed data will be stored in the clipboard which is similar to copy function
     *
     * @param text data to stored on the clipboard
     */
    public boolean writeToClipboard(String text) {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        try {
            ClipboardManager clipboardManager = (ClipboardManager) SheroesApplication.mContext.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(text, text);
            clipboardManager.setPrimaryClip(clipData);
        } catch (Exception ex) {
            LogUtils.error(TAG, "Error : " + ex, ex);
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return true;
    }

    /**
     * Read data from the clipboard which was stored using writeToClipboard function.
     *
     * @return Data stored in the clipboard
     */
    public String readFromClipboard() {
        LogUtils.enter(TAG, LogUtils.getMethodName());
        String text = null;
        ClipboardManager clipboardManager = (ClipboardManager) SheroesApplication.mContext.getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboardManager.hasPrimaryClip()) {
            ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
            // Gets the clipboard as text.
            if (item != null && item.getText() != null) {
                text = item.getText().toString();
            }
        }
        LogUtils.exit(TAG, LogUtils.getMethodName());
        return text;
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
    public <T> T parseUsingGSONFromJSON(String is, String classPath) {
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

    public String getPhoneNumber() {
        TelephonyManager mTelephonyMgr = (TelephonyManager) SheroesApplication.mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyMgr != null) {
            return mTelephonyMgr.getLine1Number();
        }
        return "";
    }

    public boolean isRunningOnEmulator() {

        final TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        final boolean hasEmulatorImei = telephonyManager != null && telephonyManager.getDeviceId() != null ? telephonyManager.getDeviceId().equals("000000000000000") : true;
        final boolean hasEmulatorModelName = Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK");
        //final boolean hasInvalidDeviceId = StringUtil.isNotNullOrEmptyString(getDeviceId());
        return hasEmulatorImei || hasEmulatorModelName;
    }


    public void scheduleAlarmWithIntent(Intent intent, int timeInMinutes) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(AppUtils.getInstance().getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) AppUtils.getInstance().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            Calendar c = Calendar.getInstance();
            if (timeInMinutes > 0) {
                c.add(Calendar.MINUTE, timeInMinutes);
            } else {
                c.add(Calendar.SECOND, 5); // atleast 5 seconds after if time is 0
            }
            alarmManager.cancel(pendingIntent);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            }
        } catch (Exception ex) {
            LogUtils.error(TAG, ex.toString(), ex);
        }
    }


    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ip = inetAddress.getHostAddress();
                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            LogUtils.error(TAG, "SocketException in getLocalIpAddress is " + e.getMessage(), e);
        } catch (Exception e) {
            LogUtils.error(TAG, "Exception in getLocalIpAddress is " + e.getMessage(), e);
        }
        return "";
    }


    public String getAppInstallDate() {
        try {
            long installedTIme = AppUtils.getInstance().getApplicationContext().getPackageManager().getPackageInfo("com.makemytrip", 0).firstInstallTime;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(installedTIme);
            String installDate = dateFormat.format(date);
            return installDate;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.error(TAG, "PackageManager.NameNotFoundException in getAppInstallDate is " + e.getMessage(), e);
        } catch (Exception e) {
            LogUtils.error(TAG, "Exception in getAppInstallDate is " + e.getMessage(), e);
        }
        return "";
    }


    public String getCurrentInternetConnectionAndApproxSpeed() {
        try {
            EncryptionUtils.initialize();
            NetworkInfo info = SHEROESTracker.SHEROESSystemInfo.getNetworkInfo(getApplicationContext());
            return ((info != null && info.isConnected()) ? getConnectionTypeAndApproxSpeed(info.getType(), info.getSubtype()) : "");
        } catch (Exception e) {
            LogUtils.error(TAG, e);
            return "";
        }
    }

    public int getTotalMemoryDeviceinGB() {
        try {
            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            float blockSize = stat.getBlockSize();
            float totalBlocks = stat.getBlockCount();
            LogUtils.info(TAG, totalBlocks + "");
            float memory = (totalBlocks * blockSize) / AppConstants.ONEGIGABYTE;
            return Math.round(memory);
        } catch (Exception e) {
            LogUtils.error(TAG, "error while device internal  memory calculation", e);
        }
        return 0;
    }

    /**
     * @return - connectivity type only if it is wifi or mobile other wise min integer value
     */
    public int getConnectivityType() {
        try {
            NetworkInfo info = SHEROESTracker.SHEROESSystemInfo.getNetworkInfo(SheroesApplication.mContext);
            if (info != null && info.isConnected()) {
                return info.getType();
            }
            return Integer.MIN_VALUE;
        } catch (Exception e) {
            LogUtils.error(TAG, e);
            return Integer.MIN_VALUE;
        }

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
                        if (expDate.before(curDate)) {
                            return true;
                        } else {
                            return false;
                        }
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


    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView list view whose height need to set
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {
        try {
            if (listView != null) {
                ListAdapter listAdapter = listView.getAdapter();
                // Get padding height
                int paddingBottom = listView.getPaddingBottom();
                int paddingTop = listView.getPaddingTop();
                if (listAdapter != null) {
                    int numberOfItems = listAdapter.getCount();
                    // Get total height of all items.
                    int totalItemsHeight = 0;
                    for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                        View item = listAdapter.getView(itemPos, null, listView);
                        if (item != null) {
                            item.measure(0, 0);
                            totalItemsHeight += item.getMeasuredHeight();
                        }
                    }
                    // Get total height of all item dividers.
                    int totalDividersHeight = listView.getDividerHeight() *
                            (numberOfItems - 1);
                    // Set list height.
                    ViewGroup.LayoutParams params = listView.getLayoutParams();
                    params.height = totalItemsHeight + totalDividersHeight + paddingBottom + paddingTop;
                    listView.setLayoutParams(params);
                    listView.requestLayout();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }
        return false;
    }

    /* Function to check if fragment UI is active*/
    public static boolean isFragmentUIActive(Fragment frag) {
        return frag != null && frag.getActivity() != null && frag.isAdded() && !frag.isDetached() && !frag.isRemoving();
    }

    /* Function to check if support fragment UI is active*/
    public static boolean isFragmentUIActive(android.support.v4.app.Fragment frag) {
        return frag != null && frag.getActivity() != null && frag.isAdded() && !frag.isDetached() && !frag.isRemoving();
    }

    public static void setClippingEnabled(View v, boolean enabled) {
        try {
            while (v.getParent() != null && v.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v.getParent();
                viewGroup.setClipChildren(enabled);
                viewGroup.setClipToPadding(enabled);
                v = viewGroup;
            }
        } catch (Exception e) {
            LogUtils.error(TAG, "error while toggling cliping  A change in viewheirarchy may be", e);
        }

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

    public String getDaysSinceInstall() {
        long noOfdays = 0;
        try {
            long installedDate = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).firstInstallTime;
            long currentTime = System.currentTimeMillis();
            long diff = currentTime - installedDate;
            noOfdays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        } catch (Exception ex) {
            LogUtils.error(TAG, "Error : " + ex, ex);
            return " ";
        }
        return String.valueOf(noOfdays);
    }

    public long getInstallDateInGMT() {
        long installedDate = 0;
        try {
            long installedDateMilli = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).firstInstallTime;
            Date date = new Date(installedDateMilli);
            DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
            formatter.setTimeZone(TimeZone.getTimeZone("IST"));
            formatter.format(date);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            String convertedString = formatter.format(date);
            installedDate = formatter.parse(convertedString).getTime();
        } catch (Exception e) {
            LogUtils.error(TAG, "Error : " + e, e);
            installedDate = 0;
        }
        return installedDate;
    }


    public boolean isTablet() {
        try {
            return (getApplicationContext().getResources().getConfiguration().screenLayout
                    & Configuration.SCREENLAYOUT_SIZE_MASK)
                    >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } catch (Exception e) {
            LogUtils.error(TAG, "Error : " + e, e);
            return false;
        }
    }

    /**
     * Method to get the current android OS version
     *
     * @return String
     */
    public String getOSVersion() {
        String lReleaseVersion = android.os.Build.VERSION.RELEASE;
        return lReleaseVersion;
    }

    /**
     * This method put a line on edit text.
     *
     * @param etArea
     * @param color
     */
    public static void setEditTextBackgroundLineColor(EditText etArea, int color) {
        if (null != etArea && null != etArea.getBackground()) {
            etArea.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    /**
     * This method put a line on edit text.
     *
     * @param progressBar
     * @param color
     */
    public static void setProgressDrawableBackgroundColor(ProgressBar progressBar, int color) {
        if (null != progressBar && null != progressBar.getIndeterminateDrawable()) {
            progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }


    /**
     * checks if activity is finished and destroyed
     *
     * @param isDestroyed
     * @return
     */
    public static boolean isActivityExists(boolean isDestroyed, Activity pActivity) {
        return !pActivity.isFinishing() && !isDestroyed;
    }


    /**
     * Disable the long press on the given edit text
     *
     * @param editText edit text on which long press to disable
     */
    public static void disableLongPressOnEditText(EditText editText) {
        editText.setLongClickable(false);
        editText.setTextIsSelectable(false);
    }
    public static LoginRequest loginRequestBuilder() {
        LoginRequest loginRequest = new LoginRequest();
        AppUtils appUtils = AppUtils.getInstance();
        loginRequest.setAdvertisementid("string");
        loginRequest.setDeviceid(appUtils.getDeviceId());
        loginRequest.setDevicetype(appUtils.getDeviceModel());
        loginRequest.setGcmorapnsid("string");
        return loginRequest;
    }
    /**
     * Request for feed api
     */
    public static FeedRequestPojo feedRequestBuilder(String typeOfFeed, int pageNo) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        return feedRequestPojo;
    }

    public static FeedRequestPojo articleCategoryRequestBuilder(String typeOfFeed, int pageNo, List<Long> categoryIds) {
        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setCategoryIds(categoryIds);
        return feedRequestPojo;
    }

    public static FeedRequestPojo feedDetailRequestBuilder(String typeOfFeed, int pageNo, String idForDetail) {

        FeedRequestPojo feedRequestPojo = makeFeedRequest(typeOfFeed, pageNo);
        feedRequestPojo.setIdForFeedDetail(idForDetail);
        return feedRequestPojo;
    }

    private static FeedRequestPojo makeFeedRequest(String typeOfFeed, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        feedRequestPojo.setSubType(typeOfFeed);
        return feedRequestPojo;
    }

    public static FeedRequestPojo getBookMarks(int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        return feedRequestPojo;
    }
    /**
     * Request for feed api
     */
    public static GetAllDataRequest onBoardingSearchRequestBuilder(String queryName,  String masterDataTypeSkill) {
        AppUtils appUtils = AppUtils.getInstance();
        GetAllDataRequest getAllDataRequest = new GetAllDataRequest();
        getAllDataRequest.setAppVersion(appUtils.getAppVersionName());
        getAllDataRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        getAllDataRequest.setCloudMessagingId(AppConstants.ALL_SEARCH);
        getAllDataRequest.setQ(queryName);
        getAllDataRequest.setMasterDataType(masterDataTypeSkill);
        //TODO:: change rquest data
        getAllDataRequest.setSource(AppConstants.BOARDING_SEARCH);
        return getAllDataRequest;
    }
    /**
     * Request for feed api
     */
    public static FeedRequestPojo searchRequestBuilder(String typeOfFeed, String queryName, int pageNo, String screenName) {
        AppUtils appUtils = AppUtils.getInstance();
        FeedRequestPojo feedRequestPojo = new FeedRequestPojo();
        feedRequestPojo.setAppVersion(appUtils.getAppVersionName());
        feedRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        feedRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
        feedRequestPojo.setPageNo(pageNo);
        feedRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        feedRequestPojo.setSubType(typeOfFeed);
        feedRequestPojo.setQuestion(queryName);
        feedRequestPojo.setScreenName(screenName);
        return feedRequestPojo;
    }
    public static GetAllDataRequest getAllDataRequestBuilder(String typeOfData, String queryName, String screenName) {
        AppUtils appUtils = AppUtils.getInstance();
        GetAllDataRequest getAllDataRequest = new GetAllDataRequest();
        getAllDataRequest.setAppVersion(appUtils.getAppVersionName());
        getAllDataRequest.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        getAllDataRequest.setCloudMessagingId(AppConstants.ALL_SEARCH);
        getAllDataRequest.setMasterDataType(typeOfData);
        getAllDataRequest.setQ(queryName);
        getAllDataRequest.setScreenName(screenName);
        getAllDataRequest.setSource("string");
        getAllDataRequest.setLastScreenName("string");
        return getAllDataRequest;
    }


    /**
     * Request for feed api
     */
    public static LikeRequestPojo likeRequestBuilder(long entityId, int reactionValue) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
        likeRequestPojo.setEntityId(entityId);
        likeRequestPojo.setReactionValue(reactionValue);
        return likeRequestPojo;
    }

    /**
     * Request for feed api
     */
    public static LikeRequestPojo unLikeRequestBuilder(long entityId) {
        AppUtils appUtils = AppUtils.getInstance();
        LikeRequestPojo likeRequestPojo = new LikeRequestPojo();
        likeRequestPojo.setAppVersion(appUtils.getAppVersionName());
        likeRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        likeRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
        likeRequestPojo.setEntityId(entityId);
        return likeRequestPojo;
    }

    public static CommentReactionRequestPojo getCommentRequestBuilder(long entityId, int pageNo) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
        commentReactionRequestPojo.setPageNo(pageNo);
        //Page size for comment list
        commentReactionRequestPojo.setPageSize(AppConstants.PAGE_SIZE);
        commentReactionRequestPojo.setEntityId(entityId);
        return commentReactionRequestPojo;
    }

    public static BookmarkRequestPojo bookMarkRequestBuilder(long entityId) {
        AppUtils appUtils = AppUtils.getInstance();
        BookmarkRequestPojo bookmarkRequestPojo = new BookmarkRequestPojo();
        bookmarkRequestPojo.setAppVersion(appUtils.getAppVersionName());
        bookmarkRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        bookmarkRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
        bookmarkRequestPojo.setEntityId(entityId);
        return bookmarkRequestPojo;
    }

    public static CommentReactionRequestPojo postCommentRequestBuilder(long entityId, String userComment, boolean isAnonymous) {
        AppUtils appUtils = AppUtils.getInstance();
        CommentReactionRequestPojo commentReactionRequestPojo = new CommentReactionRequestPojo();
        commentReactionRequestPojo.setAppVersion(appUtils.getAppVersionName());
        commentReactionRequestPojo.setDeviceUniqueId(appUtils.getDeviceId());
        //TODO:: change rquest data
        commentReactionRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
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
        commentReactionRequestPojo.setCloudMessagingId(AppConstants.ALL_SEARCH);
        commentReactionRequestPojo.setUserComment(userComment);
        commentReactionRequestPojo.setIsAnonymous(isAnonymous);
        commentReactionRequestPojo.setIsActive(isActive);
        commentReactionRequestPojo.setEntityId(entityId);
        commentReactionRequestPojo.setParticipationId(participationId);
        return commentReactionRequestPojo;
    }


    public static CommunityRequest communityRequestBuilder(List<Long> userId, long idOfEntityParticipant) {
        AppUtils appUtils = AppUtils.getInstance();
        CommunityRequest communityRequest = new CommunityRequest();
        communityRequest.setUserId(userId);
        communityRequest.setCommunityId(idOfEntityParticipant);
        communityRequest.setAppVersion(appUtils.getAppVersionName());
        communityRequest.setCloudMessagingId(AppConstants.ALL_SEARCH);
        communityRequest.setDeviceUniqueId(appUtils.getDeviceId());
        communityRequest.setLastScreenName(AppConstants.COMMUNITY_DETAIL);
        communityRequest.setScreenName(AppConstants.ALL_SEARCH);
        return communityRequest;
    }

    /**
     * Profile data
     */
    public static List<ProfileItems> profileDetail() {
        List<ProfileItems> profileItemsList = new ArrayList<>();
        ProfileItems profileItems = new ProfileItems();
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        profileItemsList.add(profileItems);
        return profileItemsList;
    }

}
