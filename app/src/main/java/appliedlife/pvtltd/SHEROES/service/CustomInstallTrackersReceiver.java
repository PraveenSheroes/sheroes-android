package appliedlife.pvtltd.SHEROES.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.f2prateek.rx.preferences.Preference;
import com.google.android.gms.tagmanager.InstallReferrerReceiver;

import java.net.URLDecoder;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserFromReferralRequest;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.presenters.LoginPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;

/**
 * Created by SHEROES-TECH on 06-07-2017.
 */

public class CustomInstallTrackersReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            InstallReferrerReceiver googleReferrerTracking = new InstallReferrerReceiver();
            googleReferrerTracking.onReceive(context, intent);
            if (intent != null) {
                Log.d("CustomInstallTrackers", "Referrer: " + intent.getStringExtra(AppConstants.GOOGLE_PLAY_URL_REFERRAL));
                if (intent.getStringExtra(AppConstants.GOOGLE_PLAY_URL_REFERRAL) != null) {
                    String url = intent.getStringExtra(AppConstants.GOOGLE_PLAY_URL_REFERRAL);
                    String referrers[] = url.split(AppConstants.AND_SIGN);
                    if (referrers != null && referrers.length >= 3) {
                        if (StringUtil.isNotNullOrEmptyString(referrers[3])) {
                            String appContactTableidArray[] = referrers[3].split(AppConstants.EQUAL_SIGN);
                            if (StringUtil.isNotNullOrEmptyString(appContactTableidArray[1])) {
                                String appUserContactid = appContactTableidArray[1];
                                Log.d("User contact table id", "app user contact id :" + appUserContactid);
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
