package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

/**
 * Created by ujjwal on 25/10/17.
 */
@Parcel(analyze = {UserProfile.class, Post.class})
public class UserProfile extends Post {
    public static final String USER_ID = "USER_ID";
    public static final String USER_OBJ = "USER_OBJ";
    public String name;
    public String bio;
    public String shortDescription;
    public String thumbUrl;
}
