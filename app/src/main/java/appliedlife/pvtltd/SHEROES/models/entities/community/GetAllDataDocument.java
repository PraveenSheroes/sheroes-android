package appliedlife.pvtltd.SHEROES.models.entities.community;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by SHEROES-TECH on 19-03-2017.
 */
@org.parceler.Parcel(analyze = {GetAllDataDocument.class,BaseResponse.class})
public class GetAllDataDocument  extends BaseResponse{
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
    private boolean isChecked;

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

    public GetAllDataDocument() {
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
