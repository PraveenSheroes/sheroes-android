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
import android.graphics.drawable.BitmapDrawable;
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
    CheckBox mCbopen_community;

    @Bind(R.id.cb_create_community_close_check)
    CheckBox mCbclose_community;

    @Bind(R.id.tv_create_community_cover_img_upload)
    TextView mTv_community_cover_img;

    @Bind(R.id.tv_create_community_logo_upload)
    TextView mTv_community_logo_img;

    @Bind(R.id.iv_create_community_cover_img)
    ImageView mIv_cover;

    @Bind(R.id.tv_create_community_logo)
    TextView mIvlogo;

    @Bind(R.id.tv_create_community_submit)
    TextView mTv_create;

    @Bind(R.id.iv_create_community_cross)
    TextView mIv_btn_cross;

    @Bind(R.id.et_create_community_type)
    EditText mEt_community_type;

    @Bind(R.id.et_create_community_tags)
    EditText mEt_create_community_tags;


    private final String mTAG = LogUtils.makeLogTag(CreateCommunityFragment.class);
    private CreateCommunityActivityIntractionListner mCreatecommunityIntractionListner;
    private int mImage_type=0;

    private static final int mCAMERA_CODE = 101, mGALLERY_CODE = 201, mCROPING_CODE = 301;
    private Uri mImageCaptureUri;
    private File mOutPutFile = null;
    private File mOutPutFile1 = null;
    View view;

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
            LogUtils.error(mTAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + mTAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
         view = inflater.inflate(R.layout.fragmentcreate_community, container, false);
        ButterKnife.bind(this, view);
        mOutPutFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        mOutPutFile1 = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
        mEt_create_community_tags.setText("Community Tag");

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
        mImage_type=1;
     checkStoragePermission();
        selectImageOption();
    }
    @OnClick(R.id.iv_create_community_cover_img)
    public void coverImageClick()
    {
        mImage_type=2;
        checkCameraPermission();
        selectImageOption();
    }
    @OnClick(R.id.tv_create_community_logo)
    public void changeCommunityLogo()
    {
        mImage_type=1;
        checkStoragePermission();
        selectImageOption();
    }
    @OnClick(R.id.et_create_community_tags)
    public void btnTagClick()
    {

        mCreatecommunityIntractionListner.callCommunityTagPage();
       // CommunitySearchTagsFragment newFragment = new CommunitySearchTagsFragment(this);
    }
    @OnClick(R.id.tv_create_community_cover_img_upload)
    public void btnChangeCover() {
        mImage_type=2;
      checkCameraPermission();
        selectImageOption();
    }
    @OnClick(R.id.tv_create_community_submit)
    public void btnCreateClick()
    {
       /* Intent intent = new Intent(getActivity(), CreateCommunityPostActivity.class);
        startActivity(intent);*/
        Toast.makeText(getActivity(),"Created",Toast.LENGTH_LONG).show();
        mCreatecommunityIntractionListner.close();
    }
    public void checkStoragePermission()
    {
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
    private void checkCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
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
        mCbclose_community.setChecked(false);
        if(mCbopen_community.isChecked())
        {
           // ChangeCommunityPrivacyDialogFragment newFragment = new ChangeCommunityPrivacyDialogFragment(this);
          //  newFragment.show(getActivity().getFragmentManager(), "dialog");
            /*CommunityJoinRegionDialogFragment newFragment = new CommunityJoinRegionDialogFragment(this);
            newFragment.show(getActivity().getFragmentManager(), "dialog");*/
        }

    }

    @OnClick(R.id.cb_create_community_close_check)
    public void OnCloseCheckClick() {
        mCbopen_community.setChecked(false);
        if(mCbclose_community.isChecked())
        {
           // ChangeCommunityPrivacyDialogFragment newFragment = new ChangeCommunityPrivacyDialogFragment(this);
           // newFragment.show(getActivity().getFragmentManager(), "dialog");
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
        mEt_community_type.setText(communitynm);

    }

    public void showTagResult(String[] tagsval) {
        String tagval="";
        for(int i=1;i<tagsval.length;i++)
        {
            tagval=tagval+" "+tagsval[i];
        }
        LogUtils.info("result-", tagval);
        mEt_create_community_tags.setText("Community Tag");

        Toast.makeText(getActivity(),tagval,Toast.LENGTH_LONG).show();
    }

    public interface CreateCommunityActivityIntractionListner {
        void close();
        void onErrorOccurence();
        void callCommunityTagPage();
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
                if (mOutPutFile.exists()) {
                    Bitmap photo = decodeFile(mOutPutFile);
                    //  btn_profile_pic.setImageBitmap(photo);//**********************set image on imageview
                    // profilepic.setImageBitmap(photo);
                    // profile.setImageBitmap(photo);//**********************set image on imageview
                    if(mImage_type==2)
                        mIv_cover.setImageBitmap(photo);
                    else {
                        //   mIvlogo.setImageBitmap(photo);
                        mIvlogo.setBackground(new BitmapDrawable(getResources(), photo));
                        mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
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
    private void CropingIMG()
    {

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
                if(mImage_type==2)
               mIv_cover.setImageBitmap(photo);
                else {
                    mIvlogo.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

                    mIvlogo.setBackground(new BitmapDrawable(getResources(), photo));
                }

              //  mIvlogo.setImageBitmap(photo);


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
                if(mImage_type==2)
                    mIv_cover.setImageBitmap(bmp);
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
    void getExternalStoragePermission()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , 1);
                LogUtils.info("testing", "Permission is revoked");


            }
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                LogUtils.info("testing", "Permission is granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                LogUtils.info("testing", "Permission is revoked");

            }
        } else { //permission is automatically granted on sdk<23 upon installation
            LogUtils.info("testing", "Permission is already granted");

        }

    }

}
