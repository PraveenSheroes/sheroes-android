package appliedlife.pvtltd.SHEROES.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;

/**
 * Created by Praveen on 16/01/18.
 */

public class BroadCastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (null != intent && null != intent.getExtras()) {
            String message;
            message = intent.getExtras().getString(AppConstants.HELPLINE_CHAT);
            if (null != message && StringUtil.isNotNullOrEmptyString(message)) {
               /* Uri uri = Uri.parse(url);
                Intent intent1 = new Intent(context, SheroesDeepLinkingActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setData(uri);
                ActivityCompat.startActivity(context, intent1, null);*/
                Intent helplineIntent = new Intent(context, HomeActivity.class);
               // helplineIntent.putExtra("msg",message);
                helplineIntent.putExtra(AppConstants.HELPLINE_CHAT, AppConstants.HELPLINE_CHAT);
                helplineIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(helplineIntent);
            }
        }
    }
}

