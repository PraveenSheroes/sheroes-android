package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

@Entity(tableName = "impression")
public class ImpressionQueryData {

    @ColumnInfo(name = "impressionsData")
    private UserEvents userEvents;

    public UserEvents getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(UserEvents userEvents) {
        this.userEvents = userEvents;
    }

}
