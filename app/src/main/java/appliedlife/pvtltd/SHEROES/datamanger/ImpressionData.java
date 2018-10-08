package appliedlife.pvtltd.SHEROES.datamanger;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "impression_data")
public class ImpressionData {

    @PrimaryKey(autoGenerate = true)
    private int index;

    @ColumnInfo(name = "userId")
    private String userId;

    @ColumnInfo(name = "clientId")
    private String clientId;

    @ColumnInfo(name = "postId")
    private String postId;

    @ColumnInfo(name = "positionInList")
    private int position;

    @ColumnInfo(name = "engagementTime")
    private int engagementTime;

    @ColumnInfo(name = "timestamp")
    private long timeStamp;

    @ColumnInfo(name = "screenName")
    private String screenName;

    @ColumnInfo(name = "event")
    private String event;

    @ColumnInfo(name = "ipAddress")
    private String ipAddress;

    @ColumnInfo(name = "deviceId")
    private String deviceId;

    @ColumnInfo(name = "gtid")
    private String gtId;

    @ColumnInfo(name = "userAgent")
    private String userAgent = "Android";

    @ColumnInfo(name = "source")
    private String source;

    @ColumnInfo(name = "sourceCollection")
    private String sourceCollection;

    @ColumnInfo(name = "sourceURL")
    private String sourceURL;

    @ColumnInfo(name = "postType")
    private String postType;

    @ColumnInfo(name = "appVersion")
    private String appVersion;

    @ColumnInfo(name = "feedConfigVersion")
    private int feedConfigVersion;

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

    public int getEngagementTime() {
        return engagementTime;
    }

    public void setEngagementTime(int engagementTime) {
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public String getGtId() {
        return gtId;
    }

    public void setGtId(String gtId) {
        this.gtId = gtId;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
