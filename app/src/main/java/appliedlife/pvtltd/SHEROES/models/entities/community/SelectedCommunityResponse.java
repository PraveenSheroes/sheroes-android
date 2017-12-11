package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 21-03-2017.
 */
@Parcel(analyze = {SelectedCommunityResponse.class,BaseResponse.class})
public class SelectedCommunityResponse extends BaseResponse{

    @SerializedName("docs")
    @Expose
    private List<CommunityPostResponse> docs = null;

    public List<CommunityPostResponse> getDocs() {
        return docs;
    }

    public void setDocs(List<CommunityPostResponse> docs) {
        this.docs = docs;
    }



}

