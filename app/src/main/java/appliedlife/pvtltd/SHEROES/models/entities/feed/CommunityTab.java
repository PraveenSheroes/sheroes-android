package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by ujjwal on 27/12/17.
 */
@Parcel(analyze = {CommunityTab.class})
public class CommunityTab {
    @SerializedName("id")
    public int id;

    @SerializedName("community_id_l")
    public int communityId;

    @SerializedName("p_crdt")
    public Date mCreated;

    @SerializedName("p_is_active")
    public boolean isActive;

    @SerializedName("created_by_l")
    public int createdBy;

    @SerializedName("p_last_modified_on")
    public Date lastModified;

    @SerializedName("last_modified_by_l")
    public int lastModifiedByL;

    @SerializedName("key_s")
    public String key;

    @SerializedName("title_s")
    public String title;

    @SerializedName("type_s")
    public String type;

    @SerializedName("data_url_s")
    public String dataUrl;

    @SerializedName("data_html_s")
    public String dataHtml;

    @SerializedName("priority_i")
    public int priority;

    @SerializedName("show_fab_b")
    public boolean showFabButton;

    @SerializedName("fab_url_s")
    public String fabUrl;

    @SerializedName("fab_icon_s")
    public String fabIconUrl;

    @SerializedName("empty_image_s")
    public String emptyImageUrl;

    @SerializedName("empty_title_s")
    public String emptyTitle;

    @SerializedName("empty_description_s")
    public String emptyDescription;

    @SerializedName("min_app_version_code_i")
    public int minAppVersionCode;

}
