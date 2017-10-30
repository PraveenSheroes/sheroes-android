package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

/**
 * Created by ujjwal on 27/10/17.
 */
@Parcel(analyze = {Community.class, Post.class})
public class Community extends Post {
    public String name;
    public String imageUrl;
    public String thumbImageUrl;
    public long id;
    public boolean isChecked;
    public boolean isOwner;
}
