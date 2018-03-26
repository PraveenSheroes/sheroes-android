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
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IArticleSubmissionView;
import butterknife.Bind;
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
    //endregion

    //region member variables
    private EditorFragmentAbstract mEditorFragment;
    private boolean isNewArticle;
    private boolean isGuidelineShown;
    private File localImageSaveForChallenge;
    private Uri mImageCaptureUri;
    private String mEncodeImageUrl;
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
        mToolbar.setNavigationIcon(R.drawable.vector_clear);
        File localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
        this.localImageSaveForChallenge = localImageSaveForChallenge;
        String articleGuideline = (mConfiguration!=null && mConfiguration.isSet() && mConfiguration.get() != null  && mConfiguration.get().configData!=null && CommonUtil.isNotEmpty(mConfiguration.get().configData.articleGuideline)) ? mConfiguration.get().configData.articleGuideline : AppConstants.ARTICLE_GUIDELINE;
        mBody.setText(Html.fromHtml(articleGuideline));
        if (article == null) {
            isNewArticle = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        if (isNewArticle && !isGuidelineShown) {
            showGuideLineView();
            isGuidelineShown = true;
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
        if (isGuidelineShown) {
            isGuidelineShown = false;
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
            if (!isGuidelineShown) {
                onBackPress();
            }
            return true;
        }
        if (id == R.id.post) {
            if (!isGuidelineShown) {
                if (validateFields(false, true)) {
                    postArticle(false);
                }
            }
        }

        if (id == R.id.draft) {
            if (!isGuidelineShown) {
                if (validateFields(true, true)) {
                    postArticle(true);
                }
            }
        }
        if (id == R.id.guideline) {
            showGuideLineView();
        }
        return super.onOptionsItemSelected(item);
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
                case AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING:
                    imageCropping(intent);
                    break;

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                    if (resultCode == RESULT_OK) {
                        MediaFile mediaFile = new MediaFile();
                        try {
                            File file = new File(result.getUri().getPath());
                            Bitmap photo = CompressImageUtil.decodeFile(file);
                            mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
                            mediaFile.setFileURL(file.getPath());
                            String mediaId = String.valueOf(System.currentTimeMillis());
                            mediaFile.setMediaId(mediaId);
                            mediaFile.setVideo(false);
                            mEditorFragment.appendMediaFile(mediaFile, "", null);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //showImage(mEncodeImageUrl);
                        //mHomePresenter.getUserSummaryDetails(mAppUtils.getUserProfileRequestBuilder(AppConstants.PROFILE_PIC_SUB_TYPE, AppConstants.PROFILE_PIC_TYPE, mEncodeImageUrl));
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                    }
            }
        }
       /* final CameraUtil.OnImageCroppedListener listener = new CameraUtil.OnImageCroppedListener() {
            @Override
            public void onDone(final Uri outputUri) {
                if (outputUri != null) {
                    showProgressBar();
                    mArticleSubmissionPresenter.uploadFile(outputUri, getApplicationContext());
                }
            }
        };
        if (mCameraUtil != null) {
            mCameraUtil.onResult(this, listener, requestCode, resultCode, data);
        }*/
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
        mEditorFragment.setContentPlaceholder("Your awesome story");
        mEditorFragment.setLocalDraft(true);
    }

    @Override
    public void onSettingsClicked() {

    }

    @Override
    public void onAddMediaClicked() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAllowRotation(true)
                .start(this);
       /* if (!CommonUtil.isMarshmallow()) {
            showCamera();
        } else {
            RxUtil.requestPermission(ArticleSubmissionActivity.this, CareApplication.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE, Globals.STORAGE_PERMISSION, Globals.CAMERA);
        }*/
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

    //region private methods
    private void showCamera() {
        /*mCameraUtil.openImageIntent(ArticleSubmissionActivity.this);*/
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
        mGuidelineContainer.setVisibility(View.VISIBLE);
        mGuidelineContainer.requestFocusFromTouch();
        hideKeyboard(mToolbar);
        isGuidelineShown = true;
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
        isGuidelineShown = false;
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
}
