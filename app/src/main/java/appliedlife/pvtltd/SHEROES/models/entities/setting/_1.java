
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _1 {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("privilegesList")
    @Expose
    private PrivilegesList privilegesList;

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

    public PrivilegesList getPrivilegesList() {
        return privilegesList;
    }

    public void setPrivilegesList(PrivilegesList privilegesList) {
        this.privilegesList = privilegesList;
    }

}
