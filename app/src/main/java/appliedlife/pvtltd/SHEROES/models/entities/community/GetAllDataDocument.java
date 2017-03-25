package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 19-03-2017.
 */

public class GetAllDataDocument  extends BaseResponse implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.category);
        dest.writeString(this.logo);
        dest.writeString(this.communityType);
        dest.writeByte(this.isCommunityClosed ? (byte) 1 : (byte) 0);
    }

    public GetAllDataDocument() {
    }

    protected GetAllDataDocument(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.category = in.readString();
        this.logo = in.readString();
        this.communityType = in.readString();
        this.isCommunityClosed = in.readByte() != 0;
    }

    public static final Creator<GetAllDataDocument> CREATOR = new Creator<GetAllDataDocument>() {
        @Override
        public GetAllDataDocument createFromParcel(Parcel source) {
            return new GetAllDataDocument(source);
        }

        @Override
        public GetAllDataDocument[] newArray(int size) {
            return new GetAllDataDocument[size];
        }
    };
}
