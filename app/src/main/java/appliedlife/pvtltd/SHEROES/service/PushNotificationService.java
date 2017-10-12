package appliedlife.pvtltd.SHEROES.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.f2prateek.rx.preferences.Preference;
import com.google.android.gms.gcm.GcmListenerService;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;
import com.moengage.pushbase.push.MoEngageNotificationUtils;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ArticleDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.SheroesDeepLinkingActivity;

/**
 * Created by Ajit on 10/22/2015.
 */
public class PushNotificationService extends GcmListenerService {
    @Inject
    Preference<LoginResponse> mUserPreference;
    Intent notificationIntent;
    int mCount = 0;
    String url = "";
    private MoEHelper mMoEHelper;
    private MoEngageUtills moEngageUtills;
    private PayloadBuilder payloadBuilder;
    private static String MOENGAGE_ALERT_MSG="gcm_alert";
    private static String MOENGAGE_TITLE="gcm_title";
    @Override
    public void onMessageReceived(String from, Bundle data) {
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        String message = "";
        if (null == data) return;

        String action = data.getString("action");
        if (action != null) {
            action = action.toLowerCase();
            if (action.equalsIgnoreCase("logout")) {
                mUserPreference.delete();
                MoEHelper.getInstance(getApplicationContext()).logoutUser();
                MixpanelHelper.clearMixpanel(SheroesApplication.mContext);
                ((NotificationManager) SheroesApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOG_OUT, GoogleAnalyticsEventActions.LOG_OUT_OF_APP, AppConstants.EMPTY_STRING);
            }

            if(action.equalsIgnoreCase("nothing")){
                return;
            }
        }

        if (MoEngageNotificationUtils.isFromMoEngagePlatform(data)) {
            //If the message is not sent from MoEngage it will be rejected
            PushManager.getInstance().getPushHandler().handlePushPayload(getApplicationContext(), data);
        }else {
            if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.MESSAGE))) {
                message = data.getString(AppConstants.MESSAGE);
            }
            if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.TITLE))) {
                from = data.getString("title");
            }
            if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.DEEP_LINK_URL))) {
                url = data.getString(AppConstants.DEEP_LINK_URL);
            }
            if (StringUtil.isNotNullOrEmptyString(url)) {
                if (StringUtil.isNotNullOrEmptyString(url)) {
                    sendNotification(from, message, url);
                }
                if (url.contains(AppConstants.ARTICLE_URL) || url.contains(AppConstants.ARTICLE_URL_COM)) {
                    moEngageUtills.entityMoEngagePushNotification(this, mMoEHelper, payloadBuilder, this.getString(R.string.ID_ARTICLE), from, from);
                } else if (url.contains(AppConstants.COMMUNITY_URL) || url.contains(AppConstants.COMMUNITY_URL_COM)) {
                    moEngageUtills.entityMoEngagePushNotification(this, mMoEHelper, payloadBuilder, this.getString(R.string.ID_COMMUNITIY), from, from);
                } else if (url.contains(AppConstants.JOB_URL) || url.contains(AppConstants.JOB_URL_COM)) {
                    moEngageUtills.entityMoEngagePushNotification(this, mMoEHelper, payloadBuilder, this.getString(R.string.ID_JOB), from, from);
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

        notificationIntent = new Intent(PushNotificationService.this, SheroesDeepLinkingActivity.class);
        notificationIntent.setData(url);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(PushNotificationService.this);
        stackBuilder.addParentStack(ArticleDetailActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        notificationIntent.setAction(AppConstants.SHEROES + mCount);

        PendingIntent pIntent = stackBuilder.getPendingIntent(AppConstants.NO_REACTION_CONSTANT,PendingIntent.FLAG_ONE_SHOT);
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
                .setColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_combined_shape))
                .setSmallIcon(getNotificationIcon()).build();
        notificationManager.notify(Integer.parseInt(mCount + ""), notification);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_combined_shape : R.drawable.ic_combined_shape;
    }


}
