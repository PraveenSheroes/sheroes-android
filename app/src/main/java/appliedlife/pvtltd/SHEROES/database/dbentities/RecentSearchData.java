package appliedlife.pvtltd.SHEROES.database.dbentities;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import appliedlife.pvtltd.SHEROES.database.dbtables.RecentSearchDataTable;
@StorIOSQLiteType(table = RecentSearchDataTable.TABLE)
public class RecentSearchData {

  @StorIOSQLiteColumn(name = RecentSearchDataTable.COL_ID,key = true)
  long internalId;
  @StorIOSQLiteColumn(name = RecentSearchDataTable.COL_ENTITY_PARTICIPANT_ID)
  long entityOrParticipantId;
  @StorIOSQLiteColumn(name = RecentSearchDataTable.COL_RECENT_SEARCH_FEED)
  String recentSearchFeed;


  public String getRecentSearchFeed() {
    return recentSearchFeed;
  }

  public void setRecentSearchFeed(String recentSearchFeed) {
    this.recentSearchFeed = recentSearchFeed;
  }

  public long getEntityOrParticipantId() {
    return entityOrParticipantId;
  }

  public void setEntityOrParticipantId(long entityOrParticipantId) {
    this.entityOrParticipantId = entityOrParticipantId;
  }

  public long getInternalId() {
    return internalId;
  }

  public void setInternalId(long internalId) {
    this.internalId = internalId;
  }
}
