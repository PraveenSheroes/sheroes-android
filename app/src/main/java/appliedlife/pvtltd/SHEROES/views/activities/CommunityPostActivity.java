package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
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

/**
 * Created by ujjwal on 28/10/17.
 */

public class CommunityPostActivity extends BaseActivity implements ICommunityPostView {
    public static final String SCREEN_LABEL = "Create Communities Post Screen";
    public static final String POSITION_ON_FEED = "POSITION_ON_FEED";
    public static final String IS_FROM_COMMUNITY = "Is from community";
    public static final int MAX_IMAGE = 5;

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
    EditText mEtDefaultHintText;

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
    private PostPhotoAdapter mPostPhotoAdapter;
    private List<Photo> mImageList = new ArrayList<>();
    private boolean isLinkRendered;
    private LinkRenderResponse mLinkRenderResponse = null;
    private CommunityPost mCommunityPost;
    private boolean mIsEditPost;
    private boolean mIsFromCommunity;
    private MyCommunities mMyCommunities;
    private int mFeedPosition;
    private String mOldText;
    private boolean mPostAsCommunitySelected;
    private boolean mIsProgressBarVisible;
    private boolean mIsChallengePost;
    private String mPrimaryColor = "#6e2f95";
    private String mTitleTextColor = "#ffffff";
    private boolean hasPermission = false;
    CallbackManager callbackManager;
    private AccessToken mAccessToken;

    //new images and deleted images are send when user edit the post
    private List<String> newEncodedImages = new ArrayList<>();
    private List<Long> deletedImageIdList = new ArrayList<>();
    //endregion

    //region Activity methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_community_post);
        ButterKnife.bind(this);
        mCreatePostPresenter.attachView(this);

        if (getIntent() != null) {
            mFeedPosition = getIntent().getIntExtra(POSITION_ON_FEED, -1);
            mIsFromCommunity = getIntent().getBooleanExtra(IS_FROM_COMMUNITY, false);
        }

        if (null != getIntent() && getIntent().getExtras() != null) {
            mPrimaryColor = getIntent().getExtras().getString(FeedFragment.PRIMARY_COLOR, "#6e2f95");
            mTitleTextColor = getIntent().getExtras().getString(FeedFragment.TITLE_TEXT_COLOR, "#ffffff");
        }

