package appliedlife.pvtltd.SHEROES.database.resolver;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import appliedlife.pvtltd.SHEROES.database.dbentities.Property;
import appliedlife.pvtltd.SHEROES.database.dbentities.PropertyStorIOSQLiteGetResolver;

/**
 * Created by Rajesh on 2/11/16.
 */
public class PropertyGetFullResolver extends PropertyStorIOSQLiteGetResolver {
  private final StorIOSQLite storIOSQLite;

  public PropertyGetFullResolver(StorIOSQLite storIOSQLite) {
    this.storIOSQLite = storIOSQLite;
  }

  @NonNull
  @Override
  public Property mapFromCursor(@NonNull Cursor cursor) {
    Property property = super.mapFromCursor(cursor);
    return property;
  }


}
