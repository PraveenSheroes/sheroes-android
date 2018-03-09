package appliedlife.pvtltd.SHEROES.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.f2prateek.rx.preferences2.Preference;
import com.google.android.gms.gcm.GcmListenerService;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;
import com.moengage.pushbase.push.MoEngageNotificationUtils;

import java.util.HashMap;
import java.util.Random;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
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




    @Override
    public void onMessageReceived(String from, Bundle data) {
        SheroesApplication.getAppComponent(this).inject(this);
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        String message = "";
        if (null == data) return;
        String notificationId = data.getString(AppConstants.NOTIFICATION_ID);
        String action = data.getString("action");
        String secret = data.getString("secret");
        if (action != null) {
            action = action.toLowerCase();
            if (action.equalsIgnoreCase("logout") && secret != null && secret.equalsIgnoreCase("avadakedavra")) {
                if (mUserPreference != null) {
                    mUserPreference.delete();
                }
                MoEHelper.getInstance(getApplicationContext()).logoutUser();
                MixpanelHelper.clearMixpanel(SheroesApplication.mContext);
                ((NotificationManager) SheroesApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
                ((SheroesApplication) this.getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOG_OUT, GoogleAnalyticsEventActions.LOG_OUT_OF_APP, AppConstants.EMPTY_STRING);
                return;
            }

            if(action.equalsIgnoreCase("nothing")){
                return;
            }
        }

        if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.MESSAGE))) {
            message = data.getString(AppConstants.MESSAGE);
        }
        if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.TITLE))) {
            from = data.getString("title");
        }
        if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.DEEP_LINK_URL))) {
            url = data.getString(AppConstants.DEEP_LINK_URL);
        }

        if (MoEngageNotificationUtils.isFromMoEngagePlatform(data)) {
            //If the message is not sent from MoEngage it will be rejected
            final HashMap<String, Object> properties = new EventProperty.Builder()
                    .id(MoEngageNotificationUtils.getCampaignIdIfAny(data))
                    .url(MoEngageNotificationUtils.getDeeplinkURIStringIfAny(data))
                    .isMonengage(true)
                    .activityName(MoEngageNotificationUtils.getRedirectActivityNameIfAny(data))
                    .title(MoEngageNotificationUtils.getNotificationTitleIfAny(data))
                    .body(MoEngageNotificationUtils.getNotificationContentTextIfAny(data))
                    .build();
            AnalyticsManager.trackEvent(Event.PUSH_NOTIFICATION_SHOWN,"", properties);
            data.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 1);
            data.putBoolean(AppConstants.IS_MOENGAGE, true);
            data.putString(AppConstants.TITLE, MoEngageNotificationUtils.getNotificationTitleIfAny(data));
            data.putString(AppConstants.NOTIFICATION_ID, MoEngageNotificationUtils.getCampaignIdIfAny(data));
            data.putString(BaseActivity.SOURCE_SCREEN, "From Push Notification");
            data.putBoolean(AppConstants.IS_FROM_PUSH, true);
            PushManager.getInstance().getPushHandler().handlePushPayload(getApplicationContext(), data);
        }else {
            if (StringUtil.isNotNullOrEmptyString(url)) {
                if (StringUtil.isNotNullOrEmptyString(url)) {
                    sendNotification(from, message, url);
                }

                String entityId = "";
                if (url.contains(AppConstants.ARTICLE_URL) || url.contains(AppConstants.ARTICLE_URL_COM)) {
                    entityId = data.getString(this.getString(R.string.ID_ARTICLE));
                    moEngageUtills.entityMoEngagePushNotification(this, mMoEHelper, payloadBuilder, this.getString(R.string.ID_ARTICLE), from, from);
                } else if (url.contains(AppConstants.COMMUNITY_URL) || url.contains(AppConstants.COMMUNITY_URL_COM)) {
                    entityId = data.getString(this.getString(R.string.ID_COMMUNITIY));
                    moEngageUtills.entityMoEngagePushNotification(this, mMoEHelper, payloadBuilder, this.getString(R.string.ID_COMMUNITIY), from, from);
                } else if (url.contains(AppConstants.JOB_URL) || url.contains(AppConstants.JOB_URL_COM)) {
                    entityId = data.getString(this.getString(R.string.ID_JOB));
                    moEngageUtills.entityMoEngagePushNotification(this, mMoEHelper, payloadBuilder, this.getString(R.string.ID_JOB), from, from);
                }else if(url.contains(AppConstants.HELPLINE_URL) || url.contains(AppConstants.HELPLINE_URL_COM))
                {
                    Intent intent = new Intent();
                    intent.setAction("BroadCastReceiver");
                    intent.putExtra(AppConstants.HELPLINE_CHAT,message);
                    sendBroadcast(intent);
                }
                final HashMap<String, Object> properties = new EventProperty.Builder()
                        .id(notificationId)
                        .url(url)
                        .entityId(entityId)
                        .title(from)
                        .isMonengage(false)
                        .body(message)
                        .build();
                AnalyticsManager.trackEvent(Event.PUSH_NOTIFICATION_SHOWN,"", properties);
            }
        }
    }


    private void sendNotification(String title, String body, String urltext) {
        Context context = getBaseContext();

        Uri url = Uri.parse(urltext);

        mCount++;

        NotificationManager notificationManager = (NotificationManager) PushNotificationService.this
                .getSystemService(Activity.NOTIFICATION_SERVICE);

        String relatedChannelId = getString(R.string.sheroesRelatedChannelID);
        CharSequence channelName = getString(R.string.sheroesRelatedChannelName);
        int importance = NotificationManagerCompat.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(relatedChannelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setShowBadge(true);
            notificationChannel.setImportance(importance);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationIntent = new Intent(PushNotificationService.this, SheroesDeepLinkingActivity.class);
        notificationIntent.setData(url);
        notificationIntent.putExtra(AppConstants.IS_MOENGAGE, false);
        notificationIntent.putExtra(BaseActivity.SOURCE_SCREEN, "From Push Notification");
        notificationIntent.putExtra(AppConstants.TITLE, title);
        notificationIntent.putExtra(AppConstants.BODY, body);

        notificationIntent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, 1);
        notificationIntent.putExtra(AppConstants.IS_FROM_PUSH, true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(PushNotificationService.this);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        notificationIntent.setAction(AppConstants.SHEROES + mCount);

        PendingIntent pIntent = stackBuilder.getPendingIntent(AppConstants.NO_REACTION_CONSTANT,PendingIntent.FLAG_ONE_SHOT);
        Notification notification = new NotificationCompat.Builder(PushNotificationService.this, relatedChannelId)
                .setContentTitle(title)
                .setTicker(AppConstants.TICKER)
                .setWhen(System.currentTimeMillis() + AppConstants.NOT_TIME)
                .setContentText(AppConstants.CHECK_OUT)
                .setContentText(body)
                .setContentIntent(pIntent)
                .setDefaults(
                        NotificationCompat.DEFAULT_SOUND
                                | NotificationCompat.DEFAULT_VIBRATE)
                .setContentIntent(pIntent).setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_combined_shape))
                .setChannelId(relatedChannelId)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setSmallIcon(getNotificationIcon()).build();
        Random random = new Random();
        int randomId = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(Integer.parseInt(randomId + ""), notification);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_combined_shape : R.drawable.ic_combined_shape;
    }


}
