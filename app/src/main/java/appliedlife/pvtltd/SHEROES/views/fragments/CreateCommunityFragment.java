package appliedlife.pvtltd.SHEROES.views.fragments;

import android.Manifest;
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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.CreateCommunityPostActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CreateCommunityView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Ajit Kumar on 11-01-2017.
 *
 * @author Ajit Kumar
 * @version 5.0
 * @since 11-01-2017.
 * Title: Create Community fragment within Create Community activity perform all the UI operation .
 * Fragment will have all UI components and operate with activity .
 */
public class CreateCommunityFragment extends BaseFragment implements CreateCommunityView,ChangeCommunityPrivacyDialogFragment.CloseListener,CommunityTypeFragment.MyDialogFragmentListener {

    @Bind(R.id.cb_create_community_open_check)
    CheckBox mopen_community;

    @Bind(R.id.cb_create_community_close_check)
    CheckBox mclose_community;

    @Bind(R.id.tv_create_community_cover_img_upload)
    TextView mbtn_community_cover_img;

    @Bind(R.id.tv_create_community_logo_upload)
    TextView mbtn_community_logo_img;

    @Bind(R.id.iv_create_community_cover_img)
    ImageView mimg_cover;

    @Bind(R.id.iv_create_community_logo)
    ImageView mimagelogo;

    @Bind(R.id.tv_create_community_submit)
    TextView mbtn_create;

    @Bind(R.id.iv_create_community_cross)
    FrameLayout miv_btn_cross;

    @Bind(R.id.et_create_community_type)
    EditText met_community_type;

    private final String TAG = LogUtils.makeLogTag(CreateCommunityFragment.class);
    private CreateCommunityActivityIntractionListner mCreatecommunityIntractionListner;
    private int mimage_type=0;

    private static final int mCAMERA_CODE = 101, mGALLERY_CODE = 201, mCROPING_CODE = 301;
    private Uri mImageCaptureUri;
    private File moutPutFile = null;
    private File moutPutFile1 = null;


    public static CreateCommunityFragment createInstance(int itemsCount) {
        CreateCommunityFragment createCommunityFragment = new CreateCommunityFragment();
        return createCommunityFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof CreateCommunityActivityIntractionListner) {
                mCreatecommunityIntractionListner = (CreateCommunityActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragmentcreate_community, container, false);
        ButterKnife.bind(this, view);
        moutPutFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        moutPutFile1 = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getExternalStoragePermission();
        Fabric.with(getActivity(), new Crashlytics());
        return view;
    }
    @OnClick(R.id.iv_create_community_cross)
    public void backClick()
    {
        mCreatecommunityIntractionListner.close();

    }
    @OnClick(R.id.tv_create_community_logo_upload)
    public void  btnChangeLogo()
    {
        mimage_type=1;
     checkStoragePermission();
        selectImageOption();
    }

