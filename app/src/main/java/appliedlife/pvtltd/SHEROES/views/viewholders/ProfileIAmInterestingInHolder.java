package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.onboarding.LabelValue;
import appliedlife.pvtltd.SHEROES.models.entities.profile.InterestType;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfilePersonalViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by priyanka on 19/02/17.
 */

public class ProfileIAmInterestingInHolder extends BaseViewHolder<MyProfileView> {

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
    private MyProfileView dataItem;


    public ProfileIAmInterestingInHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {
        this.dataItem = myProfileView;

        mTv_add_interest_details.setOnClickListener(this);
        mTv_interesting_number.setText(dataItem.getType());


        List<InterestType> interests=this.dataItem.getInterestType();

        if(null !=interests){

            if (StringUtil.isNotEmptyCollection(interests)) {

                int i = 0;

                if(interests.size()>0) {
                    mTv_interesting_text1.setVisibility(View.VISIBLE);
                    if (StringUtil.isNotNullOrEmptyString(interests.get(0).getName())) {
                        mTv_interesting_text1.setText(interests.get(0).getName());
                    }
                }
                if(interests.size()>1) {
                    mTv_interesting_text2.setVisibility(View.VISIBLE);
                    if (StringUtil.isNotNullOrEmptyString(interests.get(1).getName())) {
                        mTv_interesting_text2.setText(interests.get(1).getName());
                    }
                }
                if(interests.size()>2) {
                    mTv_interesting_text3.setVisibility(View.VISIBLE);
                    if (StringUtil.isNotNullOrEmptyString(interests.get(2).getName()) ) {
                        mTv_interesting_text3.setText(interests.get(2).getName());
                    }
                }
                if(interests.size()>3) {
                    mTv_interesting_text4.setVisibility(View.VISIBLE);
                    if (StringUtil.isNotNullOrEmptyString(interests.get(3).getName())) {
                        mTv_interesting_text4.setText(interests.get(3).getName());
                    }
                }

                i++;

             }
        }


    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_more)
    public void onClickViewMore() {
        Toast.makeText(SheroesApplication.mContext, "Under development", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_interest_details: {
                viewInterface.handleOnClick(this.dataItem, mTv_add_interest_details);
                break;
            }
            default: {
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
                break;
            }
        }
    }
    }

