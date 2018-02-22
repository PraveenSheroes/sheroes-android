package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {UserSolrObj.class, FeedDetail.class})
public class UserSolrObj extends FeedDetail {
    public int currentItemPosition;
    private boolean isSuggested;
    private boolean isCompactView;

    @SerializedName(value = "total_exp_i")
    private int totalExperience;

    @SerializedName(value = "id_city_l")
    private long cityId;

    @SerializedName(value = "city_name_s")
    private String cityName;

    @SerializedName(value = "search_id_skills")
    private List<Long> skillIds;

    @SerializedName(value = "search_text_skills")
    private List<String> skills;

    @SerializedName(value = "search_id_opportunities")
    private List<Long> opportunityTypeIds;

    @SerializedName(value = "search_text_opportunities")
    private List<String> opportunityTypes;

    @SerializedName(value = "search_id_can_help_in")
    private List<Long> canHelpInIds;

    @SerializedName(value = "search_text_can_help_in")
    private List<String> canHelpIns;

    @SerializedName(value = "search_id_experience")
    private List<Long> experienceIds;

    @SerializedName(value = "search_text_experience_title")
    private List<String> experienceTitles;

    @SerializedName(value = "search_id_experience_company")
    private List<Long> experienceCompanyIds;

    @SerializedName(value = "search_text_experience_company")
    private List<String> experienceCompanyNames;

    @SerializedName(value = "display_id_curr_experience")
    private List<Long> currExperienceIds;

    @SerializedName(value = "display_text_curr_experience_title")
    private List<String> currExperienceTitles;

    @SerializedName(value = "display_id_curr_experience_company")
    private List<Long> currExperienceCompanyIds;

    @SerializedName(value = "display_text_curr_experience_company")
    private List<String> currExperienceCompanyNames;

    @SerializedName(value = "search_id_education_id")
    private List<Long> educationIds;

    @SerializedName(value = "search_id_education_school")
    private List<Long> educationSchoolIds;

    @SerializedName(value = "search_text_education_school_name")
    private List<String> educationSchoolNames;

    @SerializedName(value = "search_id_education_degree")
    private List<Long> educationDegreeIds;

    @SerializedName(value = "search_text_education_degree_name")
    private List<String> educationDegreeNames;

    @SerializedName(value = "search_id_field_of_study_master")
    private List<Long> fieldOfStudyMasterIds;

    @SerializedName(value = "search_text_field_of_study_master_name")
    private List<String> fieldOfStudyMasterNames;

    @SerializedName(value = "gender_s")
    private String gender;

    @SerializedName(value = "currently_l")
    private Long currently_id;

    @SerializedName(value = "profile_id_l")
    private long profileId;

    @SerializedName(value = "currently_s")
    private String currently;

    @SerializedName(value = "no_of_children_i")
    private int noOfChildren;

    @SerializedName(value = "interest_ls")
    private List<Long> interestId;

    @SerializedName(value = "ineterest_ss")
    private List<String> interestNames;

    @SerializedName(value = "functional_area_ls")
    private List<Long> functionalAreaIds;

    @SerializedName(value = "functional_area_ss")
    private List<String> functionalAreaNames;

    @SerializedName(value = "search_id_communities")
    private List<Long> searchIdCommunities;

    @SerializedName(value = "last_activity_date_dt")
    private Date lastActivityDate;

    @SerializedName(value = "s_disp_emailid")
    private String emailId;

    @SerializedName(value = "s_disp_mobile")
    private String mobileNo;

    @SerializedName(value = "p_is_company_admin_b")
    private Boolean isCompanyAdmin;

    @SerializedName(value = "mars_user_current_salary_i")
    private int marsUserCurrentSalary;

    @SerializedName(value = "mars_user_preferred_time_s")
    private String marsUserPreferredTime;

    //New fields for mentor object
    @SerializedName("solr_ignore_total_no_of_post_created")
    private int solrIgnoreNoOfMentorPosts;

    @SerializedName("solr_ignore_no_of_mentor_followers")
    private int solrIgnoreNoOfMentorFollowers;

    private int userFollowing;

    @SerializedName("solr_ignore_mentor_status")
    private String solrIgnoreMentorStatus;

    @SerializedName("solr_ignore_has_user_asked_question")
    private boolean solrIgnoreHasUserAskedQuestion;

    @SerializedName("solr_ignore_mentor_community_id")
    private long solrIgnoreMentorCommunityId;

    @SerializedName("solr_ignore_no_of_mentor_answers")
    private int solrIgnoreNoOfMentorAnswers;

    @SerializedName("solr_ignore_is_mentor_followed")
    private boolean solrIgnoreIsMentorFollowed;

    @SerializedName("solr_ignore_is_user_followed")
    private boolean solrIgnoreIsUserFollowed;

    @SerializedName("solr_ignore_no_of_user_followers")
    private int userFollowersCount;

    @SerializedName("solr_ignore_total_no_of_comments_created")
    private int userCommentsCount;


