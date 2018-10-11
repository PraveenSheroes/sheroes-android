package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.util.List;


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
