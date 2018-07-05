package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.appsflyer.AppsFlyerLib;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.f2prateek.rx.preferences2.Preference;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

/**
 * Created by ravi on 21/06/18.
 * Helper class for cleverTap analytics used for setup cleverTap profile, screen and event track
 */

public class CleverTapHelper {

    //region private variable
    private static CleverTapHelper mCleverTapHelper;
    //endregion

    //region private constants
    private static final String SCREEN_OPEN = "Screen open";
    private static final String NAME = "Name";
    private static final String PHOTO = "Photo";
    private static final String DATE_OF_BIRTH = "DOB";
    private static final String IDENTITY = "Identity";
    private static final String GENDER = "Gender";
    private static final String EMAIL = "Email";
    private static final String PHONE = "Phone";
    private static final String LOCATION = "Location";
    //endregion

    // region inject variables
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<Configuration> mConfiguration;
    //endregion

    //region public methods
    public static CleverTapHelper getInstance() {
        if (mCleverTapHelper == null) {
            mCleverTapHelper = new CleverTapHelper();
        }
        return mCleverTapHelper;
    }

    /**
     * Should be run on login or app open
     *
     * @param context context
     */
     void setupCleverTap(Context context) {
        SheroesApplication.getAppComponent(context).inject(this);
        getCleverTapInstance(context);
    }

     private static CleverTapAPI getCleverTapInstance(Context context) {
        CleverTapAPI cleverTapAPI;
        try {
            cleverTapAPI = CleverTapAPI.getInstance(context);
        } catch (CleverTapMetaDataNotFoundException e) {
            cleverTapAPI = null;
        } catch (CleverTapPermissionsNotSatisfied e) {
            cleverTapAPI = null;
        }
        return cleverTapAPI;
    }

     void setupUser(Context context, boolean isNewUser) {

        UserSummary userSummary = null;
        UserBO userBio;
        if (mUserPreference == null) {
            return;
        }
        if (mUserPreference.isSet() && mUserPreference.get().getUserSummary() != null) {
            userSummary = mUserPreference.get().getUserSummary();
        }
        if (userSummary == null) {
            return;
        }

        userBio = userSummary.getUserBO();

        CleverTapAPI cleverTapAPI = getCleverTapInstance(context);

        if (cleverTapAPI != null) {

            Map<String, Object> profileUpdate = new HashMap<>();

            if (!TextUtils.isEmpty(userSummary.getFirstName())) {
                profileUpdate.put(NAME, userSummary.getFirstName() + " " + userSummary.getLastName());
            }

            if (!TextUtils.isEmpty(userSummary.getPhotoUrl())) {
                profileUpdate.put(PHOTO, userSummary.getPhotoUrl());
            }

            if (userBio != null && !TextUtils.isEmpty(userBio.getDob())) {
                profileUpdate.put(DATE_OF_BIRTH, userBio.getDob());
            }

            profileUpdate.put(IDENTITY, userSummary.getUserId());

            if (userBio != null && !TextUtils.isEmpty(userBio.getGender())) {
                profileUpdate.put(GENDER, userSummary.getUserBO().getGender());
            }

            if (!TextUtils.isEmpty(userSummary.getEmailId())) {
                profileUpdate.put(EMAIL, userSummary.getEmailId());
            }

            if (!TextUtils.isEmpty(userSummary.getMobile())) {
                profileUpdate.put(PHONE, "+91" + userSummary.getMobile());
            }

            if (userBio != null && !TextUtils.isEmpty(userBio.getCityMaster())) {
                profileUpdate.put(LOCATION, userSummary.getUserBO().getCityMaster());
            }

            //requires Location Permission in AndroidManifest e.g. "android.permission.ACCESS_COARSE_LOCATION"
            Location location = cleverTapAPI.getLocation();
            cleverTapAPI.setLocation(location);

            profileUpdate.put("MSG-sms", true);                         // Enable email notifications
            profileUpdate.put("MSG-push", true);                        // Enable push notifications
            profileUpdate.put("MSG-sms", true);                        // Enable SMS notifications

            profileUpdate = getSuperProperties(context, profileUpdate);

            if(isNewUser) {
                cleverTapAPI.onUserLogin(profileUpdate);
            } else {
                cleverTapAPI.profile.push(profileUpdate);
            }
        }
    }

    //Track screen
    static void trackScreen(Context sAppContext, Map<String, Object> properties) {
        properties = getInstance().addExtraPropertiesToEvent(sAppContext, properties);
        if (properties != null && getCleverTapInstance(sAppContext) != null)
            getCleverTapInstance(sAppContext).event.push(CleverTapHelper.SCREEN_OPEN, properties);
    }

    //Track event
     static void trackEvent(Context sAppContext, Event event, Map<String, Object> properties) {
        properties = mCleverTapHelper.addExtraPropertiesToEvent(sAppContext, properties);
        if (properties != null && getCleverTapInstance(sAppContext) != null)
            getCleverTapInstance(sAppContext).event.push(event.getFullName(), properties);
    }
    //endregion

    //region private method
    private Map<String, Object> addExtraPropertiesToEvent(Context context, Map<String, Object> properties) {
        if(properties == null) {
            properties = new HashMap<>();
        }
        properties = getSuperProperties(context, properties);
        properties = getSystemProperties(context, properties);
        return properties;
    }

