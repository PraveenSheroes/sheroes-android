package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;

/**
 * Created by Ajit Kumar on 19-03-2017.
 */
@Parcel(analyze = {GetAllData.class,BaseResponse.class})
public class GetAllData extends BaseResponse{
    @SerializedName("docs")
    @Expose
    private List<GetAllDataDocument> getAllDataDocuments = null;


    public List<GetAllDataDocument> getGetAllDataDocuments() {
        return getAllDataDocuments;
    }

    public void setGetAllDataDocuments(List<GetAllDataDocument> getAllDataDocuments) {
        this.getAllDataDocuments = getAllDataDocuments;
    }

    public GetAllData() {
    }
}
