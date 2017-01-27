
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobDetail {

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

}
