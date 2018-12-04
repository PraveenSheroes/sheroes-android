package appliedlife.pvtltd.SHEROES.datamanager.impression;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import appliedlife.pvtltd.SHEROES.analytics.Impression.Impression;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ImpressionDataDao {

    @Query("SELECT * FROM impression")
    List<Impression> getAllImpressionsSync();

    @Insert(onConflict = REPLACE)
    List<Long> insert(List<Impression> impression);

    @Delete
    void deleteImpression(List<Impression> impressions);
}
