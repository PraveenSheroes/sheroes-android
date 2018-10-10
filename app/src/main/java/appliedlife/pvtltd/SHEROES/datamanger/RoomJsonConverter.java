package appliedlife.pvtltd.SHEROES.datamanger;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RoomJsonConverter {

    private static Gson gson = new Gson();
    private static Type type = new TypeToken<UserEvents>() {}.getType();

    @TypeConverter
    public static UserEvents stringToNestedData(String json) {
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String nestedDataToString(UserEvents nestedData) {
        return gson.toJson(nestedData, type);
    }
}
