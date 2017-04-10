package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.GoodAtSkill;
import appliedlife.pvtltd.SHEROES.models.entities.profile.MyProfileView;
import appliedlife.pvtltd.SHEROES.models.entities.profile.OpportunityType;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by priyanka on 15-02-2017.
 */

public class ProfileGoodAtHolder extends BaseViewHolder<MyProfileView> {
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
    @Bind(R.id.tv_add_good_at)
    TextView mTv_add_good_at;
    BaseHolderInterface viewInterface;
    private MyProfileView dataItem;


    public ProfileGoodAtHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(MyProfileView myProfileView, Context context, int position) {
        this.dataItem = myProfileView;
        mTv_add_good_at.setOnClickListener(this);
        mTv_interesting_number.setText(dataItem.getType());
        List<GoodAtSkill> goodAtSkill=this.dataItem.getGoodAtSkill();
        if(null !=goodAtSkill) {
            if (StringUtil.isNotEmptyCollection(goodAtSkill)) {
                if(goodAtSkill.size()>0) {
                    if (StringUtil.isNotNullOrEmptyString(goodAtSkill.get(0).getName())) {
                        mTv_interesting_text1.setVisibility(View.VISIBLE);
                        mTv_interesting_text1.setText(goodAtSkill.get(0).getName());
                    }
                }
                if(goodAtSkill.size()>1) {
                    if (StringUtil.isNotNullOrEmptyString(goodAtSkill.get(1).getName())) {
                        mTv_interesting_text2.setVisibility(View.VISIBLE);
                        mTv_interesting_text2.setText(goodAtSkill.get(1).getName());
                    }
                }
                if(goodAtSkill.size()>2) {
                    if (StringUtil.isNotNullOrEmptyString(goodAtSkill.get(2).getName())) {
                        mTv_interesting_text3.setVisibility(View.VISIBLE);
                        mTv_interesting_text3.setText(goodAtSkill.get(2).getName());
                    }
                }
                if(goodAtSkill.size()>3) {
                    if (StringUtil.isNotNullOrEmptyString(goodAtSkill.get(3).getName())) {
                        mTv_interesting_text4.setVisibility(View.VISIBLE);
                        mTv_interesting_text4.setText(goodAtSkill.get(3).getName());
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

            case R.id.tv_add_good_at:

                viewInterface.handleOnClick(this.dataItem,mTv_add_good_at);
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + view.getId());
        }




    }
}
