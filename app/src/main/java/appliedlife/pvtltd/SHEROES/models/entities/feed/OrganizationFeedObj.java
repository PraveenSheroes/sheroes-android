package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {OrganizationFeedObj.class,BaseEntityOrParticipantModel.class})
public class OrganizationFeedObj extends BaseEntityOrParticipantModel {
    @SerializedName("founder_s")
    private String founder;

    @SerializedName("founded_in_dt")
    private Date foundedIn;

    @SerializedName("headquarters_s")
    private String headquarters;

    @SerializedName("number_of_employees_l")
    private Long numberOfEmployees;

    @SerializedName("revenue_s")
    private String revenue;

    @SerializedName("percent_of_women_aware_of_icc_f")
    private Float percentOfWomenAwareOfIcc;

    @SerializedName("percent_of_women_aware_of_policy_f")
    private Float percentOfWomenAwareOfPolicy;

    @SerializedName("percent_of_woman_find_interview_comfortable_f")
    private Float percentOfWomanFindInterviewComfortable;

    @SerializedName("number_of_women_faced_unwelcome_behaviour_i")
    private Integer numberOfWomenFacedUnwelcomeBehaviour;

    @SerializedName("number_of_weeks_from_last_training_i")
    private Integer numberOfWeeksFromLastTraining;

    @SerializedName("percent_of_women_f")
    private Float percentOfWomen;

    @SerializedName("women_leadership_b")
    private Boolean womenLeadership;

    @SerializedName("equal_pay_b")
    private Boolean equalPay;

    @SerializedName("returning_professional_b")
    private Boolean returningProfessional;

    @SerializedName("affirmative_actions_b")
    private Boolean affirmativeActions;

    @SerializedName("ratings_f")
    private Float ratings;

    @SerializedName("daycare_b")
    private Boolean daycare;

    @SerializedName("work_from_home_b")
    private Boolean workFromHome;

    @SerializedName("flexible_working_hours_b")
    private Boolean flexibleWorkingHours;

    @SerializedName("days_per_week_i")
    private Integer daysPerWeek;

    @SerializedName("breastfeeding_corners_b")
    private Boolean breastfeedingCorners;

    @SerializedName("nanny_to_work_b")
    private Boolean nannyToWork;

    @SerializedName("founder_image_url_s")
    private String founderImageUrl;

    @SerializedName("number_of_vacations_per_year_i")
    private Integer numberOfVacationsPerYear;

    @SerializedName("number_of_months_for_maternity_leave_i")
    private Integer numberOfMonthsForMaternityLeave;

    @SerializedName("men_women_are_treated_equally_b")
    private Boolean menWomenAreTreatedEqually;

    @SerializedName("company_master_id_l")
    private Long companyMasterId;

    @SerializedName("company_web_site_url_s")
    private String companyWebSiteUrl;

    @SerializedName("ceo_linkedin_profile_url_s")
    private String ceoLinkedinProfileUrl;

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public Date getFoundedIn() {
        return foundedIn;
    }

