package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by priyanka on 10/04/17.
 */

public class ProfileAddEditEducationRequest extends BaseRequest{

    @SerializedName("subType")
    @Expose
    private String subType;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("degree_name_master_id")
    @Expose
    private long degreeNameMasterId;
    @SerializedName("school_name_master_id")
    @Expose
    private Long schoolNameMasterId;
    @SerializedName("school")
    @Expose
    private String school;
    @SerializedName("session_start_year")
    @Expose
    private int sessionStartYear;
    @SerializedName("session_start_month")
    @Expose
    private int sessionStartMonth;
    @SerializedName("session_end_year")
    @Expose
    private int sessionEndYear;
    @SerializedName("session_end_month")
    @Expose
    private int sessionEndMonth;

    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("field_of_study")
    @Expose
    private String fieldOfStudy;
    @SerializedName("grade")
    @Expose
    private String grade;
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
    @SerializedName("is_currently_attending")
    @Expose
    private Boolean isCurrentlyAttending;
    @SerializedName("is_grade")
    @Expose
    private Boolean isGrade;
    @SerializedName("max_grade")
    @Expose
    private String maxGrade;

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getSessionStartMonth() {
        return sessionStartMonth;
    }

    public void setSessionStartMonth(int sessionStartMonth) {
        this.sessionStartMonth = sessionStartMonth;
    }

    public int getSessionEndYear() {
        return sessionEndYear;
    }

    public void setSessionEndYear(int sessionEndYear) {
        this.sessionEndYear = sessionEndYear;
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

    public String getGrade() {
        return grade;
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

    public void setGrade(String grade) {
        this.grade = grade;
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

    public Boolean getCurrentlyAttending() {
        return isCurrentlyAttending;
    }

    public void setCurrentlyAttending(Boolean currentlyAttending) {
        isCurrentlyAttending = currentlyAttending;
    }
}
