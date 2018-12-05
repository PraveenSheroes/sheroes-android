package appliedlife.pvtltd.SHEROES.analytics.Impression;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import appliedlife.pvtltd.SHEROES.datamanager.impression.RoomJsonConverter;

/**
 * Model class for db table impression
 *
 * @author ravi
 */
@Entity(tableName = "impression")
public class Impression {

    @PrimaryKey
    @NonNull
    private String gtid;

    @ColumnInfo(name = "impressionsData")
    @TypeConverters(RoomJsonConverter.class)
    private ImpressionData impressionData = null;

    public ImpressionData getImpressionData() {
        return impressionData;
    }

    public void setImpressionData(ImpressionData impressionData) {
        this.impressionData = impressionData;
    }

    @NonNull
    public String getGtid() {
        return gtid;
    }

    public void setGtid(@NonNull String gtid) {
        this.gtid = gtid;
    }
}
