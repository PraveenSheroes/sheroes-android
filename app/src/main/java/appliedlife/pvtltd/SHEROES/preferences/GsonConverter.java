
package appliedlife.pvtltd.SHEROES.preferences;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences2.Preference;
import com.google.gson.Gson;

import java.io.IOException;

public class GsonConverter<T> implements Preference.Converter<T> {
    final Gson gson;
    private String json;
    private Class<T> clazz;
    public GsonConverter(Gson gson, Class<T> clazz) {
        this.gson = gson;
        this.clazz = clazz;
    }
    @NonNull
    @Override
    public T deserialize(@NonNull String serialized) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return gson.fromJson(serialized, clazz);
    }

    @NonNull
    @Override
    public String serialize(@NonNull T t) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        json = gson.toJson(t);
        return json;
    }

    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            if (ex.getClass().equals(OutOfMemoryError.class)) {
                try {
                    android.os.Debug.dumpHprofData(Environment.getExternalStorageState()+"/dump.hprof");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ex.printStackTrace();
        }
    }
}