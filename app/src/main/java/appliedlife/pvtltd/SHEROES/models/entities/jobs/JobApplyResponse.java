
package appliedlife.pvtltd.SHEROES.models.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class JobApplyResponse extends BaseResponse {

    @SerializedName("applied_job")
    @Expose
    private AppliedJob appliedJob;


    public AppliedJob getAppliedJob() {
        return appliedJob;
    }

    public void setAppliedJob(AppliedJob appliedJob) {
        this.appliedJob = appliedJob;
    }
}
