package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by sheroes on 29/03/17.
 */
@Parcel(analyze = {UserDetails.class})
public class UserDetails{
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
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("dob")
    @Expose
    private Long dob;
    @SerializedName("personalBios")
    @Expose
    private String personalBios;
    @SerializedName("interest")
    @Expose
    private String interest;
    @SerializedName("profileWeight")
    @Expose
    private long profileWeight;
    @SerializedName("isCompanyAdmin")
    @Expose
    private boolean isCompanyAdmin;
    @SerializedName("department")
    @Expose
    private String department;
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
    @SerializedName("profileId")
    @Expose
    private long profileId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public long getCityMasterId() {
        return cityMasterId;
    }

    public String getCityMaster() {
        return cityMaster;
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

    public String getLastName() {
        return lastName;
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public Long getDob() {
        return dob;
    }

    public String getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(String numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public String getUserSummary() {
        return userSummary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfChildren() {
        return noOfChildren;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }
}
