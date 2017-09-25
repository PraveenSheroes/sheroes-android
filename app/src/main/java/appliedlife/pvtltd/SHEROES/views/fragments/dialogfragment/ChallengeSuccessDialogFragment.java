package appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.HomeView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 24-05-2017.
 */

public class ChallengeSuccessDialogFragment extends BaseDialogFragment implements HomeView {
    private final String TAG = LogUtils.makeLogTag(ChallengeSuccessDialogFragment.class);
    @Inject
    AppUtils mAppUtils;
    @Bind(R.id.iv_add_pic)
    ImageView ivAddPic;
    @Bind(R.id.li_you_tube_link_share)
    LinearLayout liYouTubeLink;
    @Bind(R.id.et_you_tube_link)
    EditText etYouTubeLink;
    @Bind(R.id.tv_share_after_success)
    TextView tvShareAfterSuccess;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar progressBar;
    private String encodedImageUrl;
    private ChallengeDataItem mChallengeDataItem;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.challenge_success_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (null != getArguments()) {
            Bundle challenge = getArguments();
            mChallengeDataItem = challenge.getParcelable(AppConstants.SUCCESS);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    public void setImageOnHolder(Bitmap photo) {
        ivAddPic.setImageBitmap(photo);
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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismissAllowingStateLoss();//dismiss dialog on back button press
                dismiss();
            }
        };
    }

    @OnClick(R.id.tv_share_after_success)
    public void shareAfterSuccessClick() {
        progressBar.setVisibility(View.VISIBLE);
        tvShareAfterSuccess.setEnabled(false);
        String youTubeLink = etYouTubeLink.getText().toString();
        ((HomeActivity) getActivity()).updateChallengeDataWithStatus(mChallengeDataItem, AppConstants.COMPLETE, encodedImageUrl, youTubeLink);
    }

    @OnClick(R.id.li_you_tube_link_share)
    public void onYouTubeLinkShareClick() {
        liYouTubeLink.setAlpha(1);
    }

    @OnClick(R.id.iv_challenge_open_camera)
    public void onCameraClick() {
        CropImage.activity(null,AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .start(getActivity());
    }

    @OnClick(R.id.tv_select_from_gallery)
    public void onSelectFromGalleryClick() {
        CropImage.activity(null,AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .start(getActivity());
    }

}
