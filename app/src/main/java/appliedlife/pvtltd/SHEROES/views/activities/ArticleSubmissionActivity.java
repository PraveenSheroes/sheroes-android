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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
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
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.Configuration;
import appliedlife.pvtltd.SHEROES.models.entities.article.ArticleTagName;
import appliedlife.pvtltd.SHEROES.models.entities.feed.ArticleSolrObj;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.presenters.ArticleSubmissionPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.ContactsCompletionView;
import appliedlife.pvtltd.SHEROES.views.fragments.CameraBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleSubmissionView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 06/09/17.
 */


public class ArticleSubmissionActivity extends BaseActivity implements IArticleSubmissionView, EditorFragmentAbstract.EditorFragmentListener, EditorFragmentAbstract.EditorDragAndDropListener, TokenCompleteTextView.TokenListener<ArticleTagName> {
    public static final String SCREEN_LABEL = "Article Submission activity";
    private static int flagActivity = 0;

    @Inject
    AppUtils mAppUtils;

    @Inject
    ArticleSubmissionPresenterImpl mArticleSubmissionPresenter;

    @Inject
    Preference<Configuration> mConfiguration;

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

    //endregion

    //region member variables
    private EditorFragmentAbstract mEditorFragment;
    private boolean shouldShowGuideLine;
    private boolean isGuidelineVisible;
    private File localImageSaveForChallenge;
    private Uri mImageCaptureUri;
    private String mEncodeImageUrl;
    private boolean isNextPage;
    private ContactsCompletionView completionView;
    private List<ArticleTagName> mArticleTagNameList;
    private ArrayAdapter<ArticleTagName> adapter;
    private boolean mIsCoverPhoto;
    private List<ArticleTagName> mTagsList = new ArrayList<>();
    private ArticleSolrObj mArticleSolrObj = null;
    private Long mArticleCategoryId;
    //endregion

