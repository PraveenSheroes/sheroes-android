package appliedlife.pvtltd.SHEROES.database.dbentities;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import appliedlife.pvtltd.SHEROES.database.dbtables.PropertyTable;
import appliedlife.pvtltd.SHEROES.utils.Exclude;


@StorIOSQLiteType(table = PropertyTable.TABLE)
public class Property {
  public static final Long INVALID_PROPERTY_ID = -1L;
  @Exclude
  @StorIOSQLiteColumn(name = PropertyTable.COL_INTERNAL_ID, key = true)
  Long internalId;
  @StorIOSQLiteColumn(name = PropertyTable.COL_UUID)
  String uuid = UUID.randomUUID().toString();
  @StorIOSQLiteColumn(name = PropertyTable.COL_ID)
  Long id;
  @StorIOSQLiteColumn(name = PropertyTable.COL_NAME)
  String name;
  @SerializedName("contactperson") @StorIOSQLiteColumn(name = PropertyTable.COL_CONTACT_PERSON)
  String contactPerson;
  @StorIOSQLiteColumn(name = PropertyTable.COL_DESCRIPTION)
  String description;
  @SerializedName("emails")
  @StorIOSQLiteColumn(name = PropertyTable.COL_EMAIL)
  String email;
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_CONTACTS)
  String contacts;
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_ADDRESS)
  String address;
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_PINCODE)
  String pincode;
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_WEBSITE)
  String website;
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_LATITUDE)
  Double latitude;
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_LONGITUDE)
  Double longitude;
  @Exclude @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_CITY_ID)
  Integer cityId;
  @SerializedName("bank_name") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BANK_NAME)
  int bankName;
  @SerializedName("bank_branch") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BANK_BRANCH)
  String bankBranch;
  @SerializedName("account_holder_name") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BANK_ACC_NAME)
  String accountHolderName;
  @SerializedName("account_number") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BANK_ACC_NUM)
  String accountNumber;
  @SerializedName("bank_ifsc_code") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BANK_IFSC)
  String bankIfscCode;
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_STATUS)
  String status;
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_TYPE)
  Integer type;
  @SerializedName("listing_type")
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_LISTING_TYPE)
  Integer listingType;
  @Exclude @StorIOSQLiteColumn(name = PropertyTable.COL_CREATED_TIME) long createdTime;
  @Exclude @StorIOSQLiteColumn(name = PropertyTable.COL_UPDATED_TIME) long updatedTime;
  @Exclude @StorIOSQLiteColumn(name = PropertyTable.COL_LAST_SYNC_TIME) long lastSyncedTime;
  @Exclude @StorIOSQLiteColumn(name = PropertyTable.COL_PROPERTY_STAGE)
  Integer stage;

  @SerializedName("booking_preference")
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BOOKING_TYPE) int bookingType;

  @SerializedName("cancellation_policies")
  ArrayList<Integer> cancellationPolicy;

  @SerializedName("checkin_time_display")
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_CHECKIN_TIME) int checkinTime;

  @SerializedName("checkout_time_display")
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_CHECKOUT_TIME) int checkoutTime;
  @SerializedName("min_nights") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_MIN_STAY_DURATION) int minStay;

  @Exclude @SerializedName("max_nights") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_MAX_STAY_DURATION) int maxTime;

  // TODO: 09/02/16 REMOVE THIS
  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_PIC_URLS)
  String picUrls;

  @SerializedName("bedrooms") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BEDROOMS)
  int bedRooms;
  @SerializedName("adults") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_MAX_GUESTS)
  int maxGuests;
  @SerializedName("bathrooms") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BATHROOMS)
  int bathrooms;

  @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BEDTYPE)
  String bedType;

  @SerializedName("cheque_image_url") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BANK_CHEQUE_IMAGE_URL)
  String chequeImageUrl;
  @SerializedName("id_image_url") @Nullable
  @StorIOSQLiteColumn(name = PropertyTable.COL_BANK_PAN_IMAGE_URL)
  String panImageUrl;

  List<Integer> amenities;



  @Nullable
  public int getBookingType() {
    return bookingType;
  }

  public void setBookingType(@Nullable int bookingType) {
    this.bookingType = bookingType;
  }

  @Nullable
  public int getCheckinTime() {
    return checkinTime;
  }

  public void setCheckinTime(@Nullable int checkinTime) {
    this.checkinTime = checkinTime;
  }

  @Nullable
  public int getCheckoutTime() {
    return checkoutTime;
  }

  public void setCheckoutTime(@Nullable int checkoutTime) {
    this.checkoutTime = checkoutTime;
  }

  @Nullable
  public int getMinStay() {
    return minStay;
  }

  public void setMinStay(@Nullable int minStay) {
    this.minStay = minStay;
  }

  @Nullable
  public int getMaxTime() {
    return maxTime;
  }

  public void setMaxTime(@Nullable int maxTime) {
    this.maxTime = maxTime;
  }

  @Nullable
  public String getPicUrls() {
    return picUrls;
  }

  public void setPicUrls(@Nullable String picUrls) {
    this.picUrls = picUrls;
  }

  @Nullable
  public int getBedRooms() {
    return bedRooms;
  }

  public void setBedRooms(@Nullable int bedRooms) {
    this.bedRooms = bedRooms;
  }

  @Nullable
  public int getMaxGuests() {
    return maxGuests;
  }

  public void setMaxGuests(@Nullable int maxGuests) {
    this.maxGuests = maxGuests;
  }

  @Nullable
  public int getBathrooms() {
    return bathrooms;
  }

  public void setBathrooms(@Nullable int bathrooms) {
    this.bathrooms = bathrooms;
  }

  @Nullable
  public String getBedType() {
    return bedType;
  }

  public void setBedType(@Nullable String bedType) {
    this.bedType = bedType;
  }

  public Long getInternalId() {
    return internalId;
  }

  public void setInternalId(Long internalId) {
    this.internalId = internalId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  @Nullable
  public String getPincode() {
    return pincode;
  }

  public void setPincode(@Nullable String pincode) {
    this.pincode = pincode;
  }

  @Nullable
  public String getWebsite() {
    return website;
  }

  public void setWebsite(@Nullable String website) {
    this.website = website;
  }

  @Nullable
  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(@Nullable Double latitude) {
    this.latitude = latitude;
  }

  @Nullable
  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(@Nullable Double longitude) {
    this.longitude = longitude;
  }

  @Nullable
  public Integer getCityId() {
    return cityId;
  }

  public void setCityId(@Nullable Integer cityId) {
    this.cityId = cityId;
  }

  public String getContactPerson() {
    return contactPerson;
  }

  public void setContactPerson(String contactPerson) {
    this.contactPerson = contactPerson;
  }

  @Nullable
  public int getBankName() {
    return bankName;
  }

  public void setBankName(@Nullable int bankName) {
    this.bankName = bankName;
  }

  @Nullable
  public String getBankBranch() {
    return bankBranch;
  }

  public void setBankBranch(@Nullable String bankBranch) {
    this.bankBranch = bankBranch;
  }

  @Nullable
  public String getAccountHolderName() {
    return accountHolderName;
  }

  public void setAccountHolderName(@Nullable String accountHolderName) {
    this.accountHolderName = accountHolderName;
  }

  @Nullable
  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(@Nullable String accountNumber) {
    this.accountNumber = accountNumber;
  }

  @Nullable
  public String getBankIfscCode() {
    return bankIfscCode;
  }

  public void setBankIfscCode(@Nullable String bankIfscCode) {
    this.bankIfscCode = bankIfscCode;
  }

  @Nullable
  public String getStatus() {
    return status;
  }

  public void setStatus(@Nullable String status) {
    this.status = status;
  }

  public long getCreatedTime() {
    return createdTime;
  }

  public void setCreatedTime(long createdTime) {
    this.createdTime = createdTime;
  }

  public long getUpdatedTime() {
    return updatedTime;
  }

  public void setUpdatedTime(long updatedTime) {
    this.updatedTime = updatedTime;
  }

  public long getLastSyncedTime() {
    return lastSyncedTime;
  }

  public void setLastSyncedTime(long lastSyncedTime) {
    this.lastSyncedTime = lastSyncedTime;
  }

  public boolean isUptoDate() {
    return updatedTime > 0 && lastSyncedTime == updatedTime;
  }

  @Nullable
  public Integer getType() {
    return type;
  }

  public void setType(@Nullable Integer type) {
    this.type = type;
  }



  @Nullable
  public String getChequeImageUrl() {
    return chequeImageUrl;
  }

  public void setChequeImageUrl(@Nullable String chequeImageUrl) {
    this.chequeImageUrl = chequeImageUrl;
  }

  @Nullable
  public String getPanImageUrl() {
    return panImageUrl;
  }

  public void setPanImageUrl(@Nullable String panImageUrl) {
    this.panImageUrl = panImageUrl;
  }

  public List<Integer> getAmenities() {
    return amenities;
  }

  public void setAmenities(List<Integer> amenities) {
    this.amenities = amenities;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Integer getStage() {return stage;}

  public void setStage(Integer stage) {this.stage = stage;}

  public ArrayList<Integer> getCancellationPolicy() {
    if(cancellationPolicy== null){
      cancellationPolicy = new ArrayList<>();
    }
    return cancellationPolicy;
  }

  public void setCancellationPolicy(ArrayList<Integer> cancellationPolicy) {
    this.cancellationPolicy = cancellationPolicy;
  }
}
