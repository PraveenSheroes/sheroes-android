package appliedlife.pvtltd.SHEROES.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import appliedlife.pvtltd.SHEROES.basecomponents.ImpressionCallback;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;

public class ImpressionHelper {

    //region private member variables
    private int minimumVisibleHeightThreshold;
    private AppUtils mAppUtils;
    private long mLoggedInUser;
    private int startViewId = -1;
    private int endViewId = -1;
    private int lastPosition = -1;
    private boolean lastDirectionDown = true;
    private boolean scrollDirectionChange = false;
    private boolean mIsTopScrolled;
    private Context mContext;
    private  boolean isLoaderVisible = false;
    private ImpressionCallback mImpressionCallback;

    // ArrayList of view ids that are being considered for tracking.
    private ArrayList<Integer> currentViewed = new ArrayList<>();
    private ArrayList<Integer> previousViewed = new ArrayList<>();
    private List<ImpressionData> finalViewData = new ArrayList<>();
    //endregion

    public ImpressionHelper(Context context, int minVisibilityPercentage, int visibility, long loggedInUserId, AppUtils appUtils, ImpressionCallback impressionCallback) {
        this.minimumVisibleHeightThreshold = minVisibilityPercentage;
        this.mAppUtils = appUtils;
        this.mLoggedInUser = loggedInUserId;
        this.mContext = context;
        this.mImpressionCallback = impressionCallback;
        isLoaderVisible = (visibility == View.VISIBLE);
    }

    public void onResume() {
        Log.i("Resume", "On Resume");
    }


    public void onPause() {
        //Fragment/ Activity get paused
        Log.i("Pause", "On Pause");
        updateEndTimeOfItems();
        mImpressionCallback.storeInDatabase(finalViewData);
    }

    /**
     * Update the end time for the item on paused
     */
    private void updateEndTimeOfItems() {
        if(finalViewData.size()>0) {
            int size = finalViewData.size();
            for (int i = size-1; i >= 0; i--) {
                ImpressionData trackingData1 = finalViewData.get(i);
                if (trackingData1.getEndTime() == -1) { //
                    Log.i("Updating", "End time");
                    finalViewData.get(i).setEndTime(System.currentTimeMillis());
                    float timeSpent = finalViewData.get(i).getEndTime() - finalViewData.get(i).getStartTime();
                    finalViewData.get(i).setEngagementTime((int) (timeSpent / 1000.0f));
                }
            }
        }

    }

    /**
     * Get callback when recycler view is being scrolled and scroll state changing
     * @param mFeedRecyclerView recyclerView
     * @param startPos first visible item on screen
     * @param endPos last visible item on screen
     */
    public void onScrollChange(RecyclerView mFeedRecyclerView, int startPos, int endPos) {
        if (isLoaderVisible) {
            return;
        }

        mIsTopScrolled = (startPos> 0) && startPos < lastPosition;
        lastPosition = startPos;
        scrollDirectionChange = lastDirectionDown != mIsTopScrolled;
        lastDirectionDown = mIsTopScrolled;

        if (!isSameView(startPos, endPos)) {
            startViewId = startPos;
            endViewId = endPos;

            analyzeAndAddViewData(mFeedRecyclerView, startPos, endPos);
        } else if(scrollDirectionChange) {
            Log.i("Direction", "Changed");
        }
    }

