package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.FilterList;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 10-02-2017.
 */

public class FilterHolder extends BaseViewHolder<FilterList> {
    @Bind(R.id.tv_filter_nm)
    TextView tvCity;
    @Bind(R.id.tv_filter_desc)
    TextView tv_owner;
    @Bind(R.id.tv_work_from_home)
    TextView mTv_work_from_home;
    @Bind(R.id.tv_freelance_work)
    TextView mTv_freelance_work;
    @Bind(R.id.tv_corporate_job)
    TextView mTv_corporate_job;
    @Bind(R.id.tv_internship_volunteer)
    TextView mTv_internship_volunteer;
    @Bind(R.id.tv_business_opertunities)
    TextView mTv_business_opertunities;

    @Bind(R.id.tv_full_time_job)
    TextView mTv_full_time;
    @Bind(R.id.tv_part_time_job)
    TextView mTv_part_time;
    @Bind(R.id.lnr2)
    LinearLayout mLnrOppertunitiesType;

    @Bind(R.id.rl_corporate_job)
    RelativeLayout mRl_corporate_job;

    BaseHolderInterface viewInterface;
    private FilterList dataItem;
    private int position;

    public FilterHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public FilterHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }


    @OnClick(R.id.tv_work_from_home)
    public void workFromHomeClick()
    {
        mTv_work_from_home.setBackgroundResource(R.drawable.unselected_oppertunity_type);
        mTv_work_from_home.setTextColor(Color.parseColor("#3949ab"));
    }
    @OnClick(R.id.tv_freelance_work)
    public void freelanceClick()
    {
        mTv_freelance_work.setBackgroundResource(R.drawable.unselected_oppertunity_type);
        mTv_freelance_work.setTextColor(Color.parseColor("#3949ab"));
    }
    @OnClick(R.id.tv_corporate_job)
    public void corporatejobClick()
    {
        mRl_corporate_job.setVisibility(View.VISIBLE);

        mTv_corporate_job.setBackgroundResource(R.drawable.unselected_oppertunity_type);
        mTv_corporate_job.setTextColor(Color.parseColor("#3949ab"));
    }
    @OnClick(R.id.tv_internship_volunteer)
    public void internshipClick()
    {
        mTv_internship_volunteer.setBackgroundResource(R.drawable.unselected_oppertunity_type);
        mTv_internship_volunteer.setTextColor(Color.parseColor("#3949ab"));
    }
    @OnClick(R.id.tv_business_opertunities)
    public void businessOpertunitiesClick()
    {
        mTv_business_opertunities.setBackgroundResource(R.drawable.unselected_oppertunity_type);
        mTv_business_opertunities.setTextColor(Color.parseColor("#3949ab"));
    }
    @OnClick(R.id.tv_full_time_job)
    public void onFullTimeJobClick()
    {
        mTv_full_time.setBackgroundResource(R.drawable.unselected_oppertunity_type);
        mTv_full_time.setTextColor(Color.parseColor("#3949ab"));
    }
    @OnClick(R.id.tv_part_time_job)
    public void onPartTimeJobClick()
    {
        mTv_part_time.setBackgroundResource(R.drawable.unselected_oppertunity_type);
        mTv_part_time.setTextColor(Color.parseColor("#3949ab"));
    }
    @Override
    public void bindData(FilterList obj, Context context, int position) {
        this.dataItem = obj;
        itemView.setOnClickListener(this);
        mLnrOppertunitiesType.setVisibility(View.GONE);
        mRl_corporate_job.setVisibility(View.GONE);
        tvCity.setText(dataItem.getName());
        tv_owner.setText(dataItem.getDescription());

    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = Integer.parseInt(dataItem.getId());
        switch (id) {
            case 1:
                mLnrOppertunitiesType.setVisibility(View.VISIBLE);
                break;
            default:
                // LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        //   map.put("collection name",dataItem.getTitle());
        map.put("collection id",dataItem.getId());
//    map.put("collection type",dataItem.getType());
        viewInterface.handleOnClick(this.dataItem,view);


    }
}
