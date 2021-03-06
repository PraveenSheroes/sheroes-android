package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView;

import org.parceler.Parcels;
import org.wordpress.android.editor.EditorFragment;
import org.wordpress.android.editor.EditorFragmentAbstract;
import org.wordpress.android.util.helpers.MediaFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.MixpanelHelper;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.AppConfiguration;
import appliedlife.pvtltd.SHEROES.models.ConfigData;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleTagName;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.presenters.ArticleSubmissionPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.vernacular.LocaleManager;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ContactsCompletionView;
import appliedlife.pvtltd.SHEROES.views.fragments.CameraBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleSubmissionView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 06/09/17.
 */


public class CreateStoryActivity extends BaseActivity implements IArticleSubmissionView, EditorFragmentAbstract.EditorFragmentListener, EditorFragmentAbstract.EditorDragAndDropListener, TokenCompleteTextView.TokenListener<ArticleTagName> {
    //region constants
    public static final String SCREEN_LABEL = "Create Story Screen";
    public static final String SCREEN_LABEL_SUBMIT_STORY = "Submit Story Screen";
    //endregion

    //region inject
    @Inject
    AppUtils mAppUtils;
    @Inject
    ArticleSubmissionPresenterImpl mArticleSubmissionPresenter;
    @Inject
    Preference<AppConfiguration> mConfiguration;
    @Inject
    Preference<LoginResponse> mUserPreference;
    //endregion

