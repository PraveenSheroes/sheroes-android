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
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

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
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.post.Article;
import appliedlife.pvtltd.SHEROES.presenters.ArticleSubmissionPresenterImpl;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.ArticleStatus;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.CameraBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.PostBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleSubmissionView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 06/09/17.
 */


public class ArticleSubmissionActivity extends BaseActivity implements IArticleSubmissionView, EditorFragmentAbstract.EditorFragmentListener, EditorFragmentAbstract.EditorDragAndDropListener {
    public static final String SCREEN_LABEL = "Article Submission activity";
    private static int flagActivity = 0;

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

    //endregion

    //region member variables
    private EditorFragmentAbstract mEditorFragment;
    private boolean shouldShowGuideLine;
    private boolean isGuidelineVisible;
    private File localImageSaveForChallenge;
    private Uri mImageCaptureUri;
    private String mEncodeImageUrl;
    private boolean isNextPage;
    //endregion

    //region activity methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_submission);
        SheroesApplication.getAppComponent(this).inject(this);
        ButterKnife.bind(this);
        mArticleSubmissionPresenter.attachView(this);

        Article article = null;
        Parcelable parcelable = getIntent().getParcelableExtra(Article.ARTICLE_OBJ);
        if (parcelable != null) {
            article = Parcels.unwrap(parcelable);
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
        if (article == null) {
            shouldShowGuideLine = true;
        }
        setupShareToFbListener();
    }

    @Override
    protected void onResumeFragments() {
        if (shouldShowGuideLine) {
            showGuideLineView();
        }
        super.onResumeFragments();
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

    private void setUpOptionMenuStates(Menu menu) {
        MenuItem itemNext = menu.findItem(R.id.next);
        MenuItem itemPost = menu.findItem(R.id.post);
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
    public void hideProgressBar() {
        mEditorFragment.getView().setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (isGuidelineVisible) {
            mGuidelineClose.performClick();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return mArticleSubmissionPresenter;
    }

    @Override
    public void setupEditArticleView(Article article) {
        getSupportActionBar().setTitle("Edit Article");
        mEditorFragment.setContent(article.body);
        mEditorFragment.setTitle(article.title);
    }

    @Override
    public void showMessage(int stringID) {
        Toast.makeText(getApplicationContext(), stringID, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMyArticleList() {
      /*  if (flagActivity != MyArticleListActivity.MY_ARTICLE_LIST_ACTIVITY) {
            MyArticleListActivity.navigateTo(this, getScreenName(), null);
        }
        finish();*/
    }

    @Override
    public void showImage(String finalImageUrl) {
        MediaFile mediaFile = new MediaFile();
        String mediaId = String.valueOf(System.currentTimeMillis());
        mediaFile.setMediaId(mediaId);
        mediaFile.setVideo(false);
        mEditorFragment.appendMediaFile(mediaFile, finalImageUrl, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
                if (isNextPage) {
                    hideNextPage();
                } else {
                    onBackPress();
                }
            return true;
        }
        if (id == R.id.post) {
                if (validateFields(false, true)) {
                    postArticle(false);
                }
        }

        if (id == R.id.next) {
            showNextPage();
        }

        if (id == R.id.draft) {
                if (validateFields(true, true)) {
                    postArticle(true);
                }
        }
        if (id == R.id.guideline) {
            showGuideLineView();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNextPage() {
        isNextPage = true;
        mArticleNextPageContainer.setVisibility(View.VISIBLE);;
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

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                    if (resultCode == RESULT_OK) {
                        try {
                            File file = new File(result.getUri().getPath());
                            Bitmap photo = CompressImageUtil.decodeFile(file);
                            mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mArticleSubmissionPresenter.uploadFile(result.getUri(), this);
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

    }

    @Override
    public void onRequestDragAndDropPermissions(DragEvent dragEvent) {

    }

    @Override
    public void onEditorFragmentInitialized() {
        mEditorFragment.setTitlePlaceholder("Article Title");
        String hintText="You story";
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


    public void selectImageFrmCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    public void selectImageFrmGallery() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    //region private methods
    private void showCamera() {
        /*mCameraUtil.openImageIntent(ArticleSubmissionActivity.this);*/
    }


    private void setupShareToFbListener() {
        mShareToFacebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            }
        });
    }

    private void postArticle(boolean isDraft) {
        String articleTitle = null;
        String articleBody = null;
        try {
            articleTitle = mEditorFragment.getTitle().toString();
        } catch (EditorFragment.IllegalEditorStateException e) {
            e.printStackTrace();
        }

        try {
            articleBody = mEditorFragment.getContent().toString();
        } catch (EditorFragment.IllegalEditorStateException e) {
            e.printStackTrace();
        }

        Article article = new Article();
        article.body = articleBody;
        article.title = articleTitle;
        String articleStatus;
        if (isDraft) {
            articleStatus = getString(ArticleStatus.DRAFT.getStatusName());
        } else {
            articleStatus = getString(ArticleStatus.SUBMITTED.getStatusName());;
        }
        showProgressBar();
        mArticleSubmissionPresenter.prepareArticle(articleTitle, articleBody, articleStatus);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mEditorFragment.getView().setVisibility(View.GONE);
    }

    private void onBackPress() {
        if (!validateFields(true, false)) {
            finish();
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
                    postArticle(true);
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
        } catch (EditorFragment.IllegalEditorStateException e) {
            e.printStackTrace();
            return false;
        }

        try {
            articleBody = mEditorFragment.getContent().toString();
        } catch (EditorFragment.IllegalEditorStateException e) {
            e.printStackTrace();
            return false;
        }
        if (!CommonUtil.isNotEmpty(articleTitle) && !CommonUtil.isNotEmpty(articleBody)) {
            if(showError){
                showMessage(R.string.error_draft);
            }
            return false;
        }
        if (!isDraft && !CommonUtil.isNotEmpty(articleTitle)) {
            if(showError){
                showMessage(R.string.error_title);
            }
            return false;
        }
        if (!isDraft && !CommonUtil.isNotEmpty(articleBody)) {
            if(showError){
                showMessage(R.string.error_body);
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

   /* private void onCameraPermission(final CameraEvent cameraEvent) {
        if (cameraEvent.isPermissionAllowed) {
            showCamera();
        } else {
            Snackbar snackbar = Snackbar
                    .make(mToolbar, R.string.chat_image_permission, Snackbar.LENGTH_LONG)
                    .setAction("Try Now", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RxUtil.requestPermission(ArticleSubmissionActivity.this, CareApplication.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE, Globals.STORAGE_PERMISSION);
                        }
                    });

            snackbar.show();
        }
    }*/
    //endregion

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

    public static void navigateTo(Activity fromActivity, Article article, String sourceScreen, HashMap<String, Object> screenProperties) {

        Intent intent = new Intent(fromActivity, ArticleSubmissionActivity.class);
        Parcelable parcelable = Parcels.wrap(article);
        intent.putExtra(Article.ARTICLE_OBJ, parcelable);
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

    }

    @Override
    public void stopProgressBar() {

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
    public void onAddCoverClicked(){

    }

    @OnClick(R.id.choose_community_container)
    public void onChooseCommunityClicked(){
        PostBottomSheetFragment.showDialog(this, SOURCE_SCREEN);
    }
    //endregion
}
