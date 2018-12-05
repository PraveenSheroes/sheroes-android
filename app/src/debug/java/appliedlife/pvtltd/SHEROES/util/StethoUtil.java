package appliedlife.pvtltd.SHEROES.util;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class StethoUtil {
    public static void initStetho(Application application) {
        Stetho.initializeWithDefaults(application);
    }
}
