package appliedlife.pvtltd.SHEROES.models.entities.profile;

import org.parceler.Parcel;

/**
 * Created by sheroes on 09/04/17.
 */

@Parcel(analyze = {CanHelpIn.class})
public class CanHelpIn {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
