package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.f2prateek.rx.preferences.Preference;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 30-06-2017.
 */

public class ProfileImageDialogFragment extends BaseDialogFragment {
    @Bind(R.id.tv_profile_name)
    TextView mTvProfileName;
    @Bind(R.id.iv_profile_image_from_camera)
    ImageView mIvProfileImageFromCamera;
    @Bind(R.id.iv_profile_image_from_gall)
    ImageView mIvProfileImageFromGall;
    @Bind(R.id.tv_profile_image_save)
    TextView mTvProfileImageSave;
    @Bind(R.id.tv_profile_image_remove)
    TextView mTvProfileImageRemove;
    @Bind(R.id.tv_profile_image_close)
    TextView mTvProfileImageClose;
    private Uri mImageCaptureUri;
    @Bind(R.id.li_user_profile_images)
    LinearLayout liUserProfileImages;
    @Inject
    Preference<LoginResponse> mUserPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.profile_image_dialog_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);

        setUserProfileData(false, null);
        return view;
    }

    public void setUserProfileData(boolean isSelectedImage, Bitmap imageUrl) {
        mTvProfileImageSave.setEnabled(true);
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            mTvProfileName.setText(mUserPreference.get().getUserSummary().getFirstName() + AppConstants.SPACE + mUserPreference.get().getUserSummary().getLastName());
            liUserProfileImages.removeAllViews();
            liUserProfileImages.removeAllViewsInLayout();
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.profile_single_image, null);
            ImageView ivUserProfileImage = (ImageView) child.findViewById(R.id.iv_profile_single_image);
            Glide.with(getActivity())
                    .load(mUserPreference.get().getUserSummary().getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivUserProfileImage);
            liUserProfileImages.addView(child);
        }
        if (isSelectedImage) {
            mTvProfileImageSave.setVisibility(View.VISIBLE);
            liUserProfileImages.removeAllViews();
            liUserProfileImages.removeAllViewsInLayout();
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View child = layoutInflater.inflate(R.layout.profile_single_image, null);
            ImageView ivUserProfileImage = (ImageView) child.findViewById(R.id.iv_profile_single_image);
            ivUserProfileImage.setImageBitmap(imageUrl);
            liUserProfileImages.addView(child);
        } else {
            mTvProfileImageSave.setVisibility(View.GONE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                dismiss();
            }
        };
    }

    @OnClick(R.id.iv_profile_image_from_camera)
    public void onCameraClick() {
        ((ProfileActicity) getActivity()).selectImageFrmCamera();
       // checkCameraPermission();
    }

    @OnClick(R.id.iv_profile_image_from_gall)
    public void onGalleryClick() {
        ((ProfileActicity) getActivity()).selectImageFrmGallery();
        //checkGalleryPermission();
    }
    @OnClick(R.id.tv_gallery)
    public void onGalleryIconClick() {
        ((ProfileActicity) getActivity()).selectImageFrmGallery();
    }


    private void checkGalleryPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                ((ProfileActicity) getActivity()).selectImageFrmGallery();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            ((ProfileActicity) getActivity()).selectImageFrmGallery();
        }
    }

    private void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                ((ProfileActicity) getActivity()).selectImageFrmCamera();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            ((ProfileActicity) getActivity()).selectImageFrmCamera();
        }
    }

    @OnClick(R.id.tv_profile_image_save)
    public void onProfileImageSaveClick() {
        mTvProfileImageSave.setEnabled(false);
        ((ProfileActicity) getActivity()).requestForUpdateProfileImage();
        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_PROFILE_EDITS, GoogleAnalyticsEventActions.UPDATED_PROFILE_PICTURE, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.tv_profile_image_remove)
    public void onProfileImageRemoveClick() {
        liUserProfileImages.removeAllViews();
        liUserProfileImages.removeAllViewsInLayout();
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = layoutInflater.inflate(R.layout.challenge_image, null);
        ImageView ivUserProfileImage = (ImageView) child.findViewById(R.id.iv_feed_challenge);
        LinearLayout liImageText = (LinearLayout) child.findViewById(R.id.li_image_text);
        liImageText.setVisibility(View.GONE);
        liUserProfileImages.addView(child);
    }

    @OnClick(R.id.tv_profile_image_close)
    public void onProfileImageCloseClick() {
        dismiss();
    }
}
