package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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

/**
 * Helper class for Impression help to detect visible impression and exit of impression view
 */
public class ImpressionHelper {

    //region private member variables
    public static final int SCROLL_UP = 2;
    public static final int SCROLL_DOWN = 1;

    private long mLoggedInUser;
    private boolean scrollDirectionChange = false;

    private int lastDirectionId = -1;
    private int mImpressionVisibilityThreshold = 20;
    private ImpressionCallback mImpressionCallback;

    private AppUtils mAppUtils;
    private Preference<Configuration> mConfiguration;
    private ImpressionSuperProperty mImpressionProperty;

    // ArrayList of view ids that are being considered for tracking.
    private ArrayList<Integer> previousViewed = new ArrayList<>();
    private ArrayList<Integer> currentViewed = new ArrayList<>();
    private List<ImpressionData> finalViewData = new ArrayList<>();
    //endregion

    //region constructor
    public ImpressionHelper(ImpressionSuperProperty impressionSuperProperty, Preference<Configuration> configuration, long loggedInUserId, AppUtils appUtils, ImpressionCallback impressionCallback) {
        this.mAppUtils = appUtils;
        this.mLoggedInUser = loggedInUserId;
        this.mImpressionCallback = impressionCallback;
        this.mImpressionProperty = impressionSuperProperty;
        this.mConfiguration = configuration;
        init();
    }

