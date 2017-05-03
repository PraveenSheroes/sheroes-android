package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;

/**
 * Created by sheroes on 29/03/17.
 */
public class UserDetails implements Parcelable {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("participantId")
    @Expose
    private long participantId;
    @SerializedName("userTypeId")
    @Expose
    private int userTypeId;
    @SerializedName("cityMasterId")
    @Expose
    private long cityMasterId;
    @SerializedName("cityMaster")
    @Expose
    private String cityMaster;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("crdt")
    @Expose
    private String crdt;
    @SerializedName("lastLogin")
    @Expose
    private String lastLogin;
    @SerializedName("lastModifiedOn")
    @Expose
    private String lastModifiedOn;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("photoUrlPath")
    @Expose
    private String photoUrlPath;
    @SerializedName("jobFunctionId")
    @Expose
    private long jobFunctionId;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("sectorId")
    @Expose
    private long sectorId;
    @SerializedName("jobTitle")
    @Expose
    private String jobTitle;
    @SerializedName("employmentTypeId")
    @Expose
    private long employmentTypeId;
    @SerializedName("totalExp")
    @Expose
    private int totalExp;

    @SerializedName("totalExpMonth")
    @Expose
    private int totalExpMonth;

    @SerializedName("personalBios")
    @Expose
    private String personalBios;
    @SerializedName("interest")
    @Expose
    private String interest;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("opportunityTypeId")
    @Expose
    private long opportunityTypeId;
    @SerializedName("jobTagId")
    @Expose
    private long jobTagId;
    @SerializedName("companyProfileid")
    @Expose
    private long companyProfileid;
    @SerializedName("profileWeight")
    @Expose
    private long profileWeight;
    @SerializedName("isEmailVerified")
    @Expose
    private boolean isEmailVerified;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("isCompanyAdmin")
    @Expose
    private boolean isCompanyAdmin;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("collage")
    @Expose
    private String collage;
    @SerializedName("userJobView_count")
    @Expose
    private int userJobViewCount;
    @SerializedName("userJobApplyCount")
    @Expose
    private int userJobApplyCount;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("latitude")
    @Expose
    private double latitude;
    @SerializedName("longitude")
    @Expose
    private double longitude;
    @SerializedName("numberOfFollowers")
    @Expose
    private String numberOfFollowers;
    @SerializedName("currentCtc")
    @Expose
    private int currentCtc;
    @SerializedName("userSummary")
    @Expose
    private String userSummary;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("twitterUsername")
    @Expose
    private String twitterUsername;
    @SerializedName("noOfChildren")
    @Expose
    private int noOfChildren;
    @SerializedName("profileId")
    @Expose
    private long profileId;
    @SerializedName("tag")
    @Expose
    private String tag;

    @SerializedName("skills")
    @Expose
    private List<LabelValue> skills;

    @SerializedName("interests")
    @Expose
    private List<LabelValue> interests;

    @SerializedName("opportunityTypes")
    @Expose
    private List<LabelValue> opportunityTypes;

    @SerializedName("language")
    @Expose
    private List<LabelValue> language;

    @SerializedName("canHelpIns")
    @Expose
    private List<LabelValue> canHelpIns;

    @SerializedName("sector")
    @Expose
    private String sector;

    private String employmentType;

    private String jobFunction;

    @SerializedName("jobTag")
    @Expose
    private String jobTag;

