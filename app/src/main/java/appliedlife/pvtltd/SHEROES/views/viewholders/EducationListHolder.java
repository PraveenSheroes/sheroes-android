package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by sheroes on 04/05/17.
 */

public class EducationListHolder extends BaseViewHolder<EducationEntity> {
    @Bind(R.id.tv_edit_education)
    TextView mTv_add_education;
    @Bind(R.id.tv_education_name)
    TextView tvEducationName;
    @Bind(R.id.tv_date_details)
    TextView mTv_date1;
    @Bind(R.id.tv_degree_name)
    TextView mTvSchool;
    @Bind(R.id.tv_college_name)
    TextView mTvStudy;
    BaseHolderInterface viewInterface;
    private EducationEntity dataItem;


    public EducationListHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }


    @Override
    public void bindData(EducationEntity educationEntity, Context context, int position) {
        this.dataItem = educationEntity;
        mTv_add_education.setOnClickListener(this);

        if(null !=educationEntity) {
            if (null !=educationEntity) {
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.getDegree())) {
                        tvEducationName.setVisibility(View.VISIBLE);
                        tvEducationName.setText(educationEntity.getDegree());
                    }
                    if(StringUtil.isNotNullOrEmptyString(educationEntity.getSchool())) {
                        mTvSchool.setVisibility(View.VISIBLE);
                        mTvSchool.setText(educationEntity.getSchool());
                    }
                if(StringUtil.isNotNullOrEmptyString(educationEntity.getFieldOfStudy())) {
                    mTvStudy.setVisibility(View.VISIBLE);
                    mTvStudy.setText(educationEntity.getFieldOfStudy());
                }
                    if(educationEntity.getSessionStartYear()>0) {
                        String session="";
                        if(educationEntity.getSessionEndYear()>0)
                        {
                            session=getMonth(educationEntity.getSessionStartMonth())+" "+educationEntity.getSessionStartYear()+" - "+getMonth(educationEntity.getSessionEndMonth())+" "+educationEntity.getSessionEndYear()+")";
                        }
                        else
                        {
                            session="("+educationEntity.getSessionStartYear()+")";
                        }
                        mTv_date1.setVisibility(View.VISIBLE);
                        mTv_date1.setText(session);
                    }
                }

        }


    }
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.tv_edit_education:
                viewInterface.handleOnClick(this.dataItem,mTv_add_education);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());


        }




    }




}
