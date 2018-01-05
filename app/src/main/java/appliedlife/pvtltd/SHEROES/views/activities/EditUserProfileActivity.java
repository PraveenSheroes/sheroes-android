package appliedlife.pvtltd.SHEROES.views.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.f2prateek.rx.preferences.Preference;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseActivity;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
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
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.ProfileImageDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.SearchProfileLocationDialogFragment;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.IEditProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ravi on 03/01/18.
 * User Profile Edit Screen
 */

public class EditUserProfileActivity extends BaseActivity implements IEditProfileView ,AdapterView.OnItemSelectedListener{

    private static final String SCREEN_LABEL = "Edit Profile Screen";
    private static final String TAG = EditUserProfileActivity.class.getName();

    private static final int BIO_MAX_LIMIT = 160;
    private SearchProfileLocationDialogFragment searchProfileLocationDialogFragment;
    private ProfileImageDialogFragment profileImageDialogFragment;
    private String mEncodeImageUrl;
    private Uri mImageCaptureUri;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    String mCityId, mcityNm;
    private File localImageSaveForChallenge;
    private String aboutMeValue = "";

    @Inject
    Preference<LoginResponse> mUserPreference;

    @Inject
    EditProfilePresenterImpl editProfilePresenter;

    @Inject
    AppUtils appUtils;

    @Bind(R.id.et_full_name_container)
    TextInputLayout fullNameContainer;

    @Bind(R.id.tv_profile_tittle)
    TextView toolbarTitle;

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

    @Bind(R.id.scrim_view)
    View scrimView;