    //Data request for Impression
    private synchronized void analyzeAndAddViewData(RecyclerView recyclerView, int firstVisibleItemPosition, int lastVisibleItemPosition) {
        Log.i(">>>>Enter event", "start");
        SharedPreferences prefs = SheroesApplication.getAppSharedPrefs();
        // Analyze all the views
        for (int viewPosition = firstVisibleItemPosition; viewPosition <= lastVisibleItemPosition; viewPosition++) {
            ImpressionData trackingData = new ImpressionData();
            View itemView = recyclerView.getLayoutManager().findViewByPosition(viewPosition);

            if (getVisibleHeightPercentage(itemView) >= minimumVisibleHeightThreshold) {  //>= 50%
                FeedDetail feedDetail = mImpressionCallback.getListItemAtPos(firstVisibleItemPosition);
                if(feedDetail ==null) return;
                trackingData.setStartTime(System.currentTimeMillis());
                trackingData.setTimeStamp(System.currentTimeMillis());
                trackingData.setViewId(viewPosition);
                trackingData.setPostType(feedDetail.getSubType());
                trackingData.setScreenName(mImpressionCallback.getScreenName());
                trackingData.setPosition(viewPosition);
                trackingData.setClientId(String.valueOf(CLIENT.ANDROID.getValue())); //For mobile android
                trackingData.setEvent(EVENT_TYPE.VIEW_IMPRESSION.getValue());
                trackingData.setAppVersion(mAppUtils.getAppVersionName());
                trackingData.setIpAddress(mAppUtils.getDeviceId()); //Change ip address and all
                trackingData.setGtId(UUID.randomUUID().toString());
                if (prefs != null && prefs.contains(AppConstants.FEED_CONFIG_VERSION)) {
                    trackingData.setFeedConfigVersion(Integer.valueOf(prefs.getString(AppConstants.FEED_CONFIG_VERSION, "0")));
                }

                trackingData.setUserId("" + mLoggedInUser);
                trackingData.setPostId("" + feedDetail.getIdOfEntityOrParticipant());
                currentViewed.add(viewPosition);

                int indexInFinalList = checkIfItemInFinal(viewPosition);
                if (indexInFinalList == -1) { //new item add it in final list
                    finalViewData.add(trackingData);
                    Log.d("@@@@New Item", "::" + viewPosition);
                } else {
                    if (mIsTopScrolled) {
                        Log.d("@@@@top scroll", "happen ::" + viewPosition);
                        addNewEntry(viewPosition, trackingData);
                    }
                    Log.d("@@@@New Item exist", "::" + viewPosition);
                }
            }

            if (previousViewed.size() > 0) { //Compare if any item was present , now not in the list
                for (int i = 0; i < previousViewed.size(); i++) {
                    int id = previousViewed.get(i);
                    if (!checkIfItemInCurrent(id)) {
                        updateEndTimeIfItemNotExist(id);
                    } else {
                        Log.i("Still visibile", "In current" + id);
                    }
                }
                previousViewed.clear();
            }
        }

        //store 10 events in DB
        if (finalViewData.size() > 10) {
            int index = getLastIndexOfUpdatedItem();
            if (index > -1) {
                List<ImpressionData> forDb = finalViewData.subList(0, index+1);
                Log.i("###", "###Added to db");
                mImpressionCallback.storeInDatabase(forDb);

                if (finalViewData.size() > index+1) {
                    finalViewData = finalViewData.subList(index+1 , finalViewData.size());
                }
                Log.i("Final list", ":"+finalViewData.size());
                mImpressionCallback.onNetworkCall();
            }
        }

        previousViewed.addAll(currentViewed);
        currentViewed.clear();

        synchronized (this) {
            Log.i("######", "#############################");
            for (int i = 0; i < finalViewData.size(); i++) {
                float timeSpent = finalViewData.get(i).getEndTime() - finalViewData.get(i).getStartTime();
                Log.d("######", "Id> " + finalViewData.get(i).getViewId() + " > Duration:::" + (timeSpent / 1000.0f));
            }
            Log.i("######", "#############################");
            Log.i(">>>>>>>>>>>>Enter event", "exit");
        }
    }

    /**
     * Get the last item on which end time was updated
     * @return index of item which have end time updated
     */
    private int getLastIndexOfUpdatedItem() {
        int index  = -1;
        if(finalViewData.size()>0) {
            for(int i = finalViewData.size()-1; i>=0; i--) {
                ImpressionData impressionData = finalViewData.get(i);
                if(impressionData.getEndTime()!=-1) {
                    index=  i;
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
     * Check if the view is visible in Current visible items
     *
     * @param itemPosition position of view
     * @return true if item is present in current visible item list
     */
    private boolean checkIfItemInCurrent(int itemPosition) {
        for (int i = 0; i < currentViewed.size(); i++) {
            int viewPosition = currentViewed.get(i);
            if (viewPosition == itemPosition) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update the end time of the view which are not visible now or visibility percentage in below the minVisiblePercentage
     *
     * @param id id of view
     */
    private void updateEndTimeIfItemNotExist(int id) {
        if(finalViewData.size()>0) {
            int size = finalViewData.size();
            for (int i = size-1; i >= 0; i--) {
                ImpressionData trackingData1 = finalViewData.get(i);
                if (trackingData1.getEndTime() == -1 && id == trackingData1.getViewId()) { //
                    Log.i("Updating", "End time" + id);
                    finalViewData.get(i).setEndTime(System.currentTimeMillis());
                    double timeSpent = finalViewData.get(i).getEndTime() - finalViewData.get(i).getStartTime();
                    finalViewData.get(i).setEngagementTime((timeSpent / 1000.0f));
                    break;
                }
            }
        }
    }

    /**
     * Check if the start and end view are same
     *
     * @param start start item position
     * @param end   end item position
     * @return true if start and end view are same
     */
    private boolean isStartEndSame(int start, int end) {
        return start == 0 && end == 0;
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
                if (trackingData1.getPosition() == id ) { //&& trackingData1.getEndTime() != -1 //&& && id!=lastItemId && lastItemId!= -1 && trackingData.getmEndTime() == -1  &&
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
            Log.i("Only add lesser value", "on top scroll");
            finalViewData.add(impressionData);
          //  currentViewed.add(viewId);
        } else {
            Log.i("these are greater", "on top scroll" + viewPosition);
        }
    }

    private void addNewEntry1(int viewPosition, ImpressionData impressionData) {

        int viewId = -1;
        if (finalViewData.size() > 0) {
            viewId = finalViewData.get(finalViewData.size() - 1).getViewId();
        }

        if (viewId != -1 && viewId < viewPosition) {
            Log.i("Only add lesser value", "on bottom scroll");
        } else {
            finalViewData.add(impressionData);
            Log.i("these are greater", "on bottom scroll" + viewPosition);
        }
    }


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

}