    @OnClick(R.id.tv_create_community_cover_img_upload)
    public void btnChangeCover() {
        mimage_type=2;
      checkCameraPermission();
        selectImageOption();
    }
    @OnClick(R.id.tv_create_community_submit)
    public void btnCreateClick()
    {
        Intent intent = new Intent(getActivity(), CreateCommunityPostActivity.class);
        startActivity(intent);
    }
    public void checkStoragePermission()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                Log.e("testing", "Permission is revoked");


            }
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                Log.e("testing", "Permission is revoked");

            }
        }

    }
    private void checkCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Log.e("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                Log.e("testing", "Permission is revoked");

            }

        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("testing", "Permission is already granted");

        }
        checkStoragePermission();
    }
    @OnClick(R.id.cb_create_community_open_check)
    public void OnOpenCheckClick() {
        mclose_community.setChecked(false);
        if(mopen_community.isChecked())
        {
            ChangeCommunityPrivacyDialogFragment newFragment = new ChangeCommunityPrivacyDialogFragment(this);
            newFragment.show(getActivity().getFragmentManager(), "dialog");
        }

    }

    @OnClick(R.id.cb_create_community_close_check)
    public void OnCloseCheckClick() {
        mopen_community.setChecked(false);
        if(mclose_community.isChecked())
        {
            ChangeCommunityPrivacyDialogFragment newFragment = new ChangeCommunityPrivacyDialogFragment(this);
            newFragment.show(getActivity().getFragmentManager(), "dialog");
        }


    }

  /*  @OnTouch(R.id.et_community_type)
    public boolean onTouch() {
        CommunityTypeFragment newFragment =new CommunityTypeFragment(this);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
        return true;
    }*/

    @OnClick(R.id.et_create_community_type)
    public void onLogInBtnClick() {

        CommunityTypeFragment newFragment =new CommunityTypeFragment(this);
        newFragment.show(getActivity().getFragmentManager(), "dialog");

      /*  FragmentCategoryList nextFrag= new FragmentCategoryList();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.top_bottom_enter_anim, 0, 0, R.anim.top_bottom_enter_anim_reverse)
                .replace(R.id.fl_fragment_container,nextFrag, SPINNER_FRAGMENT).addToBackStack(null).commit();*/

    }

    @Override
    public void getCreateCommunityResponse(LoginResponse loginResponse) {

    }

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

    }

    @Override
    public void showError(String s) {

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onClose() {
    }

    @Override
    public void onAddFriendSubmit(String communitynm, String image) {
        met_community_type.setText(communitynm);

    }

    public interface CreateCommunityActivityIntractionListner {
        void close();
        void onErrorOccurence();
    }
/*
This mathod is for select image from camera and gellery
*/
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
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(),"Permission Required",Toast.LENGTH_LONG).show();
                    checkCameraPermission();
                }


            } else if (items[item].equals("Choose from Gallery")) {
                try {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, mGALLERY_CODE);
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(),"Permission Required",Toast.LENGTH_LONG).show();
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
                if (moutPutFile.exists()) {
                    Bitmap photo = decodeFile(moutPutFile);
                    //  btn_profile_pic.setImageBitmap(photo);//**********************set image on imageview
                    // profilepic.setImageBitmap(photo);
                    // profile.setImageBitmap(photo);//**********************set image on imageview
                    if(mimage_type==2)
                        mimg_cover.setImageBitmap(photo);
                    else
                        mimagelogo.setImageBitmap(photo);
                    byte[] buffer = new byte[4096];


                    buffer = getBytesFromBitmap(photo);
                    // encImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                    //   Log.e("str", encImage);
                    //  saveImage();
                } else {
                    Toast.makeText(getActivity(), "Error while save image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void CropingIMG()
    {

        final ArrayList cropOptions = new ArrayList();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            try {
                Bitmap photo = decodeFile(moutPutFile1);
                //  btn_profile_pic.setImageBitmap(photo);
                // profilepic.setImageBitmap(photo);
                // profile.setImageBitmap(photo);
                if(mimage_type==2)
               mimg_cover.setImageBitmap(photo);
                else
                mimagelogo.setImageBitmap(photo);
                // encImage = Base64.encodeToString(buffer, Base64.DEFAULT);
                // Log.e("str", encImage);
                //  saveImage();
            }
            catch(Exception e){}
/*
            Toast.makeText(this, "Cann't find image croping app", Toast.LENGTH_SHORT).show();
*/
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);

            //TODO: don't use return-data tag because it's not return large image data and crash not given any message
            //intent.putExtra("return-data", true);

            //Create output file here
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(moutPutFile));

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
                if(mimage_type==2)
                    mimg_cover.setImageBitmap(bmp);
                else
                    mimagelogo.setImageBitmap(bmp);
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
    void getExternalStoragePermission()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , 1);
                Log.e("testing", "Permission is revoked");


            }
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                Log.e("testing", "Permission is revoked");

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.e("testing", "Permission is already granted");

        }

    }

}
