package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class MembersList extends BaseResponse implements Parcelable{
    @SerializedName("approved_date")
    @Expose
    private String approvedDate;
    @SerializedName("city_master_id")
    @Expose
    private int cityMasterId;
    @SerializedName("com_id")
    @Expose
    private int comId;
    @SerializedName("com_logo_url")
    @Expose
    private String comLogoUrl;
    @SerializedName("com_name")
    @Expose
    private String comName;
    @SerializedName("com_type")
    @Expose
    private String comType;
    @SerializedName("comm_is_active")
    @Expose
    private Boolean commIsActive;
    @SerializedName("comm_is_closed")
    @Expose
    private Boolean commIsClosed;
    @SerializedName("comm_is_deleted")
    @Expose
    private Boolean commIsDeleted;
    @SerializedName("comm_is_featured")
    @Expose
    private Boolean commIsFeatured;
    @SerializedName("community_id")
    @Expose
    private int communityId;
    @SerializedName("community_member_id")
    @Expose
    private int communityMemberId;
    @SerializedName("community_user_city_id")
    @Expose
    private int communityUserCityId;
    @SerializedName("community_user_city_name")
    @Expose
    private String communityUserCityName;
    @SerializedName("community_user_emailid")
    @Expose
    private String communityUserEmailid;
    @SerializedName("community_user_first_name")
    @Expose
    private String communityUserFirstName;
    @SerializedName("community_user_id")
    @Expose
    private int communityUserId;
    @SerializedName("community_user_last_name")
    @Expose
    private String communityUserLastName;
    @SerializedName("community_user_mobile")
    @Expose
    private String communityUserMobile;
    @SerializedName("community_user_participant_id")
    @Expose
    private int communityUserParticipantId;
    @SerializedName("community_user_photo_url_path")
    @Expose
    private String communityUserPhotoUrlPath;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("is_approved")
    @Expose
    private Boolean isApproved;
    @SerializedName("type_s")
    @Expose
    private String typeS;
    @SerializedName("users_id")
    @Expose
    private int usersId;

    public MembersList(Parcel in) {
        approvedDate = in.readString();
        comLogoUrl = in.readString();
        comName = in.readString();
        comType = in.readString();
        communityUserCityName = in.readString();
        communityUserEmailid = in.readString();
        communityUserFirstName = in.readString();
        communityUserLastName = in.readString();
        communityUserMobile = in.readString();
        communityUserPhotoUrlPath = in.readString();
        id = in.readString();
        typeS = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };



    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public int getCityMasterId() {
        return cityMasterId;
    }

    public void setCityMasterId(int cityMasterId) {
        this.cityMasterId = cityMasterId;
    }

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    public String getComLogoUrl() {
        return comLogoUrl;
    }

    public void setComLogoUrl(String comLogoUrl) {
        this.comLogoUrl = comLogoUrl;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }

    public Boolean getCommIsActive() {
        return commIsActive;
    }

    public void setCommIsActive(Boolean commIsActive) {
        this.commIsActive = commIsActive;
    }

    public Boolean getCommIsClosed() {
        return commIsClosed;
    }

    public void setCommIsClosed(Boolean commIsClosed) {
        this.commIsClosed = commIsClosed;
    }

    public Boolean getCommIsDeleted() {
        return commIsDeleted;
    }

    public void setCommIsDeleted(Boolean commIsDeleted) {
        this.commIsDeleted = commIsDeleted;
    }

    public Boolean getCommIsFeatured() {
        return commIsFeatured;
    }

    public void setCommIsFeatured(Boolean commIsFeatured) {
        this.commIsFeatured = commIsFeatured;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getCommunityMemberId() {
        return communityMemberId;
    }

    public void setCommunityMemberId(int communityMemberId) {
        this.communityMemberId = communityMemberId;
    }

    public int getCommunityUserCityId() {
        return communityUserCityId;
    }

    public void setCommunityUserCityId(int communityUserCityId) {
        this.communityUserCityId = communityUserCityId;
    }

    public String getCommunityUserCityName() {
        return communityUserCityName;
    }

    public void setCommunityUserCityName(String communityUserCityName) {
        this.communityUserCityName = communityUserCityName;
    }

    public String getCommunityUserEmailid() {
        return communityUserEmailid;
    }

    public void setCommunityUserEmailid(String communityUserEmailid) {
        this.communityUserEmailid = communityUserEmailid;
    }

    public String getCommunityUserFirstName() {
        return communityUserFirstName;
    }

    public void setCommunityUserFirstName(String communityUserFirstName) {
        this.communityUserFirstName = communityUserFirstName;
    }

    public int getCommunityUserId() {
        return communityUserId;
    }

    public void setCommunityUserId(int communityUserId) {
        this.communityUserId = communityUserId;
    }

    public String getCommunityUserLastName() {
        return communityUserLastName;
    }

    public void setCommunityUserLastName(String communityUserLastName) {
        this.communityUserLastName = communityUserLastName;
    }

    public String getCommunityUserMobile() {
        return communityUserMobile;
    }

    public void setCommunityUserMobile(String communityUserMobile) {
        this.communityUserMobile = communityUserMobile;
    }

    public int getCommunityUserParticipantId() {
        return communityUserParticipantId;
    }

    public void setCommunityUserParticipantId(int communityUserParticipantId) {
        this.communityUserParticipantId = communityUserParticipantId;
    }

    public String getCommunityUserPhotoUrlPath() {
        return communityUserPhotoUrlPath;
    }

    public void setCommunityUserPhotoUrlPath(String communityUserPhotoUrlPath) {
        this.communityUserPhotoUrlPath = communityUserPhotoUrlPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getTypeS() {
        return typeS;
    }

    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(approvedDate);
        dest.writeString(comLogoUrl);
        dest.writeString(comName);
        dest.writeString(comType);
        dest.writeString(communityUserCityName);
        dest.writeString(communityUserEmailid);
        dest.writeString(communityUserFirstName);
        dest.writeString(communityUserLastName);
        dest.writeString(communityUserMobile);
        dest.writeString(communityUserPhotoUrlPath);
        dest.writeString(id);
        dest.writeString(typeS);
    }

}
