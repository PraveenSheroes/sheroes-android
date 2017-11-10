package appliedlife.pvtltd.SHEROES.models.entities.post;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;

/**
 * Created by ujjwal on 04/05/17.
 */

public class Prize {
    public String title;
    public String description;
    public String featureImage;
    public List<UserBO> winners;
}
