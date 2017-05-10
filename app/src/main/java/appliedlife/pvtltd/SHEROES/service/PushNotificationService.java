package appliedlife.pvtltd.SHEROES.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CommunitiesDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.JobDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;

/**
 * Created by Ajit on 10/22/2015.
 */
public class PushNotificationService extends GcmListenerService {
    Intent notificationIntent;
    int mCount=0;
    String url="";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message="";
        if(null !=data) {
            if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.MESSAGE))) {
                message = data.getString(AppConstants.MESSAGE);
            }
            if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.TITLE))) {
                from = data.getString("title");
            }
            if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.DEEP_LINK_URL))) {
                url = data.getString(AppConstants.DEEP_LINK_URL);
            }
            if (null != url) {
                    if (StringUtil.isNotNullOrEmptyString(url)) {

                        sendNotification(from,message, url);
                    }
                }
            }
        }

    private void sendNotification(String title, String body, String urltext) {
        Context context = getBaseContext();

        Uri url = Uri.parse(urltext);

        mCount++;

        NotificationManager notificationManager = (NotificationManager) PushNotificationService.this
                .getSystemService(Activity.NOTIFICATION_SERVICE);

            notificationIntent = new Intent(
                    PushNotificationService.this, SheroesDeepLinkingActivity.class);
            notificationIntent.setData(url);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(PushNotificationService.this);
            stackBuilder.addParentStack(ArticleDetailActivity.class);
            stackBuilder.addNextIntent(notificationIntent);
            notificationIntent.setAction(AppConstants.SHEROES + mCount);

            PendingIntent pIntent = stackBuilder.getPendingIntent(AppConstants.NO_REACTION_CONSTANT,
                    PendingIntent.FLAG_ONE_SHOT);
            Notification notification = new NotificationCompat.Builder(PushNotificationService.this)
                    .setContentTitle(title)
                    .setTicker(AppConstants.TICKER)
                    .setWhen(System.currentTimeMillis() + AppConstants.NOT_TIME)
                    .setContentText(AppConstants.CHECK_OUT)
                    .setContentText(body)
                    .setContentIntent(pIntent)
                    .setDefaults(
                            Notification.DEFAULT_SOUND
                                    | Notification.DEFAULT_VIBRATE)
                    .setContentIntent(pIntent).setAutoCancel(true)
                    .setColor(Color.GRAY)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_sheroes_icon))
                    .setSmallIcon(getNotificationIcon()).build();
            notificationManager.notify(Integer.parseInt(mCount+""), notification);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_sheroes : R.drawable.ic_sheroes_icon;
    }


}
