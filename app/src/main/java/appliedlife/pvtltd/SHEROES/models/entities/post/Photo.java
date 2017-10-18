package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

/**
 * Created by ujjwal on 17/10/17.
 */
@Parcel(analyze = {Photo.class, Post.class})
public class Photo extends Post {
    public String url;
}
