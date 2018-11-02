package appliedlife.pvtltd.SHEROES.analytics.Impression;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel(analyze = {ImpressionResponse.class})
public class ImpressionResponse {

    @SerializedName("success")
    private boolean isSuccessFul;

    public boolean isSuccessFul() {
        return isSuccessFul;
    }

    public void setSuccessFul(boolean successFul) {
        isSuccessFul = successFul;
    }
}
