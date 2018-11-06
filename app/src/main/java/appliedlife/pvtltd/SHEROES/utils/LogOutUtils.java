package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.f2prateek.rx.preferences2.Preference;

import java.util.HashMap;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.AppInstallation;
import appliedlife.pvtltd.SHEROES.models.AppInstallationHelper;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.views.activities.WelcomeActivity;

public class LogOutUtils {
    //region member variables
    private static LogOutUtils sInstance;
    //endregion

    //region injected variables
    @Inject
    Preference<AppInstallation> mAppInstallation;
    @Inject
    Preference<LoginResponse> mUserPreference;
    //endregion

    public static synchronized LogOutUtils getInstance() {
        if (sInstance == null) {
            sInstance = new LogOutUtils();
        }
        return sInstance;
    }

    private LogOutUtils() {
        SheroesApplication.getAppComponent(SheroesApplication.mContext).inject(this);
    }


    public void logOutUser(String screenName, final Context context) {
        HashMap<String, Object> properties = new EventProperty.Builder().build();
        AnalyticsManager.trackEvent(Event.USER_LOG_OUT, screenName, properties);
        if (mAppInstallation != null && mAppInstallation.isSet()) {
            AppInstallation appInstallation = mAppInstallation.get();
            appInstallation.isLoggedOut = true;
            AppInstallationHelper appInstallationHelper = new AppInstallationHelper(context);
            appInstallationHelper.setAppInstallation(appInstallation);
            appInstallationHelper.saveInBackground(context, new CommonUtil.Callback() {
                @Override
                public void callBack(boolean isShown) {
                    Intent intent = new Intent(context, WelcomeActivity.class);
                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra(AppConstants.HIDE_SPLASH_THEME, true);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });
        }
        mUserPreference.delete();
        MixpanelHelper.clearMixpanel(SheroesApplication.mContext);
        ((NotificationManager) SheroesApplication.mContext.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        ((SheroesApplication) context.getApplicationContext()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_LOG_OUT, GoogleAnalyticsEventActions.LOG_OUT_OF_APP, AppConstants.EMPTY_STRING);

    }
}
