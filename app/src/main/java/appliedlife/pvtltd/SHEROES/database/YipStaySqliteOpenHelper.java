package appliedlife.pvtltd.SHEROES.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.database.dbentities.Property;
import appliedlife.pvtltd.SHEROES.database.dbtables.PropertyTable;

import static appliedlife.pvtltd.SHEROES.database.dbtables.PropertyTable.getHotelTypeMapping;


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
        .addTypeMapping(Property.class, getHotelTypeMapping())
        .build();
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(PropertyTable.getCreateQuery());
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //Run Migrations here
  }
}
