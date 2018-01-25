package appliedlife.pvtltd.SHEROES.models.entities.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title:User summary
 */
@Parcel(analyze = {UserSummary.class})
public class UserSummary{

    @SerializedName("user_id")
    @Expose
    private long userId;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    @SerializedName("user_details")
    @Expose
    private  UserBO userBO;

    @SerializedName("educations")
    @Expose
    private  List<EducationEntityBO> educationBO;

    @SerializedName("experiences")
    @Expose
    private  List<ExprienceEntityBO> exprienceBO;

    @SerializedName("projects")
    @Expose
    private  List<ProjectEntityBO> projectsBO;

    @SerializedName("fb_verification_required")
    @Expose
    private boolean fbVerificationRequired;

    @SerializedName("user_app_invite_url")
    private String appShareUrl;

    public String getAppShareUrl() {
        return appShareUrl;
    }

    public void setAppShareUrl(String appShareUrl) {
        this.appShareUrl = appShareUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserSummary() {
    }

    public UserBO getUserBO() {
        return userBO;
    }

    public void setUserBO(UserBO userBO) {
        this.userBO = userBO;
    }

    public List<EducationEntityBO> getEducationBO() {
        return educationBO;
    }

    public void setEducationBO(List<EducationEntityBO> educationBO) {
        this.educationBO = educationBO;
    }

    public List<ExprienceEntityBO> getExprienceBO() {
        return exprienceBO;
    }

    public void setExprienceBO(List<ExprienceEntityBO> exprienceBO) {
        this.exprienceBO = exprienceBO;
    }

    public List<ProjectEntityBO> getProjectsBO() {
        return projectsBO;
    }

    public void setProjectsBO(List<ProjectEntityBO> projectsBO) {
        this.projectsBO = projectsBO;
    }

    public boolean isFbVerificationRequired() {
        return fbVerificationRequired;
    }

    public void setFbVerificationRequired(boolean fbVerificationRequired) {
        this.fbVerificationRequired = fbVerificationRequired;
    }
}
