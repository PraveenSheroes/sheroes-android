package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import butterknife.OnClick;

import static appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView.TAG;

/**
 * Created by sheroes on 17/02/17.
 */

public class ProfileAboutMeHolder extends BaseViewHolder<MyProfileView> {
    @Bind(R.id.tv_lookinfor_number)
    TextView mTvAboutMTittle;
    @Bind(R.id.tv_lookingfor_text1)
    TextView mTv_lookingfor_text1;
    @Bind(R.id.tv_about_me)
    TextView mAboutMeTxt;
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

                mAboutMeTxt.setVisibility(View.VISIBLE);
                mAboutMeTxt.setText(userDetails.getUserSummary());

            }
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_looking_more)
    public void onClickViewMore() {
        Toast.makeText(SheroesApplication.mContext, "Under development", Toast.LENGTH_SHORT).show();
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
