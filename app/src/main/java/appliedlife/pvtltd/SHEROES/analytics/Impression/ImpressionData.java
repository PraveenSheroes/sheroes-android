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

    @SerializedName("postId")
    private String postId;

    @SerializedName("position")
    private int position;

    @SerializedName("engagementTime")
    private int engagementTime;

    @SerializedName("streamName")
    private String streamName;

    @SerializedName("timestamp")
    private long timeStamp;

    @SerializedName("event")
    private String event;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("gtid")
    private String gtid;

    @SerializedName("userAgent")
    private String userAgent;

    //current screen name
    @SerializedName("sourceScreen")
    private String source;

    @SerializedName("sourceTab")
    private String sourceTab;

    //for carousel
    @SerializedName("sourceCollection")
    private String sourceCollection;

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

    @SerializedName("language")
    private String language;

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

    void setEngagementTime(int engagementTime) {
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

    void setPostType(String postType) {
        this.postType = postType;
    }

    void setFeedConfigVersion(int feedConfigVersion) {
        this.feedConfigVersion = feedConfigVersion;
    }

    //end time
    private transient long mEndTime = -1;

    long getEndTime() {
        return mEndTime;
    }

    void setEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
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

    public void setOrderKey(String setOrderKey) {
        this.setOrderKey = setOrderKey;
    }

    void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getGtid() {
        return gtid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
