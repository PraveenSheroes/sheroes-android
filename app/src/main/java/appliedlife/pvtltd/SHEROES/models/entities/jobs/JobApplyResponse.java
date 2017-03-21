
package appliedlife.pvtltd.SHEROES.models.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobApplyResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("fieldErrorMessageMap")
    @Expose
    private FieldErrorMessageMap fieldErrorMessageMap;
    @SerializedName("screen_name")
    @Expose
    private Object screenName;
    @SerializedName("applied_job")
    @Expose
    private AppliedJob appliedJob;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FieldErrorMessageMap getFieldErrorMessageMap() {
        return fieldErrorMessageMap;
    }

    public void setFieldErrorMessageMap(FieldErrorMessageMap fieldErrorMessageMap) {
        this.fieldErrorMessageMap = fieldErrorMessageMap;
    }

    public Object getScreenName() {
        return screenName;
    }

    public void setScreenName(Object screenName) {
        this.screenName = screenName;
    }

    public AppliedJob getAppliedJob() {
        return appliedJob;
    }

    public void setAppliedJob(AppliedJob appliedJob) {
        this.appliedJob = appliedJob;
    }

}
