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

    @ColumnInfo(name = "position")
    private int position;

    @ColumnInfo(name = "engagementTime")
    private String engagementTime;

    @ColumnInfo(name = "timestamp")
    private String timeStamp;

    @ColumnInfo(name = "screenName")
    private String screenName;

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

    public String getEngagementTime() {
        return engagementTime;
    }

    public void setEngagementTime(String engagementTime) {
        this.engagementTime = engagementTime;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
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

    public String getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(String positionInList) {
        this.positionInList = positionInList;
    }

    @ColumnInfo(name = "source")
    private String source;

    @ColumnInfo(name = "sourceCollection")
    private String sourceCollection;

    @ColumnInfo(name = "sourceURL")
    private String sourceURL;

    @ColumnInfo(name = "postType")
    private String postType;

    @ColumnInfo(name = "positionInList")
    private String positionInList;

    // Duration for which the view has been viewed.
    private long viewDuration = -1;

    // ID for the view that was viewed (we'll use the position of the item here).
    private int viewId;

    // Percentage of the height visible
    private double percentageHeightVisible;

    //start time
    private long mStartTime = -1;

    //end time
    private long mEndTime = -1;

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

    public double getPercentageHeightVisible() {
        return percentageHeightVisible;
    }

    public void setPercentageHeightVisible(double percentageHeightVisible) {
        this.percentageHeightVisible = percentageHeightVisible;
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
}
