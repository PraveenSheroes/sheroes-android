package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baserequest.BaseRequest;

/**
 * Created by SHEROES-TECH on 22-03-2017.
 */

public class CommunityTopPostRequest extends BaseRequest {

    @SerializedName("id")
    @Expose
    private Long id=null;
    @SerializedName("community_id")
    @Expose
    private Long communityId=null;
    @SerializedName("creator_type")
    @Expose
    private String creatorType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_active")
    @Expose
    private boolean isActive =true;

    @SerializedName("is_top_post_b")
    @Expose
    private boolean isTopPost;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCreatorType() {
        return creatorType;
    }

    public void setCreatorType(String creatorType) {
        this.creatorType = creatorType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public boolean isTopPost() {
        return isTopPost;
    }

    public void setTopPost(boolean topPost) {
        isTopPost = topPost;
    }

}
