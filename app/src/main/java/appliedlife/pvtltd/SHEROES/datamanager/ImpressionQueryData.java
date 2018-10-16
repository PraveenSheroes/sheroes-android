package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import appliedlife.pvtltd.SHEROES.analytics.Impression.UserEvents;

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
