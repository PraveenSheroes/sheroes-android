package appliedlife.pvtltd.SHEROES.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

/**
 * Created by ravi on 21/06/18.
 * CleverTap analytics
 */

public class CleverTapHelper {

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<Configuration> mConfiguration;

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
        if (mUserPreference == null) {
            return;
        }
        if (mUserPreference.isSet() && mUserPreference.get().getUserSummary() != null) {
            userSummary = mUserPreference.get().getUserSummary();
        }
        if (userSummary == null) {
            return;
        }

        CleverTapAPI cleverTapAPI = getInstance(context);

        if (cleverTapAPI != null && userSummary != null) {

            HashMap<String, Object> profileUpdate = new HashMap<>();

            String feedConfigVersion = CommonUtil.getPref(AppConstants.FEED_CONFIG_VERSION);

            profileUpdate.put(PeopleProperty.FEED_CONFIG.name(), feedConfigVersion);

            if (!TextUtils.isEmpty(userSummary.getFirstName())) {
                profileUpdate.put(PeopleProperty.NAME.name(), userSummary.getFirstName() + " " + userSummary.getLastName());
            }

            if (!TextUtils.isEmpty(userSummary.getPhotoUrl())) {
                profileUpdate.put(PeopleProperty.PHOTO.name(), userSummary.getPhotoUrl());
            }

            profileUpdate.put(PeopleProperty.IDENTITY.name(), userSummary.getUserId());

            if (!TextUtils.isEmpty(userSummary.getUserBO().getGender())) {
                profileUpdate.put(PeopleProperty.GENDER.name(), userSummary.getUserBO().getGender());
            }

            if (!TextUtils.isEmpty(userSummary.getEmailId())) {
                profileUpdate.put("$email", userSummary.getEmailId());
            }

            if (!TextUtils.isEmpty(userSummary.getMobile())) {
                profileUpdate.put("$phone no.", "+91"+userSummary.getMobile());
            }

            if (!TextUtils.isEmpty(userSummary.getUserBO().getCityMaster())) {
                profileUpdate.put(PeopleProperty.LOCATION.name(), userSummary.getUserBO().getCityMaster());
            }

            profileUpdate.put(PeopleProperty.LATITUDE.name(), userSummary.getUserBO().getLatitude());

            profileUpdate.put(PeopleProperty.LONGITUDE.name(), userSummary.getUserBO().getlongitude());

            profileUpdate.put(PeopleProperty.CREATED_DATE.name(), userSummary.getUserBO().getCrdt());

            profileUpdate.put(PeopleProperty.CLEVER_TAP_EMAIL.name(), true);                      // Enable email notifications
            profileUpdate.put(PeopleProperty.CLEVER_TAP_PUSH.name(), true);                        // Enable push notifications
            profileUpdate.put(PeopleProperty.CLEVER_TAP_SMS.name(), true);                        // Enable SMS notifications

            cleverTapAPI.profile.push(profileUpdate);
        }
    }
    //endregion

}
