package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import appliedlife.pvtltd.SHEROES.datamanager.RoomJsonConverter;

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
