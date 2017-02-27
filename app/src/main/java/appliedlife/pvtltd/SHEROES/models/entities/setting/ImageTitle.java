
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageTitle {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("privacySettingType")
    @Expose
    private String privacySettingType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrivacySettingType() {
        return privacySettingType;
    }

    public void setPrivacySettingType(String privacySettingType) {
        this.privacySettingType = privacySettingType;
    }

}