    private int travelFlexibility;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(long participantId) {
        this.participantId = participantId;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

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

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCrdt() {
        return crdt;
    }

    public void setCrdt(String crdt) {
        this.crdt = crdt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPhotoUrlPath() {
        return photoUrlPath;
    }

    public void setPhotoUrlPath(String photoUrlPath) {
        this.photoUrlPath = photoUrlPath;
    }

    public long getJobFunctionId() {
        return jobFunctionId;
    }

    public void setJobFunctionId(long jobFunctionId) {
        this.jobFunctionId = jobFunctionId;
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

    public long getSectorId() {
        return sectorId;
    }

    public void setSectorId(long sectorId) {
        this.sectorId = sectorId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public long getEmploymentTypeId() {
        return employmentTypeId;
    }

    public void setEmploymentTypeId(long employmentTypeId) {
        this.employmentTypeId = employmentTypeId;
    }

    public int getTotalExp() {
        return totalExp;
    }

    public void setTotalExp(int totalExp) {
        this.totalExp = totalExp;
    }

    public int getTotalExpMonth() {
        return totalExpMonth;
    }

    public void setTotalExpMonth(int totalExpMonth) {
        this.totalExpMonth = totalExpMonth;
    }

    public String getPersonalBios() {
        return personalBios;
    }

    public void setPersonalBios(String personalBios) {
        this.personalBios = personalBios;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public long getOpportunityTypeId() {
        return opportunityTypeId;
    }

    public void setOpportunityTypeId(long opportunityTypeId) {
        this.opportunityTypeId = opportunityTypeId;
    }

    public long getJobTagId() {
        return jobTagId;
    }

    public void setJobTagId(long jobTagId) {
        this.jobTagId = jobTagId;
    }

    public long getCompanyProfileid() {
        return companyProfileid;
    }

    public void setCompanyProfileid(long companyProfileid) {
        this.companyProfileid = companyProfileid;
    }

    public long getProfileWeight() {
        return profileWeight;
    }

    public void setProfileWeight(long profileWeight) {
        this.profileWeight = profileWeight;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isCompanyAdmin() {
        return isCompanyAdmin;
    }

    public void setCompanyAdmin(boolean companyAdmin) {
        isCompanyAdmin = companyAdmin;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCollage() {
        return collage;
    }

    public void setCollage(String collage) {
        this.collage = collage;
    }

    public int getUserJobViewCount() {
        return userJobViewCount;
    }

    public void setUserJobViewCount(int userJobViewCount) {
        this.userJobViewCount = userJobViewCount;
    }

    public int getUserJobApplyCount() {
        return userJobApplyCount;
    }

    public void setUserJobApplyCount(int userJobApplyCount) {
        this.userJobApplyCount = userJobApplyCount;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(String numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public int getCurrentCtc() {
        return currentCtc;
    }

    public void setCurrentCtc(int currentCtc) {
        this.currentCtc = currentCtc;
    }

    public String getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(String userSummary) {
        this.userSummary = userSummary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public int getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<LabelValue> getSkills() {
        return skills;
    }

    public void setSkills(List<LabelValue> skills) {
        this.skills = skills;
    }

    public List<LabelValue> getInterests() {
        return interests;
    }

    public void setInterests(List<LabelValue> interests) {
        this.interests = interests;
    }

    public List<LabelValue> getOpportunityTypes() {
        return opportunityTypes;
    }

    public void setOpportunityTypes(List<LabelValue> opportunityTypes) {
        this.opportunityTypes = opportunityTypes;
    }

    public List<LabelValue> getLanguage() {
        return language;
    }

    public void setLanguage(List<LabelValue> language) {
        this.language = language;
    }

    public List<LabelValue> getCanHelpIns() {
        return canHelpIns;
    }

    public void setCanHelpIns(List<LabelValue> canHelpIns) {
        this.canHelpIns = canHelpIns;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getJobFunction() {
        return jobFunction;
    }

    public void setJobFunction(String jobFunction) {
        this.jobFunction = jobFunction;
    }

    public String getJobTag() {
        return jobTag;
    }

    public void setJobTag(String jobTag) {
        this.jobTag = jobTag;
    }

    public int getTravelFlexibility() {
        return travelFlexibility;
    }

    public void setTravelFlexibility(int travelFlexibility) {
        this.travelFlexibility = travelFlexibility;
    }

    public int getClientSideVisitPreference() {
        return clientSideVisitPreference;
    }

    public void setClientSideVisitPreference(int clientSideVisitPreference) {
        this.clientSideVisitPreference = clientSideVisitPreference;
    }

    private int clientSideVisitPreference;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.participantId);
        dest.writeInt(this.userTypeId);
        dest.writeLong(this.cityMasterId);
        dest.writeString(this.cityMaster);
        dest.writeString(this.emailid);
        dest.writeString(this.mobile);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.address);
        dest.writeString(this.crdt);
        dest.writeString(this.lastLogin);
        dest.writeString(this.lastModifiedOn);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.photoUrlPath);
        dest.writeLong(this.jobFunctionId);
        dest.writeString(this.maritalStatus);
        dest.writeString(this.dob);
        dest.writeLong(this.sectorId);
        dest.writeString(this.jobTitle);
        dest.writeLong(this.employmentTypeId);
        dest.writeInt(this.totalExp);
        dest.writeInt(this.totalExpMonth);
        dest.writeString(this.personalBios);
        dest.writeString(this.interest);
        dest.writeString(this.industry);
        dest.writeLong(this.opportunityTypeId);
        dest.writeLong(this.jobTagId);
        dest.writeLong(this.companyProfileid);
        dest.writeLong(this.profileWeight);
        dest.writeByte(this.isEmailVerified ? (byte) 1 : (byte) 0);
        dest.writeString(this.gender);
        dest.writeByte(this.isCompanyAdmin ? (byte) 1 : (byte) 0);
        dest.writeString(this.department);
        dest.writeString(this.year);
        dest.writeString(this.collage);
        dest.writeInt(this.userJobViewCount);
        dest.writeInt(this.userJobApplyCount);
        dest.writeString(this.pincode);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.numberOfFollowers);
        dest.writeInt(this.currentCtc);
        dest.writeString(this.userSummary);
        dest.writeString(this.name);
        dest.writeString(this.twitterUsername);
        dest.writeInt(this.noOfChildren);
        dest.writeLong(this.profileId);
        dest.writeString(this.tag);
        dest.writeTypedList(this.skills);
        dest.writeTypedList(this.interests);
        dest.writeTypedList(this.opportunityTypes);
        dest.writeTypedList(this.language);
        dest.writeTypedList(this.canHelpIns);
        dest.writeString(this.sector);
        dest.writeString(this.employmentType);
        dest.writeString(this.jobFunction);
        dest.writeString(this.jobTag);
        dest.writeInt(this.travelFlexibility);
        dest.writeInt(this.clientSideVisitPreference);
    }

    public UserDetails() {
    }

    protected UserDetails(Parcel in) {
        this.id = in.readLong();
        this.participantId = in.readLong();
        this.userTypeId = in.readInt();
        this.cityMasterId = in.readLong();
        this.cityMaster = in.readString();
        this.emailid = in.readString();
        this.mobile = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.address = in.readString();
        this.crdt = in.readString();
        this.lastLogin = in.readString();
        this.lastModifiedOn = in.readString();
        this.isActive = in.readByte() != 0;
        this.photoUrlPath = in.readString();
        this.jobFunctionId = in.readLong();
        this.maritalStatus = in.readString();
        this.dob = in.readString();
        this.sectorId = in.readLong();
        this.jobTitle = in.readString();
        this.employmentTypeId = in.readLong();
        this.totalExp = in.readInt();
        this.totalExpMonth = in.readInt();
        this.personalBios = in.readString();
        this.interest = in.readString();
        this.industry = in.readString();
        this.opportunityTypeId = in.readLong();
        this.jobTagId = in.readLong();
        this.companyProfileid = in.readLong();
        this.profileWeight = in.readLong();
        this.isEmailVerified = in.readByte() != 0;
        this.gender = in.readString();
        this.isCompanyAdmin = in.readByte() != 0;
        this.department = in.readString();
        this.year = in.readString();
        this.collage = in.readString();
        this.userJobViewCount = in.readInt();
        this.userJobApplyCount = in.readInt();
        this.pincode = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.numberOfFollowers = in.readString();
        this.currentCtc = in.readInt();
        this.userSummary = in.readString();
        this.name = in.readString();
        this.twitterUsername = in.readString();
        this.noOfChildren = in.readInt();
        this.profileId = in.readLong();
        this.tag = in.readString();
        this.skills = in.createTypedArrayList(LabelValue.CREATOR);
        this.interests = in.createTypedArrayList(LabelValue.CREATOR);
        this.opportunityTypes = in.createTypedArrayList(LabelValue.CREATOR);
        this.language = in.createTypedArrayList(LabelValue.CREATOR);
        this.canHelpIns = in.createTypedArrayList(LabelValue.CREATOR);
        this.sector = in.readString();
        this.employmentType = in.readString();
        this.jobFunction = in.readString();
        this.jobTag = in.readString();
        this.travelFlexibility = in.readInt();
        this.clientSideVisitPreference = in.readInt();
    }

    public static final Parcelable.Creator<UserDetails> CREATOR = new Parcelable.Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };
}
