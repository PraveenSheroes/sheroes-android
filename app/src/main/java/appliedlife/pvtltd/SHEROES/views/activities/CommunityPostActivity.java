package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.MyCommunities;
import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.UserTaggingPerson;
import appliedlife.pvtltd.SHEROES.presenters.CreatePostPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.PostPhotoAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PostBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityPostView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.createCommunityPostRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.editCommunityPostRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.schedulePost;

/**
 * Created by ujjwal on 28/10/17.
 */

public class CommunityPostActivity extends BaseActivity implements ICommunityPostView {
    public static final String SCREEN_LABEL = "Create Communities Post Screen";
    public static final String POSITION_ON_FEED = "POSITION_ON_FEED";
    public static final String IS_FROM_COMMUNITY = "Is from community";
    public static final String TYPE_IMAGE = "image/";
    public static final String TYPE_TEXT = "text/plain";
    public static final String TYPE_FILE = "file";
    public static final int MAX_IMAGE = 5;
    private boolean mIsPostScheduled = false;
    private boolean mStatusBarColorEmpty = true;
    private Dialog mScheduledConfirmationDialog;
    private Dialog mPostNowOrLaterDialog;

    //region View variables
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    CreatePostPresenter mCreatePostPresenter;

    @Inject
    AppUtils mAppUtils;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.title_toolbar)
    TextView mTitleToolbar;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.anonymous)
    RelativeLayout mAnonymousView;

    @Bind(R.id.anonymous_select)
    CheckBox mAnonymousSelect;

    @Bind(R.id.fb_share_container)
    RelativeLayout fbShareContainer;

    @Bind(R.id.share_on_fb)
    SwitchCompat mShareToFacebook;

    @Bind(R.id.user_pic)
    ImageView mUserPicView;

    @Bind(R.id.user_drop_down)
    ImageView mUserDropDownView;

    @Bind(R.id.user_name)
    TextView mUserName;

    @Bind(R.id.community_name)
    TextView mCommunityName;

    @Bind(R.id.et_default_hint_text)
    EditText mEtDefaultText;

    @Bind(R.id.progress_bar_link)
    ProgressBar pbLink;

    @Bind(R.id.iv_link_thumbnail)
    ImageView ivLinkThumbnail;

    @Bind(R.id.card_link_render)
    CardView cardViewLinkRender;

    @Bind(R.id.tv_link_title)
    TextView tvLinkTitle;

    @Bind(R.id.tv_link_sub_title)
    TextView tvLinkSubTitle;

    @Bind(R.id.image_list_view)
    RecyclerView mImageListView;

    @Bind(R.id.image_count)
    TextView mImageCount;

    @Bind(R.id.image_upload_view)
    LinearLayout mImageUploadView;

    @BindDimen(R.dimen.authorPicSize)
    int mAuthorPicSize;
    //endregion

    //region member variable
    private UserSummary mUserSummary;
    private boolean mIsAnonymous;
    private boolean mIsCommunityOwner;
    private boolean mIsCompanyAdmin;
    private boolean isSharedFromOtherApp;
    private PostPhotoAdapter mPostPhotoAdapter;
    private List<Photo> mImageList = new ArrayList<>();
    private boolean isLinkRendered;
    private LinkRenderResponse mLinkRenderResponse = null;
    private CommunityPost mCommunityPost;
    private boolean mIsEditPost;
    private boolean isSharedContent = false;
    private boolean mIsFromCommunity;
    private MyCommunities mMyCommunities;
    private int mFeedPosition;
    private String mOldText;
    private boolean mPostAsCommunitySelected;
    private boolean mIsProgressBarVisible;
    private boolean mIsChallengePost;
    private String mPrimaryColor = "#ffffff";
    private String mTitleTextColor = "#3c3c3c";
    private String mStatusBarColor = "#aaaaaa";
    private String mToolbarIconColor = "#90949C";
    private boolean mHasPermission = false;
    CallbackManager callbackManager;
    private AccessToken mAccessToken;

    //new images and deleted images are send when user edit the post
    private List<String> newEncodedImages = new ArrayList<>();
    private List<Long> deletedImageIdList = new ArrayList<>();
    private ArrayAdapter<UserTaggingPerson> customSocialUserAdapter;
    private View anonymousToolTip;
    //endregion

    //region Activity methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_community_post);
        ButterKnife.bind(this);
        mCreatePostPresenter.attachView(this);

        isSharedFromOtherApp = false;

        if (getIntent() != null) {
            mFeedPosition = getIntent().getIntExtra(POSITION_ON_FEED, -1);
            mIsFromCommunity = getIntent().getBooleanExtra(IS_FROM_COMMUNITY, false);
        }

        if (null != getIntent() && getIntent().getExtras() != null) {
            mPrimaryColor = getIntent().getExtras().getString(FeedFragment.PRIMARY_COLOR, mPrimaryColor);
            mTitleTextColor = getIntent().getExtras().getString(FeedFragment.TITLE_TEXT_COLOR, mTitleTextColor);

            if(getIntent().getExtras().getString(FeedFragment.PRIMARY_COLOR) == null) {
                mStatusBarColorEmpty = true;
            }
        }


        Parcelable parcelable = getIntent().getParcelableExtra(CommunityPost.COMMUNITY_POST_OBJ);
        if (parcelable != null) {
            mCommunityPost = Parcels.unwrap(parcelable);
            mIsEditPost = mCommunityPost.isEdit;
            mIsChallengePost = mCommunityPost.isChallengeType;
        }
        if (mIsChallengePost) {
            fbShareContainer.setVisibility(View.GONE);
            mAnonymousSelect.setVisibility(View.GONE);
            mAnonymousView.setVisibility(View.GONE);
            if (CommonUtil.isNotEmpty(mCommunityPost.challengeHashTag)) {
                mEtDefaultText.setText(" " + "#" + mCommunityPost.challengeHashTag);
                mEtDefaultText.requestFocus();
                mEtDefaultText.setSelection(0);
            }
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) mUserName.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mUserName.setLayoutParams(layoutParams);
        }
        if (mIsEditPost) {
            fbShareContainer.setVisibility(View.GONE);
            mPostAsCommunitySelected = mCommunityPost.isPostByCommunity;
            mIsAnonymous = mCommunityPost.isAnonymous;
            mEtDefaultText.setText(mCommunityPost.body);
            mEtDefaultText.requestFocus();
            mEtDefaultText.setSelection(mCommunityPost.body.length());
            if (mIsAnonymous) {
                mAnonymousSelect.setChecked(true);
                mPostAsCommunitySelected = false;
            } else if (!mIsAnonymous) {
                mAnonymousSelect.setChecked(false);
            }
            mOldText = mCommunityPost.body;
            invalidateUserDropDownView();
        } else {
            if (mCommunityPost != null && mCommunityPost.createPostRequestFrom != AppConstants.MENTOR_CREATE_QUESTION) {
                mEtDefaultText.requestFocus();
                if (!mIsChallengePost) {
                    fbShareContainer.setVisibility(View.VISIBLE);
                }
                if (!mIsFromCommunity && !mIsChallengePost) {
                    PostBottomSheetFragment.showDialog(this, SOURCE_SCREEN);
                }
            }
        }

        Parcelable parcelableMyCommunities = getIntent().getParcelableExtra(MyCommunities.MY_COMMUNITY_OBJ);
        if (parcelableMyCommunities != null) {
            mMyCommunities = Parcels.unwrap(parcelableMyCommunities);
        }

        setSupportActionBar(mToolbar);
        if (mUserPreference == null) {
            return;
        }

        if (mUserPreference.isSet() && mUserPreference.get() != null && mUserPreference.get().getUserSummary() != null) {
            mUserSummary = mUserPreference.get().getUserSummary();
            mIsCompanyAdmin = mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == 2;
        }
        if (mUserSummary == null) {
            return;
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mImageListView.setLayoutManager(layoutManager);
        ((SimpleItemAnimator) mImageListView.getItemAnimator()).setSupportsChangeAnimations(false);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupUserView();
        setupImageListView();
        setCommunityName();
        setupTextChangeListener();
        setupCommunityNameListener();
        setupShareToFbListener();
        setupAnonymousSlelectListener();
        setViewByCreatePostCall();
        setupToolbarItemsColor();

        Intent intent = getIntent();
        if (intent != null && intent.getType() != null) {
            mTitleToolbar.setText(R.string.title_create_post);

            String action = intent.getAction();
            String type = intent.getType();

            if (Intent.ACTION_SEND.equals(action) && type != null) {
                isSharedFromOtherApp = true;
                if (TYPE_TEXT.equals(type)) {
                    handleSendText(intent);
                } else if (type.startsWith(TYPE_IMAGE)) {
                    String textLink = intent.getStringExtra(Intent.EXTRA_TEXT);
                    if(StringUtil.isNotNullOrEmptyString(textLink)) {
                        mEtDefaultText.setText(textLink);
                        mEtDefaultText.requestFocus();
                        mEtDefaultText.setSelection(0);
                    }
                    handleSendImage((Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM));
                }
                PostBottomSheetFragment.showDialog(this, SOURCE_SCREEN);
            } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                isSharedFromOtherApp = true;
                if (type.startsWith(TYPE_IMAGE)) {
                    handleSendMultipleImages(intent);
                }
                PostBottomSheetFragment.showDialog(this, SOURCE_SCREEN);
            }
        }

        if (isSharedFromOtherApp) {
            if (mCommunityPost == null) {
                mCommunityPost = new CommunityPost();
                mCommunityPost.isEdit = false;
            }
        }

        if (!mIsChallengePost) {
            if (CommonUtil.ensureFirstTime(AppConstants.CREATE_POST_SHARE_PREF)) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            toolTipForAnonymous(CommunityPostActivity.this);
                        } catch (WindowManager.BadTokenException e) {
                            Crashlytics.getInstance().core.logException(e);
                        }
                    }
                }, 1500);
            }
        }
    }

    // Handle multiple images being sent
    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            int size = imageUris.size();
            if (imageUris.size() > MAX_IMAGE) { //display message if selected image more than 5
                size = MAX_IMAGE;
                showMessage(R.string.MAX_IMAGE_OVERFLOW_MESSAGE);
            }
            for (int i = 0; i < size; i++) {
                handleSendImage(imageUris.get(i));
            }
        }
    }

    // Handle single image being sent
    private void handleSendImage(Uri imageUri) {
        try {
            if (imageUri != null) {
                File file;
                Photo photo = new Photo();
                String realURI = getFilePath(imageUri);
                if (realURI == null) {
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    file = CommonUtil.getFilFromBitmap(this, bmp);
                    bmp.recycle();
                } else {
                    file = new File(realURI);
                }

                photo.isNew = true;
                photo.file = file;
                mImageList.add(photo);
                setImageCount();
                mPostPhotoAdapter.addPhoto(photo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //get path from Uri
    private String getFilePath(Uri imageUri) {
        if (imageUri.getScheme().equalsIgnoreCase(TYPE_FILE)) {
            return imageUri.getPath();
        } else {
            return CommonUtil.getImagePathFromInputStreamUri(this, imageUri);
        }
    }

    // Handle text being sent
    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            mIsEditPost = false;
            isSharedContent = true;
            mEtDefaultText.setText(sharedText);
        }
    }

    private void setViewByCreatePostCall() {
        if (null != mCommunityPost) {
            switch (mCommunityPost.createPostRequestFrom) {
                case AppConstants.CREATE_POST:
                    mTitleToolbar.setText(R.string.title_create_post);
                    break;
                case AppConstants.MENTOR_CREATE_QUESTION:
                    fbShareContainer.setVisibility(View.GONE);
                    mTitleToolbar.setText(R.string.title_ask_question);
                    break;
                default:
                    mTitleToolbar.setText(R.string.title_create_post);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == android.R.id.home) {
            onBackPress();
        }

        if (id == R.id.post) {
            if (!validateFields()) {
                return true;
            }
            if (mCommunityPost == null) {
                finish();
                return true;
            }
            if (mIsProgressBarVisible) {
                return true;
            }

            if ((!mIsEditPost && !mIsChallengePost) && (mIsCompanyAdmin || mCommunityPost.isMyPost)) {
                selectPostNowOrLater();
            } else {
                sendPost();
            }
        }

        return true;
    }

    private void askFacebookPublishPermission() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithPublishPermissions(CommunityPostActivity.this, Arrays.asList("publish_actions"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        mHasPermission = loginResult.getAccessToken().getPermissions().contains("publish_actions");
                    }

                    @Override
                    public void onCancel() {
                        mHasPermission = false;
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        mHasPermission = false;
                    }
                });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && accessToken.getPermissions().contains("publish_actions")) {
            mHasPermission = true;
        }
    }

    public void sendPost() {
        if (mHasPermission) {
            if (mCommunityPost != null) {
                final HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .id(Integer.toString(mCommunityPost.remote_id))
                                .title(mCommunityPost.title)
                                .build();
                AnalyticsManager.trackEvent(Event.FACEBOOK_PUBLISHED, getScreenName(), properties);
            }
        }

        if (mIsChallengePost) {
            mCreatePostPresenter.sendChallengePost(AppUtils.createChallengePostRequestBuilder(getCreatorType(), mCommunityPost.challengeId, mCommunityPost.challengeType, mEtDefaultText.getText().toString(), getImageUrls(), mLinkRenderResponse));
        } else if (!mIsEditPost) {
            String accessToken = "";
            if (AccessToken.getCurrentAccessToken() != null) {
                accessToken = AccessToken.getCurrentAccessToken().getToken();
            }

            mCreatePostPresenter.sendPost(createCommunityPostRequestBuilder(mCommunityPost.community.id, getCreatorType(), mEtDefaultText.getText().toString(), getImageUrls(), (long) 0, mLinkRenderResponse, mHasPermission, accessToken));
        } else {
            if (mCommunityPost != null) {
                mCreatePostPresenter.editPost(editCommunityPostRequestBuilder(mCommunityPost.community.id, getCreatorType(), mEtDefaultText.getText().toString(), newEncodedImages, (long) mCommunityPost.remote_id, deletedImageIdList, mLinkRenderResponse));
            }
        }
    }


    private void selectPostNowOrLater() {
        if (mPostNowOrLaterDialog != null) {
            mPostNowOrLaterDialog.dismiss();
        }

        mPostNowOrLaterDialog = new Dialog(CommunityPostActivity.this);
        mPostNowOrLaterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mPostNowOrLaterDialog.setCancelable(false);
        mPostNowOrLaterDialog.setContentView(R.layout.dialog_post_type_confirmation);

        TextView postNow = mPostNowOrLaterDialog.findViewById(R.id.post_now);
        postNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsPostScheduled = false;
                sendPost();
                mPostNowOrLaterDialog.dismiss();
            }
        });

        TextView scheduledPost = mPostNowOrLaterDialog.findViewById(R.id.schedule_post);
        scheduledPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsPostScheduled = true;
                mShareToFacebook.setChecked(false);
                datePicker();
                mPostNowOrLaterDialog.dismiss();
            }
        });

        mPostNowOrLaterDialog.show();
    }


    private void datePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        timePicker(year, monthOfYear, dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void timePicker(final int year, final int month, final int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        SimpleDateFormat formatter = new SimpleDateFormat(AppConstants.DATE_FORMAT);
                        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                        calendar.set(year, month, dayOfMonth,
                                hourOfDay, minute, 0);
                        String formattedDateTime = formatter.format(calendar.getTime());

                        if (hourOfDay >= calendar.get(Calendar.HOUR_OF_DAY) && minute >= calendar.get(Calendar.MINUTE)) { //time picker check for passed time
                            SimpleDateFormat timeFormatter = new SimpleDateFormat("dd MMM yyyy hh.mm a");
                            String dateMessage = timeFormatter.format(calendar.getTime());
                            scheduleConfirmation(formattedDateTime, dateMessage);
                        } else
                            Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE) + 1, false);

        timePickerDialog.show();
    }

    private void postForAdmin(String scheduledTime) {
        String accessToken = "";
        if (AccessToken.getCurrentAccessToken() != null) {
            accessToken = AccessToken.getCurrentAccessToken().getToken();
        }
        mCreatePostPresenter.sendPost(schedulePost(mCommunityPost.community.id, getCreatorType(), mEtDefaultText.getText().toString(), getImageUrls(), (long) 0, mLinkRenderResponse, mHasPermission, accessToken, scheduledTime));
    }

    private boolean validateFields() {
        if (!isDirty() && !mIsEditPost) {
            showMessage(R.string.error_blank);
            return false;
        }

        if (mCommunityPost != null && mCommunityPost.community == null && !mCommunityPost.isChallengeType) {
            showMessage(R.string.error_choose_community);
            return false;
        }
        return true;
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, intent);
        }
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                    if (resultCode == RESULT_OK) {
                        // ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                        try {
                            Photo photo = new Photo();
                            photo.isNew = true;
                            File file = new File(result.getUri().getPath());
                            photo.file = file;
                            mImageList.add(photo);
                            setImageCount();
                            if (mIsEditPost) {
                                Bitmap bitmap = decodeFile(photo.file);
                                byte[] buffer = new byte[4096];
                                if (null != bitmap) {
                                    buffer = getBytesFromBitmap(bitmap);
                                    if (null != buffer) {
                                        String encodedImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                                        if (StringUtil.isNotNullOrEmptyString(encodedImage)) {
                                            newEncodedImages.add(encodedImage);
                                        }
                                    }
                                }
                            }
                            mPostPhotoAdapter.addPhoto(photo);
                        } catch (Exception e) {
                            Crashlytics.getInstance().core.logException(e);
                            e.printStackTrace();
                        }
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                    }

                    break;

                default:
                    LogUtils.error(SCREEN_LABEL, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + SCREEN_LABEL + AppConstants.SPACE + requestCode);
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mPostNowOrLaterDialog != null) {
            mPostNowOrLaterDialog.dismiss();
        }

        if (mScheduledConfirmationDialog != null) {
            mScheduledConfirmationDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_create_post, menu);
         MenuItem menuItem = menu.findItem(R.id.post);

        if (mCommunityPost == null) return true;

        switch (mCommunityPost.createPostRequestFrom) {
            case AppConstants.CREATE_POST:
                SpannableString actionPost = new SpannableString(getResources().getString(R.string.action_post));
                actionPost.setSpan(new ForegroundColorSpan(Color.parseColor(mTitleTextColor)), 0, actionPost.length(), 0);
                menuItem.setTitle(actionPost);
                break;
            case AppConstants.MENTOR_CREATE_QUESTION:
                SpannableString actionMentor = new SpannableString(getResources().getString(R.string.action_mentor_post));
                actionMentor.setSpan(new ForegroundColorSpan(Color.parseColor(mTitleTextColor)), 0, actionMentor.length(), 0);
                menuItem.setTitle(actionMentor);
                break;
            default:
        }
        return true;
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void startProgressBar() {
        mIsProgressBarVisible = true;
        CommonUtil.hideKeyboard(this);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        mIsProgressBarVisible = false;
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void startNextScreen() {

    }

    @Override
    public void onBackPressed() {
        onBackPress();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mCreatePostPresenter;
    }

    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        switch (errorMsg) {
            case AppConstants.CHECK_NETWORK_CONNECTION:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_STR_NETWORK_TIME_OUT_DESCRIPTION));
                break;
            case AppConstants.HTTP_401_UNAUTHORIZED:
                showNetworkTimeoutDoalog(true, false, getString(R.string.IDS_INVALID_USER_PASSWORD));
                break;
            default:
                showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
        }
    }

    protected final void scheduleConfirmation(final String formattedDate, String formattedMessage) {

        if (mScheduledConfirmationDialog != null) {
            mScheduledConfirmationDialog.dismiss();
        }

        mScheduledConfirmationDialog = new Dialog(CommunityPostActivity.this);
        mScheduledConfirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mScheduledConfirmationDialog.setCancelable(false);
        mScheduledConfirmationDialog.setContentView(R.layout.dialog_schedule_post_confirmation);

        TextView messageText = (TextView) mScheduledConfirmationDialog.findViewById(R.id.message);
        String scheduledMessage = getResources().getString(R.string.post_schedule_message, mCommunityPost.community.name, formattedMessage);
        Spanned message = StringUtil.fromHtml(scheduledMessage);
        messageText.setText(message);
        TextView confirmedOk = (TextView) mScheduledConfirmationDialog.findViewById(R.id.ok);
        confirmedOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postForAdmin(formattedDate);
                mScheduledConfirmationDialog.dismiss();
            }
        });

        TextView editPost = (TextView) mScheduledConfirmationDialog.findViewById(R.id.edit);
        editPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
                mScheduledConfirmationDialog.dismiss();
            }
        });

        mScheduledConfirmationDialog.show();
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {
    }

    @Override
    public void onPostSend(FeedDetail feedDetail) {
        if (feedDetail != null) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            if (mIsEditPost) {
                feedDetail.setItemPosition(mFeedPosition);
                Parcelable parcelable = Parcels.wrap(feedDetail);
                bundle.putParcelable(AppConstants.COMMUNITY_POST_FRAGMENT, parcelable);
            }
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            navigateToParentActivity();
        }
    }

    //endregion

    //region private helper methods
    private String getCreatorType() {
        if (mPostAsCommunitySelected) {
            return AppConstants.COMMUNITY_OWNER;
        } else if (mIsAnonymous) {
            return AppConstants.ANONYMOUS;
        } else {
            return AppConstants.USER;
        }
    }

    private List<String> getImageUrls() {
        List<String> imageList = new ArrayList<>();
        if (StringUtil.isNotEmptyCollection(mImageList)) {
            for (Photo photo : mImageList) {
                Bitmap bitmap = decodeFile(photo.file);
                byte[] buffer = new byte[4096];
                if (null != bitmap) {
                    buffer = getBytesFromBitmap(bitmap);
                    if (null != buffer) {
                        String encodedImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                        if (StringUtil.isNotNullOrEmptyString(encodedImage)) {
                            imageList.add(encodedImage);
                        }
                    }
                }
            }
        }
        return imageList;
    }


    private void setupShareToFbListener() {
        mShareToFacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (mCommunityPost != null) {
                    final HashMap<String, Object> properties =
                            new EventProperty.Builder()
                                    .id(Integer.toString(mCommunityPost.remote_id))
                                    .title(mCommunityPost.title)
                                    .isChecked(isChecked)
                                    .build();
                    AnalyticsManager.trackEvent(Event.FACEBOOK_PUBLISHED_CLICKED, getScreenName(), properties);
                }
                if (!mIsAnonymous && !mIsPostScheduled) {
                    compoundButton.setChecked(isChecked);
                }
                setupUserView();
                if (!mIsAnonymous && isChecked) {
                    askFacebookPublishPermission();
                } else if (mIsPostScheduled) {
                    mHasPermission = false;
                } else {
                    mHasPermission = false;
                }
            }
        });
    }

    private void setupAnonymousSlelectListener() {
        mAnonymousSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mIsAnonymous = isChecked;
                mPostAsCommunitySelected = false;
                setupUserView();
            }
        });
    }

    private void toolTipForAnonymous(Context context) {
        try {
            LayoutInflater inflater = null;
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.tool_tip_arrow_down_side, null);
            FrameLayout.LayoutParams lps = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            lps.setMargins(CommonUtil.convertDpToPixel(10, context), 0, CommonUtil.convertDpToPixel(25, context), CommonUtil.convertDpToPixel(160, context));
            final ImageView ivArrow = view.findViewById(R.id.iv_arrow);
            RelativeLayout.LayoutParams arrowParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            arrowParams.setMargins(CommonUtil.convertDpToPixel(20, context), 0, 0, 0);//CommonUtil.convertDpToPixel(10, HomeActivity.this)
            arrowParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            arrowParams.addRule(RelativeLayout.BELOW, R.id.ll_tool_tip_bg);
            ivArrow.setLayoutParams(arrowParams);
            TextView text = view.findViewById(R.id.title);
            text.setText(R.string.tool_tip_create_post);
            TextView gotIt = view.findViewById(R.id.got_it);
            gotIt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAnonymousView.removeView(view);
                }
            });
            mAnonymousView.addView(view, lps);
        } catch (IllegalArgumentException e) {
            Crashlytics.getInstance().core.logException(e);
        }
    }

    private void setupCommunityNameListener() {
        mCommunityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsEditPost && !mIsFromCommunity) {
                    PostBottomSheetFragment.showDialog(CommunityPostActivity.this, SOURCE_SCREEN, mMyCommunities);
                }
            }
        });
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void setupTextChangeListener() {
        mEtDefaultText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                    if (StringUtil.isNotNullOrEmptyString(mEtDefaultText.getText().toString()) && !isLinkRendered) {
                        String editTextDescription = mEtDefaultText.getText().toString().trim();
                        if (editTextDescription.contains("https") || editTextDescription.contains("Http")) {
                            int indexOfFirstHttp = AppUtils.findNthIndexOf(editTextDescription.toLowerCase(), "https", 1);
                            int urlLength = getUrlLength(editTextDescription, indexOfFirstHttp);
                            if (urlLength <= editTextDescription.length()) {
                                String httpString = editTextDescription.substring(indexOfFirstHttp, urlLength);
                                if (mAppUtils.checkUrl(httpString)) {
                                    mCreatePostPresenter.fetchLinkDetails(mAppUtils.linkRequestBuilder(httpString));
                                }
                            }
                        } else if (editTextDescription.contains("www") || editTextDescription.contains("WWW")) {
                            int indexOfFirstWWW = AppUtils.findNthIndexOf(editTextDescription.toLowerCase(), "www", 1);
                            int urlLength = getUrlLength(editTextDescription, indexOfFirstWWW);
                            if (urlLength <= editTextDescription.length()) {
                                String wwwString = editTextDescription.substring(indexOfFirstWWW, urlLength);
                                if (mAppUtils.checkWWWUrl(wwwString)) {
                                    mCreatePostPresenter.fetchLinkDetails(mAppUtils.linkRequestBuilder(wwwString));
                                }
                            }
                        }
                    }

                }
            }
        });
    }

    //Disable the link editing and enable for others
    private void disableEditTextForLinks() {
        if (isLinkRendered) {
            mEtDefaultText.setFocusable(false);
            mEtDefaultText.setFocusableInTouchMode(false);
            mEtDefaultText.setClickable(false);
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

    private int getUrlLength(String editTextDescription, int indexOfFirstPattern) {
        int urlLength = indexOfFirstPattern;
        for (int i = indexOfFirstPattern; i < editTextDescription.length(); i++) {
            if (editTextDescription.charAt(i) == ' ') {
                break;
            } else {
                urlLength++;
            }
        }
        return urlLength;
    }

    private void setCommunityName() {
        if (mCommunityPost != null && mCommunityPost.createPostRequestFrom == AppConstants.MENTOR_CREATE_QUESTION) {
            mCommunityName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            String fullString = getString(R.string.ID_ASKING) + AppConstants.SPACE + mCommunityPost.community.name;
            SpannableString SpanString = new SpannableString(fullString);
            SpanString.setSpan(null, 0, getString(R.string.ID_ASKING).length(), 0);
            SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.recent_post_comment)), 0, getString(R.string.ID_ASKING).length(), 0);
            mCommunityName.setEnabled(false);
            mCommunityName.setMovementMethod(LinkMovementMethod.getInstance());
            mCommunityName.setText(SpanString, TextView.BufferType.SPANNABLE);
            mCommunityName.setSelected(true);
            mEtDefaultText.setHint(getString(R.string.ID_WHAT_IS_QUESTION));
            mEtDefaultText.requestFocus();
        } else {
            if (mIsChallengePost) {
                mCommunityName.setVisibility(View.GONE);
            }
            if (mCommunityPost != null && mCommunityPost.community != null)
                mCommunityName.setText(mCommunityPost.community.name);
        }

    }

    private void invalidateUserDropDownView() {
        if (mCommunityPost.community.isOwner) {
            mIsCompanyAdmin = true;
            mUserDropDownView.setVisibility(View.VISIBLE);
        } else {
            mUserDropDownView.setVisibility(View.GONE);
        }
    }

    public boolean isPostModified() {
        return !mOldText.equals(mEtDefaultText.getText().toString()) || !CommonUtil.isEmpty(newEncodedImages) || !CommonUtil.isEmpty(deletedImageIdList);
    }

    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, int requestCodeForCommunityPost, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, CommunityPostActivity.class);
        UserPostSolrObj userPostObj = (UserPostSolrObj) feedDetail;
        if (feedDetail != null) {
            CommunityPost communityPost = new CommunityPost();
            communityPost.remote_id = (int) userPostObj.getIdOfEntityOrParticipant();
            communityPost.community = new Community();
            communityPost.community.id = userPostObj.getCommunityTypeId();
            communityPost.body = userPostObj.getListDescription();
            communityPost.community.name = userPostObj.getPostCommunityName();
            communityPost.community.isOwner = userPostObj.isCommunityOwner();
            communityPost.isMyPost = userPostObj.isCommunityOwner();

            communityPost.community.thumbImageUrl = userPostObj.getSolrIgnorePostCommunityLogo();
            communityPost.isAnonymous = userPostObj.isAnonymous();
            communityPost.isEdit = true;
            communityPost.isPostByCommunity = userPostObj.isCommunityPost();
            if (!CommonUtil.isEmpty(userPostObj.getImageUrls()) && !CommonUtil.isEmpty(userPostObj.getImagesIds())) {
                for (String imageUrl : userPostObj.getImageUrls()) {
                    Photo photo = new Photo();
                    photo.url = imageUrl;
                    communityPost.photos.add(photo);
                }
                int i = 0;
                for (Long imageId : userPostObj.getImagesIds()) {
                    communityPost.photos.get(i).remote_id = imageId.intValue();
                    i++;
                }
            }
            Parcelable parcelable = Parcels.wrap(communityPost);
            intent.putExtra(CommunityPost.COMMUNITY_POST_OBJ, parcelable);
            intent.putExtra(POSITION_ON_FEED, feedDetail.getItemPosition());
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCodeForCommunityPost, null);

    }


    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, int requestCodeForCommunityPost, String primaryColor, String titleTextColor, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, CommunityPostActivity.class);
        UserPostSolrObj userPostObj = (UserPostSolrObj) feedDetail;
        if (feedDetail != null) {
            CommunityPost communityPost = new CommunityPost();
            communityPost.remote_id = (int) userPostObj.getIdOfEntityOrParticipant();
            communityPost.community = new Community();
            communityPost.community.id = userPostObj.getCommunityTypeId();
            communityPost.body = userPostObj.getListDescription();
            communityPost.community.name = userPostObj.getPostCommunityName();
            communityPost.community.isOwner = userPostObj.isCommunityOwner();
            communityPost.isMyPost = userPostObj.isCommunityOwner();

            communityPost.community.thumbImageUrl = userPostObj.getSolrIgnorePostCommunityLogo();
            communityPost.isAnonymous = userPostObj.isAnonymous();
            communityPost.isEdit = true;
            communityPost.isPostByCommunity = userPostObj.isCommunityPost();
//            communityPost.isCompanyAdmin =  userPostObj.grt();
            if (!CommonUtil.isEmpty(userPostObj.getImageUrls()) && !CommonUtil.isEmpty(userPostObj.getImagesIds())) {
                for (String imageUrl : userPostObj.getImageUrls()) {
                    Photo photo = new Photo();
                    photo.url = imageUrl;
                    communityPost.photos.add(photo);
                }
                int i = 0;
                for (Long imageId : userPostObj.getImagesIds()) {
                    communityPost.photos.get(i).remote_id = imageId.intValue();
                    i++;
                }
            }
            Parcelable parcelable = Parcels.wrap(communityPost);
            intent.putExtra(CommunityPost.COMMUNITY_POST_OBJ, parcelable);
            intent.putExtra(POSITION_ON_FEED, feedDetail.getItemPosition());
            intent.putExtra(FeedFragment.PRIMARY_COLOR, primaryColor);
            intent.putExtra(FeedFragment.TITLE_TEXT_COLOR, titleTextColor);
            if (!CommonUtil.isEmpty(properties)) {
                intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
            }
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCodeForCommunityPost, null);

    }

    public static void navigateTo(Activity fromActivity, CommunityPost communityPost, int requestCode, boolean isFromCommunity, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, CommunityPostActivity.class);
        Parcelable parcelable = Parcels.wrap(communityPost);
        intent.putExtra(CommunityPost.COMMUNITY_POST_OBJ, parcelable);
        intent.putExtra(IS_FROM_COMMUNITY, isFromCommunity);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    public static void navigateTo(Activity fromActivity, CommunityPost communityPost, int requestCode, boolean isFromCommunity, String primaryColor, String titleTextColor, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, CommunityPostActivity.class);
        Parcelable parcelable = Parcels.wrap(communityPost);
        intent.putExtra(CommunityPost.COMMUNITY_POST_OBJ, parcelable);
        intent.putExtra(IS_FROM_COMMUNITY, isFromCommunity);
        intent.putExtra(FeedFragment.PRIMARY_COLOR, primaryColor);
        intent.putExtra(FeedFragment.TITLE_TEXT_COLOR, titleTextColor);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    private void onBackPress() {

        if (isDirty() && !isSharedFromOtherApp) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(CommunityPostActivity.this);

            builder.setTitle("Discard Post?");
            builder.setMessage("Are you sure you want to discard your changes?");
            builder.setNegativeButton("NO", null);
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    CommunityPostActivity.this.finish();
                }
            });

            builder.create();
            builder.show();
        } else {
            navigateToParentActivity();
        }
    }

    private void navigateToParentActivity() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else if (isSharedFromOtherApp) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        }
        CommunityPostActivity.this.finish();
    }

    private boolean isDirty() {
        if (mIsEditPost) {
            if (isPostModified()) {
                return true;
            }
        } else {
            if (CommonUtil.isNotEmpty(mEtDefaultText.getText().toString().trim()) || !CommonUtil.isEmpty(mImageList)) {
                return true;
            }
        }
        return false;
    }

    private void setImageCount() {
        mImageCount.setText(getString(R.string.image_count, mImageList.size(), MAX_IMAGE));
        if (mImageList.size() == MAX_IMAGE) {
            mImageUploadView.setVisibility(View.GONE);
        } else {
            mImageUploadView.setVisibility(View.VISIBLE);
        }
    }

    private void setupImageListView() {
        mPostPhotoAdapter = new PostPhotoAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View recyclerViewItem = (View) view.getParent();
                int position = mImageListView.getChildAdapterPosition(recyclerViewItem);
                if(position == -1) return;
                Photo photo = mImageList.get(position);
                if (mIsEditPost && !photo.isNew) {
                    deletedImageIdList.add((long) photo.remote_id);
                }
                mImageList.remove(position);
                setImageCount();
                mPostPhotoAdapter.removePhoto(position);
            }
        });
        mImageListView.setAdapter(mPostPhotoAdapter);
        if (mIsEditPost) {
            if (!CommonUtil.isEmpty(mCommunityPost.photos)) {
                mImageList = mCommunityPost.photos;
                List<Photo> images = new ArrayList<>(mImageList);
                mPostPhotoAdapter.setData(images);
                setImageCount();
            }
        }
    }

    private void setupUserView() {
        if (mPostAsCommunitySelected) {
            mCommunityName.setVisibility(View.GONE);
            if (mCommunityPost.community != null) {
                mUserName.setText(CommonUtil.capitalizeString(mCommunityPost.community.name));
            }
        } else {
            if (mIsChallengePost) {
                mCommunityName.setVisibility(View.GONE);
            } else {
                mCommunityName.setVisibility(View.VISIBLE);
            }
            if (mIsAnonymous) {
                mUserName.setText("Anonymous");
                mShareToFacebook.setChecked(false);
            } else {
                mUserName.setText(CommonUtil.capitalizeString(mUserSummary.getFirstName() + " " + mUserSummary.getLastName()));
            }
        }
        setUserImage();
    }

    private void setUserImage() {
        if (!mIsAnonymous && !mPostAsCommunitySelected) {
            if (mUserSummary.getPhotoUrl() != null && CommonUtil.isNotEmpty(mUserSummary.getPhotoUrl())) {
                String authorImage = CommonUtil.getImgKitUri(mUserSummary.getPhotoUrl(), mAuthorPicSize, mAuthorPicSize);
                Glide.with(this)
                        .load(authorImage)
                        .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(this)))
                        .into(mUserPicView);
            }
        } else if (mPostAsCommunitySelected) {
            Glide.with(this)
                    .load(mCommunityPost.community.thumbImageUrl)
                    .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(this)))
                    .into(mUserPicView);
        } else {
            mUserPicView.setImageResource(R.drawable.ic_anonomous);
        }
    }

    //endregion

    //region public methods
    public void setMainCommunity(Community community, MyCommunities myCommunities) {
        if (mCommunityPost != null) {
            mCommunityPost.community = new Community();
            mCommunityPost.community.id = community.id;
            mCommunityPost.community.name = community.name;
            mCommunityPost.community.isOwner = community.isOwner;
            mCommunityPost.community.thumbImageUrl = community.thumbImageUrl;
            mMyCommunities = myCommunities;
            setCommunityName();
            invalidateUserDropDownView();
        } else {
            if (isSharedContent) {
                mCommunityPost = new CommunityPost();
                mCommunityPost.community = community;
                mCommunityPost.createPostRequestFrom = -1;
                mCommunityPost.isChallengeType = false;
                mCommunityPost.community.id = community.id;
                mCommunityPost.community.name = community.name;
                mCommunityPost.community.isOwner = community.isOwner;
                mCommunityPost.community.thumbImageUrl = community.thumbImageUrl;
                mMyCommunities = myCommunities;
                setCommunityName();
                invalidateUserDropDownView();
            }
        }
    }

    public void linkRenderResponse(LinkRenderResponse linkRenderResponse) {
        if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getStatus())) {
            switch (linkRenderResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    isLinkRendered = true;
                    cardViewLinkRender.setVisibility(View.VISIBLE);
                    disableEditTextForLinks();
                    mLinkRenderResponse = linkRenderResponse;
                    if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getOgTitleS())) {
                        tvLinkTitle.setText(linkRenderResponse.getOgTitleS());
                    }
                    if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getOgDescriptionS())) {
                        tvLinkSubTitle.setText(linkRenderResponse.getOgDescriptionS());
                    }

                    RequestOptions requestOptions = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.color.photo_placeholder)
                            .error(R.color.photo_placeholder)
                            .priority(Priority.HIGH)
                            .skipMemoryCache(true);

                    if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getOgImageUrlS())) {
                        Glide.with(this)
                                .asBitmap()
                                .apply(requestOptions)
                                .load(linkRenderResponse.getOgImageUrlS())
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                                        ivLinkThumbnail.setImageBitmap(profileImage);
                                        ivLinkThumbnail.setVisibility(View.VISIBLE);
                                        pbLink.setVisibility(View.GONE);
                                    }
                                });
                    }
                    break;
                case AppConstants.FAILED:
                    //mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(linkRenderResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), DELETE_COMMUNITY_POST);
                    break;
                default:
                    //mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), DELETE_COMMUNITY_POST);
            }
        } else {
            //mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), DELETE_COMMUNITY_POST);
        }
    }

    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        CommunityPostActivity.this.finish();
    }

    public void showMessage(int stringRes) {
        if (mCommunityName != null) {
            Snackbar snackbar = Snackbar.make(mCommunityName, stringRes, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }
    //endregion

    //region onclick methods

    @OnClick(R.id.add_image)
    void onAddImageClick() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(1200, 1200)
                .start(this);
    }

    @OnClick(R.id.camera)
    void onCameraClick() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(1000, 1000)
                .start(this);
    }

    @OnClick(R.id.user_drop_down)
    public void onUserDropDownClicked() {
        PopupMenu popup = new PopupMenu(CommunityPostActivity.this, mUserDropDownView);
        popup.getMenuInflater().inflate(R.menu.menu_user_spinner, popup.getMenu());
        popup.getMenu().findItem(R.id.user_menu).setTitle(mPostAsCommunitySelected ? getString(R.string.menu_post_as_user, mUserSummary.getFirstName() + " " + mUserSummary.getLastName()) : getString(R.string.menu_post_as_community));
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (!mPostAsCommunitySelected) {
                    mAnonymousSelect.setChecked(false);
                    mPostAsCommunitySelected = true;
                } else {
                    mPostAsCommunitySelected = false;
                }
                setupUserView();
                return true;
            }
        });
        popup.show();
    }
    //endregion

    private void setupToolbarItemsColor() {
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
        if (upArrow != null) {
            upArrow.setColorFilter(Color.parseColor(mTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        }
        mTitleToolbar.setTextColor(Color.parseColor(mTitleTextColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(mStatusBarColorEmpty) {
                if (upArrow != null) {
                    upArrow.setColorFilter(Color.parseColor(mToolbarIconColor), PorterDuff.Mode.SRC_ATOP);
                }
                getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mStatusBarColor)));
            } else {
                getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mPrimaryColor)));
            }
        }

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mToolbar.setBackgroundColor(Color.parseColor(mPrimaryColor));
    }
}
