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
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.UserDetails;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView.TAG;

/**
 * Created by sheroes on 17/02/17.
 */

public class ProfileAboutMeHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.tv_lookinfor_number)
    TextView mTvAboutMTittle;
    @Bind(R.id.tv_lookingfor_text1)
    TextView mTv_lookingfor_text1;
    @Bind(R.id.tv_lookingfor_text2)
    TextView mTv_lookingfor_text2;
    @Bind(R.id.tv_looking_more)
    TextView mTv_looking_more;
    @Bind(R.id.tv_add_about_me)
    TextView mTv_add_about_me;
    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;


    public ProfileAboutMeHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {

        this.dataItem = myProfileView;
        mTv_add_about_me.setOnClickListener(this);
        mTvAboutMTittle.setText(dataItem.getType());

        UserDetails userDetails=dataItem.getUserDetails();
        if(null !=userDetails) {
            if(StringUtil.isNotNullOrEmptyString(userDetails.getUserSummary())) {

                mTv_lookingfor_text2.setVisibility(View.VISIBLE);
                mTv_lookingfor_text2.setText(userDetails.getUserSummary());

            }
        }
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_add_about_me:
                viewInterface.handleOnClick(this.dataItem, mTv_add_about_me);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }


    }
}