    public void setFoundedIn(Date foundedIn) {
        this.foundedIn = foundedIn;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public Long getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Long numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public Float getPercentOfWomenAwareOfIcc() {
        return percentOfWomenAwareOfIcc;
    }

    public void setPercentOfWomenAwareOfIcc(Float percentOfWomenAwareOfIcc) {
        this.percentOfWomenAwareOfIcc = percentOfWomenAwareOfIcc;
    }

    public Float getPercentOfWomenAwareOfPolicy() {
        return percentOfWomenAwareOfPolicy;
    }

    public void setPercentOfWomenAwareOfPolicy(Float percentOfWomenAwareOfPolicy) {
        this.percentOfWomenAwareOfPolicy = percentOfWomenAwareOfPolicy;
    }

    public Float getPercentOfWomanFindInterviewComfortable() {
        return percentOfWomanFindInterviewComfortable;
    }

    public void setPercentOfWomanFindInterviewComfortable(Float percentOfWomanFindInterviewComfortable) {
        this.percentOfWomanFindInterviewComfortable = percentOfWomanFindInterviewComfortable;
    }

    public Integer getNumberOfWomenFacedUnwelcomeBehaviour() {
        return numberOfWomenFacedUnwelcomeBehaviour;
    }

    public void setNumberOfWomenFacedUnwelcomeBehaviour(Integer numberOfWomenFacedUnwelcomeBehaviour) {
        this.numberOfWomenFacedUnwelcomeBehaviour = numberOfWomenFacedUnwelcomeBehaviour;
    }

    public Integer getNumberOfWeeksFromLastTraining() {
        return numberOfWeeksFromLastTraining;
    }

    public void setNumberOfWeeksFromLastTraining(Integer numberOfWeeksFromLastTraining) {
        this.numberOfWeeksFromLastTraining = numberOfWeeksFromLastTraining;
    }

    public Float getPercentOfWomen() {
        return percentOfWomen;
    }

    public void setPercentOfWomen(Float percentOfWomen) {
        this.percentOfWomen = percentOfWomen;
    }

    public Boolean getWomenLeadership() {
        return womenLeadership;
    }

    public void setWomenLeadership(Boolean womenLeadership) {
        this.womenLeadership = womenLeadership;
    }

    public Boolean getEqualPay() {
        return equalPay;
    }

    public void setEqualPay(Boolean equalPay) {
        this.equalPay = equalPay;
    }

    public Boolean getReturningProfessional() {
        return returningProfessional;
    }

    public void setReturningProfessional(Boolean returningProfessional) {
        this.returningProfessional = returningProfessional;
    }

    public Boolean getAffirmativeActions() {
        return affirmativeActions;
    }

    public void setAffirmativeActions(Boolean affirmativeActions) {
        this.affirmativeActions = affirmativeActions;
    }

    public Float getRatings() {
        return ratings;
    }

    public void setRatings(Float ratings) {
        this.ratings = ratings;
    }

    public Boolean getDaycare() {
        return daycare;
    }

    public void setDaycare(Boolean daycare) {
        this.daycare = daycare;
    }

    public Boolean getWorkFromHome() {
        return workFromHome;
    }

    public void setWorkFromHome(Boolean workFromHome) {
        this.workFromHome = workFromHome;
    }

    public Boolean getFlexibleWorkingHours() {
        return flexibleWorkingHours;
    }

    public void setFlexibleWorkingHours(Boolean flexibleWorkingHours) {
        this.flexibleWorkingHours = flexibleWorkingHours;
    }

    public Integer getDaysPerWeek() {
        return daysPerWeek;
    }

    public void setDaysPerWeek(Integer daysPerWeek) {
        this.daysPerWeek = daysPerWeek;
    }

    public Boolean getBreastfeedingCorners() {
        return breastfeedingCorners;
    }

    public void setBreastfeedingCorners(Boolean breastfeedingCorners) {
        this.breastfeedingCorners = breastfeedingCorners;
    }

    public Boolean getNannyToWork() {
        return nannyToWork;
    }

    public void setNannyToWork(Boolean nannyToWork) {
        this.nannyToWork = nannyToWork;
    }

    public String getFounderImageUrl() {
        return founderImageUrl;
    }

    public void setFounderImageUrl(String founderImageUrl) {
        this.founderImageUrl = founderImageUrl;
    }

    public Integer getNumberOfVacationsPerYear() {
        return numberOfVacationsPerYear;
    }

    public void setNumberOfVacationsPerYear(Integer numberOfVacationsPerYear) {
        this.numberOfVacationsPerYear = numberOfVacationsPerYear;
    }

    public Integer getNumberOfMonthsForMaternityLeave() {
        return numberOfMonthsForMaternityLeave;
    }

    public void setNumberOfMonthsForMaternityLeave(Integer numberOfMonthsForMaternityLeave) {
        this.numberOfMonthsForMaternityLeave = numberOfMonthsForMaternityLeave;
    }

    public Boolean getMenWomenAreTreatedEqually() {
        return menWomenAreTreatedEqually;
    }

    public void setMenWomenAreTreatedEqually(Boolean menWomenAreTreatedEqually) {
        this.menWomenAreTreatedEqually = menWomenAreTreatedEqually;
    }

    public Long getCompanyMasterId() {
        return companyMasterId;
    }

    public void setCompanyMasterId(Long companyMasterId) {
        this.companyMasterId = companyMasterId;
    }

    public String getCompanyWebSiteUrl() {
        return companyWebSiteUrl;
    }

    public void setCompanyWebSiteUrl(String companyWebSiteUrl) {
        this.companyWebSiteUrl = companyWebSiteUrl;
    }

    public String getCeoLinkedinProfileUrl() {
        return ceoLinkedinProfileUrl;
    }

    public void setCeoLinkedinProfileUrl(String ceoLinkedinProfileUrl) {
        this.ceoLinkedinProfileUrl = ceoLinkedinProfileUrl;
    }
}
