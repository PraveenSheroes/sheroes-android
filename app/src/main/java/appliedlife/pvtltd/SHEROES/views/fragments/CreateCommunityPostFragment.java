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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.ShareCommunityActivity;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.CreateCommunityView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Ajit Kumar on 20-01-2017.
 */

public class CreateCommunityPostFragment extends BaseFragment implements CreateCommunityView,EditNameDialogListener,SelectCommunityFragment.MyDialogFragmentListener {

    @Bind(R.id.txt_choose_community_spinner)
    TextView metchoosecommunity;
    @Bind(R.id.lnr_image_container)
    ViewGroup mlnr_image_container;
    @Bind(R.id.iv_community_post_icon)
    CircleImageView mcommunity_post_icon;
    @Bind(R.id.hor_scroll_for_community_post_images)
    HorizontalScrollView mhor_scroll_for_community_post_images;
    @Bind(R.id.iv_camera_btn_for_post_images)
    ImageView miv_camera_btn_for_post_images;
    @Bind(R.id.tv_add_more_community_post_image)
    TextView mtv_add_more_community_post_image;
    @Bind(R.id.tv_community_post_submit)
    TextView mtv_community_post_submit;
    @Bind(R.id.camera_frame_iv_camera_btn_for_post_images)
    FrameLayout mcamera_frame_iv_camera_btn_for_post_images;
    @Bind(R.id.community_title)
    TextView mcreate_community_post;
    @Bind(R.id.iv_close_community)
    FrameLayout mcreate_community_post_close;
    int imgcount=0,count=0;
    String value = "";
    private CreateCommunityActivityPostIntractionListner mCreatecommunityPostIntractionListner;
    private final String TAG = LogUtils.makeLogTag(CreateCommunityPostFragment.class);
    private static final int mCAMERA_CODE = 101, mGALLERY_CODE = 201, mCROPING_CODE = 301;
    private Uri mImageCaptureUri;
    private File moutPutFile = null;
    private File moutPutFile1 = null;
    ImageView img[]=new ImageView[6];
    Button mbtncross[]=new Button[6];
    Bitmap mroundBitmap;

