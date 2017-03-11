package appliedlife.pvtltd.SHEROES.models.entities.setting;

/**
 * Created by sheroes on 10/03/17.
 */

public class Privileges {
    private int id;
    private String name;
    private String privacySettingType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Privileges() {
        super();
    }

    public Privileges(int id, String name, String privacySettingType) {
        super();
        this.id = id;
        this.name = name;
        this.privacySettingType = privacySettingType;
    }


}
