package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Converter UserEvents objects to Json and vice versa
 */
public class RoomJsonConverter {

    private static Gson gson = new Gson();
    private static Type type = new TypeToken<List<ImpressionData>>() {}.getType();

    @TypeConverter
    public static List<ImpressionData> stringToNestedData(String json) {
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String nestedDataToString(List<ImpressionData> nestedData) {
        return gson.toJson(nestedData, type);
    }
}