    //region view variables
    @Bind(R.id.title_toolbar)
    TextView toolbarTitle;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.body)
    TextView mBody;

    @Bind(R.id.guideline_container)
    RelativeLayout mGuidelineContainer;

    @Bind(R.id.guideline_close)
    ImageView mGuidelineClose;

    @Bind(R.id.editor_container)
    RelativeLayout mEditorContainer;

    @Bind(R.id.article_post_final_container)
    RelativeLayout mArticleNextPageContainer;

    @Bind(R.id.share_on_fb_switch)
    SwitchCompat mShareToFacebook;

    @Bind(R.id.add_photo_image_view)
    ImageView ivAddPhoto;

    @Bind(R.id.add_cover_text_view)
    TextView tvAddCover;

    @Bind(R.id.tv_tag_lable)
    TextView tvTagLable;

    @Bind(R.id.iv_close_img)
    ImageView ivCloseImg;


    //endregion

    //region member variables
    private EditorFragmentAbstract mEditorFragment;
    private boolean mIsGuidelineVisible;
    private File mLocalImageSaveForChallenge;
    private Uri mImageCaptureUri;
    private String mEncodeImageUrl;
    private boolean mIsNextPage;
    private ContactsCompletionView mCompletionView;
    private ArrayAdapter<ArticleTagName> mAdapter;
    private boolean mIsCoverPhoto;
    private String mCoverImageUrl;
    private List<Long> mDeletedTagsList = new ArrayList<>();
    private ArticleSolrObj mArticleSolrObj = null;
    private Long mIdOfEntityOrParticipantArticle;
    private List<ArticleTagName> mArticleTagNameList = new ArrayList<>();
    private String mSourceScreen, mStoryTagText, mHerStoryBodyMsg, mHerStoryTitle;
    private Toast mMyToast;
    //endregion

    //region activity methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        String articleGuideline;
        if (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null) {
            articleGuideline = mConfiguration.get().configData.articleGuideline;
            mStoryTagText = mConfiguration.get().configData.mWriteStoryTag;
            mHerStoryTitle = mConfiguration.get().configData.mHerStoryHintText;
            mHerStoryBodyMsg = mConfiguration.get().configData.mHerStoryTitle;
        } else {
            articleGuideline = new ConfigData().articleGuideline;
            mStoryTagText = new ConfigData().mWriteStoryTag;
            mHerStoryTitle = new ConfigData().mHerStoryHintText;
            mHerStoryBodyMsg = new ConfigData().mHerStoryTitle;
        }
        setContentView(R.layout.activity_article_submission);
        ButterKnife.bind(this);
        LocaleManager.setLocale(this);
        mArticleSubmissionPresenter.attachView(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Parcelable parcelable = getIntent().getParcelableExtra(ArticleSolrObj.ARTICLE_OBJ);
            if (parcelable != null) {
                mArticleSolrObj = Parcels.unwrap(parcelable);
            }
            mSourceScreen = getIntent().getExtras().getString(BaseActivity.SOURCE_SCREEN);
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(R.string.write_a_story);
        invalidateToolBar();
        this.mLocalImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
        mBody.setText(Html.fromHtml(articleGuideline));
        if (CommonUtil.ensureFirstTime(AppConstants.GUIDELINE_SHARE_PREF)) {
            showGuideLineView();
        }
        if (mArticleSolrObj != null) {
            setupEditArticleView(mArticleSolrObj);
        }
        mArticleNextPageContainer.setVisibility(View.GONE);
        mEditorContainer.setVisibility(View.VISIBLE);
        setupShareToFbListener();
        mArticleSubmissionPresenter.getArticleTags();
        mMyToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);
        AnalyticsManager.trackScreenView(SCREEN_LABEL, mSourceScreen, null);
    }

    @Override
    public void articleSubmitResponse(ArticleSolrObj articleSolrObj, boolean isStoryPost) {
        boolean isMentor = false;
        if (mUserPreference.get().getUserSummary().getUserBO().getUserTypeId() == AppConstants.CHAMPION_TYPE_ID) {
            isMentor = true;
        }
        ProfileActivity.navigateTo(this, articleSolrObj.getCreatedBy(), isMentor, -1, SCREEN_LABEL, null, AppConstants.REQUEST_CODE_FOR_PROFILE_DETAIL, true);
        finish();
        if (!isStoryPost) {
            HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, SCREEN_LABEL);
            AnalyticsManager.trackEvent(Event.STORY_DRAFT_SAVED, SCREEN_LABEL, properties);
        } else {
            AnalyticsManager.trackScreenView(SCREEN_LABEL_SUBMIT_STORY, SCREEN_LABEL, null);
            HashMap<String, Object> properties = MixpanelHelper.getArticleOrStoryProperties(articleSolrObj, SCREEN_LABEL);
            AnalyticsManager.trackEvent(Event.STORY_CREATED, SCREEN_LABEL_SUBMIT_STORY, properties);
        }
    }

    @Override
    public void getArticleTagList(List<ArticleTagName> articleTagNameList, boolean isEdit) {
        mArticleTagNameList = articleTagNameList;
        mCompletionView = findViewById(R.id.tag_search_view);
        mCompletionView.setHint(mStoryTagText);
        if (StringUtil.isNotEmptyCollection(articleTagNameList)) {
            if (isEdit) {
                for (ArticleTagName articleTagName : articleTagNameList) {
                    mCompletionView.addObject(articleTagName);
                }
            } else {
                mAdapter = new FilteredArrayAdapter<ArticleTagName>(this, R.layout.article_tag_layout, articleTagNameList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {

                            LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                            convertView = l.inflate(R.layout.article_tag_layout, parent, false);
                        }

                        ArticleTagName p = getItem(position);
                        ((TextView) convertView.findViewById(R.id.tv_article_tag)).setText(p.getTagName());
                        return convertView;
                    }

                    @Override
                    protected boolean keepObject(ArticleTagName articleTagName, String mask) {
                        mask = mask.toLowerCase();
                        return articleTagName.getTagName().toLowerCase().startsWith(mask);
                    }
                };
            }
            mCompletionView.setAdapter(mAdapter);
        }
        mCompletionView.setTokenListener(this);
        mCompletionView.allowDuplicates(false);
        mCompletionView.setTokenLimit(5);
        mCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public boolean shouldTrackScreen() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_submission, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setUpOptionMenuStates(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void setUpOptionMenuStates(Menu menu) {
        MenuItem itemNext = menu.findItem(R.id.next_article_submit);
        MenuItem itemPost = menu.findItem(R.id.post_article_submit);
        if (mIsNextPage) {
            itemNext.setVisible(false);
            itemPost.setVisible(true);
        } else {
            itemNext.setVisible(true);
            itemPost.setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (mIsGuidelineVisible) {
            mGuidelineClose.performClick();
        } else {
            onBackPress();
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mArticleSubmissionPresenter;
    }

    public void setupEditArticleView(ArticleSolrObj article) {
        if (article.getIdOfEntityOrParticipant() > 0) {
            mIdOfEntityOrParticipantArticle = article.getIdOfEntityOrParticipant();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.edit_story);
        }
        if (mEditorFragment != null) {
            if (StringUtil.isNotNullOrEmptyString(article.getDescription())) {
                mEditorFragment.setContent(article.getDescription());
            }
            if (StringUtil.isNotNullOrEmptyString(article.getNameOrTitle())) {
                mEditorFragment.setTitle(article.getNameOrTitle());
            }
        }
        if (StringUtil.isNotEmptyCollection(mArticleSolrObj.getTag_ids())) {
            for (int i = 0; i < mArticleSolrObj.getTag_ids().size(); i++) {
                ArticleTagName articleTagName = new ArticleTagName();
                articleTagName.setId(mArticleSolrObj.getTag_ids().get(i));
                articleTagName.setTagName(mArticleSolrObj.getTags().get(i));
                mArticleTagNameList.add(articleTagName);
            }
            getArticleTagList(mArticleTagNameList, true);
        }
        if (StringUtil.isNotNullOrEmptyString(mArticleSolrObj.getImageUrl())) {
            mIsCoverPhoto = true;
            showImage(mArticleSolrObj.getImageUrl());
        }
    }

    @Override
    public void showMessage(int stringID) {
        if (!isFinishing() && mMyToast != null) {
            mMyToast.setText(stringID);
            mMyToast.show();
        }
    }

    @Override
    public void showImage(String finalImageUrl) {
        if (mIsCoverPhoto) {
            if (StringUtil.isNotNullOrEmptyString(finalImageUrl)) {
                mCoverImageUrl = finalImageUrl;
                int width = CommonUtil.getWindowWidth(this);
                int imageHeight = width / 2;
                finalImageUrl = CommonUtil.getThumborUri(finalImageUrl, width, imageHeight);
                Glide.with(this)
                        .asBitmap()
                        .load(finalImageUrl)
                        .apply(new RequestOptions().placeholder(R.color.photo_placeholder))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap profileImage, Transition<? super Bitmap> transition) {
                                ivAddPhoto.setVisibility(View.VISIBLE);
                                ivAddPhoto.setImageBitmap(profileImage);
                                tvAddCover.setVisibility(View.GONE);
                                ivCloseImg.setVisibility(View.VISIBLE);
                            }
                        });
            } else {
                tvAddCover.setVisibility(View.VISIBLE);
                ivAddPhoto.setVisibility(View.GONE);
                ivCloseImg.setVisibility(View.GONE);
            }
        } else {
            MediaFile mediaFile = new MediaFile();
            String mediaId = String.valueOf(System.currentTimeMillis());
            mediaFile.setMediaId(mediaId);
            mediaFile.setVideo(false);
            mEditorFragment.appendMediaFile(mediaFile, finalImageUrl, null);
        }
        stopProgressBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPress();
            return true;
        }
        if (id == R.id.post_article_submit) {
            if (validateFields(false, true)) {
                if (StringUtil.isNotNullOrEmptyString(mCoverImageUrl)) {
                    draftAndSubmitStoryArticle(true);
                } else {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(CreateStoryActivity.this);

                    builder.setTitle(R.string.dialog_title_image);
                    builder.setMessage(R.string.dialog_image);
                    builder.setNegativeButton(R.string.add_image, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton(R.string.post_anyway, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            draftAndSubmitStoryArticle(true);
                        }
                    });

                    builder.create();
                    builder.show();

                }
            }
        }

        if (id == R.id.next_article_submit) {
            showNextPage();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_close_img)
    public void onImageCloseClick() {
        mCoverImageUrl = "";
        ivAddPhoto.setVisibility(View.GONE);
        tvAddCover.setVisibility(View.VISIBLE);
        ivCloseImg.setVisibility(View.GONE);
    }

    @OnClick(R.id.guideline)
    public void onGuidelineClick() {
        showGuideLineView();
    }

    @OnClick(R.id.tv_draft)
    public void onDraftClick() {
        if (validateFields(true, true)) {
            draftAndSubmitStoryArticle(false);
        }
    }

    private void showNextPage() {
        mIsNextPage = true;
        mArticleNextPageContainer.setVisibility(View.VISIBLE);
        mEditorContainer.setVisibility(View.GONE);
        invalidateToolBar();
        invalidateOptionsMenu();
    }

    private void hideNextPage() {
        mIsNextPage = false;
        mArticleNextPageContainer.setVisibility(View.GONE);
        mEditorContainer.setVisibility(View.VISIBLE);
        invalidateToolBar();
        invalidateOptionsMenu();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof EditorFragmentAbstract) {
            mEditorFragment = (EditorFragmentAbstract) fragment;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
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

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                    if (resultCode == RESULT_OK) {
                        try {
                            File file = new File(result.getUri().getPath());
                            Bitmap photo = CompressImageUtil.decodeFile(file);
                            startProgressBar();
                            mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mArticleSubmissionPresenter.uploadFile(mEncodeImageUrl, this);
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Toast.makeText(this, R.string.cropping_fail + " " + result.getError(), Toast.LENGTH_LONG).show();
                    }

                    break;
            }
        }
    }

    //endregion

    //region editor methods
    @Override
    public void onMediaDropped(ArrayList<Uri> arrayList) {
    }

    @Override
    public void onRequestDragAndDropPermissions(DragEvent dragEvent) {
    }

    @Override
    public void onEditorFragmentInitialized() {
        mEditorFragment.setTitlePlaceholder(mHerStoryBodyMsg);
        mEditorFragment.setContentPlaceholder(mHerStoryTitle);
        mEditorFragment.setLocalDraft(true);
        mEditorFragment.isActionInProgress();
    }

    @Override
    public void onSettingsClicked() {

    }

    @Override
    public void onAddMediaClicked() {
        mIsCoverPhoto = false;
        CameraBottomSheetFragment.showDialog(this, getScreenName());
    }

    @Override
    public void onMediaRetryClicked(String s) {

    }

    @Override
    public void onMediaUploadCancelClicked(String s, boolean b) {
    }

    @Override
    public void onFeaturedImageChanged(long l) {

    }

    @Override
    public void onVideoPressInfoRequested(String s) {

    }

    @Override
    public String onAuthHeaderRequested(String s) {
        return null;
    }

    @Override
    public void saveMediaFile(MediaFile mediaFile) {

    }

    @Override
    public void onTrackableEvent(EditorFragmentAbstract.TrackableEvent trackableEvent) {

    }
    //endregion

    //region public methods
    public void selectImageFrmCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE).setFixAspectRatio(true)
                .setAllowRotation(true)
                .start(this);
    }

    public void selectImageFrmGallery() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAllowRotation(true)
                .start(this);
    }
    //endregion

    //region private methods
    private void setupShareToFbListener() {
        mShareToFacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mShareToFacebook.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.vector_facebook_small_active), null, null, null);
                    mShareToFacebook.setTextColor(ContextCompat.getColor(getApplication(), R.color.fb_Color));
                } else {
                    mShareToFacebook.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getApplication(), R.drawable.vector_facebook_small), null, null, null);
                    mShareToFacebook.setTextColor(ContextCompat.getColor(getApplication(), R.color.recent_post_comment));
                }
            }
        });
    }

    private void draftAndSubmitStoryArticle(boolean isPublish) {
        String articleTitle = null;
        String articleBody = null;
        try {
            articleTitle = mEditorFragment.getTitle().toString();
            articleBody = mEditorFragment.getContent().toString();
            articleBody = articleBody.replaceAll("\n", "<br />");
            articleBody = articleBody.replaceAll("<img", "<br /><img");
        } catch (EditorFragment.IllegalEditorStateException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        List<Long> tagList = new ArrayList<>();
        if (null != mCompletionView) {
            for (ArticleTagName articleTagName : mCompletionView.getObjects()) {
                tagList.add(articleTagName.getId());
            }
        }
        if (null != mIdOfEntityOrParticipantArticle) {
            mArticleSubmissionPresenter.editArticle(mAppUtils.articleDraftAddEditRequest(mIdOfEntityOrParticipantArticle, articleTitle, articleBody, tagList, mDeletedTagsList, mArticleSolrObj, mCoverImageUrl, isPublish), isPublish);
        } else {
            mArticleSubmissionPresenter.submitAndDraftArticle(mAppUtils.articleDraftAddEditRequest(mIdOfEntityOrParticipantArticle, articleTitle, articleBody, tagList, mDeletedTagsList, mArticleSolrObj, mCoverImageUrl, isPublish), isPublish);
        }
    }

    private void onBackPress() {
        if (mIsNextPage) {
            hideNextPage();
            return;
        }
        if (!validateFields(true, false)) {
            super.onBackPressed();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateStoryActivity.this);
        builder.setTitle(R.string.dialog_title_draft);
        builder.setMessage(R.string.dialog_body_draft);
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onDraftClick();
            }
        });

        builder.create();
        builder.show();
    }

    private boolean validateFields(boolean isDraft, boolean showError) {
        String articleTitle = null;
        String articleBody = null;
        try {
            articleTitle = mEditorFragment.getTitle().toString();
            articleBody = mEditorFragment.getContent().toString();
        } catch (EditorFragment.IllegalEditorStateException e) {
            Crashlytics.getInstance().core.logException(e);
            return false;
        }
        if (!CommonUtil.isNotEmpty(articleTitle) && !CommonUtil.isNotEmpty(articleBody)) {
            if (showError) {
                showMessage(R.string.error_draft);
            }
            return false;
        }
        if (!isDraft && !CommonUtil.isNotEmpty(articleTitle)) {
            if (showError) {
                showMessage(R.string.error_title);
            }
            return false;
        }
        if (!isDraft && !CommonUtil.isNotEmpty(articleBody)) {
            if (showError) {
                showMessage(R.string.error_body);
            }
            return false;
        }
        if (!isDraft && mCompletionView != null && !StringUtil.isNotEmptyCollection(mCompletionView.getObjects())) {
            if (showError) {
                showMessage(R.string.error_tag_min);
                tvTagLable.setVisibility(View.VISIBLE);
            }
            return false;
        }
        if (isDraft && !CommonUtil.isNotEmpty(articleTitle) && CommonUtil.isNotEmpty(articleBody)) {
            showMessage(R.string.error_draft_title);
            return false;
        }
        return true;
    }

    private void showGuideLineView() {
        mIsGuidelineVisible = true;
        mGuidelineContainer.setVisibility(View.VISIBLE);
        mGuidelineContainer.requestFocusFromTouch();
        hideKeyboard(mToolbar);
    }

    private void hideKeyboard(View v) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //endregion

    //region static methods
    public static void navigateTo(Activity fromActivity, int flagActivity, String sourceScreen, HashMap<String, Object> screenProperties) {
        Intent intent = new Intent(fromActivity, CreateStoryActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        if (!CommonUtil.isEmpty(screenProperties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, screenProperties);
        }
        ActivityCompat.startActivity(fromActivity, intent, null);
    }

    public static void navigateTo(Activity fromActivity, ArticleSolrObj article, String sourceScreen, HashMap<String, Object> screenProperties) {

        Intent intent = new Intent(fromActivity, CreateStoryActivity.class);
        Parcelable parcelable = Parcels.wrap(article);
        intent.putExtra(ArticleSolrObj.ARTICLE_OBJ, parcelable);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);

        if (!CommonUtil.isEmpty(screenProperties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, screenProperties);
        }

        ActivityCompat.startActivity(fromActivity, intent, null);
    }

    private void imageCropping(Intent intent) {
        try {
            if (mLocalImageSaveForChallenge.exists()) {
                Bitmap photo = CompressImageUtil.decodeFile(mLocalImageSaveForChallenge);
                mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
            } else {
                Toast.makeText(this, R.string.error_while_save, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            e.printStackTrace();
        }
    }

    private void invalidateToolBar() {
        if (!mIsNextPage) {
            mToolbar.setNavigationIcon(R.drawable.vector_clear);
        } else {
            mToolbar.setNavigationIcon(R.drawable.vector_back_arrow);
        }
    }

    private void cropingIMG() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List list = getPackageManager().queryIntentActivities(intent, 0);
        intent.setData(mImageCaptureUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mLocalImageSaveForChallenge));
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", false);
        intent.putExtra("return-data", true);
        if (StringUtil.isNotEmptyCollection(list)) {
            Intent i = new Intent(intent);
            ResolveInfo res = (ResolveInfo) list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING);
        }
    }
    //endregion

    //region onClick methods
    @OnClick(R.id.guideline_close)
    public void onGuidelineCancel() {
        mGuidelineContainer.setVisibility(View.GONE);
        mIsGuidelineVisible = false;
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        onShowErrorDialog(s, feedParticipationEnum);
    }

    @Override
    public void showEmptyScreen(String s) {
    }

    //endregion

    //region next page view
    @OnClick(R.id.add_photo_container)
    public void onAddCoverClicked() {
        mIsCoverPhoto = true;
        CameraBottomSheetFragment.showDialog(this, getScreenName());
    }

    @Override
    public void onTokenAdded(ArticleTagName token) {
        if (StringUtil.isNotEmptyCollection(mCompletionView.getObjects())) {
            tvTagLable.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTokenRemoved(ArticleTagName token) {
        mDeletedTagsList.add(token.getId());
    }

//endregion
}
