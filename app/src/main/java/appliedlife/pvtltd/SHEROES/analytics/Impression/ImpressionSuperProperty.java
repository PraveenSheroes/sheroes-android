package appliedlife.pvtltd.SHEROES.analytics.Impression;

/**
 * Model class with super properties required for impression helper
 *
 * @author ravi
 */
public class ImpressionSuperProperty {

    private String mCommunityTab;

    private long mLoggedInUserId = -1;

    private String mSourceCollection;

    private String mOrderKey;

    private String mLanguage;

    public String getCommunityTab() {
        return mCommunityTab;
    }

    public void setCommunityTab(String mCommunityTab) {
        this.mCommunityTab = mCommunityTab;
    }

    public long getLoggedInUserId() {
        return mLoggedInUserId;
    }

    public void setLoggedInUserId(long mLoggedInUserId) {
        this.mLoggedInUserId = mLoggedInUserId;
    }

    public String getSourceCollection() {
        return mSourceCollection;
    }

    public void setSourceCollection(String mSourceCollection) {
        this.mSourceCollection = mSourceCollection;
    }

    public String getOrderKey() {
        return mOrderKey;
    }

    public void setOrderKey(String mOrderKey) {
        this.mOrderKey = mOrderKey;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String mLanguage) {
        this.mLanguage = mLanguage;
    }
}
