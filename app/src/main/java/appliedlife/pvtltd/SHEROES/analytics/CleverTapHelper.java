package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.appsflyer.AppsFlyerLib;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

/**
 * Created by ravi on 21/06/18.
 * CleverTap analytics
 */

public class CleverTapHelper {

    private static final String ORDER_KEY = "orderKey";
    private static final String NAME = "Name";
    private static final String PHOTO = "Photo";
    private static final String DATE_OF_BIRTH = "DOB";
    private static final String IDENTITY = "Identity";
    private static final String APPFLYER_ID = "Appsflyer_id";
    private static final String CREATED_DATE = "Created Date";
    private static final String GENDER = "Gender";
    private static final String EMAIL = "Email";
    private static final String PHONE = "Phone";
    private static final String LOCATION = "Location";

    @Inject
    Preference<LoginResponse> mUserPreference;

    //region public methods
    public CleverTapAPI getInstance(Context context) {
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

    public void setupUser(Context context) {
        SheroesApplication.getAppComponent(context).inject(this);
        UserSummary userSummary = null;
        UserBO userBio = null;
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

        CleverTapAPI cleverTapAPI = getInstance(context);

        if (cleverTapAPI != null) {

            HashMap<String, Object> profileUpdate = new HashMap<>();

            String feedConfigVersion = CommonUtil.getPref(AppConstants.FEED_CONFIG_VERSION);
            profileUpdate.put(SuperProperty.CONFIG_VERSION.getString(), feedConfigVersion);

            String orderKey = CommonUtil.getPref(AppConstants.SET_ORDER_KEY);
            profileUpdate.put(ORDER_KEY, orderKey);

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

            profileUpdate.put(APPFLYER_ID, AppsFlyerLib.getInstance().getAppsFlyerUID(context));

            if (userBio != null && !TextUtils.isEmpty(userBio.getCrdt())) {
                profileUpdate.put(CREATED_DATE, userBio.getCrdt());
            }

            if (userBio!=null && !TextUtils.isEmpty(userBio.getGender())) {
                profileUpdate.put(GENDER, userSummary.getUserBO().getGender());
            }

            if (!TextUtils.isEmpty(userSummary.getEmailId())) {
                profileUpdate.put(EMAIL, userSummary.getEmailId());
            }

            if (!TextUtils.isEmpty(userSummary.getMobile())) {
                profileUpdate.put(PHONE, "+91" + userSummary.getMobile());
            }

            if (userBio!=null && !TextUtils.isEmpty(userBio.getCityMaster())) {
                profileUpdate.put(LOCATION, userSummary.getUserBO().getCityMaster());
            }

            profileUpdate.put("MSG-sms", true);                      // Enable email notifications
            profileUpdate.put("MSG-push", true);                        // Enable push notifications
            profileUpdate.put("MSG-sms", true);                        // Enable SMS notifications

            cleverTapAPI.onUserLogin(profileUpdate);
        }
    }
    //endregion

}
