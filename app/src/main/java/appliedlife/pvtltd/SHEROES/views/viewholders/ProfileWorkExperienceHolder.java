package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ExprienceEntity;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by SHEROES-TECH on 16-02-2017.
 */

public class ProfileWorkExperienceHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.tv_job_language_number)
    TextView mTvJobLanguageNumber;
    @Bind(R.id.tv_degree1)
    TextView mTv_degree1;
    @Bind(R.id.tv_date1)
    TextView mTv_date1;
    @Bind(R.id.tv_degree11)
    TextView mTv_degree11;
    @Bind(R.id.tv_degree12)
    TextView mTv_degree12;
    @Bind(R.id.tv_degree2)
    TextView mTv_degree2;
    @Bind(R.id.tv_degree21)
    TextView mTv_degree21;
    @Bind(R.id.tv_degree22)
    TextView mTv_degree22;
    @Bind(R.id.tv_date2)
    TextView mTv_date2;
    @Bind(R.id.tv_add_education)
    TextView mTv_add_education;
    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;
    ExprienceEntity exprienceEntity,exprienceEntity1;
    public ProfileWorkExperienceHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {
        this.dataItem = myProfileView;
        mTv_add_education.setOnClickListener(this);

        if(null !=dataItem) {

            mTvJobLanguageNumber.setText(dataItem.getType());

            List<ExprienceEntity> exprienceEntities=this.dataItem.getExprienceEntity();
            exprienceEntity1=exprienceEntities.get(0);

            if(null!=exprienceEntities && exprienceEntities.size()>0) {


                if (StringUtil.isNotNullOrEmptyString(exprienceEntities.get(0).getTitle())) {
                    mTv_degree1.setText(exprienceEntity1.getTitle());
                }
                if (StringUtil.isNotNullOrEmptyString(exprienceEntities.get(0).getCompany())) {
                    mTv_degree11.setText(exprienceEntity1.getCompany());
                }
                if (StringUtil.isNotNullOrEmptyString(exprienceEntities.get(0).getAboutOrg())) {
                    mTv_degree12.setText(exprienceEntity1.getAboutOrg());
                }
                if (exprienceEntities.get(0).getStartYear() > 0) {
                    String session = "";
                    if (exprienceEntities.get(0).getEndYear() > 0) {
                        session = "(" + exprienceEntities.get(0).getStartYear() + "-" + exprienceEntities.get(0).getEndYear() + ")";
                    } else {
                        session = "(" + exprienceEntities.get(0).getStartYear() + ")";
                    }
                    mTv_date1.setText(session);
                }
            }
        }

    }

    @OnClick(R.id.tv_more)
    public void onClickViewMore() {
        Toast.makeText(SheroesApplication.mContext, "Under development", Toast.LENGTH_SHORT).show();
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
