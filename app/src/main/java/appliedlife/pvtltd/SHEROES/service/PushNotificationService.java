package appliedlife.pvtltd.SHEROES.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.clevertap.android.sdk.CleverTapAPI;
import com.f2prateek.rx.preferences2.Preference;
import com.google.android.gms.gcm.GcmListenerService;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;
import com.moengage.push.PushManager;
import com.moengage.pushbase.push.MoEngageNotificationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
        if (null == data) return;

        String isCleverTapNotification = data.getString(AppConstants.CLEVER_TAP_IS_PRESENT);
        if (StringUtil.isNotNullOrEmptyString(isCleverTapNotification) && isCleverTapNotification.equalsIgnoreCase("true")) {
            handleCleverTapNotification(data);
        } else {
            if (MoEngageNotificationUtils.isFromMoEngagePlatform(data)) {
                handleMoEngageNotification(data);
            } else {
                handleOtherNotification(from, data);
            }
        }
    }

    private void handleOtherNotification(String from, Bundle data) {
        mMoEHelper = MoEHelper.getInstance(this);
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();

        String message = "";
        String imageUrl = "";
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

            if (action.equalsIgnoreCase("nothing")) {
                return;
            }
        }

        if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.MESSAGE))) {
            message = data.getString(AppConstants.MESSAGE);
        }
        if (StringUtil.isNotNullOrEmptyString(data.getString("left_image_icon"))) {
            imageUrl = data.getString("left_image_icon");
        }
        if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.TITLE))) {
            from = data.getString("title");
        }
        if (StringUtil.isNotNullOrEmptyString(data.getString(AppConstants.DEEP_LINK_URL))) {
            url = data.getString(AppConstants.DEEP_LINK_URL);
        }

        if (StringUtil.isNotNullOrEmptyString(url)) {
            if (StringUtil.isNotNullOrEmptyString(url)) {
                sendNotification(from, message, url, imageUrl);
            }

            String entityId = "";
            if (url.contains(AppConstants.ARTICLE_URL) || url.contains(AppConstants.ARTICLE_URL_COM)) {
                entityId = data.getString(this.getString(R.string.ID_ARTICLE));
                moEngageUtills.entityMoEngagePushNotification(this, mMoEHelper, payloadBuilder, this.getString(R.string.ID_ARTICLE), from, from);
            } else if (url.contains(AppConstants.COMMUNITY_URL) || url.contains(AppConstants.COMMUNITY_URL_COM)) {
                entityId = data.getString(this.getString(R.string.ID_COMMUNITIY));
                moEngageUtills.entityMoEngagePushNotification(this, mMoEHelper, payloadBuilder, this.getString(R.string.ID_COMMUNITIY), from, from);
            } else if (url.contains(AppConstants.HELPLINE_URL) || url.contains(AppConstants.HELPLINE_URL_COM)) {
                Intent intent = new Intent();
                intent.setAction("BroadCastReceiver");
                intent.putExtra(AppConstants.HELPLINE_CHAT, message);
                sendBroadcast(intent);
            }
            final HashMap<String, Object> properties = new EventProperty.Builder()
                    .id(notificationId)
                    .url(url)
                    .entityId(entityId)
                    .title(from)
                    .isMonengage(false)
                    .pushProvider(AppConstants.PUSH_PROVIDER_SHEROES)
                    .body(message)
                    .build();
            AnalyticsManager.trackEvent(Event.PUSH_NOTIFICATION_SHOWN, "", properties);
        }
    }

    private void handleMoEngageNotification(Bundle data) {
        //If the message is not sent from MoEngage it will be rejected
        final HashMap<String, Object> properties = new EventProperty.Builder()
                .id(MoEngageNotificationUtils.getCampaignIdIfAny(data))
                .url(MoEngageNotificationUtils.getDeeplinkURIStringIfAny(data))
                .isMonengage(true)
                .pushProvider(AppConstants.PUSH_PROVIDER_MOENGAGE)
                .activityName(MoEngageNotificationUtils.getRedirectActivityNameIfAny(data))
                .title(MoEngageNotificationUtils.getNotificationTitleIfAny(data))
                .body(MoEngageNotificationUtils.getNotificationContentTextIfAny(data))
                .build();
        AnalyticsManager.trackEvent(Event.PUSH_NOTIFICATION_SHOWN, "", properties);
        data.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 1);
        data.putBoolean(AppConstants.IS_MOENGAGE, true);
        data.putString(AppConstants.TITLE, MoEngageNotificationUtils.getNotificationTitleIfAny(data));
        data.putString(AppConstants.NOTIFICATION_ID, MoEngageNotificationUtils.getCampaignIdIfAny(data));
        data.putString(BaseActivity.SOURCE_SCREEN, "From Push Notification");
        data.putBoolean(AppConstants.IS_FROM_PUSH, true);
        PushManager.getInstance().getPushHandler().handlePushPayload(getApplicationContext(), data);
    }

    private void handleCleverTapNotification(Bundle data) {
        String cleverTypeTitle = data.getString(AppConstants.CLEVER_TAP_TITLE);
        String cleverTypeBody = data.getString(AppConstants.CLEVER_TAP_BODY);
        String cleverTypeDeepLink = data.getString(AppConstants.CLEVER_TAP_DEEP_LINK_URL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String relatedChannelId = getString(R.string.sheroesRelatedChannelID);
            CharSequence channelName = getString(R.string.sheroesRelatedChannelName);
            String channelDescription = getString(R.string.sheroesRelatedChannelDesc);
            CleverTapAPI.createNotificationChannel(getApplicationContext(), relatedChannelId, channelName, channelDescription, NotificationManager.IMPORTANCE_MAX, true);
        }

        data.putInt(AppConstants.FROM_PUSH_NOTIFICATION, 1);
        data.putBoolean(AppConstants.IS_MOENGAGE, false);
        data.putString(AppConstants.TITLE, cleverTypeTitle);
        data.putString(BaseActivity.SOURCE_SCREEN, AppConstants.FROM_PUSH_NOTIFICATION);
        data.putBoolean(AppConstants.IS_FROM_PUSH, true);

        CleverTapAPI.createNotification(getApplicationContext(), data);

        final HashMap<String, Object> properties = new EventProperty.Builder()
                .url(cleverTypeDeepLink)
                .title(cleverTypeTitle)
                .isMonengage(false)
                .pushProvider(AppConstants.PUSH_PROVIDER_CLEVER_TAP)
                .body(cleverTypeBody)
                .build();
        AnalyticsManager.trackEvent(Event.PUSH_NOTIFICATION_SHOWN, "", properties);

    }

    private void sendNotification(String title, String body, String urlText, String imageUrl) {
        Context context = getBaseContext();
        new NotificationImageLoader(context, title, body, urlText, imageUrl).execute();
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.vector_push_notification_icon : R.drawable.vector_push_notification_icon;
    }

    private class NotificationImageLoader extends AsyncTask<String, Void, Bitmap> {

        Context ctx;
        String title;
        String body;
        String urlText;
        String imageUrl;

        public NotificationImageLoader(Context context, String title, String body, String urlText, String imageUrl) {
            super();
            this.ctx = context;
            this.title = title;
            this.body = body;
            this.urlText = urlText;
            this.imageUrl = imageUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            try {

                Uri url = Uri.parse(urlText);
                mCount++;

                NotificationManager notificationManager = (NotificationManager) PushNotificationService.this.getSystemService(Activity.NOTIFICATION_SERVICE);

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

                notificationIntent = new Intent(PushNotificationService.this, SheroesDeepLinkingActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                notificationIntent.setData(url);
                notificationIntent.putExtra(AppConstants.IS_MOENGAGE, false);
                notificationIntent.putExtra(BaseActivity.SOURCE_SCREEN, "From Push Notification");
                notificationIntent.putExtra(AppConstants.TITLE, title);
                notificationIntent.putExtra(AppConstants.BODY, body);

                notificationIntent.putExtra(AppConstants.FROM_PUSH_NOTIFICATION, 1);
                notificationIntent.putExtra(AppConstants.IS_FROM_PUSH, true);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(PushNotificationService.this);
                stackBuilder.addNextIntentWithParentStack(notificationIntent);
                notificationIntent.setAction(AppConstants.SHEROES + mCount);
                Random random = new Random();
                int randomId = random.nextInt(9999 - 1000) + 1000;
                PendingIntent pIntent = stackBuilder.getPendingIntent(randomId, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
                Notification notification = new NotificationCompat.Builder(PushNotificationService.this, relatedChannelId)
                        .setContentTitle(title)
                        .setTicker(AppConstants.TICKER)
                        .setWhen(System.currentTimeMillis() + AppConstants.NOT_TIME)
                        .setContentText(body)
                        .setContentIntent(pIntent)
                        .setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE)
                        .setContentIntent(pIntent).setAutoCancel(true)
                        .setColor(ContextCompat.getColor(getApplication(), R.color.footer_icon_text))
                        .setLargeIcon(result)
                        .setChannelId(relatedChannelId)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                        .setSmallIcon(getNotificationIcon()).build();
                notificationManager.notify(Integer.parseInt(randomId + ""), notification);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
