package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.post.Album;
import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.presenters.AlbumPresenter;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.AlbumCarouselAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.AlbumGalleryAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.ShareBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IAlbumView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 21/03/17.
 */

public class AlbumActivity extends BaseActivity implements IAlbumView {
    private static final String TAG = "AlbumActivity";
    private static final String SCREEN_LABEL = "Album Activity";
    private static final String NOTIFICATION_SCREEN = "Push Notification";
    private static final String SAVE_TO_GALLERY = "Save To Gallery";

    private Album mAlbum;
    private String mMainImageUrl;
    private AlbumCarouselAdapter mAlbumCarouselAdapter;
    private String mAlbumId;

    @Inject
    AlbumPresenter mAlbumPresenter;

    //region View variables
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.image_slides)
    ViewPager mViewPager;

    @Bind(R.id.image_list)
    RecyclerView mImageListView;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    //endregion

    //region Activity method

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        Parcelable parcelable = getIntent().getParcelableExtra(Album.ALBUM_OBJ);
        if (parcelable != null) {
            mAlbum = Parcels.unwrap(parcelable);
            if (mAlbum == null) {
                return;
            }
        } else {
            if (getIntent().getExtras() != null) {
                mAlbumId = getIntent().getExtras().getString(FeedDetail.FEED_DETAIL_OBJ);
                String notificationId = getIntent().getExtras().getString("notificationId");
                /*if (!TextUtils.isEmpty(notificationId)) {
                    setSource(NOTIFICATION_SCREEN);
                }*/
            }
        }
        mAlbumPresenter.setView(this);
        //mAlbumPresenter.attachView(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_dark));
        mToolbar.setPadding(0, CommonUtil.getStatusBarHeight(AlbumActivity.this), 0, 0);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mImageListView.setLayoutManager(layoutManager);
        ((SimpleItemAnimator) mImageListView.getItemAnimator()).setSupportsChangeAnimations(false);

        if (mAlbum != null) {
            if (CommonUtil.isEmpty(mAlbum.photos)) {
                return;
            }
            showAlbum(mAlbum);
        } /*else if (CommonUtil.isNotEmpty(mAlbumId)) {
            mAlbumPresenter.fetchAlbum(mAlbumId);
        } */else {
            return;
        }
    }

   /* @Override
    public void onStart() {
        super.onStart();
        PermissionBus.getInstance().register(this).add(PermissionBus.getInstance().toObserveable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object event) {
                if (event instanceof CameraEvent) {
                    onSaveToGallery((CameraEvent) event);
                }
            }
        }));
    }

    @Override
    public void onStop() {
        super.onStop();
        PermissionBus.getInstance().unregister(this);
    }*/

    private void initViewPager() {
        CommonUtil.Callback callback = new CommonUtil.Callback() {
            @Override
            public void callBack(boolean isShown) {
                if (isShown) {
                    mImageListView.animate().translationY(mImageListView.getHeight()).setInterpolator(new AccelerateInterpolator()).start();
                } else {
                    mImageListView.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                }
            }
        };
        AlbumGalleryAdapter mAlbumGalleryAdapter = new AlbumGalleryAdapter(AlbumActivity.this, mAlbum.photos, callback);
        mViewPager.setAdapter(mAlbumGalleryAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mMainImageUrl = mAlbum.photos.get(position).url;
                if (mImageListView != null) {
                    mImageListView.smoothScrollToPosition(position);
                    mAlbumCarouselAdapter.setSelectedPosition(position);
                }
                getSupportActionBar().setTitle(getString(R.string.album_title, position + 1, mAlbum.photos.size()));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_album, menu);
        setUpOptionMenuStates(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setUpOptionMenuStates(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            /*Intent upIntent = NavUtils.getParentActivityIntent(this);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(upIntent)
                        .startActivities();
            }*/
            onBackPressed();
        } else if (id == R.id.share) {
            ShareBottomSheetFragment.showDialog(AlbumActivity.this, mMainImageUrl, getPreviousScreenName(), mAlbum);
        }
        return true;
    }
    //endregion

    //region private helper methods
    private void initAlbumCarouselAdapter() {
        mAlbumCarouselAdapter = new AlbumCarouselAdapter(this, mAlbum.photos, new View.OnClickListener() {
            @Override
            public void onClick(View imageItem) {
                View recyclerViewItem = (View) imageItem;
                int position = mImageListView.getChildAdapterPosition(recyclerViewItem);
                mViewPager.setCurrentItem(position);
                mAlbumCarouselAdapter.setSelectedPosition(position);
            }
        });
        mImageListView.setAdapter(mAlbumCarouselAdapter);
    }

    private void setUpOptionMenuStates(Menu menu) {
        MenuItem itemShare = menu.findItem(R.id.share);
        itemShare.setVisible(true);
    }
    //endregion

    //region public helper methods
    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, String sourceScreen, HashMap<String, Object> properties) {
        if (feedDetail == null || CommonUtil.isEmpty(feedDetail.getImageUrls())) {
            return;
        }
        Album album = new Album();
        for (String url : feedDetail.getImageUrls()) {
            Photo photo = new Photo();
            photo.url = url;
            album.photos.add(photo);
        }
        Intent intent = new Intent(fromActivity, AlbumActivity.class);
        Parcelable parcelable = Parcels.wrap(album);
        intent.putExtra(album.ALBUM_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivity(fromActivity, intent, null);
    }

  /*  public void onSaveToGallery(CameraEvent event) {
        if (event.isPermissionAllowed) {
            final String newPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + Globals.GALLERY_FOLDER_NAME + File.separator + "IMG_" + new Date().getTime() + Math.floor(Math.random() * 10000) + ".jpg";
            mAlbumPresenter.onSaveImage(mMainImageUrl, newPath, getApplicationContext());
        } else {
            Snackbar snackbar = Snackbar
                    .make(mToolbar, R.string.save_to_gallery_permission, Snackbar.LENGTH_LONG)
                    .setAction("Try Now", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RxUtil.requestPermission(AlbumActivity.this, CareApplication.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE, Globals.STORAGE_PERMISSION);
                        }
                    });

            snackbar.show();
        }
    }*/

    @Override
    public void showAlbum(Album album) {
        if (CommonUtil.isEmpty(album.photos)) {
            return;
        }
        mAlbum = album;
        getSupportActionBar().setTitle(getString(R.string.album_title, 1, mAlbum.photos.size()));
        mMainImageUrl = mAlbum.photos.get(0).url;
        initAlbumCarouselAdapter();
        initViewPager();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSuccessMessage() {
        Toast.makeText(this, R.string.img_saved_info, Toast.LENGTH_LONG).show();
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        final EventProperty.Builder builder = new EventProperty.Builder();
        if (mAlbum != null) {
            builder.id("");
        }
        HashMap<String, Object> properties = builder.build();
        return properties;
    }

    @Override
    public void trackSaveImageEvent(String fullPath) {
/*        EventProperty.Builder builder = new EventProperty.Builder().sharedTo(SAVE_TO_GALLERY);
        final HashMap<String, Object> properties = builder.build();
        properties.put(EventProperty.SOURCE.getString(), getPreviousScreenName());
        properties.put(EventProperty.URL.getString(), fullPath);
        AnalyticsManager.trackEvent(Event.IMAGE_SHARED, properties);*/
    }
    //endregion
}