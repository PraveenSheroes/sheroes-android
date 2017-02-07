package appliedlife.pvtltd.SHEROES.database.dbentities;

import android.support.annotation.Nullable;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import appliedlife.pvtltd.SHEROES.database.dbtables.MasterDataTable;
@StorIOSQLiteType(table = MasterDataTable.TABLE)
public class MasterData {

  @Nullable
  @StorIOSQLiteColumn(name = MasterDataTable.COL_ID, key = true)
  int internalId;
  @StorIOSQLiteColumn(name = MasterDataTable.COL_MASTER_TYPE_ID) 
  int id;
  @StorIOSQLiteColumn(name = MasterDataTable.COL_NAME)
  String name;
  @StorIOSQLiteColumn(name = MasterDataTable.COL_DESCRIPTION)
  String description;
  @StorIOSQLiteColumn(name = MasterDataTable.COL_DISPLAY_NAME)
  String display_name;
  @StorIOSQLiteColumn(name = MasterDataTable.COL_MASTER_DATA_TYPE)
  String master_type;

  @Nullable
  public int getInternalId() {
    return internalId;
  }

  public void setInternalId(int internalId) {
    this.internalId = internalId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Nullable
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDisplayName() {
    return display_name;
  }

  public void setDisplayName(String displayName) {
    this.display_name = displayName;
  }

  public MasterDataTypes getDataType() {
    return MasterDataTypes.from(master_type);
  }

  public void setDataType(MasterDataTypes dataType) {
    this.master_type = dataType.id;
  }

  @Override
  public String toString() {
    return display_name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MasterData that = (MasterData) o;

    if (id != that.id) return false;
    if (name != null ? !name.equals(that.name) : that.name != null) return false;
    if (description != null ? !description.equals(that.description) : that.description != null)
      return false;
    if (display_name != null ? !display_name.equals(that.display_name) : that.display_name != null)
      return false;
    return master_type != null ? master_type.equals(that.master_type) : that.master_type == null;

  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (display_name != null ? display_name.hashCode() : 0);
    result = 31 * result + (master_type != null ? master_type.hashCode() : 0);
    return result;
  }
}
