package appliedlife.pvtltd.SHEROES.database.dbentities;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.List;
import java.util.UUID;

import appliedlife.pvtltd.SHEROES.database.dbtables.UserTable;
import appliedlife.pvtltd.SHEROES.utils.Exclude;


@StorIOSQLiteType(table = UserTable.TABLE)
public class User {
  public static final long INVALID_PROPERTY_ID = -1L;
  @Exclude
  @StorIOSQLiteColumn(name = UserTable.COL_INTERNAL_ID, key = true)
  long internalId;
  @StorIOSQLiteColumn(name = UserTable.COL_UUID)
  String uuid = UUID.randomUUID().toString();
  @StorIOSQLiteColumn(name = UserTable.COL_ID)
  long id;
  @StorIOSQLiteColumn(name = UserTable.COL_NAME)
  String name;
  @StorIOSQLiteColumn(name = UserTable.COL_DESCRIPTION)
  String description;
  @SerializedName("emails")
  @StorIOSQLiteColumn(name = UserTable.COL_EMAIL)
  String email;
  @Nullable
  @StorIOSQLiteColumn(name = UserTable.COL_CONTACTS)
  String contacts;
  @Nullable
  @StorIOSQLiteColumn(name = UserTable.COL_ADDRESS)
  String address;

  List<Integer> amenities;


  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public List<Integer> getAmenities() {
    return amenities;
  }

  public void setAmenities(List<Integer> amenities) {
    this.amenities = amenities;
  }

  public long getInternalId() {
    return internalId;
  }

  public void setInternalId(long internalId) {
    this.internalId = internalId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  @Nullable
  public String getContacts() {
    return contacts;
  }

  public void setContacts(@Nullable String contacts) {
    this.contacts = contacts;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Nullable
  public String getAddress() {
    return address;
  }

  public void setAddress(@Nullable String address) {
    this.address = address;
  }

}
