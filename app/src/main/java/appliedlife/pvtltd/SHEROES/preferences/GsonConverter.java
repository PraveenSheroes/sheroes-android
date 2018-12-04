
package appliedlife.pvtltd.SHEROES.preferences;

import androidx.annotation.NonNull;

import com.f2prateek.rx.preferences2.Preference;
import com.google.gson.Gson;

public class GsonConverter<T> implements Preference.Converter<T> {
    final Gson gson;
    private String json;
    private Class<T> clazz;
    public GsonConverter(Gson gson, Class<T> clazz) {
        this.gson = gson;
        this.clazz = clazz;
    }
    @NonNull @Override public T deserialize(@NonNull String serialized) {
        return gson.fromJson(serialized, clazz);
    }

    @NonNull
    @Override
    public String serialize(@NonNull T t) {
        json = gson.toJson(t);
        return json;
    }
}