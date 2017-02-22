
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class FeedDetail extends BaseResponse implements Parcelable {

    int itemPosition;
    boolean isLongPress;

    @SerializedName("published_in_s")
    @Expose
    private String publishedInS;
    @SerializedName("meta_description_s")
    @Expose
    private String metaDescriptionS;
    @SerializedName("meta_title_s")
    @Expose
    private String metaTitleS;
    @SerializedName("article_status_i")
    @Expose
    private String articleStatusI;
    @SerializedName("third_party_id_s")
    @Expose
    private String thirdPartyIdS;
    @SerializedName("article_category_name_s")
    @Expose
    private String articleCategoryNameS;
    @SerializedName("article_category_l")
    @Expose
    private int articleCategoryL;
    @SerializedName("slug_s")
    @Expose
    private String slugS;
    @SerializedName("entity_or_participant_type_id_i")
    @Expose
    private int entityOrParticipantTypeIdI;
    @SerializedName("display_id_profile_id")
    @Expose
    private String displayIdProfileId;

    @SerializedName("no_of_views")
    @Expose
    private int noOfViews;
    @SerializedName("sector_id_l")
    @Expose
    private int sectorIdL;
    @SerializedName("sector_name_s")
    @Expose
    private String sectorNameS;
    @SerializedName("job_city_id_l")
    @Expose
    private String jobCityIdL;
    @SerializedName("job_city_name_s")
    @Expose
    private String jobCityNameS;
    @SerializedName("is_from_search_firm_b")
    @Expose
    private boolean isFromSearchFirmB;
    @SerializedName("is_search_firm_b")
    @Expose
    private boolean isSearchFirmB;
    @SerializedName("h_company_name_s")
    @Expose
    private String hCompanyNameS;
    @SerializedName("h_company_logo_s")
    @Expose
    private String hCompanyLogoS;
    @SerializedName("experience_from_i")
    @Expose
    private int experienceFromI;
    @SerializedName("experience_to_i")
    @Expose
    private int experienceToI;
    @SerializedName("search_id_job_opp_types")
    @Expose
    private List<Integer> searchIdJobOppTypes = null;
    @SerializedName("search_text_job_emp_types")
    @Expose
    private List<String> searchTextJobEmpTypes = null;
    @SerializedName("search_id_job_skills")
    @Expose
    private List<Integer> searchIdJobSkills = null;
    @SerializedName("top_company_tag_link_id_l")
    @Expose
    private String topCompanyTagLinkIdL;
    @SerializedName("company_profile_id_l")
    @Expose
    private int companyProfileIdL;
    @SerializedName("searchFirm")
    @Expose
    private boolean searchFirm;
    @SerializedName("is_bookmarked")
    private boolean isBookmarked;
    @SerializedName("applied")
    private boolean isApplied;
    @SerializedName("search_id_post_image")
    private List<Long> imagesIds;
    @SerializedName("display_text_image_url")
    private List<String> imageUrls;
    /*Community*/
    @SerializedName("community_type_s")
    public String communityType;

    @SerializedName("community_type_l")
    public String communityTypeL;
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("id")
    private String id;
    @SerializedName("entity_or_participant_id")
    private long entityOrParticipantId;
    @SerializedName("id_of_entity_or_participant")
    private long idOfEntityOrParticipant;
    @SerializedName("type")
    private String type;
    @SerializedName("sub_type")
    private String subType;
    @SerializedName("name")
    private String nameOrTitle;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("thumbnail_image_url")
    private String thumbnailImageUrl;
    @SerializedName("short_description")
    private String shortDescription;
    @SerializedName("description")
    private String description;
    @SerializedName("list_short_description")
    private String listShortDescription;
    @SerializedName("list_description")
    private String listDescription;
    @SerializedName("tag_names")
    private List<String> tags;
    @SerializedName("tag_ids")
    private List<Long> tag_ids;
    @SerializedName("p_is_deleted")
    private boolean isDeleted;
    @SerializedName("p_is_active")
    private boolean isActive;
    @SerializedName("p_crdt")
    private String createdDate;
    @SerializedName("is_expired")
    private boolean isExpired;
    @SerializedName("p_last_modified_on")
    private String lastModifiedDate;
    @SerializedName("author_participant_id")
    private long authorParticipantId;
    @SerializedName("author_id")
    private long authorId;
    @SerializedName("is_author_confidential")
    private boolean isAuthorConfidential;
    @SerializedName("author_participant_type")
    private String authorParticipantType;
    @SerializedName("author_first_name")
    private String authorFirstName;
    @SerializedName("author_last_name")
    private String authorLastName;
    @SerializedName("author_name")
    private String authorName;
    @SerializedName("author_image_url")
    private String authorImageUrl;
    @SerializedName("is_author_image_public")
    private boolean isAuthorImagePublic;
    @SerializedName("author_city_id")
    private String authorCityId;
    @SerializedName("author_city_name")
    private String authorCityName;
    @SerializedName("author_short_description")
    private String authorShortDescription;
    @SerializedName("is_featured")
    private boolean isFeatured;
    @SerializedName("search_text_job_opp_types")
    private List<String> opportunityTypes;
    @SerializedName("search_id_job_emp_types")
    private List<String> employmentTypes;
    @SerializedName("search_text_job_skills")
    private List<String> skills;
    /*user object for feed*/
    @SerializedName("search_id_total_exp")
    private List<Long> totalExperience;
    @SerializedName("id_city_l")
    private Long cityId;
    @SerializedName("city_name_s")
    private String cityName;
    @SerializedName("search_id_skills")
    private List<Long> skillIds;
    @SerializedName("search_text_skills")
    private List<String> searchTextSkills;
    @SerializedName("search_id_opportunities")
    private List<Long> opportunityTypeIds;
    @SerializedName("search_text_opportunities")
    private List<String> searchIdOpportunityTypes;
    @SerializedName("search_id_can_help_in")
    private List<Long> canHelpInIds;
    @SerializedName("search_text_can_help_in")
    private List<String> canHelpIns;
    @SerializedName("search_id_experience")
    private List<Long> experienceIds;
    @SerializedName("search_text_experience_title")
    private List<String> experienceTitles;
    @SerializedName("search_id_experience_company")
    private List<Long> experienceCompanyIds;
    @SerializedName("search_text_experience_company")
    private List<String> experienceCompanyNames;
    @SerializedName("display_id_curr_experience")
    private List<Long> currExperienceIds;
    @SerializedName("display_text_curr_experience_title")
    private List<String> currExperienceTitles;
    @SerializedName("display_id_curr_experience_company")
    private List<Long> currExperienceCompanyIds;
    @SerializedName("display_text_curr_experience_company")
    private List<String> currExperienceCompanyNames;
    @SerializedName("search_id_education_id")
    private List<Long> educationIds;
    @SerializedName("search_id_education_school")
    private List<Long> educationSchoolIds;
    @SerializedName("search_text_education_school_name")
    private List<String> educationSchoolNames;
    @SerializedName("search_id_education_degree")
    private List<Long> educationDegreeIds;
    @SerializedName("search_text_education_degree_name")
    private List<String> educationDegreeNames;
    @SerializedName("gender_s")
    private String gender;
    @SerializedName("currently_l")
    private Long currently_id;
    @SerializedName("currently_s")
    private String currently;
    @SerializedName("no_of_children_i")
    private int noOfChildren;
    @SerializedName("interest_ls")
    private List<Long> interestId;
    @SerializedName("ineterest_ss")
    private List<String> interestNames;
    @SerializedName("functional_area_ls")
    private List<Long> functionalAreaIds;
    @SerializedName("functional_area_ss")
    private List<String> functionalAreaNames;
    @SerializedName("last_activity_date_dt")
    private List<String> lastActivityDate;

    /*Like and comment*/
    @SerializedName("reacted_value")
    @Expose
    private int reactionValue;
    @SerializedName("no_of_likes")
    @Expose
    private int noOfLikes;
    @SerializedName("no_of_comments")
    @Expose
    private int noOfComments;
    @SerializedName("last_comments")
    @Expose
    private List<LastComment> lastComments= null;;

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public boolean isLongPress() {
        return isLongPress;
    }

    public void setLongPress(boolean longPress) {
        isLongPress = longPress;
    }

    public String getPublishedInS() {
        return publishedInS;
    }

    public void setPublishedInS(String publishedInS) {
        this.publishedInS = publishedInS;
    }

    public String getMetaDescriptionS() {
        return metaDescriptionS;
    }

    public void setMetaDescriptionS(String metaDescriptionS) {
        this.metaDescriptionS = metaDescriptionS;
    }

    public String getMetaTitleS() {
        return metaTitleS;
    }

    public void setMetaTitleS(String metaTitleS) {
        this.metaTitleS = metaTitleS;
    }

    public String getArticleStatusI() {
        return articleStatusI;
    }

    public void setArticleStatusI(String articleStatusI) {
        this.articleStatusI = articleStatusI;
    }

    public String getThirdPartyIdS() {
        return thirdPartyIdS;
    }

    public void setThirdPartyIdS(String thirdPartyIdS) {
        this.thirdPartyIdS = thirdPartyIdS;
    }

    public String getArticleCategoryNameS() {
        return articleCategoryNameS;
    }

    public void setArticleCategoryNameS(String articleCategoryNameS) {
        this.articleCategoryNameS = articleCategoryNameS;
    }

    public int getArticleCategoryL() {
        return articleCategoryL;
    }

    public void setArticleCategoryL(int articleCategoryL) {
        this.articleCategoryL = articleCategoryL;
    }

    public String getSlugS() {
        return slugS;
    }

    public void setSlugS(String slugS) {
        this.slugS = slugS;
    }

    public int getEntityOrParticipantTypeIdI() {
        return entityOrParticipantTypeIdI;
    }

    public void setEntityOrParticipantTypeIdI(int entityOrParticipantTypeIdI) {
        this.entityOrParticipantTypeIdI = entityOrParticipantTypeIdI;
    }

    public String getDisplayIdProfileId() {
        return displayIdProfileId;
    }

    public void setDisplayIdProfileId(String displayIdProfileId) {
        this.displayIdProfileId = displayIdProfileId;
    }

    public int getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(int noOfViews) {
        this.noOfViews = noOfViews;
    }

    public int getSectorIdL() {
        return sectorIdL;
    }

    public void setSectorIdL(int sectorIdL) {
        this.sectorIdL = sectorIdL;
    }

    public String getSectorNameS() {
        return sectorNameS;
    }

    public void setSectorNameS(String sectorNameS) {
        this.sectorNameS = sectorNameS;
    }

    public String getJobCityIdL() {
        return jobCityIdL;
    }

    public void setJobCityIdL(String jobCityIdL) {
        this.jobCityIdL = jobCityIdL;
    }

    public String getJobCityNameS() {
        return jobCityNameS;
    }

    public void setJobCityNameS(String jobCityNameS) {
        this.jobCityNameS = jobCityNameS;
    }

    public boolean isFromSearchFirmB() {
        return isFromSearchFirmB;
    }

    public void setFromSearchFirmB(boolean fromSearchFirmB) {
        isFromSearchFirmB = fromSearchFirmB;
    }

    public boolean isSearchFirmB() {
        return isSearchFirmB;
    }

    public void setSearchFirmB(boolean searchFirmB) {
        isSearchFirmB = searchFirmB;
    }

    public String gethCompanyNameS() {
        return hCompanyNameS;
    }

    public void sethCompanyNameS(String hCompanyNameS) {
        this.hCompanyNameS = hCompanyNameS;
    }

    public String gethCompanyLogoS() {
        return hCompanyLogoS;
    }

    public void sethCompanyLogoS(String hCompanyLogoS) {
        this.hCompanyLogoS = hCompanyLogoS;
    }

    public int getExperienceFromI() {
        return experienceFromI;
    }

    public void setExperienceFromI(int experienceFromI) {
        this.experienceFromI = experienceFromI;
    }

    public int getExperienceToI() {
        return experienceToI;
    }

    public void setExperienceToI(int experienceToI) {
        this.experienceToI = experienceToI;
    }

    public List<Integer> getSearchIdJobOppTypes() {
        return searchIdJobOppTypes;
    }

    public void setSearchIdJobOppTypes(List<Integer> searchIdJobOppTypes) {
        this.searchIdJobOppTypes = searchIdJobOppTypes;
    }


    public List<String> getSearchTextJobEmpTypes() {
        return searchTextJobEmpTypes;
    }

    public void setSearchTextJobEmpTypes(List<String> searchTextJobEmpTypes) {
        this.searchTextJobEmpTypes = searchTextJobEmpTypes;
    }

    public List<Integer> getSearchIdJobSkills() {
        return searchIdJobSkills;
    }

    public void setSearchIdJobSkills(List<Integer> searchIdJobSkills) {
        this.searchIdJobSkills = searchIdJobSkills;
    }


    public String getTopCompanyTagLinkIdL() {
        return topCompanyTagLinkIdL;
    }

    public void setTopCompanyTagLinkIdL(String topCompanyTagLinkIdL) {
        this.topCompanyTagLinkIdL = topCompanyTagLinkIdL;
    }

    public int getCompanyProfileIdL() {
        return companyProfileIdL;
    }

    public void setCompanyProfileIdL(int companyProfileIdL) {
        this.companyProfileIdL = companyProfileIdL;
    }


    public boolean isSearchFirm() {
        return searchFirm;
    }

    public void setSearchFirm(boolean searchFirm) {
        this.searchFirm = searchFirm;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean applied) {
        isApplied = applied;
    }

    public List<Long> getImagesIds() {
        return imagesIds;
    }

    public void setImagesIds(List<Long> imagesIds) {
        this.imagesIds = imagesIds;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getCommunityType() {
        return communityType;
    }

    public void setCommunityType(String communityType) {
        this.communityType = communityType;
    }

    public String getCommunityTypeL() {
        return communityTypeL;
    }

    public void setCommunityTypeL(String communityTypeL) {
        this.communityTypeL = communityTypeL;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getAuthorParticipantId() {
        return authorParticipantId;
    }

    public void setAuthorParticipantId(long authorParticipantId) {
        this.authorParticipantId = authorParticipantId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public boolean isAuthorConfidential() {
        return isAuthorConfidential;
    }

    public void setAuthorConfidential(boolean authorConfidential) {
        isAuthorConfidential = authorConfidential;
    }

    public String getAuthorParticipantType() {
        return authorParticipantType;
    }

    public void setAuthorParticipantType(String authorParticipantType) {
        this.authorParticipantType = authorParticipantType;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public boolean isAuthorImagePublic() {
        return isAuthorImagePublic;
    }

    public void setAuthorImagePublic(boolean authorImagePublic) {
        isAuthorImagePublic = authorImagePublic;
    }

    public String getAuthorCityId() {
        return authorCityId;
    }

    public void setAuthorCityId(String authorCityId) {
        this.authorCityId = authorCityId;
    }

    public String getAuthorCityName() {
        return authorCityName;
    }

    public void setAuthorCityName(String authorCityName) {
        this.authorCityName = authorCityName;
    }

    public String getAuthorShortDescription() {
        return authorShortDescription;
    }

    public void setAuthorShortDescription(String authorShortDescription) {
        this.authorShortDescription = authorShortDescription;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public List<String> getOpportunityTypes() {
        return opportunityTypes;
    }

    public void setOpportunityTypes(List<String> opportunityTypes) {
        this.opportunityTypes = opportunityTypes;
    }

    public List<String> getEmploymentTypes() {
        return employmentTypes;
    }

    public void setEmploymentTypes(List<String> employmentTypes) {
        this.employmentTypes = employmentTypes;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Long> getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(List<Long> totalExperience) {
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

    public List<String> getSearchTextSkills() {
        return searchTextSkills;
    }

    public void setSearchTextSkills(List<String> searchTextSkills) {
        this.searchTextSkills = searchTextSkills;
    }

    public List<Long> getOpportunityTypeIds() {
        return opportunityTypeIds;
    }

    public void setOpportunityTypeIds(List<Long> opportunityTypeIds) {
        this.opportunityTypeIds = opportunityTypeIds;
    }

    public List<String> getSearchIdOpportunityTypes() {
        return searchIdOpportunityTypes;
    }

    public void setSearchIdOpportunityTypes(List<String> searchIdOpportunityTypes) {
        this.searchIdOpportunityTypes = searchIdOpportunityTypes;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getCurrently_id() {
        return currently_id;
    }

    public void setCurrently_id(Long currently_id) {
        this.currently_id = currently_id;
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

    public List<String> getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(List<String> lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public int getReactionValue() {
        return reactionValue;
    }

    public void setReactionValue(int reactionValue) {
        this.reactionValue = reactionValue;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(int noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }

    public List<LastComment> getLastComments() {
        return lastComments;
    }

    public void setLastComments(List<LastComment> lastComments) {
        this.lastComments = lastComments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getEntityOrParticipantId() {
        return entityOrParticipantId;
    }

    public void setEntityOrParticipantId(long entityOrParticipantId) {
        this.entityOrParticipantId = entityOrParticipantId;
    }

    public long getIdOfEntityOrParticipant() {
        return idOfEntityOrParticipant;
    }

    public void setIdOfEntityOrParticipant(long idOfEntityOrParticipant) {
        this.idOfEntityOrParticipant = idOfEntityOrParticipant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getNameOrTitle() {
        return nameOrTitle;
    }

    public void setNameOrTitle(String nameOrTitle) {
        this.nameOrTitle = nameOrTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getListShortDescription() {
        return listShortDescription;
    }

    public void setListShortDescription(String listShortDescription) {
        this.listShortDescription = listShortDescription;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Long> getTag_ids() {
        return tag_ids;
    }

    public void setTag_ids(List<Long> tag_ids) {
        this.tag_ids = tag_ids;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }


    public FeedDetail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemPosition);
        dest.writeByte(this.isLongPress ? (byte) 1 : (byte) 0);
        dest.writeString(this.publishedInS);
        dest.writeString(this.metaDescriptionS);
        dest.writeString(this.metaTitleS);
        dest.writeString(this.articleStatusI);
        dest.writeString(this.thirdPartyIdS);
        dest.writeString(this.articleCategoryNameS);
        dest.writeInt(this.articleCategoryL);
        dest.writeString(this.slugS);
        dest.writeInt(this.entityOrParticipantTypeIdI);
        dest.writeString(this.displayIdProfileId);
        dest.writeInt(this.noOfViews);
        dest.writeInt(this.sectorIdL);
        dest.writeString(this.sectorNameS);
        dest.writeString(this.jobCityIdL);
        dest.writeString(this.jobCityNameS);
        dest.writeByte(this.isFromSearchFirmB ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isSearchFirmB ? (byte) 1 : (byte) 0);
        dest.writeString(this.hCompanyNameS);
        dest.writeString(this.hCompanyLogoS);
        dest.writeInt(this.experienceFromI);
        dest.writeInt(this.experienceToI);
        dest.writeList(this.searchIdJobOppTypes);
        dest.writeStringList(this.searchTextJobEmpTypes);
        dest.writeList(this.searchIdJobSkills);
        dest.writeString(this.topCompanyTagLinkIdL);
        dest.writeInt(this.companyProfileIdL);
        dest.writeByte(this.searchFirm ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isBookmarked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isApplied ? (byte) 1 : (byte) 0);
        dest.writeList(this.imagesIds);
        dest.writeStringList(this.imageUrls);
        dest.writeString(this.communityType);
        dest.writeString(this.communityTypeL);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
        dest.writeLong(this.entityOrParticipantId);
        dest.writeLong(this.idOfEntityOrParticipant);
        dest.writeString(this.type);
        dest.writeString(this.subType);
        dest.writeString(this.nameOrTitle);
        dest.writeString(this.imageUrl);
        dest.writeString(this.thumbnailImageUrl);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.listShortDescription);
        dest.writeString(this.listDescription);
        dest.writeStringList(this.tags);
        dest.writeList(this.tag_ids);
        dest.writeByte(this.isDeleted ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isActive ? (byte) 1 : (byte) 0);
        dest.writeString(this.createdDate);
        dest.writeByte(this.isExpired ? (byte) 1 : (byte) 0);
        dest.writeString(this.lastModifiedDate);
        dest.writeLong(this.authorParticipantId);
        dest.writeLong(this.authorId);
        dest.writeByte(this.isAuthorConfidential ? (byte) 1 : (byte) 0);
        dest.writeString(this.authorParticipantType);
        dest.writeString(this.authorFirstName);
        dest.writeString(this.authorLastName);
        dest.writeString(this.authorName);
        dest.writeString(this.authorImageUrl);
        dest.writeByte(this.isAuthorImagePublic ? (byte) 1 : (byte) 0);
        dest.writeString(this.authorCityId);
        dest.writeString(this.authorCityName);
        dest.writeString(this.authorShortDescription);
        dest.writeByte(this.isFeatured ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.opportunityTypes);
        dest.writeStringList(this.employmentTypes);
        dest.writeStringList(this.skills);
        dest.writeList(this.totalExperience);
        dest.writeValue(this.cityId);
        dest.writeString(this.cityName);
        dest.writeList(this.skillIds);
        dest.writeStringList(this.searchTextSkills);
        dest.writeList(this.opportunityTypeIds);
        dest.writeStringList(this.searchIdOpportunityTypes);
        dest.writeList(this.canHelpInIds);
        dest.writeStringList(this.canHelpIns);
        dest.writeList(this.experienceIds);
        dest.writeStringList(this.experienceTitles);
        dest.writeList(this.experienceCompanyIds);
        dest.writeStringList(this.experienceCompanyNames);
        dest.writeList(this.currExperienceIds);
        dest.writeStringList(this.currExperienceTitles);
        dest.writeList(this.currExperienceCompanyIds);
        dest.writeStringList(this.currExperienceCompanyNames);
        dest.writeList(this.educationIds);
        dest.writeList(this.educationSchoolIds);
        dest.writeStringList(this.educationSchoolNames);
        dest.writeList(this.educationDegreeIds);
        dest.writeStringList(this.educationDegreeNames);
        dest.writeString(this.gender);
        dest.writeValue(this.currently_id);
        dest.writeString(this.currently);
        dest.writeInt(this.noOfChildren);
        dest.writeList(this.interestId);
        dest.writeStringList(this.interestNames);
        dest.writeList(this.functionalAreaIds);
        dest.writeStringList(this.functionalAreaNames);
        dest.writeStringList(this.lastActivityDate);
        dest.writeInt(this.reactionValue);
        dest.writeInt(this.noOfLikes);
        dest.writeInt(this.noOfComments);
        dest.writeTypedList(this.lastComments);
    }

    protected FeedDetail(Parcel in) {
        this.itemPosition = in.readInt();
        this.isLongPress = in.readByte() != 0;
        this.publishedInS = in.readString();
        this.metaDescriptionS = in.readString();
        this.metaTitleS = in.readString();
        this.articleStatusI = in.readString();
        this.thirdPartyIdS = in.readString();
        this.articleCategoryNameS = in.readString();
        this.articleCategoryL = in.readInt();
        this.slugS = in.readString();
        this.entityOrParticipantTypeIdI = in.readInt();
        this.displayIdProfileId = in.readString();
        this.noOfViews = in.readInt();
        this.sectorIdL = in.readInt();
        this.sectorNameS = in.readString();
        this.jobCityIdL = in.readString();
        this.jobCityNameS = in.readString();
        this.isFromSearchFirmB = in.readByte() != 0;
        this.isSearchFirmB = in.readByte() != 0;
        this.hCompanyNameS = in.readString();
        this.hCompanyLogoS = in.readString();
        this.experienceFromI = in.readInt();
        this.experienceToI = in.readInt();
        this.searchIdJobOppTypes = new ArrayList<Integer>();
        in.readList(this.searchIdJobOppTypes, Integer.class.getClassLoader());
        this.searchTextJobEmpTypes = in.createStringArrayList();
        this.searchIdJobSkills = new ArrayList<Integer>();
        in.readList(this.searchIdJobSkills, Integer.class.getClassLoader());
        this.topCompanyTagLinkIdL = in.readString();
        this.companyProfileIdL = in.readInt();
        this.searchFirm = in.readByte() != 0;
        this.isBookmarked = in.readByte() != 0;
        this.isApplied = in.readByte() != 0;
        this.imagesIds = new ArrayList<Long>();
        in.readList(this.imagesIds, Long.class.getClassLoader());
        this.imageUrls = in.createStringArrayList();
        this.communityType = in.readString();
        this.communityTypeL = in.readString();
        this.active = in.readByte() != 0;
        this.id = in.readString();
        this.entityOrParticipantId = in.readLong();
        this.idOfEntityOrParticipant = in.readLong();
        this.type = in.readString();
        this.subType = in.readString();
        this.nameOrTitle = in.readString();
        this.imageUrl = in.readString();
        this.thumbnailImageUrl = in.readString();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.listShortDescription = in.readString();
        this.listDescription = in.readString();
        this.tags = in.createStringArrayList();
        this.tag_ids = new ArrayList<Long>();
        in.readList(this.tag_ids, Long.class.getClassLoader());
        this.isDeleted = in.readByte() != 0;
        this.isActive = in.readByte() != 0;
        this.createdDate = in.readString();
        this.isExpired = in.readByte() != 0;
        this.lastModifiedDate = in.readString();
        this.authorParticipantId = in.readLong();
        this.authorId = in.readLong();
        this.isAuthorConfidential = in.readByte() != 0;
        this.authorParticipantType = in.readString();
        this.authorFirstName = in.readString();
        this.authorLastName = in.readString();
        this.authorName = in.readString();
        this.authorImageUrl = in.readString();
        this.isAuthorImagePublic = in.readByte() != 0;
        this.authorCityId = in.readString();
        this.authorCityName = in.readString();
        this.authorShortDescription = in.readString();
        this.isFeatured = in.readByte() != 0;
        this.opportunityTypes = in.createStringArrayList();
        this.employmentTypes = in.createStringArrayList();
        this.skills = in.createStringArrayList();
        this.totalExperience = new ArrayList<Long>();
        in.readList(this.totalExperience, Long.class.getClassLoader());
        this.cityId = (Long) in.readValue(Long.class.getClassLoader());
        this.cityName = in.readString();
        this.skillIds = new ArrayList<Long>();
        in.readList(this.skillIds, Long.class.getClassLoader());
        this.searchTextSkills = in.createStringArrayList();
        this.opportunityTypeIds = new ArrayList<Long>();
        in.readList(this.opportunityTypeIds, Long.class.getClassLoader());
        this.searchIdOpportunityTypes = in.createStringArrayList();
        this.canHelpInIds = new ArrayList<Long>();
        in.readList(this.canHelpInIds, Long.class.getClassLoader());
        this.canHelpIns = in.createStringArrayList();
        this.experienceIds = new ArrayList<Long>();
        in.readList(this.experienceIds, Long.class.getClassLoader());
        this.experienceTitles = in.createStringArrayList();
        this.experienceCompanyIds = new ArrayList<Long>();
        in.readList(this.experienceCompanyIds, Long.class.getClassLoader());
        this.experienceCompanyNames = in.createStringArrayList();
        this.currExperienceIds = new ArrayList<Long>();
        in.readList(this.currExperienceIds, Long.class.getClassLoader());
        this.currExperienceTitles = in.createStringArrayList();
        this.currExperienceCompanyIds = new ArrayList<Long>();
        in.readList(this.currExperienceCompanyIds, Long.class.getClassLoader());
        this.currExperienceCompanyNames = in.createStringArrayList();
        this.educationIds = new ArrayList<Long>();
        in.readList(this.educationIds, Long.class.getClassLoader());
        this.educationSchoolIds = new ArrayList<Long>();
        in.readList(this.educationSchoolIds, Long.class.getClassLoader());
        this.educationSchoolNames = in.createStringArrayList();
        this.educationDegreeIds = new ArrayList<Long>();
        in.readList(this.educationDegreeIds, Long.class.getClassLoader());
        this.educationDegreeNames = in.createStringArrayList();
        this.gender = in.readString();
        this.currently_id = (Long) in.readValue(Long.class.getClassLoader());
        this.currently = in.readString();
        this.noOfChildren = in.readInt();
        this.interestId = new ArrayList<Long>();
        in.readList(this.interestId, Long.class.getClassLoader());
        this.interestNames = in.createStringArrayList();
        this.functionalAreaIds = new ArrayList<Long>();
        in.readList(this.functionalAreaIds, Long.class.getClassLoader());
        this.functionalAreaNames = in.createStringArrayList();
        this.lastActivityDate = in.createStringArrayList();
        this.reactionValue = in.readInt();
        this.noOfLikes = in.readInt();
        this.noOfComments = in.readInt();
        this.lastComments = in.createTypedArrayList(LastComment.CREATOR);
    }

    public static final Creator<FeedDetail> CREATOR = new Creator<FeedDetail>() {
        @Override
        public FeedDetail createFromParcel(Parcel source) {
            return new FeedDetail(source);
        }

        @Override
        public FeedDetail[] newArray(int size) {
            return new FeedDetail[size];
        }
    };
}
