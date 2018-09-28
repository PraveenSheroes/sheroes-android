package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEvent {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("appVersion")
    @Expose
    private String appVersion;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("userAgent")
    @Expose
    private String userAgent;
    @SerializedName("gtid")
    @Expose
    private String gtid;
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("engagementTime")
    @Expose
    private String engagementTime;
    @SerializedName("timestamp")
    @Expose
    private long timestamp;
    @SerializedName("feedConfigVersion")
    @Expose
    private String feedConfigVersion;
    @SerializedName("setOrderKey")
    @Expose
    private String setOrderKey;
    @SerializedName("configType")
    @Expose
    private String configType;
    @SerializedName("configVersion")
    @Expose
    private String configVersion;
    @SerializedName("screenName")
    @Expose
    private String screenName;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sourceCollection")
    @Expose
    private String sourceCollection;
    @SerializedName("sourceURL")
    @Expose
    private String sourceURL;
    @SerializedName("postType")
    @Expose
    private String postType;
    @SerializedName("positionInList")
    @Expose
    private String positionInList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEngagementTime() {
        return engagementTime;
    }

    public void setEngagementTime(String engagementTime) {
        this.engagementTime = engagementTime;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFeedConfigVersion() {
        return feedConfigVersion;
    }

    public void setFeedConfigVersion(String feedConfigVersion) {
        this.feedConfigVersion = feedConfigVersion;
    }

    public String getSetOrderKey() {
        return setOrderKey;
    }

    public void setSetOrderKey(String setOrderKey) {
        this.setOrderKey = setOrderKey;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
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

    public String getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(String positionInList) {
        this.positionInList = positionInList;
    }

}


