package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import appliedlife.pvtltd.SHEROES.analytics.Impression.Impression;
import appliedlife.pvtltd.SHEROES.datamanager.impression.ImpressionDataDao;
import appliedlife.pvtltd.SHEROES.datamanager.impression.RoomJsonConverter;

@TypeConverters(RoomJsonConverter.class)
@Database(entities = {Impression.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "sheroes";
    private static AppDatabase INSTANCE;

    public abstract ImpressionDataDao impressionDataDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null && context != null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        if (INSTANCE.isOpen()) {
            INSTANCE.close();
        }
        INSTANCE = null;
    }
}
