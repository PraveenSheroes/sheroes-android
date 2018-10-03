package appliedlife.pvtltd.SHEROES.models.entities.feed;

public class TrackingData1 {

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

    private long userId;

    private long postId;

    private long timeStamp;

    private String type;

    public double getPercentageHeightVisible() {
        return percentageHeightVisible;
    }

    public void setPercentageHeightVisible(double percentageHeightVisible) {
        this.percentageHeightVisible = percentageHeightVisible;
    }

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

    @Override
    public String toString() {
        return viewId+":"+(mEndTime - mStartTime);
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}