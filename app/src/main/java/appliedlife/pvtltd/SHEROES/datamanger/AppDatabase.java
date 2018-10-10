package appliedlife.pvtltd.SHEROES.datamanger;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@TypeConverters(RoomJsonConverter.class)
@Database(entities = {ImpressionCollection.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "sheroes";
    private static AppDatabase INSTANCE;

    public abstract ImpressionDataDao impressionDataDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    // allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    .allowMainThreadQueries() //todo - change this , not to do on main thread
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        if (INSTANCE.isOpen()) INSTANCE.close();
        INSTANCE = null;
    }
}
