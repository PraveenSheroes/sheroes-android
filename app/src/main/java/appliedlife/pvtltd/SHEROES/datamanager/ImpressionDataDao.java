package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ImpressionDataDao {

    @Query("SELECT * FROM impression")
    List<Impression> getAll();

    @Insert
    void insert(Impression impression);

    @Delete
    void deleteImpression(List<Impression> impressions);
}
