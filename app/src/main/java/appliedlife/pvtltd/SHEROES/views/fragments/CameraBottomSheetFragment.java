package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.MasterDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.post.Config;
import appliedlife.pvtltd.SHEROES.models.entities.post.Contest;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ujjwal on 20-13-2018.
 */
public class CameraBottomSheetFragment extends BottomSheetDialogFragment {

    //region Private Variables
    private static final String SCREEN_LABEL = "CameraBottomSheetFragment";
    private static final String SHARE_DIALOG_TITLE = "Camera Dialog Title";
    //endregion


    //region Fragment LifeCycle Methods
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View containerView = View.inflate(getContext(), R.layout.dialog_camera, null);
        dialog.setContentView(containerView);
        ButterKnife.bind(this, containerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //endregion


    //region Public Static methods
    public static CameraBottomSheetFragment showDialog(AppCompatActivity activity, String sourceScreen) {
        CameraBottomSheetFragment cameraBottomSheetFragment = new CameraBottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(BaseActivity.SOURCE_SCREEN, sourceScreen);
        cameraBottomSheetFragment.show(activity.getSupportFragmentManager(), SCREEN_LABEL);
        return cameraBottomSheetFragment;
    }
    //endregion

    //region OnClicks
    @OnClick({R.id.layout_camera,R.id.text_camera})
    public void onCameraClicked() {
        if(getActivity() instanceof EditUserProfileActivity){
            ((EditUserProfileActivity) getActivity()).selectImageFrmCamera();
        }
        if(getActivity() instanceof ProfileActivity){
            ((ProfileActivity) getActivity()).selectImageFrmCamera();
        }
        dismiss();
    }

    @OnClick({R.id.layout_gallery,R.id.text_gallery})
    public void onGalleryClicked() {
        if(getActivity() instanceof EditUserProfileActivity){
            ((EditUserProfileActivity) getActivity()).selectImageFrmGallery();
        }
        if(getActivity() instanceof ProfileActivity){
            ((ProfileActivity) getActivity()).selectImageFrmGallery();
        }
        dismiss();
    }
    //endregion
}
