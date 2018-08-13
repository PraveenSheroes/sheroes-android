package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

@Parcel(analyze = {PollType.class})
public class PollType {
    public static final String POLL_TYPE = "poll_type";
    public int id;
    public String title;
    public String imgUrl;
}
