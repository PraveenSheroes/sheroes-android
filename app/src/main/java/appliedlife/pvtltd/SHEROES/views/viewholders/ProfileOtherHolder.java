package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

import static appliedlife.pvtltd.SHEROES.views.cutomeviews.RoundedImageView.TAG;

/**
 * Created by SHEROES-TECH on 16-02-2017.
 */

public class ProfileOtherHolder extends BaseViewHolder<ProfileViewList> {
    @Bind(R.id.tv_edit_other_textline)
    TextView mTv_edit_other_textline;

    @Bind(R.id.tv_other_view)
    TextView mTv_other_view;



    BaseHolderInterface viewInterface;
    private ProfileViewList dataItem;


    public ProfileOtherHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }



    @Override
    public void bindData(ProfileViewList obj, Context context, int position) {
        this.dataItem = obj;
        mTv_edit_other_textline.setOnClickListener(this);
        mTv_other_view.setText(dataItem.getTag());



    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_edit_other_textline:

                viewInterface.handleOnClick(this.dataItem,mTv_edit_other_textline);
                break;

            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }


    }
}
