package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by ujjwal on 17/10/17.
 */
@Parcel(analyze = {Album.class, Post.class})
public class Album extends Post {
    public static final String ALBUM_OBJ = "ALBUM_OBJ";
    public ArrayList<Photo> photos = new ArrayList<>();
    public String deepLinkUrl;
}
