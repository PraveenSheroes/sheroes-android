package appliedlife.pvtltd.SHEROES.service;

import android.app.Activity;
import android.os.StrictMode;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Created by Praveen_Singh on 11-05-2017.
 */

public class GcmIdReceiver {
     static String  mGcmId=AppConstants.EMPTY_STRING;
    public static String getGcmId(Activity context) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GCMClientManager pushClientManager = new GCMClientManager(context, AppConstants.PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                LogUtils.info("Registration id", registrationId);
                mGcmId = registrationId;
            }

            @Override
            public void onFailure(String ex) {

            }
        });
        return mGcmId;
    }

}
