package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView.TAG;

/**
 * Created by sheroes on 17/02/17.
 */

public class ProfileAboutMeHolder extends BaseViewHolder<ProfilePersonalViewList> {
    @Bind(R.id.tv_lookinfor_number)
    TextView mTv_lookinfor_number;
    @Bind(R.id.tv_lookingfor_text1)
    TextView mTv_lookingfor_text1;
    @Bind(R.id.tv_lookingfor_text2)
    TextView mTv_lookingfor_text2;
    @Bind(R.id.tv_looking_more)
    TextView mTv_looking_more;
    @Bind(R.id.tv_add_about_me)
    TextView mTv_add_about_me;

    BaseHolderInterface viewInterface;
    private ProfilePersonalViewList dataItem;


    public ProfileAboutMeHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public ProfileAboutMeHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(ProfilePersonalViewList obj, Context context, int position) {

        this.dataItem = obj;
        mTv_add_about_me.setOnClickListener(this);
        mTv_lookinfor_number.setText(dataItem.getTag());
        mTv_lookingfor_text2.setText(dataItem.getItem1());
        mTv_lookingfor_text1.setVisibility(View.GONE);

    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_add_about_me:
                viewInterface.handleOnClick(this.dataItem,mTv_add_about_me);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }



    }
}