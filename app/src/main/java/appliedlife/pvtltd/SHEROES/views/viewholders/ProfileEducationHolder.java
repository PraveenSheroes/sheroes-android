package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.EducationEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.OpportunityType;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.R.id.view;
import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by priyanka on 16-02-2017.
 */

public class ProfileEducationHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.tv_job_language_number)
    TextView mTv_job_language_number;
    @Bind(R.id.tv_add_education)
    TextView mTv_add_education;
    @Bind(R.id.tv_degree1)
    TextView mTv_degree1;
    @Bind(R.id.tv_date1)
    TextView mTv_date1;
    @Bind(R.id.tv_degree11)
    TextView mTv_degree11;
    @Bind(R.id.tv_degree2)
    TextView mTv_degree2;
    @Bind(R.id.tv_degree21)
    TextView mTv_degree21;
    @Bind(R.id.tv_date2)
    TextView mTv_date2;
    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;


    public ProfileEducationHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }


    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {
        this.dataItem = myProfileView;
        mTv_add_education.setOnClickListener(this);
        mTv_job_language_number.setText(dataItem.getType());

        List<EducationEntity> educationEntity=this.dataItem.getEducationEntity();
        if(null !=educationEntity) {
            if (StringUtil.isNotEmptyCollection(educationEntity)) {
                if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getDegree())) {
                    mTv_degree1.setVisibility(View.VISIBLE);
                    mTv_degree1.setText(educationEntity.get(0).getDegree());
                }
                if(StringUtil.isNotNullOrEmptyString(educationEntity.get(0).getSchool())) {
                    mTv_degree11.setVisibility(View.VISIBLE);
                    mTv_degree11.setText(educationEntity.get(0).getSchool());
                }
                if(educationEntity.get(0).getSessionStartYear()>0) {
                    String session="";
                    if(educationEntity.get(0).getSessionEndYear()>0)
                    {
                        session="("+educationEntity.get(0).getSessionStartYear()+"-"+educationEntity.get(0).getSessionEndYear()+")";
                    }
                    else
                    {
                        session="("+educationEntity.get(0).getSessionStartYear()+")";
                    }
                    mTv_date1.setVisibility(View.VISIBLE);
                    mTv_date1.setText(session);
                }


                if(StringUtil.isNotNullOrEmptyString(educationEntity.get(1).getDegree())) {
                    mTv_degree2.setVisibility(View.VISIBLE);
                    mTv_degree2.setText(educationEntity.get(1).getDegree());
                }
                if(StringUtil.isNotNullOrEmptyString(educationEntity.get(1).getSchool())) {
                    mTv_degree21.setVisibility(View.VISIBLE);
                    mTv_degree21.setText(educationEntity.get(1).getSchool());
                }
                if(educationEntity.get(1).getSessionStartYear()>1) {
                    String session="";
                    if(educationEntity.get(1).getSessionEndYear()>1)
                    {
                        session="("+educationEntity.get(1).getSessionStartYear()+"-"+educationEntity.get(1).getSessionEndYear()+")";
                    }
                    else
                    {
                        session="("+educationEntity.get(1).getSessionStartYear()+")";
                    }
                    mTv_date2.setVisibility(View.VISIBLE);
                    mTv_date2.setText(session);
                }




            }
        }


    }

    @Override
    public void viewRecycled() {

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.tv_add_education:
                viewInterface.handleOnClick(this.dataItem,mTv_add_education);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());


        }




    }




}
