package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.CanHelpIn;
import appliedlife.pvtltd.SHEROES.models.entities.profile.InterestType;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by sheroes on 09/04/17.
 */

public class CanHelpInHolder extends BaseViewHolder<MyProfileView> {

    @Bind(R.id.tv_can_help_number)
    TextView tvCanHelpNumber;
    @Bind(R.id.tv_can_help_text1)
    TextView mTv_interesting_text1;
    @Bind(R.id.tv_can_help_text2)
    TextView mTv_interesting_text2;
    @Bind(R.id.tv_add_can_help)
    TextView tvCanHelpAdd;


    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;


    public CanHelpInHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {
        this.dataItem = myProfileView;

        tvCanHelpAdd.setOnClickListener(this);
        tvCanHelpNumber.setText(dataItem.getType());
        List<CanHelpIn> canHelpIns=this.dataItem.getCanHelpIn();


        if(null !=dataItem.getCanHelpIn() && dataItem.getCanHelpIn().size() >0) {
            if (StringUtil.isNotNullOrEmptyString(canHelpIns.get(0).getName())) {
                mTv_interesting_text1.setVisibility(View.VISIBLE);
                mTv_interesting_text1.setText(canHelpIns.get(0).getName());

            }
            if(StringUtil.isNotNullOrEmptyString(canHelpIns.get(1).getName()) && dataItem.getCanHelpIn().size() >0)
            {
                mTv_interesting_text2.setVisibility(View.VISIBLE);
                mTv_interesting_text2.setText(canHelpIns.get(1).getName());

            }
        }
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_add_can_help:

                viewInterface.handleOnClick(this.dataItem,tvCanHelpAdd);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }

    }
}

