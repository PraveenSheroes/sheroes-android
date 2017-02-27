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
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by priyanka on 17/02/17.
 */

public class ProfilePersonalBasicDetailsHolder extends BaseViewHolder<ProfilePersonalViewList> {

    @Bind(R.id.tv_date_of_birth)
    TextView MTv_date_of_birth;
    @Bind(R.id.tv_date_of_birth_value)
    TextView mTv_date_of_birth_value;
    @Bind(R.id.tv_current_location)
    TextView mTv_current_location;
    @Bind(R.id.tv_current_location_value)
    TextView mTv_current_location_value;
    @Bind(R.id.tv_home_town)
    TextView mTv_home_town;
    @Bind(R.id.tv_home_town_value)
    TextView mTv_home_town_value;
    @Bind(R.id.tv_email)
    TextView mTtv_email;
    @Bind(R.id.tv_email_value)
    TextView mTv_email_value;
    @Bind(R.id.tv_contact_number)
    TextView mTv_contact_number;
    @Bind(R.id.tv_contact_number_value)
    TextView mTv_contact_number_value;
    @Bind(R.id.tv_status)
    TextView mTv_status;
    @Bind(R.id.tv_status_value)
    TextView mTv_status_value;
    @Bind(R.id.tv_child_no)
    TextView mTv_child_no;
    @Bind(R.id.tv_child_value)
    TextView mTv_child_value;
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

    private ProfilePersonalViewList dataItem;


    public ProfilePersonalBasicDetailsHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public ProfilePersonalBasicDetailsHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);

    }

    @Override
    public void bindData(ProfilePersonalViewList obj, Context context, int position) {

        this.dataItem = obj;
        itemView.setOnClickListener(this);
        MTv_date_of_birth.setVisibility(View.VISIBLE);
        mTv_date_of_birth_value.setVisibility(View.VISIBLE);
        mTv_current_location.setVisibility(View.VISIBLE);
        mTv_current_location_value.setVisibility(View.VISIBLE);
        mTv_home_town.setVisibility(View.VISIBLE);
        mTv_home_town_value.setVisibility(View.VISIBLE);
        mTtv_email.setVisibility(View.VISIBLE);
        mTv_email_value.setVisibility(View.VISIBLE);
        mTv_contact_number.setVisibility(View.VISIBLE);
        mTv_contact_number_value.setVisibility(View.VISIBLE);
        mTv_status.setVisibility(View.VISIBLE);
        mTv_status_value.setVisibility(View.VISIBLE);
        mTv_child_no.setVisibility(View.VISIBLE);
        mTv_child_value.setVisibility(View.VISIBLE);
        mTv_profile_basic_details.setText(dataItem.getTag());
        MTv_date_of_birth.setText(dataItem.getItem1());
        mTv_date_of_birth_value.setText(dataItem.getItem2());
        mTv_current_location.setText(dataItem.getItem3());
        mTv_current_location_value.setText(dataItem.getItem4());
        mTv_home_town.setText(dataItem.getItem5());
        mTv_home_town_value.setText(dataItem.getItem6());
        mTtv_email.setText(dataItem.getItem7());
        mTv_email_value.setText(dataItem.getItem8());
        mTv_contact_number.setText(dataItem.getItem9());
        mTv_contact_number_value.setText(dataItem.getItem10());
        mTv_status.setText(dataItem.getItem11());
        mTv_status_value.setText(dataItem.getItem12());
        mTv_child_no.setText(dataItem.getItem13());
        mTv_child_value.setText(dataItem.getItem14());
        mTv_current_status.setVisibility(View.GONE);
        mTv_current_status_value.setVisibility(View.GONE);
        mTv_sector.setVisibility(View.GONE);
        mTv_sector_value.setVisibility(View.GONE);
        mtv_tot_exp_value.setVisibility(View.GONE);
        mTv_language.setVisibility(View.GONE);
        mTv_tot_language_value.setVisibility(View.GONE);


    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        //viewInterface.handleOnClick(this.dataItem,view);



    }
}
