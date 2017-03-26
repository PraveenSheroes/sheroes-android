package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseFragment;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 27-02-2017.
 */

public class OnBoardingWorkExperienceFragment extends BaseFragment implements DayPickerDialog.MyDayPickerListener, DatePickerDialog.OnDateSetListener {
    private final String TAG = LogUtils.makeLogTag(OnBoardingWorkExperienceFragment.class);
    private static final int DATE_DIALOG_ID = 999;
    @Bind(R.id.et_exp_month)
    EditText mEtExpMonth;
    @Bind(R.id.et_exp_year)
    EditText mEtExpYear;
    View view;
    OnBoardingWorkExpActivityIntractionListner mOnboardingIntractionListner;
    private TextView tvDisplayDate;
    private DatePicker dpresult;
    private Button btnChangeDate;
    private int monthOfYear, dayOfMonth, year;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof OnBoardingWorkExpActivityIntractionListner) {
                mOnboardingIntractionListner = (OnBoardingWorkExpActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(TAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + TAG + AppConstants.SPACE + exception.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        view = inflater.inflate(R.layout.onboarding_work_exp, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.et_exp_year)
    public void clickYear() {

        DatePickerExample pd = new DatePickerExample();
        pd.setListener(this);
        pd.show(getActivity().getFragmentManager(), "MonthYearPickerDialog");

    }

    @OnClick(R.id.et_exp_month)
    public void clickMonth() {

        DayPickerDialog pd = new DayPickerDialog();
        pd.setListener(this);
        pd.show(getActivity().getFragmentManager(), "MonthYearPickerDialog");

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mEtExpYear.setText(year + AppConstants.EMPTY_STRING);

    }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onDaySubmit(int tagsval) {
        mEtExpMonth.setText(tagsval + AppConstants.EMPTY_STRING);
    }

    public interface OnBoardingWorkExpActivityIntractionListner {

        void onWorkExp(String workExp);
    }

    @OnClick(R.id.iv_work_exp_next)
    public void workExpOnClick() {
        String monthYear= mEtExpMonth.getText().toString()+ mEtExpYear.getText().toString();
        if(StringUtil.isNotNullOrEmptyString(monthYear)) {
            mOnboardingIntractionListner.onWorkExp(monthYear);
        }
    }

}
