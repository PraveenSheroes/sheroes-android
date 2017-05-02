package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView.TAG;

/**
 * Created by priyanka on 17/02/17.
 */

public class ProfilePersonalBasicDetailsHolder extends BaseViewHolder<MyProfileView> {

    @Bind(R.id.tv_current_location)
    TextView mTv_current_location;
    @Bind(R.id.tv_current_location_value)
    TextView mTv_current_location_value;
    @Bind(R.id.tv_email)
    TextView mTtv_email;
    @Bind(R.id.tv_email_value)
    TextView mTv_email_value;
    @Bind(R.id.tv_contact_number)
    TextView mTv_contact_number;
    @Bind(R.id.tv_contact_number_value)
    TextView mTv_contact_number_value;
    @Bind(R.id.tv_status)
    TextView mTv_status;
    @Bind(R.id.tv_status_value)
    TextView mTv_status_value;
    @Bind(R.id.tv_child_no)
    TextView mTv_child_no;
    @Bind(R.id.tv_child_value)
    TextView mTv_child_value;
    @Bind(R.id.tv_profile_basic_details)
    TextView mTv_profile_basic_details;
    @Bind(R.id.tv_edit_basic_details)
    TextView mTv_edit_basic_details;
    BaseHolderInterface viewInterface;

    private MyProfileView dataItem;


    public ProfilePersonalBasicDetailsHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }



    @Override
    public void bindData(MyProfileView profileView, Context context, int position) {

        this.dataItem = profileView;

        mTv_edit_basic_details.setOnClickListener(this);

        mTv_profile_basic_details.setText(dataItem.getType());


        if (null != dataItem) {

            if (StringUtil.isNotNullOrEmptyString(dataItem.getUserDetails().getCityMaster())) {
                mTv_current_location_value.setText(dataItem.getUserDetails().getCityMaster());
            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getUserDetails().getMobile())) {
                mTv_contact_number_value.setText(dataItem.getUserDetails().getMobile());
            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getUserDetails().getMaritalStatus())) {
                mTv_status_value.setText(dataItem.getUserDetails().getMaritalStatus());
            }
            if (StringUtil.isNotNullOrEmptyString("" + dataItem.getUserDetails().getNoOfChildren())) {

                mTv_child_value.setText(String.valueOf(dataItem.getUserDetails().getNoOfChildren()));

            }
            if (StringUtil.isNotNullOrEmptyString(dataItem.getUserDetails().getEmailid())) {
                mTv_email_value.setText(dataItem.getUserDetails().getEmailid());
            }

        }

    }

    @Override
    public void viewRecycled() {


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_edit_basic_details: {
                viewInterface.handleOnClick(this.dataItem, mTv_edit_basic_details);
                break;
            }
            default: {
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
                break;
            }
        }
    }
}
