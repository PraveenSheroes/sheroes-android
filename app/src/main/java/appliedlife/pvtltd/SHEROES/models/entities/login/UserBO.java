package appliedlife.pvtltd.SHEROES.models.entities.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


/**
 * @author amleshsinha
 */
@Parcel(analyze = {UserBO.class})
public class UserBO {

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
    @SerializedName("emailId")
    @Expose
    private String emailId;
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
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("numberOfFollowers")
    @Expose
    private String numberOfFollowers;
    @SerializedName("userSummary")
    @Expose
    private String userSummary;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("noOfChildren")
    @Expose
    private int noOfChildren;

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
     * @return the userTypeId
     */
    public int getUserTypeId() {
        return userTypeId;
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
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
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
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
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
     * @return the gender
     */
    public String getGender() {
        return gender;
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
}
