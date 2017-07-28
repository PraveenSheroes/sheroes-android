package appliedlife.pvtltd.SHEROES.service;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Praveen_Singh on 28-07-2017.
 */

class MyLocationListener implements LocationListener {
    Location location;

    public void onLocationChanged(Location loc) {

        if (loc != null) {
            location = loc;

        }
    }

    public void onProviderDisabled(String provider) {

    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}

