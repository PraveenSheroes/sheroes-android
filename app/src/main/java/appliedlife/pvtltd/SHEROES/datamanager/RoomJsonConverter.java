package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import appliedlife.pvtltd.SHEROES.analytics.Impression.ImpressionData;

/**
 * Converter UserEvents objects to Json and vice versa
 */
public class RoomJsonConverter {

    private static Gson gson = new Gson();
    private static Type type = new TypeToken<ImpressionData>() {}.getType();

    @TypeConverter
    public static ImpressionData stringToNestedData(String json) {
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String nestedDataToString(ImpressionData impressionData) {
        return gson.toJson(impressionData, type);
    }
}
