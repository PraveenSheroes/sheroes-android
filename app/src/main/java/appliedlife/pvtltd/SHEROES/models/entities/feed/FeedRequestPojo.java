package appliedlife.pvtltd.SHEROES.models.entities.feed;

/**
 * Created by Praveen_Singh on 10-02-2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

public class FeedRequestPojo extends BaseRequest implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.postingDate);
        dest.writeByte(this.isSpamPost ? (byte) 1 : (byte) 0);
        dest.writeValue(this.idForFeedDetail);
        dest.writeByte(this.onlyActive ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAcceptedOrActive ? (byte) 1 : (byte) 0);
        dest.writeValue(this.autherId);
        dest.writeString(this.subType);
        dest.writeString(this.question);
        dest.writeStringList(this.articleCategories);
        dest.writeList(this.categoryIds);
        dest.writeValue(this.communityId);
        dest.writeStringList(this.cities);
        dest.writeValue(this.experienceFrom);
        dest.writeValue(this.experienceTo);
        dest.writeStringList(this.functionalAreas);
        dest.writeStringList(this.opportunityTypes);
        dest.writeStringList(this.skills);
        dest.writeValue(this.sourceEntityId);
    }

    protected FeedRequestPojo(Parcel in) {
        this.postingDate = in.readString();
        this.isSpamPost = in.readByte() != 0;
        this.idForFeedDetail = (Long) in.readValue(Long.class.getClassLoader());
        this.onlyActive = in.readByte() != 0;
        this.isAcceptedOrActive = in.readByte() != 0;
        this.autherId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subType = in.readString();
        this.question = in.readString();
        this.articleCategories = in.createStringArrayList();
        this.categoryIds = new ArrayList<Long>();
        in.readList(this.categoryIds, Long.class.getClassLoader());
        this.communityId = (Long) in.readValue(Long.class.getClassLoader());
        this.cities = in.createStringArrayList();
        this.experienceFrom = (Integer) in.readValue(Integer.class.getClassLoader());
        this.experienceTo = (Integer) in.readValue(Integer.class.getClassLoader());
        this.functionalAreas = in.createStringArrayList();
        this.opportunityTypes = in.createStringArrayList();
        this.skills = in.createStringArrayList();
        this.sourceEntityId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<FeedRequestPojo> CREATOR = new Creator<FeedRequestPojo>() {
        @Override
        public FeedRequestPojo createFromParcel(Parcel source) {
            return new FeedRequestPojo(source);
        }

        @Override
        public FeedRequestPojo[] newArray(int size) {
            return new FeedRequestPojo[size];
        }
    };
}
