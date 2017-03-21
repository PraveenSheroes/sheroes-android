
package appliedlife.pvtltd.SHEROES.models.entities.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppliedJob {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("entity_or_participant_id")
    @Expose
    private Integer entityOrParticipantId;
    @SerializedName("entity_or_participant_type_id_i")
    @Expose
    private Integer entityOrParticipantTypeIdI;
    @SerializedName("display_id_profile_id")
    @Expose
    private Object displayIdProfileId;
    @SerializedName("created_by_l")
    @Expose
    private Object createdByL;
    @SerializedName("id_of_entity_or_participant")
    @Expose
    private Integer idOfEntityOrParticipant;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_url")
    @Expose
    private Object imageUrl;
    @SerializedName("thumbnail_image_url")
    @Expose
    private Object thumbnailImageUrl;
    @SerializedName("short_description")
    @Expose
    private Object shortDescription;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("list_short_description")
    @Expose
    private Object listShortDescription;
    @SerializedName("list_description")
    @Expose
    private String listDescription;
    @SerializedName("tag_names")
    @Expose
    private Object tagNames;
    @SerializedName("tag_ids")
    @Expose
    private Object tagIds;
    @SerializedName("p_is_deleted")
    @Expose
    private Boolean pIsDeleted;
    @SerializedName("p_is_active")
    @Expose
    private Boolean pIsActive;
    @SerializedName("p_crdt")
    @Expose
    private String pCrdt;
    @SerializedName("posting_date_dt")
    @Expose
    private String postingDateDt;
    @SerializedName("posting_date_only_dt")
    @Expose
    private String postingDateOnlyDt;
    @SerializedName("is_expired")
    @Expose
    private Boolean isExpired;
    @SerializedName("p_last_modified_on")
    @Expose
    private String pLastModifiedOn;
    @SerializedName("author_participant_id")
    @Expose
    private Integer authorParticipantId;
    @SerializedName("author_id")
    @Expose
    private Integer authorId;
    @SerializedName("is_author_confidential")
    @Expose
    private Boolean isAuthorConfidential;
    @SerializedName("author_participant_type")
    @Expose
    private String authorParticipantType;
    @SerializedName("author_first_name")
    @Expose
    private Object authorFirstName;
    @SerializedName("author_last_name")
    @Expose
    private Object authorLastName;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("author_image_url")
    @Expose
    private String authorImageUrl;
    @SerializedName("is_author_image_public")
    @Expose
    private Boolean isAuthorImagePublic;
    @SerializedName("author_city_id")
    @Expose
    private String authorCityId;
    @SerializedName("author_city_name")
    @Expose
    private String authorCityName;
    @SerializedName("author_short_description")
    @Expose
    private Object authorShortDescription;
    @SerializedName("is_featured")
    @Expose
    private Boolean isFeatured;
    @SerializedName("solr_ignore_no_of_likes")
    @Expose
    private Integer solrIgnoreNoOfLikes;
    @SerializedName("solr_ignore_no_of_comments")
    @Expose
    private Integer solrIgnoreNoOfComments;
    @SerializedName("solr_ignore_last_comments")
    @Expose
    private Object solrIgnoreLastComments;
    @SerializedName("solr_ignore_reacted_value")
    @Expose
    private Integer solrIgnoreReactedValue;
    @SerializedName("solr_ignore_is_bookmarked")
    @Expose
    private Boolean solrIgnoreIsBookmarked;
    @SerializedName("solr_ignore_no_of_views")
    @Expose
    private Integer solrIgnoreNoOfViews;
    @SerializedName("solr_ignore_is_applied")
    @Expose
    private Boolean solrIgnoreIsApplied;
    @SerializedName("solr_ignore_is_viewed")
    @Expose
    private Boolean solrIgnoreIsViewed;
    @SerializedName("solr_ignore_no_of_applies")
    @Expose
    private Integer solrIgnoreNoOfApplies;
    @SerializedName("company_profile_id_l")
    @Expose
    private Integer companyProfileIdL;
    @SerializedName("top_company_tag_link_id_l")
    @Expose
    private Object topCompanyTagLinkIdL;
    @SerializedName("sector_id_l")
    @Expose
    private Object sectorIdL;
    @SerializedName("sector_name_s")
    @Expose
    private Object sectorNameS;
    @SerializedName("job_city_id_l")
    @Expose
    private Object jobCityIdL;
    @SerializedName("job_city_name_s")
    @Expose
    private Object jobCityNameS;
    @SerializedName("is_from_search_firm_b")
    @Expose
    private Boolean isFromSearchFirmB;
    @SerializedName("is_search_firm_b")
    @Expose
    private Boolean isSearchFirmB;
    @SerializedName("h_company_name_s")
    @Expose
    private Object hCompanyNameS;
    @SerializedName("h_company_logo_s")
    @Expose
    private Object hCompanyLogoS;
    @SerializedName("experience_from_i")
    @Expose
    private Integer experienceFromI;
    @SerializedName("experience_to_i")
    @Expose
    private Integer experienceToI;
    @SerializedName("search_id_job_opp_types")
    @Expose
    private List<Integer> searchIdJobOppTypes = null;
    @SerializedName("search_text_job_opp_types")
    @Expose
    private List<String> searchTextJobOppTypes = null;
    @SerializedName("search_id_job_emp_types")
    @Expose
    private List<String> searchIdJobEmpTypes = null;
    @SerializedName("search_text_job_emp_types")
    @Expose
    private List<String> searchTextJobEmpTypes = null;
    @SerializedName("search_id_job_skills")
    @Expose
    private List<Integer> searchIdJobSkills = null;
    @SerializedName("search_text_job_skills")
    @Expose
    private List<String> searchTextJobSkills = null;
    @SerializedName("job_Type_s")
    @Expose
    private Object jobTypeS;
    @SerializedName("end_date_dt")
    @Expose
    private Long endDateDt;
    @SerializedName("start_date_dt")
    @Expose
    private Long startDateDt;
    @SerializedName("is_assisted_b")
    @Expose
    private Boolean isAssistedB;
    @SerializedName("is_premium_b")
    @Expose
    private Boolean isPremiumB;
    @SerializedName("is_ctc_required_b")
    @Expose
    private Boolean isCtcRequiredB;
    @SerializedName("compensation_from_l")
    @Expose
    private Integer compensationFromL;
    @SerializedName("compensation_to_l")
    @Expose
    private Integer compensationToL;
    @SerializedName("no_of_openings_i")
    @Expose
    private Integer noOfOpeningsI;
    @SerializedName("compensation_details_s")
    @Expose
    private Object compensationDetailsS;
    @SerializedName("company_master_id_l")
    @Expose
    private Integer companyMasterIdL;
    @SerializedName("s_disp_external_application_url")
    @Expose
    private Object sDispExternalApplicationUrl;
    @SerializedName("address_s")
    @Expose
    private String addressS;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEntityOrParticipantId() {
        return entityOrParticipantId;
    }

    public void setEntityOrParticipantId(Integer entityOrParticipantId) {
        this.entityOrParticipantId = entityOrParticipantId;
    }

    public Integer getEntityOrParticipantTypeIdI() {
        return entityOrParticipantTypeIdI;
    }

    public void setEntityOrParticipantTypeIdI(Integer entityOrParticipantTypeIdI) {
        this.entityOrParticipantTypeIdI = entityOrParticipantTypeIdI;
    }

    public Object getDisplayIdProfileId() {
        return displayIdProfileId;
    }

    public void setDisplayIdProfileId(Object displayIdProfileId) {
        this.displayIdProfileId = displayIdProfileId;
    }

    public Object getCreatedByL() {
        return createdByL;
    }

    public void setCreatedByL(Object createdByL) {
        this.createdByL = createdByL;
    }

    public Integer getIdOfEntityOrParticipant() {
        return idOfEntityOrParticipant;
    }

    public void setIdOfEntityOrParticipant(Integer idOfEntityOrParticipant) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(Object thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public Object getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Object shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getListShortDescription() {
        return listShortDescription;
    }

    public void setListShortDescription(Object listShortDescription) {
        this.listShortDescription = listShortDescription;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public Object getTagNames() {
        return tagNames;
    }

    public void setTagNames(Object tagNames) {
        this.tagNames = tagNames;
    }

    public Object getTagIds() {
        return tagIds;
    }

    public void setTagIds(Object tagIds) {
        this.tagIds = tagIds;
    }

    public Boolean getPIsDeleted() {
        return pIsDeleted;
    }

    public void setPIsDeleted(Boolean pIsDeleted) {
        this.pIsDeleted = pIsDeleted;
    }

    public Boolean getPIsActive() {
        return pIsActive;
    }

    public void setPIsActive(Boolean pIsActive) {
        this.pIsActive = pIsActive;
    }

    public String getPCrdt() {
        return pCrdt;
    }

    public void setPCrdt(String pCrdt) {
        this.pCrdt = pCrdt;
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

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public String getPLastModifiedOn() {
        return pLastModifiedOn;
    }

    public void setPLastModifiedOn(String pLastModifiedOn) {
        this.pLastModifiedOn = pLastModifiedOn;
    }

    public Integer getAuthorParticipantId() {
        return authorParticipantId;
    }

    public void setAuthorParticipantId(Integer authorParticipantId) {
        this.authorParticipantId = authorParticipantId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Boolean getIsAuthorConfidential() {
        return isAuthorConfidential;
    }

    public void setIsAuthorConfidential(Boolean isAuthorConfidential) {
        this.isAuthorConfidential = isAuthorConfidential;
    }

    public String getAuthorParticipantType() {
        return authorParticipantType;
    }

    public void setAuthorParticipantType(String authorParticipantType) {
        this.authorParticipantType = authorParticipantType;
    }

    public Object getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(Object authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public Object getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(Object authorLastName) {
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

    public Boolean getIsAuthorImagePublic() {
        return isAuthorImagePublic;
    }

    public void setIsAuthorImagePublic(Boolean isAuthorImagePublic) {
        this.isAuthorImagePublic = isAuthorImagePublic;
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

    public Object getAuthorShortDescription() {
        return authorShortDescription;
    }

    public void setAuthorShortDescription(Object authorShortDescription) {
        this.authorShortDescription = authorShortDescription;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Integer getSolrIgnoreNoOfLikes() {
        return solrIgnoreNoOfLikes;
    }

    public void setSolrIgnoreNoOfLikes(Integer solrIgnoreNoOfLikes) {
        this.solrIgnoreNoOfLikes = solrIgnoreNoOfLikes;
    }

    public Integer getSolrIgnoreNoOfComments() {
        return solrIgnoreNoOfComments;
    }

    public void setSolrIgnoreNoOfComments(Integer solrIgnoreNoOfComments) {
        this.solrIgnoreNoOfComments = solrIgnoreNoOfComments;
    }

    public Object getSolrIgnoreLastComments() {
        return solrIgnoreLastComments;
    }

    public void setSolrIgnoreLastComments(Object solrIgnoreLastComments) {
        this.solrIgnoreLastComments = solrIgnoreLastComments;
    }

    public Integer getSolrIgnoreReactedValue() {
        return solrIgnoreReactedValue;
    }

    public void setSolrIgnoreReactedValue(Integer solrIgnoreReactedValue) {
        this.solrIgnoreReactedValue = solrIgnoreReactedValue;
    }

    public Boolean getSolrIgnoreIsBookmarked() {
        return solrIgnoreIsBookmarked;
    }

    public void setSolrIgnoreIsBookmarked(Boolean solrIgnoreIsBookmarked) {
        this.solrIgnoreIsBookmarked = solrIgnoreIsBookmarked;
    }

    public Integer getSolrIgnoreNoOfViews() {
        return solrIgnoreNoOfViews;
    }

    public void setSolrIgnoreNoOfViews(Integer solrIgnoreNoOfViews) {
        this.solrIgnoreNoOfViews = solrIgnoreNoOfViews;
    }

    public Boolean getSolrIgnoreIsApplied() {
        return solrIgnoreIsApplied;
    }

    public void setSolrIgnoreIsApplied(Boolean solrIgnoreIsApplied) {
        this.solrIgnoreIsApplied = solrIgnoreIsApplied;
    }

    public Boolean getSolrIgnoreIsViewed() {
        return solrIgnoreIsViewed;
    }

    public void setSolrIgnoreIsViewed(Boolean solrIgnoreIsViewed) {
        this.solrIgnoreIsViewed = solrIgnoreIsViewed;
    }

    public Integer getSolrIgnoreNoOfApplies() {
        return solrIgnoreNoOfApplies;
    }

    public void setSolrIgnoreNoOfApplies(Integer solrIgnoreNoOfApplies) {
        this.solrIgnoreNoOfApplies = solrIgnoreNoOfApplies;
    }

    public Integer getCompanyProfileIdL() {
        return companyProfileIdL;
    }

    public void setCompanyProfileIdL(Integer companyProfileIdL) {
        this.companyProfileIdL = companyProfileIdL;
    }

    public Object getTopCompanyTagLinkIdL() {
        return topCompanyTagLinkIdL;
    }

    public void setTopCompanyTagLinkIdL(Object topCompanyTagLinkIdL) {
        this.topCompanyTagLinkIdL = topCompanyTagLinkIdL;
    }

    public Object getSectorIdL() {
        return sectorIdL;
    }

    public void setSectorIdL(Object sectorIdL) {
        this.sectorIdL = sectorIdL;
    }

    public Object getSectorNameS() {
        return sectorNameS;
    }

    public void setSectorNameS(Object sectorNameS) {
        this.sectorNameS = sectorNameS;
    }

    public Object getJobCityIdL() {
        return jobCityIdL;
    }

    public void setJobCityIdL(Object jobCityIdL) {
        this.jobCityIdL = jobCityIdL;
    }

    public Object getJobCityNameS() {
        return jobCityNameS;
    }

    public void setJobCityNameS(Object jobCityNameS) {
        this.jobCityNameS = jobCityNameS;
    }

    public Boolean getIsFromSearchFirmB() {
        return isFromSearchFirmB;
    }

    public void setIsFromSearchFirmB(Boolean isFromSearchFirmB) {
        this.isFromSearchFirmB = isFromSearchFirmB;
    }

    public Boolean getIsSearchFirmB() {
        return isSearchFirmB;
    }

    public void setIsSearchFirmB(Boolean isSearchFirmB) {
        this.isSearchFirmB = isSearchFirmB;
    }

    public Object getHCompanyNameS() {
        return hCompanyNameS;
    }

    public void setHCompanyNameS(Object hCompanyNameS) {
        this.hCompanyNameS = hCompanyNameS;
    }

    public Object getHCompanyLogoS() {
        return hCompanyLogoS;
    }

    public void setHCompanyLogoS(Object hCompanyLogoS) {
        this.hCompanyLogoS = hCompanyLogoS;
    }

    public Integer getExperienceFromI() {
        return experienceFromI;
    }

    public void setExperienceFromI(Integer experienceFromI) {
        this.experienceFromI = experienceFromI;
    }

    public Integer getExperienceToI() {
        return experienceToI;
    }

    public void setExperienceToI(Integer experienceToI) {
        this.experienceToI = experienceToI;
    }

    public List<Integer> getSearchIdJobOppTypes() {
        return searchIdJobOppTypes;
    }

    public void setSearchIdJobOppTypes(List<Integer> searchIdJobOppTypes) {
        this.searchIdJobOppTypes = searchIdJobOppTypes;
    }

    public List<String> getSearchTextJobOppTypes() {
        return searchTextJobOppTypes;
    }

    public void setSearchTextJobOppTypes(List<String> searchTextJobOppTypes) {
        this.searchTextJobOppTypes = searchTextJobOppTypes;
    }

    public List<String> getSearchIdJobEmpTypes() {
        return searchIdJobEmpTypes;
    }

    public void setSearchIdJobEmpTypes(List<String> searchIdJobEmpTypes) {
        this.searchIdJobEmpTypes = searchIdJobEmpTypes;
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

    public List<String> getSearchTextJobSkills() {
        return searchTextJobSkills;
    }

    public void setSearchTextJobSkills(List<String> searchTextJobSkills) {
        this.searchTextJobSkills = searchTextJobSkills;
    }

    public Object getJobTypeS() {
        return jobTypeS;
    }

    public void setJobTypeS(Object jobTypeS) {
        this.jobTypeS = jobTypeS;
    }

    public Long getEndDateDt() {
        return endDateDt;
    }

    public void setEndDateDt(Long endDateDt) {
        this.endDateDt = endDateDt;
    }

    public Long getStartDateDt() {
        return startDateDt;
    }

    public void setStartDateDt(Long startDateDt) {
        this.startDateDt = startDateDt;
    }

    public Boolean getIsAssistedB() {
        return isAssistedB;
    }

    public void setIsAssistedB(Boolean isAssistedB) {
        this.isAssistedB = isAssistedB;
    }

    public Boolean getIsPremiumB() {
        return isPremiumB;
    }

    public void setIsPremiumB(Boolean isPremiumB) {
        this.isPremiumB = isPremiumB;
    }

    public Boolean getIsCtcRequiredB() {
        return isCtcRequiredB;
    }

    public void setIsCtcRequiredB(Boolean isCtcRequiredB) {
        this.isCtcRequiredB = isCtcRequiredB;
    }

    public Integer getCompensationFromL() {
        return compensationFromL;
    }

    public void setCompensationFromL(Integer compensationFromL) {
        this.compensationFromL = compensationFromL;
    }

    public Integer getCompensationToL() {
        return compensationToL;
    }

    public void setCompensationToL(Integer compensationToL) {
        this.compensationToL = compensationToL;
    }

    public Integer getNoOfOpeningsI() {
        return noOfOpeningsI;
    }

    public void setNoOfOpeningsI(Integer noOfOpeningsI) {
        this.noOfOpeningsI = noOfOpeningsI;
    }

    public Object getCompensationDetailsS() {
        return compensationDetailsS;
    }

    public void setCompensationDetailsS(Object compensationDetailsS) {
        this.compensationDetailsS = compensationDetailsS;
    }

    public Integer getCompanyMasterIdL() {
        return companyMasterIdL;
    }

    public void setCompanyMasterIdL(Integer companyMasterIdL) {
        this.companyMasterIdL = companyMasterIdL;
    }

    public Object getSDispExternalApplicationUrl() {
        return sDispExternalApplicationUrl;
    }

    public void setSDispExternalApplicationUrl(Object sDispExternalApplicationUrl) {
        this.sDispExternalApplicationUrl = sDispExternalApplicationUrl;
    }

    public String getAddressS() {
        return addressS;
    }

    public void setAddressS(String addressS) {
        this.addressS = addressS;
    }

}
