package appliedlife.pvtltd.SHEROES.views.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
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
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 27-02-2017.
 */

public class OnboardingWorkExperience extends BaseFragment implements DayPickerDialog.MyDayPickerListener,DatePickerDialog.OnDateSetListener {
   @Bind(R.id.et_exp_month)
    EditText met_exp_month;
    @Bind(R.id.et_exp_year)
    EditText met_exp_year;
    View view;
    OnBoardingWorkExpActivityIntractionListner mOnboardingIntractionListner;
    private final String mTAG = LogUtils.makeLogTag(SheroesHelpYouFragment.class);
    private TextView tvDisplayDate;
    private DatePicker dpresult;
    private Button btnChangeDate;



    static final int DATE_DIALOG_ID = 999;
    int monthOfYear,dayOfMonth,year;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (getActivity() instanceof OnBoardingWorkExpActivityIntractionListner) {
                mOnboardingIntractionListner = (OnBoardingWorkExpActivityIntractionListner) getActivity();
            }
        } catch (InstantiationException exception) {
            LogUtils.error(mTAG, AppConstants.EXCEPTION_MUST_IMPLEMENT + AppConstants.SPACE + mTAG + AppConstants.SPACE + exception.getMessage());
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SheroesApplication.getAppComponent(getContext()).inject(this);
        view = inflater.inflate(R.layout.onboarding_work_exp, container, false);
        ButterKnife.bind(this, view);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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

        DayPickerDialog pd = new DayPickerDialog(this);
        pd.setListener(this);
        pd.show(getActivity().getFragmentManager(), "MonthYearPickerDialog");

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        met_exp_year.setText(year+"");

       }

    @Override
    public void onErrorOccurence() {

    }

    @Override
    public void onDaySubmit(int tagsval) {
        met_exp_month.setText(tagsval+"");
    }

    public interface OnBoardingWorkExpActivityIntractionListner {
        void close();
        void onErrorOccurence();
        void callSheroesHelpYouTagPage();
    }


}
