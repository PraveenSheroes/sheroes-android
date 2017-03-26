package appliedlife.pvtltd.SHEROES.models.entities.onboarding;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum;

/**
 * Created by Praveen_Singh on 26-03-2017.
 */

public class BoardingInterestJobSearch  extends BaseResponse implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.onBoardingEnum == null ? -1 : this.onBoardingEnum.ordinal());
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.category);
        dest.writeString(this.logo);
        dest.writeString(this.communityType);
        dest.writeByte(this.isCommunityClosed ? (byte) 1 : (byte) 0);
    }

    protected BoardingInterestJobSearch(Parcel in) {
        int tmpOnBoardingEnum = in.readInt();
        this.onBoardingEnum = tmpOnBoardingEnum == -1 ? null : OnBoardingEnum.values()[tmpOnBoardingEnum];
        this.id = in.readString();
        this.title = in.readString();
        this.category = in.readString();
        this.logo = in.readString();
        this.communityType = in.readString();
        this.isCommunityClosed = in.readByte() != 0;
    }

    public static final Creator<BoardingInterestJobSearch> CREATOR = new Creator<BoardingInterestJobSearch>() {
        @Override
        public BoardingInterestJobSearch createFromParcel(Parcel source) {
            return new BoardingInterestJobSearch(source);
        }

        @Override
        public BoardingInterestJobSearch[] newArray(int size) {
            return new BoardingInterestJobSearch[size];
        }
    };
}