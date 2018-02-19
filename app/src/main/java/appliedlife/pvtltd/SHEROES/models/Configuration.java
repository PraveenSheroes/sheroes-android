package appliedlife.pvtltd.SHEROES.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppModule;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;

/**
 * Created by ujjwal on 13/02/17.
 */
public class Configuration extends BaseRequest {
    public String id;

    @SerializedName("config_type")
    public String configType;

    @SerializedName("config_data")
    public ConfigData configData;

    @SerializedName("config_version")
    public String configVersion;

    public static Configuration getConfig() {
        SharedPreferences prefs = SheroesApplication.mContext.getSharedPreferences(AppConstants.SHARED_PREFS, Context.MODE_PRIVATE);
        String restoredText = prefs.getString(AppConstants.CONFIG_KEY, null);
        if (restoredText != null) {
            return SheroesAppModule.ensureGson().fromJson(restoredText, Configuration.class);
        }
        return null;
    }
}
