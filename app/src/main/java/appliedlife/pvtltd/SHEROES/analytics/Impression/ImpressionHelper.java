package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppModule;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Helper class for Impression help to detect visible impression and exit of impression view
 */
public class ImpressionHelper {

    //region private member variables
    private static final String TAG = ImpressionHelper.class.getName();
    private static final String CLIENT_ID = "2";
    public static final int SCROLL_UP = 2;
    public static final int SCROLL_DOWN = 1;

    private boolean scrollDirectionChange = false;
    private boolean isRunning;

    private int lastDirectionId = -1;
    private int mImpressionVisibilityThreshold;
    private int mImpressionBatchSize;
    private int mImpressionFrequency;
    private float minEngagementTime;
    private long mLoggedInUser;
    private long lastScrollingEndTime;

    private ImpressionCallback mImpressionCallback;
    private CountDownTimer countDownTimer;
    private AppUtils mAppUtils;
    private Preference<Configuration> mConfiguration;
    private ImpressionSuperProperty mImpressionProperty;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    // ArrayList of view ids that are being considered for tracking.
    private ArrayList<Integer> currentViewed = new ArrayList<>();
    private ArrayList<Integer> allVisibleViews = new ArrayList<>();
    private List<ImpressionData> finalViewData = new ArrayList<>();

    //endregion

    //region constructor
    public ImpressionHelper(ImpressionSuperProperty impressionSuperProperty, Preference<Configuration> configuration, RecyclerView recyclerView, LinearLayoutManager layoutManager, long loggedInUserId, AppUtils appUtils, ImpressionCallback impressionCallback) {
        this.mAppUtils = appUtils;
        this.mLoggedInUser = loggedInUserId;
        this.mImpressionCallback = impressionCallback;
        this.mImpressionProperty = impressionSuperProperty;
        this.mConfiguration = configuration;
        this.recyclerView =  recyclerView;
        this.linearLayoutManager = layoutManager;
        init();
    }

    private void init() {
        mImpressionVisibilityThreshold = new ConfigData().visibilityPercentage;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //Get the view visibility percentage from remote config
            mImpressionVisibilityThreshold = mConfiguration.get().configData.visibilityPercentage;
        }

