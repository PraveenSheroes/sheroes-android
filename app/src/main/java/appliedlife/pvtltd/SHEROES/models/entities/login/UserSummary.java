package appliedlife.pvtltd.SHEROES.models.entities.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title:User summary
 */
public class UserSummary implements Parcelable {

    @SerializedName("user_id")
    @Expose
    private int userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.emailId);
        dest.writeString(this.mobile);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.photoUrl);
        dest.writeParcelable(this.userBO, flags);
        dest.writeTypedList(this.educationBO);
        dest.writeTypedList(this.exprienceBO);
        dest.writeTypedList(this.projectsBO);
    }

    protected UserSummary(Parcel in) {
        this.userId = in.readInt();
        this.emailId = in.readString();
        this.mobile = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.photoUrl = in.readString();
        this.userBO = in.readParcelable(UserBO.class.getClassLoader());
        this.educationBO = in.createTypedArrayList(EducationEntityBO.CREATOR);
        this.exprienceBO = in.createTypedArrayList(ExprienceEntityBO.CREATOR);
        this.projectsBO = in.createTypedArrayList(ProjectEntityBO.CREATOR);
    }

    public static final Creator<UserSummary> CREATOR = new Creator<UserSummary>() {
        @Override
        public UserSummary createFromParcel(Parcel source) {
            return new UserSummary(source);
        }

        @Override
        public UserSummary[] newArray(int size) {
            return new UserSummary[size];
        }
    };
}
