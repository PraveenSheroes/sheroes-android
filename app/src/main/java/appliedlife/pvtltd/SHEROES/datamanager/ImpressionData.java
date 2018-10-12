package appliedlife.pvtltd.SHEROES.datamanager;

import com.google.gson.annotations.SerializedName;

public class ImpressionData {

    @SerializedName("userId")
    private String userId;

    @SerializedName("clientId")
    private String clientId;

    @SerializedName("postId") //*
    private String postId;

    @SerializedName("position")
    private int position;

    @SerializedName("engagementTime")
    private double engagementTime;

    @SerializedName("timestamp") //*
    private long timeStamp;

    @SerializedName("screenName")
    private String screenName;

    @SerializedName("event") //*
    private String event;

    @SerializedName("ipAddress")
    private String ipAddress;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("gtid") //*
    private String gtid;

    @SerializedName("userAgent")
    private String userAgent ;

    @SerializedName("sourceScreen")
    private String source;

    @SerializedName("sourceTab")
    private String sourceTab;

    @SerializedName("sourceCollection")
    private String sourceCollection;

    @SerializedName("sourceURL")
    private String sourceURL;

    @SerializedName("postType")
    private String postType;

    @SerializedName("appVersion")
    private String appVersion;

    @SerializedName("feedConfigVersion")
    private int feedConfigVersion;

    @SerializedName("configVersion")
    private String configVersion;

    @SerializedName("configType")
    private String configType;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getEngagementTime() {
        return engagementTime;
    }

    public void setEngagementTime(double engagementTime) {
        this.engagementTime = engagementTime;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceCollection() {
        return sourceCollection;
    }

    public void setSourceCollection(String sourceCollection) {
        this.sourceCollection = sourceCollection;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public int getFeedConfigVersion() {
        return feedConfigVersion;
    }

    public void setFeedConfigVersion(int feedConfigVersion) {
        this.feedConfigVersion = feedConfigVersion;
    }

    // Duration for which the view has been viewed.
    private transient long viewDuration = -1;

    // ID for the view that was viewed (we'll use the position of the item here).
    private transient int viewId;

    //start time
    private transient long mStartTime = -1;

    //end time
    private transient long mEndTime = -1;

    public long getViewDuration() {
        return viewDuration;
    }

    public void setViewDuration(long viewDuration) {
        this.viewDuration = viewDuration;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getSourceTab() {
        return sourceTab;
    }

    public void setSourceTab(String sourceTab) {
        this.sourceTab = sourceTab;
    }
}
