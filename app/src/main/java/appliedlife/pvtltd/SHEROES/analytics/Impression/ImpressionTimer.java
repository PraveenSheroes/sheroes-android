package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.os.CountDownTimer;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;

/**
 * Timer for impressions
 *
 * @author ravi
 */
public class ImpressionTimer extends CountDownTimer {

    //region private variables
    private static final String TAG = ImpressionTimer.class.getName();

    private long mLastScrollingEndTime;
    private int mImpressionMaxTimeout;
    private boolean mIsTimerRunning;

    private static ImpressionTimer mImpressionTimer;
    private ITimerCallback mTimerCallback;
    //endregion

    //region constructor
    private ImpressionTimer(long millisInFuture, long countDownInterval, int maxTimeout, ITimerCallback timerCallback) {
        super(millisInFuture, countDownInterval);
        this.mImpressionMaxTimeout = maxTimeout;
        mTimerCallback = timerCallback;
    }
    //endregion

    //region public method
    @Override
    public void onTick(long l) {
    }

    @Override
    public void onFinish() {
        if (System.currentTimeMillis() - mLastScrollingEndTime > mImpressionMaxTimeout) {
            LogUtils.info(TAG, "MAX time expired");
            mIsTimerRunning = false;
            mTimerCallback.stopTimer();
            cancel();
        } else {
            LogUtils.info(TAG, "Time Expired");
            mTimerCallback.sendImpressions();
            mIsTimerRunning = true;
            start();
        }
    }

    public boolean isTimerRunning() {
        return mIsTimerRunning;
    }

    public void setTimerRunning(boolean timerRunning) {
        mIsTimerRunning = timerRunning;
    }

    public void setLastScrollingEndTime(long lastScrollingEndTime) {
        this.mLastScrollingEndTime = lastScrollingEndTime;
    }
    //endregion

    //region private method
    public static ImpressionTimer getInstance(long millisInFuture, long countDownInterval, int impressionMaxTimeout, ITimerCallback timerCallback) {
        if (mImpressionTimer == null) {
            mImpressionTimer = new ImpressionTimer(millisInFuture, countDownInterval, impressionMaxTimeout, timerCallback);
        }
        return mImpressionTimer;
    }
    //endregion

    interface ITimerCallback {
        //stop the timer
        void stopTimer();

        //send the impressions
        void sendImpressions();
    }
}
