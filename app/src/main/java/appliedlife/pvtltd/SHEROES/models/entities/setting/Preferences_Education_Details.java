
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Preferences_Education_Details {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("privilegesList")
    @Expose
    private PrivilegesList_ privilegesList;

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

    public PrivilegesList_ getPrivilegesList() {
        return privilegesList;
    }

    public void setPrivilegesList(PrivilegesList_ privilegesList) {
        this.privilegesList = privilegesList;
    }

}
