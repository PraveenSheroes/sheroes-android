package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ujjwal on 29/10/17.
 */
@Parcel(analyze = {MyCommunities.class})
public class MyCommunities {
    public static final String MY_COMMUNITY_OBJ = "MY_COMMUNITY_OBJ";
    public List<Community> myCommunities = new ArrayList<>();
}
