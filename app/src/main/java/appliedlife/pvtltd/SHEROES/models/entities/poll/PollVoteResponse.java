package appliedlife.pvtltd.SHEROES.models.entities.poll;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.LikeResponse;

@Parcel(analyze = {PollVoteResponse.class, BaseResponse.class})
public class PollVoteResponse extends BaseResponse{
}
