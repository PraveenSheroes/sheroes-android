package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "impression")
public class Impression {

    @PrimaryKey(autoGenerate = true)
    private int index;

    @ColumnInfo(name = "impressionsData")
    @TypeConverters(RoomJsonConverter.class)
    private UserEvents impressionData;

    public UserEvents getImpressionData() {
        return impressionData;
    }

    public void setImpressionData(UserEvents impressionData) {
        this.impressionData = impressionData;
    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
