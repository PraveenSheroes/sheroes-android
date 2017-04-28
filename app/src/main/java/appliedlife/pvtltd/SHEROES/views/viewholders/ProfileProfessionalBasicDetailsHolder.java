package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserDetails;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by priyanka on 16-02-2017.
 */

public class ProfileProfessionalBasicDetailsHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.tv_profile_basic_details)
    TextView mTv_profile_basic_details;
    @Bind(R.id.tv_current_status_value)
    TextView mTvCurrentStatusValue;
    @Bind(R.id.tv_sector_value)
    TextView mTvSectorValue;
    @Bind(R.id.tv_tot_exp_value)
    TextView mTvTotalWorkExperienceValue;
    @Bind(R.id.tv_tot_language_value)
    TextView mTvLanguageValue;
    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;
    @Bind(R.id.tv_professional_edit_basic_details)
    TextView mTvProfessionalEditBasicDetails;

    public ProfileProfessionalBasicDetailsHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {
        this.dataItem = myProfileView;
        mTvProfessionalEditBasicDetails.setOnClickListener(this);
        mTv_profile_basic_details.setText(AppConstants.USER_PROFILE);

        if (null != dataItem) {
            UserDetails userDetails = dataItem.getUserDetails();
            if (StringUtil.isNotNullOrEmptyString(userDetails.getJobTag())) {
                mTvCurrentStatusValue.setText(userDetails.getJobTag());
            }
            if (StringUtil.isNotNullOrEmptyString(userDetails.getSector())) {
                mTvSectorValue.setText(userDetails.getSector());
            }
            if (StringUtil.isNotNullOrEmptyString(String.valueOf(userDetails.getTotalExp()))) {
                mTvTotalWorkExperienceValue.setText(String.valueOf(userDetails.getTotalExp()));
            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getUserDetails().getDepartment())) {
                // mTvLanguageValue.setText(dataItem.getUserDetails().);TODO:need to check about language with Sumit(server)
            }

        }

        }
    @Override
    public void viewRecycled() {


    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.tv_professional_edit_basic_details:

                viewInterface.handleOnClick(this.dataItem,mTvProfessionalEditBasicDetails);

                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }

    }


}
