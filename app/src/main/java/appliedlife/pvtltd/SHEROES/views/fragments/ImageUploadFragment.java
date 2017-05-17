
package appliedlife.pvtltd.SHEROES.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;

/**
 * Created by Dilip.Chaudhary on 23/3/17.
 */

public class ImageUploadFragment extends BaseFragment implements View.OnKeyListener {
    private static final String TAG = "ImageUploadFragment";
    private static final int BG_ALPHA = 255;

    private ImageUploadCallable mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ImageUploadCallable) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_upload, container, false);
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.layout_image_upload);
        FrameLayout frmCamera = (FrameLayout) rootView.findViewById(R.id.frm_camera);
        FrameLayout frmGallery = (FrameLayout) rootView.findViewById(R.id.frm_gallery);
        linearLayout.getBackground().setAlpha(BG_ALPHA);

        linearLayout.setOnClickListener(this);
        frmCamera.setOnClickListener(this);
        frmGallery.setOnClickListener(this);

        rootView.setOnKeyListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_image_upload: {
                onBackPress();
                break;
            }
            case R.id.frm_camera: {
                mCallback.onCameraSelection();
                onBackPress();
                break;
            }
            case R.id.frm_gallery: {
                mCallback.onGallerySelection();
                onBackPress();
                break;
            }
            default: {
                break;
            }
        }
    }
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPress();
            return true;
        } else {
            return false;
        }
    }

    public interface ImageUploadCallable {
        void onCameraSelection();

        void onGallerySelection();
    }
}
