package appliedlife.pvtltd.SHEROES.models.entities.miscellanous;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateRequest;

/**
 * Created by Praveen_Singh on 17-08-2017.
 */

public class MakeIndiaSafeDetail extends BaseResponse {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    private Bitmap bitmap;
    private File localImageSaveForChallenge;
    private boolean isPicClick;
    private LatLongWithLocation latLongWithLocation;
    private CommunityPostCreateRequest communityPostCreateRequest;
    private boolean isLinkClicked;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLongWithLocation getLatLongWithLocation() {
        return latLongWithLocation;
    }

    public void setLatLongWithLocation(LatLongWithLocation latLongWithLocation) {
        this.latLongWithLocation = latLongWithLocation;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public File getLocalImageSaveForChallenge() {
        return localImageSaveForChallenge;
    }

    public void setLocalImageSaveForChallenge(File localImageSaveForChallenge) {
        this.localImageSaveForChallenge = localImageSaveForChallenge;
    }

    public boolean isPicClick() {
        return isPicClick;
    }

    public void setPicClick(boolean picClick) {
        isPicClick = picClick;
    }

    public CommunityPostCreateRequest getCommunityPostCreateRequest() {
        return communityPostCreateRequest;
    }

    public void setCommunityPostCreateRequest(CommunityPostCreateRequest communityPostCreateRequest) {
        this.communityPostCreateRequest = communityPostCreateRequest;
    }

    public MakeIndiaSafeDetail() {
    }

    public boolean isLinkClicked() {
        return isLinkClicked;
    }

    public void setLinkClicked(boolean linkClicked) {
        isLinkClicked = linkClicked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.bitmap, flags);
        dest.writeSerializable(this.localImageSaveForChallenge);
        dest.writeByte(this.isPicClick ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.latLongWithLocation, flags);
        dest.writeParcelable(this.communityPostCreateRequest, flags);
        dest.writeByte(this.isLinkClicked ? (byte) 1 : (byte) 0);
    }

    protected MakeIndiaSafeDetail(Parcel in) {
        super(in);
        this.id = in.readInt();
        this.name = in.readString();
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.localImageSaveForChallenge = (File) in.readSerializable();
        this.isPicClick = in.readByte() != 0;
        this.latLongWithLocation = in.readParcelable(LatLongWithLocation.class.getClassLoader());
        this.communityPostCreateRequest = in.readParcelable(CommunityPostCreateRequest.class.getClassLoader());
        this.isLinkClicked = in.readByte() != 0;
    }

    public static final Creator<MakeIndiaSafeDetail> CREATOR = new Creator<MakeIndiaSafeDetail>() {
        @Override
        public MakeIndiaSafeDetail createFromParcel(Parcel source) {
            return new MakeIndiaSafeDetail(source);
        }

        @Override
        public MakeIndiaSafeDetail[] newArray(int size) {
            return new MakeIndiaSafeDetail[size];
        }
    };
}
