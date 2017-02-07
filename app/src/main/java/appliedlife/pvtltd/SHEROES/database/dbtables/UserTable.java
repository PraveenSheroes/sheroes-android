package appliedlife.pvtltd.SHEROES.database.dbtables;


import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.queries.Query;

import appliedlife.pvtltd.SHEROES.database.dbentities.User;
import appliedlife.pvtltd.SHEROES.database.dbentities.UserStorIOSQLiteDeleteResolver;
import appliedlife.pvtltd.SHEROES.database.dbentities.UserStorIOSQLiteGetResolver;
import appliedlife.pvtltd.SHEROES.database.dbentities.UserStorIOSQLitePutResolver;


public final class UserTable {
  public static final String TABLE = "property";
  public static final String COL_INTERNAL_ID = "internal_id";
  public static final String COL_ID = "id";
  public static final String COL_UUID = "uuid";
  public static final String COL_NAME = "name";
  public static final String COL_DESCRIPTION = "description";
  public static final String COL_EMAIL = "email";
  public static final String COL_ADDRESS = "address";
  public static final String COL_CONTACTS = "landline";


  private UserTable() {
  }

  @SuppressWarnings("PMD")
  public static String getCreateQuery() {
    //@formatter:off
        return "CREATE TABLE " + TABLE + "("
                + COL_INTERNAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ID + " INTEGER , "
                + COL_UUID + " TEXT , "
                + COL_NAME + " TEXT, "
                + COL_DESCRIPTION + " TEXT, "
                + COL_EMAIL + " TEXT, "
                + COL_ADDRESS + " TEXT, "
                + COL_CONTACTS + " TEXT "+
            ");";
        //@formatter:on
  }

  public static Query getAllQuery() {
    return Query.builder().table(TABLE).build();
  }

  public static Query getQueryWithId(long id) {
    return Query.builder()
        .table(TABLE)
        .where(COL_INTERNAL_ID + "= ?")
        .whereArgs(String.valueOf(id))
        .build();
  }

  public static Query getQueryWithGroupId(long id) {
    return Query.builder()
        .table(TABLE)
        .where(COL_ID + " =? ")
        .whereArgs(String.valueOf(id))
        .build();
  }

  public static SQLiteTypeMapping<User> getUserTypeMapping() {
    return SQLiteTypeMapping.<User>builder().putResolver(new UserStorIOSQLitePutResolver())
        .getResolver(new UserStorIOSQLiteGetResolver())
        .deleteResolver(new UserStorIOSQLiteDeleteResolver())
        .build();
  }
}
