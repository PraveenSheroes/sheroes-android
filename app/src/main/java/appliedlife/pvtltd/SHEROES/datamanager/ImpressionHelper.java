package appliedlife.pvtltd.SHEROES.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import appliedlife.pvtltd.SHEROES.basecomponents.ImpressionCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesAppModule;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;

public class ImpressionHelper {

    //region private member variables
    private long mLoggedInUser;
    private int startViewId = -1;
    private int endViewId = -1;
    private int lastPosition = -1;
    private boolean lastDirectionDown = true;
    private boolean scrollDirectionChange = false;

    private boolean isScrollUp = false;
    private boolean isScrollDown = false;
    private int directionId = -1;
    private int lastDirectionId = -1;
    private boolean mIsTopScrolled;
    private boolean isLoaderVisible = false;
    private int viewVisibilityThreshold = 50;
    private ImpressionCallback mImpressionCallback;

    private AppUtils mAppUtils;
    private Preference<Configuration> mConfiguration;
    private ImpressionSuperProperty mImpressionProperty;

    // ArrayList of view ids that are being considered for tracking.
    private ArrayList<Integer> previousViewed = new ArrayList<>();
    private ArrayList<Integer> currentViewed = new ArrayList<>();
    private List<ImpressionData> finalViewData = new ArrayList<>();
    private List<Integer> lessVisible = new ArrayList<>();
    private Context mContext;
    //endregion

    //region constructor
    public ImpressionHelper(ImpressionSuperProperty impressionSuperProperty, int visibility, Preference<Configuration> configuration, long loggedInUserId, AppUtils appUtils, ImpressionCallback impressionCallback) {
        this.mAppUtils = appUtils;
        this.mLoggedInUser = loggedInUserId;
        this.mImpressionCallback = impressionCallback;
        isLoaderVisible = (visibility == View.VISIBLE);
        this.mImpressionProperty = impressionSuperProperty;
        this.mConfiguration = configuration;
        init();
    }

