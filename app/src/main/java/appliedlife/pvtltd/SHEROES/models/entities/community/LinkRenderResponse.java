package appliedlife.pvtltd.SHEROES.models.entities.community;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 18-07-2017.
 */

public class LinkRenderResponse  extends BaseResponse{
    @SerializedName("is_og_video_link_b")
    @Expose
    private boolean isOgVideoLinkB;

    @SerializedName("og_description_s")
    @Expose
    private String ogDescriptionS;
    @SerializedName("og_image_url_s")
    @Expose
    private String ogImageUrlS;
    @SerializedName("og_requested_url_s")
    @Expose
    private String ogRequestedUrlS;
    @SerializedName("og_title_s")
    @Expose
    private String ogTitleS;

    public boolean isOgVideoLinkB() {
        return isOgVideoLinkB;
    }

    public void setOgVideoLinkB(boolean ogVideoLinkB) {
        isOgVideoLinkB = ogVideoLinkB;
    }

    public String getOgDescriptionS() {
        return ogDescriptionS;
    }

    public void setOgDescriptionS(String ogDescriptionS) {
        this.ogDescriptionS = ogDescriptionS;
    }

    public String getOgImageUrlS() {
        return ogImageUrlS;
    }

    public void setOgImageUrlS(String ogImageUrlS) {
        this.ogImageUrlS = ogImageUrlS;
    }

    public String getOgRequestedUrlS() {
        return ogRequestedUrlS;
    }

    public void setOgRequestedUrlS(String ogRequestedUrlS) {
        this.ogRequestedUrlS = ogRequestedUrlS;
    }

    public String getOgTitleS() {
        return ogTitleS;
    }

    public void setOgTitleS(String ogTitleS) {
        this.ogTitleS = ogTitleS;
    }
}
