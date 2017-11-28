package appliedlife.pvtltd.SHEROES.models.entities.community;

/**
 * Created by Praveen_Singh on 12-03-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
@Parcel(analyze = {Member.class,BaseResponse.class})
public class Member extends BaseResponse{

    @SerializedName("approved_date")
    @Expose
    private String approvedDate;
    @SerializedName("city_master_id")
    @Expose
    private Integer cityMasterId;
    @SerializedName("com_id")
    @Expose
    private Integer comId;
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
    private boolean commIsActive;
    @SerializedName("comm_is_closed")
    @Expose
    private boolean commIsClosed;
    @SerializedName("comm_is_deleted")
    @Expose
    private boolean commIsDeleted;
    @SerializedName("comm_is_featured")
    @Expose
    private boolean commIsFeatured;
    @SerializedName("community_id")
    @Expose
    private Integer communityId;
    @SerializedName("community_member_id")
    @Expose
    private Integer communityMemberId;
    @SerializedName("community_user_city_id")
    @Expose
    private Integer communityUserCityId;
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
    private Integer communityUserId;
    @SerializedName("community_user_last_name")
    @Expose
    private String communityUserLastName;
    @SerializedName("community_user_mobile")
    @Expose
    private String communityUserMobile;
    @SerializedName("community_user_participant_id")
    @Expose
    private Integer communityUserParticipantId;
    @SerializedName("community_user_photo_url_path")
    @Expose
    private String communityUserPhotoUrlPath;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;
    @SerializedName("is_approved")
    @Expose
    private boolean isApproved;
    @SerializedName("type_s")
    @Expose
    private String typeS;
    @SerializedName("users_id")
    @Expose
    private Integer usersId;

    @SerializedName("is_owner")
    @Expose
    private boolean isOwner;

    @SerializedName("owner_count")
    @Expose
    private Integer ownerCount;


    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Integer getCityMasterId() {
        return cityMasterId;
    }

    public void setCityMasterId(Integer cityMasterId) {
        this.cityMasterId = cityMasterId;
    }

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
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

    public boolean isCommIsActive() {
        return commIsActive;
    }

    public void setCommIsActive(boolean commIsActive) {
        this.commIsActive = commIsActive;
    }

    public boolean isCommIsClosed() {
        return commIsClosed;
    }

    public void setCommIsClosed(boolean commIsClosed) {
        this.commIsClosed = commIsClosed;
    }

    public boolean isCommIsDeleted() {
        return commIsDeleted;
    }

    public void setCommIsDeleted(boolean commIsDeleted) {
        this.commIsDeleted = commIsDeleted;
    }

    public boolean isCommIsFeatured() {
        return commIsFeatured;
    }

    public void setCommIsFeatured(boolean commIsFeatured) {
        this.commIsFeatured = commIsFeatured;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public Integer getCommunityMemberId() {
        return communityMemberId;
    }

    public void setCommunityMemberId(Integer communityMemberId) {
        this.communityMemberId = communityMemberId;
    }

    public Integer getCommunityUserCityId() {
        return communityUserCityId;
    }

    public void setCommunityUserCityId(Integer communityUserCityId) {
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

    public Integer getCommunityUserId() {
        return communityUserId;
    }

    public void setCommunityUserId(Integer communityUserId) {
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

    public Integer getCommunityUserParticipantId() {
        return communityUserParticipantId;
    }

    public void setCommunityUserParticipantId(Integer communityUserParticipantId) {
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getTypeS() {
        return typeS;
    }

    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public Integer getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public Integer getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(Integer ownerCount) {
        this.ownerCount = ownerCount;
    }

}