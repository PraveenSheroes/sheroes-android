package appliedlife.pvtltd.SHEROES.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;

public class ShareUtils {
    //region memeber varioables
    private static ShareUtils mInstance;
    //endregion

    //region constant variables
    private static final String SHARE_WHATSAPP = "Whatsapp";
    private static final String SHARE_FACEBOOK = "Facebook";
    private static final String ANDROID_DEFAULT = "Android Default";
    //endregion

    public static synchronized ShareUtils getInstance() {
        if (mInstance == null) {
            mInstance = new ShareUtils();
        }
        return mInstance;
    }

    private ShareUtils() {
    }

    public void initShare(Context context, Intent intent, String screenName) {
        String shareText = intent.getExtras().getString(AppConstants.SHARE_TEXT);
        String shareImage = intent.getExtras().getString(AppConstants.SHARE_IMAGE);
        String shareDeeplLink = intent.getExtras().getString(AppConstants.SHARE_DEEP_LINK_URL);
        String shareDialog = intent.getExtras().getString(AppConstants.SHARE_DIALOG_TITLE);
        String shareChannel = intent.getExtras().getString(AppConstants.SHARE_CHANNEL);
        Boolean isShareImage = false;
        if (CommonUtil.isNotEmpty(shareImage)) {
            isShareImage = true;
        }
        if (CommonUtil.isNotEmpty(shareChannel)) {
            if (shareChannel.equalsIgnoreCase(ANDROID_DEFAULT)) {
                if (isShareImage) {
                    CommonUtil.shareImageChooser(context, shareText, shareImage);
                } else {
                    CommonUtil.shareCardViaSocial(context, shareDeeplLink);
                }
            } else if (shareChannel.equalsIgnoreCase(SHARE_WHATSAPP)) {
                if (isShareImage) {
                    CommonUtil.shareImageWhatsApp(context, shareText, shareImage, screenName, true, null, null);
                } else {
                    CommonUtil.shareLinkToWhatsApp(context, shareText);
                }
            } else if (shareChannel.equalsIgnoreCase(SHARE_FACEBOOK)) {
                if (isShareImage) {
                    CommonUtil.facebookImageShare((Activity) context, shareImage);
                } else {
                    CommonUtil.shareFacebookLink((Activity) context, shareText);
                }
            } else {
                ShareBottomSheetFragment.showDialog((AppCompatActivity) context, shareText, shareImage, shareDeeplLink, "", isShareImage, shareDeeplLink, false, false, false, shareDialog);
            }
        } else {
            ShareBottomSheetFragment.showDialog((AppCompatActivity) context, shareText, shareImage, shareDeeplLink, "", isShareImage, shareDeeplLink, false, false, false, shareDialog);
        }
    }
}
