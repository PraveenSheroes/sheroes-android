package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.f2prateek.rx.preferences.Preference;
import com.moe.pushlibrary.MoEHelper;
import com.moe.pushlibrary.PayloadBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.LinkRenderResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageConstants;
import appliedlife.pvtltd.SHEROES.moengage.MoEngageUtills;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SelectCommunityDialogFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.DELETE_COMMUNITY_POST;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.createCommunityPostRequestBuilder;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.editCommunityPostRequestBuilder;

/**
 * Created by Ajit Kumar on 20-01-2017.
 */

public class CreateCommunityPostFragment extends BaseFragment implements SelectCommunityDialogFragment.MyDialogFragmentListener {

    @Inject
    Preference<LoginResponse> mUserPreference;
    @Bind(R.id.txt_choose_community_spinner)
    TextView mEtchoosecommunity;
    @Bind(R.id.lnr_image_container)
    LinearLayout mVg_image_container;
    @Bind(R.id.iv_community_post_icon)
    CircleImageView mIvcommunity_post_icon;
    @Bind(R.id.hor_scroll_for_community_post_images)
    HorizontalScrollView mHor_scroll_for_community_post_images;
    @Bind(R.id.iv_camera_btn_for_post_images)
    ImageView mIv_camera_btn_for_post_images;
    @Bind(R.id.tv_add_more_community_post_image)
    TextView mTv_add_more_community_post_image;
    @Bind(R.id.tv_community_post_submit)
    TextView mTv_community_post_submit;
    @Bind(R.id.fl_camera_btn_for_post_images)
    FrameLayout mFl_camera_btn_for_post_images;
    @Bind(R.id.tv_community_title)
    TextView mTvcreate_community_post;
    @Bind(R.id.tv_close_community)
    TextView mTvcreate_community_post_close;
    @Bind(R.id.lnr_community_post)
    LinearLayout lnr_community_post;
    @Bind(R.id.lnr_community_post1)
    LinearLayout lnr_community_post1;
    @Bind(R.id.lnr_community_post2)
    LinearLayout lnr_community_post2;
    @Bind(R.id.et_share_community_post_text)
    EditText mEtShareCommunityPostText;
    @Bind(R.id.iv_community_owner)
    ImageView iv_community_owner;
    @Bind(R.id.iv_community_anonymous)
    ImageView iv_community_anonymous;
    @Bind(R.id.iv_community_user)
    ImageView iv_community_user;
    @Bind(R.id.tv_community_owner)
    TextView tv_community_owner;
    @Bind(R.id.tv_community_poster_user)
    TextView tv_community_poster_user;
    @Bind(R.id.tv_community_anonomous)
    TextView tv_community_anonomous;
    @Bind(R.id.scroll_community_post)
    ScrollView scroll_community_post;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar pbCreateCommunityPost;
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

