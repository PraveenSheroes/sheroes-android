package appliedlife.pvtltd.SHEROES.models.entities.setting;

import java.util.Map;

/**
 * Created by sheroes on 10/03/17.
 */

public class SegmentHash {
    private int id;
    private String name;
    private Map<String, Privileges> privilegesList;

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

    public Map<String, Privileges> getPrivilegesList() {
        return privilegesList;
    }

    public void setPrivilegesList(Map<String, Privileges> privilegesList) {
        this.privilegesList = privilegesList;
    }

    public SegmentHash() {
        super();
    }

    public SegmentHash(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
