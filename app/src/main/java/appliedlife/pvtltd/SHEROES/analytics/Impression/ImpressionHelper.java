package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppModule;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;

/**
 * Helper class for Impression help to detect visible impression and exit of impression view
 *
 * @author ravi
 */
public class ImpressionHelper implements ImpressionTimer.ITimerCallback {

    //region private member variables
    private static final String TAG = ImpressionHelper.class.getName();

    private static final String CLIENT_ID = "2"; //i.e 2 = Android
    private static final String EVENT_TYPE_VI = "vi"; //view impression
    public static final int SCROLL_UP = 2;
    public static final int SCROLL_DOWN = 1;

    private boolean mScrollDirectionChange = false;

    private int mLastDirectionId = -1;
    private int mImpressionVisibilityThreshold;
    private int mImpressionBatchSize;
    private float mMinEngagementTime;
    private long mLoggedInUser;

    private ImpressionCallback mImpressionCallback;
    private AppUtils mAppUtils;
    private Preference<AppConfiguration> mConfiguration;
    private ImpressionSuperProperty mImpressionProperty;
    private ImpressionPresenter mImpressionPresenter;
    private RecyclerView mRecyclerView;
    private ImpressionTimer mImpressionTimer;

    // ArrayList of view ids that are being considered for tracking.
    private ArrayList<Long> currentViewed = new ArrayList<>();
    private ArrayList<Long> allVisibleViews = new ArrayList<>();
    private List<ImpressionData> finalViewData = new ArrayList<>();

    //endregion

    //region constructor
    public ImpressionHelper(ImpressionSuperProperty impressionSuperProperty, ImpressionPresenter impressionPresenter, Preference<AppConfiguration> configuration, RecyclerView recyclerView, long loggedInUserId, AppUtils appUtils, ImpressionCallback impressionCallback) {
        this.mAppUtils = appUtils;
        this.mLoggedInUser = loggedInUserId;
        this.mImpressionProperty = impressionSuperProperty;
        this.mConfiguration = configuration;
        this.mRecyclerView = recyclerView;
        this.mImpressionPresenter = impressionPresenter;
        this.mImpressionCallback = impressionCallback;
        init();
    }

    private void init() {
        ConfigData configData = new ConfigData();

        mImpressionVisibilityThreshold = configData.visibilityPercentage;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //Get the view visibility percentage from remote config
            mImpressionVisibilityThreshold = mConfiguration.get().configData.visibilityPercentage;
        }

