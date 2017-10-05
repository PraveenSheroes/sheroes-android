package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.graphics.Palette;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.EditCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedResponsePojo;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.presenters.HomePresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.CommunityTypeDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.COMMUNITY_OWNER;
import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_MY_COMMUNITIES;

/**
 * Created by Ajit Kumar on 11-01-2017.
 *
 * @author Ajit Kumar
 * @version 5.0
 * @since 11-01-2017.
 * Title: Create Community fragment within Create Community activity perform all the UI operation .
 * Fragment will have all UI components and operate with activity .
 */
public class CreateCommunityFragment extends BaseFragment implements CommunityView, CommunityTypeDialogFragment.MyDialogFragmentListener, HomeView {
    private static final String SCREEN_LABEL = "Create Community Screen";
    private final String TAG = LogUtils.makeLogTag(CreateCommunityFragment.class);

    @Bind(R.id.cb_create_community_open_check)
    CheckBox mCbopenCommunity;

    @Inject
    CreateCommunityPresenter createCommunityPresenter;

    @Bind(R.id.cb_create_community_close_check)
    CheckBox mCbcloseCommunity;

    @Bind(R.id.tv_create_community_cover_img_upload)
    TextView mTvCommunityCoverImg;

    @Bind(R.id.tv_create_community_logo_upload)
    TextView mTvCommunityLogoImg;

    @Bind(R.id.iv_create_community_cover_img)
    ImageView mIvCover;

    @Bind(R.id.tv_create_community_logo)
    TextView mIvlogo;

    @Bind(R.id.tv_create_community_submit)
    TextView mTvCreate;

    @Bind(R.id.iv_create_community_cross)
    TextView mIvBtnCross;

    @Bind(R.id.tv_create_community_title)
    TextView mtvCreateCommunityTitle;

    @Bind(R.id.et_create_community_type)
    EditText mEtCommunityType;

    @Bind(R.id.et_create_community_tags)
    EditText mEtCreateCommunityTags;

    @Bind(R.id.et_create_community_description)
    EditText metCreateCommunityDescription;

    @Bind(R.id.txt_counter)
    TextView mCounterTxt;

    @Bind(R.id.et_create_community_name)
    EditText metCreateCommunityName;

    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;

    int mTagFlag = 0;

    String encImage;

    Long typeId;
    String encCoverImage;
    private int mImage_type = 0;
    private int mLogoFlag=0,mCoverFlag=0;
    private static final int mCAMERA_CODE = 101, mGALLERY_CODE = 201, mCROPING_CODE = 301;
    private Uri mImageCaptureUri;
    private File mOutPutFile = null;
    private File mOutPutFile1 = null;
    FeedDetail mFeedDetail;
    String mDescription = "";
    String mCommunityName = "";
    View view;
    long tagId[] = new long[4];

