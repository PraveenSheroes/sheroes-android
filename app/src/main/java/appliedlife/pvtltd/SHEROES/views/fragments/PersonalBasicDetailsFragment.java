package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.Doc;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetTagData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.PersonalBasicDetailsRequest;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserDetails;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserProfileResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.social.GoogleAnalyticsEventActions;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.ProfileSpinnerAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.ProfileView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by priyanka on 22/03/17.
 * Personal Basic Details Fragment
 */

public class PersonalBasicDetailsFragment extends BaseFragment implements ProfileView, View.OnClickListener {
    private final String TAG = LogUtils.makeLogTag(ProfessionalEditBasicDetailsFragment.class);
    private final String SCREEN_NAME = "Personal_edit_basic_details_screen";
    @Inject
    ProfilePersenter mProfilePresenter;
    @Bind(R.id.tv_profile_tittle)
    TextView mTvProfileTittle;
    @Bind(R.id.iv_back_profile)
    ImageView mIvBackProfile;
    @Bind(R.id.btn_personal_basic_details_save)
    Button mBtnPersonalBasicDetailsSave;
    @Bind(R.id.et_first_name)
    EditText mEtFirstName;
    @Bind(R.id.et_last_name)
    EditText mEtLastName;
    @Bind(R.id.et_child_number)
    EditText mEtChildNumber;
    @Bind(R.id.et_currnt_location)
    EditText mEtCurrntLocation;
    @Bind(R.id.spinner_relation_status)
    Spinner mSpinnerRelationStatus;
    @Bind(R.id.tv_mobile_value)
    TextView mTvMobileNo;
    @Bind(R.id.tv_email_value)
    TextView mTvEmailNo;
    ProfileView mProfileBasicDetailsCallBack;
    String mCitiId, mcityNm;
    MyProfileView myProfileView;
    @Bind(R.id.pb_profile_progress_bar)
    ProgressBar mProgress;
    @Bind(R.id.etxt_fromdate)
    EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    Date thedate;

