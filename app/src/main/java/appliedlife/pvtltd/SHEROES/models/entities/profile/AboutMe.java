package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by priyanka on 07/04/17.
 */

public class AboutMe {
    private String description;

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {

        this.description = description;
    }
}
