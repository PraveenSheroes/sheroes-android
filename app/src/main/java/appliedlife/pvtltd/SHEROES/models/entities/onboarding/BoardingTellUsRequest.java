package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 01-04-2017.
 */

public class BoardingTellUsRequest extends BaseRequest {
    @SerializedName("city_master_id")
    @Expose
    private long cityMasterId;
    @SerializedName("city_master")
    @Expose
    private String cityMaster;
    @SerializedName("job_tag")
    @Expose
    private String jobTag;
    @SerializedName("job_tag_id")
    @Expose
    private long jobTagId;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("subType")
    @Expose
    private String subtype;
    @SerializedName("type")
    @Expose
    private String type;

    public long getCityMasterId() {
        return cityMasterId;
    }

    public void setCityMasterId(long cityMasterId) {
        this.cityMasterId = cityMasterId;
    }

    public String getCityMaster() {
        return cityMaster;
    }

    public void setCityMaster(String cityMaster) {
        this.cityMaster = cityMaster;
    }

    public String getJobTag() {
        return jobTag;
    }

    public void setJobTag(String jobTag) {
        this.jobTag = jobTag;
    }

    public long getJobTagId() {
        return jobTagId;
    }

    public void setJobTagId(long jobTagId) {
        this.jobTagId = jobTagId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
