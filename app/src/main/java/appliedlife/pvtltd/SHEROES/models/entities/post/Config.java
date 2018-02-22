package appliedlife.pvtltd.SHEROES.models.entities.post;

import android.content.Context;
import android.content.SharedPreferences;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

/**
 * Created by ujjwal on 17/10/17.
 */
@Parcel(analyze = {Config.class})
public class Config {

    private static final String APP_SHARE_URL = "";
    public static final String COMMUNITY_POST_IMAGE_SHARE = "I found this useful infographic on Sheroes app. Download the app and view the complete post here";
    public static final String COMMUNITY_POST_CHALLENGE_SHARE = "I found this challenge on Sheroes app. Download the app and view the complete challenge here";
    public static final String PROFILE_SHARE = "I found like-minded women on SHEROES to share my thoughts without hesitation.";
    public static Config getConfig() {
//        SharedPreferences prefs = SheroesApplication.getAppContext().getSharedPreferences(Globals.SHARED_PREFS, Context.MODE_PRIVATE);
//        String restoredText = prefs.getString(Globals.CONFIG_KEY, null);
//        if (restoredText != null) {
//            return CareServiceHelper.ensureGson().fromJson(restoredText, Config.class);
//        }
        return null;
    }

    public static String getShareUrl() {
        if(getConfig() == null){
            return APP_SHARE_URL;
        }
        return APP_SHARE_URL;
    }
}
