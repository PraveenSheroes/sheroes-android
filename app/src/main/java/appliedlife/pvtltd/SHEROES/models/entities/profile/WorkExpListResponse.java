package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 04-05-2017.
 */

public class WorkExpListResponse extends BaseResponse{
    @SerializedName("exprienceBO")
    @Expose
    private ExperienceBOdetail experienceBOdetail;

    public ExperienceBOdetail getExperienceBOdetail() {
        return experienceBOdetail;
    }

    public void setExperienceBOdetail(ExperienceBOdetail experienceBOdetail) {
        this.experienceBOdetail = experienceBOdetail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.experienceBOdetail, flags);
    }

    public WorkExpListResponse() {
    }

    protected WorkExpListResponse(Parcel in) {
        super(in);
        this.experienceBOdetail = in.readParcelable(ExperienceBOdetail.class.getClassLoader());
    }

    public static final Creator<WorkExpListResponse> CREATOR = new Creator<WorkExpListResponse>() {
        @Override
        public WorkExpListResponse createFromParcel(Parcel source) {
            return new WorkExpListResponse(source);
        }

        @Override
        public WorkExpListResponse[] newArray(int size) {
            return new WorkExpListResponse[size];
        }
    };
}
