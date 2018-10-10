package appliedlife.pvtltd.SHEROES.datamanger;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

@Entity(tableName = "impression_collection")
public class ImpressionCollection {

    @PrimaryKey(autoGenerate = true)
    private int index;

    @ColumnInfo(name = "option_values")
    @TypeConverters(RoomJsonConverter.class)
    private UserEvents userEvents;

    public UserEvents getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(UserEvents userEvents) {
        this.userEvents = userEvents;
    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