    private String messageForSuccess;
    List<Long> tagsid = new ArrayList<>();
    @Inject
    AppUtils mAppUtils;
    @Inject
    HomePresenter mHomePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            SheroesApplication.getAppComponent(getContext()).inject(this);
            view = inflater.inflate(R.layout.fragmentcreate_community, container, false);
            ButterKnife.bind(this, view);
            mHomePresenter.attachView(this);
            mCbopenCommunity.setChecked(true);
            mOutPutFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
            mOutPutFile1 = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
            //    mEtCreateCommunityTags.setText("Community Tag");
            createCommunityPresenter.attachView(this);
            setProgressBar(mProgressBar);
            if (null != getArguments()) {
                mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITIES_DETAIL);
                if (null != mFeedDetail) {
                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCommunityType())) {
                        mEtCommunityType.setText(mFeedDetail.getCommunityType());
                        typeId = mFeedDetail.getCommunityTypeL();
                    }
                    if (null != mFeedDetail.getTag_ids() && mFeedDetail.getTag_ids().size() > 0) {
                        mEtCreateCommunityTags.setText(mFeedDetail.getTags().toString());
                       // tagsid=mFeedDetail.getTag_ids();
                    }
                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
                        metCreateCommunityName.setText(mFeedDetail.getNameOrTitle());
                    }
                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getListDescription())) {
                        metCreateCommunityDescription.setText(mFeedDetail.getListDescription());
                    }

                    if (mFeedDetail.isOwner()) {
                        mTvCreate.setText(R.string.ID_SAVE);
                        mtvCreateCommunityTitle.setText("EDIT COMMUNITY");
                    }
                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getImageUrl())) {
                        Glide.with(this)
                                .load(mFeedDetail.getImageUrl()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .skipMemoryCache(true)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                        mIvCover.setImageBitmap(resource);
                                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                            public void onGenerated(Palette palette) {
                                                getActivity().supportStartPostponedEnterTransition();
                                            }
                                        });
                                    }
                                });
                        if (null != mIvCover.getDrawable()) {
                            Bitmap bitmap = ((BitmapDrawable) mIvCover.getDrawable()).getBitmap();
                            byte[] buffer = new byte[4096];
                            buffer = getBytesFromBitmap(bitmap);
                            encCoverImage = Base64.encodeToString(buffer, Base64.DEFAULT);

                        }
                    }
                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getThumbnailImageUrl())) {
                        Glide.with(mIvlogo.getContext())
                                .load((mFeedDetail.getThumbnailImageUrl()))
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>(100, 100) {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                        mIvlogo.setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(mIvlogo.getResources(), resource), null, null);
                                    }
                                });
                        if (null != (mIvlogo.getDrawingCache())) {
                            mIvlogo.buildDrawingCache();
                            Bitmap bitmap1 = mIvlogo.getDrawingCache();
                            //  Bitmap bitmap1 = mIvlogo.getDrawingCache();
                            byte[] buffer = new byte[4096];
                            buffer = getBytesFromBitmap(bitmap1);
                            encImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                        }
                         mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    }
                    mCbcloseCommunity.setChecked(mFeedDetail.isClosedCommunity());
                    mCbopenCommunity.setChecked(!mFeedDetail.isClosedCommunity());
                    messageForSuccess=getString(R.string.ID_EDITED);
                }else
                {
                    messageForSuccess=getString(R.string.ID_CREATED);
                }

            } else {
                mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog((AppConstants.ERROR_OCCUR), ERROR_MY_COMMUNITIES);
            }

            // getActivity().setRequestedOrientation(
            //  ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getExternalStoragePermission();
            metCreateCommunityDescription.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int aft) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        mCounterTxt.setVisibility(View.VISIBLE);
                        mCounterTxt.setText(String.valueOf(AppConstants.MAX_WORD_COUNTER - s.toString().length()));
                    } else {
                        mCounterTxt.setVisibility(View.GONE);
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void getFeedListSuccess(FeedResponsePojo feedResponsePojo) {
        List<FeedDetail> feedDetailList = feedResponsePojo.getFeedDetails();
        if (StringUtil.isNotEmptyCollection(feedDetailList)) {
            Toast.makeText(getActivity(),messageForSuccess, Toast.LENGTH_SHORT).show();
            mFeedDetail = feedDetailList.get(0);
            ((CreateCommunityActivity) getActivity()).onBackClickHandle(mFeedDetail);
        }
    }

    public void setCommunitiyTags(String[] tagsval, long[] tagsid) {
        tagId = tagsid;
        StringBuilder stringBuilder = new StringBuilder();
        if (tagsval.length > 0) {
            for (String tagValue : tagsval) {
                if (StringUtil.isNotNullOrEmptyString(tagValue)) {
                    stringBuilder.append(tagValue).append(AppConstants.COMMA);
                }
            }
        }
        mEtCreateCommunityTags.setText(stringBuilder.substring(0, stringBuilder.length() - 1));
    }

    @OnClick(R.id.iv_create_community_cross)
    public void backClick() {

        getActivity().finish();
    }

    @OnClick(R.id.tv_create_community_logo_upload)
    public void btnChangeLogo() {
        mLogoFlag=1;
        checkCameraPermission();
        openImageOption();
    }

    @OnClick(R.id.iv_create_community_cover_img)
    public void coverImageClick() {

        mImage_type = 2;
        checkCameraPermission();
        openImageOption();
    }

    @OnClick(R.id.tv_create_community_logo)
    public void changeCommunityLogo() {
        mImage_type = 1;
        checkCameraPermission();
        openImageOption();
    }

    //*********Tag Click**********************//
    @OnClick(R.id.et_create_community_tags)
    public void btnTagClick() {
        mTagFlag = 1;
        CreateCommunityRequest createCommunityRequest = new CreateCommunityRequest();

        if (StringUtil.isNotNullOrEmptyString(metCreateCommunityName.getText().toString())) {
            createCommunityRequest.setName(metCreateCommunityName.getText().toString());

        }

        if (null != mIvCover.getDrawable()) {
            Bitmap bitmap = ((BitmapDrawable) mIvCover.getDrawable()).getBitmap();
            byte[] buffer = new byte[4096];
            buffer = getBytesFromBitmap(bitmap);
            encCoverImage = Base64.encodeToString(buffer, Base64.DEFAULT);
            createCommunityRequest.setCoverImage(encCoverImage);


        }
        mIvlogo.buildDrawingCache();
        if (null != (mIvlogo.getDrawingCache())) {
            Bitmap bitmap1 = mIvlogo.getDrawingCache();
            //  Bitmap bitmap1 = mIvlogo.getDrawingCache();
            byte[] buffer = new byte[4096];
            buffer = getBytesFromBitmap(bitmap1);
            encImage = Base64.encodeToString(buffer, Base64.DEFAULT);
            createCommunityRequest.setLogo(encImage);


        }
        if (StringUtil.isNotNullOrEmptyString(metCreateCommunityDescription.getText().toString())) {
            createCommunityRequest.setDescription(metCreateCommunityDescription.getText().toString());

        }
        if (StringUtil.isNotNullOrEmptyString(mEtCommunityType.getText().toString())) {
            createCommunityRequest.setType(mEtCommunityType.getText().toString());

        }
        if (null != typeId) {
            createCommunityRequest.setCommunityTypeId(typeId);
        }

        mTagFlag=1;
        ((CreateCommunityActivity)getActivity()).callCommunityTagPage(mFeedDetail);

        //  CommunitySearchTagsDialogFragment newFragment = new CommunitySearchTagsDialogFragment();
    }

    @OnClick(R.id.tv_create_community_cover_img_upload)
    public void btnChangeCover() {
        mImage_type = 2;
        checkCameraPermission();
        openImageOption();
    }

    @OnClick(R.id.tv_create_community_submit)
    public void btnCreateClick() {
        mTvCreate.setEnabled(false);
        if (StringUtil.isNotNullOrEmptyString(metCreateCommunityDescription.getText().toString()) && StringUtil.isNotNullOrEmptyString(mEtCommunityType.getText().toString()) && StringUtil.isNotNullOrEmptyString(metCreateCommunityName.getText().toString()) && StringUtil.isNotNullOrEmptyString(mEtCreateCommunityTags.getText().toString())) {
            if (mTvCreate.getText().toString().equalsIgnoreCase(getString(R.string.ID_CREATE))) {
                callCreateCommunitySubmit();
            } else {
                callEditCommunitySubmit();
            }
        } else {
            mTvCreate.setEnabled(true);
            mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog((AppConstants.BLANK_MESSAGE), ERROR_MY_COMMUNITIES);
        }
    }

    void callEditCommunitySubmit() {


        List<Long> tagsIdValue = new ArrayList<>();
        for(int i=0;i<tagId.length;i++)
        {
            if(tagId[i]>0)
                tagsIdValue.add(tagId[i]);

        }

        EditCommunityRequest editCommunityRequest = new EditCommunityRequest();
        if(mCoverFlag==1) {
            editCommunityRequest.setCoverImage(encCoverImage);
        }
        if(mLogoFlag==1) {
            editCommunityRequest.setLogo(encImage);
        }
        editCommunityRequest.setId((int) mFeedDetail.getIdOfEntityOrParticipant());
        editCommunityRequest.setActive(true);
        editCommunityRequest.setAppVersion("String");
        editCommunityRequest.setCloudMessagingId("String");
        editCommunityRequest.setCommunityTypeId(typeId);
        if (null != metCreateCommunityDescription.getText().toString() && StringUtil.isNotNullOrEmptyString(metCreateCommunityDescription.getText().toString())) {
            mDescription = metCreateCommunityDescription.getText().toString();
        }
        editCommunityRequest.setDescription(mDescription);
        editCommunityRequest.setDeviceUniqueId("String");
        if (mCbcloseCommunity.isChecked()) {
            editCommunityRequest.setClosed(true);
        }
        else {
            editCommunityRequest.setClosed(false);
        }
        editCommunityRequest.setLastScreenName("String");

        if (null != metCreateCommunityName.getText().toString() && StringUtil.isNotNullOrEmptyString(metCreateCommunityName.getText().toString())) {
            mCommunityName = metCreateCommunityName.getText().toString();
            editCommunityRequest.setName(mCommunityName);

        }
        editCommunityRequest.setPurpose("sink more test code");


        editCommunityRequest.setScreenName("String");

        if(null !=mFeedDetail.getTag_ids() && tagsIdValue.size()>0) {
            editCommunityRequest.setRemovedTags(mFeedDetail.getTag_ids());
        }

        if(tagsIdValue.size()>0) {
            editCommunityRequest.setTags(tagsIdValue);
        }
        else
        {
            editCommunityRequest.setTags(mFeedDetail.getTag_ids());
        }
        if (StringUtil.isNotNullOrEmptyString(mCommunityName) || StringUtil.isNotNullOrEmptyString(mDescription))
            createCommunityPresenter.postCommunityList(editCommunityRequest);
        else {
            mTvCreate.setEnabled(true);
            mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog((AppConstants.INAVLID_DATA), ERROR_MY_COMMUNITIES);
        }

    }

    private void callCreateCommunitySubmit() {

        List<Long> tags = new ArrayList<>();
        for (int i = 0; i < tagId.length; i++) {
            if (tagId[i] > 0)
                tags.add(tagId[i]);

        }

        CreateCommunityRequest createCommunityRequest = new CreateCommunityRequest();


        if(mCoverFlag==1) {
            createCommunityRequest.setCoverImage(encCoverImage);
        }
        if(mLogoFlag==1) {
            createCommunityRequest.setLogo(encImage);
        }



        createCommunityRequest.setAppVersion("String");

        createCommunityRequest.setCloudMessagingId("String");

        createCommunityRequest.setCommunityTypeId(typeId);
        if (null != metCreateCommunityDescription.getText().toString() && StringUtil.isNotNullOrEmptyString(metCreateCommunityDescription.getText().toString())) {
            mDescription = metCreateCommunityDescription.getText().toString();
        }
        createCommunityRequest.setDescription(mDescription);

        createCommunityRequest.setDeviceUniqueId("String");
        if (mCbcloseCommunity.isChecked())
            createCommunityRequest.setClosed(true);
        else
            createCommunityRequest.setClosed(false);

        createCommunityRequest.setLastScreenName("String");

        if (null != metCreateCommunityName.getText().toString() && StringUtil.isNotNullOrEmptyString(metCreateCommunityName.getText().toString())) {
            mCommunityName = metCreateCommunityName.getText().toString();
        }

        createCommunityRequest.setName(mCommunityName);


        createCommunityRequest.setPurpose("sink more test code");


        createCommunityRequest.setScreenName("String");


        createCommunityRequest.setTags(tags);

        createCommunityPresenter.postCreateCommunityList(createCommunityRequest);


    }

    public void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                LogUtils.info("testing", "Permission is revoked");


            }
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                LogUtils.info("testing", "Permission is revoked");

            }
        }

    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                LogUtils.info("testing", "Permission is revoked");

            }

        } else { //permission is automatically granted on sdk<23 upon installation
            LogUtils.info("testing", "Permission is already granted");

        }
        checkStoragePermission();
    }

    @OnClick(R.id.cb_create_community_open_check)
    public void OnOpenCheckClick() {
        mCbcloseCommunity.setChecked(false);
        if (mCbopenCommunity.isChecked()) {
            ((CreateCommunityActivity)getActivity()).changePrivacy(false);
        }

    }
    @OnClick(R.id.cb_create_community_close_check)
    public void OnCloseCheckClick() {
        mCbopenCommunity.setChecked(false);
        if (mCbcloseCommunity.isChecked()) {
            ((CreateCommunityActivity)getActivity()).changePrivacy(true);
        }
    }
