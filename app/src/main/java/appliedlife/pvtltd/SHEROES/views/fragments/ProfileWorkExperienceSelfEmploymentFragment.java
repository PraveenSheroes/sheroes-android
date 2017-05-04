package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseDialogFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllDataDocument;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileEditVisitingCardResponse;
import appliedlife.pvtltd.SHEROES.presenters.ProfilePersenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.MARK_AS_SPAM;
import static appliedlife.pvtltd.SHEROES.enums.OnBoardingEnum.LOCATION;
import static appliedlife.pvtltd.SHEROES.utils.AppUtils.workExpRequestBuilder;

/**
 * Created by sheroes on 08/03/17.
 */

public class ProfileWorkExperienceSelfEmploymentFragment extends BaseDialogFragment implements DatePickerDialog.OnDateSetListener {
    private final String TAG = LogUtils.makeLogTag(ProfileWorkExperienceSelfEmploymentFragment.class);
    private ExprienceEntity mExprienceEntity;
    @Bind(R.id.tv_setting_tittle)
    TextView mTvTitle;
    @Bind(R.id.tv_setting_tittle1)
    TextView mTvSubTitle;
    @Bind(R.id.et_self_designation)
    EditText mEtvSelfDesignation;
    @Bind(R.id.tv_functional_area_item)
    TextView mTvFunctionAreaItem;
    @Bind(R.id.et_self_organization_name)
    EditText mEtvSelfOrgnisationName;
    @Bind(R.id.et_self_organization_brandname)
    EditText mEtvSelfOrgnisationBrandName;
    @Bind(R.id.et_job_start_day)
    TextView mEtvJobStartDay;
    @Bind(R.id.et_job_start_month)
    TextView mEtvJobStartMonth;
    @Bind(R.id.et_job_start_year)
    TextView mEtvJobStartYear;
    @Bind(R.id.et_job_end_day)
    TextView mEtvJobEndDay;
    @Bind(R.id.et_job_end_month)
    TextView mEtvJobEndMonth;
    @Bind(R.id.et_job_end_year)
    TextView mEtvJobEndYear;
    @Bind(R.id.et_description_your_work)
    EditText mEtDescriptionYourWork;
    @Bind(R.id.et_self_sector_name)
    EditText mEtSectorName;
    @Bind(R.id.et_self_about_organization)
    EditText mEtSelfAboutOrganization;
    @Bind(R.id.et_self_weburl)
    EditText mEtSelfWebUrl;
    @Bind(R.id.et_self_app_url)
    EditText mEtSelfAppUrl;
    @Bind(R.id.checkbox_is_working)
    CheckBox checkBoxIsWorking;
    @Bind(R.id.tv_job_loc_city)
    TextView mTvJobLocCity;
    @Bind(R.id.sp_orgnization_type)
    Spinner mSpinerOrgType;
    @Bind(R.id.pb_login_progress_bar)
    ProgressBar mProgressBar;
    private int mOrgTypeDataPosition = 1;
    List<String> categories;
    @Inject
    AppUtils mAppUtils;
    @Inject
    ProfilePersenter mProfilePersenter;
    private String editOrAdd;
    private Long functionAreaId;
    private Long locationId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getActivity()).inject(this);
        View view = inflater.inflate(R.layout.fragment_workexperience_selfempolyment, container, false);
        ButterKnife.bind(this, view);
        mProfilePersenter.attachView(this);
        initializeAllViews();
        return view;
    }

    private void initializeAllViews() {
        if (null != getArguments()) {
            mExprienceEntity = getArguments().getParcelable(AppConstants.WORK_EXPERIENCE_TYPE);
            editOrAdd = getString(R.string.ID_EDITED);
        } else {
            editOrAdd = getString(R.string.ID_ADDED);
        }
        mTvTitle.setText(getString(R.string.ID_WORK_EXPERIENCE));
        mTvSubTitle.setText(getString(R.string.ID_JOB));
        if (null != mExprienceEntity) {
            if (mExprienceEntity.isCurrentlyWorkingHere()) {
                checkBoxIsWorking.setChecked(mExprienceEntity.isCurrentlyWorkingHere());
                mEtvJobEndDay.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_setting_preference_basicdetails));
                mEtvJobEndMonth.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_setting_preference_basicdetails));
                mEtvJobEndYear.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_setting_preference_basicdetails));
                mEtvJobEndDay.setEnabled(false);
                mEtvJobEndMonth.setEnabled(false);
                mEtvJobEndYear.setEnabled(false);
                mEtvJobEndDay.setText(getString(R.string.ID_DD));
                mEtvJobEndMonth.setText(getString(R.string.ID_MM));
                mEtvJobEndYear.setText(getString(R.string.ID_YYYY));
            } else {
                if (mExprienceEntity.getEndMonth() > 0) {
                    mEtvJobEndMonth.setText(String.valueOf(mExprienceEntity.getEndMonth()));
                }
                if (mExprienceEntity.getEndYear() > 0) {
                    mEtvJobEndYear.setText(String.valueOf(mExprienceEntity.getEndYear()));
                }
            }
            if (StringUtil.isNotNullOrEmptyString(mExprienceEntity.getTitle())) {
                mEtvSelfDesignation.setText(mExprienceEntity.getTitle());
            }
            if (StringUtil.isNotNullOrEmptyString(mExprienceEntity.getCompany())) {
                mEtvSelfOrgnisationName.setText(mExprienceEntity.getCompany());
            }
            if (StringUtil.isNotNullOrEmptyString(mExprienceEntity.getOrgBrandName())) {
                mEtvSelfOrgnisationBrandName.setText(mExprienceEntity.getOrgBrandName());
            }
            if (StringUtil.isNotNullOrEmptyString(mExprienceEntity.getDescription())) {
                mEtDescriptionYourWork.setText(mExprienceEntity.getDescription());
            }
            if (StringUtil.isNotNullOrEmptyString(mExprienceEntity.getFunctionalAreaName())) {
                functionAreaId = mExprienceEntity.getFunctionalAreaId();
                mTvFunctionAreaItem.setText(mExprienceEntity.getFunctionalAreaName());
                mTvFunctionAreaItem.setVisibility(View.VISIBLE);
            } else {
                mTvFunctionAreaItem.setVisibility(View.GONE);
            }
            if (StringUtil.isNotNullOrEmptyString(mExprienceEntity.getOrgWebUrl())) {
                mEtSelfWebUrl.setText(mExprienceEntity.getOrgWebUrl());
            }
            if (StringUtil.isNotNullOrEmptyString(mExprienceEntity.getAboutOrg())) {
                mEtSelfAboutOrganization.setText(mExprienceEntity.getAboutOrg());
            }
            if (StringUtil.isNotNullOrEmptyString(mExprienceEntity.getLocation())) {
                locationId = mExprienceEntity.getLocationId();
                mTvJobLocCity.setText(mExprienceEntity.getLocation());
                mTvJobLocCity.setVisibility(View.VISIBLE);
            } else {
                mTvJobLocCity.setVisibility(View.GONE);
            }
            if (mExprienceEntity.getStartMonth() > 0) {
                mEtvJobStartMonth.setText(String.valueOf(mExprienceEntity.getStartMonth()));
            }
            if (mExprienceEntity.getStartYear() > 0) {
                mEtvJobStartYear.setText(String.valueOf(mExprienceEntity.getStartYear()));
            }
        }
        categories = new ArrayList<String>();
        categories.add("Private");
        categories.add("Public");
        categories.add("Government");
        categories.add("Non Proit");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinerOrgType.setAdapter(dataAdapter);
        mSpinerOrgType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mOrgTypeDataPosition = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ExprienceEntity makeWorkExpRequest() {
        ExprienceEntity exprienceEntity = workExpRequestBuilder(mOrgTypeDataPosition, true);
        if (StringUtil.isNotNullOrEmptyString(mEtvSelfDesignation.getText().toString())) {
            exprienceEntity.setTitle(mEtvSelfDesignation.getText().toString());

            if (StringUtil.isNotNullOrEmptyString(mTvFunctionAreaItem.getText().toString()) && null != functionAreaId) {
                exprienceEntity.setFunctionalAreaId(functionAreaId);

                if (StringUtil.isNotNullOrEmptyString(mTvJobLocCity.getText().toString()) && null != locationId) {
                    exprienceEntity.setLocationId(locationId);

                    if (StringUtil.isNotNullOrEmptyString(mEtvSelfOrgnisationName.getText().toString())) {
                        exprienceEntity.setCompany(mEtvSelfOrgnisationName.getText().toString());


                        if (StringUtil.isNotNullOrEmptyString(mEtvJobStartMonth.getText().toString()) && StringUtil.isNotNullOrEmptyString(mEtvJobStartYear.getText().toString())) {
                           // exprienceEntity.setStartDay(Integer.parseInt(mEtvJobStartDay.getText().toString()));
                            exprienceEntity.setStartMonth(Integer.parseInt(mEtvJobStartMonth.getText().toString()));
                            exprienceEntity.setStartYear(Integer.parseInt(mEtvJobStartYear.getText().toString()));

                            if (checkBoxIsWorking.isChecked()) {
                                exprienceEntity.setCurrentlyWorkingHere(checkBoxIsWorking.isChecked());
                                if (StringUtil.isNotNullOrEmptyString(mEtDescriptionYourWork.getText().toString())) {
                                    exprienceEntity.setDescription(mEtDescriptionYourWork.getText().toString());
                                }
                                if (StringUtil.isNotNullOrEmptyString(mEtSelfAboutOrganization.getText().toString())) {
                                    exprienceEntity.setAboutOrg(mEtSelfAboutOrganization.getText().toString());
                                }
                                if (StringUtil.isNotNullOrEmptyString(mEtSelfWebUrl.getText().toString())) {
                                    exprienceEntity.setOrgWebUrl(mEtSelfWebUrl.getText().toString());
                                }
                                if (StringUtil.isNotNullOrEmptyString(mEtvSelfOrgnisationBrandName.getText().toString())) {
                                    exprienceEntity.setOrgBrandName(mEtvSelfOrgnisationBrandName.getText().toString());
                                }
                                exprienceEntity.setOrganisationType(mOrgTypeDataPosition);
                                if (null != mExprienceEntity && null != mExprienceEntity.getId() && mExprienceEntity.getId() > 0) {
                                    editOrAdd = getString(R.string.ID_EDITED);
                                    exprienceEntity.setId(mExprienceEntity.getId());
                                }else
                                {
                                    editOrAdd = getString(R.string.ID_ADDED);
                                }
                            } else {
                                if ( StringUtil.isNotNullOrEmptyString(mEtvJobEndMonth.getText().toString()) && StringUtil.isNotNullOrEmptyString(mEtvJobEndYear.getText().toString())) {
                                    exprienceEntity.setEndDay(Integer.parseInt(mEtvJobEndDay.getText().toString()));
                                    exprienceEntity.setEndMonth(Integer.parseInt(mEtvJobEndMonth.getText().toString()));
                                    exprienceEntity.setEndYear(Integer.parseInt(mEtvJobEndYear.getText().toString()));

                                    if (StringUtil.isNotNullOrEmptyString(mEtDescriptionYourWork.getText().toString())) {
                                        exprienceEntity.setDescription(mEtDescriptionYourWork.getText().toString());
                                    }

                                    if (StringUtil.isNotNullOrEmptyString(mEtSelfAboutOrganization.getText().toString())) {
                                        exprienceEntity.setAboutOrg(mEtSelfAboutOrganization.getText().toString());
                                    }
                                    if (StringUtil.isNotNullOrEmptyString(mEtSelfWebUrl.getText().toString())) {
                                        exprienceEntity.setOrgWebUrl(mEtSelfWebUrl.getText().toString());
                                    }
                                    if (StringUtil.isNotNullOrEmptyString(mEtvSelfOrgnisationBrandName.getText().toString())) {
                                        exprienceEntity.setOrgBrandName(mEtvSelfOrgnisationBrandName.getText().toString());
                                    }
                                    exprienceEntity.setOrganisationType(mOrgTypeDataPosition);
                                    exprienceEntity.setCurrentlyWorkingHere(checkBoxIsWorking.isChecked());
                                    if (null != mExprienceEntity && null != mExprienceEntity.getId() && mExprienceEntity.getId() > 0) {
                                        editOrAdd = getString(R.string.ID_EDITED);
                                        exprienceEntity.setId(mExprienceEntity.getId());
                                    }else
                                    {
                                        editOrAdd = getString(R.string.ID_ADDED);
                                    }
                                } else {
                                    exprienceEntity = null;
                                    Toast.makeText(getActivity(), "Please enter  valid end date", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            exprienceEntity = null;
                            Toast.makeText(getActivity(), "Please enter valid start date", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        exprienceEntity = null;
                        Toast.makeText(getActivity(), "Please enter organization name", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    exprienceEntity = null;
                    Toast.makeText(getActivity(), "Please enter location(city)", Toast.LENGTH_SHORT).show();
                }

            } else {
                exprienceEntity = null;
                Toast.makeText(getActivity(), "Please enter functional area", Toast.LENGTH_SHORT).show();
            }

        } else {
            exprienceEntity = null;
            Toast.makeText(getActivity(), "Please enter designation", Toast.LENGTH_SHORT).show();
        }


        return exprienceEntity;
    }

    public void locationData(List<GetAllDataDocument> jobLocationList) {
        if (StringUtil.isNotEmptyCollection(jobLocationList)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (GetAllDataDocument city : jobLocationList) {
                locationId = Long.parseLong(city.getId());
                stringBuilder.append(city.getTitle()).append(AppConstants.COMMA);
            }
            String loc = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
            mTvJobLocCity.setText(loc);
        }
    }

    public void setFunctionAreaDataItem(List<LabelValue> functionAreaDataItem) {
        if (StringUtil.isNotEmptyCollection(functionAreaDataItem)) {
            mTvFunctionAreaItem.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            for (LabelValue fuctinalArea : functionAreaDataItem) {
                functionAreaId = fuctinalArea.getValue();
                stringBuilder.append(fuctinalArea.getLabel()).append(AppConstants.COMMA);
            }
            String funcArea = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
            mTvFunctionAreaItem.setText(funcArea);
        }
    }

    @OnCheckedChanged(R.id.checkbox_is_working)
    public void clickCheckBox() {
        if (checkBoxIsWorking.isChecked()) {
            mEtvJobEndDay.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_setting_preference_basicdetails));
            mEtvJobEndMonth.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_setting_preference_basicdetails));
            mEtvJobEndYear.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_setting_preference_basicdetails));
            mEtvJobEndDay.setEnabled(false);
            mEtvJobEndMonth.setEnabled(false);
            mEtvJobEndYear.setEnabled(false);
            mEtvJobEndDay.setText(getString(R.string.ID_DD));
            mEtvJobEndMonth.setText(getString(R.string.ID_MM));
            mEtvJobEndYear.setText(getString(R.string.ID_YYYY));
        } else {
            mEtvJobEndDay.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_contactdetails_page));
            mEtvJobEndMonth.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_contactdetails_page));
            mEtvJobEndYear.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_contactdetails_page));
            mEtvJobEndDay.setEnabled(true);
            mEtvJobEndMonth.setEnabled(true);
            mEtvJobEndYear.setEnabled(true);
            mEtvJobEndDay.setText(AppConstants.EMPTY_STRING);
            mEtvJobEndMonth.setText(AppConstants.EMPTY_STRING);
            mEtvJobEndYear.setText(AppConstants.EMPTY_STRING);
        }
    }

    @OnClick(R.id.et_job_start_day)
    public void clickStartDay() {
        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this, AppConstants.ONE_CONSTANT);
        pd.show(getActivity().getFragmentManager(), AppConstants.MONTH_YEAR_PICKER_DIALOG);
    }

    @OnClick(R.id.et_job_start_month)
    public void clickStartMonth() {
        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this, AppConstants.TWO_CONSTANT);
        pd.show(getActivity().getFragmentManager(), AppConstants.MONTH_YEAR_PICKER_DIALOG);
    }

    @OnClick(R.id.et_job_start_year)
    public void clickStartYear() {
        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this, AppConstants.THREE_CONSTANT);
        pd.show(getActivity().getFragmentManager(), AppConstants.MONTH_YEAR_PICKER_DIALOG);
    }

    @OnClick(R.id.et_job_end_day)
    public void clickEndDay() {
        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this, AppConstants.FOURTH_CONSTANT);
        pd.show(getActivity().getFragmentManager(), AppConstants.MONTH_YEAR_PICKER_DIALOG);
    }

    @OnClick(R.id.et_job_end_month)
    public void clickEndMoth() {
        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this, 5);
        pd.show(getActivity().getFragmentManager(), AppConstants.MONTH_YEAR_PICKER_DIALOG);
    }

    @OnClick(R.id.et_job_end_year)
    public void clickEndYear() {
        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this, 6);
        pd.show(getActivity().getFragmentManager(), AppConstants.MONTH_YEAR_PICKER_DIALOG);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setDateAccordingToInput(dayOfMonth, year);
    }

    @OnClick(R.id.tv_functional_area_lable)
    public void onFuncAreaClick() {
        ((ProfileActicity) getActivity()).functionAreaData();
    }

    @OnClick(R.id.tv_functional_area_item)
    public void onFuncAreaDataClick() {
        ((ProfileActicity) getActivity()).functionAreaData();
    }

    @OnClick(R.id.tv_job_loc_lable)
    public void onJobCityLableClick() {
        mTvJobLocCity.setVisibility(View.VISIBLE);
        ((ProfileActicity) getActivity()).searchLocationData(AppConstants.LOCATION_CITY_GET_ALL_DATA_KEY, LOCATION);
    }

    @OnClick(R.id.tv_job_loc_city)
    public void onJobCityClick() {
        mTvJobLocCity.setVisibility(View.VISIBLE);
        ((ProfileActicity) getActivity()).searchLocationData(AppConstants.LOCATION_CITY_GET_ALL_DATA_KEY, LOCATION);
    }

    @OnClick(R.id.tv_save_work_exp)
    public void onSaveWorkExp() {
        mExprienceEntity = makeWorkExpRequest();
        if (null != mExprienceEntity) {
            mExprienceEntity.setStartDay(1);
            mExprienceEntity.setEndDay(1);
            mProfilePersenter.getWorkExpResponseInPresenter(mExprienceEntity);
        }
    }

    @OnClick(R.id.iv_back_setting)
    public void onBackWorkExp() {
        ((ProfileActicity) getActivity()).onBackPressed();
    }

    private void setDateAccordingToInput(int callForDate, int value) {
        switch (callForDate) {
            case AppConstants.ONE_CONSTANT:
                mEtvJobStartDay.setText(String.valueOf(value));
                break;
            case AppConstants.TWO_CONSTANT:
                mEtvJobStartMonth.setText(String.valueOf(value));
                break;
            case AppConstants.THREE_CONSTANT:
                mEtvJobStartYear.setText(String.valueOf(value));
                break;
            case AppConstants.FOURTH_CONSTANT:
                mEtvJobEndDay.setText(String.valueOf(value));
                break;
            case 5:
                mEtvJobEndMonth.setText(String.valueOf(value));
                break;
            case 6:
                mEtvJobEndYear.setText(String.valueOf(value));
                break;

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
        switch (boardingDataResponse.getStatus()) {
            case AppConstants.SUCCESS:
                ((ProfileActicity) getActivity()).onBackPressed();
                Toast.makeText(getActivity(), "Work experience " + editOrAdd + " successfully", Toast.LENGTH_SHORT).show();
                break;
            case AppConstants.FAILED:
                ((ProfileActicity) getActivity()).onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), MARK_AS_SPAM);
                break;
            default:
                ((ProfileActicity) getActivity()).onShowErrorDialog(getString(R.string.ID_GENERIC_ERROR), MARK_AS_SPAM);
        }
    }

    @Override
    public void startProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.bringToFront();
    }

    @Override
    public void stopProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), R.style.Theme_Material_Light_Dialog_NoMinWidth) {
            @Override
            public void onBackPressed() {
                ((ProfileActicity) getActivity()).onBackPressed();
            }
        };
    }
}