        minEngagementTime = new ConfigData().minEngagementTime;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //Get min engagement time for impression
            minEngagementTime = mConfiguration.get().configData.minEngagementTime;
        }

        minEngagementTime = minEngagementTime/1000f; //i.e 0.25 ms

        mImpressionBatchSize = new ConfigData().frequencyBatchRequest;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //get the batch size
            mImpressionBatchSize = mConfiguration.get().configData.frequencyBatchRequest;
        }

        mImpressionFrequency = new ConfigData().impressionFrequency;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //get impression frequency in ms
            mImpressionFrequency = mConfiguration.get().configData.impressionFrequency;
        }

        mImpressionFrequency = mImpressionFrequency/3; //todo - testing purpose
    }
    //endregion

    //region public method

    public void scrollingIdle(long idleTime) {
        lastScrollingEndTime = idleTime;
    }

    /**
     * Fragment/ Activity is resumed
     */
    public void onResume() {
        LogUtils.info(TAG, "On Resume");

        //Get the visible items on screen
        int startPos = linearLayoutManager.findFirstVisibleItemPosition();
        int endPos = linearLayoutManager.findLastVisibleItemPosition();
        if (startPos >= 0 || endPos > 0) {
            for (int viewPosition = startPos; viewPosition <= endPos; viewPosition++) {
                allVisibleViews.add(viewPosition);
                getValidImpressionView(viewPosition, recyclerView);
            }
        }
    }

    public void getGlobalLayoutChanges(final RecyclerView recyclerView) {

        final ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    recyclerView.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                } else {
                    recyclerView.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                }

                LogUtils.info(TAG, "Global layout change");
                onResume();
            }
        };
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
    }


    /**
     * Fragment/ Activity is paused
     */
    public void onPause() {
        LogUtils.info(TAG, "On Pause");
        updateEndTimeOfItems();
        storeChunks();
    }

    /**
     * Get callback when recycler view is being scrolled and scroll state changing
     *
     * @param startPos     first visible item on screen
     * @param endPos       last visible item on screen
     */
    public void onScrollChange(RecyclerView recyclerView, int scrollDirection, int startPos, int endPos) {
        if(scrollDirection>0 && scrollDirection!= lastDirectionId) {
            scrollDirectionChange = true;
        }
        lastDirectionId = scrollDirection;

        //Scroll view visibility and duration
        lastScrollingEndTime = System.currentTimeMillis();
        if (countDownTimer == null) {
            countDownTimer = new ImpressionTimer(mImpressionFrequency, 1000);
            countDownTimer.start();
        } else if (!isRunning) {
            countDownTimer.start();
        }

        analyzeAndAddViewData(scrollDirectionChange, scrollDirection, recyclerView, startPos, endPos);
    }

    /**
     * Update the end time for the item on paused
     */
    private void updateEndTimeOfItems() {
        if (finalViewData.size() > 0) {
            int size = finalViewData.size();
            for (int i = size - 1; i >= 0; i--) {
                ImpressionData trackingData1 = finalViewData.get(i);
                if (trackingData1.getEndTime() == -1) {
                    LogUtils.info(TAG, "Screen Exit: Update End time for all visible");

                    finalViewData.get(i).setEndTime(System.currentTimeMillis());
                    float timeSpent = finalViewData.get(i).getEndTime() - finalViewData.get(i).getStartTime();
                    finalViewData.get(i).setEngagementTime(timeSpent / 1000.0f);
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
                storeChunks();
                scrollDirectionChange = false;
            }

            for (int viewPosition = firstVisibleItemPosition; viewPosition <= lastVisibleItemPosition; viewPosition++) {
                allVisibleViews.add(viewPosition);
                getValidImpressionView(viewPosition, recyclerView);
            }

            if (allVisibleViews.size() > 0) { //Compare if any item was present , now not in the list
                for (int i = 0; i < allVisibleViews.size(); i++) {
                    int id = allVisibleViews.get(i);
                    View itemView = recyclerView.getLayoutManager().findViewByPosition(id);
                    if (itemView != null) {
                        int itemPosition = checkIfItemInFinal(id);
                        int lastItem = getLastItemInFinalList();
                        if (scrollDirection == SCROLL_UP) {
                            //if final list have that and not in current list and visibility below 20% marked as exit
                            if (itemPosition > -1 && lastItem > -1 && lastItem != id && !currentViewed.contains(id) && getVisibleHeightPercentage(itemView) < mImpressionVisibilityThreshold) {
                                updateEndTimeIfItemNotExist(id);
                            }
                        } else if (!currentViewed.contains(id) && getVisibleHeightPercentage(itemView) < mImpressionVisibilityThreshold) {
                            updateEndTimeIfItemNotExist(id);
                        }
                    }
                }
            }
            // store 10 events in DB
            if (finalViewData.size() > 10) {
                storeChunks();
            }
            currentViewed.clear();
            allVisibleViews.clear();
        }
    }

    private void getValidImpressionView(int viewPosition, RecyclerView recyclerView) {
        ImpressionData impressionData = updateProperties(recyclerView, viewPosition); //> 50 visible view
        if (impressionData == null) return;

        currentViewed.add(viewPosition);

        int itemPosition = checkIfItemInFinal(viewPosition);
        if (itemPosition == -1) { //new item add it in final list
            finalViewData.add(impressionData);
            mImpressionCallback.showToast("Screen Enter" + viewPosition);
        }
    }

    private ImpressionData updateProperties(RecyclerView recyclerView, int viewPosition) {
        View itemView = recyclerView.getLayoutManager().findViewByPosition(viewPosition);
        if (itemView == null) {
            return null;
        }

        if (getVisibleHeightPercentage(itemView) >= mImpressionVisibilityThreshold) {  //Only those views which have visibility >= 50%
            ImpressionData impressionData = new ImpressionData();
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
            FeedDetail feedDetail = mImpressionCallback.getListItemAtPos(viewPosition);

            if (feedDetail == null) return null;

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

            impressionData.setStartTime(System.currentTimeMillis());
            impressionData.setTimeStamp(System.currentTimeMillis());
            impressionData.setPostType(feedDetail.getSubType());
            impressionData.setStreamName(feedDetail.getStreamType());
            impressionData.setSourceTab(mImpressionProperty.getCommunityTab());
            impressionData.setSource(mImpressionCallback.getScreenName());
            impressionData.setOrderKey(mImpressionProperty.getOrderKey());
            impressionData.setPosition(viewPosition);
            impressionData.setUserAgent(recyclerView.getContext() != null ? SheroesAppModule.getUserAgent(recyclerView.getContext()) : "");
            impressionData.setClientId(CLIENT_ID); //For mobile android
            impressionData.setEvent(EVENT_TYPE.VIEW_IMPRESSION.getValue());
            impressionData.setAppVersion(mAppUtils.getAppVersionName());
            impressionData.setDeviceId(mAppUtils.getDeviceId());
            impressionData.setIpAddress(recyclerView.getContext() != null ? SheroesAppModule.getIpAddress() : "");
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
    private void storeChunks() {
        int index = getLastIndexOfUpdatedItem();
        if (index > -1) {
            List<ImpressionData> updatedImpressions = finalViewData.subList(0, index + 1);
            LogUtils.info(TAG, "###Added to db");
            mImpressionCallback.storeInDatabase(updatedImpressions,mImpressionBatchSize, minEngagementTime, false);

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
    private int getLastIndexOfUpdatedItem() {
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
     * @param id id of view
     */
    private void updateEndTimeIfItemNotExist(int id) {
        if (finalViewData.size() > 0) {
            int size = finalViewData.size();
            for (int i = size - 1; i >= 0; i--) {
                ImpressionData trackingData1 = finalViewData.get(i);
                if (trackingData1.getStartTime() != -1 && trackingData1.getEndTime() == -1 && id == trackingData1.getPosition()) {
                    finalViewData.get(i).setEndTime(System.currentTimeMillis());
                    double timeSpent = finalViewData.get(i).getEndTime() - finalViewData.get(i).getStartTime();
                    finalViewData.get(i).setEngagementTime(timeSpent / 1000.0f);

                    LogUtils.info(TAG, "@@@Screen Exit " +id + "End time" + timeSpent / 1000.0f);
                    mImpressionCallback.showToast("Screen Exit" + id + "::Duration" + timeSpent / 1000.0f);
                    break;
                }
            }
        }
    }

    /**
     * Check if the item is present in the final list or not
     *
     * @param id id of view
     * @return index of view if its present in the final list
     */
    private int checkIfItemInFinal(int id) {
        int index = -1;
        if (finalViewData.size() > 0) {
            int length = finalViewData.size();
            for (int i = length - 1; i >= 0; i--) {
                ImpressionData trackingData1 = finalViewData.get(i);
                if (trackingData1.getPosition() == id) { //&& trackingData1.getEndTime() != -1 //&& && id!=lastItemId && lastItemId!= -1 && trackingData.getmEndTime() == -1  &&
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    private int getLastItemInFinalList() {
        int viewId = -1;
        if (finalViewData.size() > 0) {
            int lastPos = finalViewData.size() - 1;
            viewId = finalViewData.get(lastPos).getPosition();
        }
        return viewId;
    }

    //endregion

    //region enum

    //Impression Event Type
    public enum EVENT_TYPE {

        VIEW_IMPRESSION("vi"),
        VIEW_CLICK("cl");

        private final String value;

        EVENT_TYPE(final String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }
    //endregion


    //Timer in android
    public class ImpressionTimer extends CountDownTimer {

        public ImpressionTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            if (System.currentTimeMillis() - lastScrollingEndTime > 100000) {
                LogUtils.info(TAG, "MAX time expired");
                    onPause();
                    isRunning = false;
                cancel();
            } else {
                LogUtils.info(TAG, "Time Expired");

                mImpressionCallback.sendImpression();
                isRunning = true;
                start();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // Log.i("Time Expired", "1 Min/60 sec");
        }
    }
}
