package appliedlife.pvtltd.SHEROES.analytics.Impression;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for impression
 * @author ravi
 */
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

    @SerializedName("streamName")
    private String streamName;

    @SerializedName("timestamp") //*
    private long timeStamp;

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

    @SerializedName("setOrderKey")
    private String setOrderKey;

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

    void setClientId(String clientId) {
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

    double getEngagementTime() {
        return engagementTime;
    }

    void setEngagementTime(double engagementTime) {
        this.engagementTime = engagementTime;
    }

    void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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

    void setPostType(String postType) {
        this.postType = postType;
    }

    void setFeedConfigVersion(int feedConfigVersion) {
        this.feedConfigVersion = feedConfigVersion;
    }
    // ID for the view that was viewed (we'll use the position of the item here).
    private transient int viewId;

    //start time
    private transient long mStartTime = -1;

    //end time
    private transient long mEndTime = -1;

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    long getStartTime() {
        return mStartTime;
    }

    void setStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    long getEndTime() {
        return mEndTime;
    }

    void setEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    void setGtid(String gtid) {
        this.gtid = gtid;
    }

    void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    void setConfigType(String configType) {
        this.configType = configType;
    }

     void setSourceTab(String sourceTab) {
        this.sourceTab = sourceTab;
    }

    public String getOrderKey() {
        return setOrderKey;
    }

    public void setOrderKey(String setOrderKey) {
        this.setOrderKey = setOrderKey;
    }

    public String getStreamName() {
        return streamName;
    }

    void setStreamName(String streamName) {
        this.streamName = streamName;
    }
}
