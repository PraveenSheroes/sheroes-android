package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by priyanka on 19/03/17.
 */

public class PersonalBasicDetailsRequest {

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
    @Expose(deserialize = false)
    private String fullName;

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("city_master_id")
    @Expose
    private int cityMasterId;

    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("dob")
    @Expose
    private String dateOfBirth;

    @SerializedName("mobile")
    @Expose
    private String mobileNumber;

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

    public int getCityMasterId() {
        return cityMasterId;
    }

    public void setCityMasterId(int cityMasterId) {
        this.cityMasterId = cityMasterId;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
