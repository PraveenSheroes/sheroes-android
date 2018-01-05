package appliedlife.pvtltd.SHEROES.models.entities.feed;

/**
 * Created by Praveen_Singh on 10-02-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectedCommunityResponse;

@Parcel(analyze = {FeedRequestPojo.class,BaseRequest.class})
public class FeedRequestPojo extends BaseRequest{

    @SerializedName("is_onboarding_communities")
    @Expose
    private boolean isOnBoardingCommunities;
    @SerializedName("posting_date_dt")
    @Expose
    private String postingDate;
    @SerializedName("is_spam_post_b")
    @Expose
    private boolean isSpamPost;
    @SerializedName("id_of_entity_or_participant")
    @Expose
    private Long idForFeedDetail=null;

    @SerializedName("only_active")
    @Expose
    private boolean onlyActive;

    @SerializedName("is_accepted_or_active")
    @Expose
    private boolean isAcceptedOrActive;

    @SerializedName("author_id")
    @Expose
    private Integer autherId;
    @SerializedName("sub_type")
    @Expose
    private String subType;
    @SerializedName("q")
    @Expose
    private String question;

    @SerializedName("article_categories")
    @Expose
    private List<String> articleCategories;
    @SerializedName("category_ids")
    @Expose
    private List<Long> categoryIds;

    @SerializedName("community_id")
    @Expose
    public Long communityId=null;

    @SerializedName("cities")
    @Expose
    private List<String> cities = null;


    @SerializedName("experience_from")
    @Expose
    private Integer experienceFrom=null;
    @SerializedName("experience_to")
    @Expose
    private Integer experienceTo=null;
    @SerializedName("functional_areas")
    @Expose
    private List<String> functionalAreas = null;

    @SerializedName("opportunity_types")
    @Expose
    private List<String> opportunityTypes = null;

    @SerializedName("skills")
    @Expose
    private List<String> skills = null;

    @SerializedName("source_entity_id")
    @Expose
    private Integer sourceEntityId;

    @SerializedName("is_anonymous_post_hide")
    @Expose
    private boolean isAnonymousPostHide;


    public Integer getSourceEntityId() {
        return sourceEntityId;
    }

    public void setSourceEntityId(Integer sourceEntityId) {
        this.sourceEntityId = sourceEntityId;
    }

    public Long getIdForFeedDetail() {
        return idForFeedDetail;
    }

    public void setIdForFeedDetail(Long idForFeedDetail) {
        this.idForFeedDetail = idForFeedDetail;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getArticleCategories() {
        return articleCategories;
    }

    public void setArticleCategories(List<String> articleCategories) {
        this.articleCategories = articleCategories;
    }

    public boolean isOnlyActive() {
        return onlyActive;
    }

    public void setOnlyActive(boolean onlyActive) {
        this.onlyActive = onlyActive;
    }

    public boolean isAcceptedOrActive() {
        return isAcceptedOrActive;
    }

    public void setAcceptedOrActive(boolean acceptedOrActive) {
        isAcceptedOrActive = acceptedOrActive;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public Integer getExperienceFrom() {
        return experienceFrom;
    }

    public void setExperienceFrom(Integer experienceFrom) {
        this.experienceFrom = experienceFrom;
    }

    public Integer getExperienceTo() {
        return experienceTo;
    }

    public void setExperienceTo(Integer experienceTo) {
        this.experienceTo = experienceTo;
    }

    public List<String> getFunctionalAreas() {
        return functionalAreas;
    }

    public void setFunctionalAreas(List<String> functionalAreas) {
        this.functionalAreas = functionalAreas;
    }

    public List<String> getOpportunityTypes() {
        return opportunityTypes;
    }

    public void setOpportunityTypes(List<String> opportunityTypes) {
        this.opportunityTypes = opportunityTypes;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public FeedRequestPojo() {
    }

    public Integer getAutherId() {
        return autherId;
    }

    public void setAutherId(Integer autherId) {
        this.autherId = autherId;
    }

    public boolean isSpamPost() {
        return isSpamPost;
    }

    public void setSpamPost(boolean spamPost) {
        isSpamPost = spamPost;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public boolean isAnonymousPostHide() {
        return isAnonymousPostHide;
    }

    public void setAnonymousPostHide(boolean anonymousPostHide) {
        isAnonymousPostHide = anonymousPostHide;
    }

    public boolean isOnBoardingCommunities() {
        return isOnBoardingCommunities;
    }

    public void setOnBoardingCommunities(boolean onBoardingCommunities) {
        isOnBoardingCommunities = onBoardingCommunities;
    }
}
