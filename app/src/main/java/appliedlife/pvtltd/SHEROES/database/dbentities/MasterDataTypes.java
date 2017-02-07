package appliedlife.pvtltd.SHEROES.database.dbentities;

public enum MasterDataTypes {

  MASTER_TYPE("test"),
  FEED_DATA("FeedData");

  public final String id;

  MasterDataTypes(String id) {this.id = id;}

  public static MasterDataTypes from(String id) {
    MasterDataTypes[] values = values();
    for (MasterDataTypes value : values) {
      if (value.id.equals(id)) {
        return value;
      }
    }
    return null;
  }

}
