package appliedlife.pvtltd.SHEROES.analytics.Impression;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import appliedlife.pvtltd.SHEROES.datamanager.RoomJsonConverter;

/**
 * Model class for db table impression
 * @author ravi
 */
@Entity(tableName = "impression")
public class Impression {

    @PrimaryKey(autoGenerate = true)
    private int index;

    @ColumnInfo(name = "impressionsData")
    @TypeConverters(RoomJsonConverter.class)
    @SerializedName("userEvents")
    private List<ImpressionData> impressionDataList = null;

    public List<ImpressionData> getImpressionDataList() {
        return impressionDataList;
    }

    public void setImpressionDataList(List<ImpressionData> impressionDataList) {
        this.impressionDataList = impressionDataList;
    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
