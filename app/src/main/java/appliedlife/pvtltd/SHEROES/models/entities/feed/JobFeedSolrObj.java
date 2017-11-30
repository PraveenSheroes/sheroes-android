package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {JobFeedSolrObj.class,FeedDetail.class})
public class JobFeedSolrObj extends FeedDetail {
    @SerializedName(value="company_profile_id_l")
    private int companyProfileId;

    @SerializedName(value="top_company_tag_link_id_l")
    private String topCompanyTagLinkId;

    @SerializedName(value="sector_id_l")
    private int sectorId;

    @SerializedName(value="sector_name_s")
    private String sectorName;

    @SerializedName(value="job_city_id_l")
    private String cityId;

    @SerializedName(value="job_city_name_s")
    private String cityName;

    @SerializedName(value="is_from_search_firm_b")
    private Boolean isFromSearchFirm;

    @SerializedName(value="is_search_firm_b")
    private boolean isSearchFirm;

    @SerializedName(value="h_company_name_s")
    private String proxiedCompanyName;

    @SerializedName(value="h_company_logo_s")
    private String proxiedCompanyLogo;

    @SerializedName(value="experience_from_i")
    private int experiecneFrom;

    @SerializedName(value="experience_to_i")
    private int experienceTo;

    @SerializedName(value = "search_id_job_opp_types")
    private List<Long> opportunityTypeIds;

    @SerializedName(value = "search_text_job_opp_types")
    private List<String> opportunityTypes;

    @SerializedName(value = "search_id_job_emp_types")
    private List<Long> employmentTypeIds;

    @SerializedName(value = "search_text_job_emp_types")
    private List<String> searchTextJobEmpTypes;

    @SerializedName(value = "search_id_job_skills")
    private List<Long> skillIds;

    @SerializedName(value = "search_text_job_skills")
    private List<String> searchTextJobSkills;

    @SerializedName(value = "job_Type_s")
    private String jobType;

    @SerializedName(value = "end_date_dt")
    private String endDate;

    @SerializedName(value = "start_date_dt")
    private String startDate;

    @SerializedName(value = "is_assisted_b")
    private boolean isAssisted;

    @SerializedName(value = "is_premium_b")
    private boolean isPremimiun;

    @SerializedName(value = "is_ctc_required_b")
    private boolean isCtcRequired;

    @SerializedName(value = "compensation_from_l")
    private long compensationFrom;

    @SerializedName(value = "compensation_to_l")
    private long compensationTo;

    @SerializedName(value = "no_of_openings_i")
    private int noOfOpenings;

    @SerializedName(value = "compensation_details_s")
    private String compensationDetails;

    @SerializedName(value = "company_master_id_l")
    private Long companyMasterId;

    @SerializedName(value = "s_disp_external_application_url")
    private String externalApplicationUrl;

    @SerializedName(value = "address_s")
    private String address;

    @SerializedName(value="s_disp_emailid")
    private String companyEmailId;

    /*@SerializedName(value="author_first_name")
    private String companyFirstContectPersionName;*/

    @SerializedName(value="s_disp_compensation_currency")
    private String compensationCurrency;

    @SerializedName(value="s_disp_job_type_details")
    private String jobTypeDetails;

    @SerializedName(value="solr_ignore_job_level")
    private String jobLevel;

    public int getCompanyProfileId() {
        return companyProfileId;
    }

    public void setCompanyProfileId(int companyProfileId) {
        this.companyProfileId = companyProfileId;
    }

    public String getTopCompanyTagLinkId() {
        return topCompanyTagLinkId;
    }

    public void setTopCompanyTagLinkId(String topCompanyTagLinkId) {
        this.topCompanyTagLinkId = topCompanyTagLinkId;
    }

    public int getSectorId() {
        return sectorId;
    }

