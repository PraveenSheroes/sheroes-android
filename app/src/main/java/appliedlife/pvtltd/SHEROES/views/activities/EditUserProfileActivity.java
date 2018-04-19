package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences2.Preference;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.analytics.AnalyticsManager;
import appliedlife.pvtltd.SHEROES.analytics.Event;
import appliedlife.pvtltd.SHEROES.analytics.EventProperty;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesPresenter;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.imageops.CropImage;
import appliedlife.pvtltd.SHEROES.imageops.CropImageView;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.login.LoginResponse;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserSummary;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserDetails;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserSummaryRequest;
import appliedlife.pvtltd.SHEROES.presenters.EditProfilePresenterImpl;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.CompressImageUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.CameraBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileImageDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SearchProfileLocationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IEditProfileView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 03/01/18.
 * User Profile Edit Screen
 */

public class EditUserProfileActivity extends BaseActivity implements IEditProfileView, AdapterView.OnItemSelectedListener {

    private static final String SCREEN_LABEL = "Edit Profile Screen";
    private static final String TAG = EditUserProfileActivity.class.getName();

    private static final int BIO_MAX_LIMIT = 140;
    private SearchProfileLocationDialogFragment searchProfileLocationDialogFragment;
    private ProfileImageDialogFragment profileImageDialogFragment;
    private String mEncodeImageUrl;
    private Uri mImageCaptureUri;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private DatePickerDialog fromDatePickerDialog;
    private String locationName;
    private int cityId;
    private File localImageSaveForChallenge;
    private String aboutMeValue = "";
    private LoginResponse userDetailsResponse;

    @Bind(R.id.title_toolbar)
    TextView toolbarTitle;

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    EditProfilePresenterImpl editProfilePresenter;

