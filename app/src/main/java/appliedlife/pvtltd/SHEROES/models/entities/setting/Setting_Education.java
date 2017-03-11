
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Setting_Education {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("privilegesList")
    @Expose
    private Setting_PrivilegesList_1 privilegesList;

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

    public Setting_PrivilegesList_1 getPrivilegesList() {
        return privilegesList;
    }

    public void setPrivilegesList(Setting_PrivilegesList_1 privilegesList) {
        this.privilegesList = privilegesList;
    }

}
