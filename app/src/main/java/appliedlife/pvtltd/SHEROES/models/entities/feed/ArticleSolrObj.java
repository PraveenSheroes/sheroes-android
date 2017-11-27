package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {ArticleSolrObj.class,FeedDetail.class})
public class ArticleSolrObj extends FeedDetail {
    @SerializedName(value = "slug_s")
    private String slug;

    @SerializedName(value = "s_disp_published_in")
    private String publishedIn;

    @SerializedName(value = "meta_description_s")
    private String metaDescription;

    @SerializedName(value = "meta_title_s")
    private String metaTitle;

    @SerializedName(value = "article_status_i")
    private Integer articleStatus;

    @SerializedName(value = "third_party_id_s")
    private String thirdPartyId;

    @SerializedName(value = "s_disp_third_party_unique_id")
    private String thirdPartyUniqueId;

    @SerializedName(value = "article_category_name_s")
    private String articleCategory;

    @SerializedName(value = "article_category_l")
    private Long articleCategoryId;

    @SerializedName(value = "char_count_i")
    private int charCount;

    @SerializedName(value="thumbnailImage_width_i")
    private int thumbnailImageWidth;

    @SerializedName(value="thumbnailImage_height_i")
    private int thumbnailImageHeight;

    @SerializedName(value="highresImage_width_i")
    private int highresImageWidth;

    @SerializedName(value="highresImage_height_i")
    private int highresImageHeight;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
