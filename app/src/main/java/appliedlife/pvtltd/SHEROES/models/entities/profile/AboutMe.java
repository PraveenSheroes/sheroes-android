package appliedlife.pvtltd.SHEROES.models.entities.profile;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;

/**
 * Created by priyanka on 07/04/17.
 */
@Parcel(analyze = {AboutMe.class})
public class AboutMe{
    private String description;

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {

        this.description = description;
    }
}
