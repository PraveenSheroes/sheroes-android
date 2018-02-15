package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.post.Album;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.presenters.AlbumPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.AlbumCarouselAdapter;
import appliedlife.pvtltd.SHEROES.views.adapters.AlbumGalleryAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IAlbumView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 21/03/17.
 */

public class AlbumActivity extends BaseActivity implements IAlbumView {
    private static final String TAG = "AlbumActivity";
    private static final String SCREEN_LABEL = "Album Activity";
    private static final String MAIN_ITEM_POSITION = "Main Item Position";

    private Album mAlbum;
    private String mMainImageUrl;
    private AlbumCarouselAdapter mAlbumCarouselAdapter;
    private int mMainItemPosition;
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
            if (getIntent().getExtras() != null) {
                mMainItemPosition = getIntent().getExtras().getInt(MAIN_ITEM_POSITION, 1);
            }
        } else {
            if (getIntent().getExtras() != null) {
                mAlbumId = getIntent().getExtras().getString(FeedDetail.FEED_DETAIL_OBJ);
            }
        }
        mAlbumPresenter.setView(this);
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
        } */ else {
            return;
        }
       if (CommonUtil.forGivenCountOnly(AppConstants.PICTURE_SHARE_SESSION_PREF, AppConstants.ALBUM_SESSION)== AppConstants.ALBUM_SESSION) {
          if (CommonUtil.ensureFirstTime(AppConstants.PICTURE_SHARE_PREF)) {
                toolTipForPictureShare();
           }
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
        if (mMainItemPosition > 0 && mMainItemPosition < mAlbum.photos.size()) {
            mViewPager.setCurrentItem(mMainItemPosition);
            mMainImageUrl = mAlbum.photos.get(mMainItemPosition).url;
            mAlbumCarouselAdapter.setSelectedPosition(mMainItemPosition);
        }
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
            if (CommonUtil.isNotEmpty(mAlbum.deepLinkUrl)) {

                String shareText = Config.COMMUNITY_POST_IMAGE_SHARE + System.getProperty("line.separator") + mAlbum.deepLinkUrl;
                CommonUtil.shareImageWhatsApp(this, shareText, mMainImageUrl, "Album Screen", true);
                //Not removed because we have added whatsapp share feature for experiment and if in future we want roll back then we can use this code.

                //  ShareBottomSheetFragment.showDialog(AlbumActivity.this, shareText, mMainImageUrl, mAlbum.deepLinkUrl, getPreviousScreenName(), true, mMainImageUrl, false);
            }
        }
        return true;
    }
    //endregion

    //region private helper methods
    private void initAlbumCarouselAdapter() {
        mAlbumCarouselAdapter = new AlbumCarouselAdapter(this, mAlbum.photos, new View.OnClickListener() {
            @Override
            public void onClick(View imageItem) {
                View recyclerViewItem = imageItem;
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
    private void toolTipForPictureShare() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    final View albumToolTip;
                    final PopupWindow popupWindowAlbumTooTip;
                    int width = AppUtils.getWindowWidth(AlbumActivity.this);
                    LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    albumToolTip = layoutInflater.inflate(R.layout.tooltip_arrow_up_side, null);
                    popupWindowAlbumTooTip = new PopupWindow(albumToolTip, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindowAlbumTooTip.setOutsideTouchable(false);
                    if(width<750) {
                        popupWindowAlbumTooTip.showAsDropDown(mToolbar, 40, -10);
                    }else
                    {
                        popupWindowAlbumTooTip.showAsDropDown(mToolbar, width-200, -10);
                    }
                    final ImageView ivArrow = albumToolTip.findViewById(R.id.iv_arrow);
                    RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    imageParams.setMargins(0, 0, CommonUtil.convertDpToPixel(10, AlbumActivity.this), 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
                    imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
                    ivArrow.setLayoutParams(imageParams);
                    final TextView tvGotIt = albumToolTip.findViewById(R.id.got_it);
                    final TextView tvTitle = albumToolTip.findViewById(R.id.title);
                    tvTitle.setText(getString(R.string.tool_tip_picture_share));
                    tvGotIt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindowAlbumTooTip.dismiss();
                        }
                    });
                } catch (WindowManager.BadTokenException e) {
                    Crashlytics.getInstance().core.logException(e);
                }
            }
        }, 1000);


    }

    //endregion

    //region public helper methods
    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, String sourceScreen, HashMap<String, Object> properties) {
        UserPostSolrObj userPostObj = (UserPostSolrObj) feedDetail;
        if (feedDetail == null || CommonUtil.isEmpty(userPostObj.getImageUrls())) {
            return;
        }
        Album album = new Album();
        for (String url : userPostObj.getImageUrls()) {
            Photo photo = new Photo();
            photo.url = url;
            album.photos.add(photo);
        }
        if (StringUtil.isNotNullOrEmptyString(feedDetail.getPostShortBranchUrls())) {
            album.deepLinkUrl = feedDetail.getPostShortBranchUrls();
        } else {
            album.deepLinkUrl = feedDetail.getDeepLinkUrl();
        }
        Intent intent = new Intent(fromActivity, AlbumActivity.class);
        Parcelable parcelable = Parcels.wrap(album);
        intent.putExtra(Album.ALBUM_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(MAIN_ITEM_POSITION, feedDetail.getItemPosition());
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
        mMainImageUrl = mAlbum.photos.get(0).url;
        mAlbum = album;
        if (mAlbum.photos.size() < 2) {
            getSupportActionBar().setTitle("");
            mImageListView.setVisibility(View.GONE);
        } else {
            mImageListView.setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(getString(R.string.album_title, mMainItemPosition + 1, mAlbum.photos.size()));
        }
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
    protected SheroesPresenter getPresenter() {
        return null;
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
