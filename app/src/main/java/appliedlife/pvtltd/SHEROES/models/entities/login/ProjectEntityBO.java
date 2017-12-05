package appliedlife.pvtltd.SHEROES.models.entities.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

@Parcel(analyze = {ProjectEntityBO.class})
public class ProjectEntityBO {

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
}
