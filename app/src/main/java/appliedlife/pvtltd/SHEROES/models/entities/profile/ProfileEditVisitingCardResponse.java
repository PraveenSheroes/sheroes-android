package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by priyanka on 27/03/17.
 */

public class ProfileEditVisitingCardResponse  extends BaseResponse{
    @SerializedName("aboutMe")
    @Expose
    private String aboutMe;
    @SerializedName("currentCompany")
    @Expose
    private String currentCompany;
    @SerializedName("currentDesignation")
    @Expose
    private String currentDesignation;
    @SerializedName("currentLocation")
    @Expose
    private String currentLocation;
    @SerializedName("emailid")
    @Expose
    private String emailid;

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("heighestDegree")
    @Expose
    private String heighestDegree;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("school")
    @Expose
    private String school;

    @SerializedName("url")
    @Expose
    private String url;

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
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
    public String getEmailid() {
        return emailid;
    }
    public void setEmailid(String emailid) {
        this.emailid = emailid;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
