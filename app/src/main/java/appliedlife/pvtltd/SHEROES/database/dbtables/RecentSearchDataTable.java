package appliedlife.pvtltd.SHEROES.database.dbtables;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchData;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchDataStorIOSQLiteDeleteResolver;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchDataStorIOSQLiteGetResolver;
import appliedlife.pvtltd.SHEROES.database.dbentities.RecentSearchDataStorIOSQLitePutResolver;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;


public final class RecentSearchDataTable {
  public static final String TABLE = "master_data";
  public static final String COL_ID = "_id";
  public static final String COL_ENTITY_PARTICIPANT_ID = "entity_id";
  public static final String COL_RECENT_SEARCH_FEED = "recent_search_object";

  private RecentSearchDataTable() {
  }
  public static String getCreateQuery() {
      return "CREATE TABLE " + TABLE + " ( " +
              COL_ID + " LONG PRIMARY KEY , " +
              COL_ENTITY_PARTICIPANT_ID + " LONG  , " +
              COL_RECENT_SEARCH_FEED + " TEXT "+ ");";
  }
  public static Query getAllQuery() {
    return Query.builder().table(TABLE).build();
  }
    public static RawQuery getQueryForList() {
        String query= AppConstants.SELECT_ALL_QUERY+TABLE;
        return RawQuery.builder().query(query).build();
    }
  public static SQLiteTypeMapping<RecentSearchData> getAllTypeMapping() {
    return SQLiteTypeMapping.<RecentSearchData>builder().putResolver(
        new RecentSearchDataStorIOSQLitePutResolver())
        .getResolver(new RecentSearchDataStorIOSQLiteGetResolver())
        .deleteResolver(new RecentSearchDataStorIOSQLiteDeleteResolver())
        .build();
  }
}
