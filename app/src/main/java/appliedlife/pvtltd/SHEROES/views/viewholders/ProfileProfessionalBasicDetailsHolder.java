package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by priyanka on 16-02-2017.
 */

public class ProfileProfessionalBasicDetailsHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.tv_profile_basic_details)
    TextView mTv_profile_basic_details;
    @Bind(R.id.tv_current_status)
    TextView mTv_current_status;
    @Bind(R.id.tv_current_status_value)
    TextView mTv_current_status_value;
    @Bind(R.id.tv_sector)
    TextView mTv_sector;
    @Bind(R.id.tv_sector_value)
    TextView mTv_sector_value;
    @Bind(R.id.tv_total_work_experience)
    TextView mTv_total_work_experience;
    @Bind(R.id.tv_tot_exp_value)
    TextView mtv_tot_exp_value;
    @Bind(R.id.tv_language)
    TextView mTv_language;
    @Bind(R.id.tv_tot_language_value)
    TextView mTv_tot_language_value;
    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;
    @Bind(R.id.tv_professional_edit_basic_details)
    TextView mTv_professional_edit_basic_details;

    public ProfileProfessionalBasicDetailsHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {
        this.dataItem = myProfileView;
        mTv_professional_edit_basic_details.setOnClickListener(this);
        mTv_profile_basic_details.setText(AppConstants.USER_PROFILE);

        if(null !=dataItem) {
            if (StringUtil.isNotNullOrEmptyString(dataItem.getUserDetails().getMaritalStatus())) {
                mTv_current_status_value.setText(dataItem.getUserDetails().getMaritalStatus());
            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getUserDetails().getSector())) {
                mTv_sector_value.setText(dataItem.getUserDetails().getSector());
            }
            if (StringUtil.isNotNullOrEmptyString(""+dataItem.getUserDetails().getTotalExp())) {
                mtv_tot_exp_value.setText(""+dataItem.getUserDetails().getTotalExp());
            }
            /*if (StringUtil.isNotNullOrEmptyString(dataItem.getUserDetails().getDepartment())) {
                mTv_tot_language_value.setText(dataItem.getUserDetails().getDepartment());
            }*/

        }

        }
    @Override
    public void viewRecycled() {


    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.tv_professional_edit_basic_details:

                viewInterface.handleOnClick(this.dataItem,mTv_professional_edit_basic_details);

                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }

    }


}