    private SimpleDateFormat dateFormatter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof ProfileView) {

                mProfileBasicDetailsCallBack = (ProfileView) getActivity();

            }
        } catch (Exception e) {


        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        View view = inflater.inflate(R.layout.fragment_personal_basicdetails, container, false);
        ButterKnife.bind(this, view);
        mProfilePresenter.attachView(this);
        setProgressBar(mProgress);
        mTvProfileTittle.setText(R.string.ID_BASICDETAILS);
        mSpinnerRelationStatus.setAdapter(new ProfileSpinnerAdapter(getActivity(), R.layout.profile_current_status_spinner, getResources().getStringArray(R.array.relationship_status_arr)));

        if (null != getArguments()) {
            myProfileView = getArguments().getParcelable(AppConstants.EDUCATION_PROFILE);
            if (myProfileView != null && myProfileView.getUserDetails() != null) {
                UserDetails userDetails = myProfileView.getUserDetails();
                if (userDetails != null) {
                    if (StringUtil.isNotNullOrEmptyString(userDetails.getFirstName())) {
                        mEtFirstName.setText(userDetails.getFirstName());
                    }
                    if (StringUtil.isNotNullOrEmptyString(userDetails.getLastName())) {
                        mEtLastName.setText(userDetails.getLastName());
                    }
                    if (StringUtil.isNotNullOrEmptyString(userDetails.getCityMaster())) {
                        mEtCurrntLocation.setText(userDetails.getCityMaster());
                    }
                    if (userDetails.getNoOfChildren() > 0) {
                        mEtChildNumber.setText(String.valueOf(userDetails.getNoOfChildren()));
                    }
                    if (getString(R.string.married).equalsIgnoreCase(userDetails.getMaritalStatus())) {
                        mSpinnerRelationStatus.setSelection(0, true);
                    } else {
                        mSpinnerRelationStatus.setSelection(1, true);
                    }
                    mTvMobileNo.setText(userDetails.getMobile());
                    mTvEmailNo.setText(userDetails.getEmailid());
                    if (userDetails.getCityMasterId() > 0) {
                        mCitiId = String.valueOf(myProfileView.getUserDetails().getCityMasterId());
                    }
                    if (null != myProfileView.getUserDetails().getDob()) {
                        Calendar cal = new GregorianCalendar();
                        cal.setTime(new Date(myProfileView.getUserDetails().getDob()));
                        fromDateEtxt.setText(cal.get(Calendar.DAY_OF_MONTH) + " " + new SimpleDateFormat("MMM").format(cal.getTime()) + " " + cal.get(Calendar.YEAR));
                    }
                }
            }
        }
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        setDateTimeField();
        ((SheroesApplication) getActivity().getApplication()).trackScreenView(getString(R.string.ID_MY_PROFILE_PERSONAL_EDIT_BASIC_DETAIL));
        return view;
    }


    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    public void submitLocation(String cityId, String city) {
        mcityNm = city;
        mCitiId = cityId;
        mEtCurrntLocation.setText(city);

    }


    @OnClick(R.id.btn_personal_basic_details_save)
    public void Save_Basic_Details() {
        if (!StringUtil.isNotNullOrEmptyString(mEtFirstName.getText().toString()) || !StringUtil.isNotNullOrEmptyString(mEtLastName.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter First and Last Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (!StringUtil.isNotNullOrEmptyString(mEtCurrntLocation.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter Current location", Toast.LENGTH_SHORT).show();
            return;
        } else if (!StringUtil.isNotNullOrEmptyString(fromDateEtxt.getText().toString())) {
            Toast.makeText(getActivity(), "Please enter Date of birth", Toast.LENGTH_SHORT).show();
            return;
        } else {

            PersonalBasicDetailsRequest personalBasicDetailsRequest = new PersonalBasicDetailsRequest();
            AppUtils appUtils = AppUtils.getInstance();
            personalBasicDetailsRequest.setAppVersion(appUtils.getAppVersionName());
            //TODO:change Messageid
            personalBasicDetailsRequest.setCloudMessagingId(appUtils.getCloudMessaging());
            personalBasicDetailsRequest.setDeviceUniqueId(appUtils.getDeviceId());
            personalBasicDetailsRequest.setLastScreenName("string");
            personalBasicDetailsRequest.setScreenName("string");
            personalBasicDetailsRequest.setType("BASIC_PROFILE");
            personalBasicDetailsRequest.setSubType("BASIC_USER_PROFILE_SERVICE");
            personalBasicDetailsRequest.setSource(AppConstants.SOURCE_NAME);

            if (StringUtil.isNotNullOrEmptyString(mCitiId)) {
                personalBasicDetailsRequest.setCityMasterId(Integer.parseInt(mCitiId));
            }
            personalBasicDetailsRequest.setMaritalStatus(mSpinnerRelationStatus.getSelectedItem().toString());
            if (StringUtil.isNotNullOrEmptyString(mEtChildNumber.getText().toString())) {
                personalBasicDetailsRequest.setNoOfChildren(Integer.parseInt(mEtChildNumber.getText().toString()));
            }
            personalBasicDetailsRequest.setFirstName(mEtFirstName.getText().toString());
            personalBasicDetailsRequest.setLastName(mEtLastName.getText().toString());

            try {
                thedate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(fromDateEtxt.getText().toString());
                personalBasicDetailsRequest.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").format(thedate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mProfilePresenter.getPersonalBasicDetailsAuthTokeInPresenter(personalBasicDetailsRequest);
        }

        ((SheroesApplication) getActivity().getApplication()).trackEvent(GoogleAnalyticsEventActions.CATEGORY_PROFILE_EDITS, GoogleAnalyticsEventActions.EDIT_BASIC_DETAIl_PERSONAL, AppConstants.EMPTY_STRING);
    }

    @OnClick(R.id.et_currnt_location)
    public void onLocationClick() {

        ((ProfileActicity) getActivity()).callProfileLocation();

    }

    @OnClick(R.id.iv_back_profile)

    public void OnbackClick() {
        AppUtils.hideKeyboard(mEtChildNumber, TAG);
        mProfileBasicDetailsCallBack.onBackPressed(R.id.iv_back_profile);

    }


    @Override
    public void onClick(View view) {


        int id = view.getId();
        if (view == fromDateEtxt) {
            fromDatePickerDialog.show();
        }

    }

    @Override
    public void onBackPressed(int id) {


    }

    @Override
    public void visitingCardOpen(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void callFragment(int id) {

    }

    @Override
    public void getEducationResponse(BoardingDataResponse boardingDataResponse) {

    }


    @Override
    public void getPersonalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {
        int toastDuration;
        if (boardingDataResponse.getStatus().equals(AppConstants.SUCCESS)) {
            toastDuration = Toast.LENGTH_SHORT;
            mProfileBasicDetailsCallBack.onBackPressed(0);//TODO:Need to check with priyanka why need to pass value?
        } else {
            toastDuration = Toast.LENGTH_LONG;
        }
        //TODO:TBD about toast showing
        Toast.makeText(getActivity(), boardingDataResponse.getStatus(),
                toastDuration).show();
    }

    @Override
    public void getprofiletracelflexibilityResponse(BoardingDataResponse boardingDataResponse) {

    }


    @Override
    public void getUserSummaryResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalBasicDetailsResponse(BoardingDataResponse boardingDataResponse) {

    }

    @Override
    public void getProfessionalWorkLocationResponse(BoardingDataResponse boardingDataResponse) {

    }


    @Override
    public void getProfileVisitingCardResponse(ProfileEditVisitingCardResponse profileEditVisitingCardResponse) {

    }

    @Override
    public void getUserData(UserProfileResponse userProfileResponse) {

    }

    @Override
    public void getProfileListSuccess(GetTagData getAllData) {

    }

    @Override
    public void getProfileListSuccess(List<Doc> feedDetailList) {

    }
}
