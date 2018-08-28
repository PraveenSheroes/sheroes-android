package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.annotation.NonNull;
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
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.PollTypeCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.poll.CreatorType;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollOptionRequestModel;
import appliedlife.pvtltd.SHEROES.models.entities.poll.PollType;
import appliedlife.pvtltd.SHEROES.models.entities.post.Community;
import appliedlife.pvtltd.SHEROES.models.entities.post.CommunityPost;
import appliedlife.pvtltd.SHEROES.models.entities.post.MyCommunities;
import appliedlife.pvtltd.SHEROES.models.entities.post.Photo;
import appliedlife.pvtltd.SHEROES.models.entities.post.PollOptionType;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.Mention;
import appliedlife.pvtltd.SHEROES.models.entities.usertagging.SearchUserDataResponse;
import appliedlife.pvtltd.SHEROES.presenters.CreatePostPresenter;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.Mentionable;
import appliedlife.pvtltd.SHEROES.usertagging.suggestions.interfaces.Suggestible;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.QueryToken;
import appliedlife.pvtltd.SHEROES.usertagging.tokenization.interfaces.QueryTokenReceiver;
import appliedlife.pvtltd.SHEROES.usertagging.ui.RichEditorView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.PostPhotoAdapter;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.RippleViewLinear;
import appliedlife.pvtltd.SHEROES.views.fragments.CameraBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.FeedFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PostBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ICommunityPostView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static appliedlife.pvtltd.SHEROES.models.entities.poll.PollType.BOOLEAN;
import static appliedlife.pvtltd.SHEROES.models.entities.poll.PollType.EMOJI;
import static appliedlife.pvtltd.SHEROES.models.entities.poll.PollType.IMAGE;
import static appliedlife.pvtltd.SHEROES.models.entities.poll.PollType.TEXT;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.createChallengePostRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.createCommunityPostRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.editCommunityPostRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.schedulePost;

/**
 * Created by ujjwal on 28/10/17.
 */

public class CommunityPostActivity extends BaseActivity implements ICommunityPostView, QueryTokenReceiver, PollTypeCallBack {
    public static final String SCREEN_LABEL = "Create Communities Post Screen";
    public static final String POSITION_ON_FEED = "POSITION_ON_FEED";
    public static final String IS_FROM_COMMUNITY = "Is from community";
    public static final String IS_FROM_BRANCH = "Is from branch";
    public static final String TYPE_IMAGE = "image/";
    public static final String TYPE_TEXT = "text/plain";
    public static final String TYPE_FILE = "file";
    public static final int MAX_IMAGE = 5;
    private boolean mIsPostScheduled = false;
    private boolean mStatusBarColorEmpty = false;
    private Dialog mScheduledConfirmationDialog;
    private Dialog mPostNowOrLaterDialog;

    //region View variables
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    Preference<Configuration> mConfiguration;

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

    @Bind(R.id.action)
    TextView mAction;

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

    @Bind(R.id.add_image)
    RippleViewLinear mRippleViewLinearAddImage;

    @Bind(R.id.camera)
    RippleViewLinear mRippleViewLinearCamera;

    @Bind(R.id.poll_survey)
    RippleViewLinear mRippleViewLinearPollSurvey;

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

    @Bind(R.id.li_image_upload_view)
    LinearLayout mImageUploadView;

    @Bind(R.id.et_view)
    RichEditorView etView;

    @Bind(R.id.suggestions_list)
    RecyclerView mSuggestionList;

    @Bind(R.id.tv_add_photo_lable)
    TextView tvAddPhotoLable;

    @Bind(R.id.tv_photo_lable)
    TextView tvPhotoLable;

    @Bind(R.id.tv_camera_lable)
    TextView tvCameraLable;

    @Bind(R.id.tv_poll_survey_lable)
    TextView tvPollSurveyLable;

    @Bind(R.id.li_upload_image_container)
    LinearLayout liUploadImageContainer;

    @Bind(R.id.li_poll_container)
    LinearLayout mLiPollContainer;

    @Bind(R.id.li_main_poll_view)
    LinearLayout mLiMainPollView;

    @Bind(R.id.rl_Add_option_poll)
    RelativeLayout rlAddOptionPoll;

    @Bind(R.id.iv_add_poll_img)
    ImageView mAddPollImg;

    @Bind(R.id.tv_add_poll_text)
    TextView mAddPollText;

    @Bind(R.id.rl_image_list)
    RelativeLayout mRlImageList;

    @Bind(R.id.tv_day_selector)
    TextView tvDaySelector;

    @Bind(R.id.rl_main_layout)
    RelativeLayout mRlMainLayout;


    @BindDimen(R.dimen.authorPicSize)
    int mAuthorPicSize;

    @BindDimen(R.dimen.option_poll_margintop)
    int mPollMarginTop;

    @BindDimen(R.dimen.option_poll_margin_left_right)
    int mPollMarginLeftRight;

    @BindDimen(R.dimen.add_icon_left_right)
    int mPhotoCameraPollImageLeftRight;
    @BindDimen(R.dimen.add_icon_top_bottom)
    int mPhotoCameraPollImageTopBottom;

    private int mPollOptionCount;
    //endregion

    //region member variable
    private UserSummary mUserSummary;
    private boolean mIsAnonymous;
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
    private boolean mIsFromBranch;
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
    private String actionDefault = "#dc4541";
    private boolean mHasPermission = false;
    CallbackManager mCallbackManager;
    private boolean mHasMentions = false;
    private String mUserTagCreatePostText;
    private List<MentionSpan> mMentionSpanList;
    //new images and deleted images are send when user edit the post
    private List<String> mNewEncodedImages = new ArrayList<>();
    private List<Long> mDeletedImageIdList = new ArrayList<>();
    private List<Mention> mMentionList;
    private ImageView mIvImagePollLeft, mIvImagePollRight;
    private EditText mEtImagePollLeft, mEtImagePollRight;
    private String mImagePollLeftUrl, mImagePollRightUrl;
    private boolean mIsPollOptionClicked;
    private PollType mPollOptionType;
    private List<EditText> mEtTextPollList = new ArrayList<>();
    private EditText mEtTextPoll;


