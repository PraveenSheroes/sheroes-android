package appliedlife.pvtltd.SHEROES.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import androidx.core.app.ActivityCompat;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.CleverTapHelper;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogOutUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;

/**
 * Created by ujjwal on 21/12/17.
 */

public class BranchDeepLink extends BaseActivity {
    // region constants
    public static final String TAG = "BranchDeepLinkActivity";
    public static final String SCREEN_LABEL = "Deeplink Activity";
    // endregion

    // region Inject
    @Inject
    Preference<LoginResponse> mUserPreference;
    @Inject
    LogOutUtils mLogOutUtils;
    // endregion

    // region Class overridden methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        try {
            if (null == mUserPreference || null == mUserPreference.get().getUserSummary()) {
                mLogOutUtils.logOutUser(getScreenName(), this);
            } else {
                if (getIntent() == null || getIntent().getData() == null) {
                    startMainActivity();
                } else {
                    routeDeepLink();
                }
                setContentView(R.layout.activity_branch_deeplink);
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            mLogOutUtils.logOutUser(getScreenName(), this);
        }
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return null;
    }
    // endregion

    //region Private Helper methods
    private void routeDeepLink() {
        Branch branch = Branch.getInstance();
        branch.resetUserSession();
        if (CleverTapHelper.getCleverTapInstance(getApplicationContext()) != null) {
            branch.setRequestMetadata(CleverTapHelper.CLEVERTAP_ATTRIBUTION_ID,
                    CleverTapHelper.getCleverTapInstance(getApplicationContext()).getCleverTapAttributionIdentifier());
        }
        branch.initSession(new Branch.BranchReferralInitListener() {
                               @Override
                               public void onInitFinished(JSONObject referringParams, BranchError error) {
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

    private void deepLinkingRedirection() {
        // params are the deep linked params associated with the link that the user clicked before showing up
        // params will be empty if no data found
        boolean isShareDeepLink = false;
        Intent intent = new Intent();
        JSONObject sessionParams = Branch.getInstance().getLatestReferringParams();
        String shareText = "";
        String shareImage = "";
        String shareDeepLink = "";
        String shareDialogTitle = "";
        String shareChannel = "";
        try {
            String url = sessionParams.has(AppConstants.DEEP_LINK_URL) ? sessionParams.getString(AppConstants.DEEP_LINK_URL) : "";
            String openWebViewFlag = sessionParams.has(AppConstants.OPEN_IN_WEBVIEW) ? sessionParams.getString(AppConstants.OPEN_IN_WEBVIEW) : "";

            if (sessionParams.has(AppConstants.SHARE_DEEP_LINK_URL) && sessionParams.has(AppConstants.SHARE_TEXT)) {
                shareText = sessionParams.has(AppConstants.SHARE_TEXT) ? sessionParams.getString(AppConstants.SHARE_TEXT) : "";
                shareImage = sessionParams.has(AppConstants.SHARE_IMAGE) ? sessionParams.getString(AppConstants.SHARE_IMAGE) : "";
                shareDeepLink = sessionParams.has(AppConstants.SHARE_DEEP_LINK_URL) ? sessionParams.getString(AppConstants.SHARE_DEEP_LINK_URL) : "";
                shareDialogTitle = sessionParams.has(AppConstants.SHARE_DIALOG_TITLE) ? sessionParams.getString(AppConstants.SHARE_DIALOG_TITLE) : "";
                shareChannel = sessionParams.has(AppConstants.SHARE_CHANNEL) ? sessionParams.getString(AppConstants.SHARE_CHANNEL) : "";

                isShareDeepLink = true;
                if (!CommonUtil.isNotEmpty(url) && isShareDeepLink) {
                    Intent intentResult = new Intent();
                    intentResult.putExtra(AppConstants.SHARE_TEXT, shareText);
                    intentResult.putExtra(AppConstants.SHARE_IMAGE, shareImage);
                    intentResult.putExtra(AppConstants.SHARE_DEEP_LINK_URL, shareDeepLink);
                    intentResult.putExtra(AppConstants.SHARE_DIALOG_TITLE, shareDialogTitle);
                    intentResult.putExtra(AppConstants.IS_SHARE_DEEP_LINK, isShareDeepLink);
                    intentResult.putExtra(AppConstants.SHARE_CHANNEL, shareChannel);
                    setResult(RESULT_OK, intentResult);
                    finish();
                    return;
                }
            }

            if (url.equalsIgnoreCase(AppConstants.COLLECTION_NEW_URL) || url.equalsIgnoreCase(AppConstants.COLLECTION_NEW_URL_COM)) {
                String endPointUrl = sessionParams.has(AppConstants.END_POINT_URL) ? sessionParams.getString(AppConstants.END_POINT_URL) : "";
                String screenName = sessionParams.has(AppConstants.SCREEN_NAME) ? sessionParams.getString(AppConstants.SCREEN_NAME) : "";
                String viewType = sessionParams.has(AppConstants.COLLECTION_VIEW_TYPE) ? sessionParams.getString(AppConstants.COLLECTION_VIEW_TYPE) : "";

                if (StringUtil.isNotNullOrEmptyString(viewType)) {
                    if (viewType.equalsIgnoreCase(AppConstants.GRID_VIEW_TYPE)) { //Redirect to User Collection i.e in Grid
                        UsersCollectionActivity.navigateTo(this, endPointUrl, screenName, getScreenName(), screenName, null, 1);
                    }
                } else {
                    CollectionActivity.navigateTo(this, endPointUrl, screenName, getScreenName(), screenName, null, 1);
                }
                finish();
                return;
            }

            if (url.equalsIgnoreCase(AppConstants.CREATE_POST_URL) || url.equalsIgnoreCase(AppConstants.CREATE_POST_URL_COM)) {
                String id_for_entity = sessionParams.has(AppConstants.ID_FOR_CREATE_POST_ENTITY) ? sessionParams.getString(AppConstants.ID_FOR_CREATE_POST_ENTITY) : "";
                String entity_name = sessionParams.has(AppConstants.CREATE_POST_ENTITY_NAME) ? sessionParams.getString(AppConstants.CREATE_POST_ENTITY_NAME) : "";
                int createRequestFor = sessionParams.has(AppConstants.CREATE_POST_REQUEST_FOR) ? sessionParams.getInt(AppConstants.CREATE_POST_REQUEST_FOR) : 0;
                boolean isMyEntity = sessionParams.has(AppConstants.IS_MY_ENTITY) && sessionParams.getBoolean(AppConstants.IS_MY_ENTITY);
                String prefillText = sessionParams.has(AppConstants.PREFILL_TEXT) ? sessionParams.getString(AppConstants.PREFILL_TEXT) : "";
                boolean isChallengeType = sessionParams.has(AppConstants.IS_CHALLENGE_TYPE) && sessionParams.getBoolean(AppConstants.IS_CHALLENGE_TYPE);
                String challengeAuthorType = sessionParams.has(AppConstants.CHALLENGE_AUTHOR_TYPE) ? sessionParams.getString(AppConstants.CHALLENGE_AUTHOR_TYPE) : "";

                CommunityPost communityPost = new CommunityPost();
                communityPost.community = new Community();
                communityPost.community.name = entity_name;
                communityPost.body = prefillText;
                communityPost.isMyPost = isMyEntity;
                communityPost.createPostRequestFrom = createRequestFor;
                if (isChallengeType) {
                    communityPost.isChallengeType = true;
                    communityPost.challengeType = challengeAuthorType;
                    communityPost.challengeHashTag = prefillText;
                    communityPost.challengeId = Integer.parseInt(id_for_entity);
                } else {
                    communityPost.community.id = Long.parseLong(id_for_entity);
                }
                HashMap<String, Object> screenProperties = new EventProperty.Builder()
                        .sourceScreenId(entity_name)
                        .build();
                CommunityPostActivity.navigateTo(this, communityPost, AppConstants.REQUEST_CODE_FOR_COMMUNITY_POST, screenProperties, true);
                finish();
                return;
            }
            if (TextUtils.isEmpty(url)) {
                startMainActivity();
            } else {
                if (openWebViewFlag != null && openWebViewFlag.equalsIgnoreCase("true")) {
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
            intent.putExtra(AppConstants.IS_SHARE_DEEP_LINK, isShareDeepLink);
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
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            Intent intent = new Intent(BranchDeepLink.this, HomeActivity.class);
            ActivityCompat.startActivity(BranchDeepLink.this, intent, null);
            finish();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
            intent.setClass(BranchDeepLink.this, SheroesDeepLinkingActivity.class);
            startActivity(intent);
        }
    }

    private boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list =
                mgr.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
    // endregion
}
