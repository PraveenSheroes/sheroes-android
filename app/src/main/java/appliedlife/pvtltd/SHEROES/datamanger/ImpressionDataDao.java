package appliedlife.pvtltd.SHEROES.datamanger;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface ImpressionDataDao {

    //@Query("SELECT * FROM impression_data")
    //List<ImpressionData> getAll();

    @Insert
    void insert(ImpressionCollection impressionCollection);

    //@Delete
    //void deleteImpression(List<ImpressionData> impressionData);
}
