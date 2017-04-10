package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.ExprienceEntityBO;

/**
 * Created by sheroes on 29/03/17.
 */
public class ExprienceEntity extends BaseResponse {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("experience_type")
    @Expose
    private long experienceType;
    @SerializedName("organisation_type")
    @Expose
    private long organisationType;
    @SerializedName("sector")
    @Expose
    private long sector;
    @SerializedName("company_id")
    @Expose
    private long companyId;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("location_id")
    @Expose
    private long locationId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("start_month")
    @Expose
    private int startMonth;
    @SerializedName("start_year")
    @Expose
    private int startYear;
    @SerializedName("end_month")
    @Expose
    private int endMonth;
    @SerializedName("end_year")
    @Expose
    private int endYear;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_currently_working_here")
    @Expose
    private boolean isCurrentlyWorkingHere;
    @SerializedName("functional_area_id")
    @Expose
    private long functionalAreaId;
    @SerializedName("functional_area_name")
    @Expose
    private String functionalAreaName;
    @SerializedName("org_brand_name")
    @Expose
    private String orgBrandName;
    @SerializedName("about_org")
    @Expose
    private String aboutOrg;
    @SerializedName("org_web_url")
    @Expose
    private String orgWebUrl;
    @SerializedName("org_contact_no")
    @Expose
    private String orgContactNo;
    @SerializedName("portfolio_url")
    @Expose
    private String portfolioUrl;
    @SerializedName("image_title")
    @Expose
    private String imageTitle;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;
    @SerializedName("tag")
    @Expose
    private String tag;

    @SerializedName("tag1")
    @Expose
    private String tag1;

    @SerializedName("title1")
    @Expose
    private String title1;

    private ExprienceEntity exprienceEntity;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getExperienceType() {
        return experienceType;
    }
    public void setExperienceType(long experienceType) {
        this.experienceType = experienceType;
    }
    public long getOrganisationType() {
        return organisationType;
    }
    public void setOrganisationType(long organisationType) {
        this.organisationType = organisationType;
    }
    public long getSector() {
        return sector;
    }
    public void setSector(long sector) {
        this.sector = sector;
    }
    public long getCompanyId() {
        return companyId;
    }
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public long getLocationId() {
        return locationId;
    }
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getStartMonth() {
        return startMonth;
    }
    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }
    public int getStartYear() {
        return startYear;
    }
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    public int getEndMonth() {
        return endMonth;
    }
    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }
    public int getEndYear() {
        return endYear;
    }
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCurrentlyWorkingHere() {
        return isCurrentlyWorkingHere;
    }
    public void setCurrentlyWorkingHere(boolean isCurrentlyWorkingHere) {
        this.isCurrentlyWorkingHere = isCurrentlyWorkingHere;
    }
    public long getFunctionalAreaId() {
        return functionalAreaId;
    }
    public void setFunctionalAreaId(long functionalAreaId) {
        this.functionalAreaId = functionalAreaId;
    }
    public String getFunctionalAreaName() {
        return functionalAreaName;
    }
    public void setFunctionalAreaName(String functionalAreaName) {
        this.functionalAreaName = functionalAreaName;
    }
    public String getOrgBrandName() {
        return orgBrandName;
    }
    public void setOrgBrandName(String orgBrandName) {
        this.orgBrandName = orgBrandName;
    }
    public String getAboutOrg() {
        return aboutOrg;
    }
    public void setAboutOrg(String aboutOrg) {
        this.aboutOrg = aboutOrg;
    }
    public String getOrgWebUrl() {
        return orgWebUrl;
    }
    public void setOrgWebUrl(String orgWebUrl) {
        this.orgWebUrl = orgWebUrl;
    }
    public String getOrgContactNo() {
        return orgContactNo;
    }
    public void setOrgContactNo(String orgContactNo) {
        this.orgContactNo = orgContactNo;
    }
    public String getPortfolioUrl() {
        return portfolioUrl;
    }
    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }
    public String getImageTitle() {
        return imageTitle;
    }
    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag1() {
        return tag1;
    }
    public void setTag1(String tag1) {
        this.tag = tag1;
    }

    public String getTitle1() {
        return title1;
    }
    public void setTitle1(String title1) {
        this.title1 = title1;
    }


    public ExprienceEntity getExprienceEntity() {
        return exprienceEntity;
    }

    public void setExprienceEntity(ExprienceEntity exprienceEntity) {
        this.exprienceEntity = exprienceEntity;
    }
}
