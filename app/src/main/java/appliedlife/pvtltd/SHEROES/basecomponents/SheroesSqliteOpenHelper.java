package appliedlife.pvtltd.SHEROES.basecomponents;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.database.dbtables.RecentSearchDataTable;

import static appliedlife.pvtltd.SHEROES.database.dbtables.RecentSearchDataTable.getAllTypeMapping;


public class SheroesSqliteOpenHelper extends SQLiteOpenHelper {

  public static final String DATABASE = "sheroes_database";
  public static final int DATABASE_VERSION = 1;
  @Inject
  public SheroesSqliteOpenHelper() {
    super(SheroesApplication.mContext, DATABASE, null, DATABASE_VERSION);
  }

  public static StorIOSQLite getStorIOSQLite(SQLiteOpenHelper sqliteOpenHelper) {
    return DefaultStorIOSQLite.builder()
        .sqliteOpenHelper(sqliteOpenHelper)
            .addTypeMapping(RecentSearchData.class, getAllTypeMapping())
        .build();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(RecentSearchDataTable.getCreateQuery());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //Run Migrations here
  }
}
