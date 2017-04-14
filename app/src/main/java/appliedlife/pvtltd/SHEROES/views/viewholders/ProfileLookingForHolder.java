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
import appliedlife.pvtltd.SHEROES.models.entities.profile.InterestType;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.OpportunityType;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by priyanka on 17/02/17.
 */

public class ProfileLookingForHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.tv_lookinfor_number)
    TextView mTv_lookinfor_number;
    @Bind(R.id.tv_lookingfor_text1)
    TextView mTv_lookingfor_text1;
    @Bind(R.id.tv_lookingfor_text2)
    TextView mTv_lookingfor_text2;
    @Bind(R.id.tv_looking_more)
    TextView mTv_looking_more;
    @Bind(R.id.tv_looking_for)
    TextView mTv_looking_for;
    BaseHolderInterface viewInterface;

    private MyProfileView dataItem;


    public ProfileLookingForHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {

        this.dataItem = myProfileView;

        mTv_looking_for.setOnClickListener(this);
        mTv_lookinfor_number.setText(dataItem.getType());
        List<OpportunityType> opportunityType=this.dataItem.getOpportunityType();

        if(null !=opportunityType) {
            if (StringUtil.isNotEmptyCollection(opportunityType)) {
                if(opportunityType.size()>0) {
                    if (StringUtil.isNotNullOrEmptyString(opportunityType.get(0).getName())) {
                        mTv_lookingfor_text1.setVisibility(View.VISIBLE);
                        mTv_lookingfor_text1.setText(opportunityType.get(0).getName());
                    }
                }
                if(opportunityType.size()>1) {
                    if (StringUtil.isNotNullOrEmptyString(opportunityType.get(1).getName())) {
                        mTv_lookingfor_text2.setVisibility(View.VISIBLE);
                        mTv_lookingfor_text2.setText(opportunityType.get(1).getName());
                    }
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
            case R.id.tv_looking_for: {
                viewInterface.handleOnClick(this.dataItem, mTv_looking_for);
                break;
            }
            default: {
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
                break;
            }

        }
    }
}
