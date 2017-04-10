package appliedlife.pvtltd.SHEROES.models.entities.profile;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by sheroes on 29/03/17.
 */

public class ProfileListResponse extends BaseResponse  {

    @SerializedName("education")
    @Expose
    private ArrayList<EducationEntity> educations;

    @SerializedName("experience")
    @Expose
    private ArrayList<ExprienceEntity> experiences;

    @SerializedName("project")
    @Expose
    private ArrayList<ProjectEntity> projects;


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
