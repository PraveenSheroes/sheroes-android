package appliedlife.pvtltd.SHEROES.models.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen_Singh on 03-04-2017.
 */

public class JobFilterRequest extends BaseRequest {
    @SerializedName("functional_areas")
    @Expose
    private List<String> functionalArea;
    @SerializedName("experience_from")
    @Expose
    private Integer experienceFrom;

    @SerializedName("experience_to")
    @Expose
    private Integer experienceTo;
    /** job filter starts **/
    @SerializedName("opportunity_types")
    @Expose
    private List<String> opportunityTypes;

    @SerializedName("skills")
    @Expose
    private List<String> skills;

    @SerializedName("cities")
    @Expose
    private List<String> cities;

    @SerializedName("opportunity_type_ids")
    @Expose
    private List<Long> opportunityTypeIds;

    @SerializedName("skill_ids")
    @Expose
    private List<Long> skillIds;

    @SerializedName("city_ids")
    @Expose
    private List<Long> cityId;

    @SerializedName("company_master_id")
    @Expose
    private Long companyMasterId;
}
