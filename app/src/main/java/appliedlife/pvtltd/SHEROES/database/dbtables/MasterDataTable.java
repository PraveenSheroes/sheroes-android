package appliedlife.pvtltd.SHEROES.database.dbtables;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import appliedlife.pvtltd.SHEROES.database.dbentities.MasterData;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterDataStorIOSQLiteDeleteResolver;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterDataStorIOSQLiteGetResolver;
import appliedlife.pvtltd.SHEROES.database.dbentities.MasterDataStorIOSQLitePutResolver;


public final class MasterDataTable {
  public static final String TABLE = "master_data";
  public static final String COL_ID = "_id";
  public static final String COL_MASTER_TYPE_ID = "master_type_id";
  public static final String COL_MASTER_DATA_TYPE = "master_data_type";
  public static final String COL_NAME = "name";
  public static final String COL_DESCRIPTION = "description";
  public static final String COL_DISPLAY_NAME = "display_name";

  private MasterDataTable() {
  }

  /*public static String getCreateQuery() {
    return "CREATE TABLE " + TABLE + " ( " +
        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_MASTER_TYPE_ID + " INTEGER , " +
        COL_MASTER_DATA_TYPE + " TEXT NOT NULL, " +
        COL_NAME + " TEXT NOT NULL, " +
        COL_DESCRIPTION + " TEXT, " +
        COL_DISPLAY_NAME + " TEXT NOT NULL, " +
        "UNIQUE(" + COL_MASTER_TYPE_ID + ") ON CONFLICT REPLACE"
        + ");";
  }*/
  public static String getCreateQuery() {
      return "CREATE TABLE " + TABLE + " ( " +
              COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
              COL_MASTER_TYPE_ID + " INTEGER , " +
              COL_MASTER_DATA_TYPE + " TEXT NOT NULL, " +
              COL_NAME + " TEXT NOT NULL, " +
              COL_DESCRIPTION + " TEXT, " +
              COL_DISPLAY_NAME + " TEXT NOT NULL " + ");";
  }
  public static Query getAllQuery() {
    return Query.builder().table(TABLE).build();
  }

 /* public static Query getQueryFor(MasterDataTypes masterDataType) {

    return Query.builder()
        .table(TABLE)
        .where(COL_MASTER_DATA_TYPE + "=?").whereArgs(masterDataType.id).build();
  }*/
  public static Query getQueryFor(int id) {

    return Query.builder()
            .table(TABLE)
            .where(COL_MASTER_DATA_TYPE + "=?").whereArgs(id).build();
  }
/*  public static RawQuery getQueryForList(List<Integer> masterDataTypes) {
    String query="Select * from "+TABLE+" WHERE "+COL_MASTER_TYPE_ID+" in "
        +masterDataTypes.toString().replace('[','(').replace(']',')');
    return RawQuery.builder().query(query).build();
  }*/
    public static RawQuery getQueryForList() {
        String query="Select * from "+TABLE;
        return RawQuery.builder().query(query).build();
    }
  public static SQLiteTypeMapping<MasterData> getAllTypeMapping() {
    return SQLiteTypeMapping.<MasterData>builder().putResolver(
        new MasterDataStorIOSQLitePutResolver())
        .getResolver(new MasterDataStorIOSQLiteGetResolver())
        .deleteResolver(new MasterDataStorIOSQLiteDeleteResolver())
        .build();
  }
}
