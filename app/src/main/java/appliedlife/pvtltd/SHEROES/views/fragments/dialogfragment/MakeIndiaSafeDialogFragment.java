package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.f2prateek.rx.preferences.Preference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.CommunityEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.CreateCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.DeactivateOwnerResponse;
import appliedlife.pvtltd.SHEROES.models.entities.community.OwnerListResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.LatLongWithLocation;
import appliedlife.pvtltd.SHEROES.models.entities.miscellanous.MakeIndiaSafeResponse;
import appliedlife.pvtltd.SHEROES.presenters.CreateCommunityPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.utils.AppUtils.createCommunityPostRequestBuilder;

/**
 * Created by Praveen_Singh on 28-07-2017.
 */

public class MakeIndiaSafeDialogFragment extends BaseDialogFragment implements CommunityView {
    private final String TAG = LogUtils.makeLogTag(MakeIndiaSafeDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.iv_make_india_safe_pic)
    ImageView ivMakeIndiaSafePic;
    @Bind(R.id.tv_title_make_india_safe)
    TextView tvTitleMakeIndiaSafe;
    @Bind(R.id.tv_donot_stay_quiet)
    TextView tvTvDonotStayQuiet;
    @Bind(R.id.tv_message_for_safe)
    TextView tvTvMessageForSafe;
    @Bind(R.id.tv_make_india_safe_description)
    TextView tvTvMakeIndiaSafeDescription;
    @Inject
    Preference<LoginResponse> mUserPreference;
    LatLongWithLocation mLatLongWithLocation;
    @Inject
    CreateCommunityPresenter mCreateCommunityPresenter;
    @Bind(R.id.cb_post_as_first_name)
    CheckBox mCbPostName;
    @Bind(R.id.cb_post_as_annonyms)
    CheckBox mCbPostAsAnnoyms;
    @Bind(R.id.scroll_make_india_safe_events)
    ScrollView scrollMakeIndiaSafeEvent;
    @Bind(R.id.scroll_make_india_safe_image_holder)
    ScrollView scrollMakeIndiaSafeImageHolder;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    private Long mCommunityId = 273l;
    private String encodedImageUrl;
    private String messageFirst = "Hey, I have been seeing events of women getting harassed in";
    private String messageSecond = "I think we should do something about it. How should we go ahead? #MakeIndiaSafe I shared this via SHEROES app- a women only safe space to talk openly without getting judged. You should also install the app from play store here: tinyurl.com/sheroes-app-safe-india";
    private String local = " my locality ";
    private String mCreaterType;
    private File localImageSaveForChallenge;
    private boolean isCreatedPost;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.make_india_safe, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        mCreateCommunityPresenter.attachView(this);
        scrollMakeIndiaSafeEvent.setVisibility(View.VISIBLE);
        scrollMakeIndiaSafeImageHolder.setVisibility(View.GONE);
        if (null != getArguments()) {
            Bundle bundle = getArguments();
            mLatLongWithLocation = bundle.getParcelable(AppConstants.LAT_LONG_DETAIL);
        }
        initialize();
        return view;
    }

    private void initialize() {
        mCreaterType = AppConstants.USER;
        mCbPostName.setChecked(true);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            mCbPostName.setText(mUserPreference.get().getUserSummary().getFirstName());
        }
        if (null != mLatLongWithLocation) {
            if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
                String title = getString(R.string.ID_DO_YOU_SEE) + AppConstants.SPACE + mLatLongWithLocation.getLocality() + AppConstants.SPACE + getString(R.string.ID_YOUR_LOCALITY);
                tvTitleMakeIndiaSafe.setText(title);
            } else {
                String title = getString(R.string.ID_DO_YOU_SEE) + local + getString(R.string.ID_YOUR_LOCALITY);
                tvTitleMakeIndiaSafe.setText(title);
            }
            if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
                String locality = getString(R.string.ID_NO) + AppConstants.COMMA + mLatLongWithLocation.getLocality() + AppConstants.SPACE + getString(R.string.ID_LOCALITY_NAME);
                tvTvMessageForSafe.setText(locality);
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                if (!isCreatedPost) {
                    dismiss();
                } else {
                    dismiss();
                    ((HomeActivity) getActivity()).homeOnClick();

                }
            }
        };
    }

    @OnClick(R.id.tv_click_pic_and_tell_friends)
    public void onClickPicTellFriend() {
        checkCameraPermission();
    }

    @OnClick(R.id.tv_tell_your_friends)
    public void onTellYourFriend() {
        StringBuilder shareData = new StringBuilder();
        if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
            shareData.append(messageFirst).append(AppConstants.SPACE).append(mLatLongWithLocation.getLocality()).append(AppConstants.SPACE).append(messageSecond);
        } else {
            shareData.append(messageFirst).append(local).append(messageSecond);
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(AppConstants.SHARE_MENU_TYPE);
        intent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
        startActivity(Intent.createChooser(intent, AppConstants.SHARE));
    }

    private void shareWithFriend() {
        StringBuilder shareData = new StringBuilder();
        if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
            shareData.append(messageFirst).append(AppConstants.SPACE).append(mLatLongWithLocation.getLocality()).append(AppConstants.SPACE).append(messageSecond);
        } else {
            shareData.append(messageFirst).append(local).append(messageSecond);
        }
    }

    @OnClick(R.id.cb_post_as_first_name)
    public void OnPostAsNameClick() {
        mCbPostAsAnnoyms.setChecked(false);
        mCreaterType = AppConstants.USER;
    }

    @OnClick(R.id.cb_post_as_annonyms)
    public void OnPostAnnonymsClick() {
        mCbPostName.setChecked(false);
        mCreaterType = getString(R.string.ID_COMMUNITY_ANNONYMOUS);
    }

    @OnClick(R.id.tv_make_india_safe_back)
    public void backMakeIndiaSafe() {
        if (!isCreatedPost) {
            dismiss();
        } else {
            dismiss();
            ((HomeActivity) getActivity()).homeOnClick();

        }
    }

    @OnClick(R.id.tv_share_with_friends)
    public void onShareClick() {
        if (null != mLatLongWithLocation) {
            List<String> imag = new ArrayList<>();
            Long mIdForEditPost = null;
            mLatLongWithLocation.setDescription(tvTvMakeIndiaSafeDescription.getText().toString());
            if (StringUtil.isNotNullOrEmptyString(encodedImageUrl)) {
                imag.add(encodedImageUrl);
            }
            mCreateCommunityPresenter.postCommunityList(createCommunityPostRequestBuilder(mCommunityId, mCreaterType, tvTvMakeIndiaSafeDescription.getText().toString(), imag, mIdForEditPost, null));
        }
    }

    public void setImageOnHolder(Bitmap photo, File localImageSaveForChallenge) {
        this.localImageSaveForChallenge = localImageSaveForChallenge;
        ivMakeIndiaSafePic.setImageBitmap(photo);
        scrollMakeIndiaSafeEvent.setVisibility(View.GONE);
        scrollMakeIndiaSafeImageHolder.setVisibility(View.VISIBLE);
        StringBuilder shareData = new StringBuilder();
        if (StringUtil.isNotNullOrEmptyString(mLatLongWithLocation.getLocality())) {
            shareData.append(messageFirst).append(AppConstants.SPACE).append(mLatLongWithLocation.getLocality()).append(AppConstants.SPACE).append(messageSecond);
        } else {
            shareData.append(messageFirst).append(local).append(messageSecond);
        }
        tvTvMakeIndiaSafeDescription.setText(shareData.toString());
        byte[] buffer = new byte[4096];
        if (null != photo) {
            buffer = getBytesFromBitmap(photo);
            if (null != buffer) {
                encodedImageUrl = Base64.encodeToString(buffer, Base64.DEFAULT);
            }
        }
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                ((HomeActivity) getActivity()).selectImageFrmCamera();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            ((HomeActivity) getActivity()).selectImageFrmCamera();
        }
    }

    @Override
    public void getSelectedCommunityListSuccess(List<CommunityPostResponse> selected_community_response) {

    }

    @Override
    public void getOwnerListSuccess(OwnerListResponse ownerListResponse) {

    }

    @Override
    public void createCommunitySuccess(BaseResponse baseResponse) {
        if (baseResponse instanceof CreateCommunityResponse) {
            CreateCommunityResponse createCommunityResponse = ((CreateCommunityResponse) baseResponse);
            communityPostResponse(createCommunityResponse);
        } else if (baseResponse instanceof MakeIndiaSafeResponse) {
            MakeIndiaSafeResponse makeIndiaSafeResponse = ((MakeIndiaSafeResponse) baseResponse);
            makeIndiaSafeResponse(makeIndiaSafeResponse);
        }
    }

    private void communityPostResponse(CreateCommunityResponse createCommunityResponse) {
        if (StringUtil.isNotNullOrEmptyString(createCommunityResponse.getStatus())) {
            switch (createCommunityResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    isCreatedPost = true;
                    FeedDetail feedDetail = createCommunityResponse.getFeedDetail();
                    if (null != feedDetail) {
                        mLatLongWithLocation.setEntityOrParticipantId(feedDetail.getEntityOrParticipantId());
                        mCreateCommunityPresenter.getMakeIndiaSafeFromPresenter(mAppUtils.makeIndiaSafeRequestBuilder(mLatLongWithLocation));
                    }
                    break;
                case AppConstants.FAILED:
                    break;
                default:
            }
        }
    }

    private void makeIndiaSafeResponse(MakeIndiaSafeResponse makeIndiaSafeResponse) {
        if (StringUtil.isNotNullOrEmptyString(makeIndiaSafeResponse.getStatus())) {
            switch (makeIndiaSafeResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    Uri uri = Uri.parse("file://" + localImageSaveForChallenge.getAbsolutePath());
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.putExtra(Intent.EXTRA_TEXT, mLatLongWithLocation.getDescription());
                    share.setType("image/*");
                    share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    getActivity().startActivity(Intent.createChooser(share, "Share image File"));
                    break;
                case AppConstants.FAILED:
                    break;
                default:
            }
        }
    }

    @Override
    public void getOwnerListDeactivateSuccess(DeactivateOwnerResponse deactivateOwnerResponse) {

    }

    @Override
    public void postCreateCommunityOwner(CreateCommunityOwnerResponse createCommunityOwnerResponse) {

    }

    @Override
    public void getSuccessForAllResponse(BaseResponse baseResponse, CommunityEnum communityEnum) {

    }

    @Override
    public void startProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.bringToFront();
        }
    }

    @Override
    public void stopProgressBar() {
        if (null != mProgressBar) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

}

