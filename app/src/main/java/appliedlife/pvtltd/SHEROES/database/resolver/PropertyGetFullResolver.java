package appliedlife.pvtltd.SHEROES.database.resolver;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import appliedlife.pvtltd.SHEROES.database.dbentities.User;
import appliedlife.pvtltd.SHEROES.database.dbentities.UserStorIOSQLiteGetResolver;

/**
 * Created by Rajesh on 2/11/16.
 */
public class PropertyGetFullResolver extends UserStorIOSQLiteGetResolver {
  private final StorIOSQLite storIOSQLite;

  public PropertyGetFullResolver(StorIOSQLite storIOSQLite) {
    this.storIOSQLite = storIOSQLite;
  }

  @NonNull
  @Override
  public User mapFromCursor(@NonNull Cursor cursor) {
    User user = super.mapFromCursor(cursor);
    return user;
  }


}
