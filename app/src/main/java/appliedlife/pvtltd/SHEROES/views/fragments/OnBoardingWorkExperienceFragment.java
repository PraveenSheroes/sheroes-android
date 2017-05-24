package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum;
import appliedlife.pvtltd.SHEROES.models.entities.community.GetAllData;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.BoardingDataResponse;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.GetInterestJobResponse;
import appliedlife.pvtltd.SHEROES.presenters.OnBoardingPresenter;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.OnBoardingActivity;
import appliedlife.pvtltd.SHEROES.views.fragments.dialogfragment.DatePickerExample;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.OnBoardingView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.enums.FeedParticipationEnum.ERROR_ON_ONBOARDING;

/**
 * Created by Ajit Kumar on 27-02-2017.
 */

public class OnBoardingWorkExperienceFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener,OnBoardingView {
    private final String TAG = LogUtils.makeLogTag(OnBoardingWorkExperienceFragment.class);
    @Bind(R.id.et_exp_month)
    EditText mEtExpMonth;
    @Bind(R.id.et_exp_year)
    EditText mEtExpYear;
    private View view;
    @Bind(R.id.pb_work_exp_progress_bar)
    ProgressBar mProgressBar;
    @Inject
    OnBoardingPresenter mOnBoardingPresenter;
    @Inject
    AppUtils mAppUtils;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        view = inflater.inflate(R.layout.onboarding_work_exp, container, false);
        ButterKnife.bind(this, view);
        mOnBoardingPresenter.attachView(this);
        super.setProgressBar(mProgressBar);
        return view;
    }

    @OnClick(R.id.et_exp_year)
    public void clickYear() {
        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this,AppConstants.ONE_CONSTANT);
        pd.show(getActivity().getFragmentManager(), AppConstants.MONTH_YEAR_PICKER_DIALOG);

    }

    @OnClick(R.id.et_exp_month)
    public void clickMonth() {
        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this,AppConstants.TWO_CONSTANT);
        pd.show(getActivity().getFragmentManager(), AppConstants.MONTH_YEAR_PICKER_DIALOG);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if(dayOfMonth==AppConstants.ONE_CONSTANT)
        {
            mEtExpYear.setText(year + AppConstants.EMPTY_STRING);
        }else
        {
            mEtExpMonth.setText(month + AppConstants.EMPTY_STRING);
        }
    }
    @OnClick(R.id.iv_work_exp_next)
    public void workExpOnClick() {
        if(StringUtil.isNotNullOrEmptyString(mEtExpMonth.getText().toString())&&StringUtil.isNotNullOrEmptyString(mEtExpYear.getText().toString())) {
            int year = Integer.parseInt(mEtExpYear.getText().toString());
            int month = Integer.parseInt(mEtExpMonth.getText().toString());
            mOnBoardingPresenter.getWorkExpToPresenter(mAppUtils.boardingWorkExpRequestBuilder(year,month));
        }
    }


    @Override
    public void getAllDataResponse(GetAllData getAllData) {

    }

    @Override
    public void getIntersetJobResponse(GetInterestJobResponse getInterestJobResponse) {

    }
    @Override
    public void showError(String errorMsg, FeedParticipationEnum feedParticipationEnum) {
        mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(errorMsg, feedParticipationEnum);
    }
    @Override
    public void getBoardingJobResponse(BoardingDataResponse boardingDataResponse) {
        if (null != boardingDataResponse) {
            switch (boardingDataResponse.getStatus()) {
                case AppConstants.SUCCESS:
                    ((OnBoardingActivity)getActivity()).onWorkExpSuccess();
                    break;
                case AppConstants.FAILED:
                    mHomeSearchActivityFragmentIntractionWithActivityListner.onShowErrorDialog(boardingDataResponse.getFieldErrorMessageMap().get(AppConstants.INAVLID_DATA), ERROR_ON_ONBOARDING);
                    break;
            }
        }
    }
}
