package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import appliedlife.pvtltd.SHEROES.analytics.Impression.Impression;

@TypeConverters(RoomJsonConverter.class)
@Database(entities = {Impression.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "sheroes";
    static final String TABLE_NAME = "impression";
    private static AppDatabase INSTANCE;

    public abstract ImpressionDataDao impressionDataDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        if (INSTANCE.isOpen()) INSTANCE.close();
        INSTANCE = null;
    }
}
