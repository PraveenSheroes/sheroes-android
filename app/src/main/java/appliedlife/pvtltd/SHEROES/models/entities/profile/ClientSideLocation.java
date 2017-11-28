package appliedlife.pvtltd.SHEROES.models.entities.profile;

import org.parceler.Parcel;

/**
 * Created by priyanka on 07/04/17.
 */
@Parcel(analyze = {ClientSideLocation.class})
public class ClientSideLocation {


    private String description;

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {

        this.description = description;
    }
}
