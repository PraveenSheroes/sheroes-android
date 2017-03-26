package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;

/**
 * Created by Praveen_Singh on 26-03-2017.
 */

public class GetInterestJobResponse extends BaseResponse implements Parcelable {
    @SerializedName("docs")
    @Expose
    private List<BoardingInterestJobSearch> getBoardingInterestJobSearches = null;


    public List<BoardingInterestJobSearch> getGetBoardingInterestJobSearches() {
        return getBoardingInterestJobSearches;
    }

    public void setGetBoardingInterestJobSearches(List<BoardingInterestJobSearch> getBoardingInterestJobSearches) {
        this.getBoardingInterestJobSearches = getBoardingInterestJobSearches;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.getBoardingInterestJobSearches);
    }

    public GetInterestJobResponse() {
    }

    protected GetInterestJobResponse(Parcel in) {
        this.getBoardingInterestJobSearches = in.createTypedArrayList(BoardingInterestJobSearch.CREATOR);
    }

    public static final Creator<GetInterestJobResponse> CREATOR = new Creator<GetInterestJobResponse>() {
        @Override
        public GetInterestJobResponse createFromParcel(Parcel source) {
            return new GetInterestJobResponse(source);
        }

        @Override
        public GetInterestJobResponse[] newArray(int size) {
            return new GetInterestJobResponse[size];
        }
    };
}
