 package appliedlife.pvtltd.SHEROES.utils;

 import android.text.format.DateUtils;

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
     public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z";
     private static final String CONTEST_TIME = "d MMM, h aaa";
     public static final Locale LOCALE = Locale.US;
     public static final String PRETTY_DATE_WITHOUT_TIME = "d MMM yyyy";
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

     public static String contestDate(Date date) {
         if (!validateDate(date)) {
             return "";
         }
         SimpleDateFormat dateFormat = new SimpleDateFormat(CONTEST_TIME, LOCALE);
         return dateFormat.format(date);
     }

     private static boolean validateDate(Date date) {
         return date != null;
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

     public static CharSequence getRelativeTimeSpanString(Date date) {
         return DateUtils.getRelativeTimeSpanString(date.getTime(), new Date().getTime(), DateUtils.SECOND_IN_MILLIS);
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

     public static String toPrettyDateWithoutTime(Date date) {
         if (!validateDate(date)) {
             return "";
         }
         SimpleDateFormat dateFormat = new SimpleDateFormat(PRETTY_DATE_WITHOUT_TIME, LOCALE);
         return dateFormat.format(date);
     }

     public static Date parseDateFormat(String dateStr, String dateformat) {

         Date date = null;
         try {
             date = parseDateFormatHelper(dateStr, dateformat);
         } catch (ParseException e) {
//            Crashlytics.logException(e);
         }

         return date;
     }

     public static Date parseDateFormatHelper(String dateStr, String dateformat) throws ParseException {
         if (dateStr == null)
             return null;

         Date date = null;
         SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat, LOCALE);

         return dateFormat.parse(dateStr);
     }
 }
