
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobDetail implements Parcelable {

    @SerializedName("typeOfJob")
    @Expose
    private String typeOfJob;
    @SerializedName("jobDescription")
    @Expose
    private String jobDescription;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("isApplied")
    @Expose
    private Boolean isApplied;

    public String getTypeOfJob() {
        return typeOfJob;
    }

    public void setTypeOfJob(String typeOfJob) {
        this.typeOfJob = typeOfJob;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getIsApplied() {
        return isApplied;
    }

    public void setIsApplied(Boolean isApplied) {
        this.isApplied = isApplied;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.typeOfJob);
        dest.writeString(this.jobDescription);
        dest.writeString(this.location);
        dest.writeValue(this.isApplied);
    }

    public JobDetail() {
    }

    protected JobDetail(Parcel in) {
        this.typeOfJob = in.readString();
        this.jobDescription = in.readString();
        this.location = in.readString();
        this.isApplied = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<JobDetail> CREATOR = new Parcelable.Creator<JobDetail>() {
        @Override
        public JobDetail createFromParcel(Parcel source) {
            return new JobDetail(source);
        }

        @Override
        public JobDetail[] newArray(int size) {
            return new JobDetail[size];
        }
    };
}
