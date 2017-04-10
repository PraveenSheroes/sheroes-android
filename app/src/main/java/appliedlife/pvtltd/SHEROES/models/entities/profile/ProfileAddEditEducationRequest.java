package appliedlife.pvtltd.SHEROES.models.entities.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sheroes on 10/04/17.
 */

public class ProfileAddEditEducationRequest {

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getCurrentlyAttending() {
        return isCurrentlyAttending;
    }

    public void setCurrentlyAttending(Boolean currentlyAttending) {
        isCurrentlyAttending = currentlyAttending;
    }

    @SerializedName("tag")
    @Expose
    private String tag;


    private Boolean isCurrentlyAttending;
    @SerializedName("is_grade")
    @Expose
    private Boolean isGrade;
    @SerializedName("max_grade")
    @Expose
    private String maxGrade;


}
