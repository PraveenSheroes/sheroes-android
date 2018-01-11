package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {LeaderObj.class, FeedDetail.class})
public class LeaderObj extends FeedDetail {
    @SerializedName("crown_url")
    public String crownUrl;
}
