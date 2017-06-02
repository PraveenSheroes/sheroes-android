
package appliedlife.pvtltd.SHEROES.models.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppliedJob {

    @SerializedName("address_s")
    @Expose
    private String addressS;
    @SerializedName("author_city_id")
    @Expose
    private int authorCityId;
    @SerializedName("author_city_name")
    @Expose
    private String authorCityName;
    @SerializedName("author_first_name")
    @Expose
    private String authorFirstName;
    @SerializedName("author_id")
    @Expose
    private int authorId;
    @SerializedName("author_image_url")
    @Expose
    private String authorImageUrl;
    @SerializedName("author_last_name")
    @Expose
    private String authorLastName;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("author_participant_id")
    @Expose
    private int authorParticipantId;
    @SerializedName("author_participant_type")
    @Expose
    private String authorParticipantType;
    @SerializedName("author_short_description")
    @Expose
    private String authorShortDescription;
    @SerializedName("company_master_id_l")
    @Expose
    private int companyMasterIdL;
    @SerializedName("company_profile_id_l")
    @Expose
    private int companyProfileIdL;
    @SerializedName("compensation_details_s")
    @Expose
    private String compensationDetailsS;
    @SerializedName("compensation_from_l")
    @Expose
    private int compensationFromL;
    @SerializedName("compensation_to_l")
    @Expose
    private int compensationToL;
    @SerializedName("created_by_l")
    @Expose
    private int createdByL;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("display_id_profile_id")
    @Expose
    private int displayIdProfileId;
    @SerializedName("end_date_dt")
    @Expose
    private String endDateDt;
    @SerializedName("entity_or_participant_id")
    @Expose
    private int entityOrParticipantId;
    @SerializedName("entity_or_participant_type_id_i")
    @Expose
    private int entityOrParticipantTypeIdI;
    @SerializedName("experience_from_i")
    @Expose
    private int experienceFromI;
    @SerializedName("experience_to_i")
    @Expose
    private int experienceToI;
    @SerializedName("h_company_logo_s")
    @Expose
    private String hCompanyLogoS;
    @SerializedName("h_company_name_s")
    @Expose
    private String hCompanyNameS;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_of_entity_or_participant")
    @Expose
    private int idOfEntityOrParticipant;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("is_assisted_b")
    @Expose
    private boolean isAssistedB;
    @SerializedName("is_author_confidential")
    @Expose
    private boolean isAuthorConfidential;
    @SerializedName("is_author_image_public")
    @Expose
    private boolean isAuthorImagePublic;
    @SerializedName("is_ctc_required_b")
    @Expose
    private boolean isCtcRequiredB;
    @SerializedName("is_expired")
    @Expose
    private boolean isExpired;
    @SerializedName("is_featured")
    @Expose
    private boolean isFeatured;
    @SerializedName("is_from_search_firm_b")
    @Expose
    private boolean isFromSearchFirmB;
    @SerializedName("is_premium_b")
    @Expose
    private boolean isPremiumB;
    @SerializedName("is_search_firm_b")
    @Expose
    private boolean isSearchFirmB;
    @SerializedName("job_Type_s")
    @Expose
    private String jobTypeS;
    @SerializedName("job_city_id_l")
    @Expose
    private int jobCityIdL;
    @SerializedName("job_city_name_s")
    @Expose
    private String jobCityNameS;
    @SerializedName("list_description")
    @Expose
    private String listDescription;
    @SerializedName("list_short_description")
    @Expose
    private String listShortDescription;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("no_of_openings_i")
    @Expose
    private int noOfOpeningsI;
    @SerializedName("p_crdt")
    @Expose
    private String pCrdt;
    @SerializedName("p_is_active")
    @Expose
    private boolean pIsActive;
    @SerializedName("p_is_deleted")
    @Expose
    private boolean pIsDeleted;
    @SerializedName("p_last_modified_on")
    @Expose
    private String pLastModifiedOn;
    @SerializedName("posting_date_dt")
    @Expose
    private String postingDateDt;
    @SerializedName("posting_date_only_dt")
    @Expose
    private String postingDateOnlyDt;
    @SerializedName("s_disp_compensation_currency")
    @Expose
    private String sDispCompensationCurrency;
    @SerializedName("s_disp_emailid")
    @Expose
    private String sDispEmailid;
    @SerializedName("s_disp_external_application_url")
    @Expose
    private String sDispExternalApplicationUrl;
    @SerializedName("search_id_job_emp_types")
    @Expose
    private List<Integer> searchIdJobEmpTypes = null;
    @SerializedName("search_id_job_opp_types")
    @Expose
    private List<Integer> searchIdJobOppTypes = null;
    @SerializedName("search_id_job_skills")
    @Expose
    private List<Integer> searchIdJobSkills = null;
    @SerializedName("search_text_job_emp_types")
    @Expose
    private List<String> searchTextJobEmpTypes = null;
    @SerializedName("search_text_job_opp_types")
    @Expose
    private List<String> searchTextJobOppTypes = null;
    @SerializedName("search_text_job_skills")
    @Expose
    private List<String> searchTextJobSkills = null;
    @SerializedName("sector_id_l")
    @Expose
    private int sectorIdL;
    @SerializedName("sector_name_s")
    @Expose
    private String sectorNameS;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("solr_ignore_deep_link_url")
    @Expose
    private String solrIgnoreDeepLinkUrl;
    @SerializedName("solr_ignore_end_date_dt")
    @Expose
    private String solrIgnoreEndDateDt;
    @SerializedName("solr_ignore_is_applied")
    @Expose
    private boolean solrIgnoreIsApplied;
    @SerializedName("solr_ignore_is_bookmarked")
    @Expose
    private boolean solrIgnoreIsBookmarked;
    @SerializedName("solr_ignore_is_viewed")
    @Expose
    private boolean solrIgnoreIsViewed;
 
    @SerializedName("solr_ignore_no_of_applies")
    @Expose
    private int solrIgnoreNoOfApplies;
    @SerializedName("solr_ignore_no_of_comments")
    @Expose
    private int solrIgnoreNoOfComments;
    @SerializedName("solr_ignore_no_of_likes")
    @Expose
    private int solrIgnoreNoOfLikes;
    @SerializedName("solr_ignore_no_of_views")
    @Expose
    private int solrIgnoreNoOfViews;
    @SerializedName("solr_ignore_p_crdt")
    @Expose
    private String solrIgnorePCrdt;
    @SerializedName("solr_ignore_p_last_modified_on")
    @Expose
    private String solrIgnorePLastModifiedOn;
    @SerializedName("solr_ignore_posting_date_dt")
    @Expose
    private String solrIgnorePostingDateDt;
    @SerializedName("solr_ignore_posting_date_only_dt")
    @Expose
    private String solrIgnorePostingDateOnlyDt;
    @SerializedName("solr_ignore_reacted_value")
    @Expose
    private int solrIgnoreReactedValue;
    @SerializedName("solr_ignore_start_date_dt")
    @Expose
    private String solrIgnoreStartDateDt;
    @SerializedName("start_date_dt")
    @Expose
    private String startDateDt;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("tag_ids")
    @Expose
    private List<Integer> tagIds = null;
    @SerializedName("tag_names")
    @Expose
    private List<String> tagNames = null;
    @SerializedName("thumbnail_image_url")
    @Expose
    private String thumbnailImageUrl;
    @SerializedName("top_company_tag_link_id_l")
    @Expose
    private int topCompanyTagLinkIdL;
    @SerializedName("type")
    @Expose
    private String type;


    public String getAddressS() {
        return addressS;
    }

    public void setAddressS(String addressS) {
        this.addressS = addressS;
    }

    public int getAuthorCityId() {
        return authorCityId;
    }

    public void setAuthorCityId(int authorCityId) {
        this.authorCityId = authorCityId;
    }

    public String getAuthorCityName() {
        return authorCityName;
    }

    public void setAuthorCityName(String authorCityName) {
        this.authorCityName = authorCityName;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
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

    public int getAuthorParticipantId() {
        return authorParticipantId;
    }

    public void setAuthorParticipantId(int authorParticipantId) {
        this.authorParticipantId = authorParticipantId;
    }

    public String getAuthorParticipantType() {
        return authorParticipantType;
    }

    public void setAuthorParticipantType(String authorParticipantType) {
        this.authorParticipantType = authorParticipantType;
    }

    public String getAuthorShortDescription() {
        return authorShortDescription;
    }

    public void setAuthorShortDescription(String authorShortDescription) {
        this.authorShortDescription = authorShortDescription;
    }

    public int getCompanyMasterIdL() {
        return companyMasterIdL;
    }

    public void setCompanyMasterIdL(int companyMasterIdL) {
        this.companyMasterIdL = companyMasterIdL;
    }

    public int getCompanyProfileIdL() {
        return companyProfileIdL;
    }

    public void setCompanyProfileIdL(int companyProfileIdL) {
        this.companyProfileIdL = companyProfileIdL;
    }

    public String getCompensationDetailsS() {
        return compensationDetailsS;
    }

    public void setCompensationDetailsS(String compensationDetailsS) {
        this.compensationDetailsS = compensationDetailsS;
    }

    public int getCompensationFromL() {
        return compensationFromL;
    }

    public void setCompensationFromL(int compensationFromL) {
        this.compensationFromL = compensationFromL;
    }

    public int getCompensationToL() {
        return compensationToL;
    }

    public void setCompensationToL(int compensationToL) {
        this.compensationToL = compensationToL;
    }

    public int getCreatedByL() {
        return createdByL;
    }

    public void setCreatedByL(int createdByL) {
        this.createdByL = createdByL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDisplayIdProfileId() {
        return displayIdProfileId;
    }

    public void setDisplayIdProfileId(int displayIdProfileId) {
        this.displayIdProfileId = displayIdProfileId;
    }

    public String getEndDateDt() {
        return endDateDt;
    }

    public void setEndDateDt(String endDateDt) {
        this.endDateDt = endDateDt;
    }

    public int getEntityOrParticipantId() {
        return entityOrParticipantId;
    }

    public void setEntityOrParticipantId(int entityOrParticipantId) {
        this.entityOrParticipantId = entityOrParticipantId;
    }

    public int getEntityOrParticipantTypeIdI() {
        return entityOrParticipantTypeIdI;
    }

    public void setEntityOrParticipantTypeIdI(int entityOrParticipantTypeIdI) {
        this.entityOrParticipantTypeIdI = entityOrParticipantTypeIdI;
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

    public String gethCompanyLogoS() {
        return hCompanyLogoS;
    }

    public void sethCompanyLogoS(String hCompanyLogoS) {
        this.hCompanyLogoS = hCompanyLogoS;
    }

    public String gethCompanyNameS() {
        return hCompanyNameS;
    }

    public void sethCompanyNameS(String hCompanyNameS) {
        this.hCompanyNameS = hCompanyNameS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdOfEntityOrParticipant() {
        return idOfEntityOrParticipant;
    }

    public void setIdOfEntityOrParticipant(int idOfEntityOrParticipant) {
        this.idOfEntityOrParticipant = idOfEntityOrParticipant;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isAssistedB() {
        return isAssistedB;
    }

    public void setAssistedB(boolean assistedB) {
        isAssistedB = assistedB;
    }

    public boolean isAuthorConfidential() {
        return isAuthorConfidential;
    }

    public void setAuthorConfidential(boolean authorConfidential) {
        isAuthorConfidential = authorConfidential;
    }

    public boolean isAuthorImagePublic() {
        return isAuthorImagePublic;
    }

    public void setAuthorImagePublic(boolean authorImagePublic) {
        isAuthorImagePublic = authorImagePublic;
    }

    public boolean isCtcRequiredB() {
        return isCtcRequiredB;
    }

    public void setCtcRequiredB(boolean ctcRequiredB) {
        isCtcRequiredB = ctcRequiredB;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public boolean isFromSearchFirmB() {
        return isFromSearchFirmB;
    }

    public void setFromSearchFirmB(boolean fromSearchFirmB) {
        isFromSearchFirmB = fromSearchFirmB;
    }

    public boolean isPremiumB() {
        return isPremiumB;
    }

    public void setPremiumB(boolean premiumB) {
        isPremiumB = premiumB;
    }

    public boolean isSearchFirmB() {
        return isSearchFirmB;
    }

    public void setSearchFirmB(boolean searchFirmB) {
        isSearchFirmB = searchFirmB;
    }

    public String getJobTypeS() {
        return jobTypeS;
    }

    public void setJobTypeS(String jobTypeS) {
        this.jobTypeS = jobTypeS;
    }

    public int getJobCityIdL() {
        return jobCityIdL;
    }

    public void setJobCityIdL(int jobCityIdL) {
        this.jobCityIdL = jobCityIdL;
    }

    public String getJobCityNameS() {
        return jobCityNameS;
    }

    public void setJobCityNameS(String jobCityNameS) {
        this.jobCityNameS = jobCityNameS;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public String getListShortDescription() {
        return listShortDescription;
    }

    public void setListShortDescription(String listShortDescription) {
        this.listShortDescription = listShortDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfOpeningsI() {
        return noOfOpeningsI;
    }

    public void setNoOfOpeningsI(int noOfOpeningsI) {
        this.noOfOpeningsI = noOfOpeningsI;
    }

    public String getpCrdt() {
        return pCrdt;
    }

    public void setpCrdt(String pCrdt) {
        this.pCrdt = pCrdt;
    }

    public boolean ispIsActive() {
        return pIsActive;
    }

    public void setpIsActive(boolean pIsActive) {
        this.pIsActive = pIsActive;
    }

    public boolean ispIsDeleted() {
        return pIsDeleted;
    }

    public void setpIsDeleted(boolean pIsDeleted) {
        this.pIsDeleted = pIsDeleted;
    }

    public String getpLastModifiedOn() {
        return pLastModifiedOn;
    }

    public void setpLastModifiedOn(String pLastModifiedOn) {
        this.pLastModifiedOn = pLastModifiedOn;
    }

    public String getPostingDateDt() {
        return postingDateDt;
    }

    public void setPostingDateDt(String postingDateDt) {
        this.postingDateDt = postingDateDt;
    }

    public String getPostingDateOnlyDt() {
        return postingDateOnlyDt;
    }

    public void setPostingDateOnlyDt(String postingDateOnlyDt) {
        this.postingDateOnlyDt = postingDateOnlyDt;
    }

    public String getsDispCompensationCurrency() {
        return sDispCompensationCurrency;
    }

    public void setsDispCompensationCurrency(String sDispCompensationCurrency) {
        this.sDispCompensationCurrency = sDispCompensationCurrency;
    }

    public String getsDispEmailid() {
        return sDispEmailid;
    }

    public void setsDispEmailid(String sDispEmailid) {
        this.sDispEmailid = sDispEmailid;
    }

    public String getsDispExternalApplicationUrl() {
        return sDispExternalApplicationUrl;
    }

    public void setsDispExternalApplicationUrl(String sDispExternalApplicationUrl) {
        this.sDispExternalApplicationUrl = sDispExternalApplicationUrl;
    }

    public List<Integer> getSearchIdJobEmpTypes() {
        return searchIdJobEmpTypes;
    }

    public void setSearchIdJobEmpTypes(List<Integer> searchIdJobEmpTypes) {
        this.searchIdJobEmpTypes = searchIdJobEmpTypes;
    }

    public List<Integer> getSearchIdJobOppTypes() {
        return searchIdJobOppTypes;
    }

    public void setSearchIdJobOppTypes(List<Integer> searchIdJobOppTypes) {
        this.searchIdJobOppTypes = searchIdJobOppTypes;
    }

    public List<Integer> getSearchIdJobSkills() {
        return searchIdJobSkills;
    }

    public void setSearchIdJobSkills(List<Integer> searchIdJobSkills) {
        this.searchIdJobSkills = searchIdJobSkills;
    }

    public List<String> getSearchTextJobEmpTypes() {
        return searchTextJobEmpTypes;
    }

    public void setSearchTextJobEmpTypes(List<String> searchTextJobEmpTypes) {
        this.searchTextJobEmpTypes = searchTextJobEmpTypes;
    }

    public List<String> getSearchTextJobOppTypes() {
        return searchTextJobOppTypes;
    }

    public void setSearchTextJobOppTypes(List<String> searchTextJobOppTypes) {
        this.searchTextJobOppTypes = searchTextJobOppTypes;
    }

    public List<String> getSearchTextJobSkills() {
        return searchTextJobSkills;
    }

    public void setSearchTextJobSkills(List<String> searchTextJobSkills) {
        this.searchTextJobSkills = searchTextJobSkills;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSolrIgnoreDeepLinkUrl() {
        return solrIgnoreDeepLinkUrl;
    }

    public void setSolrIgnoreDeepLinkUrl(String solrIgnoreDeepLinkUrl) {
        this.solrIgnoreDeepLinkUrl = solrIgnoreDeepLinkUrl;
    }

    public String getSolrIgnoreEndDateDt() {
        return solrIgnoreEndDateDt;
    }

    public void setSolrIgnoreEndDateDt(String solrIgnoreEndDateDt) {
        this.solrIgnoreEndDateDt = solrIgnoreEndDateDt;
    }

    public boolean isSolrIgnoreIsApplied() {
        return solrIgnoreIsApplied;
    }

    public void setSolrIgnoreIsApplied(boolean solrIgnoreIsApplied) {
        this.solrIgnoreIsApplied = solrIgnoreIsApplied;
    }

    public boolean isSolrIgnoreIsBookmarked() {
        return solrIgnoreIsBookmarked;
    }

    public void setSolrIgnoreIsBookmarked(boolean solrIgnoreIsBookmarked) {
        this.solrIgnoreIsBookmarked = solrIgnoreIsBookmarked;
    }

    public boolean isSolrIgnoreIsViewed() {
        return solrIgnoreIsViewed;
    }

    public void setSolrIgnoreIsViewed(boolean solrIgnoreIsViewed) {
        this.solrIgnoreIsViewed = solrIgnoreIsViewed;
    }

    public int getSolrIgnoreNoOfApplies() {
        return solrIgnoreNoOfApplies;
    }

    public void setSolrIgnoreNoOfApplies(int solrIgnoreNoOfApplies) {
        this.solrIgnoreNoOfApplies = solrIgnoreNoOfApplies;
    }

    public int getSolrIgnoreNoOfComments() {
        return solrIgnoreNoOfComments;
    }

    public void setSolrIgnoreNoOfComments(int solrIgnoreNoOfComments) {
        this.solrIgnoreNoOfComments = solrIgnoreNoOfComments;
    }

    public int getSolrIgnoreNoOfLikes() {
        return solrIgnoreNoOfLikes;
    }

    public void setSolrIgnoreNoOfLikes(int solrIgnoreNoOfLikes) {
        this.solrIgnoreNoOfLikes = solrIgnoreNoOfLikes;
    }

    public int getSolrIgnoreNoOfViews() {
        return solrIgnoreNoOfViews;
    }

    public void setSolrIgnoreNoOfViews(int solrIgnoreNoOfViews) {
        this.solrIgnoreNoOfViews = solrIgnoreNoOfViews;
    }

    public String getSolrIgnorePCrdt() {
        return solrIgnorePCrdt;
    }

    public void setSolrIgnorePCrdt(String solrIgnorePCrdt) {
        this.solrIgnorePCrdt = solrIgnorePCrdt;
    }

    public String getSolrIgnorePLastModifiedOn() {
        return solrIgnorePLastModifiedOn;
    }

    public void setSolrIgnorePLastModifiedOn(String solrIgnorePLastModifiedOn) {
        this.solrIgnorePLastModifiedOn = solrIgnorePLastModifiedOn;
    }

    public String getSolrIgnorePostingDateDt() {
        return solrIgnorePostingDateDt;
    }

    public void setSolrIgnorePostingDateDt(String solrIgnorePostingDateDt) {
        this.solrIgnorePostingDateDt = solrIgnorePostingDateDt;
    }

    public String getSolrIgnorePostingDateOnlyDt() {
        return solrIgnorePostingDateOnlyDt;
    }

    public void setSolrIgnorePostingDateOnlyDt(String solrIgnorePostingDateOnlyDt) {
        this.solrIgnorePostingDateOnlyDt = solrIgnorePostingDateOnlyDt;
    }

    public int getSolrIgnoreReactedValue() {
        return solrIgnoreReactedValue;
    }

    public void setSolrIgnoreReactedValue(int solrIgnoreReactedValue) {
        this.solrIgnoreReactedValue = solrIgnoreReactedValue;
    }

    public String getSolrIgnoreStartDateDt() {
        return solrIgnoreStartDateDt;
    }

    public void setSolrIgnoreStartDateDt(String solrIgnoreStartDateDt) {
        this.solrIgnoreStartDateDt = solrIgnoreStartDateDt;
    }

    public String getStartDateDt() {
        return startDateDt;
    }

    public void setStartDateDt(String startDateDt) {
        this.startDateDt = startDateDt;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public int getTopCompanyTagLinkIdL() {
        return topCompanyTagLinkIdL;
    }

    public void setTopCompanyTagLinkIdL(int topCompanyTagLinkIdL) {
        this.topCompanyTagLinkIdL = topCompanyTagLinkIdL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
