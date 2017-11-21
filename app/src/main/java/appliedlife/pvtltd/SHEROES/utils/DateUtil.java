 package appliedlife.pvtltd.SHEROES.utils;

 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Locale;

 import appliedlife.pvtltd.SHEROES.R;
 import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;

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
                 sb.append(AppConstants.DAY);
             } else {
                 sb.append(AppConstants.DAY+ AppConstants.S);
             }
         }else
         if(hour>0) {
             sb.append(hour);
             if(hour==1) {
                 sb.append(AppConstants.HOUR);
             } else {
                 sb.append(AppConstants.HOUR+ AppConstants.S);
             }
         }else
         if(differenceInMinutes>0) {
             sb.append(differenceInMinutes);
             if(differenceInMinutes==1) {
                 sb.append(AppConstants.MINUT);
             } else {
                 sb.append(AppConstants.MINUT+ AppConstants.S);
             }
         }else
         {
             sb.append(SheroesApplication.mContext.getString(R.string.ID_JUST_NOW));
         }
         return sb.toString();
     }
     public static String getDateWithFormat(String dateString, String format) {
         SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
         try {
             Date date = sdf.parse(dateString);
             String stringDate = sdf.format(date);
             return stringDate;
         } catch (ParseException e) {
             e.printStackTrace();
         }
         return null;
     }
 }
