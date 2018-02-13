package appliedlife.pvtltd.SHEROES.models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppModule;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by ujjwal on 13/02/17.
 */
public class RemoteConfig extends BaseRequest {

    public String supportEmail;
    public String feedbackEmail;
    public String supportPhone;
    public String appShareUrl;
    public Integer minSessionRatePublish;
    public Integer minGapRateRequest;
    public Integer maxRatePublishCount;
    public Integer minDpChangeRatePublish;
    public Integer minDocUploadRatePublish;
    public Integer minLikesRatePublish;
    public Integer minSharesRatePublish;
    public Integer sessionExpiryMinute;
    public String idealWeightMax;
    public String idealHeightMax;
    public String idealWeightMin;
    public String idealHeightMin;
    public boolean enableShareLayout;
    public String articleShareText;
    public String appShareText;
    public boolean enableImmediateAnswering;
    public int minAnswerLength;
    public int feedLimit;
    public int maxLimitForNewsfeed;
    public int refreshFeedTimePeriod;
    public boolean shouldShowToOldUser = false;
    public String rateDialogImageUrl;
    public String webViewStyle;
    public String webJavaScript;
    public boolean useNewSavedItemDesign = false;
    public String mImageShareText;
    public String thumborUrl;
    public String thumborKey;
    public String anonymousUserName;
    public boolean alwaysShowSimilarQuestion;
    public String suggestedQuestionTitle;
    public String userWhatsAppShareText;
    public boolean shareProfileEnable = false;
    public String updateTitle;
    public String updateDescription;
    public Integer updateVersion;
    public String fbQuestionImageUrl;
    public String inviteLayoutText;
    public String inviteToastText;
    public boolean showInviteImage = false;
    public boolean enableQuestionSearch = false;
    public String youtubeApiKey;
    public String albumShareText;
    public List<String> tabList;

    public static RemoteConfig getConfig() {
        SharedPreferences prefs = SheroesApplication.mContext.getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        String restoredText = prefs.getString(AppConstants.CONFIG_KEY, null);
        if (restoredText != null) {
            return SheroesAppModule.ensureGson().fromJson(restoredText, RemoteConfig.class);
        }
        return null;
    }
}
