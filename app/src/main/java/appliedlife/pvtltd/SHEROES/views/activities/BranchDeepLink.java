package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

/**
 * Created by ujjwal on 21/12/17.
 */

public class BranchDeepLink extends BaseActivity {
    public static final String TAG = "BranchDeepLinkActivity";
    public static final String SCREEN_LABEL = "Deeplink Activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean(AppConstants.DEFFERED_DEEP_LINK)) {
                routeDeepLink();
                return;
            }
        }
        if (getIntent() == null || getIntent().getData() == null) {
            startMainActivity();
        } else {
            routeDeepLink();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Branch.isAutoDeepLinkLaunch(this)) {
            LogUtils.info(TAG,"##################### isAutoDeepLinkLaunch########");
        } else {
        }
    }

    //region Private Helper methods
    private void routeDeepLink() {
        Branch branch = Branch.getInstance();
        branch.resetUserSession();
        branch.initSession(new Branch.BranchReferralInitListener() {
                               @Override
                               public void onInitFinished(JSONObject referringParams, BranchError error) {
                                   if (getIntent().getExtras().getBoolean(AppConstants.DEFFERED_DEEP_LINK)) {
                                       deepLinkingRedirection();
                                   }else {
                                       if (getIntent() == null || getIntent().getData() == null) {
                                           return;
                                       }
                                   }
                                   if (error == null) {
                                       deepLinkingRedirection();
                                   } else {
                                       Crashlytics.getInstance().core.logException(new Exception("Failed to initialize Branch Session " + error.getMessage()));
                                       startMainActivity();
                                   }
                               }
                           }
                , this.getIntent().getData(), this);
    }
    private void deepLinkingRedirection()
    {
        // params are the deep linked params associated with the link that the user clicked before showing up
        // params will be empty if no data found
        Intent intent = new Intent();

        JSONObject sessionParams = Branch.getInstance().getLatestReferringParams();
        LogUtils.info(TAG,"##################### Branch session params########"+new Gson().toJson(sessionParams));
        try {
            String url = sessionParams.has(AppConstants.DEEP_LINK_URL) ? sessionParams.getString(AppConstants.DEEP_LINK_URL) : "";
            String openWebViewFlag = sessionParams.has(AppConstants.OPEN_IN_WEBVIEW) ? sessionParams.getString(AppConstants.OPEN_IN_WEBVIEW) : "";
            if (TextUtils.isEmpty(url)) {
                startMainActivity();
            } else {
                if (openWebViewFlag!=null && openWebViewFlag.equalsIgnoreCase("true")) {
                    Uri urlWebSite = Uri.parse(url);
                    AppUtils.openChromeTabForce(BranchDeepLink.this, urlWebSite);
                    finish();
                    return;
                }
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (url.startsWith("https://sheroes.com") || url.startsWith("http://sheroes.com") || url.startsWith("https://sheroes.in") || url.startsWith("http://sheroes.in")) {
                    // Do not let others grab our call
                    intent.setPackage(SheroesApplication.mContext.getPackageName());
                } else {
                    startActivity(intent);
                    finish();
                    return;
                }
            }
            Iterator<String> iterator = sessionParams.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                try {
                    Object value = sessionParams.get(key);
                    if (value instanceof String) {
                        intent.putExtra(key, (String) value);
                    }
                    if (value instanceof Boolean) {
                        intent.putExtra(key, (boolean) value);
                    }
                    if (value instanceof Integer) {
                        intent.putExtra(key, (int) value);
                    }
                } catch (JSONException e) {
                }
            }
            if (isIntentAvailable(BranchDeepLink.this, intent)) {
                intent.putExtra(BaseActivity.SOURCE_SCREEN, getScreenName());
                if (Uri.parse(url).getPath().equals("/home/") && intent.getExtras() != null) {
                    intent.setClass(BranchDeepLink.this, SheroesDeepLinkingActivity.class);
                }
                startActivity(intent);
            }
            finish();
        } catch (JSONException e) {
            Crashlytics.getInstance().core.logException(e);
            startMainActivity();
        }
    }
    private void startMainActivity() {
        Intent intent = new Intent(BranchDeepLink.this, HomeActivity.class);
        ActivityCompat.startActivity(BranchDeepLink.this, intent, null);
        finish();
    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }
}
