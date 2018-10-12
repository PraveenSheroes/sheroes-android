package appliedlife.pvtltd.SHEROES.datamanager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ImpressionDataDao {

    @Query("SELECT * FROM impression")
    List<Impression> getAll();

    @Insert(onConflict = REPLACE)
    void insert(Impression impression);

    @Delete
    void deleteImpression(List<Impression> impressions);
}
