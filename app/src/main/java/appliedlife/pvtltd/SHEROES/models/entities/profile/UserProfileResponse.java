package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.EducationEntityBO;
import appliedlife.pvtltd.SHEROES.models.entities.login.ExprienceEntityBO;
import appliedlife.pvtltd.SHEROES.models.entities.login.ProjectEntityBO;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;

/**
 * Created by priyanka on 28/03/17.
 */

public class UserProfileResponse extends BaseResponse {


    @SerializedName("user_details")

    @Expose
    private UserDetails userDetails;

    @SerializedName("education")
    @Expose
    private List<EducationEntity> education;

    @SerializedName("experience")
    @Expose
    private List<ExprienceEntity> experience;

    @SerializedName("project")
    @Expose
    private List<ProjectEntity> project;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<EducationEntity> getEducation() {
        return education;
    }

    public void setEducation(List<EducationEntity> education) {
        this.education = education;
    }

    public List<ExprienceEntity> getExperience() {
        return experience;
    }

    public void setExperience(List<ExprienceEntity> experience) {
        this.experience = experience;
    }

    public List<ProjectEntity> getProject() {
        return project;
    }

    public void setProject(List<ProjectEntity> project) {
        this.project = project;
    }
}
