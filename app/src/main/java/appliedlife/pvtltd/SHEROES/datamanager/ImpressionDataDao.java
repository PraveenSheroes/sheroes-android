package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface ImpressionDataDao {

    @Query("SELECT * FROM impression")
    Impression getAll();

    @Insert
    void insert(Impression impressionData);

  //  @Delete
  //  void deleteImpression(List<ImpressionData> impressionData);
}
