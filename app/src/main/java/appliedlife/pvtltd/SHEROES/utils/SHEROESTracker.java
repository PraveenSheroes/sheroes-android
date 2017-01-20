package appliedlife.pvtltd.SHEROES.utils;


import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import appliedlife.pvtltd.SHEROES.R;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.capitalize;

public class SHEROESTracker {


    public static class SHEROESSystemInfo {
        private String userAgent;
        private String clientIP;
        private String screenSize;
        private String deviceManufacturer;
        private String mobileOperatingSystemVersion;
        private String mobileOperatingSystem;
        private String deviceName;
        private String deviceModel;

        private String tag = "Production";
        private String trafficType = "Direct";
        private String mobileAppVersion;

        /*
         * Dynamic Properties
         */
        private String deviceToken;
        private String emailID;
        private String currentInternetConnection;
        private String deviceId;

        private String loggedinstatus;
        private static SHEROESSystemInfo instance = new SHEROESSystemInfo(AppUtils.getInstance().getApplicationContext());

        public static SHEROESSystemInfo getInstance() {
            return instance != null ? instance : new SHEROESSystemInfo(AppUtils.getInstance().getApplicationContext());
        }

        public static String getConnectionType(Context context) {
            NetworkInfo info = getNetworkInfo(context);
            return ((info != null && info.isConnected()) ? getConnectionType(info.getType(), info.getSubtype()) : "");
        }

        public static NetworkInfo getNetworkInfo(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        }

        private SHEROESSystemInfo(Context pContext) {
            try {
                userAgent = "android_"
                        + pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {

                e.printStackTrace();
            }
            clientIP = "null";

            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            Display display = ((WindowManager) pContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            display.getMetrics(mDisplayMetrics);

            screenSize = mDisplayMetrics.widthPixels + "x" + mDisplayMetrics.heightPixels;
            deviceManufacturer = Build.BRAND == null ? "UNKNOWN" : Build.BRAND;
            mobileOperatingSystemVersion = Build.VERSION.RELEASE == null ? "UNKNOWN" : Build.VERSION.RELEASE;
            mobileOperatingSystem = "Android";// check
            deviceName = makeDeviceName();
            boolean tabletSize = false;
            if (pContext != null && pContext.getResources() != null) {
                tabletSize = pContext.getResources().getBoolean(R.bool.isTablet);
            }
            if (tabletSize) {
                deviceModel = "Tablet";
            } else {
                deviceModel = "Mobile";
            }


        }

        private String makeDeviceName() {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.startsWith(manufacturer)) {
                return capitalize(model);
            } else {
                return capitalize(manufacturer) + " " + model;
            }
        }

        /**
         * Check if the connection is fast
         *
         * @param type
         * @param subType
         * @return
         */
        public static String getConnectionType(int type, int subType) {
            if (type == ConnectivityManager.TYPE_WIFI) {
                return "WiFi";
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                switch (subType) {
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        return "1xRTT"; // ~ 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        return "CDMA"; // ~ 14-64 kbps
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return "EDGE"; // ~ 50-100 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        return "EVDO_0"; // ~ 400-1000 kbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        return "EVDO_A"; // ~ 600-1400 kbps
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        return "GPRS"; // ~ 100 kbps
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        return "HSDPA"; // ~ 2-14 Mbps
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        return "HSPA"; // ~ 700-1700 kbps
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        return "HSUPA"; // ~ 1-23 Mbps
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        return "UMTS"; // ~ 400-7000 kbps
                    /*
					 * Above API level 7, make sure to set
					 * android:targetSdkVersion to appropriate level to use
					 * these
					 */
                    case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                        return "EHRPD"; // ~ 1-2 Mbps
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                        return "EVDO_B"; // ~ 5 Mbps
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                        return "HSPAP"; // ~ 10-20 Mbps
                    case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                        return "IDEN"; // ~25 kbps
                    case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                        return "LTE"; // ~ 10+ Mbps
                    // Unknown
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    default:
                        return "UNKNOWN";
                }
            } else {
                return "Not Connected";
            }
        }

    }
}
