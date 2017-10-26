package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

import java.util.Date;

import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;

/**
 * Created by ujjwal on 17/10/17.
 */
@Parcel(analyze = {Post.class})
public class Post {
    public static final String FEED_OBJ = "FEED_OBJ";
    public String title;
    public String body;
    public int remote_id;
    public UserBO author;
    public String createdAt;
    public String updatedAt;
}
