package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by priyanka on 16-02-2017.
 */

public class ProfileBasicDetailsHolder extends BaseViewHolder<ProfileViewList> {
    @Bind(R.id.tv_profile_basic_details)
    TextView mTv_profile_basic_details;
    @Bind(R.id.tv_current_status)
    TextView mTv_current_status;
    @Bind(R.id.tv_current_status_value)
    TextView mTv_current_status_value;
    @Bind(R.id.tv_sector)
    TextView mTv_sector;
    @Bind(R.id.tv_sector_value)
    TextView mTv_sector_value;
    @Bind(R.id.tv_total_work_experience)
    TextView mTv_total_work_experience;
    @Bind(R.id.tv_tot_exp_value)
    TextView mtv_tot_exp_value;
    @Bind(R.id.tv_language)
    TextView mTv_language;
    @Bind(R.id.tv_tot_language_value)
    TextView mTv_tot_language_value;
    BaseHolderInterface viewInterface;
    private ProfileViewList dataItem;

    public ProfileBasicDetailsHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public ProfileBasicDetailsHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @Override
    public void bindData(ProfileViewList obj, Context context, int position) {
        this.dataItem = obj;
        itemView.setOnClickListener(this);

        mTv_profile_basic_details.setText(dataItem.getTag());
        mTv_current_status.setText(dataItem.getItem1());
        mTv_current_status_value.setText(dataItem.getItem2());
        mTv_sector.setText(dataItem.getItem3());
        mTv_sector_value.setText(dataItem.getItem4());
        mTv_total_work_experience.setText(dataItem.getItem5());
        mtv_tot_exp_value.setText(dataItem.getItem6());
        mTv_language.setText(dataItem.getItem7());
        mTv_tot_language_value.setText(dataItem.getItem8());

    }
    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

      //  viewInterface.handleOnClick(this.dataItem,view);


    }

}