    @Inject
    AppUtils appUtils;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.scroll_fragment_edit)
    ScrollView scrollFragmentEdit;

    @Bind(R.id.et_full_name_container)
    TextInputLayout fullNameContainer;

    @Bind(R.id.et_mobile_container)
    TextInputLayout mobileContainer;

    @Bind(R.id.et_about_me)
    public EditText aboutMe;

    @Bind(R.id.et_mobilenumber)
    public EditText mobileNumber;

    @Bind(R.id.tv_email_value)
    TextView emailAddress;

    @Bind(R.id.bio_limit)
    public TextView bioMaxCharLimit;

    @Bind(R.id.et_dob)
    EditText dateOfBirth;

    @Bind(R.id.et_children_number)
    EditText noOfChildren;

    @Bind(R.id.et_full_name)
    EditText name;

    @Bind(R.id.spinner_relation_status)
    Spinner relationshipStatus;

    @Bind(R.id.et_location)
    EditText location;

    @Bind(R.id.iv_profile_image)
    CircleImageView userImage;

    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar mProgressBar;

    @Bind(R.id.pb_image_loader)
    ProgressBar imageLoader;

    @BindDimen(R.dimen.dp_size_80)
    int authorProfileSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        editProfilePresenter.attachView(this);

        setupToolbarItemsColor();

        String imageUrl = getIntent().getStringExtra(AppConstants.EXTRA_IMAGE);
        setProfileNameData(imageUrl);

        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && null != mUserPreference.get().getUserSummary().getUserBO()) {
            userDetailsResponse = mUserPreference.get();
        }

        editProfilePresenter.getALLUserDetails();

        relationshipStatus.setOnItemSelectedListener(this);
        location.setOnClickListener(this);
        userImage.setOnClickListener(this);

        aboutMe.setFilters(new InputFilter[]{new InputFilter.LengthFilter(BIO_MAX_LIMIT)});

        aboutMe.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bioMaxCharLimit.setText(s.toString().length() + "/" + BIO_MAX_LIMIT);
            }
        });

        setDateTimeField();
    }

    @Override
    public String getScreenName() {
        return SCREEN_LABEL;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showError(int error) {

    }

    @Override
    public void errorMessage(String message) {
        if (StringUtil.isNotNullOrEmptyString(message) && message.contains("mobile number already registered with us")) {
            mobileContainer.setError(message.toUpperCase());
            requestFocus(mobileNumber);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String relationshipStatusValue = (String) parent.getItemAtPosition(position);
        LogUtils.info(TAG, relationshipStatusValue);
        if (relationshipStatusValue.equalsIgnoreCase("unmarried")) {
            resetNoOfChildrenField(false);
        } else {
            resetNoOfChildrenField(true);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void resetNoOfChildrenField(boolean isEnabled) {
        if (!isEnabled) {
            noOfChildren.setText("0");
            noOfChildren.setEnabled(false);
            noOfChildren.setClickable(false);
        } else {
            noOfChildren.setEnabled(true);
            noOfChildren.setClickable(true);
        }
    }

    private void setDateTimeField() {
        dateOfBirth.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void setUserDetails(UserSummary userSummary) {
        String fullName = userSummary.getFirstName() + AppConstants.SPACE + userSummary.getLastName();
        if (StringUtil.isNotNullOrEmptyString(fullName)) {
            name.setText(fullName);
        }

        String locationValue = userSummary.getUserBO().getCityMaster();
        if (StringUtil.isNotNullOrEmptyString(locationValue)) {
            location.setText(locationValue);
        }

        aboutMeValue = userSummary.getUserBO().getUserSummary();
        if (StringUtil.isNotNullOrEmptyString(aboutMeValue)) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                aboutMe.setText(Html.fromHtml(aboutMeValue)); // for 24 api and more
            } else {
                aboutMe.setText(Html.fromHtml(aboutMeValue));
            }
            int length = aboutMeValue.length();
            bioMaxCharLimit.setText(length + "/" + BIO_MAX_LIMIT);
        } else {
            bioMaxCharLimit.setText(0 + "/" + BIO_MAX_LIMIT);
        }

        String dob = userSummary.getUserBO().getDob();
        if (CommonUtil.isNotEmpty(dob) && !dob.equalsIgnoreCase("null")) {

            Calendar cal = new GregorianCalendar();
            cal.setTime(new Date(Long.valueOf(dob)));
            String formattedDate = dateFormatter.format(cal.getTime());

            if (formattedDate != null) {
                dateOfBirth.setText(formattedDate);
            }
        }

        int noOfChildrenValue = userSummary.getUserBO().getNoOfChildren();
        noOfChildren.setText(String.valueOf(noOfChildrenValue));

        String relationStatus = userSummary.getUserBO().getMaritalStatus();
        if (StringUtil.isNotNullOrEmptyString(relationStatus)) {
            relationshipStatus.setSelection(((ArrayAdapter<String>) relationshipStatus.getAdapter()).getPosition(relationStatus));
        }

        String email = userSummary.getEmailId();
        if (StringUtil.isNotNullOrEmptyString(email)) {
            emailAddress.setText(email);
        }

        locationName = locationValue;  //initially set value from preferenece
        cityId = (int) userSummary.getUserBO().getCityMasterId();

        String mobile = userSummary.getMobile();
        if (StringUtil.isNotNullOrEmptyString(mobile)) {
            mobileNumber.setText(mobile);
        }

    }

    @Override
    public void getUserData(UserProfileResponse userProfileResponse) {

        if (null != userProfileResponse) {
            LoginResponse loginResponse = mUserPreference.get();
            UserDetails userDetails = userProfileResponse.getUserDetails();
            if (null != userDetails) {

                UserSummary userSummary = loginResponse.getUserSummary();
                if (StringUtil.isNotNullOrEmptyString(userDetails.getFirstName())) {
                    userSummary.setFirstName(userDetails.getFirstName());
                }
                if (StringUtil.isNotNullOrEmptyString(userDetails.getLastName())) {
                    userSummary.setLastName(userDetails.getLastName());
                }
                if (StringUtil.isNotNullOrEmptyString(userDetails.getCityMaster())) {
                    userSummary.getUserBO().setCityMaster(userDetails.getCityMaster());
                }

                if (StringUtil.isNotNullOrEmptyString(userDetails.getEmailid())) {
                    userSummary.getUserBO().setEmailid(userDetails.getEmailid());
                }

                if (StringUtil.isNotNullOrEmptyString(userDetails.getMaritalStatus())) {
                    userSummary.getUserBO().setMaritalStatus(userDetails.getMaritalStatus());
                }

                if (StringUtil.isNotNullOrEmptyString(userDetails.getMobile())) {
                    userSummary.setMobile(userDetails.getMobile());
                }

                if (StringUtil.isNotNullOrEmptyString(userDetails.getUserSummary())) {
                    userSummary.getUserBO().setUserSummary(userDetails.getUserSummary());
                }

                userSummary.getUserBO().setNoOfChildren(userDetails.getNoOfChildren());
                userSummary.getUserBO().setDob(String.valueOf(userDetails.getDob()));

                userSummary.getUserBO().setCityMasterId(userDetails.getCityMasterId());

                loginResponse.setUserSummary(userSummary);
                setUserDetails(userSummary);
                mUserPreference.set(loginResponse);
            }

        }
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        setResult();
        finish();
    }

    @Override
    protected SheroesPresenter getPresenter() {
        return editProfilePresenter;
    }


    private void setResult() {
        imageLoader.setVisibility(View.GONE);
        if (userDetailsResponse != null) {
            Intent intent = new Intent();
            UserSummary summary = userDetailsResponse.getUserSummary();
            Bundle bundle = new Bundle();
            bundle.putString("NAME", summary.getFirstName() + AppConstants.SPACE + summary.getLastName());
            bundle.putString("BIO", summary.getUserBO().getUserSummary());
            bundle.putString("LOCATION", summary.getUserBO().getCityMaster());
            bundle.putString("IMAGE_URL", summary.getPhotoUrl());
            intent.putExtras(bundle);
            setResult(AppConstants.REQUEST_CODE_FOR_EDIT_PROFILE, intent);
        }
    }

    public void setProfileNameData(String imageUrl) {
        if (null != imageUrl) {
            userImage.setCircularImage(true);
            String authorThumborUrl = CommonUtil.getThumborUri(imageUrl, authorProfileSize, authorProfileSize);
            userImage.bindImage(authorThumborUrl);
        }
        File localImageSaveForChallenge = new File(Environment.getExternalStorageDirectory(), AppConstants.IMAGE + AppConstants.JPG_FORMATE);
        setLocalImageSaveForChallenge(localImageSaveForChallenge);
    }

    public void setLocalImageSaveForChallenge(File localImageSaveForChallenge) {
        this.localImageSaveForChallenge = localImageSaveForChallenge;
    }

    @Override
    public void handleOnClick(BaseResponse baseResponse, View view) {
        if (baseResponse instanceof GetAllDataDocument) {
            dataOnClickForCardItem(view, baseResponse);
        }
    }

    private void dataOnClickForCardItem(View view, BaseResponse baseResponse) {
        int id = view.getId();
        switch (id) {
            case R.id.li_city_name_layout:
                updateUserLocation(baseResponse);
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + id);
        }
    }

    private void updateUserLocation(BaseResponse baseResponse) {
        GetAllDataDocument dataItem = (GetAllDataDocument) baseResponse;
        if (null != searchProfileLocationDialogFragment) {
            searchProfileLocationDialogFragment.dismiss();
            submitLocation(dataItem.getId(), dataItem.getTitle());
        }
    }

    public void submitLocation(String cityId, String city) {
        locationName = city;
        this.cityId = Integer.valueOf(cityId);
        location.setText(city);
        if (null != city) {
            int length = city.length();
            location.setSelection(length);
        }
    }

    /**
     * Search user location
     *
     * @return
     */
    public DialogFragment searchUserLocation() {
        searchProfileLocationDialogFragment = (SearchProfileLocationDialogFragment) getFragmentManager().findFragmentByTag(SearchProfileLocationDialogFragment.class.getName());
        if (searchProfileLocationDialogFragment == null) {
            searchProfileLocationDialogFragment = new SearchProfileLocationDialogFragment();
        }

        if (!searchProfileLocationDialogFragment.isVisible() && !searchProfileLocationDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            searchProfileLocationDialogFragment.show(getFragmentManager(), SearchProfileLocationDialogFragment.class.getName());
        }
        return searchProfileLocationDialogFragment;
    }

    public DialogFragment profileImageDialog() {

        profileImageDialogFragment = (ProfileImageDialogFragment) getFragmentManager().findFragmentByTag(ProfileImageDialogFragment.class.getName());
        if (profileImageDialogFragment == null) {
            profileImageDialogFragment = new ProfileImageDialogFragment();
            Bundle bundle = new Bundle();
            profileImageDialogFragment.setArguments(bundle);
        }
        if (!profileImageDialogFragment.isVisible() && !profileImageDialogFragment.isAdded() && !isFinishing() && !mIsDestroyed) {
            profileImageDialogFragment.show(getFragmentManager(), ProfileImageDialogFragment.class.getName());
        }
        return profileImageDialogFragment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
         /* 2:- For refresh list if value pass two Home activity means its Detail section changes of activity*/
        if (null != intent) {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_GALLERY:
                    mImageCaptureUri = intent.getData();
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
                    }
                    break;
                case AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING:
                    imageCropping(intent);
                    break;

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                    CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                    if (resultCode == RESULT_OK) {
                        try {
                            File file = new File(result.getUri().getPath());
                            Bitmap photo = CompressImageUtil.decodeFile(file);
                            mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        requestForUpdateProfileImage();

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                    }

                    break;

                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        } else {
            switch (requestCode) {
                case AppConstants.REQUEST_CODE_FOR_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        cropingIMG();
                    }
                    break;
                default:
                    LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + requestCode);
            }
        }

    }

    private void cropingIMG() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List list = getPackageManager().queryIntentActivities(intent, 0);
        intent.setData(mImageCaptureUri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(localImageSaveForChallenge));
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        if (StringUtil.isNotEmptyCollection(list)) {
            Intent i = new Intent(intent);
            ResolveInfo res = (ResolveInfo) list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(i, AppConstants.REQUEST_CODE_FOR_IMAGE_CROPPING);
        }
    }

    private void imageCropping(Intent intent) {
        try {
            if (localImageSaveForChallenge.exists()) {
                Bitmap photo = CompressImageUtil.decodeFile(localImageSaveForChallenge);
                mEncodeImageUrl = CompressImageUtil.setImageOnHolder(photo);
                if (null != profileImageDialogFragment) {
                    profileImageDialogFragment.setUserProfileData(true, photo);
                }
            } else {
                Toast.makeText(this, "Error while save image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Crashlytics.getInstance().core.logException(e);
            e.printStackTrace();
        }
    }


    public void selectImageFrmGallery() {
        CropImage.activity(null, AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    public void requestForUpdateProfileImage() {
        imageLoader.setVisibility(View.VISIBLE);
        imageLoader.bringToFront();
        if (StringUtil.isNotNullOrEmptyString(mEncodeImageUrl)) {
            updateProfileData(mEncodeImageUrl);
            if (null != profileImageDialogFragment) {
                profileImageDialogFragment.dismiss();
            }
        }
    }

    public void updateProfileData(String imageUrl) {
        editProfilePresenter.getUserSummaryDetails(appUtils.getUserProfileRequestBuilder(AppConstants.PROFILE_PIC_SUB_TYPE, AppConstants.PROFILE_PIC_TYPE, imageUrl));
    }

    public void selectImageFrmCamera() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CropImage.activity(null, AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .setAspectRatio(1, 1)
                .setAllowRotation(true)
                .start(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == dateOfBirth) {
            fromDatePickerDialog.show();
        } else if (view == location) {
            searchUserLocation();
        } else if (view == userImage) {
            CameraBottomSheetFragment.showDialog(this, getScreenName());
            HashMap<String, Object> properties =
                    new EventProperty.Builder()
                            .id(Long.toString(mUserPreference.get().getUserSummary().getUserId()))
                            .name(mUserPreference.get().getUserSummary().getFirstName())
                            .build();
            trackEvent(Event.PROFILE_PIC_EDIT_CLICKED, properties);
        }
    }

    @Override
    protected Map<String, Object> getExtraPropertiesToTrack() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserPreference.get().getUserSummary().getUserId()))
                        .name(mUserPreference.get().getUserSummary().getFirstName())
                        .build();
        return properties;
    }

    @Override
    protected boolean trackScreenTime() {
        return true;
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
    public void showError(String s, FeedParticipationEnum feedParticipationEnum) {
        mProgressBar.setVisibility(View.GONE);
        showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        scrollFragmentEdit.scrollTo(0, view.getBottom());
    }

    private boolean validateName() {
        if (!StringUtil.isNotNullOrEmptyString(name.getText().toString().trim())) {
            fullNameContainer.setError("Please enter full name");
            requestFocus(name);
            return false;
        } else {
            fullNameContainer.setError(null);
        }
        scrollFragmentEdit.scrollTo(0, fullNameContainer.getBottom());
        return true;
    }

    private boolean validateLocation() {
        if (!StringUtil.isNotNullOrEmptyString(location.getText().toString())) {
            location.setError("Please enter Current location");
            requestFocus(location);
            return false;
        } else {
            location.setError(null);
        }
        scrollFragmentEdit.scrollTo(0, location.getBottom());
        return true;
    }

    private boolean validateDOB() {
        if (!StringUtil.isNotNullOrEmptyString(dateOfBirth.getText().toString())) {
            dateOfBirth.setError("Please enter date of birth");
            requestFocus(dateOfBirth);
            return false;
        } else {
            dateOfBirth.setError(null);
        }
        scrollFragmentEdit.scrollTo(0, dateOfBirth.getBottom());
        return true;
    }

    private boolean validateMobile() {

        if (!StringUtil.isNotNullOrEmptyString(mobileNumber.getText().toString()) || mobileNumber.getText().length() < 10) {
            mobileContainer.setError("Please enter valid mobile number");
            requestFocus(mobileNumber);
            return false;
        } else {
            mobileContainer.setError(null);
        }
        scrollFragmentEdit.scrollTo(0, mobileContainer.getBottom());
        return true;
    }

    private boolean validateUserDetails() {
        return validateName() && validateLocation() && validateDOB() && validateMobile();
    }

    private void setupToolbarItemsColor() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.vector_back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbarTitle.setText(R.string.ID_EDIT_PROFILE);
    }

    @OnClick(R.id.btn_personal_basic_details_save)
    public void Save_Basic_Details() {
        HashMap<String, Object> properties =
                new EventProperty.Builder()
                        .id(Long.toString(mUserPreference.get().getUserSummary().getUserId()))
                        .isOwnProfile(true)
                        .build();
        AnalyticsManager.trackEvent(Event.PROFILE_EDITED, getScreenName(), properties);

        if (validateUserDetails()) {
            PersonalBasicDetailsRequest personalBasicDetailsRequest = new PersonalBasicDetailsRequest();
            AppUtils appUtils = AppUtils.getInstance();
            personalBasicDetailsRequest.setAppVersion(appUtils.getAppVersionName());
            personalBasicDetailsRequest.setCloudMessagingId(appUtils.getCloudMessaging());
            personalBasicDetailsRequest.setDeviceUniqueId(appUtils.getDeviceId());
            personalBasicDetailsRequest.setLastScreenName("string");
            personalBasicDetailsRequest.setScreenName("string");
            personalBasicDetailsRequest.setType("BASIC_PROFILE");
            personalBasicDetailsRequest.setSubType("BASIC_USER_PROFILE_SERVICE");
            personalBasicDetailsRequest.setSource(AppConstants.SOURCE_NAME);

            //USer Bio region
            //if(!aboutMeValue.trim().equalsIgnoreCase(aboutMe.getText().toString().trim())) {
            UserSummaryRequest userSummaryRequest = new UserSummaryRequest();
            userSummaryRequest.setAppVersion(appUtils.getAppVersionName());
            userSummaryRequest.setCloudMessagingId(appUtils.getCloudMessaging());
            userSummaryRequest.setDeviceUniqueId(appUtils.getDeviceId());
            userSummaryRequest.setLastScreenName(AppConstants.STRING);
            userSummaryRequest.setScreenName(AppConstants.STRING);
            userSummaryRequest.setSource(AppConstants.SOURCE_NAME);
            userSummaryRequest.setType(AppConstants.SUMMARY);
            String aboutMeValue = StringUtil.isNotNullOrEmptyString(aboutMe.getText().toString()) ? aboutMe.getText().toString() : "";
            userSummaryRequest.setSummary(aboutMeValue);
            userSummaryRequest.setSubType(AppConstants.USER_SUMMARY_SERVICE);

            editProfilePresenter.getUserSummaryDetails(userSummaryRequest);
            //}
            //end region

            String userFullName = name.getText().toString().trim();

            if (StringUtil.isNotNullOrEmptyString(userFullName)) {
                if (userFullName.contains(AppConstants.SPACE)) {
                    userFullName = CommonUtil.camelCaseString(userFullName);
                    String name[] = userFullName.split(AppConstants.SPACE);
                    String firstName = name[0];
                    String lastName = userFullName.substring(firstName.length()+1, userFullName.length());

                    personalBasicDetailsRequest.setFirstName(firstName);
                    personalBasicDetailsRequest.setLastName(lastName);
                    personalBasicDetailsRequest.setFullName(userFullName);

                } else {
                    personalBasicDetailsRequest.setFirstName(userFullName);
                    personalBasicDetailsRequest.setLastName("");
                    personalBasicDetailsRequest.setFullName(userFullName);
                }
            }

            if (StringUtil.isNotNullOrEmptyString(mobileNumber.getText().toString())) {
                personalBasicDetailsRequest.setMobileNumber(mobileNumber.getText().toString());
            }

            personalBasicDetailsRequest.setCityMasterId(cityId);
            try {
                Date date1 = dateFormatter.parse(dateOfBirth.getText().toString());
                DateFormat inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
                String output1 = inputFormatter1.format(date1);
                personalBasicDetailsRequest.setDateOfBirth(output1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            personalBasicDetailsRequest.setMaritalStatus(relationshipStatus.getSelectedItem().toString());
            if (StringUtil.isNotNullOrEmptyString(noOfChildren.getText().toString())) {
                personalBasicDetailsRequest.setNoOfChildren(Integer.parseInt(noOfChildren.getText().toString()));
            }

            editProfilePresenter.getPersonalBasicDetails(personalBasicDetailsRequest);
            ((SheroesApplication) getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_PROFILE_EDITS, GoogleAnalyticsEventActions.EDIT_BASIC_DETAIl_PERSONAL, AppConstants.EMPTY_STRING);
        }
    }

    @Override
    public void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {
        if (boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {

            if (userDetailsResponse != null) {

                try {
                    UserSummary userSummary = userDetailsResponse.getUserSummary();
                    String userName = name.getText().toString().trim();

                    if (userName.contains(AppConstants.SPACE)) {
                        String name[] = userName.split(AppConstants.SPACE);
                        String firstName = name[0];
                        String lastName = userName.substring(firstName.length()+1, userName.length());
                        userSummary.setFirstName(firstName);
                        userSummary.setLastName(lastName);
                        userSummary.getUserBO().setName(userName);
                    } else {
                        userSummary.getUserBO().setName(userName);
                        userSummary.setFirstName(userName);
                        userSummary.setLastName("");
                    }

                    try {
                        String city = location.getText() != null ? location.getText().toString() : ""; //name
                        userSummary.getUserBO().setCityMasterId(cityId);
                        userSummary.getUserBO().setCityMaster(city);

                        String userBio = aboutMe.getText() != null ? aboutMe.getText().toString() : "";
                        userSummary.getUserBO().setUserSummary(userBio);

                        userDetailsResponse.setUserSummary(userSummary);
                        mUserPreference.get().getUserSummary().setFirstName(userSummary.getFirstName());
                        mUserPreference.get().getUserSummary().setLastName(userSummary.getLastName());
                        mUserPreference.set(userDetailsResponse);
                    } catch (Exception e) {
                        LogUtils.error("err", e.toString());
                    }

                } catch (Exception e) {
                    LogUtils.info(TAG, "Error while saving details to preference");
                }
            }

            onBackPressed();
        }
    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {
        if (boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {
            if (userDetailsResponse != null) {
                try {
                    if (boardingDataResponse.getResponse() != null && boardingDataResponse.getResponse().contains("img.") && boardingDataResponse.getResponse().startsWith("http")) {
                        setProfileNameData(boardingDataResponse.getResponse());
                        //Save image
                        userDetailsResponse.getUserSummary().getUserBO().setPhotoUrlPath(boardingDataResponse.getResponse());
                        userDetailsResponse.getUserSummary().setPhotoUrl(boardingDataResponse.getResponse());
                        mUserPreference.get().getUserSummary().setPhotoUrl(boardingDataResponse.getResponse());
                        mUserPreference.set(userDetailsResponse);
                    } else {
                        String userBio = aboutMe.getText() != null ? aboutMe.getText().toString() : "";
                        userDetailsResponse.getUserSummary().getUserBO().setUserSummary(userBio);
                        mUserPreference.get().getUserSummary().getUserBO().setUserSummary(userBio);
                        mUserPreference.set(userDetailsResponse);
                    }
                } catch (Exception e) {
                    LogUtils.info(TAG, e.getMessage());
                }
                setResult();
            }
        }
    }


    public static void navigateTo(Activity fromActivity, String sourceScreen, String imageUrl, HashMap<String, Object> properties, int requestCode) {
        Intent intent = new Intent(fromActivity, EditUserProfileActivity.class);
        intent.putExtra(BaseActivity.SOURCE_SCREEN, sourceScreen);
        intent.putExtra(AppConstants.EXTRA_IMAGE, imageUrl);
        if (!CommonUtil.isEmpty(properties)) {
            intent.putExtra(BaseActivity.SOURCE_PROPERTIES, properties);
        }
        ActivityCompat.startActivityForResult(fromActivity, intent, requestCode, null);
    }

}