        Parcelable parcelable = getIntent().getParcelableExtra(CommunityPost.COMMUNITY_POST_OBJ);
        if (parcelable != null) {
            mCommunityPost = Parcels.unwrap(parcelable);
            mIsEditPost = mCommunityPost.isEdit;
            mIsChallengePost = mCommunityPost.isChallengeType;
        }
        if (mIsChallengePost) {
            mAnonymousSelect.setVisibility(View.GONE);
            mAnonymousView.setVisibility(View.GONE);
            if (CommonUtil.isNotEmpty(mCommunityPost.challengeHashTag)) {
                mEtDefaultHintText.setText(" " + "#" + mCommunityPost.challengeHashTag);
                mEtDefaultHintText.requestFocus();
                mEtDefaultHintText.setSelection(0);
            }
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) mUserName.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mUserName.setLayoutParams(layoutParams);
        }
        if (mIsEditPost) {
            mPostAsCommunitySelected = mCommunityPost.isPostByCommunity;
            mIsAnonymous = mCommunityPost.isAnonymous;
            mEtDefaultHintText.setText(mCommunityPost.body);
            mEtDefaultHintText.requestFocus();
            mEtDefaultHintText.setSelection(mCommunityPost.body.length());
            if (mIsAnonymous) {
                mAnonymousSelect.setChecked(true);
                mPostAsCommunitySelected = false;
            } else if (!mIsAnonymous) {
                mAnonymousSelect.setChecked(false);
            }
            mOldText = mCommunityPost.body;
            invalidateUserDropDownView();
        } else {
            if (mCommunityPost.createPostRequestFrom != AppConstants.MENTOR_CREATE_QUESTION) {
                mEtDefaultHintText.requestFocus();
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
    }

    private void setViewByCreatePostCall() {
        if (null != mCommunityPost) {
            switch (mCommunityPost.createPostRequestFrom) {
                case AppConstants.CREATE_POST:
                    mTitleToolbar.setText(R.string.title_create_post);
                    break;
                case AppConstants.MENTOR_CREATE_QUESTION:
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

            sendPost();
        }

        return super.onOptionsItemSelected(item);
    }

    private void askFacebookPublishPermission() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithPublishPermissions(CommunityPostActivity.this, Arrays.asList("publish_actions"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        LogUtils.info("dddd", "********success fb login ***********");
                        if(!loginResult.getAccessToken().getPermissions().contains("publish_actions")){
                            hasPermission = false;
                        }else {
                            hasPermission = true;
                        }
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.info("dddd", "********cancel fb login ***********");
                        hasPermission = false;
                        //sendPost();
                        //Toast.makeText(AppIntro.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        LogUtils.info("dddd", "********error fb login ***********");
                        hasPermission = false;
                       // sendPost();
                        //Toast.makeText(AppIntro.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null && accessToken.getPermissions().contains("publish_actions")){
            hasPermission = true;
           // sendPost();
           /* alreadyHasFbPermission = true;
            LoginManager.getInstance().logInWithPublishPermissions(CommunityPostActivity.this, Arrays.asList("publish_actions"));*/
        }else {
            LoginManager.getInstance().logInWithReadPermissions(CommunityPostActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
        }
    }

    public void sendPost(){
        if(mIsChallengePost){
            mCreatePostPresenter.sendChallengePost(AppUtils.createChallengePostRequestBuilder(getCreatorType(),mCommunityPost.challengeId, mCommunityPost.challengeType, mEtDefaultHintText.getText().toString(), getImageUrls(), mLinkRenderResponse));
        }else if (!mIsEditPost) {
            mCreatePostPresenter.sendPost(createCommunityPostRequestBuilder((mCommunityPost.community.id), getCreatorType(), mEtDefaultHintText.getText().toString(), getImageUrls(), (long) 0, mLinkRenderResponse, hasPermission, AccessToken.getCurrentAccessToken().getToken()));
        } else {
            mCreatePostPresenter.editPost(editCommunityPostRequestBuilder(mCommunityPost.community.id, getCreatorType(), mEtDefaultHintText.getText().toString(), newEncodedImages, (long) mCommunityPost.remote_id, deletedImageIdList, mLinkRenderResponse));
        }
    }


    private boolean validateFields() {
        if (!isDirty() && !mIsEditPost) {
            showMessage(R.string.error_blank);
            return false;
        }

        if (mCommunityPost.community == null && !mCommunityPost.isChallengeType) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_post, menu);
        MenuItem menuItem = menu.findItem(R.id.post);
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

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    @Override
    public void onPostSend(FeedDetail feedDetail) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if (mIsEditPost) {
            feedDetail.setItemPosition(mFeedPosition);
            Parcelable parcelable = Parcels.wrap(feedDetail);
            bundle.putParcelable(AppConstants.COMMUNITY_POST_FRAGMENT, parcelable);
        }
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        CommunityPostActivity.this.finish();
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
                if(!mIsAnonymous) {
                    compoundButton.setChecked(isChecked);
                }
                //mIsAnonymous = isChecked;
                //mPostAsCommunitySelected = false;
                setupUserView();
                if(isChecked) {
                    askFacebookPublishPermission();
                } else {
                    hasPermission = false;
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
        mEtDefaultHintText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (StringUtil.isNotNullOrEmptyString(mEtDefaultHintText.getText().toString()) && !isLinkRendered) {
                        String editTextDescription = mEtDefaultHintText.getText().toString().trim();
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

                } else {
                }
            }
        });
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
        if (mCommunityPost.createPostRequestFrom == AppConstants.MENTOR_CREATE_QUESTION) {
            mCommunityName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            String fullString = getString(R.string.ID_ASKING) + AppConstants.SPACE + mCommunityPost.community.name;
            SpannableString SpanString = new SpannableString(fullString);
            SpanString.setSpan(null, 0, getString(R.string.ID_ASKING).length(), 0);
            SpanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.recent_post_comment)), 0, getString(R.string.ID_ASKING).length(), 0);
            mCommunityName.setEnabled(false);
            mCommunityName.setMovementMethod(LinkMovementMethod.getInstance());
            mCommunityName.setText(SpanString, TextView.BufferType.SPANNABLE);
            mCommunityName.setSelected(true);
            mEtDefaultHintText.setHint(getString(R.string.ID_WHAT_IS_QUESTION));
            mEtDefaultHintText.requestFocus();
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
            mUserDropDownView.setVisibility(View.VISIBLE);
        } else {
            mUserDropDownView.setVisibility(View.GONE);
        }
    }

    public boolean isPostModified() {
        return !mOldText.equals(mEtDefaultHintText.getText().toString()) || !CommonUtil.isEmpty(newEncodedImages) || !CommonUtil.isEmpty(deletedImageIdList);
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
        if (isDirty()) {
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
            CommunityPostActivity.this.finish();
        }

    }

    private boolean isDirty() {
        if (mIsEditPost) {
            if (isPostModified()) {
                return true;
            }
        } else {
            if (CommonUtil.isNotEmpty(mEtDefaultHintText.getText().toString().trim()) || !CommonUtil.isEmpty(mImageList)) {
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
        }
    }

    public void linkRenderResponse(LinkRenderResponse linkRenderResponse) {
        if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getStatus())) {
            switch (linkRenderResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    isLinkRendered = true;
                    cardViewLinkRender.setVisibility(View.VISIBLE);
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
        final Drawable upArrow = getResources().getDrawable(R.drawable.vector_back_arrow);
        upArrow.setColorFilter(Color.parseColor(mTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mTitleToolbar.setTextColor(Color.parseColor(mTitleTextColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mPrimaryColor)));
        }
        mToolbar.setBackgroundColor(Color.parseColor(mPrimaryColor));
    }
}
