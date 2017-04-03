package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 02-04-2017.
 */

public class BoardingWorkExpRequest extends BaseRequest {
    @SerializedName("subType")
    @Expose
    private String subtype;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sector_id")
    @Expose
    private long sectorId;

    @SerializedName("job_tag_id")
    @Expose
    private long jobTagId;
    @SerializedName("total_exp_year")
    @Expose
    private int totalExpYear;
    @SerializedName("total_exp_month")
    @Expose
    private int totalExpMonth;
    @SerializedName("add_languages")
    @Expose
    private List<LanguageEntity> addLanguages;
    @SerializedName("remove_languages")
    @Expose
    private List<LanguageEntity> removeLanguages;


    public long getSectorId() {
        return sectorId;
    }

    public void setSectorId(long sectorId) {
        this.sectorId = sectorId;
    }

    public long getJobTagId() {
        return jobTagId;
    }

    public void setJobTagId(long jobTagId) {
        this.jobTagId = jobTagId;
    }

    public int getTotalExpYear() {
        return totalExpYear;
    }

    public void setTotalExpYear(int totalExpYear) {
        this.totalExpYear = totalExpYear;
    }

    public int getTotalExpMonth() {
        return totalExpMonth;
    }

    public void setTotalExpMonth(int totalExpMonth) {
        this.totalExpMonth = totalExpMonth;
    }

    public List<LanguageEntity> getAddLanguages() {
        return addLanguages;
    }

    public void setAddLanguages(List<LanguageEntity> addLanguages) {
        this.addLanguages = addLanguages;
    }

    public List<LanguageEntity> getRemoveLanguages() {
        return removeLanguages;
    }

    public void setRemoveLanguages(List<LanguageEntity> removeLanguages) {
        this.removeLanguages = removeLanguages;
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
