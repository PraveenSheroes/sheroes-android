package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 26-03-2017.
 */
@Parcel(analyze = {GetInterestJobResponse.class, BaseResponse.class})
public class GetInterestJobResponse extends BaseResponse {
    @SerializedName("docs")
    @Expose
    private List<BoardingInterestJobSearch> getBoardingInterestJobSearches = null;


    public List<BoardingInterestJobSearch> getGetBoardingInterestJobSearches() {
        return getBoardingInterestJobSearches;
    }

    public void setGetBoardingInterestJobSearches(List<BoardingInterestJobSearch> getBoardingInterestJobSearches) {
        this.getBoardingInterestJobSearches = getBoardingInterestJobSearches;
    }
}