    private void init() {
        //Get the view min Visibility of View from remote config
        mImpressionVisibilityThreshold = new ConfigData().visibilityPercentage;
        if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //Get the view visibility percentage from remote config
            mImpressionVisibilityThreshold = mConfiguration.get().configData.visibilityPercentage;
        }
    }
    //endregion

    //region public method

    /**
     * Fragment/ Activity is resumed
     */
    public void onResume() {
        // Log.i("###IH-Resume", "On Resume");
    }

    /**
     * Fragment/ Activity is paused
     */
    public void onPause() {
        //Log.i("###IH-Pause", "On Pause");
        updateEndTimeOfItems();
        mImpressionCallback.storeInDatabase(finalViewData);
    }

    /**
     * Get callback when recycler view is being scrolled and scroll state changing
     *
     * @param recyclerView recyclerView
     * @param startPos     first visible item on screen
     * @param endPos       last visible item on screen
     */
    public void onScrollChange(int scrollDirection, RecyclerView recyclerView, int startPos, int endPos) {
        if(scrollDirection>0 && scrollDirection!= lastDirectionId) {
            scrollDirectionChange = true;
        }
        lastDirectionId = scrollDirection;

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
                if (trackingData1.getEndTime() == -1) { //
                    Log.i("Screen Exit", "Update End time for all visible");

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

        if (directionChange) {
            Log.d("@@@Dir beforechange-clr", "::" + finalViewData.size());
            storeChunks(firstVisibleItemPosition, lastVisibleItemPosition);
            Log.d("@@@Dir After change-clr", "::" + finalViewData.size());
            for(int i=0; i<finalViewData.size(); i++) {
                Log.i("@@@", ":"+finalViewData.get(i).getPosition() +" :"+ finalViewData.get(i).getEngagementTime());
            }
            scrollDirectionChange = false;
        }

        if (scrollDirection == SCROLL_UP) { //Swap loop on scroll up
            for (int viewPosition = lastVisibleItemPosition; viewPosition >= firstVisibleItemPosition; viewPosition--) {
                analayzeView(viewPosition, scrollDirection, recyclerView);
            }
        } else if (scrollDirection == SCROLL_DOWN) {
            for (int viewPosition = firstVisibleItemPosition; viewPosition <= lastVisibleItemPosition; viewPosition++) {
                analayzeView(viewPosition, scrollDirection, recyclerView);
            }
        }

        if(scrollDirection == SCROLL_UP || scrollDirection == SCROLL_DOWN) {
            if (previousViewed.size() > 0) { //Compare if any item was present , now not in the list
                for (int i = 0; i < previousViewed.size(); i++) {
                    int id = previousViewed.get(i);
                    View itemView = recyclerView.getLayoutManager().findViewByPosition(id);
                    if (itemView != null) {
                        if (!currentViewed.contains(id) && getVisibleHeightPercentage(itemView) <= mImpressionVisibilityThreshold) {
                            updateEndTimeIfItemNotExist(id);
                        }
                    }
                }
                previousViewed.clear();
            }

            if (currentViewed.size() > 0) {
                previousViewed.addAll(currentViewed);
            }

            // store 10 events in DB
            if (finalViewData.size() > 10) {
                storeChunks(firstVisibleItemPosition, lastVisibleItemPosition);
            }
            currentViewed.clear();
        }
    }


    private void analayzeView(int viewPosition, int scrollDirection, RecyclerView recyclerView) {
        ImpressionData impressionData = updateProperties(recyclerView, viewPosition); //> 50 visible view
        if (impressionData == null) return;

        currentViewed.add(viewPosition);

        int itemPosition = checkIfItemInFinal(viewPosition);
        if (scrollDirection == SCROLL_UP && itemPosition == -1) {
            addNewEntry(viewPosition, impressionData);
            // finalViewData.add(impressionData);
        } else if (scrollDirection == SCROLL_DOWN && itemPosition == -1) { //new item add it in final list
            finalViewData.add(impressionData);
            Log.d("@@@New Enter- Down", "::" + viewPosition);
            mImpressionCallback.showToast("Screen Enter" + viewPosition);
        }

       /* if(directionId == SCROLL_UP) {
            Log.i("Update item in final", ""+finalViewData.size());
            //updateEndTimeOfItems();
           // updateItems();
        } else*/
    }


    //Update items which are not present in current
    private void updateItems() {
        if (finalViewData.size() > 0) {
            int length = finalViewData.size();
            for (int id = length - 1; id >= 0; id--) {
                ImpressionData trackingData1 = finalViewData.get(id);
                if (trackingData1.getEndTime() == -1) { //
                    Log.i("Screen Exit", "Update End time for all visible");

                    if (!currentViewed.contains(finalViewData.get(id).getPosition())) {
                        finalViewData.get(id).setEndTime(System.currentTimeMillis());
                        float timeSpent = finalViewData.get(id).getEndTime() - finalViewData.get(id).getStartTime();
                        finalViewData.get(id).setEngagementTime(timeSpent / 1000.0f);

                        Log.i("@@@Screen Exit ", +id + "End time" + timeSpent / 1000.0f);
                        mImpressionCallback.showToast("Screen Exit" + id + "::Duration" + timeSpent / 1000.0f);
                    }
                }
            }
        }
    }

    private ImpressionData updateProperties(RecyclerView recyclerView, int viewPosition) {
        View itemView = recyclerView.getLayoutManager().findViewByPosition(viewPosition);
        if (itemView == null) {
            Log.i("@@@", "view null 1");
            return null;
        }

        if (getVisibleHeightPercentage(itemView) > mImpressionVisibilityThreshold) {  //Only those views which have visibility >= 50%
            ImpressionData impressionData = new ImpressionData();
            SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();

            FeedDetail feedDetail = mImpressionCallback.getListItemAtPos(viewPosition);
            if (feedDetail == null) return null;

            impressionData.setStartTime(System.currentTimeMillis());
            impressionData.setTimeStamp(System.currentTimeMillis());
           // impressionData.setViewId(viewPosition);
            impressionData.setPostType(feedDetail.getSubType());
            if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.CAROUSEL_SUB_TYPE)) {
                //Add position in list
                //trackingData.setPosition();
            }
            impressionData.setStreamName(mImpressionProperty.getOrderKey());
            impressionData.setSourceTab(mImpressionProperty.getCommunityTab()); //with latest 1
            impressionData.setSource(mImpressionCallback.getScreenName()); //with latest 2
            impressionData.setOrderKey(mImpressionProperty.getOrderKey());
            impressionData.setPosition(viewPosition);
            impressionData.setUserAgent(recyclerView.getContext() != null ? SheroesAppModule.getUserAgent(recyclerView.getContext()) : "");
            impressionData.setClientId(String.valueOf(CLIENT.ANDROID.getValue())); //For mobile android
            impressionData.setEvent(EVENT_TYPE.VIEW_IMPRESSION.getValue());
            impressionData.setAppVersion(mAppUtils.getAppVersionName());
            impressionData.setDeviceId(mAppUtils.getDeviceId());
            impressionData.setIpAddress(recyclerView.getContext() != null ? SheroesAppModule.getIpAddress() : "");
            impressionData.setConfigType(mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configType != null ? mConfiguration.get().configType : "");
            impressionData.setConfigVersion(mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configVersion != null ? mConfiguration.get().configVersion : "");
            impressionData.setGtid(UUID.randomUUID().toString());
            if (prefs != null && prefs.contains(AppConstants.FEED_CONFIG_VERSION)) {
                impressionData.setFeedConfigVersion(Integer.valueOf(prefs.getString(AppConstants.FEED_CONFIG_VERSION, "0")));
            }

            impressionData.setUserId(mLoggedInUser == -1 ? "" : String.valueOf(mLoggedInUser));
            impressionData.setPostId(feedDetail.getIdOfEntityOrParticipant() != -1 ? String.valueOf(feedDetail.getIdOfEntityOrParticipant()) : "");

            return impressionData;
        }
        return null;
    }

    /**
     * Store the final Impression in db
     * @param firstVisibleItemPosition
     * @param lastVisibleItemPosition
     */
    private void storeChunks(int firstVisibleItemPosition, int lastVisibleItemPosition) {
        int index = getLastIndexOfUpdatedItem();
        if (index > -1) {
            List<ImpressionData> forDb = finalViewData.subList(0, index + 1);
            Log.i("@@@DB", "###Added to db");
            //Check while spliting is there any item whose time not updated , update that

            mImpressionCallback.storeInDatabase(forDb);

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
        Rect itemRect = new Rect();
        view.getLocalVisibleRect(itemRect);

        // Find the height of the item.
        double visibleHeight = itemRect.height();
        double height = view.getMeasuredHeight();

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

                    Log.i("@@@Screen Exit ", +id + "End time" + timeSpent / 1000.0f);
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
        } else {
            index = 0;
        }
        return index;
    }

    //Top scroll new entry
    private void addNewEntry(int viewPosition, ImpressionData impressionData) {
        int viewId = -1;
        if (finalViewData.size() > 0) {
            int lastPos = finalViewData.size() - 1;
            viewId = finalViewData.get(lastPos).getPosition();
        }

        if (viewId > viewPosition) {
            //Log.i("###IH-add lesser value", "on top scroll");
            Log.d("@@@New Enter -TOP", "::" + viewPosition);
            mImpressionCallback.showToast("Screen Enter" + viewPosition);
            finalViewData.add(impressionData);
        } //else {
        // Log.i("###IH-these are greater", "on top scroll" + viewPosition);
        //}
    }
    //endregion

    //region enum
    //Type of client
    public enum CLIENT {
        WEB(1),
        ANDROID(2),
        IOS(3),
        PayTM_WEB(401),
        PayTM_ANDROID(402),
        PayTM_IOS(403);

        private final int value;

        CLIENT(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

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
}
