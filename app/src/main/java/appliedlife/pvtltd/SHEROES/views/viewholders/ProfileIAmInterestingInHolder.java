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

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by priyanka on 19/02/17.
 */

public class ProfileIAmInterestingInHolder extends BaseViewHolder<ProfilePersonalViewList> {

    @Bind(R.id.tv_interesting_number)
    TextView mTv_interesting_number;
    @Bind(R.id.tv_interesting_text1)
    TextView mTv_interesting_text1;
    @Bind(R.id.tv_interesting_text2)
    TextView mTv_interesting_text2;
    @Bind(R.id.tv_interesting_text3)
    TextView mTv_interesting_text3;
    @Bind(R.id.tv_interesting_text4)
    TextView mTv_interesting_text4;
    @Bind(R.id.tv_add_interest_details)
     TextView mTv_add_interest_details;


    BaseHolderInterface viewInterface;
    private ProfilePersonalViewList dataItem;


    public ProfileIAmInterestingInHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public ProfileIAmInterestingInHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }



    @Override
    public void bindData(ProfilePersonalViewList obj, Context context, int position) {
        this.dataItem = obj;
        mTv_add_interest_details.setOnClickListener(this);
        mTv_interesting_number.setText(dataItem.getTag());
        mTv_interesting_text1.setText(dataItem.getItem1());
        mTv_interesting_text2.setText(dataItem.getItem2());
        mTv_interesting_text3.setText(dataItem.getItem3());
        mTv_interesting_text4.setText(dataItem.getItem4());
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = Integer.parseInt(dataItem.getId());

        switch (view.getId()) {

            case R.id.tv_add_interest_details:

                viewInterface.handleOnClick(this.dataItem,mTv_add_interest_details);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }

    }
    }

