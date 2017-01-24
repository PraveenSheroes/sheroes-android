package appliedlife.pvtltd.SHEROES.models.entities.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentsList extends BaseResponse{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("communityId")
    @Expose
    private String communityId;
    @SerializedName("participatingEntityId")
    @Expose
    private String participatingEntityId;
    @SerializedName("usersId")
    @Expose
    private String usersId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("isCommunityPost")
    @Expose
    private String isCommunityPost;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("profilePicUrl")
    @Expose
    private String profilePicUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getParticipatingEntityId() {
        return participatingEntityId;
    }

    public void setParticipatingEntityId(String participatingEntityId) {
        this.participatingEntityId = participatingEntityId;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getIsCommunityPost() {
        return isCommunityPost;
    }

    public void setIsCommunityPost(String isCommunityPost) {
        this.isCommunityPost = isCommunityPost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