    //region activity methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_submission);
        SheroesApplication.getAppComponent(this).inject(this);
        ButterKnife.bind(this);
        mArticleSubmissionPresenter.attachView(this);
        Parcelable parcelable = getIntent().getParcelableExtra(ArticleSolrObj.ARTICLE_OBJ);
        if (parcelable != null) {
            mArticleSolrObj = Parcels.unwrap(parcelable);
        }
        setSupportActionBar(mToolbar);
        toolbarTitle.setText(R.string.title_article_submit);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        invalidateToolBar();
        File localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
        this.localImageSaveForChallenge = localImageSaveForChallenge;
        String articleGuideline = (mConfiguration != null && mConfiguration.isSet() && mConfiguration.get().configData != null && CommonUtil.isNotEmpty(mConfiguration.get().configData.articleGuideline)) ? mConfiguration.get().configData.articleGuideline : AppConstants.ARTICLE_GUIDELINE;
        mBody.setText(Html.fromHtml(articleGuideline));
        if (mArticleSolrObj == null) {
            shouldShowGuideLine = true;
        } else {
            setupEditArticleView(mArticleSolrObj);
        }
        setupShareToFbListener();
        setupSearchAddRemoveTagContainer();
    }

    @Override
    protected void onResumeFragments() {
        if (shouldShowGuideLine) {
            showGuideLineView();
        }
        super.onResumeFragments();
    }

    @Override
    public void articleSubmitResponse(ArticleSolrObj articleSolrObj) {
        hideNextPage();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
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

    private void setupSearchAddRemoveTagContainer() {
        ArticleTagName articleTagName = new ArticleTagName();
        ArticleTagName articleTagName1 = new ArticleTagName();
        ArticleTagName articleTagName2 = new ArticleTagName();
        ArticleTagName articleTagName3 = new ArticleTagName();
        ArticleTagName articleTagName4 = new ArticleTagName();
        ArticleTagName articleTagName5 = new ArticleTagName();
        articleTagName.setTagName("Marshall Weir");
        articleTagName1.setTagName("Margaret Smith");
        articleTagName2.setTagName("Max Jordan");
        articleTagName3.setTagName("Meg Peterson");
        articleTagName4.setTagName("Amanda Johnson");
        articleTagName5.setTagName("Terry Anderson");
        mArticleTagNameList = new ArrayList<>();
        mArticleTagNameList.add(articleTagName);
        mArticleTagNameList.add(articleTagName1);
        mArticleTagNameList.add(articleTagName2);
        mArticleTagNameList.add(articleTagName3);
        mArticleTagNameList.add(articleTagName4);
        mArticleTagNameList.add(articleTagName5);
        adapter = new FilteredArrayAdapter<ArticleTagName>(this, R.layout.article_tag_layout, mArticleTagNameList) {
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
                if (StringUtil.isNotEmptyCollection(mTagsList) && mTagsList.size() > 4) {
                    showMessage(R.string.error_tag_max);
                    return false;
                } else {
                    mask = mask.toLowerCase();
                    return articleTagName.getTagName().toLowerCase().startsWith(mask);
                }
            }
        };

        completionView = findViewById(R.id.tag_search_view);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Delete);

    }

    private void setUpOptionMenuStates(Menu menu) {
        MenuItem itemNext = menu.findItem(R.id.next_article_submit);
        MenuItem itemPost = menu.findItem(R.id.post_article_submit);
        MenuItem itemInfo = menu.findItem(R.id.guideline);
        if (isNextPage) {
            itemInfo.setVisible(false);
            itemNext.setVisible(false);
            itemPost.setVisible(true);
        } else {
            itemInfo.setVisible(true);
            itemNext.setVisible(true);
            itemPost.setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (isGuidelineVisible) {
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
        mArticleCategoryId=article.getArticleCategoryId();
        getSupportActionBar().setTitle("Edit Article");
        mEditorFragment.setContent(article.getListDescription());
        mEditorFragment.setTitle(article.getNameOrTitle());
    }

    @Override
    public void showMessage(int stringID) {
        Toast.makeText(getApplicationContext(), stringID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showImage(String finalImageUrl) {
        if (mIsCoverPhoto) {
            if (StringUtil.isNotNullOrEmptyString(finalImageUrl)) {
                int imageHeight = CommonUtil.getWindowWidth(this) / 2;
                finalImageUrl = CommonUtil.getThumborUri(finalImageUrl, CommonUtil.getWindowWidth(this), imageHeight);
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
                            }
                        });
            } else {
                tvAddCover.setVisibility(View.VISIBLE);
                ivAddPhoto.setVisibility(View.GONE);
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
                postArticle();
            }
        }

        if (id == R.id.next_article_submit) {
            showNextPage();
        }

        if (id == R.id.draft) {
            if (validateFields(true, true)) {
                draftArticle();
            }
        }
        if (id == R.id.guideline) {
            showGuideLineView();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNextPage() {
        isNextPage = true;
        mArticleNextPageContainer.setVisibility(View.VISIBLE);
        mEditorContainer.setVisibility(View.GONE);
        invalidateToolBar();
        invalidateOptionsMenu();
    }

    private void hideNextPage() {
        isNextPage = false;
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
                        Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                    }

                    break;
            }
        }
    }

    //endregion

    //region editor methods
    @Override
    public void onMediaDropped(ArrayList<Uri> arrayList) {
        Toast.makeText(this, "onMediaDropped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestDragAndDropPermissions(DragEvent dragEvent) {
        Toast.makeText(this, "onRequestDragAndDropPermissions", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditorFragmentInitialized() {
        mEditorFragment.setTitlePlaceholder("Article Title");
        String hintText = "You story";
        if (null != mConfiguration && mConfiguration.isSet() && mConfiguration.get().configData != null) {
            hintText = mConfiguration.get().configData.mHerStoryHintText;
        }
        mEditorFragment.setContentPlaceholder(hintText);
        mEditorFragment.setLocalDraft(true);
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
        Toast.makeText(this, "onMediaRetryClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMediaUploadCancelClicked(String s, boolean b) {
        Toast.makeText(this, "onMediaUploadCancelClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFeaturedImageChanged(long l) {
        Toast.makeText(this, "onFeaturedImageChanged", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVideoPressInfoRequested(String s) {
        Toast.makeText(this, "onVideoPressInfoRequested", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String onAuthHeaderRequested(String s) {
        return null;
    }

    @Override
    public void saveMediaFile(MediaFile mediaFile) {
        Toast.makeText(this, "saveMediaFile", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTrackableEvent(EditorFragmentAbstract.TrackableEvent trackableEvent) {
        Toast.makeText(this, "onTrackableEvent", Toast.LENGTH_SHORT).show();
    }
    //endregion


    public void selectImageFrmCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAllowRotation(true)
                .start(this);
    }

    public void selectImageFrmGallery() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAllowRotation(true)
                .start(this);
    }

    private void setupShareToFbListener() {
        mShareToFacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            }
        });
    }

    private void draftArticle() {
        String articleTitle = null;
        String articleBody = null;
        try {
            articleTitle = mEditorFragment.getTitle().toString();
            articleBody = mEditorFragment.getContent().toString();
        } catch (EditorFragment.IllegalEditorStateException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        mArticleSubmissionPresenter.postArticle(mAppUtils.makeArticleDraftRequest(articleTitle, articleBody));
    }

    private void postArticle() {
        String articleTitle = null;
        String articleBody = null;
        try {
            articleTitle = mEditorFragment.getTitle().toString();
            articleBody = mEditorFragment.getContent().toString();
        } catch (EditorFragment.IllegalEditorStateException e) {
            Crashlytics.getInstance().core.logException(e);
        }
        if (null != mArticleCategoryId) {
            mArticleSubmissionPresenter.postArticle(mAppUtils.articleAddEditRequest(mArticleCategoryId, articleTitle, articleBody));
        } else {
            mArticleSubmissionPresenter.editArticle(mAppUtils.articleAddEditRequest(null, articleTitle, articleBody));
        }

    }

    private void onBackPress() {
        if (isNextPage) {
            hideNextPage();
            return;
        }
        if (!validateFields(true, false)) {
            super.onBackPressed();
            return;
        }

        AlertDialog.Builder builder =
                new AlertDialog.Builder(ArticleSubmissionActivity.this);

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
                if (validateFields(true, true)) {
                    postArticle();
                }
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

        if (!StringUtil.isNotEmptyCollection(mTagsList)) {
            if (showError) {
                showMessage(R.string.error_tag_min);
                tvTagLable.setVisibility(View.VISIBLE);
            }
            return false;
        }

        return true;
    }

    private void showGuideLineView() {
        shouldShowGuideLine = false;
        isGuidelineVisible = true;
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

    //region static methods
    public static void navigateTo(Activity fromActivity, int flagActivity, String sourceScreen, HashMap<String, Object> screenProperties) {

        Intent intent = new Intent(fromActivity, ArticleSubmissionActivity.class);
        ArticleSubmissionActivity.flagActivity = flagActivity;
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);

        if (!CommonUtil.isEmpty(screenProperties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, screenProperties);
        }

        ActivityCompat.startActivity(fromActivity, intent, null);
    }

    public static void navigateTo(Activity fromActivity, ArticleSolrObj article, String sourceScreen, HashMap<String, Object> screenProperties) {

        Intent intent = new Intent(fromActivity, ArticleSubmissionActivity.class);
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
            if (localImageSaveForChallenge.exists()) {
                Bitmap photo = CompressImageUtil.decodeFile(localImageSaveForChallenge);
                mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
            } else {
                Toast.makeText(this, "Error while save image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            e.printStackTrace();
        }
    }


    private void invalidateToolBar() {
        if (!isNextPage) {
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(localImageSaveForChallenge));
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
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
        isGuidelineVisible = false;
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
    public void startNextScreen() {

    }

    @Override
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {

    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

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
        if (StringUtil.isNotEmptyCollection(completionView.getObjects())) {
            tvTagLable.setVisibility(View.GONE);
        }
        mTagsList.clear();
        mTagsList.addAll(completionView.getObjects());
    }

    @Override
    public void onTokenRemoved(ArticleTagName token) {
        mTagsList.clear();
        mTagsList.addAll(completionView.getObjects());
    }

    //endregion
}
