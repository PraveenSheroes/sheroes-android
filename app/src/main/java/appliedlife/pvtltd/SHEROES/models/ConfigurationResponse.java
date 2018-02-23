package appliedlife.pvtltd.SHEROES.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ujjwal on 19/02/18.
 */

public class ConfigurationResponse {
    @SerializedName("configuration")
    public Configuration configuration;

    @SerializedName("message")
    public String message;

    @SerializedName("status")
    public String status;
}
