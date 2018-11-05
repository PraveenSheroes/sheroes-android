package appliedlife.pvtltd.SHEROES.datamanager.impression;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import appliedlife.pvtltd.SHEROES.analytics.Impression.Impression;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ImpressionDataDao {

    @Query("SELECT * FROM impression")
    List<Impression> getAllImpressionsSync();

    @Insert(onConflict = REPLACE)
    List<Long> insert(List<Impression> impression);

    @Delete
    void deleteImpression(List<Impression> impressions);
}
