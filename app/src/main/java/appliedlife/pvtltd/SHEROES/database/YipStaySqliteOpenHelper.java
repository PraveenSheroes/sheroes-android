package appliedlife.pvtltd.SHEROES.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterData;
import appliedlife.pvtltd.SHEROES.database.dbentities.User;
import appliedlife.pvtltd.SHEROES.database.dbtables.MasterDataTable;

import static appliedlife.pvtltd.SHEROES.database.dbtables.MasterDataTable.getAllTypeMapping;
import static appliedlife.pvtltd.SHEROES.database.dbtables.UserTable.getUserTypeMapping;


public class YipStaySqliteOpenHelper extends SQLiteOpenHelper {

  public static final String DATABASE = "sheroes_database";
  public static final int DATABASE_VERSION = 1;
  @Inject
  public YipStaySqliteOpenHelper() {
    super(SheroesApplication.mContext, DATABASE, null, DATABASE_VERSION);
  }

  public static StorIOSQLite getStorIOSQLite(SQLiteOpenHelper sqliteOpenHelper) {
    return DefaultStorIOSQLite.builder()
        .sqliteOpenHelper(sqliteOpenHelper)
            .addTypeMapping(MasterData.class, getAllTypeMapping())
        .addTypeMapping(User.class, getUserTypeMapping())
        .build();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(MasterDataTable.getCreateQuery());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //Run Migrations here
  }
}
