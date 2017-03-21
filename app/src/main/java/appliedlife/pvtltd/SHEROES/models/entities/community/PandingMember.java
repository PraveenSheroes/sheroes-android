package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 10-03-2017.
 */

public class PandingMember extends BaseResponse {

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("community_member_id")
    @Expose
    private Object communityMemberId;
    @SerializedName("approved_date")
    @Expose
    private Object approvedDate;
    @SerializedName("community_id")
    @Expose
    private Object communityId;
    @SerializedName("users_id")
    @Expose
    private Object usersId;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("is_approved")
    @Expose
    private Boolean isApproved;
    @SerializedName("type_s")
    @Expose
    private String typeS;
    @SerializedName("community_user_photo_url_path")
    @Expose
    private String communityUserPhotoUrlPath;
    @SerializedName("community_user_participant_id")
    @Expose
    private Integer communityUserParticipantId;
    @SerializedName("city_master_id")
    @Expose
    private Object cityMasterId;
    @SerializedName("community_user_mobile")
    @Expose
    private Object communityUserMobile;
    @SerializedName("community_user_last_name")
    @Expose
    private String communityUserLastName;
    @SerializedName("community_user_emailid")
    @Expose
    private Object communityUserEmailid;
    @SerializedName("community_user_id")
    @Expose
    private Object communityUserId;
    @SerializedName("community_user_first_name")
    @Expose
    private String communityUserFirstName;
    @SerializedName("community_user_city_name")
    @Expose
    private String communityUserCityName;
    @SerializedName("community_user_city_id")
    @Expose
    private Object communityUserCityId;
    @SerializedName("com_type")
    @Expose
    private Object comType;
    @SerializedName("comm_is_active")
    @Expose
    private Object commIsActive;
    @SerializedName("comm_is_deleted")
    @Expose
    private Object commIsDeleted;
    @SerializedName("com_logo_url")
    @Expose
    private Object comLogoUrl;
    @SerializedName("com_id")
    @Expose
    private Object comId;
    @SerializedName("comm_is_featured")
    @Expose
    private Object commIsFeatured;
    @SerializedName("com_name")
    @Expose
    private String comName;
    @SerializedName("comm_is_closed")
    @Expose
    private Boolean commIsClosed;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getCommunityMemberId() {
        return communityMemberId;
    }

    public void setCommunityMemberId(Object communityMemberId) {
        this.communityMemberId = communityMemberId;
    }

    public Object getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Object approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Object getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Object communityId) {
        this.communityId = communityId;
    }

    public Object getUsersId() {
        return usersId;
    }

    public void setUsersId(Object usersId) {
        this.usersId = usersId;
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

    public String getCommunityUserPhotoUrlPath() {
        return communityUserPhotoUrlPath;
    }

    public void setCommunityUserPhotoUrlPath(String communityUserPhotoUrlPath) {
        this.communityUserPhotoUrlPath = communityUserPhotoUrlPath;
    }

    public Integer getCommunityUserParticipantId() {
        return communityUserParticipantId;
    }

    public void setCommunityUserParticipantId(Integer communityUserParticipantId) {
        this.communityUserParticipantId = communityUserParticipantId;
    }

    public Object getCityMasterId() {
        return cityMasterId;
    }

    public void setCityMasterId(Object cityMasterId) {
        this.cityMasterId = cityMasterId;
    }

    public Object getCommunityUserMobile() {
        return communityUserMobile;
    }

    public void setCommunityUserMobile(Object communityUserMobile) {
        this.communityUserMobile = communityUserMobile;
    }

    public String getCommunityUserLastName() {
        return communityUserLastName;
    }

    public void setCommunityUserLastName(String communityUserLastName) {
        this.communityUserLastName = communityUserLastName;
    }

    public Object getCommunityUserEmailid() {
        return communityUserEmailid;
    }

    public void setCommunityUserEmailid(Object communityUserEmailid) {
        this.communityUserEmailid = communityUserEmailid;
    }

    public Object getCommunityUserId() {
        return communityUserId;
    }

    public void setCommunityUserId(Object communityUserId) {
        this.communityUserId = communityUserId;
    }

    public String getCommunityUserFirstName() {
        return communityUserFirstName;
    }

    public void setCommunityUserFirstName(String communityUserFirstName) {
        this.communityUserFirstName = communityUserFirstName;
    }

    public String getCommunityUserCityName() {
        return communityUserCityName;
    }

    public void setCommunityUserCityName(String communityUserCityName) {
        this.communityUserCityName = communityUserCityName;
    }

    public Object getCommunityUserCityId() {
        return communityUserCityId;
    }

    public void setCommunityUserCityId(Object communityUserCityId) {
        this.communityUserCityId = communityUserCityId;
    }

    public Object getComType() {
        return comType;
    }

    public void setComType(Object comType) {
        this.comType = comType;
    }

    public Object getCommIsActive() {
        return commIsActive;
    }

    public void setCommIsActive(Object commIsActive) {
        this.commIsActive = commIsActive;
    }

    public Object getCommIsDeleted() {
        return commIsDeleted;
    }

    public void setCommIsDeleted(Object commIsDeleted) {
        this.commIsDeleted = commIsDeleted;
    }

    public Object getComLogoUrl() {
        return comLogoUrl;
    }

    public void setComLogoUrl(Object comLogoUrl) {
        this.comLogoUrl = comLogoUrl;
    }

    public Object getComId() {
        return comId;
    }

    public void setComId(Object comId) {
        this.comId = comId;
    }

    public Object getCommIsFeatured() {
        return commIsFeatured;
    }

    public void setCommIsFeatured(Object commIsFeatured) {
        this.commIsFeatured = commIsFeatured;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public Boolean getCommIsClosed() {
        return commIsClosed;
    }

    public void setCommIsClosed(Boolean commIsClosed) {
        this.commIsClosed = commIsClosed;
    }
}