    //endregion

    //region Activity methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_community_post);
        ButterKnife.bind(this);
        mCreatePostPresenter.attachView(this);
        mUserTagCreatePostText = getString(R.string.user_mention_area_at_post);
        if (getIntent() != null) {
            mFeedPosition = getIntent().getIntExtra(POSITION_ON_FEED, -1);
            mIsFromCommunity = getIntent().getBooleanExtra(IS_FROM_COMMUNITY, false);
            mIsFromBranch = getIntent().getBooleanExtra(IS_FROM_BRANCH, false);

        }
        if (mIsFromBranch) {
            branchUrlHandle();
        } else {
            isSharedFromOtherApp = false;
            if (null != mConfiguration && mConfiguration.isSet() && mConfiguration.get().configData != null) {
                etView.getEditText().setHint(mConfiguration.get().configData.mCreatePostText);
                mUserTagCreatePostText = mConfiguration.get().configData.mUserTagCreatePostInfoText;
            } else {
                etView.getEditText().setHint(new ConfigData().mCreatePostText);
            }
            if (null != getIntent() && getIntent().getExtras() != null) {
                mPrimaryColor = getIntent().getExtras().getString(FeedFragment.PRIMARY_COLOR, mPrimaryColor);
                mTitleTextColor = getIntent().getExtras().getString(FeedFragment.TITLE_TEXT_COLOR, mTitleTextColor);

                if (getIntent().getExtras().getString(FeedFragment.PRIMARY_COLOR) == null) {
                    mStatusBarColorEmpty = true;
                }
                Parcelable parcelable = getIntent().getParcelableExtra(CommunityPost.COMMUNITY_POST_OBJ);
                if (parcelable != null) {
                    mCommunityPost = Parcels.unwrap(parcelable);
                    mIsEditPost = mCommunityPost.isEdit;
                    mIsChallengePost = mCommunityPost.isChallengeType;
                }
            }


            if (mIsChallengePost) {
                fbShareContainer.setVisibility(View.GONE);
                mAnonymousSelect.setVisibility(View.GONE);
                mAnonymousView.setVisibility(View.GONE);
                mCommunityName.setText("Challenge");
                if (mCommunityPost.hasMention) {
                    mMentionSpanList = mCommunityPost.userMentionList;
                    editUserMentionWithFullDescriptionText(mMentionSpanList, " " + "#" + mCommunityPost.challengeHashTag);
                } else {
                    if (CommonUtil.isNotEmpty(mCommunityPost.challengeHashTag)) {
                        etView.setEditText(" " + "#" + mCommunityPost.challengeHashTag, 0);
                    }
                }
            }
            if (mIsEditPost) {
                fbShareContainer.setVisibility(View.GONE);
                mPostAsCommunitySelected = mCommunityPost.isPostByCommunity;
                mIsAnonymous = mCommunityPost.isAnonymous;
                if (mIsAnonymous) {
                    mAnonymousSelect.setChecked(true);
                    mPostAsCommunitySelected = false;
                } else {
                    mAnonymousSelect.setChecked(false);
                }
                mOldText = mCommunityPost.body;
                if (mCommunityPost.hasMention) {
                    mHasMentions = mCommunityPost.hasMention;
                    mMentionSpanList = mCommunityPost.userMentionList;
                    editUserMentionWithFullDescriptionText(mMentionSpanList, mOldText);
                } else {
                    etView.setEditText(mOldText, mCommunityPost.body.length());
                }
                invalidateUserDropDownView();
            } else {
                if (mCommunityPost != null && mCommunityPost.createPostRequestFrom != AppConstants.MENTOR_CREATE_QUESTION) {
                    etView.getEditText().requestFocus();
                    if (!mIsChallengePost) {
                        fbShareContainer.setVisibility(View.VISIBLE);
                    }
                    if (!mIsFromCommunity && !mIsChallengePost) {
                        PostBottomSheetFragment.showDialog(this, SOURCE_SCREEN);
                    }
                }
            }

            setSupportActionBar(mToolbar);
            if (mUserPreference == null) {
                return;
            }

            if (mUserPreference.isSet() && mUserPreference.get().getUserSummary() != null) {
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
            setupCommunityNameListener();
            setupShareToFbListener();
            setupAnonymousSlelectListener();
            setViewByCreatePostCall();
            setupToolbarItemsColor();
            externalImageWithTextShare();
            setupToolBarItem();


        }
        etView.onReceiveSuggestionsListView(mSuggestionList);

        etView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!keyboardShown(etView.getRootView())) {
                    setImageCount();
                    mAnonymousView.setVisibility(View.VISIBLE);
                    bottomSheetExpanded();
                } else {
                    bottomSheetCollapsed();
                }
            }
        });

        mCreatePostPresenter.getUserMentionSuggestion(etView, mCommunityPost);
    }

    private void bottomSheetCollapsed() {
        mImageUploadView.setOrientation(LinearLayout.HORIZONTAL);
        tvPhotoLable.setVisibility(View.GONE);
        tvCameraLable.setVisibility(View.GONE);
        tvPollSurveyLable.setVisibility(View.GONE);
        tvAddPhotoLable.setVisibility(View.VISIBLE);

        LinearLayout.LayoutParams photo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        photo.setMargins(mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom, mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom);
        mRippleViewLinearAddImage.setLayoutParams(photo);
        mRippleViewLinearAddImage.setGravity(Gravity.CENTER);


        LinearLayout.LayoutParams camera = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        camera.setMargins(mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom, mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom);
        mRippleViewLinearCamera.setLayoutParams(camera);

        mRippleViewLinearCamera.setGravity(Gravity.CENTER);


        LinearLayout.LayoutParams pollSurvey = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        pollSurvey.setMargins(mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom, 0, mPhotoCameraPollImageTopBottom);
        mRippleViewLinearPollSurvey.setLayoutParams(pollSurvey);

        mRippleViewLinearPollSurvey.setGravity(Gravity.CENTER);


    }

    private void bottomSheetExpanded() {
        mImageUploadView.setOrientation(LinearLayout.VERTICAL);
        tvPhotoLable.setVisibility(View.VISIBLE);
        tvCameraLable.setVisibility(View.VISIBLE);
        tvPollSurveyLable.setVisibility(View.VISIBLE);
        tvAddPhotoLable.setVisibility(View.GONE);

        LinearLayout.LayoutParams photo = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        photo.setMargins(mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom, mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom);
        mRippleViewLinearAddImage.setLayoutParams(photo);
        mRippleViewLinearAddImage.setGravity(Gravity.START);


        LinearLayout.LayoutParams camera = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        camera.setMargins(mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom, mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom);
        mRippleViewLinearCamera.setLayoutParams(camera);

        mRippleViewLinearCamera.setGravity(Gravity.START);


        LinearLayout.LayoutParams pollSurvey = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        pollSurvey.setMargins(mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom, mPhotoCameraPollImageLeftRight, mPhotoCameraPollImageTopBottom);
        mRippleViewLinearPollSurvey.setLayoutParams(pollSurvey);

        mRippleViewLinearPollSurvey.setGravity(Gravity.START);
    }

    private void editUserMentionWithFullDescriptionText(@NonNull List<MentionSpan> mentionSpanList, String editDescText) {
        if (StringUtil.isNotEmptyCollection(mentionSpanList)) {
            for (int i = 0; i < mentionSpanList.size(); i++) {
                final MentionSpan mentionSpan = mentionSpanList.get(i);
                if (mentionSpan.getDisplayMode() == Mentionable.MentionDisplayMode.PARTIAL) {
                    editDescText = editDescText.replaceFirst(mentionSpan.getMention().getName(), " ");

                } else {
                    editDescText = editDescText.replace(mentionSpan.getDisplayString(), " ");
                }
            }

            etView.getEditText().setText(editDescText);

            for (int i = 0; i < mentionSpanList.size(); i++) {
                final MentionSpan mentionSpan = mentionSpanList.get(i);
                Mention userMention = mentionSpan.getMention();
                int index = userMention.getStartIndex();
                etView.setCreateEditMentionSelectionText(userMention, index, index + 1);
            }
            etView.getEditText().setSelection(etView.getEditText().length());
        }
    }

    private void branchUrlHandle() {
        if (null != getIntent() && getIntent().getExtras() != null) {
            Parcelable parcelable = getIntent().getParcelableExtra(CommunityPost.COMMUNITY_POST_OBJ);
            if (parcelable != null) {
                mCommunityPost = Parcels.unwrap(parcelable);
                mIsChallengePost = mCommunityPost.isChallengeType;
            }
        }
        if (mIsChallengePost) {
            fbShareContainer.setVisibility(View.GONE);
            mAnonymousSelect.setVisibility(View.GONE);
            mAnonymousView.setVisibility(View.GONE);
            mCommunityName.setText("Challenge");
            if (mCommunityPost.hasMention) {
                mHasMentions = true;
                mMentionSpanList = mCommunityPost.userMentionList;
                editUserMentionWithFullDescriptionText(mMentionSpanList, " " + "#" + mCommunityPost.challengeHashTag);
            } else {
                if (CommonUtil.isNotEmpty(mCommunityPost.challengeHashTag)) {
                    etView.setEditText(" " + "#" + mCommunityPost.challengeHashTag, 0);
                }
            }

        } else {
            fbShareContainer.setVisibility(View.GONE);
            mIsAnonymous = mCommunityPost.isAnonymous;
            if (StringUtil.isNotNullOrEmptyString(mCommunityPost.body)) {
                mOldText = mCommunityPost.body;
                if (mCommunityPost.hasMention) {
                    mHasMentions = true;
                    mMentionSpanList = mCommunityPost.userMentionList;
                    editUserMentionWithFullDescriptionText(mMentionSpanList, mOldText);
                } else {
                    mOldText = mCommunityPost.body;
                    etView.setEditText(mOldText, mOldText.length());
                }
            }
        }
        invalidateUserDropDownView();
        if (mCommunityPost != null && mCommunityPost.createPostRequestFrom != AppConstants.MENTOR_CREATE_QUESTION) {
            etView.getEditText().requestFocus();
            if (!mIsChallengePost) {
                fbShareContainer.setVisibility(View.VISIBLE);
            }
        }
        setSupportActionBar(mToolbar);
        if (mUserPreference == null) {
            return;
        }

        if (mUserPreference.isSet() && mUserPreference.get().getUserSummary() != null) {
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
        setupCommunityNameListener();
        setupShareToFbListener();
        setupAnonymousSlelectListener();
        setViewByCreatePostCall();
        setupToolbarItemsColor();
        setupToolBarItem();
    }

    private void externalImageWithTextShare() {
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
                    if (StringUtil.isNotNullOrEmptyString(textLink)) {
                        etView.setEditText(textLink, 0);
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
    }

    @Override
    public boolean shouldTrackScreen() {
        return true;
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        HashMap<String, Object> properties = new
                EventProperty.Builder()
                .isOpenedFromExternalApp(String.valueOf(isSharedFromOtherApp))
                .build();
        return properties;
    }

    private void setupToolBarItem() {
        switch (mCommunityPost.createPostRequestFrom) {
            case AppConstants.CREATE_POST:
                mAction.setText(getResources().getString(R.string.action_post));
                break;
            case AppConstants.MENTOR_CREATE_QUESTION:
                mAction.setText(getResources().getString(R.string.action_mentor_post));
                break;
            default:
                mAction.setText(getResources().getString(R.string.action_post));
                break;
        }
        if (mStatusBarColorEmpty) {
            mAction.setTextColor(Color.parseColor(actionDefault));
        } else {
            mAction.setTextColor(Color.parseColor(mTitleTextColor));
        }

    }

    @Override
    protected boolean trackScreenTime() {
        return true;
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
            etView.setText(sharedText);
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
        return true;
    }

    private void askFacebookPublishPermission() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithPublishPermissions(CommunityPostActivity.this, Arrays.asList("publish_actions"));
        LoginManager.getInstance().registerCallback(mCallbackManager,
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

        mMentionSpanList = etView.getMentionSpans();
        addMentionSpanDetail();
        if (mIsChallengePost) {
            mCreatePostPresenter.sendChallengePost(createChallengePostRequestBuilder(getCreatorType(), mCommunityPost.challengeId, mCommunityPost.challengeType, etView.getEditText().getText().toString(), getImageUrls(), mLinkRenderResponse, mHasMentions, mMentionSpanList));
        } else if (!mIsEditPost) {
            String accessToken = "";
            if (AccessToken.getCurrentAccessToken() != null) {
                accessToken = AccessToken.getCurrentAccessToken().getToken();
            }
            mCreatePostPresenter.sendPost(createCommunityPostRequestBuilder(mCommunityPost.community.id, getCreatorType(), etView.getEditText().getText().toString(), getImageUrls(), (long) 0, mLinkRenderResponse, mHasPermission, accessToken, mHasMentions, mMentionSpanList), isSharedFromOtherApp);

        } else {
            if (mCommunityPost != null) {
                mCreatePostPresenter.editPost(editCommunityPostRequestBuilder(mCommunityPost.community.id, getCreatorType(), etView.getEditText().getText().toString(), mNewEncodedImages, (long) mCommunityPost.remote_id, mDeletedImageIdList, mLinkRenderResponse, mHasMentions, mMentionSpanList));
            }
        }
    }

    @Override
    public void showImage(final String imageUrl) {

        if (StringUtil.isNotNullOrEmptyString(imageUrl)) {
            int width = CommonUtil.getWindowWidth(this);
            int imageHeight = CommonUtil.getWindowHeight(this);
            String finalImageUrl = CommonUtil.getThumborUriWithFit(imageUrl, width, imageHeight);
            Glide.with(this)
                    .asBitmap()
                    .load(finalImageUrl)
                    .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap bitmap, Transition<? super Bitmap> transition) {

                            if (mIvImagePollLeft != null && mIvImagePollRight != null) {
                                // mLiMainPollView.setVisibility(View.VISIBLE);
                                // mRlImageList.setVisibility(View.GONE);
                                if ((Boolean) mIvImagePollLeft.getTag()) {
                                    mImagePollLeftUrl = imageUrl;
                                    mIvImagePollLeft.setImageBitmap(bitmap);
                                } else if ((Boolean) mIvImagePollRight.getTag()) {
                                    mIvImagePollRight.setImageBitmap(bitmap);
                                    mImagePollRightUrl = imageUrl;
                                }
                            }
                        }
                    });
        }
    }

    private void addMentionSpanDetail() {
        for (MentionSpan mentionSpan : mMentionSpanList) {
            Mention mention = mentionSpan.getMention();
            Editable editable = etView.getEditText().getEditableText();
            mention.setStartIndex(editable.getSpanStart(mentionSpan));
            mention.setEndIndex(editable.getSpanEnd(mentionSpan));
            mention.setName(mentionSpan.getDisplayString());
            mentionSpan.setMention(mention);
        }
        Collections.sort(mMentionSpanList, new Comparator<MentionSpan>() {
            @Override
            public int compare(MentionSpan span1, MentionSpan span2) {
                return span1.getMention().getStartIndex() - span2.getMention().getStartIndex();
            }
        });
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
        mMentionSpanList = etView.getMentionSpans();
        addMentionSpanDetail();
        String accessToken = "";
        if (AccessToken.getCurrentAccessToken() != null) {
            accessToken = AccessToken.getCurrentAccessToken().getToken();
        }
        mCreatePostPresenter.sendPost(schedulePost(mCommunityPost.community.id, getCreatorType(), etView.getEditText().getText().toString(), getImageUrls(), (long) 0, mLinkRenderResponse, mHasPermission, accessToken, scheduledTime, mHasMentions, mMentionSpanList), isSharedFromOtherApp);
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
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, intent);
        }
        /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                    if (resultCode == RESULT_OK) {
                        try {
                            Photo photo = new Photo();
                            photo.isNew = true;
                            File file = new File(result.getUri().getPath());
                            photo.file = file;
                            if (mIsEditPost) {
                                Bitmap bitmap = decodeFile(photo.file);
                                byte[] buffer = new byte[4096];
                                if (null != bitmap) {
                                    buffer = getBytesFromBitmap(bitmap);
                                    if (null != buffer) {
                                        String encodedImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                                        if (StringUtil.isNotNullOrEmptyString(encodedImage)) {
                                            mNewEncodedImages.add(encodedImage);
                                        }
                                    }
                                }
                            }
                            if (mIvImagePollLeft != null && mIvImagePollRight != null) {
                                Bitmap bitmap = decodeFile(photo.file);
                                startProgressBar();
                                mCreatePostPresenter.uploadFile(CompressImageUtil.setImageOnHolder(bitmap));
                            } else {
                                postPicsAndCountView(photo);
                            }
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

    private void postPicsAndCountView(Photo photo) {
        mImageList.add(photo);
        mLiMainPollView.setVisibility(View.GONE);
        mRlImageList.setVisibility(View.VISIBLE);
        setImageCount();
        mPostPhotoAdapter.addPhoto(photo);
        stopProgressBar();
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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        super.onShowErrorDialog(s, feedParticipationEnum);
    }

    protected final void scheduleConfirmation(final String formattedDate, String formattedMessage) {

        if (mScheduledConfirmationDialog != null) {
            mScheduledConfirmationDialog.dismiss();
        }

        mScheduledConfirmationDialog = new Dialog(CommunityPostActivity.this);
        mScheduledConfirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mScheduledConfirmationDialog.setCancelable(false);
        mScheduledConfirmationDialog.setContentView(R.layout.dialog_schedule_post_confirmation);

        TextView messageText = mScheduledConfirmationDialog.findViewById(R.id.message);
        String scheduledMessage = getResources().getString(R.string.post_schedule_message, mCommunityPost.community.name, formattedMessage);
        Spanned message = StringUtil.fromHtml(scheduledMessage);
        messageText.setText(message);
        TextView confirmedOk = mScheduledConfirmationDialog.findViewById(R.id.ok);
        confirmedOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postForAdmin(formattedDate);
                mScheduledConfirmationDialog.dismiss();
            }
        });

        TextView editPost = mScheduledConfirmationDialog.findViewById(R.id.edit);
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
            return CreatorType.COMMUNITY_OWNER.toString();
        } else if (mIsAnonymous) {
            return CreatorType.ANONYMOUS.toString();
        } else {
            return CreatorType.USER.toString();
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

    private void setupCommunityNameListener() {
        mCommunityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSuggestionList.setVisibility(View.GONE);
                if (!mIsEditPost && !mIsFromCommunity) {
                    PostBottomSheetFragment.showDialog(CommunityPostActivity.this, SOURCE_SCREEN, mMyCommunities);
                }
            }
        });
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream);
        return stream.toByteArray();
    }

    //Disable the link editing and enable for others
    private void disableEditTextForLinks() {
        if (isLinkRendered) {
            etView.getEditText().setFocusable(false);
            etView.getEditText().setFocusableInTouchMode(false);
            etView.getEditText().setClickable(false);
        }
    }

    private Bitmap decodeFile(File file) {
        try {
            // decode image size
            if (file == null) return null;
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, o);

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
            return BitmapFactory.decodeStream(new FileInputStream(file), null, o2);
        } catch (FileNotFoundException e) {
            Crashlytics.getInstance().core.logException(e);
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
            etView.getEditText().setHint(getString(R.string.ID_WHAT_IS_QUESTION));
            etView.getEditText().requestFocus();
        } else {
            if (mIsChallengePost) {
                mCommunityName.setVisibility(View.VISIBLE);
                mCommunityName.setText("Challenge");
                mCommunityName.setEnabled(false);
            } else {
                if (mCommunityPost != null && mCommunityPost.community != null)
                    mCommunityName.setText(mCommunityPost.community.name);
            }
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
        return !mOldText.equals(etView.getEditText().getText().toString()) || !CommonUtil.isEmpty(mNewEncodedImages) || !CommonUtil.isEmpty(mDeletedImageIdList);

        // return !mOldText.equals(mAllText) || !CommonUtil.isEmpty(mNewEncodedImages) || !CommonUtil.isEmpty(mDeletedImageIdList);
    }

    public static void navigateTo(Activity fromActivity, FeedDetail feedDetail, int requestCodeForCommunityPost, HashMap<String, Object> properties) {
        Intent intent = new Intent(fromActivity, CommunityPostActivity.class);
        UserPostSolrObj userPostObj = (UserPostSolrObj) feedDetail;
        if (feedDetail != null) {
            CommunityPost communityPost = new CommunityPost();
            communityPost.remote_id = (int) userPostObj.getIdOfEntityOrParticipant();
            communityPost.community = new Community();
            communityPost.community.id = userPostObj.getCommunityId();//userPostObj.getCommunityTypeId();
            communityPost.body = userPostObj.getListDescription();
            communityPost.community.name = userPostObj.getPostCommunityName();
            communityPost.community.isOwner = userPostObj.isCommunityOwner();
            communityPost.isMyPost = userPostObj.isCommunityOwner();
            communityPost.community.thumbImageUrl = userPostObj.getSolrIgnorePostCommunityLogo();
            communityPost.isAnonymous = userPostObj.isAnonymous();
            communityPost.isEdit = true;
            communityPost.isPostByCommunity = userPostObj.isCommunityPost();
            if (userPostObj.isHasMention()) {
                communityPost.hasMention = userPostObj.isHasMention();
                communityPost.userMentionList = userPostObj.getUserMentionList();
            }
            if (userPostObj.getCommunityId() != null && userPostObj.getCommunityId() == 0) {
                communityPost.isChallengeType = true;
            }
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
            communityPost.community.id = userPostObj.getCommunityId();//userPostObj.getCommunityTypeId();
            communityPost.body = userPostObj.getListDescription();
            communityPost.community.name = userPostObj.getPostCommunityName();
            communityPost.community.isOwner = userPostObj.isCommunityOwner();
            communityPost.isMyPost = userPostObj.isCommunityOwner();
            if (userPostObj.getCommunityId() != null && userPostObj.getCommunityId() == 0) {
                communityPost.isChallengeType = true;
            }
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
            if (userPostObj.isHasMention()) {
                communityPost.hasMention = userPostObj.isHasMention();
                communityPost.userMentionList = userPostObj.getUserMentionList();
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

    public static void navigateTo(Activity fromActivity, CommunityPost communityPost, int requestCode, HashMap<String, Object> properties, boolean isFromBranch) {
        Intent intent = new Intent(fromActivity, CommunityPostActivity.class);
        Parcelable parcelable = Parcels.wrap(communityPost);
        intent.putExtra(CommunityPost.COMMUNITY_POST_OBJ, parcelable);
        intent.putExtra(IS_FROM_BRANCH, isFromBranch);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

    private void onBackPress() {
        if (isDirty()) {
            confirmationAlert();
        } else {
            navigateToParentActivity();
        }
    }


    private void confirmationAlert() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(CommunityPostActivity.this);
        builder.setTitle("Discard Post?");
        builder.setMessage("Are you sure you want to discard your changes?");
        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (isSharedFromOtherApp) {
                    navigateToParentActivity();
                } else {
                    CommunityPostActivity.this.finish();
                }
            }
        });

        builder.create();
        builder.show();
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
            return isPostModified();
        } else {
            return CommonUtil.isNotEmpty(etView.getEditText().getText().toString().trim()) || !CommonUtil.isEmpty(mImageList);
        }
    }

    private void setImageCount() {
        mImageCount.setText(getString(R.string.image_count, mImageList.size(), MAX_IMAGE));
        if (mImageList.size() == MAX_IMAGE) {
            mImageUploadView.setVisibility(View.GONE);
        } else {
            mImageUploadView.setVisibility(View.VISIBLE);
        }
        if (mImageList.size() > 0) {
            mImageCount.setVisibility(View.VISIBLE);
        } else {
            mImageCount.setVisibility(View.GONE);
        }
    }

    private void setupImageListView() {
        mPostPhotoAdapter = new PostPhotoAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View recyclerViewItem = (View) view.getParent();
                int position = mImageListView.getChildAdapterPosition(recyclerViewItem);
                if (position == -1) return;
                Photo photo = mImageList.get(position);
                if (mIsEditPost && !photo.isNew) {
                    mDeletedImageIdList.add((long) photo.remote_id);
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
            mCommunityName.setVisibility(View.VISIBLE);
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
                String authorImage = CommonUtil.getThumborUri(mUserSummary.getPhotoUrl(), mAuthorPicSize, mAuthorPicSize);
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
                    break;
                default:
            }
        }
    }

    @Override
    public void showUserMentionSuggestionResponse(SearchUserDataResponse searchUserDataResponse, QueryToken queryToken) {
        if (StringUtil.isNotEmptyCollection(mMentionList)) {
            if (StringUtil.isNotEmptyCollection(searchUserDataResponse.getParticipantList())) {
                mMentionList = searchUserDataResponse.getParticipantList();
                mMentionList.add(0, new Mention(AppConstants.USER_MENTION_HEADER, mUserTagCreatePostText, "", "", 0));
                etView.notifyData(mMentionList);
            } else {
                List<Mention> mentionList = new ArrayList<>();
                mentionList.add(0, new Mention(AppConstants.USER_MENTION_HEADER, mUserTagCreatePostText, "", "", 0));
                mentionList.add(1, new Mention(AppConstants.USER_MENTION_NO_RESULT_FOUND, "", "", "", 0));
                etView.notifyData(mentionList);
            }
        }
    }


    @Override
    public void finishActivity() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        navigateToParentActivity();
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
    public void onAddImageClick() {
        mRippleViewLinearAddImage.setOnRippleCompleteListener(new RippleViewLinear.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleViewLinear rippleView) {
                selectImageFrmGallery();
            }
        });

    }

    public void selectImageFrmGallery() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE).setRequestedSize(1200, 1200).setFixAspectRatio(true)
                .start(CommunityPostActivity.this);
    }

    @OnClick(R.id.camera)
    public void onCameraClick() {
        mRippleViewLinearCamera.setOnRippleCompleteListener(new RippleViewLinear.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleViewLinear rippleView) {
                selectImageFrmCamera();
            }
        });

    }

    public void selectImageFrmCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(1200, 1200).setFixAspectRatio(true)
                .start(CommunityPostActivity.this);
    }

    @OnClick(R.id.poll_survey)
    void onPollClick() {
        mRippleViewLinearPollSurvey.setOnRippleCompleteListener(new RippleViewLinear.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleViewLinear rippleView) {
                mSuggestionList.setVisibility(View.GONE);
                List<PollOptionType> pollOptionTypeList = new ArrayList<>();

                PollOptionType textPoll = new PollOptionType();
                textPoll.pollType = TEXT;
                textPoll.id = 1;
                textPoll.title = "Text Poll";
                textPoll.imgUrl = R.drawable.vector_text_poll;
                pollOptionTypeList.add(textPoll);

                PollOptionType imagePoll = new PollOptionType();
                imagePoll.pollType = IMAGE;
                imagePoll.id = 2;
                imagePoll.title = "Image Poll";
                imagePoll.imgUrl = R.drawable.vector_image_poll_icon;
                pollOptionTypeList.add(imagePoll);
                AnalyticsManager.trackEvent(Event.POLL_CLICKED, getScreenName(), null);
                PostBottomSheetFragment.showDialog(CommunityPostActivity.this, SOURCE_SCREEN, pollOptionTypeList);
            }
        });

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

    @OnTouch({R.id.tv_add_photo_lable, R.id.li_image_upload_view})
    public boolean onAddPhotoClicked() {
        bottomSheetExpanded();
        CommonUtil.hideKeyboard(this);
        return true;
    }

    @OnClick({R.id.iv_add_poll_img, R.id.tv_add_poll_text})
    public void onAddMoreOptionClicked() {
        if (mPollOptionCount < 8) {
            mPollOptionCount++;
            addTextPollOptionView();
        }
        addOptionButtonView();
    }

    @OnClick(R.id.tv_day_selector)
    public void onTvDaySelectorClicked() {
        addPollSelectionDay();
    }


    //endregion

    private void setupToolbarItemsColor() {
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
        if (upArrow != null) {
            upArrow.setColorFilter(Color.parseColor(mTitleTextColor), PorterDuff.Mode.SRC_ATOP);
        }
        mTitleToolbar.setTextColor(Color.parseColor(mTitleTextColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mStatusBarColorEmpty) {
                if (upArrow != null) {
                    upArrow.setColorFilter(Color.parseColor(mTitleTextColor), PorterDuff.Mode.SRC_ATOP);
                }
                getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mStatusBarColor)));
            } else {
                getWindow().setStatusBarColor(CommonUtil.colorBurn(Color.parseColor(mPrimaryColor)));
            }
        }

        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        mToolbar.setBackgroundColor(Color.parseColor(mPrimaryColor));
    }

    @OnClick(R.id.action)
    public void onPostClicked() {
        if (!validateFields()) {
            return;
        }
        if (mCommunityPost == null) {
            finish();
            return;
        }
        if (mIsProgressBarVisible) {
            return;
        }
        if (mIsPollOptionClicked) {
            createPoll();
        } else {
            if ((!mIsEditPost && !mIsChallengePost) && (mIsCompanyAdmin || mCommunityPost.isMyPost)) {
                selectPostNowOrLater();
            } else {
                sendPost();
            }
        }
    }

    private void createPoll() {
        List<PollOptionRequestModel> pollOptionModelList = new ArrayList<>();
        PollType pollType = TEXT;
        if (mPollOptionType != null) {
            switch (mPollOptionType) {
                case TEXT:
                    for (int i = 0; i < mEtTextPollList.size(); i++) {
                        PollOptionRequestModel imagePollOptionModel = new PollOptionRequestModel();
                        imagePollOptionModel.setActive(true);
                        if (StringUtil.isNotNullOrEmptyString(mEtTextPollList.get(i).getText().toString())) {
                            imagePollOptionModel.setDescription(mEtTextPollList.get(i).getText().toString());
                        } else {
                            Snackbar.make(mRlMainLayout, getString(R.string.option_empty), Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        pollOptionModelList.add(imagePollOptionModel);
                    }
                    pollType = TEXT;
                    break;
                case IMAGE:
                    PollOptionRequestModel imagePollOptionModelLeft = new PollOptionRequestModel();
                    imagePollOptionModelLeft.setActive(true);
                    imagePollOptionModelLeft.setImageUrl(mImagePollLeftUrl);
                    if (StringUtil.isNotNullOrEmptyString(mEtImagePollLeft.getText().toString())) {
                        imagePollOptionModelLeft.setDescription(mEtImagePollLeft.getText().toString());
                    } else {
                        Snackbar.make(mRlMainLayout, getString(R.string.option_empty), Snackbar.LENGTH_SHORT).show();
                        return;
                    }


                    PollOptionRequestModel imagePollOptionModelRight = new PollOptionRequestModel();
                    imagePollOptionModelRight.setActive(true);
                    imagePollOptionModelRight.setImageUrl(mImagePollRightUrl);
                    if (StringUtil.isNotNullOrEmptyString(mEtImagePollRight.getText().toString())) {
                        imagePollOptionModelRight.setDescription(mEtImagePollRight.getText().toString());
                    } else {
                        Snackbar.make(mRlMainLayout, getString(R.string.option_empty), Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    pollOptionModelList.add(imagePollOptionModelLeft);
                    pollOptionModelList.add(imagePollOptionModelRight);
                    pollType = IMAGE;
                    break;
                case EMOJI:
                    pollType = EMOJI;
                    break;
                case BOOLEAN:
                    pollType = BOOLEAN;
                    break;
            }
        }
        String startDate = DateUtil.getDateFromMillisecondsWithFormat(System.currentTimeMillis(), AppConstants.DATE_FORMAT);
        String endDate = DateUtil.getDateForAddedDays((int) tvDaySelector.getTag());
        mCreatePostPresenter.createPoll(mAppUtils.createPollRequestBuilder(mCommunityPost.community.id, getCreatorType(), pollType, etView.getEditText().getText().toString(), pollOptionModelList, startDate, endDate));
    }

    private boolean keyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    @Override
    public List<String> onQueryReceived(@NonNull final QueryToken queryToken) {
        final String searchText = queryToken.getTokenString();
        if (searchText.contains("@")) {
            List<Mention> mentionList = new ArrayList<>();
            mentionList.add(0, new Mention(AppConstants.USER_MENTION_HEADER, mUserTagCreatePostText, "", "", 0));
            mentionList.add(1, new Mention(AppConstants.USER_MENTION_NO_RESULT_FOUND, getString(R.string.searching), "", "", 0));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mSuggestionList.setLayoutManager(layoutManager);
            mSuggestionList.setAdapter(etView.notifyAdapterOnData(mentionList));
            mMentionList = mentionList;
        }
        List<String> buckets = Collections.singletonList("user-history");
        return buckets;
    }

    @Override
    public Suggestible onMentionUserSuggestionClick(@NonNull Suggestible suggestible, View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_social_user:
                mMentionList.clear();
                etView.displayHide();
                Mention mention = (Mention) suggestible;
                etView.setInsertion(mention);
                final HashMap<String, Object> properties =
                        new EventProperty.Builder()
                                .postId(Integer.toString(mCommunityPost.remote_id))
                                .taggedIn("POST")
                                .taggedUserId(Integer.toString(mention.getUserId()))
                                .build();
                AnalyticsManager.trackEvent(Event.USER_TAGGED, getScreenName(), properties);
                break;
            default:
        }

        return null;
    }

    @Override
    public void textChangeListner(@NonNull Editable editText) {

        if (editText.length() > 0) {
            if (StringUtil.isNotNullOrEmptyString(editText.toString()) && !isLinkRendered) {
                String editTextDescription = editText.toString().trim();
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
                if (editTextDescription.contains("@")) {
                    mAnonymousView.setVisibility(View.GONE);
                    mImageUploadView.setVisibility(View.GONE);
                } else {
                    mAnonymousView.setVisibility(View.VISIBLE);
                    setImageCount();
                }
            }
            mAction.setAlpha(1f);
            etView.getEditText().setCursorVisible(true);
        }else
        {
            mAction.setAlpha(.9f);
        }

    }


    @Override
    public void onPollTypeClicked(PollType pollType) {
        mPollOptionType = pollType;
        mIsPollOptionClicked = true;
        mImageList.clear();
        etView.getEditText().setCursorVisible(false);
        mTitleToolbar.setText(R.string.title_create_poll);
        etView.getEditText().setHint(getString(R.string.ID_ASK_QUESTION));
        mLiMainPollView.setVisibility(View.VISIBLE);
        fbShareContainer.setVisibility(View.GONE);
        mRlImageList.setVisibility(View.GONE);
        liUploadImageContainer.setVisibility(View.GONE);
        String[] pollTime = getResources().getStringArray(R.array.poll_time);
        int[] pollDaysCount = getResources().getIntArray(R.array.poll_days_count);
        switch (pollType) {
            case TEXT:
                for (int i = 0; i <= 1; i++) {
                    mPollOptionCount++;
                    addTextPollOptionView();
                }
                tvDaySelector.setText(pollTime[0]);
                tvDaySelector.setTag(pollDaysCount[0]);
                break;
            case IMAGE:
                addImagePollView();
                tvDaySelector.setText(pollTime[0]);
                tvDaySelector.setTag(pollDaysCount[0]);
                break;
            case EMOJI:
                break;
            case BOOLEAN:
                break;
        }
    }

    private void addPollSelectionDay() {
        String[] pollTime = getResources().getStringArray(R.array.poll_time);
        int[] pollDaysCount = getResources().getIntArray(R.array.poll_days_count);
        PopupMenu popup = new PopupMenu(this, tvDaySelector);
        for (int i = 1; i <= pollTime.length; i++) {
            popup.getMenu().add(0, pollDaysCount[i - 1], i, menuWithText(pollTime[i - 1]));
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                tvDaySelector.setText(item.getTitle());
                tvDaySelector.setTag(item.getItemId());
                return true;
            }
        });
        popup.show();

    }

    private CharSequence menuWithText(String title) {
        SpannableString spannableString = new SpannableString(title);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.footer_icon_text)), 0, spannableString.length(), 0);
        return spannableString;
    }

    private void addTextPollOptionView() {
        final View pollLayout = LayoutInflater.from(this).inflate(R.layout.poll_option_type_layout, null);
        final LinearLayout liTextPollRow = pollLayout.findViewById(R.id.li_text_poll_row);
        mEtTextPoll = pollLayout.findViewById(R.id.et_text_poll);
        mEtTextPoll.setHint(getString(R.string.poll_option) + mPollOptionCount);
        if (mPollOptionCount > 2) {
            final ImageView ivDeleteTextPoll = pollLayout.findViewById(R.id.iv_delete_text_poll);
            ivDeleteTextPoll.setVisibility(View.VISIBLE);
            ivDeleteTextPoll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mLiPollContainer.removeView(pollLayout);
                    mEtTextPollList.clear();
                    for (int i = 0; i < mLiPollContainer.getChildCount(); i++) {
                        // mEtTextPollList.get(i).setHint(mEtTextPollList.get(i).getHint());
                        View pollLayout = mLiPollContainer.getChildAt(i);
                        EditText editText = pollLayout.findViewById(R.id.et_text_poll);
                        int count = i + 1;
                        editText.setHint(getString(R.string.poll_option) + count);
                        mEtTextPollList.add(editText);
                    }
                    mPollOptionCount--;
                    addOptionButtonView();
                }
            });
        }
        mEtTextPollList.add(mEtTextPoll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        params.setMargins(mPollMarginLeftRight, mPollMarginTop, mPollMarginLeftRight, mPollMarginTop);
        liTextPollRow.setLayoutParams(params);
        mLiPollContainer.addView(liTextPollRow);
    }

    private void addOptionButtonView() {
        if (mPollOptionCount <= 7) {
            mAddPollImg.setVisibility(View.VISIBLE);
            mAddPollText.setVisibility(View.VISIBLE);
        } else {
            mAddPollImg.setVisibility(View.GONE);
            mAddPollText.setVisibility(View.GONE);
        }
    }

    private void addImagePollView() {
        mAddPollImg.setVisibility(View.GONE);
        mAddPollText.setVisibility(View.GONE);
        final View pollLayout = LayoutInflater.from(this).inflate(R.layout.poll_image_view_layout, null);
        final LinearLayout liImagePollRow = pollLayout.findViewById(R.id.li_image_poll_view);
        mEtImagePollLeft = pollLayout.findViewById(R.id.et_image_poll_left);
        mEtImagePollLeft.setHint(getString(R.string.poll_option) + 1);
        mEtImagePollRight = pollLayout.findViewById(R.id.et_image_poll_right);
        mEtImagePollRight.setHint(getString(R.string.poll_option) + 2);
        mIvImagePollLeft = pollLayout.findViewById(R.id.iv_image_poll_left);
        mIvImagePollLeft.setTag(false);
        mIvImagePollLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIvImagePollLeft.setTag(true);
                mIvImagePollRight.setTag(false);
                CameraBottomSheetFragment.showDialog(CommunityPostActivity.this, SCREEN_LABEL);
            }
        });
        mIvImagePollRight = pollLayout.findViewById(R.id.iv_image_poll_right);
        mIvImagePollRight.setTag(false);
        mIvImagePollRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIvImagePollRight.setTag(true);
                mIvImagePollLeft.setTag(false);
                CameraBottomSheetFragment.showDialog(CommunityPostActivity.this, SCREEN_LABEL);
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //Layout params for Button
        params.setMargins(mPollMarginLeftRight, mPollMarginTop, mPollMarginLeftRight, mPollMarginTop);
        liImagePollRow.setLayoutParams(params);
        mLiPollContainer.addView(liImagePollRow);
    }
}
