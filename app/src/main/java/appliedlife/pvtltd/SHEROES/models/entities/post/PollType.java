package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

@Parcel(analyze = {PollType.class})
public class PollType {
    public int id;
    public String title;
    public String body;
    public String imgUrl;
}
