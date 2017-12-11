package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by sheroes on 29/03/17.
 */
@Parcel(analyze = {EducationEntity.class})
public class EducationEntity extends BaseResponse{
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("field_of_study_master_id")
    @Expose
    private long fieldOfStudyMasterId;
    @SerializedName("degree_name_master_id")
    @Expose
    private long degreeNameMasterId;
    @SerializedName("school_name_master_id")
    @Expose
    private long schoolNameMasterId;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("session_start_year")
    @Expose
    private int sessionStartYear;
    @SerializedName("session_end_year")
    @Expose
    private int sessionEndYear;
    @SerializedName("session_start_month")
    @Expose
    private int sessionStartMonth;
    @SerializedName("session_end_month")
    @Expose
    private int sessionEndMonth;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("field_of_study")
    @Expose
    private String fieldOfStudy;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("activities")
    @Expose
    private String activities;
    @SerializedName("display_order")
    @Expose
    private int displayOrder;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;
    @SerializedName("tag")
    @Expose
    private String tag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFieldOfStudyMasterId() {
        return fieldOfStudyMasterId;
    }

    public void setFieldOfStudyMasterId(long fieldOfStudyMasterId) {
        this.fieldOfStudyMasterId = fieldOfStudyMasterId;
    }

    public long getDegreeNameMasterId() {
        return degreeNameMasterId;
    }

    public void setDegreeNameMasterId(long degreeNameMasterId) {
        this.degreeNameMasterId = degreeNameMasterId;
    }

    public long getSchoolNameMasterId() {
        return schoolNameMasterId;
    }

    public void setSchoolNameMasterId(long schoolNameMasterId) {
        this.schoolNameMasterId = schoolNameMasterId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getSessionStartYear() {
        return sessionStartYear;
    }

    public void setSessionStartYear(int sessionStartYear) {
        this.sessionStartYear = sessionStartYear;
    }

    public int getSessionEndYear() {
        return sessionEndYear;
    }

    public void setSessionEndYear(int sessionEndYear) {
        this.sessionEndYear = sessionEndYear;
    }

    public int getSessionStartMonth() {
        return sessionStartMonth;
    }

    public void setSessionStartMonth(int sessionStartMonth) {
        this.sessionStartMonth = sessionStartMonth;
    }

    public int getSessionEndMonth() {
        return sessionEndMonth;
    }

    public void setSessionEndMonth(int sessionEndMonth) {
        this.sessionEndMonth = sessionEndMonth;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public EducationEntity getEducationEntity() {
        return educationEntity;
    }

    public void setEducationEntity(EducationEntity educationEntity) {
        this.educationEntity = educationEntity;
    }

    public Boolean getCurrentlyAttending() {
        return isCurrentlyAttending;
    }

    public void setCurrentlyAttending(Boolean currentlyAttending) {
        isCurrentlyAttending = currentlyAttending;
    }

    public Boolean getGrade() {
        return isGrade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setGrade(Boolean grade) {
        isGrade = grade;
    }

    public String getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(String maxGrade) {
        this.maxGrade = maxGrade;
    }

    private EducationEntity educationEntity;
    private Boolean isCurrentlyAttending;
    @SerializedName("is_grade")
    @Expose
    private Boolean isGrade;
    @SerializedName("max_grade")
    @Expose
    private String maxGrade;
    @SerializedName("grade")
    @Expose
    private String grade;
}
