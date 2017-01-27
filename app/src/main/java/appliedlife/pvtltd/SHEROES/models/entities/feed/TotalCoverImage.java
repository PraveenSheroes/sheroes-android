
package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalCoverImage {

    @SerializedName("feedDetailImageUrl")
    @Expose
    private String feedDetailImageUrl;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("width")
    @Expose
    private int width;

    public String getFeedDetailImageUrl() {
        return feedDetailImageUrl;
    }

    public void setFeedDetailImageUrl(String feedDetailImageUrl) {
        this.feedDetailImageUrl = feedDetailImageUrl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
