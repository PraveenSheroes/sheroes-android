package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by priyanka on 28/03/17.
 */

public class GetUserVisitingCardRequest {

    @SerializedName("about_me")
    @Expose
    private String aboutMe;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("cloudMessagingId")
    @Expose
    private String cloudMessagingId;
    @SerializedName("current_company")
    @Expose
    private String currentCompany;
    @SerializedName("current_designation")
    @Expose
    private String currentDesignation;
    @SerializedName("current_location")
    @Expose
    private String currentLocation;
    @SerializedName("deviceUniqueId")
    @Expose
    private String deviceUniqueId;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("heighest_degree")
    @Expose
    private String heighestDegree;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("last_screen_name")
    @Expose
    private String lastScreenName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("screen_name")
    @Expose
    private String screenName;
    @SerializedName("source")
    @Expose
    private String source;

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCloudMessagingId() {
        return cloudMessagingId;
    }

    public void setCloudMessagingId(String cloudMessagingId) {

        this.cloudMessagingId = cloudMessagingId;
    }

    public String getCurrentCompany() {

        return currentCompany;
    }

    public void setCurrentCompany(String currentCompany) {

        this.currentCompany = currentCompany;
    }

    public String getCurrentDesignation() {

        return currentDesignation;
    }

    public void setCurrentDesignation(String currentDesignation) {

        this.currentDesignation = currentDesignation;
    }

    public String getCurrentLocation() {

        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getDeviceUniqueId() {
        return deviceUniqueId;
    }

    public void setDeviceUniqueId(String deviceUniqueId) {
        this.deviceUniqueId = deviceUniqueId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHeighestDegree() {
        return heighestDegree;
    }

    public void setHeighestDegree(String heighestDegree) {
        this.heighestDegree = heighestDegree;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastScreenName() {
        return lastScreenName;
    }

    public void setLastScreenName(String lastScreenName) {
        this.lastScreenName = lastScreenName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
