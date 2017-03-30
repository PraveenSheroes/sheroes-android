package appliedlife.pvtltd.SHEROES.models.entities.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationEntityBO implements Parcelable {

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
	@SerializedName("is_currently_attending")
	@Expose
	private boolean isCurrentlyAttending;
	@SerializedName("is_grade")
	@Expose
	private boolean isGrade;
	@SerializedName("max_grade")
	@Expose
	private String maxGrade;
	@SerializedName("display_order")
	@Expose
	private int displayOrder;
	@SerializedName("is_active")
	@Expose
	private boolean isActive;
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
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public EducationEntityBO() {
	}

	public boolean isCurrentlyAttending() {
		return isCurrentlyAttending;
	}

	public void setCurrentlyAttending(boolean currentlyAttending) {
		isCurrentlyAttending = currentlyAttending;
	}

	public boolean isGrade() {
		return isGrade;
	}

	public void setGrade(boolean grade) {
		isGrade = grade;
	}

	public String getMaxGrade() {
		return maxGrade;
	}

	public void setMaxGrade(String maxGrade) {
		this.maxGrade = maxGrade;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeLong(this.fieldOfStudyMasterId);
		dest.writeLong(this.degreeNameMasterId);
		dest.writeLong(this.schoolNameMasterId);
		dest.writeString(this.school);
		dest.writeInt(this.sessionStartYear);
		dest.writeInt(this.sessionEndYear);
		dest.writeString(this.degree);
		dest.writeString(this.fieldOfStudy);
		dest.writeString(this.grade);
		dest.writeString(this.description);
		dest.writeString(this.activities);
		dest.writeByte(this.isCurrentlyAttending ? (byte) 1 : (byte) 0);
		dest.writeByte(this.isGrade ? (byte) 1 : (byte) 0);
		dest.writeString(this.maxGrade);
		dest.writeInt(this.displayOrder);
		dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
	}

	protected EducationEntityBO(Parcel in) {
		this.id = in.readLong();
		this.fieldOfStudyMasterId = in.readLong();
		this.degreeNameMasterId = in.readLong();
		this.schoolNameMasterId = in.readLong();
		this.school = in.readString();
		this.sessionStartYear = in.readInt();
		this.sessionEndYear = in.readInt();
		this.degree = in.readString();
		this.fieldOfStudy = in.readString();
		this.grade = in.readString();
		this.description = in.readString();
		this.activities = in.readString();
		this.isCurrentlyAttending = in.readByte() != 0;
		this.isGrade = in.readByte() != 0;
		this.maxGrade = in.readString();
		this.displayOrder = in.readInt();
		this.isActive = in.readByte() != 0;
	}

	public static final Creator<EducationEntityBO> CREATOR = new Creator<EducationEntityBO>() {
		@Override
		public EducationEntityBO createFromParcel(Parcel source) {
			return new EducationEntityBO(source);
		}

		@Override
		public EducationEntityBO[] newArray(int size) {
			return new EducationEntityBO[size];
		}
	};
}
