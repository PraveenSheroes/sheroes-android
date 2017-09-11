/**
 * 
 */
package appliedlife.pvtltd.SHEROES.models.entities.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;


/**
 * @author amleshsinha
 *
 */
public class UserBO implements Parcelable {

	@SerializedName("homeTown")
	@Expose
	private String homeTown;
	@SerializedName("homeTownId")
	@Expose
	private long homeTownId;
	@SerializedName("languageId")
	@Expose
	private long languageId;
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
	@SerializedName("userJobViewCount")
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

	@SerializedName("proficiencyLevel")
	@Expose
	private String proficiencyLevel;
	@SerializedName("skills")
	@Expose
	private List<LabelValue> skills;
	@SerializedName("interests")
	@Expose
	private List<LabelValue> interests;
	@SerializedName("opportunityTypes")
	@Expose
	private List<LabelValue> opportunityTypes;
	@SerializedName("canHelpIns")
	@Expose
	private List<LabelValue> canHelpIns;
	@SerializedName("sector")
	@Expose
	private String sector;
	@SerializedName("employmentType")
	@Expose
	private String employmentType;
	@SerializedName("jobFunction")
	@Expose
	private String jobFunction;
	@SerializedName("jobTag")
	@Expose
	private String jobTag;
	@SerializedName("travelFlexibility")
	@Expose
	private int travelFlexibility;
	@SerializedName("clientSideVisitPreference")
	@Expose
	private int clientSideVisitPreference;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the participantId
	 */
	public long getParticipantId() {
		return participantId;
	}
	/**
	 * @param participantId the participantId to set
	 */
	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}
	/**
	 * @return the userTypeId
	 */
	public int getUserTypeId() {
		return userTypeId;
	}
	/**
	 * @param userTypeId the userTypeId to set
	 */
	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
	/**
	 * @return the cityMasterId
	 */
	public long getCityMasterId() {
		return cityMasterId;
	}
	/**
	 * @param cityMasterId the cityMasterId to set
	 */
	public void setCityMasterId(long cityMasterId) {
		this.cityMasterId = cityMasterId;
	}
	/**
	 * @return the emailid
	 */
	public String getEmailid() {
		return emailid;
	}
	/**
	 * @param emailid the emailid to set
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the crdt
	 */
	public String getCrdt() {
		return crdt;
	}
	/**
	 * @param crdt the crdt to set
	 */
	public void setCrdt(String crdt) {
		this.crdt = crdt;
	}
	/**
	 * @return the lastLogin
	 */
	public String getLastLogin() {
		return lastLogin;
	}
	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	/**
	 * @return the lastModifiedOn
	 */
	public String getLastModifiedOn() {
		return lastModifiedOn;
	}
	/**
	 * @param lastModifiedOn the lastModifiedOn to set
	 */
	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	/**
	 * @return the isActive
	 */
	public boolean getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	/**
	 * @return the photoUrlPath
	 */
	public String getPhotoUrlPath() {
		return photoUrlPath;
	}
	/**
	 * @param photoUrlPath the photoUrlPath to set
	 */
	public void setPhotoUrlPath(String photoUrlPath) {
		this.photoUrlPath = photoUrlPath;
	}
	/**
	 * @return the jobFunctionId
	 */
	public long getJobFunctionId() {
		return jobFunctionId;
	}
	/**
	 * @param jobFunctionId the jobFunctionId to set
	 */
	public void setJobFunctionId(long jobFunctionId) {
		this.jobFunctionId = jobFunctionId;
	}
	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}
	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}
	/**
	 * @return the sectorId
	 */
	public long getSectorId() {
		return sectorId;
	}
	/**
	 * @param sectorId the sectorId to set
	 */
	public void setSectorId(long sectorId) {
		this.sectorId = sectorId;
	}
	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}
	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * @return the employmentTypeId
	 */
	public long getEmploymentTypeId() {
		return employmentTypeId;
	}
	/**
	 * @param employmentTypeId the employmentTypeId to set
	 */
	public void setEmploymentTypeId(long employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}
	/**
	 * @return the totalExp
	 */
	public int getTotalExp() {
		return totalExp;
	}
	/**
	 * @param totalExp the totalExp to set
	 */
	public void setTotalExp(int totalExp) {
		this.totalExp = totalExp;
	}
	/**
	 * @return the personalBios
	 */
	public String getPersonalBios() {
		return personalBios;
	}
	/**
	 * @param personalBios the personalBios to set
	 */
	public void setPersonalBios(String personalBios) {
		this.personalBios = personalBios;
	}
	/**
	 * @return the interest
	 */
	public String getInterest() {
		return interest;
	}
	/**
	 * @param interest the interest to set
	 */
	public void setInterest(String interest) {
		this.interest = interest;
	}
	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}
	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	/**
	 * @return the opportunityTypeId
	 */
	public long getOpportunityTypeId() {
		return opportunityTypeId;
	}
	/**
	 * @param opportunityTypeId the opportunityTypeId to set
	 */
	public void setOpportunityTypeId(long opportunityTypeId) {
		this.opportunityTypeId = opportunityTypeId;
	}
	/**
	 * @return the jobTagId
	 */
	public long getJobTagId() {
		return jobTagId;
	}
	/**
	 * @param jobTagId the jobTagId to set
	 */
	public void setJobTagId(long jobTagId) {
		this.jobTagId = jobTagId;
	}
	/**
	 * @return the companyProfileid
	 */
	public long getCompanyProfileid() {
		return companyProfileid;
	}
	/**
	 * @param companyProfileid the companyProfileid to set
	 */
	public void setCompanyProfileid(long companyProfileid) {
		this.companyProfileid = companyProfileid;
	}
	/**
	 * @return the profileWeight
	 */
	public long getProfileWeight() {
		return profileWeight;
	}
	/**
	 * @param profileWeight the profileWeight to set
	 */
	public void setProfileWeight(long profileWeight) {
		this.profileWeight = profileWeight;
	}

	/**
	 * @return the isEmailVerified
	 */
	public boolean getIsEmailVerified() {
		return isEmailVerified;
	}
	/**
	 * @param isEmailVerified the isEmailVerified to set
	 */
	public void setIsEmailVerified(boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the isCompanyAdmin
	 */
	public boolean getIsCompanyAdmin() {
		return isCompanyAdmin;
	}
	/**
	 * @param isCompanyAdmin the isCompanyAdmin to set
	 */
	public void setIsCompanyAdmin(boolean isCompanyAdmin) {
		this.isCompanyAdmin = isCompanyAdmin;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the collage
	 */
	public String getCollage() {
		return collage;
	}
	/**
	 * @param collage the collage to set
	 */
	public void setCollage(String collage) {
		this.collage = collage;
	}
	/**
	 * @return the userJobViewCount
	 */
	public int getUserJobViewCount() {
		return userJobViewCount;
	}
	/**
	 * @param userJobViewCount the userJobViewCount to set
	 */
	public void setUserJobViewCount(int userJobViewCount) {
		this.userJobViewCount = userJobViewCount;
	}
	/**
	 * @return the userJobApplyCount
	 */
	public int getUserJobApplyCount() {
		return userJobApplyCount;
	}
	/**
	 * @param userJobApplyCount the userJobApplyCount to set
	 */
	public void setUserJobApplyCount(int userJobApplyCount) {
		this.userJobApplyCount = userJobApplyCount;
	}

	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getlongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setlongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the numberOfFollowers
	 */
	public String getNumberOfFollowers() {
		return numberOfFollowers;
	}
	/**
	 * @param numberOfFollowers the numberOfFollowers to set
	 */
	public void setNumberOfFollowers(String numberOfFollowers) {
		this.numberOfFollowers = numberOfFollowers;
	}
	/**
	 * @return the currentCtc
	 */
	public int getCurrentCtc() {
		return currentCtc;
	}
	/**
	 * @param currentCtc the currentCtc to set
	 */
	public void setCurrentCtc(int currentCtc) {
		this.currentCtc = currentCtc;
	}
	/**
	 * @return the profileId
	 */
	public long getProfileId() {
		return profileId;
	}
	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	/**
	 * @return the skills
	 */
	public List<LabelValue> getSkills() {
		return skills;
	}
	/**
	 * @param skills the skills to set
	 */
	public void setSkills(List<LabelValue> skills) {
		this.skills = skills;
	}
	/**
	 * @return the interests
	 */
	public List<LabelValue> getInterests() {
		return interests;
	}
	/**
	 * @param interests the interests to set
	 */
	public void setInterests(List<LabelValue> interests) {
		this.interests = interests;
	}
	/**
	 * @return the opportunityTypes
	 */
	public List<LabelValue> getOpportunityTypes() {
		return opportunityTypes;
	}
	/**
	 * @param opportunityTypes the opportunityTypes to set
	 */
	public void setOpportunityTypes(List<LabelValue> opportunityTypes) {
		this.opportunityTypes = opportunityTypes;
	}
	/**
	 * @return the canHelpIns
	 */
	public List<LabelValue> getCanHelpIns() {
		return canHelpIns;
	}
	/**
	 * @param canHelpIns the canHelpIns to set
	 */
	public void setCanHelpIns(List<LabelValue> canHelpIns) {
		this.canHelpIns = canHelpIns;
	}
	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}
	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		return employmentType;
	}
	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	/**
	 * @return the jobFunction
	 */
	public String getJobFunction() {
		return jobFunction;
	}
	/**
	 * @param jobFunction the jobFunction to set
	 */
	public void setJobFunction(String jobFunction) {
		this.jobFunction = jobFunction;
	}
	/**
	 * @return the jobTag
	 */
	public String getJobTag() {
		return jobTag;
	}
	/**
	 * @param jobTag the jobTag to set
	 */
	public void setJobTag(String jobTag) {
		this.jobTag = jobTag;
	}
	/**
	 * @return the userSummary
	 */
	public String getUserSummary() {
		return userSummary;
	}
	/**
	 * @param userSummary the userSummary to set
	 */
	public void setUserSummary(String userSummary) {
		this.userSummary = userSummary;
	}
	/**
<<<<<<< Updated upstream
<<<<<<< Updated upstream
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the travelFlexibility
	 */
	public int getTravelFlexibility() {
		return travelFlexibility;
	}
	/**
	 * @param travelFlexibility the travelFlexibility to set
	 */
	public void setTravelFlexibility(int travelFlexibility) {
		this.travelFlexibility = travelFlexibility;
	}
	/**
	 * @return the clientSideVisitPreference
	 */
	public int getClientSideVisitPreference() {
		return clientSideVisitPreference;
	}
	/**
	 * @param clientSideVisitPreference the clientSideVisitPreference to set
	 */
	public void setClientSideVisitPreference(int clientSideVisitPreference) {
		this.clientSideVisitPreference = clientSideVisitPreference;
	}
	/**
	 * @return the noOfChildren
	 */
	public int getNoOfChildren() {
		return noOfChildren;
	}
	/**
	 * @param noOfChildren the noOfChildren to set
	 */
	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}
	/**
	 * @return the cityMaster
	 */
	public String getCityMaster() {
		return cityMaster;
	}
	/**
	 * @param cityMaster the cityMaster to set
	 */
	public void setCityMaster(String cityMaster) {
		this.cityMaster = cityMaster;
	}
	/**
	 * @return the twitterUsername
	 */
	public String getTwitterUsername() {
		return twitterUsername;
	}
	/**
	 * @param twitterUsername the twitterUsername to set
	 */
	public void setTwitterUsername(String twitterUsername) {
		this.twitterUsername = twitterUsername;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		isEmailVerified = emailVerified;
	}

	public boolean isCompanyAdmin() {
		return isCompanyAdmin;
	}

	public void setCompanyAdmin(boolean companyAdmin) {
		isCompanyAdmin = companyAdmin;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public UserBO() {
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public long getHomeTownId() {
		return homeTownId;
	}

	public void setHomeTownId(long homeTownId) {
		this.homeTownId = homeTownId;
	}

	public long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(long languageId) {
		this.languageId = languageId;
	}

	public String getProficiencyLevel() {
		return proficiencyLevel;
	}

	public void setProficiencyLevel(String proficiencyLevel) {
		this.proficiencyLevel = proficiencyLevel;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.homeTown);
		dest.writeLong(this.homeTownId);
		dest.writeLong(this.languageId);
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
		dest.writeString(this.proficiencyLevel);
		dest.writeTypedList(this.skills);
		dest.writeTypedList(this.interests);
		dest.writeTypedList(this.opportunityTypes);
		dest.writeTypedList(this.canHelpIns);
		dest.writeString(this.sector);
		dest.writeString(this.employmentType);
		dest.writeString(this.jobFunction);
		dest.writeString(this.jobTag);
		dest.writeInt(this.travelFlexibility);
		dest.writeInt(this.clientSideVisitPreference);
	}

	protected UserBO(Parcel in) {
		this.homeTown = in.readString();
		this.homeTownId = in.readLong();
		this.languageId = in.readLong();
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
		this.proficiencyLevel = in.readString();
		this.skills = in.createTypedArrayList(LabelValue.CREATOR);
		this.interests = in.createTypedArrayList(LabelValue.CREATOR);
		this.opportunityTypes = in.createTypedArrayList(LabelValue.CREATOR);
		this.canHelpIns = in.createTypedArrayList(LabelValue.CREATOR);
		this.sector = in.readString();
		this.employmentType = in.readString();
		this.jobFunction = in.readString();
		this.jobTag = in.readString();
		this.travelFlexibility = in.readInt();
		this.clientSideVisitPreference = in.readInt();
	}

	public static final Creator<UserBO> CREATOR = new Creator<UserBO>() {
		@Override
		public UserBO createFromParcel(Parcel source) {
			return new UserBO(source);
		}

		@Override
		public UserBO[] newArray(int size) {
			return new UserBO[size];
		}
	};
}
