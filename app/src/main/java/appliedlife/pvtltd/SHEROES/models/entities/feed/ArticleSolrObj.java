package appliedlife.pvtltd.SHEROES.models.entities.feed;

import android.support.annotation.DrawableRes;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import appliedlife.pvtltd.SHEROES.R;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {ArticleSolrObj.class, FeedDetail.class})
public class ArticleSolrObj extends FeedDetail {
    public static final String ARTICLE_OBJ = "ARTICLE_OBJ";
    public boolean isLiked;
    public int likesCount;

    public boolean isThreadClosed;
    public boolean showComments = true;

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

    @SerializedName(value = "thumbnailImage_width_i")
    private int thumbImageWidth;

    @SerializedName(value = "thumbnailImage_height_i")
    private int thumbImageHeight;

    @SerializedName(value = "highresImage_width_i")
    private int highresImageWidth;

    @SerializedName(value = "highresImage_height_i")
    private int highresImageHeight;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPublishedIn() {
        return publishedIn;
    }

    public void setPublishedIn(String publishedIn) {
        this.publishedIn = publishedIn;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getThirdPartyUniqueId() {
        return thirdPartyUniqueId;
    }

    public void setThirdPartyUniqueId(String thirdPartyUniqueId) {
        this.thirdPartyUniqueId = thirdPartyUniqueId;
    }

    public String getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(String articleCategory) {
        this.articleCategory = articleCategory;
    }

    public Long getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(Long articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public int getThumbImageWidth() {
        return thumbImageWidth;
    }

    public void setThumbImageWidth(int thumbImageWidth) {
        this.thumbImageWidth = thumbImageWidth;
    }

    public int getThumbImageHeight() {
        return thumbImageHeight;
    }

    public void setThumbImageHeight(int thumbImageHeight) {
        this.thumbImageHeight = thumbImageHeight;
    }

    public int getHighresImageWidth() {
        return highresImageWidth;
    }

    public void setHighresImageWidth(int highresImageWidth) {
        this.highresImageWidth = highresImageWidth;
    }

    public int getHighresImageHeight() {
        return highresImageHeight;
    }

    public void setHighresImageHeight(int highresImageHeight) {
        this.highresImageHeight = highresImageHeight;
    }
    public
    @DrawableRes
     int getBookmarkActivityDrawable() {
        return this.isBookmarked() ? R.drawable.vector_bookmarked : R.drawable.vector_unbookmarked;
    }

    public
    @DrawableRes
    int getLikeActivityDrawable() {
        return this.isLiked ? R.drawable.vector_like : R.drawable.vector_unlike;
    }

}