    String encCoverImage;
    private Long mCommunityId;
    private Long mIdForEditPost;
    @Inject
    CreateCommunityPresenter mCreateCommunityPresenter;
    private String mCreaterType;
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.txt_counter)
    TextView mCounterTxt;
    String mImages;
    int mImgcount = 0, mCount = 0;
    String mValue = "";
    private final String TAG = LogUtils.makeLogTag(CreateCommunityPostFragment.class);
    private static final int mCAMERA_CODE = 101, mGALLERY_CODE = 201, mCROPING_CODE = 301;
    private Uri mImageCaptureUri;
    private File mOutPutFile = null;
    private File mOutPutFile1 = null;
    ImageView mImg[] = new ImageView[6];
    List<Bitmap> newImages = new ArrayList<>();
    Button mBtncross[] = new Button[6];
    Bitmap mRoundBitmap;
    float alpha = 0.7f;
    private FeedDetail mFeedDetail;
    List<String> imageUrls;
    List<Long> imageIDs;
    List<Long> deletedImageIds = new ArrayList<>();
    private String messageForSuccess;
    private MoEHelper mMoEHelper;
    private PayloadBuilder payloadBuilder;
    private MoEngageUtills moEngageUtills;
    private int positionOfFeedItem;
    private LinkRenderResponse mLinkRenderResponse = null;
    private boolean isLinkRendered;
    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.create_community_post_fragment, container, false);
        ButterKnife.bind(this, view);
        mMoEHelper = MoEHelper.getInstance(getActivity());
        payloadBuilder = new PayloadBuilder();
        moEngageUtills = MoEngageUtills.getInstance();
        mCreateCommunityPresenter.attachView(this);
        setProgressBar(pbCreateCommunityPost);
        defaultUserSelection();
        mTvcreate_community_post.setText(R.string.ID_CREATEPOST);
        if (null != getArguments()) {
            mFeedDetail = getArguments().getParcelable(AppConstants.COMMUNITY_POST_FRAGMENT);
            if (null != mFeedDetail) {
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getNameOrTitle())) {
                    mEtchoosecommunity.setText(mFeedDetail.getNameOrTitle());
                }
                communityValue();
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCallFromName()) && AppConstants.COMMUNITIES_DETAIL.equalsIgnoreCase(mFeedDetail.getCallFromName())) {
                    mCommunityId = mFeedDetail.getIdOfEntityOrParticipant();
                    mTvcreate_community_post.setText(R.string.ID_CREATEPOST);
                    messageForSuccess = getString(R.string.ID_POSTED);
                } else {
                    checkIntentWithImageUrls();
                    messageForSuccess = getString(R.string.ID_EDITED);
                }
            }
        } else {
            mTvcreate_community_post.setText(R.string.ID_CREATEPOST);
            messageForSuccess = getString(R.string.ID_POSTED);
        }
        mOutPutFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        mOutPutFile1 = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        iv_community_owner.setAlpha(alpha);
        iv_community_anonymous.setAlpha(alpha);
        mEtShareCommunityPostText.addTextChangedListener(new TextWatcher() {
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

                    if (StringUtil.isNotNullOrEmptyString(mEtShareCommunityPostText.getText().toString()) && !isLinkRendered) {
                        String editTextDescription = mEtShareCommunityPostText.getText().toString().trim();
                        if (editTextDescription.contains("https") || editTextDescription.contains("Http")) {
                            int indexOfFirstHttp = AppUtils.findNthIndexOf(editTextDescription.toLowerCase(), "https", 1);
                            int urlLength=getUrlLength(editTextDescription,indexOfFirstHttp);
                            if(urlLength<=editTextDescription.length()) {
                                String httpString = editTextDescription.substring(indexOfFirstHttp, urlLength);
                                if (mAppUtils.checkUrl(httpString)) {
                                    mCreateCommunityPresenter.linkRenderFromPresenter(mAppUtils.linkRequestBuilder(httpString));
                                }
                            }
                        } else if (editTextDescription.contains("www") || editTextDescription.contains("WWW")) {
                            int indexOfFirstWWW = AppUtils.findNthIndexOf(editTextDescription.toLowerCase(), "www", 1);
                            int urlLength=getUrlLength(editTextDescription,indexOfFirstWWW);
                            if(urlLength<=editTextDescription.length()) {
                                String wwwString = editTextDescription.substring(indexOfFirstWWW,urlLength);
                            if (mAppUtils.checkWWWUrl(wwwString)) {
                                mCreateCommunityPresenter.linkRenderFromPresenter(mAppUtils.linkRequestBuilder(wwwString));
                            }
                            }
                        }
                    }

                } else {
                    mCounterTxt.setVisibility(View.GONE);
                }
            }
        });
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            String userImage = mUserPreference.get().getUserSummary().getPhotoUrl();
            Glide.with(this)
                    .load(userImage)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(iv_community_user);

            Glide.with(this).load(userImage).transform(new CommunityOpenAboutFragment.CircleTransform(getActivity())).into(iv_community_user);
            if (StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getFirstName())) {
                tv_community_poster_user.setText(mUserPreference.get().getUserSummary().getFirstName());
            }
        }
        // iv_community_user.setImageBitmap(loginResponse);
        return view;
    }
    private int getUrlLength(String editTextDescription,int indexOfFirstPattern)
    {
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
    private void communityValue() {
        mIvcommunity_post_icon.setCircularImage(true);
        mIvcommunity_post_icon.bindImage(mFeedDetail.getThumbnailImageUrl());
        // tv_community_owner.setText(mFeedDetail.getTitle());
        Glide.with(this).load(mFeedDetail.getThumbnailImageUrl()).transform(new CommunityOpenAboutFragment.CircleTransform(getActivity())).into(iv_community_owner);
        if (mFeedDetail.isOwner()) {
            lnr_community_post1.setVisibility(View.VISIBLE);
        } else {
            lnr_community_post1.setVisibility(View.GONE);
        }
    }

    private void checkIntentWithImageUrls() {
        if (null != mFeedDetail) {
            mCreateCommunityPresenter.getSelectCommunityFromPresenter(mAppUtils.selectCommunityRequestBuilder());
            mVg_image_container.removeAllViews();
            mVg_image_container.removeAllViewsInLayout();
            mTvcreate_community_post.setText(getString(R.string.ID_EDIT_POST));
            mCommunityId = mFeedDetail.getCommunityId();
            mIdForEditPost = mFeedDetail.getIdOfEntityOrParticipant();
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getListDescription())) {
                mEtShareCommunityPostText.setText(mFeedDetail.getListDescription());
                mEtShareCommunityPostText.setSelection(mFeedDetail.getListDescription().length());
                mCounterTxt.setVisibility(View.VISIBLE);
                mCounterTxt.setText(String.valueOf(AppConstants.MAX_WORD_COUNTER - mFeedDetail.getListDescription().length()));
            }
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getPostCommunityName())) {
                mEtchoosecommunity.setText(mFeedDetail.getPostCommunityName());
                tv_community_owner.setText(mFeedDetail.getPostCommunityName());
            }
            imageUrls = mFeedDetail.getImageUrls();
            imageIDs = mFeedDetail.getImagesIds();
            if (StringUtil.isNotEmptyCollection(imageUrls)) {
                mImgcount = imageUrls.size();
                for (int i = 0; i < imageUrls.size(); i++) {
                    LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View layout2 = layoutInflater.inflate(R.layout.imagevie_with_cross, null);
                    mIv_camera_btn_for_post_images.setVisibility(View.GONE);
                    mTv_add_more_community_post_image.setVisibility(View.VISIBLE);
                    final int finalI = i;
                    mImg[finalI] = (ImageView) layout2.findViewById(R.id.customView);
                    mBtncross[finalI] = (Button) layout2.findViewById(R.id.button1);
                    mImg[finalI].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    mBtncross[finalI].setTag(AppConstants.IMAGE + finalI);
                    mBtncross[finalI].setOnClickListener(mCorkyListener);
                    Glide.with(this)
                            .load(imageUrls.get(i)).asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .skipMemoryCache(true).into(mImg[finalI]);
                    mBtncross[finalI].setTag("Img" + finalI);
                    mBtncross[finalI].setOnClickListener(mCorkyListener);
                    mVg_image_container.addView(layout2);
                }
            }
            if (mFeedDetail.isOgVideoLinkB()) {
                mLinkRenderResponse = new LinkRenderResponse();

                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getOgTitleS())) {
                    mLinkRenderResponse.setOgTitleS(mFeedDetail.getOgTitleS());
                    tvLinkTitle.setText(mLinkRenderResponse.getOgTitleS());
                }
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getOgDescriptionS())) {
                    mLinkRenderResponse.setOgDescriptionS(mFeedDetail.getOgDescriptionS());
                    tvLinkSubTitle.setText(mLinkRenderResponse.getOgDescriptionS());
                }
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getOgImageUrlS())) {
                    mLinkRenderResponse.setOgVideoLinkB(mFeedDetail.isOgVideoLinkB());
                    mLinkRenderResponse.setOgImageUrlS(mFeedDetail.getOgImageUrlS());
                    mLinkRenderResponse.setOgRequestedUrlS(mFeedDetail.getOgRequestedUrlS());
                    Glide.with(this)
                            .load(mLinkRenderResponse.getOgImageUrlS()).asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .skipMemoryCache(true)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
                                    ivLinkThumbnail.setImageBitmap(profileImage);
                                    ivLinkThumbnail.setVisibility(View.VISIBLE);
                                    pbLink.setVisibility(View.GONE);
                                }
                            });
                }
            }
        }
    }

    public void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                LogUtils.info("testing", "Permission is revoked");

            }
        }

    }

    @OnClick(R.id.lnr_community_post)
    public void postAsName() {
        defaultUserSelection();

    }

    private void defaultUserSelection() {
        iv_community_user.setAlpha(1.0f);
        tv_community_poster_user.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        tv_community_anonomous.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));
        tv_community_owner.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));
        iv_community_owner.setAlpha(alpha);
        iv_community_anonymous.setAlpha(alpha);

        scroll_community_post.fullScroll(ScrollView.FOCUS_DOWN);
        scroll_community_post.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mTv_community_post_submit.setVisibility(View.VISIBLE);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get()) {
            LoginResponse loginResponse = mUserPreference.get();
            if (null != loginResponse && null != loginResponse.getUserSummary()) {
                UserSummary userSummary = loginResponse.getUserSummary();
                if (StringUtil.isNotNullOrEmptyString(userSummary.getFirstName())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(getString(R.string.ID_POST_AS)).append(AppConstants.SPACE).append(userSummary.getFirstName());
                    mTv_community_post_submit.setText(stringBuilder.toString());
                }
            }
        }
        mCreaterType = AppConstants.USER;
    }

    @OnClick(R.id.lnr_community_post1)
    public void postAsOwner() {
        tv_community_owner.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        iv_community_owner.setAlpha(1.0f);

        tv_community_anonomous.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));
        tv_community_poster_user.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));
        tv_community_poster_user.setAlpha(alpha);
        iv_community_anonymous.setAlpha(alpha);
        scroll_community_post.fullScroll(ScrollView.FOCUS_DOWN);

        scroll_community_post.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        mTv_community_post_submit.setText("POST AS COMMUNITY");
        mTv_community_post_submit.setVisibility(View.VISIBLE);
        mCreaterType = "COMMUNITY_OWNER";
    }

    @OnClick(R.id.lnr_community_post2)
    public void postAsAnonomous() {
        tv_community_anonomous.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        scroll_community_post.fullScroll(ScrollView.FOCUS_DOWN);
        scroll_community_post.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        tv_community_owner.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));
        tv_community_poster_user.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey2));
        tv_community_poster_user.setAlpha(alpha);
        iv_community_owner.setAlpha(alpha);
        iv_community_anonymous.setAlpha(1.0f);
        mTv_community_post_submit.setText("POST AS ANONYMOUS");
        mTv_community_post_submit.setVisibility(View.VISIBLE);
        mCreaterType = "ANONYMOUS";

    }

    @OnClick(R.id.tv_close_community)
    public void onCloseClick() {
        ((CreateCommunityPostActivity) getActivity()).editedSuccessFully(mFeedDetail);
    }

    @OnClick(R.id.tv_add_more_community_post_image)
    public void addMoreClick() {
       // checkCameraPermission();
        openImageOption();
    }

    @OnClick(R.id.fl_camera_btn_for_post_images)
    public void camerabtnClick() {
      //  checkCameraPermission();
        openImageOption();
    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)  == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                LogUtils.info("testing", "Permission is revoked");
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            LogUtils.info("testing", "Permission is already granted");

        }
    }

    @TargetApi(AppConstants.ANDROID_SDK_24)
    @OnClick(R.id.tv_community_post_submit)
    public void communityPostSubmitClick() {
        if (null != mCommunityId && null != mCreaterType && StringUtil.isNotNullOrEmptyString(mCreaterType) && StringUtil.isNotNullOrEmptyString(mEtShareCommunityPostText.getText().toString())) {
            pbCreateCommunityPost.setVisibility(View.VISIBLE);
            String description = mEtShareCommunityPostText.getText().toString();
            if (null != mFeedDetail) {
                if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCallFromName()) && mFeedDetail.getCallFromName().equalsIgnoreCase(AppConstants.FEED_COMMUNITY_POST)) {
                    List<String> imag = new ArrayList<>();
                    if (StringUtil.isNotEmptyCollection(newImages)) {
                        for (Bitmap bitmap : newImages) {
                            byte[] buffer = new byte[4096];
                            if (null != bitmap) {
                                buffer = getBytesFromBitmap(bitmap);
                                if (null != buffer) {
                                    encCoverImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                                    if (StringUtil.isNotNullOrEmptyString(encCoverImage)) {
                                        imag.add(encCoverImage);
                                    }
                                }
                            }
                        }
                    }
                    mCreateCommunityPresenter.editCommunityPost(editCommunityPostRequestBuilder(mCommunityId, mCreaterType, description, imag, mIdForEditPost, deletedImageIds, mLinkRenderResponse));
                } else {
                    List<String> imag = new ArrayList<>();
                    for (int i = 0; i < mImg.length; i++) {
                        if (null != mImg[i]) {
                            Bitmap bitmap = ((BitmapDrawable) mImg[i].getDrawable()).getBitmap();
                            byte[] buffer = new byte[4096];
                            buffer = getBytesFromBitmap(bitmap);
                            encCoverImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                            if (StringUtil.isNotNullOrEmptyString(encCoverImage)) {
                                imag.add(encCoverImage);
                            }
                        }
                    }
                    mCreateCommunityPresenter.postCommunityList(createCommunityPostRequestBuilder(mCommunityId, mCreaterType, description, imag, mIdForEditPost, mLinkRenderResponse));
                    ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_CREATED_CONTENT, GoogleAnalyticsEventActions.CREATED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
                }
            } else {
                List<String> imag = new ArrayList<>();
                for (int i = 0; i < mImg.length; i++) {
                    if (null != mImg[i]) {
                        Bitmap bitmap = ((BitmapDrawable) mImg[i].getDrawable()).getBitmap();
                        byte[] buffer = new byte[4096];
                        buffer = getBytesFromBitmap(bitmap);
                        encCoverImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                        if (StringUtil.isNotNullOrEmptyString(encCoverImage)) {
                            imag.add(encCoverImage);
                        }
                    }
                }
                mCreateCommunityPresenter.postCommunityList(createCommunityPostRequestBuilder(mCommunityId, mCreaterType, description, imag, mIdForEditPost, mLinkRenderResponse));
                ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_CREATED_CONTENT, GoogleAnalyticsEventActions.CREATED_COMMUNITY_POST, AppConstants.EMPTY_STRING);
            }
            mTv_community_post_submit.setEnabled(false);
        } else {
            mTv_community_post_submit.setEnabled(true);
            mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(AppConstants.BLANK_MESSAGE, FeedParticipationEnum.ERROR_CREATE_COMMUNITY);
        }
    }

    @OnClick(R.id.txt_choose_community_spinner)
    public void spnOnClick() {
        showDialog();
    }

    @Override
    public void onAddCommunityDetailSubmit(CommunityPostResponse communityPostResponse) {
        mEtchoosecommunity.setText(communityPostResponse.getTitle());
        mEtchoosecommunity.setTextColor(Color.BLACK);
      /*  Typeface face = Typeface.createFromAsset(getActivity().getAssets(),
                "Roboto-Medium.ttf");
        mEtchoosecommunity.setTypeface(face);*/
        mEtchoosecommunity.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

        mCommunityId = Long.parseLong(communityPostResponse.getId());
        mImages = communityPostResponse.getLogo();
        mIvcommunity_post_icon.setCircularImage(true);
        mIvcommunity_post_icon.bindImage(mImages);
        tv_community_owner.setText(communityPostResponse.getTitle());
        Glide.with(this).load(mImages).transform(new CommunityOpenAboutFragment.CircleTransform(getActivity())).into(iv_community_owner);
        if (communityPostResponse.isOwner()) {
            lnr_community_post1.setVisibility(View.VISIBLE);
        } else {
            lnr_community_post1.setVisibility(View.GONE);
        }
    }


    @Override
    public void getSelectedCommunityListSuccess(List<CommunityPostResponse> selected_community_response) {
        if (StringUtil.isNotEmptyCollection(selected_community_response)) {
            if (null != mFeedDetail) {
                for (CommunityPostResponse communityPostResponse : selected_community_response) {
                    long communityId = Long.parseLong(communityPostResponse.getId());
                    if (mFeedDetail.getCommunityId() == communityId) {
                        mImages = communityPostResponse.getLogo();
                        break;
                    }
                }
                if (StringUtil.isNotNullOrEmptyString(mImages)) {
                    mIvcommunity_post_icon.setCircularImage(true);
                    mIvcommunity_post_icon.bindImage(mImages);
                    Glide.with(this).load(mImages).transform(new CommunityOpenAboutFragment.CircleTransform(getActivity())).into(iv_community_owner);
                }
            }
        }
    }

    @Override
    public void getOwnerListSuccess(OwnerListResponse ownerListResponse) {

    }

    public void createCommunitySuccess(BaseResponse baseResponse) {
        if (baseResponse instanceof CreateCommunityResponse) {
            pbCreateCommunityPost.setVisibility(View.GONE);
            mTv_community_post_submit.setEnabled(true);
            CreateCommunityResponse createCommunityResponse = ((CreateCommunityResponse) baseResponse);
            communityPostResponse(createCommunityResponse);
        } else if (baseResponse instanceof LinkRenderResponse) {
            LinkRenderResponse linkRenderResponse = ((LinkRenderResponse) baseResponse);
            linkRenderResponse(linkRenderResponse);
        }

    }

    private void communityPostResponse(CreateCommunityResponse createCommunityResponse) {
        if (StringUtil.isNotNullOrEmptyString(createCommunityResponse.getStatus())) {
            switch (createCommunityResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    mTv_community_post_submit.setVisibility(View.VISIBLE);
                    afterSuccessCommunityPost(createCommunityResponse);
                    break;
                case AppConstants.FAILED:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(createCommunityResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), DELETE_COMMUNITY_POST);
                    break;
                default:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), DELETE_COMMUNITY_POST);
            }
        } else {
            mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), DELETE_COMMUNITY_POST);
        }
    }

    private void linkRenderResponse(LinkRenderResponse linkRenderResponse) {
        if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getStatus())) {
            switch (linkRenderResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    isLinkRendered = true;
                    cardViewLinkRender.setVisibility(View.VISIBLE);
                    scroll_community_post.post(new Runnable() {
                        public void run() {
                            scroll_community_post.fullScroll(scroll_community_post.FOCUS_DOWN);
                        }
                    });
                    scroll_community_post.scrollTo(0, scroll_community_post.getBottom() + 1);
                    mLinkRenderResponse = linkRenderResponse;
                    if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getOgTitleS())) {
                        tvLinkTitle.setText(linkRenderResponse.getOgTitleS());
                    }
                    if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getOgDescriptionS())) {
                        tvLinkSubTitle.setText(linkRenderResponse.getOgDescriptionS());
                    }
                    if (StringUtil.isNotNullOrEmptyString(linkRenderResponse.getOgImageUrlS())) {
                        Glide.with(this)
                                .load(linkRenderResponse.getOgImageUrlS()).asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .skipMemoryCache(true)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap profileImage, GlideAnimation glideAnimation) {
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
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), DELETE_COMMUNITY_POST);
            }
        } else {
            mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), DELETE_COMMUNITY_POST);
        }
    }

    @OnClick(R.id.tv_close_link)
    public void tvCloseLink() {
        mLinkRenderResponse = null;
        cardViewLinkRender.setVisibility(View.GONE);
    }

    private void afterSuccessCommunityPost(CreateCommunityResponse createCommunityResponse) {
        if (null != mFeedDetail) {
            positionOfFeedItem = mFeedDetail.getItemPosition();
            moEngageUtills.entityMoEngageCreatePost(getActivity(), mMoEHelper, payloadBuilder, mFeedDetail.getNameOrTitle(), mFeedDetail.getIdOfEntityOrParticipant(), mFeedDetail.getCommunityId(), mFeedDetail.isClosedCommunity(), MoEngageConstants.COMMUNITY_POST_TAG, TAG);
            Toast.makeText(getActivity(), messageForSuccess, Toast.LENGTH_LONG).show();
            if (StringUtil.isNotNullOrEmptyString(mFeedDetail.getCallFromName()) && AppConstants.COMMUNITIES_DETAIL.equalsIgnoreCase(mFeedDetail.getCallFromName())) {
                FeedDetail localFeed = createCommunityResponse.getFeedDetail();
                mFeedDetail = createCommunityResponse.getFeedDetail();
                mFeedDetail.setIdOfEntityOrParticipant(localFeed.getCommunityId());
            } else {
                mFeedDetail = createCommunityResponse.getFeedDetail();
            }
            if (messageForSuccess.equalsIgnoreCase(getString(R.string.ID_POSTED))) {
                mFeedDetail.setFromHome(true);
            }
            mFeedDetail.setItemPosition(positionOfFeedItem);
            ((CreateCommunityPostActivity) getActivity()).editedSuccessFully(mFeedDetail);
        } else {
            Toast.makeText(getActivity(), getString(R.string.ID_POSTED), Toast.LENGTH_LONG).show();
            ((CreateCommunityPostActivity) getActivity()).editedSuccessFully(null);
            if (null != createCommunityResponse.getFeedDetail()) {
                FeedDetail localFeed = createCommunityResponse.getFeedDetail();
                moEngageUtills.entityMoEngageCreatePost(getActivity(), mMoEHelper, payloadBuilder, localFeed.getNameOrTitle(), localFeed.getIdOfEntityOrParticipant(), localFeed.getCommunityId(), localFeed.isClosedCommunity(), MoEngageConstants.COMMUNITY_POST_TAG, TAG);
            }
        }
    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse) {
    }

    @Override
    public void postCreateCommunityOwner(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

    }

    public void closeDialog(String dialogType, Context cn) {
        LogUtils.info("click", dialogType);
        Toast.makeText(cn, dialogType, Toast.LENGTH_LONG).show();
        mValue = dialogType;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
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

    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        try {
            if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
                finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                        false);
            else
                finalBitmap = bitmap;

            Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                    finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                    finalBitmap.getHeight());

            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setDither(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.parseColor("#BAB399"));
            canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f,
                    finalBitmap.getHeight() / 2 + 0.7f,
                    finalBitmap.getWidth() / 2 + 0.1f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(finalBitmap, rect, rect, paint);
            return output;

        } catch (Exception e) {
            Bitmap bm = null;
            return bm;
        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void openImageOption() {
        ImageUploadFragment imageUploadFragment = new ImageUploadFragment();
        ((BaseActivity) getActivity()).replaceFragment(imageUploadFragment, R.id.create_community_post_container, null, true);
    }

    public void selectImageFrmCamera() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
                    mImageCaptureUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    startActivityForResult(intent, mCAMERA_CODE);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please open again for required permission", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
                mImageCaptureUri = Uri.fromFile(f);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                startActivityForResult(intent, mCAMERA_CODE);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Please open again for required permission", Toast.LENGTH_LONG).show();
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "SHEROES_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    public void selectImageFrmGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(getActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                try {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, mGALLERY_CODE);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Please open again for required permission", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            try {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, mGALLERY_CODE);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Please open again for required permission", Toast.LENGTH_LONG).show();
            }
        }
    }

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
                    }

                } else if (items[item].equals("Choose from Gallery")) {
                    try {
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, mGALLERY_CODE);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Permission Required", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
        builder.show();
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == mGALLERY_CODE && resultCode == Activity.RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            CropingIMG();

        } else if (requestCode == mCAMERA_CODE && resultCode == Activity.RESULT_OK) {

            System.out.println("Camera Image URI : " + mImageCaptureUri);
            CropingIMG();
        } else if (requestCode == mCROPING_CODE && resultCode == Activity.RESULT_OK) {

            try {
                if (mOutPutFile.exists()) {
                    setImages();

                } else {
                    Toast.makeText(getActivity(), "Error while save image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    public void setImages(File file) {
        Bitmap photo = decodeFile(file);
        mRoundBitmap = getRoundedCroppedBitmap(photo, 500);
        //  btn_profile_pic.setImageBitmap(photo);//**********************set image on imageview
        // profilepic.setImageBitmap(photo);
        // profile.setImageBitmap(photo);//**********************set image on imageview
        View layout2 = LayoutInflater.from(getActivity()).inflate(R.layout.imagevie_with_cross, mHor_scroll_for_community_post_images, false);
        // View layout3 = LayoutInflater.from(getActivity()).inflate(R.layout.addimage, mhor_scroll_for_community_post_images, false);
        mIv_camera_btn_for_post_images.setVisibility(View.GONE);
        mFl_camera_btn_for_post_images.setVisibility(View.GONE);
        mTv_add_more_community_post_image.setVisibility(View.VISIBLE);
        if (mImgcount == 4) {
            mTv_add_more_community_post_image.setVisibility(View.VISIBLE);
            mTv_add_more_community_post_image.setText("5/5");
        }
        if (mImgcount == 3) {
            mTv_add_more_community_post_image.setVisibility(View.VISIBLE);
            mTv_add_more_community_post_image.setText("4/5 " + getString(R.string.ID_ADDMORE));
        }
        if (mImgcount == 2) {
            mTv_add_more_community_post_image.setVisibility(View.VISIBLE);
            mTv_add_more_community_post_image.setText("3/5 " + getString(R.string.ID_ADDMORE));
        }
        if (mImgcount == 1) {
            mTv_add_more_community_post_image.setVisibility(View.VISIBLE);
            mTv_add_more_community_post_image.setText("2/5 " + getString(R.string.ID_ADDMORE));
        }
        if (mImgcount == 0) {
            mTv_add_more_community_post_image.setVisibility(View.VISIBLE);
            mTv_add_more_community_post_image.setText("1/5 " + getString(R.string.ID_ADDMORE));
        }
        mHor_scroll_for_community_post_images.setVisibility(View.VISIBLE);
        mHor_scroll_for_community_post_images.setHorizontalScrollBarEnabled(false);

        mImg[mImgcount] = (ImageView) layout2.findViewById(R.id.customView);
        mBtncross[mImgcount] = (Button) layout2.findViewById(R.id.button1);
        mImg[mImgcount].setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImg[mImgcount].setImageBitmap(photo);
        newImages.add(photo);
        mBtncross[mImgcount].setTag("Img" + mImgcount);
        mBtncross[mImgcount].setOnClickListener(mCorkyListener);

        mVg_image_container.addView(layout2);

        // layout2.setOnClickListener(myClickListner);
        // mygallery.addView(layout3);
        mImgcount++;
        byte[] buffer = new byte[4096];

        mCount++;

    }

    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        public void onClick(View v) {
            mTv_add_more_community_post_image.setVisibility(View.VISIBLE);

            if (v.getTag().equals("Img0")) {
                if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getImagesIds()) && mFeedDetail.getImagesIds().size() > 0 && null != mFeedDetail.getImagesIds().get(0)) {
                    deletedImageIds.add(mFeedDetail.getImagesIds().get(0));
                    if (newImages.size() > 0)
                        newImages.remove(0);
                }
                mImg[0].setVisibility(View.GONE);
                mBtncross[0].setVisibility(View.GONE);
                mImg[0] = null;
                mImgcount--;
                //do stuff
            } else if (v.getTag().equals("Img1")) {
                if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getImagesIds()) && mFeedDetail.getImagesIds().size() > 1 && null != mFeedDetail.getImagesIds().get(1)) {
                    deletedImageIds.add(mFeedDetail.getImagesIds().get(1));
                    if (newImages.size() > 1)
                        newImages.remove(1);
                }
                mImg[1].setVisibility(View.GONE);
                mBtncross[1].setVisibility(View.GONE);
                mImg[1] = null;
                mImgcount--;

                //do something else
            } else if (v.getTag().equals("Img2")) {
                if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getImagesIds()) && mFeedDetail.getImagesIds().size() > 2 && null != mFeedDetail.getImagesIds().get(2)) {
                    deletedImageIds.add(mFeedDetail.getImagesIds().get(2));
                    if (newImages.size() > 2)
                        newImages.remove(2);
                }
                mImg[2].setVisibility(View.GONE);
                mBtncross[2].setVisibility(View.GONE);
                mImg[2] = null;
                mImgcount--;

                //do something else
            } else if (v.getTag().equals("Img3")) {
                if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getImagesIds()) && mFeedDetail.getImagesIds().size() > 3 && null != mFeedDetail.getImagesIds().get(3)) {
                    deletedImageIds.add(mFeedDetail.getImagesIds().get(3));
                    if (newImages.size() > 3)
                        newImages.remove(3);
                }
                mImg[3].setVisibility(View.GONE);
                mBtncross[3].setVisibility(View.GONE);
                mImg[3] = null;
                mImgcount--;
                //do something else
            } else if (v.getTag().equals("Img4")) {
                if (null != mFeedDetail && StringUtil.isNotEmptyCollection(mFeedDetail.getImagesIds()) && mFeedDetail.getImagesIds().size() > 4 && null != mFeedDetail.getImagesIds().get(4)) {
                    deletedImageIds.add(mFeedDetail.getImagesIds().get(4));
                    if (newImages.size() > 4)
                        newImages.remove(4);
                }
                mImg[4].setVisibility(View.GONE);
                mBtncross[4].setVisibility(View.GONE);
                mImg[4] = null;
                //do something else
                mImgcount--;

            }
            if (mImgcount == 0) {
                mIv_camera_btn_for_post_images.setVisibility(View.VISIBLE);
                mIv_camera_btn_for_post_images.setImageResource(R.drawable.ic_camera_icon_with_rectangle);
                mFl_camera_btn_for_post_images.setVisibility(View.VISIBLE);
                mTv_add_more_community_post_image.setVisibility(View.GONE);
            }
            mTv_add_more_community_post_image.setText(mImgcount + "/5 " + getString(R.string.ID_ADDMORE));

            // do something when the button is clicked
            // Yes we will handle click here but which button clicked??? We don't know

        }
    };

    private void CropingIMG() {

        final ArrayList cropOptions = new ArrayList();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            try {
                Bitmap photo = decodeFile(mOutPutFile1);
                mVg_image_container.setVisibility(View.VISIBLE);
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(photo);
                imageView.setImageBitmap(photo);
                mVg_image_container.addView(imageView);
                byte[] buffer = new byte[4096];
                buffer = getBytesFromBitmap(photo);

            } catch (Exception e) {
            }
/*
            Toast.makeText(this, "Cann't find image croping app", Toast.LENGTH_SHORT).show();
*/
            return;
        } else {
            intent.setData(mImageCaptureUri);
            /*intent.putExtra("outputX", 512);
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
                mVg_image_container.setVisibility(View.VISIBLE);
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(bmp);
                imageView.setImageBitmap(bmp);
                mVg_image_container.addView(imageView);
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


    void showDialog() {
        SelectCommunityDialogFragment newFragment = new SelectCommunityDialogFragment();
        newFragment.setListener(this);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }

}