/*
    @OnTouch(R.id.et_create_community_type)
    public boolean onTouch() {
        CommunityTypeDialogFragment newFragment =new CommunityTypeDialogFragment();
        newFragment.setListener(this);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
        return true;
    }*/

    @OnClick(R.id.et_create_community_type)
    public void onLogInBtnClick() {
        CommunityTypeDialogFragment newFragment = new CommunityTypeDialogFragment();
        newFragment.setListener(this);

        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }


    @Override
    public void onAddFriendSubmit(String communitynm, Long typeId) {
        mEtCommunityType.setText(communitynm);
        this.typeId = typeId;


    }

    public void showTagResult(String[] tagsval) {
        String tagval = "";
        for (int i = 1; i < tagsval.length; i++) {
            tagval = tagval + " " + tagsval[i];
        }
        LogUtils.info("result-", tagval);
        mEtCreateCommunityTags.setText(tagval);

    }
    @Override
    public void createCommunitySuccess(BaseResponse baseResponse) {
        if (baseResponse instanceof CreateCommunityResponse) {
            CreateCommunityResponse createCommunityResponse = ((CreateCommunityResponse) baseResponse);
            mTvCreate.setEnabled(true);
            switch (createCommunityResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    if (null != mFeedDetail && StringUtil.isNotNullOrEmptyString(mFeedDetail.getId())) {
                        mHomePresenter.getFeedFromPresenter(mAppUtils.feedDetailRequestBuilder(AppConstants.FEED_COMMUNITY, AppConstants.ONE_CONSTANT,  mFeedDetail.getIdOfEntityOrParticipant()));
                    }else
                    {
                        Toast.makeText(getActivity(),messageForSuccess, Toast.LENGTH_SHORT).show();
                        ((CreateCommunityActivity) getActivity()).onBackClickHandle(mFeedDetail);
                    }
                    break;
                case AppConstants.FAILED:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(createCommunityResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), COMMUNITY_OWNER);
                    break;
                default:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), COMMUNITY_OWNER);
            }
        }

    }

    private void openImageOption() {
        ImageUploadFragment imageUploadFragment = new ImageUploadFragment();
        ((BaseActivity) getActivity()).replaceFragment(imageUploadFragment, R.id.create_community_container, null, true);
    }

    public void selectImageFrmCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
        mImageCaptureUri = Uri.fromFile(f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, mCAMERA_CODE);

    }

    public void selectImageFrmGallery() {
        try {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            i.setType("image/*");
            startActivityForResult(i, mGALLERY_CODE);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_LONG).show();
            checkCameraPermission();
        }
    }

    /*
    This mathod is for select image from camera and gellery
    */
    @Deprecated
    private void selectImageOption() {
        final CharSequence[] items = {"Take Selfie", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Selfie")) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
                        mImageCaptureUri = Uri.fromFile(f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        startActivityForResult(intent, mCAMERA_CODE);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_LONG).show();
                        checkCameraPermission();
                    }


                } else if (items[item].equals("Choose from Gallery")) {
                    try {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, mGALLERY_CODE);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_LONG).show();
                        checkCameraPermission();
                    }
                }
            }
        });
        builder.show();
    }

    /*
    This mathod is for set Image on view
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == mGALLERY_CODE && resultCode == Activity.RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            CropingIMG();

        } else if (requestCode == mCAMERA_CODE && resultCode == Activity.RESULT_OK) {

            System.out.println("Camera Image URI : " + mImageCaptureUri);
            CropingIMG();
        } else if (requestCode == mCROPING_CODE) {

            try {
                if (mOutPutFile.exists()) {
                    Bitmap photo = decodeFile(mOutPutFile);
                    //  btn_profile_pic.setImageBitmap(photo);//**********************set image on imageview
                    // profilepic.setImageBitmap(photo);
                    // profile.setImageBitmap(photo);//**********************set image on imageview
                    if (mImage_type == 2) {
                        mIvCover.setImageDrawable(null);
                        mIvCover.setImageBitmap(photo);
                        byte[] buffer = new byte[4096];
                        buffer = getBytesFromBitmap(photo);
                        encCoverImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                        Log.e("str", encCoverImage);
                        mCoverFlag = 1;


                    } else {
                        //   mIvlogo.setImageBitmap(photo);
                        mIvlogo.setBackground(new BitmapDrawable(getResources(), photo));
                        mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        byte[] buffer = new byte[4096];
                        buffer = getBytesFromBitmap(photo);
                        encImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                        mImage_type = 1;
                        mLogoFlag=1;
                        Log.e("str", encImage);
                    }
                    byte[] buffer = new byte[4096];


                    buffer = getBytesFromBitmap(photo);

                } else {
                    Toast.makeText(getActivity(), "Error while save image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void CropingIMG() {

        final ArrayList cropOptions = new ArrayList();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            try {
                Bitmap photo = decodeFile(mOutPutFile1);
                //  btn_profile_pic.setImageBitmap(photo);
                // profilepic.setImageBitmap(photo);
                // profile.setImageBitmap(photo);
                if (mImage_type == 2) {
                    mIvCover.setImageDrawable(null);
                    mIvCover.setImageBitmap(photo);
                } else {
                    mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    mIvlogo.setBackground(new BitmapDrawable(getResources(), photo));
                }

                //  mIvlogo.setImageBitmap(photo);


            } catch (Exception e) {
            }
/*
            Toast.makeText(this, "Cann't find image croping app", Toast.LENGTH_SHORT).show();
*/
            return;
        } else {

            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            if (mImage_type == 1) {
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
            } else {
                intent.putExtra("aspectX", 5);
                intent.putExtra("aspectY", 2);
            }
            intent.putExtra("scale", true);

            //TODO: don't use return-data tag_item_ui_for_onboarding because it's not return large image data and crash not given any message
            intent.putExtra("return-data", true);

            //Create output file here
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mOutPutFile));

            if (size >= 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = (ResolveInfo) list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, mCROPING_CODE);
            } else {
                Bitmap bmp = BitmapFactory.decodeFile(String.valueOf(mImageCaptureUri));
                //  btn_profile_pic.setImageBitmap(bmp);
                // roundBitmap = getRoundedCroppedBitmap(bmp, 500);
                // btn_profile_pic.setImageBitmap(bmp);//**********************set image on imageview
                // profile.setImageBitmap(bmp);//**********************set image on imageview
                // profilepic.setImageBitmap(bmp);
                if (mImage_type == 2) {
                    mIvCover.setImageDrawable(null);
                    mIvCover.setImageBitmap(bmp);
                } else {
                    mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    mIvlogo.setBackground(new BitmapDrawable(getResources(), bmp));
                }

                //  mIvlogo.setImageBitmap(bmp);
                //  imageView.setImageBitmap(bmp);
            }

        }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
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
    public String getScreenName() {
        return SCREEN_LABEL;
    }
}

