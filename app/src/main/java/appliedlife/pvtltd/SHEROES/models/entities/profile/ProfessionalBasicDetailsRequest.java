package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by priyanka on 24/03/17.
 */

public class ProfessionalBasicDetailsRequest {


    @SerializedName("subType")
    @Expose
    private String subType;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("deviceUniqueId")
    @Expose
    private String deviceUniqueId;
    @SerializedName("cloudMessagingId")
    @Expose
    private String cloudMessagingId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("last_screen_name")
    @Expose
    private String lastScreenName;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("no_Of_children")
    @Expose
    private int noOfChildren;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("heighest_qualification")
    @Expose
    private String heighestQualification;
    @SerializedName("heighest_qualification_id")
    @Expose
    private String heighestQualificationId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("job_tag_id")
    @Expose
    private int jobTagId;
    @SerializedName("job_tag")
    @Expose
    private String jobTag;
    @SerializedName("total_exp")
    @Expose
    private int totalExp;
    @SerializedName("city_master_id")
    @Expose
    private int cityMasterId;
    @SerializedName("city_master")
    @Expose
    private String cityMaster;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("sector_id")
    @Expose
    private int sectorId;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("opportunity_type_id")
    @Expose
    private int opportunityTypeId;
    @SerializedName("twitter_username")
    @Expose
    private String twitterUsername;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("interest")
    @Expose
    private String interest;
    @SerializedName("home_town")
    @Expose
    private int homeTown;
    @SerializedName("photo_url_path")
    @Expose
    private String photoUrlPath;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceUniqueId() {
        return deviceUniqueId;
    }

    public void setDeviceUniqueId(String deviceUniqueId) {
        this.deviceUniqueId = deviceUniqueId;
    }

    public String getCloudMessagingId() {
        return cloudMessagingId;
    }

    public void setCloudMessagingId(String cloudMessagingId) {
        this.cloudMessagingId = cloudMessagingId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getLastScreenName() {
        return lastScreenName;
    }

    public void setLastScreenName(String lastScreenName) {
        this.lastScreenName = lastScreenName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeighestQualification() {
        return heighestQualification;
    }

    public void setHeighestQualification(String heighestQualification) {
        this.heighestQualification = heighestQualification;
    }

    public String getHeighestQualificationId() {
        return heighestQualificationId;
    }

    public void setHeighestQualificationId(String heighestQualificationId) {
        this.heighestQualificationId = heighestQualificationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getJobTagId() {
        return jobTagId;
    }

    public void setJobTagId(int jobTagId) {
        this.jobTagId = jobTagId;
    }

    public String getJobTag() {
        return jobTag;
    }

    public void setJobTag(String jobTag) {
        this.jobTag = jobTag;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getCityMasterId() {
        return cityMasterId;
    }

    public void setCityMasterId(int cityMasterId) {
        this.cityMasterId = cityMasterId;
    }

    public String getCityMaster() {
        return cityMaster;
    }

    public void setCityMaster(String cityMaster) {
        this.cityMaster = cityMaster;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public int getSectorId() {
        return sectorId;
    }

    public void setSectorId(int sectorId) {
        this.sectorId = sectorId;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getOpportunityTypeId() {
        return opportunityTypeId;
    }

    public void setOpportunityTypeId(int opportunityTypeId) {
        this.opportunityTypeId = opportunityTypeId;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public int getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(int homeTown) {
        this.homeTown = homeTown;
    }

    public String getPhotoUrlPath() {
        return photoUrlPath;
    }

    public void setPhotoUrlPath(String photoUrlPath) {
        this.photoUrlPath = photoUrlPath;
    }



}