    public int getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(int totalExperience) {
        this.totalExperience = totalExperience;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<Long> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(List<Long> skillIds) {
        this.skillIds = skillIds;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Long> getOpportunityTypeIds() {
        return opportunityTypeIds;
    }

    public void setOpportunityTypeIds(List<Long> opportunityTypeIds) {
        this.opportunityTypeIds = opportunityTypeIds;
    }

    public List<String> getOpportunityTypes() {
        return opportunityTypes;
    }

    public void setOpportunityTypes(List<String> opportunityTypes) {
        this.opportunityTypes = opportunityTypes;
    }

    public List<Long> getCanHelpInIds() {
        return canHelpInIds;
    }

    public void setCanHelpInIds(List<Long> canHelpInIds) {
        this.canHelpInIds = canHelpInIds;
    }

    public List<String> getCanHelpIns() {
        return canHelpIns;
    }

    public void setCanHelpIns(List<String> canHelpIns) {
        this.canHelpIns = canHelpIns;
    }

    public List<Long> getExperienceIds() {
        return experienceIds;
    }

    public void setExperienceIds(List<Long> experienceIds) {
        this.experienceIds = experienceIds;
    }

    public List<String> getExperienceTitles() {
        return experienceTitles;
    }

    public void setExperienceTitles(List<String> experienceTitles) {
        this.experienceTitles = experienceTitles;
    }

    public List<Long> getExperienceCompanyIds() {
        return experienceCompanyIds;
    }

    public void setExperienceCompanyIds(List<Long> experienceCompanyIds) {
        this.experienceCompanyIds = experienceCompanyIds;
    }

    public List<String> getExperienceCompanyNames() {
        return experienceCompanyNames;
    }

    public void setExperienceCompanyNames(List<String> experienceCompanyNames) {
        this.experienceCompanyNames = experienceCompanyNames;
    }

    public List<Long> getCurrExperienceIds() {
        return currExperienceIds;
    }

    public void setCurrExperienceIds(List<Long> currExperienceIds) {
        this.currExperienceIds = currExperienceIds;
    }

    public List<String> getCurrExperienceTitles() {
        return currExperienceTitles;
    }

    public void setCurrExperienceTitles(List<String> currExperienceTitles) {
        this.currExperienceTitles = currExperienceTitles;
    }

    public List<Long> getCurrExperienceCompanyIds() {
        return currExperienceCompanyIds;
    }

    public void setCurrExperienceCompanyIds(List<Long> currExperienceCompanyIds) {
        this.currExperienceCompanyIds = currExperienceCompanyIds;
    }

    public List<String> getCurrExperienceCompanyNames() {
        return currExperienceCompanyNames;
    }

    public void setCurrExperienceCompanyNames(List<String> currExperienceCompanyNames) {
        this.currExperienceCompanyNames = currExperienceCompanyNames;
    }

    public List<Long> getEducationIds() {
        return educationIds;
    }

    public void setEducationIds(List<Long> educationIds) {
        this.educationIds = educationIds;
    }

    public List<Long> getEducationSchoolIds() {
        return educationSchoolIds;
    }

    public void setEducationSchoolIds(List<Long> educationSchoolIds) {
        this.educationSchoolIds = educationSchoolIds;
    }

    public List<String> getEducationSchoolNames() {
        return educationSchoolNames;
    }

    public void setEducationSchoolNames(List<String> educationSchoolNames) {
        this.educationSchoolNames = educationSchoolNames;
    }

    public List<Long> getEducationDegreeIds() {
        return educationDegreeIds;
    }

    public void setEducationDegreeIds(List<Long> educationDegreeIds) {
        this.educationDegreeIds = educationDegreeIds;
    }

    public List<String> getEducationDegreeNames() {
        return educationDegreeNames;
    }

    public void setEducationDegreeNames(List<String> educationDegreeNames) {
        this.educationDegreeNames = educationDegreeNames;
    }

    public List<Long> getFieldOfStudyMasterIds() {
        return fieldOfStudyMasterIds;
    }

    public void setFieldOfStudyMasterIds(List<Long> fieldOfStudyMasterIds) {
        this.fieldOfStudyMasterIds = fieldOfStudyMasterIds;
    }

    public List<String> getFieldOfStudyMasterNames() {
        return fieldOfStudyMasterNames;
    }

    public void setFieldOfStudyMasterNames(List<String> fieldOfStudyMasterNames) {
        this.fieldOfStudyMasterNames = fieldOfStudyMasterNames;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getCurrently_id() {
        return currently_id;
    }

    public void setCurrently_id(long currently_id) {
        this.currently_id = currently_id;
    }

    @Override
    public long getProfileId() {
        return profileId;
    }

    @Override
    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getCurrently() {
        return currently;
    }

    public void setCurrently(String currently) {
        this.currently = currently;
    }

    public int getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(int noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public List<Long> getInterestId() {
        return interestId;
    }

    public void setInterestId(List<Long> interestId) {
        this.interestId = interestId;
    }

    public List<String> getInterestNames() {
        return interestNames;
    }

    public void setInterestNames(List<String> interestNames) {
        this.interestNames = interestNames;
    }

    public List<Long> getFunctionalAreaIds() {
        return functionalAreaIds;
    }

    public void setFunctionalAreaIds(List<Long> functionalAreaIds) {
        this.functionalAreaIds = functionalAreaIds;
    }

    public List<String> getFunctionalAreaNames() {
        return functionalAreaNames;
    }

    public void setFunctionalAreaNames(List<String> functionalAreaNames) {
        this.functionalAreaNames = functionalAreaNames;
    }

    public List<Long> getSearchIdCommunities() {
        return searchIdCommunities;
    }

    public void setSearchIdCommunities(List<Long> searchIdCommunities) {
        this.searchIdCommunities = searchIdCommunities;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Boolean getCompanyAdmin() {
        return isCompanyAdmin;
    }

    public void setCompanyAdmin(Boolean companyAdmin) {
        isCompanyAdmin = companyAdmin;
    }

    public int getMarsUserCurrentSalary() {
        return marsUserCurrentSalary;
    }

    public void setMarsUserCurrentSalary(int marsUserCurrentSalary) {
        this.marsUserCurrentSalary = marsUserCurrentSalary;
    }

    public String getMarsUserPreferredTime() {
        return marsUserPreferredTime;
    }

    public void setMarsUserPreferredTime(String marsUserPreferredTime) {
        this.marsUserPreferredTime = marsUserPreferredTime;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public void setCurrently_id(Long currently_id) {
        this.currently_id = currently_id;
    }

    public int getSolrIgnoreNoOfMentorFollowers() {
        return solrIgnoreNoOfMentorFollowers;
    }

    public void setSolrIgnoreNoOfMentorFollowers(int solrIgnoreNoOfMentorFollowers) {
        this.solrIgnoreNoOfMentorFollowers = solrIgnoreNoOfMentorFollowers;
    }

    public String getSolrIgnoreMentorStatus() {
        return solrIgnoreMentorStatus;
    }

    public void setSolrIgnoreMentorStatus(String solrIgnoreMentorStatus) {
        this.solrIgnoreMentorStatus = solrIgnoreMentorStatus;
    }

    public boolean isSolrIgnoreHasUserAskedQuestion() {
        return solrIgnoreHasUserAskedQuestion;
    }

    public void setSolrIgnoreHasUserAskedQuestion(boolean solrIgnoreHasUserAskedQuestion) {
        this.solrIgnoreHasUserAskedQuestion = solrIgnoreHasUserAskedQuestion;
    }

    public long getSolrIgnoreMentorCommunityId() {
        return solrIgnoreMentorCommunityId;
    }

    public void setSolrIgnoreMentorCommunityId(long solrIgnoreMentorCommunityId) {
        this.solrIgnoreMentorCommunityId = solrIgnoreMentorCommunityId;
    }

    public int getSolrIgnoreNoOfMentorAnswers() {
        return solrIgnoreNoOfMentorAnswers;
    }

    public void setSolrIgnoreNoOfMentorAnswers(int solrIgnoreNoOfMentorAnswers) {
        this.solrIgnoreNoOfMentorAnswers = solrIgnoreNoOfMentorAnswers;
    }

    public boolean isSolrIgnoreIsMentorFollowed() {
        return solrIgnoreIsMentorFollowed;
    }

    public void setSolrIgnoreIsMentorFollowed(boolean solrIgnoreIsMentorFollowed) {
        this.solrIgnoreIsMentorFollowed = solrIgnoreIsMentorFollowed;
    }

    public boolean isSuggested() {
        return isSuggested;
    }

    public void setSuggested(boolean suggested) {
        isSuggested = suggested;
    }

    public int getSolrIgnoreNoOfMentorPosts() {
        return solrIgnoreNoOfMentorPosts;
    }

    public void setSolrIgnoreNoOfMentorPosts(int solrIgnoreNoOfMentorPosts) {
        this.solrIgnoreNoOfMentorPosts = solrIgnoreNoOfMentorPosts;
    }

    public boolean isSolrIgnoreIsUserFollowed() {
        return solrIgnoreIsUserFollowed;
    }

    public void setSolrIgnoreIsUserFollowed(boolean solrIgnoreIsUserFollowed) {
        this.solrIgnoreIsUserFollowed = solrIgnoreIsUserFollowed;
    }

    public int getUserFollowing() {
        return userFollowing;
    }

    public void setUserFollowing(int userFollowing) {
        this.userFollowing = userFollowing;
    }

    public boolean isCompactView() {
        return isCompactView;
    }

    public void setCompactView(boolean compactView) {
        isCompactView = compactView;
    }

    public int getUserFollowersCount() {
        return userFollowersCount;
    }

    public void setUserFollowersCount(int userFollowersCount) {
        this.userFollowersCount = userFollowersCount;
    }

    public int getUserCommentsCount() {
        return userCommentsCount;
    }

    public void setUserCommentsCount(int userCommentsCount) {
        this.userCommentsCount = userCommentsCount;
    }
}
