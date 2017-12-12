package appliedlife.pvtltd.SHEROES.models.entities.post;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ujjwal on 03/05/17.
 */
@Parcel(analyze = {Winner.class})
public class Winner {
    @SerializedName("image_url")
    public String imageUrl;

    @SerializedName("name")
    public String name;

    @SerializedName("prize_icon")
    public String mPrizeIcon;

    @SerializedName("challenge_id")
    public String challengeId;

    @SerializedName("winner_rank")
    public String rank;

    @SerializedName("prize_description")
    public String prizeDescription;

    @SerializedName("user_id")
    public String userId;

    public boolean isHeader;
}
