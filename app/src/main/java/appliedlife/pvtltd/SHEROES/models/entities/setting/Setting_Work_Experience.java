
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Setting_Work_Experience {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("privilegesList")
    @Expose
    private Setting_Privileges_List privilegesList;

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

    public Setting_Privileges_List getPrivilegesList() {
        return privilegesList;
    }

    public void setPrivilegesList(Setting_Privileges_List privilegesList) {
        this.privilegesList = privilegesList;
    }

}
