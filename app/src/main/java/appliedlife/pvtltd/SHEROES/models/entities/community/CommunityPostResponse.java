package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Ajit Kumar on 22-03-2017.
 */

public class CommunityPostResponse extends BaseResponse {
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
        private String community_type;
        @SerializedName("is_community_closed")
        @Expose
        private String is_community_closed;

        @SerializedName("is_owner")
        @Expose
        private boolean isOwner;

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

    public String getCommunity_type() {
        return community_type;
    }

    public void setCommunity_type(String community_type) {
        this.community_type = community_type;
    }

    public String getIs_community_closed() {
        return is_community_closed;
    }

    public void setIs_community_closed(String is_community_closed) {
        this.is_community_closed = is_community_closed;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}


