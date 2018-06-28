package appliedlife.pvtltd.SHEROES.models.entities.imageUpload;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by ujjwal on 12/06/18.
 */

public class UploadImageRequest extends BaseRequest {
    @SerializedName("images")
    public List<String> images;
}