    @Bind(R.id.iv_profile_image)
    CircleImageView userImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SheroesApplication.getAppComponent(this).inject(this);
        setContentView(R.layout.fragment_edit_profile);
        ButterKnife.bind(this);
        editProfilePresenter.attachView(this);
        toolbarTitle.setText(R.string.ID_EDIT_PROFILE);

        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary()) {
            UserSummary userSummary = mUserPreference.get().getUserSummary();
            setUserDetails(userSummary);
        }

        editProfilePresenter.getALLUserDetails();

        scrimView.setBackgroundColor(Color.BLACK);
        scrimView.setAlpha(0.5f);

        setProfileNameData();
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

        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        setDateTimeField();
        ((SheroesApplication) getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PERSONAL_EDIT_BASIC_DETAIL));
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
    public void getUserData(UserProfileResponse userProfileResponse) {

        if (null != userProfileResponse ) {
            LoginResponse loginResponse = mUserPreference.get();
            UserDetails userDetails=userProfileResponse.getUserDetails();
            if(null!=loginResponse && null!=loginResponse.getUserSummary()&&null!=userDetails) {

                if (StringUtil.isNotNullOrEmptyString(userDetails.getFirstName())) {
                    loginResponse.getUserSummary().setFirstName(userDetails.getFirstName());
                }
                if (StringUtil.isNotNullOrEmptyString(userDetails.getLastName())) {
                    loginResponse.getUserSummary().setLastName(userDetails.getLastName());
                }
                if (StringUtil.isNotNullOrEmptyString(userDetails.getCityMaster())) {
                    loginResponse.getUserSummary().getUserBO().setCityMaster(userDetails.getCityMaster());
                }

                if (StringUtil.isNotNullOrEmptyString(userDetails.getEmailid())) {
                    loginResponse.getUserSummary().getUserBO().setEmailid(userDetails.getEmailid());
                }

                if (StringUtil.isNotNullOrEmptyString(userDetails.getMaritalStatus())) {
                    loginResponse.getUserSummary().getUserBO().setMaritalStatus(userDetails.getMaritalStatus());
                }

                if (StringUtil.isNotNullOrEmptyString(userDetails.getMobile())) {
                    loginResponse.getUserSummary().setMobile(userDetails.getMobile());
                }

                if (StringUtil.isNotNullOrEmptyString(userDetails.getUserSummary())) {
                    loginResponse.getUserSummary().getUserBO().setUserSummary(userDetails.getUserSummary());
                }

                loginResponse.getUserSummary().getUserBO().setNoOfChildren(userDetails.getNoOfChildren());
                loginResponse.getUserSummary().getUserBO().setDob(String.valueOf(userDetails.getDob()));

                loginResponse.getUserSummary().getUserBO().setCityMasterId(userDetails.getCityMasterId());

            }
            mUserPreference.set(loginResponse);
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

        aboutMeValue = userSummary.getUserBO().getUserSummary() ==  null ? "" : userSummary.getUserBO().getUserSummary();
        if (StringUtil.isNotNullOrEmptyString(aboutMeValue)) {
            if (Build.VERSION.SDK_INT >= AppConstants.ANDROID_SDK_24) {
                aboutMe.setText(Html.fromHtml(aboutMeValue)); // for 24 api and more
            } else {
                aboutMe.setText(Html.fromHtml(aboutMeValue));
            }
            int length = aboutMeValue.length();
            bioMaxCharLimit.setText(length + "/160");
        } else {
            bioMaxCharLimit.setText(0 + "/160");
        }

        String dob = userSummary.getUserBO().getDob();
        if (CommonUtil.isNotEmpty(dob) && !dob.equalsIgnoreCase("null")) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(new Date(Long.valueOf(dob)));
            dateOfBirth.setText(cal.get(Calendar.DAY_OF_MONTH) + " " + new SimpleDateFormat("MMM").format(cal.getTime()) + " " + cal.get(Calendar.YEAR));
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

        mcityNm = locationValue;  //initially set value from preferenece
        mCityId = String.valueOf(userSummary.getUserBO().getCityMasterId());

        String mobile = userSummary.getMobile();
        if (StringUtil.isNotNullOrEmptyString(mobile)) {
            mobileNumber.setText(mobile);
        }

    }


    public void setProfileNameData() {
        if (null != mUserPreference && mUserPreference.isSet() && null != mUserPreference.get() && null != mUserPreference.get().getUserSummary() && StringUtil.isNotNullOrEmptyString(mUserPreference.get().getUserSummary().getPhotoUrl())) {
            String profile = mUserPreference.get().getUserSummary().getPhotoUrl();
            if (null != profile) {
                userImage.setCircularImage(true);
                userImage.setPlaceHolderId(R.drawable.default_img);
                userImage.setErrorPlaceHolderId(R.drawable.default_img);
                userImage.bindImage(profile);
            }

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
        mcityNm = city;
        mCityId = cityId;
        location.setText(city);
        if (null != city) {
            int length = city.length();
            location.setSelection(length);
        }
    }

    /**
     * Search user location
     * @return
     */
    public DialogFragment searchUserLocation() {
        searchProfileLocationDialogFragment = (SearchProfileLocationDialogFragment) getFragmentManager().findFragmentByTag(SearchProfileLocationDialogFragment.class.getName());
        if (searchProfileLocationDialogFragment == null) {
            searchProfileLocationDialogFragment = new SearchProfileLocationDialogFragment();
        }

        if (!searchProfileLocationDialogFragment.isVisible() && !searchProfileLocationDialogFragment.isAdded()&& !isFinishing() && !mIsDestroyed) {
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
                            File file=new File(result.getUri().getPath());
                            Bitmap photo = editProfilePresenter.decodeFile(file);
                            if(null!=profileImageDialogFragment) {
                                profileImageDialogFragment.setUserProfileData(true, photo);
                                mEncodeImageUrl = editProfilePresenter.setImageOnHolder(photo);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
                Bitmap photo = editProfilePresenter.decodeFile(localImageSaveForChallenge);
                mEncodeImageUrl = editProfilePresenter.setImageOnHolder(photo);
                if (null != profileImageDialogFragment) {
                    profileImageDialogFragment.setUserProfileData(true,photo);
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
        CropImage.activity(null,AppConstants.TWO_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .start(this);
    }

    public void requestForUpdateProfileImage() {
        if (StringUtil.isNotNullOrEmptyString(mEncodeImageUrl)) {
            updateProfileData(mEncodeImageUrl);
            if (null != profileImageDialogFragment) {
                profileImageDialogFragment.dismiss();
            }
        }
    }

    public void updateProfileData(String imageUrl) {
        editProfilePresenter.getUserSummaryDetailsAuthTokeInPresenter(appUtils.getUserProfileRequestBuilder(AppConstants.PROFILE_PIC_SUB_TYPE, AppConstants.PROFILE_PIC_TYPE, imageUrl));
    }

    public void selectImageFrmCamera() {
        CropImage.activity(null,AppConstants.ONE_CONSTANT).setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .start(this);
    }


    @OnClick(R.id.iv_back_profile)
    public void backOnclick() {
        //overridePendingTransition(R.anim.top_to_bottom_exit,R.anim.top_bottom_exit_anim);
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == dateOfBirth) {
            fromDatePickerDialog.show();
        } else if (view == location) {
            searchUserLocation();
        } else if (view == userImage) {
            profileImageDialog();
        }
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
        showNetworkTimeoutDoalog(true, false, getString(R.string.ID_GENERIC_ERROR));
    }

    @Override
    public void getMasterDataResponse(HashMap<String, HashMap<String, ArrayList<LabelValue>>> mapOfResult) {

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateName() {
        if (!StringUtil.isNotNullOrEmptyString(name.getText().toString())) {
            fullNameContainer.setError("Please enter full name");
            requestFocus(name);
            return false;
        } else {
            fullNameContainer.setErrorEnabled(false);
        }
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
        return true;
    }

    private boolean validateDOB() {
        if (!StringUtil.isNotNullOrEmptyString(dateOfBirth.getText().toString())) {
            dateOfBirth.setError("Please enter Current location");
            requestFocus(dateOfBirth);
            return false;
        } else {
            dateOfBirth.setError(null);
        }
        return true;
    }

    private boolean validateUserDetails() {
        return validateName() && validateLocation() && validateDOB();
    }


    @OnClick(R.id.btn_personal_basic_details_save)
    public void Save_Basic_Details() {

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

            if (aboutMeValue!=null && !aboutMeValue.equalsIgnoreCase(aboutMe.getText().toString())) {
                UserSummaryRequest userSummaryRequest = new UserSummaryRequest();
                userSummaryRequest.setAppVersion(appUtils.getAppVersionName());
                userSummaryRequest.setCloudMessagingId(appUtils.getCloudMessaging());
                userSummaryRequest.setDeviceUniqueId(appUtils.getDeviceId());
                userSummaryRequest.setLastScreenName(AppConstants.STRING);
                userSummaryRequest.setScreenName(AppConstants.STRING);
                userSummaryRequest.setSource(AppConstants.SOURCE_NAME);
                userSummaryRequest.setType(AppConstants.SUMMARY);
                userSummaryRequest.setSummary(aboutMe.getText().toString());
                userSummaryRequest.setSubType(AppConstants.USER_SUMMARY_SERVICE);

                editProfilePresenter.getUserSummaryDetailsAuthTokeInPresenter(userSummaryRequest);
            }

            String userFullName = name.getText().toString();

            if (StringUtil.isNotNullOrEmptyString(userFullName)) {
                if (userFullName.contains(AppConstants.SPACE)) {
                    int firstSpace = userFullName.indexOf(" "); // detect the first space character
                    String firstName = userFullName.substring(0, firstSpace);  // get everything upto the first space character
                    String lastName = userFullName.substring(firstSpace).trim();

                    personalBasicDetailsRequest.setFirstName(firstName);
                    personalBasicDetailsRequest.setLastName(lastName);


                } else {
                    personalBasicDetailsRequest.setFirstName(userFullName);
                }

            }


            if (StringUtil.isNotNullOrEmptyString(mobileNumber.getText().toString())) {
                personalBasicDetailsRequest.setMobileNumber(mobileNumber.getText().toString());
            }

            if (StringUtil.isNotNullOrEmptyString(mCityId)) {
                personalBasicDetailsRequest.setCityMasterId(Integer.parseInt(mCityId));
            }
            try {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
                Date convertedDate = (Date) dateFormatter.parse(dateOfBirth.getText().toString());
                personalBasicDetailsRequest.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(convertedDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            personalBasicDetailsRequest.setMaritalStatus(relationshipStatus.getSelectedItem().toString());
            if (StringUtil.isNotNullOrEmptyString(noOfChildren.getText().toString())) {
                personalBasicDetailsRequest.setNoOfChildren(Integer.parseInt(noOfChildren.getText().toString()));
            }

            editProfilePresenter.getPersonalBasicDetailsAuthTokeInPresenter(personalBasicDetailsRequest);
            ((SheroesApplication) getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_PROFILE_EDITS, GoogleAnalyticsEventActions.EDIT_BASIC_DETAIl_PERSONAL, AppConstants.EMPTY_STRING);
            finish();
        }

    }

    @Override
    public void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {
        int toastDuration;
        if (boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {
            toastDuration = Toast.LENGTH_SHORT;
        } else {
            toastDuration = Toast.LENGTH_LONG;
        }
        Toast.makeText(EditUserProfileActivity.this, boardingDataResponse.getStatus(),
                toastDuration).show();
    }

    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {
        getPersonalBasicDetailsResponse(boardingDataResponse);
    }

}
