package appliedlife.pvtltd.SHEROES.models.entities.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;

/**
 * Created by Praveen_Singh on 14-02-2017.
 */

public class CommentReactionRequestPojo extends BaseRequest {
    @SerializedName("entity_id")
    @Expose
    private long entityId;
    @SerializedName("comment")
    @Expose
    private String userComment;
    @SerializedName("is_active")
    @Expose
    private boolean isActive;
    @SerializedName("is_anonymous")
    @Expose
    private boolean isAnonymous;
    @SerializedName("participation_id")
    @Expose
    private long participationId;

    @SerializedName("list_type")
    @Expose
    private String listType;
    @SerializedName("search_text")
    @Expose
    private String searchText;

    /* Mention fields*/

    @SerializedName("has_mentions")
    @Expose
    private boolean hasMentions;

    @SerializedName("user_mentions")
    @Expose
    private List<MentionSpan> userMentionList;

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public long getParticipationId() {
        return participationId;
    }

    public void setParticipationId(long participationId) {
        this.participationId = participationId;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public boolean isHasMentions() {
        return hasMentions;
    }

    public void setHasMentions(boolean hasMentions) {
        this.hasMentions = hasMentions;
    }

    public List<MentionSpan> getUserMentionList() {
        return userMentionList;
    }

    public void setUserMentionList(List<MentionSpan> userMentionList) {
        this.userMentionList = userMentionList;
    }
}
