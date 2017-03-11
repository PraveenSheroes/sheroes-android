package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by SHEROES-TECH on 16-02-2017.
 */

public class ProfileWorkExperienceHolder extends BaseViewHolder<ProfileViewList> {
    @Bind(R.id.tv_job_language_number)
    TextView mTv_job_language_number;
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
    private ProfileViewList dataItem;







    public ProfileWorkExperienceHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public ProfileWorkExperienceHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(ProfileViewList obj, Context context, int position) {
        this.dataItem = obj;
        mTv_add_education.setOnClickListener(this);
        mTv_degree12.setVisibility(View.VISIBLE);
        mTv_degree22.setVisibility(View.VISIBLE);

        mTv_job_language_number.setText(dataItem.getTag());
        mTv_degree1.setText(dataItem.getItem1());
        mTv_date1.setText(dataItem.getItem4());
        mTv_degree11.setText(dataItem.getItem2());
        mTv_degree12.setText(dataItem.getItem3());
        mTv_degree2.setText(dataItem.getItem5());
        mTv_degree21.setText(dataItem.getItem6());
        mTv_degree22.setText(dataItem.getItem7());
        mTv_date2.setText(dataItem.getItem8());

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
