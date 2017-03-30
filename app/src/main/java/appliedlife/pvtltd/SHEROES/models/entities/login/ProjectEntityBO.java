package appliedlife.pvtltd.SHEROES.models.entities.login;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectEntityBO implements Parcelable {

	@SerializedName("experience_id")
	@Expose
	private Long experienceId;
	@SerializedName("id")
	@Expose
	private long id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("start_date_month")
	@Expose
	private int startDateMonth;
	@SerializedName("start_date_year")
	@Expose
	private int startDateYear;
	@SerializedName("end_date_month")
	@Expose
	private int endDateMonth;
	@SerializedName("end_date_year")
	@Expose
	private int endDateYear;
	@SerializedName("is_on_going")
	@Expose
	private int isProjectOngoing;
	@SerializedName("project_url")
	@Expose
	private String projectUrl;
	@SerializedName("description")
	@Expose
	private String descrption;
	@SerializedName("is_active")
	@Expose
	private boolean isActive;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public int getStartDateYear() {
		return startDateYear;
	}
	public void setStartDateYear(int startDateYear) {
		this.startDateYear = startDateYear;
	}
	public int getEndDateMonth() {
		return endDateMonth;
	}
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public int getEndDateYear() {
		return endDateYear;
	}
	public void setEndDateYear(int endDateYear) {
		this.endDateYear = endDateYear;
	}
	public int getIsProjectOngoing() {
		return isProjectOngoing;
	}
	public void setIsProjectOngoing(int isProjectOngoing) {
		this.isProjectOngoing = isProjectOngoing;
	}
	public String getProjectUrl() {
		return projectUrl;
	}
	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}
	public String getDescrption() {
		return descrption;
	}
	public void setDescrption(String descrption) {
		this.descrption = descrption;
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

	public ProjectEntityBO() {
	}

	public Long getExperienceId() {
		return experienceId;
	}

	public void setExperienceId(Long experienceId) {
		this.experienceId = experienceId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(this.experienceId);
		dest.writeLong(this.id);
		dest.writeString(this.name);
		dest.writeInt(this.startDateMonth);
		dest.writeInt(this.startDateYear);
		dest.writeInt(this.endDateMonth);
		dest.writeInt(this.endDateYear);
		dest.writeInt(this.isProjectOngoing);
		dest.writeString(this.projectUrl);
		dest.writeString(this.descrption);
		dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
	}

	protected ProjectEntityBO(Parcel in) {
		this.experienceId = (Long) in.readValue(Long.class.getClassLoader());
		this.id = in.readLong();
		this.name = in.readString();
		this.startDateMonth = in.readInt();
		this.startDateYear = in.readInt();
		this.endDateMonth = in.readInt();
		this.endDateYear = in.readInt();
		this.isProjectOngoing = in.readInt();
		this.projectUrl = in.readString();
		this.descrption = in.readString();
		this.isActive = in.readByte() != 0;
	}

	public static final Creator<ProjectEntityBO> CREATOR = new Creator<ProjectEntityBO>() {
		@Override
		public ProjectEntityBO createFromParcel(Parcel source) {
			return new ProjectEntityBO(source);
		}

		@Override
		public ProjectEntityBO[] newArray(int size) {
			return new ProjectEntityBO[size];
		}
	};
}
