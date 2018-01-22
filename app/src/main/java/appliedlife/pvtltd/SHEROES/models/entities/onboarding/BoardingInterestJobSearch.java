package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;

/**
 * Created by Praveen_Singh on 26-03-2017.
 */
@Parcel(analyze = {BoardingInterestJobSearch.class, BaseResponse.class})
public class BoardingInterestJobSearch extends BaseResponse {
    OnBoardingEnum onBoardingEnum;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("community_type")
    @Expose
    private String communityType;
    @SerializedName("is_community_closed")
    @Expose
    private boolean isCommunityClosed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCommunityType() {
        return communityType;
    }

    public void setCommunityType(String communityType) {
        this.communityType = communityType;
    }

    public boolean isCommunityClosed() {
        return isCommunityClosed;
    }

    public void setCommunityClosed(boolean communityClosed) {
        isCommunityClosed = communityClosed;
    }


    public BoardingInterestJobSearch() {
    }

    public OnBoardingEnum getOnBoardingEnum() {
        return onBoardingEnum;
    }

    public void setOnBoardingEnum(OnBoardingEnum onBoardingEnum) {
        this.onBoardingEnum = onBoardingEnum;
    }

}