    private void init() {
        //Get the view min Visibility of View from remote config
        //  viewVisibilityThreshold = new ConfigData().visibilityPercentage;
        //  if (mConfiguration.isSet() && mConfiguration.get().configData != null) { //Get the view visibility percentage from remote config
        //      viewVisibilityThreshold = mConfiguration.get().configData.visibilityPercentage;
        //  }
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
    public void onScrollChange(RecyclerView recyclerView, int startPos, int endPos) {
        // if (isLoaderVisible) {
        //     return;
        // }

        mContext = recyclerView.getContext();
        if (scrollDirectionChange) {
            scrollDirectionChange = false;
            //Log.i("###IH-Direction", "Changed");
            storeChunks();
        } else {

            startViewId = startPos;
            endViewId = endPos;

            mIsTopScrolled = (startPos > 0) && startPos < lastPosition;
            if (!isSameView(startPos, endPos)) {
                lastPosition = startPos;
            }

            if (startPos > 0 && lastPosition < startPos) {
                //  Log.i("###IH-SCROLLING DOWN", "TRUE");
                directionId = 1;
            } else if (lastPosition > startPos) {
                //  Log.i("###IH-SCROLLING UP", "TRUE");
                directionId = 2;
            }

            if (lastDirectionId != -1 && lastDirectionId != directionId) {
                scrollDirectionChange = true;
            }

            lastDirectionId = directionId;
            lastDirectionDown = mIsTopScrolled;

            analyzeAndAddViewData(recyclerView, startPos, endPos);
        }
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
    private synchronized void analyzeAndAddViewData(RecyclerView recyclerView, int firstVisibleItemPosition, int lastVisibleItemPosition) {
        // Log.i(">>>>###IH-Enter event", "start");
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        // Analyze all the views
        for (int viewPosition = firstVisibleItemPosition; viewPosition <= lastVisibleItemPosition; viewPosition++) {
            ImpressionData impressionData = new ImpressionData();
            View itemView = recyclerView.getLayoutManager().findViewByPosition(viewPosition);

            if (getVisibleHeightPercentage(itemView) >= viewVisibilityThreshold) {  //Only those views which have visibility >= 50%
                FeedDetail feedDetail = mImpressionCallback.getListItemAtPos(firstVisibleItemPosition);
                if (feedDetail == null) return;
                impressionData.setStartTime(System.currentTimeMillis());
                impressionData.setTimeStamp(System.currentTimeMillis());
                impressionData.setViewId(viewPosition);
                impressionData.setPostType(feedDetail.getSubType());
                if (feedDetail.getSubType().equalsIgnoreCase(AppConstants.CAROUSEL_SUB_TYPE)) {
                    //Add position in list
                    //trackingData.setPosition();
                }
                //impressionData.setSourceTab(mImpressionProperty.getCommunityTab()); //with latest 1
                //impressionData.setSource(mImpressionCallback.getScreenName()); //with latest 2
                impressionData.setOrderKey(mImpressionProperty.getOrderKey());
                impressionData.setScreenName(mImpressionCallback.getScreenName()); //remove this once enable 1, 2 above and remove from POJo too
                impressionData.setPosition(viewPosition);
                impressionData.setUserAgent(recyclerView.getContext() != null ? SheroesAppModule.getUserAgent(recyclerView.getContext()) : "");
                impressionData.setClientId(String.valueOf(CLIENT.ANDROID.getValue())); //For mobile android
                impressionData.setEvent(EVENT_TYPE.VIEW_IMPRESSION.getValue());
                impressionData.setAppVersion(mAppUtils.getAppVersionName());
                impressionData.setDeviceId(mAppUtils.getDeviceId()); //Change ip address and all
                impressionData.setIpAddress(recyclerView.getContext() != null ? SheroesAppModule.getIpAddress() : "");
                impressionData.setConfigType(mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configType != null ? mConfiguration.get().configType : "");
                impressionData.setConfigVersion(mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configVersion != null ? mConfiguration.get().configVersion : "");
                impressionData.setGtid(UUID.randomUUID().toString());
                if (prefs != null && prefs.contains(AppConstants.FEED_CONFIG_VERSION)) {
                    impressionData.setFeedConfigVersion(Integer.valueOf(prefs.getString(AppConstants.FEED_CONFIG_VERSION, "0")));
                }

                impressionData.setUserId(mLoggedInUser == -1 ? "" : String.valueOf(mLoggedInUser));
                impressionData.setPostId(feedDetail.getIdOfEntityOrParticipant() != -1 ? String.valueOf(feedDetail.getIdOfEntityOrParticipant()) : "");
                currentViewed.add(viewPosition);

                int indexInFinalList = checkIfItemInFinal(viewPosition);
                if (mIsTopScrolled) {
                    // Log.d("###IH-top scroll", "happen ::" + viewPosition);
                    addNewEntry(viewPosition, impressionData);
                    mIsTopScrolled = false;
                } else if (indexInFinalList == -1) { //new item add it in final list
                    finalViewData.add(impressionData);
                    Log.d("@@@New Enter", "::" + viewPosition);
                    Toast.makeText(recyclerView.getContext(), "Screen Enter" + viewPosition, Toast.LENGTH_SHORT).show();
                }
            } else {
                lessVisible.add(viewPosition); //View which are less visible
            }

            if (previousViewed.size() > 0) { //Compare if any item was present , now not in the list
                for (int i = 0; i < previousViewed.size(); i++) {
                    int id = previousViewed.get(i);
                    if (!currentViewed.contains(id)) {
                        updateEndTimeIfItemNotExist(id);
                    } else {
                        //  Log.i("###IH-Still visibile", "In current" + id);
                    }
                }
                previousViewed.clear();
            }
        }
        //store 10 events in DB
        if (finalViewData.size() > 10) {
            storeChunks();
        }

        previousViewed.addAll(lessVisible);
        lessVisible.clear();
        currentViewed.clear();

       /* synchronized (this) {
            Log.i("######", "#############################");
            for (int i = 0; i < finalViewData.size(); i++) {
                float timeSpent = finalViewData.get(i).getEndTime() - finalViewData.get(i).getStartTime();
                Log.d("###IH-", "Id> " + finalViewData.get(i).getViewId() + " > Duration:::" + (timeSpent / 1000.0f));
            }
            Log.i("######", "#############################");
        }*/
    }

    private void storeChunks() {
        int index = getLastIndexOfUpdatedItem();
        if (index > -1) {
            List<ImpressionData> forDb = finalViewData.subList(0, index + 1);
            Log.i("@@@DB", "###Added to db");
            mImpressionCallback.storeInDatabase(forDb);

            if (finalViewData.size() > index + 1) {
                finalViewData = finalViewData.subList(index + 1, finalViewData.size());
            }
            //  Log.i("###IH-Final list", ":"+finalViewData.size());
            // mImpressionCallback.onNetworkCall();
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
                if (trackingData1.getStartTime() != -1 && trackingData1.getEndTime() == -1 && id == trackingData1.getViewId()) {
                    finalViewData.get(i).setEndTime(System.currentTimeMillis());
                    double timeSpent = finalViewData.get(i).getEndTime() - finalViewData.get(i).getStartTime();
                    finalViewData.get(i).setEngagementTime(timeSpent / 1000.0f);

                    Log.i("@@@Screen Exit ", +id + "End time" + timeSpent / 1000.0f);
                    Toast.makeText(mContext, "Screen Exit" + id + "::Duration" + timeSpent / 1000.0f, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }


    /**
     * Check if the visible view are same after on scroll
     *
     * @param start start view position
     * @param endId end view position
     * @return true if the visible view are same after on scroll
     */
    private boolean isSameView(int start, int endId) {
        return (start == startViewId && endId == endViewId);
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


    private void addNewEntry(int viewPosition, ImpressionData impressionData) {
        int viewId = -1;
        if (finalViewData.size() > 0) {
            viewId = finalViewData.get(finalViewData.size() - 1).getViewId();
        }

        if (viewId != -1 && viewId > viewPosition) {
            //Log.i("###IH-add lesser value", "on top scroll");
            Log.d("New Enter", "::" + viewPosition);
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
