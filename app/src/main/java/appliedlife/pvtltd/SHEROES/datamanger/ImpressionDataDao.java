package appliedlife.pvtltd.SHEROES.datamanger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ImpressionDataDao {

    @Query("SELECT * FROM impression_data")
    List<ImpressionData> getAll();

    @Insert
    void insert(List<ImpressionData> impressionData);

}
