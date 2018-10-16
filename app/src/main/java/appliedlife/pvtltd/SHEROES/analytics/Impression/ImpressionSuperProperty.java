package appliedlife.pvtltd.SHEROES.analytics.Impression;

/**
 * Model class with super properties required for impression helper
 */
public class ImpressionSuperProperty {

    private String communityTab;

    private long loggedInUserId = -1;

    private String sourceCollection;

    private String orderKey;

    public String getCommunityTab() {
        return communityTab;
    }

    public void setCommunityTab(String communityTab) {
        this.communityTab = communityTab;
    }

    public long getLoggedInUserId() {
        return loggedInUserId;
    }

    public void setLoggedInUserId(long loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public String getSourceCollection() {
        return sourceCollection;
    }

    public void setSourceCollection(String sourceCollection) {
        this.sourceCollection = sourceCollection;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }
}
