package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.activities.CreateStoryActivity;
import appliedlife.pvtltd.SHEROES.views.activities.EditUserProfileActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
            ((ProfileActivity) getActivity()).selectImageFrmCamera(AppConstants.ONE_CONSTANT);
        }
        if(getActivity() instanceof CreateStoryActivity){
            ((CreateStoryActivity) getActivity()).selectImageFrmCamera();
        }
        if(getActivity() instanceof CommunityPostActivity){
            ((CommunityPostActivity) getActivity()).selectImageFromCamera();
        }

        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).selectImageFrmCamera();
        }
        dismiss();
    }

    @OnClick({R.id.layout_gallery,R.id.text_gallery})
    public void onGalleryClicked() {
        if(getActivity() instanceof EditUserProfileActivity){
            ((EditUserProfileActivity) getActivity()).selectImageFrmGallery();
        }
        if(getActivity() instanceof ProfileActivity){
            ((ProfileActivity) getActivity()).openGalleryOrCamera(AppConstants.TWO_CONSTANT);
        }

        if(getActivity() instanceof CreateStoryActivity){
            ((CreateStoryActivity) getActivity()).selectImageFrmGallery();
        }
        if(getActivity() instanceof CommunityPostActivity){
            ((CommunityPostActivity) getActivity()).selectImageFromGallery();
        }
        if(getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).selectImageFrmGallery();
        }
        dismiss();
    }
    //endregion
}
