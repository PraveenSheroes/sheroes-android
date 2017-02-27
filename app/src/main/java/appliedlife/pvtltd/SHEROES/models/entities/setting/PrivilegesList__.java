
package appliedlife.pvtltd.SHEROES.models.entities.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrivilegesList__ {

    @SerializedName("portfolio_link")
    @Expose
    private PortfolioLink portfolioLink;
    @SerializedName("image_title")
    @Expose
    private ImageTitle imageTitle;

    public PortfolioLink getPortfolioLink() {
        return portfolioLink;
    }

    public void setPortfolioLink(PortfolioLink portfolioLink) {
        this.portfolioLink = portfolioLink;
    }

    public ImageTitle getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(ImageTitle imageTitle) {
        this.imageTitle = imageTitle;
    }

}
