package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
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
import com.f2prateek.rx.preferences.Preference;

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
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostCreateResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.Docs;
import appliedlife.pvtltd.SHEROES.models.entities.community.EditCommunityRequest;
import appliedlife.pvtltd.SHEROES.models.entities.community.Member;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 11-01-2017.
 *
 * @author Ajit Kumar
 * @version 5.0
 * @since 11-01-2017.
 * Title: Create Community fragment within Create Community activity perform all the UI operation .
 * Fragment will have all UI components and operate with activity .
 */
public class CreateCommunityFragment extends BaseFragment implements CommunityView, ChangeCommunityPrivacyDialogFragment.CloseListener, CommunityTypeFragment.MyDialogFragmentListener,HomeView{


    @Bind(R.id.cb_create_community_open_check)
    CheckBox mCbopenCommunity;
    @Inject
    Preference<CreateCommunityRequest> mUserPreference;
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

    @Bind(R.id.pb_create_community_progress_bar)
    ProgressBar mProgressBar;

    int mTagFlag=0;

    String encImage;

    Long typeId;
    String encCoverImage;
    private final String mTAG = LogUtils.makeLogTag(CreateCommunityFragment.class);
    private CreateCommunityActivityIntractionListner mCreatecommunityIntractionListner;
    private int mImage_type = 0;
    private static final int mCAMERA_CODE = 101, mGALLERY_CODE = 201, mCROPING_CODE = 301;
    private Uri mImageCaptureUri;
    private File mOutPutFile = null;
    private File mOutPutFile1 = null;
    FeedDetail mFeedDetail;
    String mDescription = "";
    String mCommunityName = "";
    View view;
    long tagId[]=new long[4];

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof CreateCommunityActivityIntractionListner) {
                mCreatecommunityIntractionListner = (CreateCommunityActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(mTAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + mTAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            SheroesApplication.getAppComponent(getContext()).inject(this);
            view = inflater.inflate(R.layout.fragmentcreate_community, container, false);
            ButterKnife.bind(this, view);
            mCbopenCommunity.setChecked(true);
            mOutPutFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
            mOutPutFile1 = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
            //    mEtCreateCommunityTags.setText("Community Tag");
            createCommunityPresenter.attachView(this);


            if (null != getArguments()) {
                mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITIES_DETAIL);
                if (null != mFeedDetail) {

                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
                        metCreateCommunityName.setText(mFeedDetail.getNameOrTitle());
                    }
                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getListDescription())) {
                        metCreateCommunityDescription.setText(mFeedDetail.getListDescription());
                    }

                    if(mFeedDetail.isOwner()) {
                        mTvCreate.setText(R.string.ID_EDIT);
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
                    }
                    if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getThumbnailImageUrl())) {
                        Glide.with(mIvlogo.getContext())
                                .load((mFeedDetail.getThumbnailImageUrl()))
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>(100,100) {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                        mIvlogo.setCompoundDrawablesWithIntrinsicBounds(null, new BitmapDrawable(mIvlogo.getResources(),resource), null, null);
                                    }
                                });
                       // mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                    mCbcloseCommunity.setChecked(mFeedDetail.isClosedCommunity());
                    mCbopenCommunity.setChecked(!mFeedDetail.isClosedCommunity());
                }
                if (null != getArguments().getStringArray(AppConstants.TAG_LIST)) {
                    String[] tagsval = getArguments().getStringArray(AppConstants.TAG_LIST);
                    long tagIds[]=getArguments().getLongArray(AppConstants.TAG_ID);
                    tagId=tagIds;
                    String tagval = "";
                    for (int i = 1; i < tagsval.length; i++) {
                        if(i==1 )
                        tagval = tagval + " " + tagsval[i];
                        else
                            tagval = tagval + ", " + tagsval[i];
                    }
                    tagval=tagval.replaceAll("null","");
                    tagval=tagval.substring(0,tagval.length()-1);
                    mEtCreateCommunityTags.setText(tagval);
                }

            } else {
                genericFragmentActivityIntractionListner.onErrorOccurence((AppConstants.ERROR_OCCUR));
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
        setPrefrencesValue();
        return view;
    }


    @OnClick(R.id.iv_create_community_cross)
    public void backClick() {
        mUserPreference.delete();

        genericFragmentActivityIntractionListner.close();

    }

    @OnClick(R.id.tv_create_community_logo_upload)
    public void btnChangeLogo() {
        mImage_type = 1;
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

    @OnClick(R.id.et_create_community_tags)
    public void btnTagClick() {
        mTagFlag=1;
        CreateCommunityRequest createCommunityRequest=new CreateCommunityRequest();

        if(StringUtil.isNotNullOrEmptyString(metCreateCommunityName.getText().toString()))
        {
            createCommunityRequest.setName(metCreateCommunityName.getText().toString());

        }
        if(null !=mIvCover.getDrawable()) {
            Bitmap bitmap = ((BitmapDrawable) mIvCover.getDrawable()).getBitmap();
            byte[] buffer = new byte[4096];
            buffer = getBytesFromBitmap(bitmap);
            encCoverImage = Base64.encodeToString(buffer, Base64.DEFAULT);
            if (StringUtil.isNotNullOrEmptyString(encCoverImage)) {
                createCommunityRequest.setCoverImage(encCoverImage);

            }
        }
        if(null !=(mIvlogo.getDrawingCache())) {
            mIvlogo.buildDrawingCache();
            Bitmap bitmap1 = mIvlogo.getDrawingCache();
            //  Bitmap bitmap1 = mIvlogo.getDrawingCache();
            byte[] buffer = new byte[4096];
            buffer = getBytesFromBitmap(bitmap1);
            encImage = Base64.encodeToString(buffer, Base64.DEFAULT);
            if (StringUtil.isNotNullOrEmptyString(encImage)) {
                createCommunityRequest.setLogo(encImage);

            }
        }
        if(StringUtil.isNotNullOrEmptyString(metCreateCommunityDescription.getText().toString()))
        {
            createCommunityRequest.setDescription(metCreateCommunityDescription.getText().toString());

        }
        if(StringUtil.isNotNullOrEmptyString(mEtCommunityType.getText().toString()))
        {
            createCommunityRequest.setType(mEtCommunityType.getText().toString());

        }
        if(null !=typeId)
        {
            createCommunityRequest.setCommunityTypeId(2);
        }

        mUserPreference.set(createCommunityRequest);
        mCreatecommunityIntractionListner.callCommunityTagPage(mFeedDetail);

        //  CommunitySearchTagsFragment newFragment = new CommunitySearchTagsFragment();
    }

    @OnClick(R.id.tv_create_community_cover_img_upload)
    public void btnChangeCover() {
        mImage_type = 2;
        checkCameraPermission();
        openImageOption();
    }

    @OnClick(R.id.tv_create_community_submit)
    public void btnCreateClick() {
        mTvCreate.setVisibility(View.GONE);
       /* Intent intent = new Intent(getActivity(), CreateCommunityPostActivity.class);
        startActivity(intent);*/
        if(StringUtil.isNotNullOrEmptyString(metCreateCommunityDescription.getText().toString()) && StringUtil.isNotNullOrEmptyString(mEtCommunityType.getText().toString()) && StringUtil.isNotNullOrEmptyString(metCreateCommunityName.getText().toString()) && StringUtil.isNotNullOrEmptyString(mEtCreateCommunityTags.getText().toString())) {
            if (mTvCreate.getText().toString().equalsIgnoreCase("create")) {
                callCreateCommunitySubmit();
            } else {
                callEditCommunitySubmit();
            }
        }
        else {
            genericFragmentActivityIntractionListner.onErrorOccurence((AppConstants.BLANK_MESSAGE));
        }
        // mCreatecommunityIntractionListner.close();
    }

    void callEditCommunitySubmit() {
        mProgressBar.setVisibility(View.VISIBLE);

        List<Integer> tags = new ArrayList<>();
        tags.add(1);
        tags.add(2);
        List<Integer> tags1 = new ArrayList<>();
        tags1.add(1);
        tags1.add(2);

        List<Long> tags2 = new ArrayList<>();
        for(int i=0;i<tagId.length;i++)
        {
            if(tagId[i]>0)
                tags2.add(tagId[i]);

        }

        EditCommunityRequest editCommunityRequest = new EditCommunityRequest();
        editCommunityRequest.setCoverImageUrl(encCoverImage);

        editCommunityRequest.setLogoUrl(encImage);
        editCommunityRequest.setId((int) mFeedDetail.getIdOfEntityOrParticipant());

        editCommunityRequest.setIsActive(true);

        editCommunityRequest.setAppVersion("String");

        editCommunityRequest.setCloudMessagingId("String");

        editCommunityRequest.setCommunityTypeId(2);

        if (null != metCreateCommunityDescription.getText().toString() && StringUtil.isNotNullOrEmptyString(metCreateCommunityDescription.getText().toString())) {
            mDescription = metCreateCommunityDescription.getText().toString();
        }
        editCommunityRequest.setDescription(mDescription);
        editCommunityRequest.setDeviceUniqueId("String");
        if (mCbcloseCommunity.isChecked())
            editCommunityRequest.setIsClosed(true);
        else
            editCommunityRequest.setIsClosed(false);

        editCommunityRequest.setLastScreenName("String");

        if (null != metCreateCommunityName.getText().toString() && StringUtil.isNotNullOrEmptyString(metCreateCommunityName.getText().toString())) {
            mCommunityName = metCreateCommunityName.getText().toString();
        }
        editCommunityRequest.setName(mCommunityName);
        editCommunityRequest.setPurpose("sink more test code");


        editCommunityRequest.setScreenName("String");

        editCommunityRequest.setRemovedTags(tags1);

        editCommunityRequest.setTags(tags2);
        if (StringUtil.isNotNullOrEmptyString(mCommunityName) || StringUtil.isNotNullOrEmptyString(mDescription))
            createCommunityPresenter.postEditCommunityList(editCommunityRequest);
        else
            genericFragmentActivityIntractionListner.onErrorOccurence((AppConstants.INAVLID_DATA));

    }

    private void callCreateCommunitySubmit() {
        mProgressBar.setVisibility(View.VISIBLE);

        List<Long> tags = new ArrayList<>();
        for(int i=0;i<tagId.length;i++)
        {
            if(tagId[i]>0)
                tags.add(tagId[i]);

        }
        // tags.add(tagIds[0]);

        CreateCommunityRequest createCommunityRequest1 = new CreateCommunityRequest();
        createCommunityRequest1 = mUserPreference.get();
        CreateCommunityRequest createCommunityRequest = new CreateCommunityRequest();
        if(null !=createCommunityRequest1.getCoverImage()) {
            createCommunityRequest.setCoverImage(createCommunityRequest1.getCoverImage());

        }
        else {
            createCommunityRequest.setCoverImage(encCoverImage);

        }
        if(null !=createCommunityRequest1.getLogo()) {
            createCommunityRequest.setLogo(createCommunityRequest1.getLogo());

        }
        else {
            createCommunityRequest.setLogo(encImage);

        }

        createCommunityRequest.setAppVersion("String");

        createCommunityRequest.setCloudMessagingId("String");

        createCommunityRequest.setCommunityTypeId(2);
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

        mUserPreference.delete();

    }
    private void setPrefrencesValue()
    {
        if(null !=mUserPreference)
        {
            if(mTagFlag==0) {
                CreateCommunityRequest createCommunityRequest = new CreateCommunityRequest();
                createCommunityRequest = mUserPreference.get();
                if (null != createCommunityRequest) {
                    if (null != createCommunityRequest.getName())
                        metCreateCommunityName.setText(createCommunityRequest.getName());
                    if (null != createCommunityRequest.getDescription())
                        metCreateCommunityDescription.setText(createCommunityRequest.getDescription());
                    if (null != createCommunityRequest.getType())
                        mEtCommunityType.setText(createCommunityRequest.getType());
                    if (null != createCommunityRequest.getCoverImage()) {
                        byte[] decodedString = Base64.decode(createCommunityRequest.getCoverImage(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        mIvCover.setImageBitmap(decodedByte);
                    }
                    if (null != createCommunityRequest.getLogo()) {
                        byte[] decodedString = Base64.decode(createCommunityRequest.getLogo(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        mIvlogo.setBackground(new BitmapDrawable(getResources(), decodedByte));
                        mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    }
                    if (null != createCommunityRequest.getType()) {

                        mEtCommunityType.setText(createCommunityRequest.getType());

                    }
                }

            }


        }
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
            ChangeCommunityPrivacyDialogFragment newFragment = new ChangeCommunityPrivacyDialogFragment();
            newFragment.show(getActivity().getFragmentManager(), "dialog");
      /*      CommunityJoinRegionDialogFragment newFragment = new CommunityJoinRegionDialogFragment(this);
            newFragment.show(getActivity().getFragmentManager(), "dialog");*/
        }

    }

    @OnClick(R.id.cb_create_community_close_check)
    public void OnCloseCheckClick() {
        mCbopenCommunity.setChecked(false);
        if (mCbcloseCommunity.isChecked()) {
            // ChangeCommunityPrivacyDialogFragment newFragment = new ChangeCommunityPrivacyDialogFragment(this);
            // newFragment.show(getActivity().getFragmentManager(), "dialog");
        }


    }
/*
    @OnTouch(R.id.et_create_community_type)
    public boolean onTouch() {
        CommunityTypeFragment newFragment =new CommunityTypeFragment();
        newFragment.setListener(this);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
        return true;
    }*/

    @OnClick(R.id.et_create_community_type)
    public void onLogInBtnClick() {
        CommunityTypeFragment newFragment =new CommunityTypeFragment();
        newFragment.setListener(this);

        newFragment.show(getActivity().getFragmentManager(), "dialog");

      /*  FragmentCategoryList nextFrag= new FragmentCategoryList();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_bottom_enter_anim, 0, 0, R.anim.top_bottom_enter_anim_reverse)
                .replace(R.id.fl_fragment_container,nextFrag, SPINNER_FRAGMENT).addToBackStack(null).commit();
*/
    }

  /*  @Override
    public void getCreateCommunityResponse(LoginResponse loginResponse) {

    }*/
/*
    @Override
    public void showNwError() {

    }

    @Override
    public void dialogValue(String dilogval) {

    }


    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

    @Override
    public void startNextScreen() {

    }*/


    @Override
    public void onErrorOccurence() {

    }


    @Override
    public void onClose() {
    }

    @Override
    public void onAddFriendSubmit(String communitynm, Long typeId) {
        mEtCommunityType.setText(communitynm);
        this.typeId=typeId;


    }

    public void showTagResult(String[] tagsval) {
        String tagval = "";
        for (int i = 1; i < tagsval.length; i++) {
            tagval = tagval + " " + tagsval[i];
        }
        LogUtils.info("result-", tagval);
        mEtCreateCommunityTags.setText(tagval);

        Toast.makeText(getActivity(), tagval, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getityCommunityListSuccess(List<CommunityList> data) {

    }

    @Override
    public void getSelectedCommunityListSuccess(List<Docs> selected_community_response) {

    }

    @Override
    public void getOwnerListSuccess(List<Member> ownerListResponse) {

    }


    @Override
    public void postCreateCommunitySuccess(CreateCommunityResponse createCommunityResponse) {
        mProgressBar.setVisibility(View.GONE);
        mTvCreate.setVisibility(View.GONE);

        if (null != createCommunityResponse && StringUtil.isNotNullOrEmptyString(createCommunityResponse.getStatus())) {

            Toast.makeText(getActivity(), createCommunityResponse.getStatus(), Toast.LENGTH_LONG).show();

            genericFragmentActivityIntractionListner.close();
        }
        else
        {
            genericFragmentActivityIntractionListner.onErrorOccurence((AppConstants.INAVLID_DATA));

        }
    }

    @Override
    public void addPostCreateCommunitySuccess(CommunityPostCreateResponse createCommunityResponse) {

    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse) {

    }

    @Override
    public void postCreateCommunityOwnerSuccess(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

    }

    @Override
    public void showNwError() {

    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        //  mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void startNextScreen() {

    }

   /* @Override
    public void showError(String s) {
        mProgressBar.setVisibility(View.GONE);

    }*/

    public interface CreateCommunityActivityIntractionListner {
        void callCommunityTagPage(FeedDetail mfeed);
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
                        mIvCover.setImageBitmap(photo);
                        byte[] buffer = new byte[4096];
                        buffer = getBytesFromBitmap(photo);
                        encCoverImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                        Log.e("str", encCoverImage);

                    } else {
                        //   mIvlogo.setImageBitmap(photo);
                        mIvlogo.setBackground(new BitmapDrawable(getResources(), photo));
                        mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        byte[] buffer = new byte[4096];
                        buffer = getBytesFromBitmap(photo);
                        encImage = Base64.encodeToString(buffer, Base64.DEFAULT);
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
                if (mImage_type == 2)
                    mIvCover.setImageBitmap(photo);
                else {
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
           /* intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);*/

            //TODO: don't use return-data tag_item_ui_for_onboarding because it's not return large image data and crash not given any message
            //intent.putExtra("return-data", true);

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
                if (mImage_type == 2)
                    mIvCover.setImageBitmap(bmp);
                else {
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
}