    public CreateCommunityPostFragment() {

    }
    public CreateCommunityPostFragment(String name) {

    }
    public static CreateCommunityPostFragment createInstance(int itemsCount) {
        CreateCommunityPostFragment createCommunityPostFragment = new CreateCommunityPostFragment("");


        return createCommunityPostFragment;
    }
    void showDialog() {
        SelectCommunityFragment newFragment =new SelectCommunityFragment(this);
        newFragment.show(getActivity().getFragmentManager(), "dialog");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof CreateCommunityActivityPostIntractionListner) {
                mCreatecommunityPostIntractionListner = (CreateCommunityActivityPostIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.create_community_post_fragment, container, false);
        ButterKnife.bind(this, view);
        moutPutFile = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        moutPutFile1 = new File(Environment.getExternalStorageDirectory(), "temp1.jpg");
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mcreate_community_post.setText(R.string.ID_CREATEPOST);
        checkStoragePermission();


        Fabric.with(getActivity(), new Crashlytics());

        return view;
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
    }
    @OnClick(R.id.iv_close_community)
    public void onCloseClick()
    {
        mCreatecommunityPostIntractionListner.onClose();
    }
    @OnClick(R.id.tv_add_more_community_post_image)
    public void addMoreClick()
    {
       checkCameraPermission();
        selectImageOption();

    }
    @OnClick(R.id.camera_frame_iv_camera_btn_for_post_images)
    public void camerabtnClick()
    {
       checkCameraPermission();
        selectImageOption();

    }
    @OnClick(R.id.tv_community_post_submit)
    public void communityPostSubmitClick()
    {
        Intent intent = new Intent(getActivity(), ShareCommunityActivity.class);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        super.onResume();

    }


    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of loginFragment");
        super.onPause();
    }
    @Override
    public void onStop()
    {
        Log.e("DEBUG", "OnPause of loginFragment");
        super.onStop();
    }
    @OnClick(R.id.txt_choose_community_spinner)
    public void spnOnClick()
    {
       // showSelectCommunityDilog(true);
        showDialog();
    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onAddFriendSubmit(String communitynm,String logoimg) {
        // Do stuff
        metchoosecommunity.setText(communitynm);

        mcommunity_post_icon.setCircularImage(true);
        mcommunity_post_icon.bindImage(logoimg);

    }

    @Override
    public void getCreateCommunityResponse(LoginResponse loginResponse) {

    }

    @Override
    public void showNwError() {

    }

    @Override
    public void dialogValue(String dilogval) {
        metchoosecommunity.setText(dilogval);

    }

    public void closeDialog(String dialogType,Context cn) {
        Log.e("click",dialogType);
        Toast.makeText(cn,dialogType,Toast.LENGTH_LONG).show();
       value=dialogType;
   /*     SharedPreferences sharedpreferences = getActivity().getSharedPreferences("ins", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("newins", "1");
        editor.commit();*/
      //  onResume();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

        //etcommunityname.setText(dialogType);
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
    public void onFinishEditDialog(String inputText) {
        Log.e("value",inputText);
    }

    public interface CreateCommunityActivityPostIntractionListner {
        void onErrorOccurence();
        void onClose();
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

        }
        catch (Exception e){
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
                    mroundBitmap = getRoundedCroppedBitmap(photo, 500);
                  //  btn_profile_pic.setImageBitmap(photo);//**********************set image on imageview
                   // profilepic.setImageBitmap(photo);
                   // profile.setImageBitmap(photo);//**********************set image on imageview
                    View layout2 = LayoutInflater.from(getActivity()).inflate(R.layout.imagevie_with_cross, mhor_scroll_for_community_post_images, false);
                   // View layout3 = LayoutInflater.from(getActivity()).inflate(R.layout.addimage, mhor_scroll_for_community_post_images, false);
                        miv_camera_btn_for_post_images.setVisibility(View.GONE);
                    mcamera_frame_iv_camera_btn_for_post_images.setVisibility(View.GONE);
                    mtv_add_more_community_post_image.setVisibility(View.VISIBLE);
                    if(imgcount==4)
                     mtv_add_more_community_post_image.setVisibility(View.GONE);
                    mhor_scroll_for_community_post_images.setVisibility(View.VISIBLE);
                    mhor_scroll_for_community_post_images.setHorizontalScrollBarEnabled(false);

                         img[imgcount] = (ImageView) layout2.findViewById(R.id.customView);
                    mbtncross[imgcount]= (Button) layout2.findViewById(R.id.button1);
                    img[imgcount].setScaleType(ImageView.ScaleType.CENTER_CROP);
                        img[imgcount].setImageBitmap(photo);
                    mbtncross[imgcount].setTag("Img"+imgcount);
                    mbtncross[imgcount].setOnClickListener(mCorkyListener);

                    mlnr_image_container.addView(layout2);

                   // layout2.setOnClickListener(myClickListner);
                   // mygallery.addView(layout3);
                    imgcount++;
                    byte[] buffer = new byte[4096];

                    count++;

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
    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        public void onClick(View v) {
            mtv_add_more_community_post_image.setVisibility(View.VISIBLE);
            if(v.getTag().equals("Img0")){
                img[0].setVisibility(View.GONE);
                mbtncross[0].setVisibility(View.GONE);
                imgcount--;
                //do stuff
            }else if(v.getTag().equals("Img1")){
                img[1].setVisibility(View.GONE);
                mbtncross[1].setVisibility(View.GONE);
                imgcount--;

                //do something else
            }else if(v.getTag().equals("Img2")){
                img[2].setVisibility(View.GONE);
                mbtncross[2].setVisibility(View.GONE);
                imgcount--;

                //do something else
            }else if(v.getTag().equals("Img3")){
                img[3].setVisibility(View.GONE);
                mbtncross[3].setVisibility(View.GONE);
                //do something else
            }else if(v.getTag().equals("Img4")){
                img[4].setVisibility(View.GONE);
                mbtncross[4].setVisibility(View.GONE);
                //do something else
                imgcount--;

            }

            // do something when the button is clicked
            // Yes we will handle click here but which button clicked??? We don't know

        }
    };
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
                mlnr_image_container.setVisibility(View.VISIBLE);
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(photo);
                imageView.setImageBitmap(photo);
                mlnr_image_container.addView(imageView);
                byte[] buffer = new byte[4096];
                buffer = getBytesFromBitmap(photo);
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
                mlnr_image_container.setVisibility(View.VISIBLE);
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(220, 220));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(bmp);
                imageView.setImageBitmap(bmp);
                mlnr_image_container.addView(imageView);
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
