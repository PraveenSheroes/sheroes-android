package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.jobs.JobLocationList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 13-02-2017.
 */

public class JobLocationHolder extends BaseViewHolder<JobLocationList> {
    @Bind(R.id.tv_location_nm)
    TextView tvCity;
    @Bind(R.id.tv_check)
    TextView tv_owner;

    BaseHolderInterface viewInterface;
    private JobLocationList dataItem;
    private int position;

    public JobLocationHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public JobLocationHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(JobLocationList obj, Context context, int position) {
        this.dataItem = obj;
        itemView.setOnClickListener(this);

        tvCity.setText(dataItem.getName());
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        tv_owner.setBackgroundResource(R.drawable.ic_chek_box);

        HashMap<String,Object> map = new HashMap<String,Object>();
        //   map.put("collection name",dataItem.getTitle());
        map.put("collection id",dataItem.getId());
//    map.put("collection type",dataItem.getType());
        viewInterface.handleOnClick(this.dataItem,view);



    }
}
