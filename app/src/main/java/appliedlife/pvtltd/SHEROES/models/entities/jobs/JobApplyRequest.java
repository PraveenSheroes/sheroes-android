package appliedlife.pvtltd.SHEROES.models.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Ajit Kumar on 10-03-2017.
 */

public class JobApplyRequest extends BaseRequest{
    @SerializedName("cover_note")
    @Expose
    private String coverNote;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("job_id")
    @Expose
    private Long jobId;


    public String getCoverNote() {
        return coverNote;
    }

    public void setCoverNote(String coverNote) {
        this.coverNote = coverNote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
