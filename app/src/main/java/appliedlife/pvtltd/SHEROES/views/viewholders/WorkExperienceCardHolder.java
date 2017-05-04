package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 03-05-2017.
 */

public class WorkExperienceCardHolder extends BaseViewHolder<ExprienceEntity> {
    private final String TAG = LogUtils.makeLogTag(WorkExperienceCardHolder.class);
    @Bind(R.id.tv_work_exp_designation_name)
    TextView mTvWorkExpDesignationName;
    @Bind(R.id.tv_work_exp_date_details)
    TextView mTvWorkExpDateDetails;
    @Bind(R.id.tv_work_exp_about_company)
    TextView mTvAboutCompany;
    @Bind(R.id.tv_work_exp_company_name)
    TextView mTvCompanyName;
    @Bind(R.id.tv_edit_workexperience)
    TextView mTvAddWorkExp;
    @Bind(R.id.tv_work_exp_type)
    TextView mTvAddWorkExpType;

    BaseHolderInterface viewInterface;
    private ExprienceEntity dataItem;

    public WorkExperienceCardHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ExprienceEntity exprienceEntity, Context context, int position) {
        this.dataItem = exprienceEntity;
        dataItem.setItemPosition(position);
        if (null != dataItem) {
            if (StringUtil.isNotNullOrEmptyString(dataItem.getTitle())) {
                mTvWorkExpDesignationName.setText(dataItem.getTitle());
            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getCompany())) {
                mTvCompanyName.setText(dataItem.getCompany());
            }

            if (StringUtil.isNotNullOrEmptyString(dataItem.getDescription())) {
                mTvAboutCompany.setText(dataItem.getDescription());
            }
            if (dataItem.isCurrentlyWorkingHere()) {
                if (dataItem.getStartMonth() > 0) {
                    StringBuilder currentWork = new StringBuilder();
                    currentWork.append(setDateWithYearMonth(dataItem.getStartMonth())).append(AppConstants.SPACE).append(dataItem.getStartYear()).append(AppConstants.SPACE).append(AppConstants.DASH).append(AppConstants.SPACE).append(AppConstants.PRESENT);
                    mTvWorkExpDateDetails.setText(currentWork.toString());
                }
            } else {
                if (dataItem.getStartMonth() > 0 && dataItem.getEndYear() > 0) {
                    StringBuilder workExpDate = new StringBuilder();
                    workExpDate.append(setDateWithYearMonth(dataItem.getStartMonth())).append(AppConstants.SPACE).append(dataItem.getStartYear()).append(AppConstants.SPACE).append(AppConstants.DASH).append(AppConstants.SPACE).append(setDateWithYearMonth(dataItem.getEndMonth())).append(AppConstants.SPACE).append(dataItem.getEndYear());
                    mTvWorkExpDateDetails.setText(workExpDate.toString());
                }
            }

        }


    }

    private String setDateWithYearMonth(int month) {
        String monthName = AppConstants.EMPTY_STRING;
        switch (month) {
            case 1:
                monthName = "JAN";
                break;
            case 2:
                monthName = "FEB";
                break;
            case 3:
                monthName = "MAR";
                break;
            case 4:

                monthName = "APR";
                break;
            case 5:

                monthName = "MAY";
                break;
            case 6:
                monthName = "JUN";
                break;
            case 7:
                monthName = "JUL";
                break;
            case 8:
                monthName = "AUG";
                break;
            case 9:
                monthName = "SEP";
                break;
            case 10:
                monthName = "OCT";
                break;
            case 11:
                monthName = "NOV";
                break;
            case 12:
                monthName = "DEC";
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + AppConstants.SPACE + TAG + AppConstants.SPACE + month);
        }
        return monthName;
    }

    @OnClick(R.id.tv_edit_workexperience)
    public void onClickAddEducation() {
        viewInterface.handleOnClick(dataItem, mTvAddWorkExp);
    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {

    }
}
