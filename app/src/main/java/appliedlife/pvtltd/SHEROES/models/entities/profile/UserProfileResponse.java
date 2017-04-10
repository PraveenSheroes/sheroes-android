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
    private ArrayList<EducationEntity> educations;

    @SerializedName("experience")
    @Expose
    private ArrayList<ExprienceEntity> experiences;

    @SerializedName("project")
    @Expose
    private ArrayList<ProjectEntity> projects;

    public UserDetails getUserDetails()
    {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public ArrayList<EducationEntity> getEducations() {
        return educations;
    }

    public void setEducations(ArrayList<EducationEntity> educations) {
        this.educations = educations;
    }

    public ArrayList<ExprienceEntity> getExperiences() {
        return experiences;
    }

    public void setExperiences(ArrayList<ExprienceEntity> experiences) {
        this.experiences = experiences;
    }

    public ArrayList<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<ProjectEntity> projects) {
        this.projects = projects;
    }

  }
