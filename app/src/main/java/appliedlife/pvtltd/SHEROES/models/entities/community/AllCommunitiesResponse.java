package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;

/**
 * Created by ujjwal on 02/11/17.
 */
@Parcel(analyze = {AllCommunitiesResponse.class,BaseResponse.class})
public class AllCommunitiesResponse extends BaseResponse {
    @SerializedName("docs")
    @Expose
    public List<FeedDetail> feedDetails = null;
}
