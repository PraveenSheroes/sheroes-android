package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

import java.util.ArrayList;


/**
 * Created by ujjwal on 17/10/17.
 */
@Parcel(analyze = {CommunityPost.class, Post.class})
public class CommunityPost extends Post {
    public ArrayList<Photo> photos;
}
