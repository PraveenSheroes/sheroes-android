package appliedlife.pvtltd.SHEROES.models.entities.post;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.models.entities.poll.PollType;

@Parcel(analyze = {PollOptionType.class})
public class PollOptionType {
    public static final String POLL_TYPE = "poll_type";
    public int id;
    public String title;
    public int imgUrl;
    public PollType pollType;
}