    //Add super properties with each event in cleverTap
    private Map<String, Object> getSuperProperties(Context context, Map<String, Object> properties) {

        UserSummary userSummary = null;
        if (mUserPreference == null) {
            SheroesApplication.getAppComponent(context).inject(this);
        }

        if (mUserPreference != null && mUserPreference.isSet() && mUserPreference.get().getUserSummary() != null) {
            userSummary = mUserPreference.get().getUserSummary();
        }

        if (userSummary != null) {

            if (properties == null) {
                properties = new HashMap<>();
            }
            String setOrderKey = CommonUtil.getPref(AppConstants.SET_ORDER_KEY);
            String feedConfigVersion = CommonUtil.getPref(AppConstants.FEED_CONFIG_VERSION);

            properties.put(SuperProperty.USER_ID.getString(), Long.toString(userSummary.getUserId()));
            properties.put(SuperProperty.USER_NAME.getString(), userSummary.getFirstName() + " " + userSummary.getLastName());
            properties.put(SuperProperty.DATE_OF_BIRTH.getString(), userSummary.getUserBO().getDob());
            properties.put(SuperProperty.CREATED_DATE.getString(), userSummary.getUserBO().getCrdt());
            properties.put(SuperProperty.FEED_CONFIG_VERSION.getString(), feedConfigVersion);
            properties.put(SuperProperty.MOBILE_NUMBER.getString(), userSummary.getMobile());
            properties.put(SuperProperty.SET_ORDER_KEY.getString(), setOrderKey);
            properties.put(SuperProperty.APPSFLYER_ID.getString(), AppsFlyerLib.getInstance().getAppsFlyerUID(context));
            properties.put(SuperProperty.CONFIG_TYPE.getString(), mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configType != null ? mConfiguration.get().configType : "");
            properties.put(SuperProperty.CONFIG_VERSION.getString(), mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configVersion != null ? mConfiguration.get().configVersion : "");
            properties.put(SuperProperty.EMAIL_ID.getString(), userSummary.getEmailId());

            return properties;
        }
        return null;
    }

    //Add additional properties like os, build version etc
    private Map<String, Object> getSystemProperties(Context context, Map<String, Object> properties) {

        if (properties == null) {
            properties = new HashMap<>();
        }

        properties.put("$os", "Android");
        properties.put("$os_version", Build.VERSION.RELEASE == null ? "UNKNOWN" : Build.VERSION.RELEASE);

        properties.put("$manufacturer", Build.MANUFACTURER == null ? "UNKNOWN" : Build.MANUFACTURER);
        properties.put("$brand", Build.BRAND == null ? "UNKNOWN" : Build.BRAND);
        properties.put("$model", Build.MODEL == null ? "UNKNOWN" : Build.MODEL);

        try {
            try {
                final int servicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
                switch (servicesAvailable) {
                    case ConnectionResult.SUCCESS:
                        properties.put("$google_play_services", "available");
                        break;
                    case ConnectionResult.SERVICE_MISSING:
                        properties.put("$google_play_services", "missing");
                        break;
                    case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                        properties.put("$google_play_services", "out of date");
                        break;
                    case ConnectionResult.SERVICE_DISABLED:
                        properties.put("$google_play_services", "disabled");
                        break;
                    case ConnectionResult.SERVICE_INVALID:
                        properties.put("$google_play_services", "invalid");
                        break;
                }
            } catch (RuntimeException e) {
                // Turns out even checking for the service will cause explosions
                // unless we've set up meta-data
                properties.put("$google_play_services", "not configured");
            }

        } catch (NoClassDefFoundError e) {
            properties.put("$google_play_services", "not included");
        }

        SystemInformation mSystemInformation = SystemInformation.getInstance(context);
        final DisplayMetrics displayMetrics = mSystemInformation.getDisplayMetrics();
        properties.put("$screen_dpi", displayMetrics.densityDpi);
        properties.put("$screen_height", displayMetrics.heightPixels);
        properties.put("$screen_width", displayMetrics.widthPixels);

        final String applicationVersionName = mSystemInformation.getAppVersionName();
        if (null != applicationVersionName) {
            properties.put("$app_version", applicationVersionName);
            properties.put("$app_version_string", applicationVersionName);
        }

        final Integer applicationVersionCode = mSystemInformation.getAppVersionCode();
        if (null != applicationVersionCode) {
            properties.put("$app_release", applicationVersionCode);
            properties.put("$app_build_number", applicationVersionCode);
        }

        final Boolean hasNFC = mSystemInformation.hasNFC();
        if (null != hasNFC)
            properties.put("$has_nfc", hasNFC.booleanValue());

        final Boolean hasTelephony = mSystemInformation.hasTelephony();
        if (null != hasTelephony)
            properties.put("$has_telephone", hasTelephony.booleanValue());

        final String carrier = mSystemInformation.getCurrentNetworkOperator();
        if (null != carrier)
            properties.put("$carrier", carrier);

        final Boolean isWifi = mSystemInformation.isWifiConnected();
        if (null != isWifi)
            properties.put("$wifi", isWifi.booleanValue());

        final Boolean isBluetoothEnabled = mSystemInformation.isBluetoothEnabled();
        if (isBluetoothEnabled != null)
            properties.put("$bluetooth_enabled", isBluetoothEnabled);

        final String bluetoothVersion = mSystemInformation.getBluetoothVersion();
        if (bluetoothVersion != null)
            properties.put("$bluetooth_version", bluetoothVersion);

        return properties;
    }
    //endregion

}
