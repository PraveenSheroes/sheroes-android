
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
    boolean isTrending;
    boolean isFromHome;
    @SerializedName("solr_ignore_deep_link_url")
    @Expose
    private String deepLinkUrl;

    /*Community post*/

    @SerializedName("search_id_post_image")
    @Expose
    private List<Long> imagesIds;
    @SerializedName("display_text_image_url")
    @Expose
    private List<String> imageUrls;
    @SerializedName("community_i")
    @Expose
    public long communityId;
    @SerializedName("is_commumity_post_b")
    @Expose
    public boolean isCommunityPost;
    @SerializedName("is_anonymous_b")
    @Expose
    public boolean isAnonymous;
    @SerializedName("solr_ignore_post_community_name")
    @Expose
    private String postCommunityName;
    @SerializedName("solr_ignore_is_closed")
    @Expose
    private boolean postCommunityClosed;

    /*Job*/
    @SerializedName("company_profile_id_l")
    @Expose
    private int companyProfileIdL;
    @SerializedName("top_company_tag_link_id_l")
    @Expose
    private String topCompanyTagLinkIdL;
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


    @SerializedName("job_Type_s")
    @Expose
    private String jobType;

    @SerializedName("end_date_dt")
    private String endDate;

    @SerializedName("start_date_dt")
    @Expose
    private String startDate;

    @SerializedName("is_assisted_b")
    @Expose
    private boolean isAssisted;

    @SerializedName("is_premium_b")
    @Expose
    private boolean isPremimiun;

    @SerializedName("is_ctc_required_b")
    @Expose
    private boolean isCtcRequired;

    @SerializedName("compensation_from_l")
    @Expose
    private long compensationFrom;

    @SerializedName("compensation_to_l")
    @Expose
    private long compensationTo;

    @SerializedName("no_of_openings_i")
    @Expose
    private int noOfOpenings;

    @SerializedName("compensation_details_s")
    @Expose
    private String compensationDetails;

    @SerializedName("company_master_id_l")
    @Expose
    private Long companyMasterId;

    @SerializedName("s_disp_external_application_url")
    @Expose
    private String externalApplicationUrl;

    @SerializedName("address_s")
    @Expose
    private String address;

    @SerializedName("s_disp_emailid")
    @Expose
    private String companyEmailId;
    @SerializedName("s_disp_compensation_currency")
    @Expose
    private String compensationCurrency;

    /*Community*/
    @SerializedName("community_type_s")
    @Expose
    public String communityType;

    @SerializedName("community_type_l")
    @Expose
    public long communityTypeL;

    /*Article*/
    @SerializedName("slug_s")
    @Expose
    private String slugS;
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

    /*User*/
    @SerializedName("total_exp_i")
    @Expose
    private int totalExperienceUser;
    @SerializedName("id_city_l")
    @Expose
    private Long cityId;
    @SerializedName("city_name_s")
    @Expose
    private String cityName;
    @SerializedName("search_id_job_skills")
    @Expose
    private List<Long> skillJobIds;
    @SerializedName("search_text_job_skills")
    @Expose
    private List<String> searchTextJobSkills;
    @SerializedName("search_id_opportunities")
    @Expose
    private List<Long> opportunityTypeIds;
    @SerializedName("search_text_opportunities")
    @Expose
    private List<String> searchIdOpportunityTypes;
    @SerializedName("search_id_can_help_in")
    @Expose
    private List<Long> canHelpInIds;
    @SerializedName("search_text_can_help_in")
    @Expose
    private List<String> canHelpIns;
    @SerializedName("search_id_experience")
    @Expose
    private List<Long> experienceIds;
    @SerializedName("search_text_experience_title")
    @Expose
    private List<String> experienceTitles;
    @SerializedName("search_id_experience_company")
    @Expose
    private List<Long> experienceCompanyIds;
    @SerializedName("search_text_experience_company")
    @Expose
    private List<String> experienceCompanyNames;
    @SerializedName("display_id_curr_experience")
    @Expose
    private List<Long> currExperienceIds;
    @SerializedName("display_text_curr_experience_title")
    @Expose
    private List<String> currExperienceTitles;
    @SerializedName("display_id_curr_experience_company")
    @Expose
    private List<Long> currExperienceCompanyIds;
    @SerializedName("display_text_curr_experience_company")
    @Expose
    private List<String> currExperienceCompanyNames;
    @SerializedName("search_id_education_id")
    @Expose
    private List<Long> educationIds;
    @SerializedName("search_id_education_school")
    @Expose
    private List<Long> educationSchoolIds;
    @SerializedName("search_text_education_school_name")
    @Expose
    private List<String> educationSchoolNames;
    @SerializedName("search_id_education_degree")
    @Expose
    private List<Long> educationDegreeIds;
    @SerializedName("search_text_education_degree_name")
    @Expose
    private List<String> educationDegreeNames;
    @SerializedName("search_id_field_of_study_master")
    @Expose
    private List<Long> fieldOfStudyMasterIds;
    @SerializedName("search_text_field_of_study_master_name")
    @Expose
    private List<String> fieldOfStudyMasterNames;
    @SerializedName("gender_s")
    @Expose
    private String gender;
    @SerializedName("currently_l")
    @Expose
    private long currently_id;
    @SerializedName("profile_id_l")
    @Expose
    private long profileId;
    @SerializedName("currently_s")
    @Expose
    private String currently;
    @SerializedName("no_of_children_i")
    @Expose
    private int noOfChildren;
    @SerializedName("interest_ls")
    @Expose
    private List<Long> interestId;
    @SerializedName("ineterest_ss")
    @Expose
    private List<String> interestNames;
    @SerializedName("functional_area_ls")
    @Expose
    private List<Long> functionalAreaIds;
    @SerializedName("functional_area_ss")
    @Expose
    private List<String> functionalAreaNames;
    @SerializedName("last_activity_date_dt")
    @Expose
    private List<String> lastActivityDate;

    /*Common*/
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("entity_or_participant_id")
    @Expose
    private long entityOrParticipantId;
    @SerializedName("entity_or_participant_type_id_i")
    @Expose
    private int entityOrParticipantTypeIdI;
    @SerializedName("display_id_profile_id")
    @Expose
    private String displayIdProfileId;
    @SerializedName("created_by_l")
    @Expose
    private long createdBy;
    @SerializedName("id_of_entity_or_participant")
    @Expose
    private long idOfEntityOrParticipant;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("name")
    @Expose
    private String nameOrTitle;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("thumbnail_image_url")
    @Expose
    private String thumbnailImageUrl;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("list_short_description")
    @Expose
    private String listShortDescription;
    @SerializedName("list_description")
    @Expose
    private String listDescription;
    @SerializedName("tag_names")
    @Expose
    private List<String> tags;
    @SerializedName("tag_ids")
    @Expose
    private List<Long> tag_ids;
    @SerializedName("p_is_deleted")
    @Expose
    private boolean isDeleted;
    @SerializedName("p_is_active")
    @Expose
    private boolean isActive;
    @SerializedName("p_crdt")
    @Expose
    private String createdDate;
    @SerializedName("posting_date_dt")
    @Expose
    private String postingDate;
    @SerializedName("posting_date_only_dt")
    @Expose
    private String postingDateOnly;
    @SerializedName("is_expired")
    @Expose
    private boolean isExpired;
    @SerializedName("p_last_modified_on")
    @Expose
    private String lastModifiedDate;
    @SerializedName("author_participant_id")
    @Expose
    private long authorParticipantId;
    @SerializedName("author_id")
    @Expose
    private long authorId;
    @SerializedName("is_author_confidential")
    @Expose
    private boolean isAuthorConfidential;
    @SerializedName("author_participant_type")
    @Expose
    private String authorParticipantType;
    @SerializedName("author_first_name")
    @Expose
    private String authorFirstName;
    @SerializedName("author_last_name")
    @Expose
    private String authorLastName;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("author_image_url")
    @Expose
    private String authorImageUrl;
    @SerializedName("is_author_image_public")
    @Expose
    private boolean isAuthorImagePublic;
    @SerializedName("author_city_id")
    @Expose
    private String authorCityId;
    @SerializedName("author_city_name")
    @Expose
    private String authorCityName;
    @SerializedName("author_short_description")
    @Expose
    private String authorShortDescription;
    @SerializedName("is_featured")
    @Expose
    private boolean isFeatured;
    /*Like and comment*/
    @SerializedName("solr_ignore_reacted_value")
    @Expose
    private int reactionValue;
    @SerializedName("solr_ignore_no_of_likes")
    @Expose
    private int noOfLikes;
    @SerializedName("solr_ignore_no_of_comments")
    @Expose
    private int noOfComments;
    @SerializedName("solr_ignore_last_comments")
    @Expose
    private List<LastComment> lastComments = null;
    @SerializedName("solr_ignore_no_of_views")
    @Expose
    private int noOfViews;
    @SerializedName("solr_ignore_is_applied")
    @Expose
    private boolean isApplied;
    @SerializedName("solr_ignore_is_bookmarked")
    @Expose
    private boolean isBookmarked;
    @SerializedName("solr_ignore_no_of_applies")
    @Expose
    private int noOfApplied;

    @SerializedName("solr_ignore_is_viewed")
    @Expose
    private boolean isViewed;
    @SerializedName("char_count_i")
    @Expose
    private int charCount;


    @SerializedName("is_closed_b")
    @Expose
    private boolean isClosedCommunity;
    @SerializedName("solr_ignore_no_of_members")
    @Expose
    private int noOfMembers;
    @SerializedName("solr_ignore_no_of_pending_requests")
    @Expose
    private int noOfPendingRequest;
    @SerializedName("solr_ignore_is_owner")
    @Expose
    private boolean isOwner;
    @SerializedName("solr_ignore_is_member")
    @Expose
    private boolean isMember;
    @SerializedName("solr_ignore_is_request_pending")
    @Expose
    private boolean isRequestPending;


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

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }

    public boolean isFromHome() {
        return isFromHome;
    }

    public void setFromHome(boolean fromHome) {
        isFromHome = fromHome;
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

    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public boolean isCommunityPost() {
        return isCommunityPost;
    }

    public void setCommunityPost(boolean communityPost) {
        isCommunityPost = communityPost;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getPostCommunityName() {
        return postCommunityName;
    }

    public void setPostCommunityName(String postCommunityName) {
        this.postCommunityName = postCommunityName;
    }

    public boolean isPostCommunityClosed() {
        return postCommunityClosed;
    }

    public void setPostCommunityClosed(boolean postCommunityClosed) {
        this.postCommunityClosed = postCommunityClosed;
    }

    public int getCompanyProfileIdL() {
        return companyProfileIdL;
    }

    public void setCompanyProfileIdL(int companyProfileIdL) {
        this.companyProfileIdL = companyProfileIdL;
    }

    public String getTopCompanyTagLinkIdL() {
        return topCompanyTagLinkIdL;
    }

    public void setTopCompanyTagLinkIdL(String topCompanyTagLinkIdL) {
        this.topCompanyTagLinkIdL = topCompanyTagLinkIdL;
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

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isAssisted() {
        return isAssisted;
    }

    public void setAssisted(boolean assisted) {
        isAssisted = assisted;
    }

    public boolean isPremimiun() {
        return isPremimiun;
    }

    public void setPremimiun(boolean premimiun) {
        isPremimiun = premimiun;
    }

    public boolean isCtcRequired() {
        return isCtcRequired;
    }

    public void setCtcRequired(boolean ctcRequired) {
        isCtcRequired = ctcRequired;
    }

    public long getCompensationFrom() {
        return compensationFrom;
    }

    public void setCompensationFrom(long compensationFrom) {
        this.compensationFrom = compensationFrom;
    }

    public long getCompensationTo() {
        return compensationTo;
    }

    public void setCompensationTo(long compensationTo) {
        this.compensationTo = compensationTo;
    }

    public int getNoOfOpenings() {
        return noOfOpenings;
    }

    public void setNoOfOpenings(int noOfOpenings) {
        this.noOfOpenings = noOfOpenings;
    }

    public String getCompensationDetails() {
        return compensationDetails;
    }

    public void setCompensationDetails(String compensationDetails) {
        this.compensationDetails = compensationDetails;
    }

    public Long getCompanyMasterId() {
        return companyMasterId;
    }

    public void setCompanyMasterId(Long companyMasterId) {
        this.companyMasterId = companyMasterId;
    }

    public String getExternalApplicationUrl() {
        return externalApplicationUrl;
    }

    public void setExternalApplicationUrl(String externalApplicationUrl) {
        this.externalApplicationUrl = externalApplicationUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyEmailId() {
        return companyEmailId;
    }

    public void setCompanyEmailId(String companyEmailId) {
        this.companyEmailId = companyEmailId;
    }

    public String getCommunityType() {
        return communityType;
    }

    public void setCommunityType(String communityType) {
        this.communityType = communityType;
    }

    public long getCommunityTypeL() {
        return communityTypeL;
    }

    public void setCommunityTypeL(long communityTypeL) {
        this.communityTypeL = communityTypeL;
    }

    public String getSlugS() {
        return slugS;
    }

    public void setSlugS(String slugS) {
        this.slugS = slugS;
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

    public int getTotalExperienceUser() {
        return totalExperienceUser;
    }

    public void setTotalExperienceUser(int totalExperienceUser) {
        this.totalExperienceUser = totalExperienceUser;
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

    public List<Long> getSkillJobIds() {
        return skillJobIds;
    }

    public void setSkillJobIds(List<Long> skillJobIds) {
        this.skillJobIds = skillJobIds;
    }

    public List<String> getSearchTextJobSkills() {
        return searchTextJobSkills;
    }

    public void setSearchTextJobSkills(List<String> searchTextJobSkills) {
        this.searchTextJobSkills = searchTextJobSkills;
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

    public long getProfileId() {
        return profileId;
    }

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

    public List<String> getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(List<String> lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
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

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getPostingDateOnly() {
        return postingDateOnly;
    }

    public void setPostingDateOnly(String postingDateOnly) {
        this.postingDateOnly = postingDateOnly;
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

    public int getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(int noOfViews) {
        this.noOfViews = noOfViews;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public void setApplied(boolean applied) {
        isApplied = applied;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    public int getNoOfApplied() {
        return noOfApplied;
    }

    public void setNoOfApplied(int noOfApplied) {
        this.noOfApplied = noOfApplied;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void setViewed(boolean viewed) {
        isViewed = viewed;
    }

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public boolean isClosedCommunity() {
        return isClosedCommunity;
    }

    public void setClosedCommunity(boolean closedCommunity) {
        isClosedCommunity = closedCommunity;
    }

    public int getNoOfMembers() {
        return noOfMembers;
    }

    public void setNoOfMembers(int noOfMembers) {
        this.noOfMembers = noOfMembers;
    }

    public int getNoOfPendingRequest() {
        return noOfPendingRequest;
    }

    public void setNoOfPendingRequest(int noOfPendingRequest) {
        this.noOfPendingRequest = noOfPendingRequest;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public boolean isRequestPending() {
        return isRequestPending;
    }

    public void setRequestPending(boolean requestPending) {
        isRequestPending = requestPending;
    }

    public FeedDetail() {
    }

    public String getCompensationCurrency() {
        return compensationCurrency;
    }

    public void setCompensationCurrency(String compensationCurrency) {
        this.compensationCurrency = compensationCurrency;
    }

    public String getDeepLinkUrl() {
        return deepLinkUrl;
    }

    public void setDeepLinkUrl(String deepLinkUrl) {
        this.deepLinkUrl = deepLinkUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemPosition);
        dest.writeByte(this.isLongPress ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isTrending ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isFromHome ? (byte) 1 : (byte) 0);
        dest.writeString(this.deepLinkUrl);
        dest.writeList(this.imagesIds);
        dest.writeStringList(this.imageUrls);
        dest.writeLong(this.communityId);
        dest.writeByte(this.isCommunityPost ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAnonymous ? (byte) 1 : (byte) 0);
        dest.writeString(this.postCommunityName);
        dest.writeByte(this.postCommunityClosed ? (byte) 1 : (byte) 0);
        dest.writeInt(this.companyProfileIdL);
        dest.writeString(this.topCompanyTagLinkIdL);
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
        dest.writeString(this.jobType);
        dest.writeString(this.endDate);
        dest.writeString(this.startDate);
        dest.writeByte(this.isAssisted ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isPremimiun ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isCtcRequired ? (byte) 1 : (byte) 0);
        dest.writeLong(this.compensationFrom);
        dest.writeLong(this.compensationTo);
        dest.writeInt(this.noOfOpenings);
        dest.writeString(this.compensationDetails);
        dest.writeValue(this.companyMasterId);
        dest.writeString(this.externalApplicationUrl);
        dest.writeString(this.address);
        dest.writeString(this.companyEmailId);
        dest.writeString(this.compensationCurrency);
        dest.writeString(this.communityType);
        dest.writeLong(this.communityTypeL);
        dest.writeString(this.slugS);
        dest.writeString(this.publishedInS);
        dest.writeString(this.metaDescriptionS);
        dest.writeString(this.metaTitleS);
        dest.writeString(this.articleStatusI);
        dest.writeString(this.thirdPartyIdS);
        dest.writeString(this.articleCategoryNameS);
        dest.writeInt(this.articleCategoryL);
        dest.writeInt(this.totalExperienceUser);
        dest.writeValue(this.cityId);
        dest.writeString(this.cityName);
        dest.writeList(this.skillJobIds);
        dest.writeStringList(this.searchTextJobSkills);
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
        dest.writeList(this.fieldOfStudyMasterIds);
        dest.writeStringList(this.fieldOfStudyMasterNames);
        dest.writeString(this.gender);
        dest.writeLong(this.currently_id);
        dest.writeLong(this.profileId);
        dest.writeString(this.currently);
        dest.writeInt(this.noOfChildren);
        dest.writeList(this.interestId);
        dest.writeStringList(this.interestNames);
        dest.writeList(this.functionalAreaIds);
        dest.writeStringList(this.functionalAreaNames);
        dest.writeStringList(this.lastActivityDate);
        dest.writeString(this.id);
        dest.writeLong(this.entityOrParticipantId);
        dest.writeInt(this.entityOrParticipantTypeIdI);
        dest.writeString(this.displayIdProfileId);
        dest.writeLong(this.createdBy);
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
        dest.writeString(this.postingDate);
        dest.writeString(this.postingDateOnly);
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
        dest.writeInt(this.reactionValue);
        dest.writeInt(this.noOfLikes);
        dest.writeInt(this.noOfComments);
        dest.writeTypedList(this.lastComments);
        dest.writeInt(this.noOfViews);
        dest.writeByte(this.isApplied ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isBookmarked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.noOfApplied);
        dest.writeByte(this.isViewed ? (byte) 1 : (byte) 0);
        dest.writeInt(this.charCount);
        dest.writeByte(this.isClosedCommunity ? (byte) 1 : (byte) 0);
        dest.writeInt(this.noOfMembers);
        dest.writeInt(this.noOfPendingRequest);
        dest.writeByte(this.isOwner ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isMember ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isRequestPending ? (byte) 1 : (byte) 0);
    }

    protected FeedDetail(Parcel in) {
        this.itemPosition = in.readInt();
        this.isLongPress = in.readByte() != 0;
        this.isTrending = in.readByte() != 0;
        this.isFromHome = in.readByte() != 0;
        this.deepLinkUrl = in.readString();
        this.imagesIds = new ArrayList<Long>();
        in.readList(this.imagesIds, Long.class.getClassLoader());
        this.imageUrls = in.createStringArrayList();
        this.communityId = in.readLong();
        this.isCommunityPost = in.readByte() != 0;
        this.isAnonymous = in.readByte() != 0;
        this.postCommunityName = in.readString();
        this.postCommunityClosed = in.readByte() != 0;
        this.companyProfileIdL = in.readInt();
        this.topCompanyTagLinkIdL = in.readString();
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
        this.jobType = in.readString();
        this.endDate = in.readString();
        this.startDate = in.readString();
        this.isAssisted = in.readByte() != 0;
        this.isPremimiun = in.readByte() != 0;
        this.isCtcRequired = in.readByte() != 0;
        this.compensationFrom = in.readLong();
        this.compensationTo = in.readLong();
        this.noOfOpenings = in.readInt();
        this.compensationDetails = in.readString();
        this.companyMasterId = (Long) in.readValue(Long.class.getClassLoader());
        this.externalApplicationUrl = in.readString();
        this.address = in.readString();
        this.companyEmailId = in.readString();
        this.compensationCurrency = in.readString();
        this.communityType = in.readString();
        this.communityTypeL = in.readLong();
        this.slugS = in.readString();
        this.publishedInS = in.readString();
        this.metaDescriptionS = in.readString();
        this.metaTitleS = in.readString();
        this.articleStatusI = in.readString();
        this.thirdPartyIdS = in.readString();
        this.articleCategoryNameS = in.readString();
        this.articleCategoryL = in.readInt();
        this.totalExperienceUser = in.readInt();
        this.cityId = (Long) in.readValue(Long.class.getClassLoader());
        this.cityName = in.readString();
        this.skillJobIds = new ArrayList<Long>();
        in.readList(this.skillJobIds, Long.class.getClassLoader());
        this.searchTextJobSkills = in.createStringArrayList();
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
        this.fieldOfStudyMasterIds = new ArrayList<Long>();
        in.readList(this.fieldOfStudyMasterIds, Long.class.getClassLoader());
        this.fieldOfStudyMasterNames = in.createStringArrayList();
        this.gender = in.readString();
        this.currently_id = in.readLong();
        this.profileId = in.readLong();
        this.currently = in.readString();
        this.noOfChildren = in.readInt();
        this.interestId = new ArrayList<Long>();
        in.readList(this.interestId, Long.class.getClassLoader());
        this.interestNames = in.createStringArrayList();
        this.functionalAreaIds = new ArrayList<Long>();
        in.readList(this.functionalAreaIds, Long.class.getClassLoader());
        this.functionalAreaNames = in.createStringArrayList();
        this.lastActivityDate = in.createStringArrayList();
        this.id = in.readString();
        this.entityOrParticipantId = in.readLong();
        this.entityOrParticipantTypeIdI = in.readInt();
        this.displayIdProfileId = in.readString();
        this.createdBy = in.readLong();
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
        this.postingDate = in.readString();
        this.postingDateOnly = in.readString();
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
        this.reactionValue = in.readInt();
        this.noOfLikes = in.readInt();
        this.noOfComments = in.readInt();
        this.lastComments = in.createTypedArrayList(LastComment.CREATOR);
        this.noOfViews = in.readInt();
        this.isApplied = in.readByte() != 0;
        this.isBookmarked = in.readByte() != 0;
        this.noOfApplied = in.readInt();
        this.isViewed = in.readByte() != 0;
        this.charCount = in.readInt();
        this.isClosedCommunity = in.readByte() != 0;
        this.noOfMembers = in.readInt();
        this.noOfPendingRequest = in.readInt();
        this.isOwner = in.readByte() != 0;
        this.isMember = in.readByte() != 0;
        this.isRequestPending = in.readByte() != 0;
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
