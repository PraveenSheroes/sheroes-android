package appliedlife.pvtltd.SHEROES.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tagmanager.InstallReferrerReceiver;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;

/**
 * Created by SHEROES-TECH on 06-07-2017.
 */

public class CustomInstallTrackersReceiver extends BroadcastReceiver {
    private final String TAG = LogUtils.makeLogTag(CustomInstallTrackersReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            InstallReferrerReceiver googleReferrerTracking = new InstallReferrerReceiver();
            googleReferrerTracking.onReceive(context, intent);
            if (intent != null) {
                LogUtils.info(TAG, "********Referral Install tracker***********"+ intent.getStringExtra(AppConstants.GOOGLE_PLAY_URL_REFERRAL));
                if (intent.getStringExtra(AppConstants.GOOGLE_PLAY_URL_REFERRAL) != null) {
                    String url = intent.getStringExtra(AppConstants.GOOGLE_PLAY_URL_REFERRAL);
                    String referrers[] = url.split(AppConstants.AND_SIGN);
                    if (referrers != null && referrers.length >= 3) {
                        LogUtils.info(TAG, "********Size refral User Id tracker***********"+ referrers.length);
                        if (StringUtil.isNotNullOrEmptyString(referrers[3])) {
                            String appContactTableidArray[] = referrers[3].split(AppConstants.EQUAL_SIGN);
                            if (StringUtil.isNotNullOrEmptyString(appContactTableidArray[1])) {
                                String appUserContactid = appContactTableidArray[1];
                                LogUtils.info(TAG, "********App User Id tracker***********"+ appUserContactid);
                                Intent i = new Intent(context, WelcomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(AppConstants.GOOGLE_PLAY_URL_REFERRAL_CONTACT_ID, appUserContactid);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                //  i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.putExtras(bundle);
                                context.startActivity(i);
                            }
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
