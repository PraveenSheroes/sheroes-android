package appliedlife.pvtltd.SHEROES.models.entities.usertagging;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by Praveen on 02/02/18.
 */

public class SearchUserDataRequest extends BaseRequest {
    @SerializedName("community_id")
    @Expose
    private Long communityId;
    @SerializedName("post_entity_id")
    @Expose
    private Long postEntityId;
    @SerializedName("post_author_user_id")
    @Expose
    private Long postAuthorUserId;
    @SerializedName("user_mention_context")
    @Expose
    private String userMentionContext;
    @SerializedName("search_text")
    @Expose
    private String searchNameOfUserForTagging;

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public Long getPostEntityId() {
        return postEntityId;
    }

    public void setPostEntityId(Long postEntityId) {
        this.postEntityId = postEntityId;
    }

    public Long getPostAuthorUserId() {
        return postAuthorUserId;
    }

    public void setPostAuthorUserId(Long postAuthorUserId) {
        this.postAuthorUserId = postAuthorUserId;
    }

    public String getUserMentionContext() {
        return userMentionContext;
    }

    public void setUserMentionContext(String userMentionContext) {
        this.userMentionContext = userMentionContext;
    }

    public String getSearchNameOfUserForTagging() {
        return searchNameOfUserForTagging;
    }

    public void setSearchNameOfUserForTagging(String searchNameOfUserForTagging) {
        this.searchNameOfUserForTagging = searchNameOfUserForTagging;
    }
}
