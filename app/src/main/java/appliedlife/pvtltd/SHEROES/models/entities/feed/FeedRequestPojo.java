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

    @SerializedName("id")
    @Expose
    private String idForFeedDetail;
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
    public long communityId;



    @SerializedName("cities")
    @Expose
    private List<String> cities = null;
    @SerializedName("city_ids")
    @Expose
    private List<Integer> cityIds = null;

    @SerializedName("experience_from")
    @Expose
    private int experienceFrom;
    @SerializedName("experience_to")
    @Expose
    private int experienceTo;
    @SerializedName("functional_areas")
    @Expose
    private List<String> functionalAreas = null;

    @SerializedName("opportunity_type_ids")
    @Expose
    private List<Integer> opportunityTypeIds = null;
    @SerializedName("opportunity_types")
    @Expose
    private List<String> opportunityTypes = null;

    @SerializedName("skill_ids")
    @Expose
    private List<Integer> skillIds = null;
    @SerializedName("skills")
    @Expose
    private List<String> skills = null;

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

    public String getIdForFeedDetail() {
        return idForFeedDetail;
    }

    public void setIdForFeedDetail(String idForFeedDetail) {
        this.idForFeedDetail = idForFeedDetail;
    }


    public List<String> getArticleCategories() {
        return articleCategories;
    }

    public void setArticleCategories(List<String> articleCategories) {
        this.articleCategories = articleCategories;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(long communityId) {
        this.communityId = communityId;
    }

    public FeedRequestPojo() {
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<Integer> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<Integer> cityIds) {
        this.cityIds = cityIds;
    }

    public int getExperienceFrom() {
        return experienceFrom;
    }

    public void setExperienceFrom(int experienceFrom) {
        this.experienceFrom = experienceFrom;
    }

    public int getExperienceTo() {
        return experienceTo;
    }

    public void setExperienceTo(int experienceTo) {
        this.experienceTo = experienceTo;
    }

    public List<String> getFunctionalAreas() {
        return functionalAreas;
    }

    public void setFunctionalAreas(List<String> functionalAreas) {
        this.functionalAreas = functionalAreas;
    }

    public List<Integer> getOpportunityTypeIds() {
        return opportunityTypeIds;
    }

    public void setOpportunityTypeIds(List<Integer> opportunityTypeIds) {
        this.opportunityTypeIds = opportunityTypeIds;
    }

    public List<String> getOpportunityTypes() {
        return opportunityTypes;
    }

    public void setOpportunityTypes(List<String> opportunityTypes) {
        this.opportunityTypes = opportunityTypes;
    }

    public List<Integer> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(List<Integer> skillIds) {
        this.skillIds = skillIds;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idForFeedDetail);
        dest.writeString(this.subType);
        dest.writeString(this.question);
        dest.writeStringList(this.articleCategories);
        dest.writeList(this.categoryIds);
        dest.writeLong(this.communityId);
        dest.writeStringList(this.cities);
        dest.writeList(this.cityIds);
        dest.writeInt(this.experienceFrom);
        dest.writeInt(this.experienceTo);
        dest.writeStringList(this.functionalAreas);
        dest.writeList(this.opportunityTypeIds);
        dest.writeStringList(this.opportunityTypes);
        dest.writeList(this.skillIds);
        dest.writeStringList(this.skills);
    }

    protected FeedRequestPojo(Parcel in) {
        this.idForFeedDetail = in.readString();
        this.subType = in.readString();
        this.question = in.readString();
        this.articleCategories = in.createStringArrayList();
        this.categoryIds = new ArrayList<Long>();
        in.readList(this.categoryIds, Long.class.getClassLoader());
        this.communityId = in.readLong();
        this.cities = in.createStringArrayList();
        this.cityIds = new ArrayList<Integer>();
        in.readList(this.cityIds, Integer.class.getClassLoader());
        this.experienceFrom = in.readInt();
        this.experienceTo = in.readInt();
        this.functionalAreas = in.createStringArrayList();
        this.opportunityTypeIds = new ArrayList<Integer>();
        in.readList(this.opportunityTypeIds, Integer.class.getClassLoader());
        this.opportunityTypes = in.createStringArrayList();
        this.skillIds = new ArrayList<Integer>();
        in.readList(this.skillIds, Integer.class.getClassLoader());
        this.skills = in.createStringArrayList();
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
