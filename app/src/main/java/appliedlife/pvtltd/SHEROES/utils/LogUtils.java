package appliedlife.pvtltd.SHEROES.utils;

import android.util.Log;

import appliedlife.pvtltd.SHEROES.BuildConfig;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;

public class LogUtils {
    public static final boolean ENABLE_LOG = BuildConfig.DEBUG?true:false;
    private static final String LOG_PREFIX = "sheroes_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;
    private static final String ENTER_TAG;
    private static final String EXIT_TAG;

    static {
        ENTER_TAG = " <<<<<<<<";
        EXIT_TAG = " >>>>>>>>";
    }

    enum LogLevel {
        LOG_LEVEL_ERROR, LOG_LEVEL_WARNING, LOG_LEVEL_INFO, LOG_LEVEL_DEBUG, LOG_LEVEL_VERBOSE
    }

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static final String getMethodName() {
        return new Exception().getStackTrace()[1].getMethodName();
    }

    public static final String getClassName() {
        return new Exception().getStackTrace()[1].getClassName();
    }

    private static void outLog(LogLevel loglevel, String tag, String message) {
        try {
            if (!StringUtil.isNotNullOrEmptyString(tag) && !StringUtil.isNotNullOrEmptyString(message)) {
                return;
            }

            switch (loglevel) {
                case LOG_LEVEL_ERROR:
                    Log.e(tag, message);
                    break;
                case LOG_LEVEL_WARNING:
                    Log.w(tag, message);
                    break;
                case LOG_LEVEL_INFO:
                    Log.i(tag, message);
                    break;
                case LOG_LEVEL_DEBUG:
                    Log.d(tag, message);
                    break;
                case LOG_LEVEL_VERBOSE:
                    Log.v(tag, message);
                    break;
                default:
                    break;
            }
        }catch(Exception e){
        }
    }

    public static final void trace(String tag, String message) {
        try {
            if (ENABLE_LOG) {
                outLog(LogLevel.LOG_LEVEL_DEBUG, tag, message);
            }
        }catch(Exception e){
        }
    }

    /**
     * for display debug. working same as Log.d(tag_item_ui_for_onboarding,log) with enter tag_item_ui_for_onboarding
     *
     * use at starting of method to mark initial point.
     */
    public static final void enter(String tag, String message) {
        try {
            if (ENABLE_LOG) {
                outLog(LogLevel.LOG_LEVEL_DEBUG, tag, message + ENTER_TAG);
            }
        }catch(Exception e){
        }
    }

    /**
     * for display debug. working same as Log.d(tag_item_ui_for_onboarding,log) with exit tag_item_ui_for_onboarding
     *
     * use at ending of method to mark exit point.
     *
     */
    public static final void exit(String tag, String message) {
        try {
            if (ENABLE_LOG) {
                outLog(LogLevel.LOG_LEVEL_DEBUG, tag, message + EXIT_TAG);
            }
        }catch(Exception e){
        }
    }

    public static final void checkIf(String tag, String message) {
        try {
            if (ENABLE_LOG || Log.isLoggable(tag, Log.DEBUG)) {
                outLog(LogLevel.LOG_LEVEL_DEBUG, tag, message);
            }
        }catch(Exception e){
        }
    }

    public static final void checkIf(final String tag, String message, Throwable cause) {
        try {
            if (ENABLE_LOG || Log.isLoggable(tag, Log.DEBUG)) {
                Log.d(tag, message, cause);
            }
        }catch(Exception e){
        }
        if (!BuildConfig.DEBUG) {
            try {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static final void checkAll(final String tag, String message) {
        try {
            if (ENABLE_LOG && Log.isLoggable(tag, Log.VERBOSE)) {
                outLog(LogLevel.LOG_LEVEL_VERBOSE, tag, message);
            }
        }catch(Exception e){
        }
    }

    public static final void checkAll(final String tag, String message, Throwable cause) {
        try {
            if (ENABLE_LOG && Log.isLoggable(tag, Log.VERBOSE)) {
                Log.v(tag, message, cause);
            }
        }catch(Exception e){
        }
        if (!BuildConfig.DEBUG) {
            try {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * for display information . working same as Log.i(tag_item_ui_for_onboarding,message)
     *
     */
    public static final void info(String tag, String message) {
        try {
            if (ENABLE_LOG) {
                outLog(LogLevel.LOG_LEVEL_INFO, tag, message);
            }
        }catch(Exception e){
        }
    }

    /**
     * for display warning. working same as Log.d(tag_item_ui_for_onboarding,message)
     *
     */
    public static final void warning(String tag, String message) {
        try {
            if (ENABLE_LOG) {
                outLog(LogLevel.LOG_LEVEL_WARNING, tag, message);
            }
        }catch(Exception e){
        }
    }

    /**
     * for display error. working same as Log.e(tag_item_ui_for_onboarding,message)
     *
     */
    public static final void error(String tag, String message) {
        try {
            if (ENABLE_LOG) {
                outLog(LogLevel.LOG_LEVEL_ERROR, tag, message);
            }
        }catch(Exception e){
        }
    }

    /**
     *  For display exception on console only on DEBUG build.
     * @param tag
     * @param e
     */
    public static final void debugError(String tag, Exception e) {
        if(ENABLE_LOG) {
            e.printStackTrace();
        }
    }
    /**
     * for display error. working same as Log.e(tag_item_ui_for_onboarding,message). Added print stack track and Crashlytics
     * caught exception logging
     */
    public static final void error(String tag, String message, Throwable cause) {
        try {
            error(tag, message);
            if (ENABLE_LOG) {
                cause.printStackTrace();
            }
        }catch(Exception e){
        }
        if (!BuildConfig.DEBUG) {
            try {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * for display error. Added print stack track and Crashlytics caught exception
     * logging
     */
    public static final void error(String tag, Throwable cause) {
        try {
            error(tag, cause.toString());
            if (ENABLE_LOG) {
                cause.printStackTrace();
            }
        }catch(Exception e){
        }
        if (!BuildConfig.DEBUG) {
            try {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}