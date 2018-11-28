package appliedlife.pvtltd.SHEROES.models.entities.vernacular;

import com.google.gson.annotations.SerializedName;

public class LanguageUpdateRequest {
    @SerializedName("language")
    public String language;
    @SerializedName("user_id")
    public Long userId;
}
