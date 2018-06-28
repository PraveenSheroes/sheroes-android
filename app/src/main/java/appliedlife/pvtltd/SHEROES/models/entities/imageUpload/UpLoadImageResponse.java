package appliedlife.pvtltd.SHEROES.models.entities.imageUpload;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.Image;

/**
 * Created by ujjwal on 12/06/18.
 */

public class UpLoadImageResponse extends BaseResponse {

    @SerializedName("images")
    public List<Image> images;

}
