package appliedlife.pvtltd.SHEROES.database.dbtables;


import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.queries.Query;

import appliedlife.pvtltd.SHEROES.database.dbentities.Property;
import appliedlife.pvtltd.SHEROES.database.dbentities.PropertyStorIOSQLiteDeleteResolver;
import appliedlife.pvtltd.SHEROES.database.dbentities.PropertyStorIOSQLiteGetResolver;
import appliedlife.pvtltd.SHEROES.database.dbentities.PropertyStorIOSQLitePutResolver;

public final class PropertyTable {
  public static final String TABLE = "property";
  public static final String COL_INTERNAL_ID = "internal_id";
  public static final String COL_ID = "id";
  public static final String COL_UUID = "uuid";
  public static final String COL_NAME = "name";
  public static final String COL_DESCRIPTION = "description";
  public static final String COL_EMAIL = "email";
  public static final String COL_ADDRESS = "address";
  public static final String COL_WEBSITE = "website";
  public static final String COL_CONTACTS = "landline";
  public static final String COL_LATITUDE = "latitude";
  public static final String COL_LONGITUDE = "longitude";
  public static final String COL_BANK_NAME = "bank_name";
  public static final String COL_BANK_BRANCH = "bank_branch";
  public static final String COL_BANK_ACC_NAME = "bank_account_name";
  public static final String COL_BANK_ACC_NUM = "bank_account_num";
  public static final String COL_BANK_IFSC = "bank_ifsc";
  public static final String COL_BANK_CHEQUE_IMAGE_URL = "bank_cheque_image_url";
  public static final String COL_BANK_PAN_IMAGE_URL = "bank_pan_image_url";
  public static final String COL_CONTACT_PERSON = "contact_person";
  public static final String COL_CITY_ID = "city_id";
  public static final String COL_STATUS = "status";
  public static final String COL_CREATED_TIME = "created_time";
  public static final String COL_UPDATED_TIME = "updated_time";
  public static final String COL_LAST_SYNC_TIME = "last_sync_time";
  public static final String COL_PINCODE = "pincode";
  public static final String COL_TYPE = "type";
  public static final String COL_LISTING_TYPE = "listing_type";

  //Apartment
  public static final String COL_PIC_URLS = "picUrls";
  public static final String COL_BEDROOMS = "bedrooms";
  public static final String COL_BATHROOMS = "bathrooms";
  public static final String COL_MAX_GUESTS = "maxGuests";
  public static final String COL_BEDTYPE = "bedType";
  public static final String COL_PRICE_PD = "pricePerDay";

  //amenities
  public static final String COL_BATHROOM_TYPE = "bathroomType";

  //bookingsettings
  public static final String COL_BOOKING_TYPE = "bookingType";
  public static final String COL_CHECKIN_TIME = "checkinTime";
  public static final String COL_CHECKOUT_TIME = "checkoutTime";
  public static final String COL_MIN_STAY_DURATION = "minStay";
  public static final String COL_MAX_STAY_DURATION = "maxStay";

  public static final String COL_PROPERTY_GROUP_ID = "propertyGroupId";

  public static final String COL_PROPERTY_STAGE = "stage";

  private PropertyTable() {
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
                + COL_CONTACT_PERSON + " TEXT, "
                + COL_ADDRESS + " TEXT, "
                + COL_PINCODE + " TEXT, "
                + COL_CITY_ID + " INTEGER, "
                + COL_LATITUDE + " NUMERIC, "
                + COL_LONGITUDE + " NUMERIC, "
                + COL_WEBSITE + " TEXT, "
                + COL_CONTACTS + " TEXT, "
                + COL_BANK_NAME + " TEXT, "
                + COL_BANK_BRANCH + " TEXT, "
                + COL_BANK_ACC_NAME + " TEXT, "
                + COL_BANK_ACC_NUM + " TEXT, "
                + COL_BANK_IFSC + " TEXT, "
                + COL_BANK_CHEQUE_IMAGE_URL + " TEXT, "
                + COL_BANK_PAN_IMAGE_URL + " TEXT, "
                + COL_TYPE + " INTEGER, "
                + COL_LISTING_TYPE + " INTEGER, "
                + COL_STATUS + " TEXT, "
                + COL_LAST_SYNC_TIME + " NUMERIC, "
                + COL_CREATED_TIME + " NUMERIC, "
                + COL_UPDATED_TIME + " NUMERIC,"
                + COL_PIC_URLS + " TEXT," +
                COL_BEDROOMS + " INTEGER," +
                COL_BATHROOMS + " INTEGER," +
                COL_MAX_GUESTS + " INTEGER," +
                COL_BEDTYPE + " TEXT," +
                COL_PRICE_PD + " TEXT," +
                COL_BATHROOM_TYPE + " INTEGER," +
                COL_BOOKING_TYPE +" INTEGER,"+
                COL_CHECKIN_TIME+" INTEGER,"+
                COL_CHECKOUT_TIME+" INTEGER,"+
                COL_MIN_STAY_DURATION+" INTEGER,"+
                COL_MAX_STAY_DURATION+" INTEGER ,"+
                COL_PROPERTY_STAGE+" INTEGER ,"+
                COL_PROPERTY_GROUP_ID + " TEXT" +

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
        .where(COL_PROPERTY_GROUP_ID + " =? ")
        .whereArgs(String.valueOf(id))
        .build();
  }

  public static SQLiteTypeMapping<Property> getHotelTypeMapping() {
    return SQLiteTypeMapping.<Property>builder().putResolver(new PropertyStorIOSQLitePutResolver())
        .getResolver(new PropertyStorIOSQLiteGetResolver())
        .deleteResolver(new PropertyStorIOSQLiteDeleteResolver())
        .build();
  }
}
