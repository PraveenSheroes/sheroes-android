package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.LatLongWithLocation;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.MakeIndiaSafeDetail;
import appliedlife.pvtltd.SHEROES.service.GPSTracker;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.ViewPagerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.MakeIndiaSafeFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An activity that displays a map showing the place at the device's current location.
 */
public class MakeIndiaSafeMapActivity extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final String TAG = LogUtils.makeLogTag(MakeIndiaSafeMapActivity.class);
    @Bind(R.id.app_bar_map_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.view_pager_map_layout)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_map_layout)
    Toolbar mToolbar;
    ViewPagerAdapter mViewPagerAdapter;
    @Bind(R.id.tab_map_layout)
    public TabLayout mTabLayout;
    @Bind(R.id.fm_map_layout)
    public FrameLayout mFlMapLayout;
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleApiClient mGoogleApiClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private LatLng mDefaultLocation;
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.
    private final int mMaxEntries = 5;
    private String[] mLikelyPlaceNames = new String[mMaxEntries];
    private String[] mLikelyPlaceAddresses = new String[mMaxEntries];
    private String[] mLikelyPlaceAttributions = new String[mMaxEntries];
    private LatLng[] mLikelyPlaceLatLngs = new LatLng[mMaxEntries];
    private Uri mImageCaptureUri;
    private File localImageSaveForChallenge;
    private LatLongWithLocation mLatLongWithLocation = new LatLongWithLocation();
    @Bind(R.id.tv_loc_text)
    TextView tvLocText;
    private MakeIndiaSafeDetail mMakeIndiaSafeDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.map_activity_with_drawer);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationTracker();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            locationTracker();
        }
        ((SheroesApplication) getApplication()).trackScreenView(getString(R.string.ID_MAKE_INDIA_SAFE_SCREEN));
       /* if (null != getIntent() && null != getIntent().getExtras()) {
            mLatLongWithLocation = getIntent().getExtras().getParcelable(AppConstants.LAT_LONG_DETAIL);
            if (null != mLatLongWithLocation) {
                mDefaultLocation = new LatLng(mLatLongWithLocation.getLatitude(), mLatLongWithLocation.getLongitude());
            }
        }*/
        // Build the Play services client for use by the Fused Location Provider and the Places API.
        // Use the addApi() method to request the Google Places API and the Fused Location Provider.
    }

    private void setPagerAndLayouts() {

        ViewCompat.setTransitionName(mAppBarLayout, AppConstants.LAT_LONG_DETAIL);
        supportPostponeEnterTransition();
        setSupportActionBar(mToolbar);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(MakeIndiaSafeFragment.createInstance(mLatLongWithLocation), AppConstants.SPACE);
        mViewPager.setAdapter(mViewPagerAdapter);
        // mTabLayout.setupWithViewPager(mViewPager);
    }


    private void locationTracker() {
        GPSTracker gps = new GPSTracker(this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            LatLongWithLocation latLongWithLocation = new LatLongWithLocation();
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            latLongWithLocation.setLatitude(latitude);
            latLongWithLocation.setLongitude(longitude);
            String cityName, locality, state;
            Geocoder geocoder = new Geocoder(MakeIndiaSafeMapActivity.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (StringUtil.isNotEmptyCollection(addresses)) {
                    if (StringUtil.isNotNullOrEmptyString(addresses.get(0).getLocality())) {
                        cityName = addresses.get(0).getLocality();
                        latLongWithLocation.setCityName(cityName);
                    }
                    if (StringUtil.isNotNullOrEmptyString(addresses.get(0).getAddressLine(1))) {
                        locality = addresses.get(0).getAddressLine(1);
                        latLongWithLocation.setLocality(locality);
                    }
                    if (StringUtil.isNotNullOrEmptyString(addresses.get(0).getAddressLine(2))) {
                        state = addresses.get(0).getAddressLine(2);
                        latLongWithLocation.setState(state);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mLatLongWithLocation = latLongWithLocation;
            mDefaultLocation = new LatLng(latLongWithLocation.getLatitude(), latLongWithLocation.getLongitude());
            if (StringUtil.isNotNullOrEmptyString(latLongWithLocation.getLocality())) {
                String title = getString(R.string.ID_DO_YOU_SEE) + AppConstants.SPACE + latLongWithLocation.getLocality() + AppConstants.SPACE + "?";
                tvLocText.setText(title);
            } else {
                String title = getString(R.string.ID_DO_YOU_SEE) + AppConstants.SPACE + getString(R.string.ID_YOUR_LOCALITY);
                tvLocText.setText(title);
            }

            setPagerAndLayouts();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */,
                            this /* OnConnectionFailedListener */)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build();
            mGoogleApiClient.connect();
           /* if(latitude>0&&longitude>0)
            {
                makeWomenSafeDialog(mLatLongWithLocation);
            }else
            {
                showNetworkTimeoutDoalog(true, true, "Please Goto setting->Permission control->Get loc-> SHEROES app allow");
            }*/

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }


    /**
     * Builds the map when the Google Play services client is successfully connected.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Handles failure to connect to the Google Play services client.
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        // Refer to the reference doc for ConnectionResult to see what error codes might
        // be returned in onConnectionFailed.
    }

    /**
     * Handles suspension of the connection to the Google Play services client.
     */
    @Override
    public void onConnectionSuspended(int cause) {
    }


    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Use a custom info window adapter to handle multiple lines of text in the
        // info window contents.
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custome_info_contents,
                        (FrameLayout) findViewById(R.id.map), false);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        if (null != mDefaultLocation) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.addMarker(new MarkerOptions().position(mDefaultLocation));
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    locationTracker();
                }
            }
            break;
            case AppConstants.REQUEST_CODE_FOR_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  selectImageFrmCamera();
                }
            }

        }
        updateLocationUI();
    }

    /**
     * Prompts the user to select the current place from a list of likely places, and shows the
     * current place on the map - provided the user has granted location permission.
     */
    private void showCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            // Get the likely places - that is, the businesses and other points of interest that
            // are the best match for the device's current location.
            @SuppressWarnings("MissingPermission")
            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                    .getCurrentPlace(mGoogleApiClient, null);
            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {
                    int i = 0;
                    mLikelyPlaceNames = new String[mMaxEntries];
                    mLikelyPlaceAddresses = new String[mMaxEntries];
                    mLikelyPlaceAttributions = new String[mMaxEntries];
                    mLikelyPlaceLatLngs = new LatLng[mMaxEntries];
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        // Build a list of likely places to show the user. Max 5.
                        mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                        mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace().getAddress();
                        mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace()
                                .getAttributions();
                        mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                        i++;
                        if (i > (mMaxEntries - 1)) {
                            break;
                        }
                    }
                    // Release the place likelihood buffer, to avoid memory leaks.
                    likelyPlaces.release();

                    // Show a dialog offering the user the list of likely places, and add a
                    // marker at the selected place.
                    openPlacesDialog();
                }
            });
        } else {
            // Add a default marker, because the user hasn't selected a place.
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.ID_APP_NAME))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.ID_BOOKMARK)));
        }
    }

    /**
     * Displays a form allowing the user to select a place from a list of likely places.
     */
    private void openPlacesDialog() {
        // Ask the user to choose the place where they are now.
        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // The "which" argument contains the position of the selected item.
                        LatLng markerLatLng = mLikelyPlaceLatLngs[which];
                        String markerSnippet = mLikelyPlaceAddresses[which];
                        if (mLikelyPlaceAttributions[which] != null) {
                            markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[which];
                        }
                        // Add a marker for the selected place, with an info window
                        // showing information about that place.
                        mMap.addMarker(new MarkerOptions()
                                .title(mLikelyPlaceNames[which])
                                .position(markerLatLng)
                                .snippet(markerSnippet));

                        // Position the map's camera at the location of the marker.
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                                DEFAULT_ZOOM));
                    }
                };

        // Display the dialog.
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.ID_ASK_SHEROES)
                .setItems(mLikelyPlaceNames, listener)
                .show();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            if (null!=mLatLongWithLocation&&StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
                String title = getString(R.string.ID_DO_YOU_SEE) + AppConstants.SPACE + mLatLongWithLocation.getLocality() + AppConstants.SPACE + "?";
                tvLocText.setText(title);
            } else {
                String title = getString(R.string.ID_DO_YOU_SEE) + AppConstants.SPACE + getString(R.string.ID_YOUR_LOCALITY);
                tvLocText.setText(title);
            }
            if(null==mLatLongWithLocation)
            {
                mLatLongWithLocation=new LatLongWithLocation();
                mLatLongWithLocation.setLongitude(20.5937);
                mLatLongWithLocation.setLatitude(78.9629);
            }
            setPagerAndLayouts();
            return;
        }

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {

                case AppConstants.REQUEST_CODE_FOR_GALLERY:
                    mImageCaptureUri = intent.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING:
                    imageCropping(intent);
                    break;

                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        } else {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
                    }
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        }

    }


    public void selectImageFrmCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (null == localImageSaveForChallenge && null == mImageCaptureUri) {
                    Uri imageCaptureUri;
                    File localImageSaveForChallenge;
                    localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
                    imageCaptureUri = Uri.fromFile(localImageSaveForChallenge);
                    this.localImageSaveForChallenge = localImageSaveForChallenge;
                    mImageCaptureUri = imageCaptureUri;
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CAMERA);
                } else {
                    mImageCaptureUri = Uri.fromFile(localImageSaveForChallenge);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CAMERA);
                }
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (null == localImageSaveForChallenge && null == mImageCaptureUri) {
                Uri imageCaptureUri;
                File localImageSaveForChallenge;
                localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
                imageCaptureUri = Uri.fromFile(localImageSaveForChallenge);
                this.localImageSaveForChallenge = localImageSaveForChallenge;
                mImageCaptureUri = imageCaptureUri;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CAMERA);

            } else {
                mImageCaptureUri = Uri.fromFile(localImageSaveForChallenge);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, AppConstants.REQUEST_CODE_FOR_CAMERA);
            }

        }

    }

    public void selectImageFrmGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                try {
                    Intent galIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galIntent.setType("image/*");
                    startActivityForResult(galIntent, AppConstants.REQUEST_CODE_FOR_GALLERY);
                } catch (Exception e) {
                }
            }
        } else {
            try {
                Intent galIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galIntent.setType("image/*");
                startActivityForResult(galIntent, AppConstants.REQUEST_CODE_FOR_GALLERY);
            } catch (Exception e) {
            }
        }
    }

    private void cropingIMG() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List list = getPackageManager().queryIntentActivities(intent, 0);
        intent.setData(mImageCaptureUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(localImageSaveForChallenge));
        if (StringUtil.isNotEmptyCollection(list)) {
            Intent i = new Intent(intent);
            ResolveInfo res = (ResolveInfo) list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING);
        }

    }

    private void imageCropping(Intent intent) {
        try {
            if (localImageSaveForChallenge.exists()) {
                Bitmap photo = decodeFile(localImageSaveForChallenge);
                Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((MakeIndiaSafeFragment) fragment).setImageOnHolder(mMakeIndiaSafeDetail,photo, localImageSaveForChallenge);
                }
            } else {
                Toast.makeText(this, "Error while save image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @OnClick(R.id.iv_back_map_layout)
    public void onBackButton() {
        onBackPressed();
    }
    public void backClick() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.MAKE_INDIA_SAFE, AppConstants.MAKE_INDIA_SAFE);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onBackPressed();
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof MakeIndiaSafeDetail) {
            MakeIndiaSafeDetail makeIndiaSafeDetail = (MakeIndiaSafeDetail) baseResponse;
            mMakeIndiaSafeDetail=makeIndiaSafeDetail;
            int id = view.getId();
            if (id == R.id.tv_click_pic_and_tell_friends) {
                Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((MakeIndiaSafeFragment) fragment).checkCameraPermission();
                }

            } else if (id == R.id.tv_share_with_friends) {
                Fragment fragment = mViewPagerAdapter.getActiveFragment(mViewPager, AppConstants.NO_REACTION_CONSTANT);
                if (AppUtils.isFragmentUIActive(fragment)) {
                    ((MakeIndiaSafeFragment) fragment).makeCommunityPostRequest(makeIndiaSafeDetail);
                }
            }
        }
    }
}