package appliedlife.pvtltd.SHEROES.preferences;

import android.content.SharedPreferences;

import com.f2prateek.rx.preferences2.Preference;
import com.google.gson.Gson;
/**
 * Created by Praveen Singh on 29/12/2016.
 *
 * @author Praveen Singh
 * @version 5.0
 * @since 29/12/2016.
 * Title: RxSharedpreference use this adapter for save gson response.
 */
public class GsonPreferenceAdapter<T> implements RealPreference.Adapter<T> {
    final Gson gson;
    private Class<T> clazz;

    public GsonPreferenceAdapter(Gson gson, Class<T> clazz) {
        this.gson = gson;
        this.clazz = clazz;
    }

    // Constructor and exception handling omitted for brevity.

    @Override
    public T get(String key, SharedPreferences preferences) {
        return gson.fromJson(preferences.getString(key, null), clazz);
    }

    @Override
    public void set(String key, T value, SharedPreferences.Editor editor) {
        editor.putString(key, gson.toJson(value));
    }
}