        mMinEngagementTime = configData.minEngagementTime;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //Get min engagement time for impression
            mMinEngagementTime = mConfiguration.get().configData.minEngagementTime;
        }

        mImpressionBatchSize = configData.frequencyBatchRequest;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //get the batch size
            mImpressionBatchSize = mConfiguration.get().configData.frequencyBatchRequest;
        }

        int mImpressionFrequency = configData.impressionFrequency;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //get impression frequency in ms
            mImpressionFrequency = mConfiguration.get().configData.impressionFrequency;
        }
        mImpressionFrequency = mImpressionFrequency / 3;

        int mImpressionMaxTimeout = configData.impressionMaxTimeout;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //get impression frequency in ms
            mImpressionMaxTimeout = mConfiguration.get().configData.impressionMaxTimeout;
        }

        mImpressionTimer = ImpressionTimer.getInstance(mImpressionFrequency, mImpressionFrequency, mImpressionMaxTimeout, this);
    }
    //endregion

    //region public method

    public void scrollingIdle(long idleTime) {
        mImpressionTimer.setLastScrollingEndTime(idleTime);
    }

    /**
     * Fragment/ Activity is resumed
     */
    public void getVisibleViews(int startPos, int endPos) {
        //Get the visible items on screen
        allVisibleViews.clear();
        for (int viewPosition = startPos; viewPosition <= endPos; viewPosition++) {
            getValidImpressionView(viewPosition, mRecyclerView);
        }
    }

    /**
     * Fragment/ Activity is paused
     */
    public void stopImpression() {
        updateEndTimeOfItems();
        storeChunks(true);

        if (mImpressionTimer != null) {
            mImpressionTimer.setTimerRunning(false);
            mImpressionTimer.cancel();
        }
    }

    /**
     * Get callback when recycler view is being scrolled and scroll state changing
     *
     * @param startPos first visible item on screen
     * @param endPos   last visible item on screen
     */
    public void onScrollChange(RecyclerView recyclerView, int scrollDirection, int startPos, int endPos) {
        if (scrollDirection > 0 && scrollDirection != mLastDirectionId) {
            mScrollDirectionChange = true;
        }
        mLastDirectionId = scrollDirection;

        //Scroll view visibility and duration

        if (!mImpressionTimer.isTimerRunning()) {
            mImpressionTimer.setTimerRunning(true);
            mImpressionTimer.start();
        }
        mImpressionTimer.setLastScrollingEndTime(System.currentTimeMillis());

        analyzeAndAddViewData(mScrollDirectionChange, scrollDirection, recyclerView, startPos, endPos);
    }

    /**
     * Update the end time for the item on paused
     */
    private void updateEndTimeOfItems() {
        if (finalViewData.size() > 0) {
            int size = finalViewData.size();
            for (int i = size - 1; i >= 0; i--) {
                ImpressionData impressionData = finalViewData.get(i);
                if (impressionData.getEndTime() == -1) {
                    finalViewData.get(i).setEndTime(System.currentTimeMillis());

                    int timeSpent = (int) (finalViewData.get(i).getEndTime() - finalViewData.get(i).getTimeStamp());
                    finalViewData.get(i).setEngagementTime(timeSpent);
                }
            }
        }
    }
    //endregion

    //region private method
    //Data request for Impression
    private synchronized void analyzeAndAddViewData(boolean directionChange, int scrollDirection, RecyclerView recyclerView, int firstVisibleItemPosition, int lastVisibleItemPosition) {

        synchronized (this) {
            if (directionChange) {
                storeChunks(false);
                mScrollDirectionChange = false;
            }

            for (int viewPosition = firstVisibleItemPosition; viewPosition <= lastVisibleItemPosition; viewPosition++) {
                getValidImpressionView(viewPosition, recyclerView);
            }

            if (allVisibleViews.size() > 0) { //Compare if any item was present , now not in the list
                for (long postId : allVisibleViews) {
                    if (postId == -1) continue;

                    int positionOfView = mImpressionCallback.findPositionById(postId);
                    if (positionOfView == -1) continue; //skip if position not found

                    View itemView = recyclerView.getLayoutManager().findViewByPosition(positionOfView);
                    if (itemView == null) continue; //skip if view not found

                    int itemPosition = checkIfItemInFinal(postId);
                    long lastItem = getLastItemInFinalList();
                    boolean validImpression = getVisibleHeightPercentage(itemView) < mImpressionVisibilityThreshold;

                    if (scrollDirection == SCROLL_UP) {
                        //if final list have that and not in current list and visibility below 20% marked as exit
                        if (itemPosition > -1 && lastItem > -1 && lastItem != postId && !currentViewed.contains(postId) && validImpression) {
                            updateEndTimeIfItemNotExist(postId);
                        }
                    } else if (!currentViewed.contains(postId) && validImpression) {
                        updateEndTimeIfItemNotExist(postId);
                    }
                }
            }

            // store 10 events in DB
            if (finalViewData.size() >= mImpressionBatchSize) { //split when final list reach batch size but it may have impression which are below min engagement time , will get filter later
                storeChunks(false);
            }
            currentViewed.clear();
            allVisibleViews.clear();
        }
    }

    private void getValidImpressionView(int viewPosition, RecyclerView recyclerView) {

        FeedDetail feedDetail = mImpressionCallback.getListItemAtPos(viewPosition);
        if (feedDetail == null) return;
        long postId = feedDetail.getIdOfEntityOrParticipant();
        if (postId == -1) return;

        allVisibleViews.add(postId);

        ImpressionData impressionData = updateProperties(recyclerView, feedDetail, viewPosition); //> 20 visible view
        if (impressionData == null) return;

        currentViewed.add(postId);

        int itemPosition = checkIfItemInFinal(postId);
        if (itemPosition == -1) { //new item add it in final list
            finalViewData.add(impressionData);
        }
    }

    private ImpressionData updateProperties(RecyclerView recyclerView, FeedDetail feedDetail, int viewPosition) {
        View itemView = recyclerView.getLayoutManager().findViewByPosition(viewPosition);
        if (itemView == null) {
            return null;
        }

        if (getVisibleHeightPercentage(itemView) >= mImpressionVisibilityThreshold) {  //Only those views which have visibility >= 50%
            ImpressionData impressionData = new ImpressionData();
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();

            //Ignore header
            if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.HOME_FEED_HEADER))
                return null;

            if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.CAROUSEL_SUB_TYPE)) {
                //Add position in list
                //trackingData.setPosition();
            }

            if (prefs != null && prefs.contains(AppConstants.FEED_CONFIG_VERSION)) {
                impressionData.setFeedConfigVersion(Integer.valueOf(prefs.getString(AppConstants.FEED_CONFIG_VERSION, "0")));
            }

            //impressionData.setStartTime(System.currentTimeMillis());
            impressionData.setTimeStamp(System.currentTimeMillis());
            impressionData.setPostType(feedDetail.getSubType());
            impressionData.setStreamName(feedDetail.getStreamType());
            impressionData.setSourceTab(mImpressionProperty.getCommunityTab());
            impressionData.setSource(mImpressionCallback.getScreenName());
            impressionData.setOrderKey(mImpressionProperty.getOrderKey());
            impressionData.setPosition(viewPosition);
            impressionData.setUserAgent(recyclerView.getContext() != null ? SheroesAppModule.getUserAgent(recyclerView.getContext()) : "");
            impressionData.setClientId(CLIENT_ID); //For mobile android
            impressionData.setEvent(EVENT_TYPE_VI);
            impressionData.setAppVersion(mAppUtils.getAppVersionName());
            impressionData.setDeviceId(mAppUtils.getDeviceId());
            impressionData.setConfigType(mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configType != null ? mConfiguration.get().configType : "");
            impressionData.setConfigVersion(mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configVersion != null ? mConfiguration.get().configVersion : "");
            impressionData.setGtid(UUID.randomUUID().toString());
            impressionData.setUserId(mLoggedInUser == -1 ? "" : String.valueOf(mLoggedInUser));
            impressionData.setPostId(feedDetail.getIdOfEntityOrParticipant() != -1 ? String.valueOf(feedDetail.getIdOfEntityOrParticipant()) : "");
            return impressionData;
        }
        return null;
    }

    /**
     * Store the final Impression in db
     */
    private void storeChunks(boolean forceNetworkCall) {
        int index = getItemWithUpdatedEngagementTime();
        if (index > -1) {
            List<ImpressionData> updatedImpressions = finalViewData.subList(0, index + 1);
            mImpressionPresenter.storeBatchInDb(mMinEngagementTime, mImpressionBatchSize, updatedImpressions, forceNetworkCall);

            if (finalViewData.size() >= index + 1) { //recheck sublist in multiple case
                finalViewData = finalViewData.subList(index + 1, finalViewData.size());
            }
        }
    }

    /**
     * Get the last item on which end time was updated
     *
     * @return index of item which have end time updated
     */
    private int getItemWithUpdatedEngagementTime() {
        int index = -1;
        if (finalViewData.size() > 0) {
            for (int i = finalViewData.size() - 1; i >= 0; i--) {
                ImpressionData impressionData = finalViewData.get(i);
                if (impressionData.getEndTime() != -1) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /**
     * Get the percentage of view height visible
     *
     * @param view visible view
     * @return visibility percentage
     */
    private double getVisibleHeightPercentage(View view) {
        final Rect itemRect = new Rect();

        if (view == null || view.getVisibility() != View.VISIBLE || view.getParent() == null) {
            return -1;
        }

        if (!view.getGlobalVisibleRect(itemRect)) {
            return -1;
        }

        // Find the height of the item.
        double visibleHeight = itemRect.height();
        double height = view.getHeight();

        return ((visibleHeight / height) * 100);
    }

    /**
     * Update the end time of the view which are not visible now or visibility percentage in below the minVisiblePercentage
     *
     * @param postId id of view
     */
    private void updateEndTimeIfItemNotExist(long postId) {
        if (finalViewData.size() > 0) {
            int size = finalViewData.size();
            for (int i = size - 1; i >= 0; i--) {
                ImpressionData impressionData = finalViewData.get(i);
                if (impressionData.getTimeStamp() != -1 && impressionData.getEndTime() == -1 && impressionData.getPostId().equalsIgnoreCase(String.valueOf(postId))) {
                    finalViewData.get(i).setEndTime(System.currentTimeMillis());
                    int timeSpent = (int) (finalViewData.get(i).getEndTime() - finalViewData.get(i).getTimeStamp());
                    finalViewData.get(i).setEngagementTime(timeSpent);
                    break;
                }
            }
        }
    }

    /**
     * Check if the item is present in the final list or not
     *
     * @param postId id of view
     * @return index of view if its present in the final list
     */
    private int checkIfItemInFinal(long postId) {
        int index = -1;
        if (finalViewData.size() > 0) {
            int length = finalViewData.size();
            for (int i = length - 1; i >= 0; i--) {
                ImpressionData impressionData = finalViewData.get(i);
                if (impressionData.getPostId().equalsIgnoreCase(String.valueOf(postId))) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    private long getLastItemInFinalList() {
        long viewId = -1;
        if (finalViewData.size() > 0) {
            int lastPos = finalViewData.size() - 1;
            viewId = Long.valueOf(finalViewData.get(lastPos).getPostId());
        }
        return viewId;
    }

    @Override
    public void stopTimer() {
        stopImpression();
    }

    @Override
    public void sendImpressions() {
        mImpressionPresenter.impressionObserver(mImpressionBatchSize, true);
    }

    //endregion
}
