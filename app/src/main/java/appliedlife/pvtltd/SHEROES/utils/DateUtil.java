package appliedlife.pvtltd.SHEROES.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String getRoundedDifferenceInHours(long timeOne, long timeTwo) {

        //long Millis24Hrs = 24 * 60 * 60 * 1000;
        long differenceInMinutes = Math.round((timeOne - timeTwo)/(60 * 1000));
        //long  seconds =(long)difference % 60;
        //difference=(long)difference/60;
        int day = (int)(differenceInMinutes/(24*60));
        differenceInMinutes = differenceInMinutes - (day * 24 * 60);
        int hour = (int)(differenceInMinutes/60);
        differenceInMinutes = differenceInMinutes - (hour * 60);
        StringBuilder sb = new StringBuilder();
        if(day>0) {
            sb.append(day);
            if(day==1) {
                sb.append("day ");
            } else {
                sb.append("days ");
            }
        }else
        if(hour>0) {
            sb.append(hour);
            if(hour==1) {
                sb.append("hour ");
            } else {
                sb.append("hours ");
            }
        }else
        if(differenceInMinutes>0) {
            sb.append(differenceInMinutes);
            if(differenceInMinutes==1) {
                sb.append("minute ");
            } else {
                sb.append("minutes ");
            }
        }
        if(sb.length()>0) {
            sb.append("ago");
        }
        return sb.toString();
    }
    public static String getDateWithFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(date);
    }
}
