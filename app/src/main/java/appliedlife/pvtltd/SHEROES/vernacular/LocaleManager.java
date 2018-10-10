package appliedlife.pvtltd.SHEROES.vernacular;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

import appliedlife.pvtltd.SHEROES.utils.CommonUtil;

import static appliedlife.pvtltd.SHEROES.utils.AppConstants.LANGUAGE_KEY;

public class LocaleManager {
    public static Context setLocale(Context c) {
        return updateResources(c, getLanguage(c));
    }

    public static void setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    public static String getLanguage(Context c) {
        return CommonUtil.getPrefStringValue(LANGUAGE_KEY);
    }

    private static void persistLanguage(Context c, String language) {
        CommonUtil.setPrefStringValue(LANGUAGE_KEY, language);
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
       /* Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        } else {
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        }*/
        res.updateConfiguration(config, res.getDisplayMetrics());
        return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ? config.getLocales().get(0) : config.locale;
    }
}
