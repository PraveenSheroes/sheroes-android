package appliedlife.pvtltd.SHEROES.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Praveen_Singh on 19-02-2017.
 */

public class DateUtil {
    private static final String TAG = LogUtils.makeLogTag(DateUtil.class);
    public static DateUtil getInstance(){
      return   new DateUtil();
    }
    /**
     * Format a timestamp to standard format.
     *
     * @param time is the Timestamp to be formatted
     * @param format format of date to be returned
     * @return the representation of the give date
     */
    public static String getDateFromMillisecondsWithFormat(Long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }
    public long getTimeInMillis(String dateString, String format) {
        long time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            time = sdf.parse(dateString).getTime();
        } catch (Exception e) {
            LogUtils.error(TAG, e);
        }

        return time;
    }
    /**
     * @return absolute rounded off difference in days
     */
    public int getRoundedDifferenceInDays(long timeOne, long timeTwo) {
        long Millis24Hrs = 24 * 60 * 60 * 1000;
        double difference = timeOne - timeTwo;
        if (difference < 0) {
            difference *= -1;
        }
        return (int) ((difference / Millis24Hrs) + 0.5);
    }
    /**
     * @return absolute rounded off difference in days
     */
    public long getRoundedDifferenceInHours(long timeOne, long timeTwo) {
        long Millis24Hrs = 24 * 60 * 60 * 1000;
        double difference = timeOne - timeTwo;
        long  seconds =(long)difference % 60;
        difference=(long)difference/60;
        long minutes =(long)difference % 60;
        return minutes;
    }
}