    public void setSectorId(int sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getFromSearchFirm() {
        return isFromSearchFirm;
    }

    public void setFromSearchFirm(Boolean fromSearchFirm) {
        isFromSearchFirm = fromSearchFirm;
    }

    public boolean isSearchFirm() {
        return isSearchFirm;
    }

    public void setSearchFirm(boolean searchFirm) {
        isSearchFirm = searchFirm;
    }

    public String getProxiedCompanyName() {
        return proxiedCompanyName;
    }

    public void setProxiedCompanyName(String proxiedCompanyName) {
        this.proxiedCompanyName = proxiedCompanyName;
    }

    public String getProxiedCompanyLogo() {
        return proxiedCompanyLogo;
    }

    public void setProxiedCompanyLogo(String proxiedCompanyLogo) {
        this.proxiedCompanyLogo = proxiedCompanyLogo;
    }

    public int getExperiecneFrom() {
        return experiecneFrom;
    }

    public void setExperiecneFrom(int experiecneFrom) {
        this.experiecneFrom = experiecneFrom;
    }

    public int getExperienceTo() {
        return experienceTo;
    }

    public void setExperienceTo(int experienceTo) {
        this.experienceTo = experienceTo;
    }

    public List<Long> getOpportunityTypeIds() {
        return opportunityTypeIds;
    }

    public void setOpportunityTypeIds(List<Long> opportunityTypeIds) {
        this.opportunityTypeIds = opportunityTypeIds;
    }

    public List<String> getOpportunityTypes() {
        return opportunityTypes;
    }

    public void setOpportunityTypes(List<String> opportunityTypes) {
        this.opportunityTypes = opportunityTypes;
    }

    public List<Long> getEmploymentTypeIds() {
        return employmentTypeIds;
    }

    public void setEmploymentTypeIds(List<Long> employmentTypeIds) {
        this.employmentTypeIds = employmentTypeIds;
    }

    public List<String> getSearchTextJobEmpTypes() {
        return searchTextJobEmpTypes;
    }

    public void setSearchTextJobEmpTypes(List<String> searchTextJobEmpTypes) {
        this.searchTextJobEmpTypes = searchTextJobEmpTypes;
    }

    public List<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(List<Long> skillIds) {
        this.skillIds = skillIds;
    }

    public List<String> getSearchTextJobSkills() {
        return searchTextJobSkills;
    }

    public void setSearchTextJobSkills(List<String> searchTextJobSkills) {
        this.searchTextJobSkills = searchTextJobSkills;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isAssisted() {
        return isAssisted;
    }

    public void setAssisted(boolean assisted) {
        isAssisted = assisted;
    }

    public boolean isPremimiun() {
        return isPremimiun;
    }

    public void setPremimiun(boolean premimiun) {
        isPremimiun = premimiun;
    }

    public boolean isCtcRequired() {
        return isCtcRequired;
    }

    public void setCtcRequired(boolean ctcRequired) {
        isCtcRequired = ctcRequired;
    }

    public long getCompensationFrom() {
        return compensationFrom;
    }

    public void setCompensationFrom(long compensationFrom) {
        this.compensationFrom = compensationFrom;
    }

    public long getCompensationTo() {
        return compensationTo;
    }

    public void setCompensationTo(long compensationTo) {
        this.compensationTo = compensationTo;
    }

    public int getNoOfOpenings() {
        return noOfOpenings;
    }

    public void setNoOfOpenings(int noOfOpenings) {
        this.noOfOpenings = noOfOpenings;
    }

    public String getCompensationDetails() {
        return compensationDetails;
    }

    public void setCompensationDetails(String compensationDetails) {
        this.compensationDetails = compensationDetails;
    }

    public Long getCompanyMasterId() {
        return companyMasterId;
    }

    public void setCompanyMasterId(Long companyMasterId) {
        this.companyMasterId = companyMasterId;
    }

    public String getExternalApplicationUrl() {
        return externalApplicationUrl;
    }

    public void setExternalApplicationUrl(String externalApplicationUrl) {
        this.externalApplicationUrl = externalApplicationUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyEmailId() {
        return companyEmailId;
    }

    public void setCompanyEmailId(String companyEmailId) {
        this.companyEmailId = companyEmailId;
    }

    public String getCompensationCurrency() {
        return compensationCurrency;
    }

    public void setCompensationCurrency(String compensationCurrency) {
        this.compensationCurrency = compensationCurrency;
    }

    public String getJobTypeDetails() {
        return jobTypeDetails;
    }

    public void setJobTypeDetails(String jobTypeDetails) {
        this.jobTypeDetails = jobTypeDetails;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }
}
