package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by sheroes on 29/03/17.
 */
public class ExprienceEntity extends BaseResponse {
    int itemPosition;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("experience_type")
    @Expose
    private long experienceType=1;
    @SerializedName("experience_type_string")
    @Expose
    private String experienceTypeString;
    @SerializedName("organisation_type")
    @Expose
    private long organisationType;
    @SerializedName("organisation_type_string")
    @Expose
    private String organisationTypeString;
    @SerializedName("sector")
    @Expose
    private long sector;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("location_id")
    @Expose
    private long locationId;
    @SerializedName("location")
    @Expose
    private String location;
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
    @SerializedName("mob_app_url")
    @Expose
    private String mobAppUrl;
    @SerializedName("is_active")
    @Expose
    private boolean isActive=true;
    @SerializedName("tag")
    @Expose
    private String tag;

    @SerializedName("tag1")
    @Expose
    private String tag1;

    @SerializedName("title1")
    @Expose
    private String title1;
    @SerializedName("start_day")
    @Expose
    private Integer startDay;
    @SerializedName("end_day")
    @Expose
    private Integer endDay;
    @SerializedName("type")
    @Expose
    private String type="EXPERIENCE";
    @SerializedName("subType")
    @Expose
    private String subType="EXPERIENCE_SERVICE";

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getExperienceType() {
        return experienceType;
    }

    public void setExperienceType(long experienceType) {
        this.experienceType = experienceType;
    }

    public String getExperienceTypeString() {
        return experienceTypeString;
    }

    public void setExperienceTypeString(String experienceTypeString) {
        this.experienceTypeString = experienceTypeString;
    }

    public long getOrganisationType() {
        return organisationType;
    }

    public void setOrganisationType(long organisationType) {
        this.organisationType = organisationType;
    }

    public String getOrganisationTypeString() {
        return organisationTypeString;
    }

    public void setOrganisationTypeString(String organisationTypeString) {
        this.organisationTypeString = organisationTypeString;
    }

    public long getSector() {
        return sector;
    }

    public void setSector(long sector) {
        this.sector = sector;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public void setCurrentlyWorkingHere(boolean currentlyWorkingHere) {
        isCurrentlyWorkingHere = currentlyWorkingHere;
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

    public String getMobAppUrl() {
        return mobAppUrl;
    }

    public void setMobAppUrl(String mobAppUrl) {
        this.mobAppUrl = mobAppUrl;
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
        this.tag1 = tag1;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.itemPosition);
        dest.writeValue(this.id);
        dest.writeLong(this.experienceType);
        dest.writeString(this.experienceTypeString);
        dest.writeLong(this.organisationType);
        dest.writeString(this.organisationTypeString);
        dest.writeLong(this.sector);
        dest.writeString(this.company);
        dest.writeLong(this.locationId);
        dest.writeString(this.location);
        dest.writeString(this.title);
        dest.writeInt(this.startMonth);
        dest.writeInt(this.startYear);
        dest.writeInt(this.endMonth);
        dest.writeInt(this.endYear);
        dest.writeString(this.description);
        dest.writeByte(this.isCurrentlyWorkingHere ? (byte) 1 : (byte) 0);
        dest.writeLong(this.functionalAreaId);
        dest.writeString(this.functionalAreaName);
        dest.writeString(this.orgBrandName);
        dest.writeString(this.aboutOrg);
        dest.writeString(this.orgWebUrl);
        dest.writeString(this.orgContactNo);
        dest.writeString(this.portfolioUrl);
        dest.writeString(this.imageTitle);
        dest.writeString(this.imageUrl);
        dest.writeString(this.mobAppUrl);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.tag);
        dest.writeString(this.tag1);
        dest.writeString(this.title1);
        dest.writeValue(this.startDay);
        dest.writeValue(this.endDay);
        dest.writeString(this.type);
        dest.writeString(this.subType);
    }

    public ExprienceEntity() {
    }

    protected ExprienceEntity(Parcel in) {
        super(in);
        this.itemPosition = in.readInt();
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.experienceType = in.readLong();
        this.experienceTypeString = in.readString();
        this.organisationType = in.readLong();
        this.organisationTypeString = in.readString();
        this.sector = in.readLong();
        this.company = in.readString();
        this.locationId = in.readLong();
        this.location = in.readString();
        this.title = in.readString();
        this.startMonth = in.readInt();
        this.startYear = in.readInt();
        this.endMonth = in.readInt();
        this.endYear = in.readInt();
        this.description = in.readString();
        this.isCurrentlyWorkingHere = in.readByte() != 0;
        this.functionalAreaId = in.readLong();
        this.functionalAreaName = in.readString();
        this.orgBrandName = in.readString();
        this.aboutOrg = in.readString();
        this.orgWebUrl = in.readString();
        this.orgContactNo = in.readString();
        this.portfolioUrl = in.readString();
        this.imageTitle = in.readString();
        this.imageUrl = in.readString();
        this.mobAppUrl = in.readString();
        this.isActive = in.readByte() != 0;
        this.tag = in.readString();
        this.tag1 = in.readString();
        this.title1 = in.readString();
        this.startDay = (Integer) in.readValue(Integer.class.getClassLoader());
        this.endDay = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
        this.subType = in.readString();
    }

    public static final Creator<ExprienceEntity> CREATOR = new Creator<ExprienceEntity>() {
        @Override
        public ExprienceEntity createFromParcel(Parcel source) {
            return new ExprienceEntity(source);
        }

        @Override
        public ExprienceEntity[] newArray(int size) {
            return new ExprienceEntity[size];
        }
    };
}
