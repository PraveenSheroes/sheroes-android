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

public class ProfileEducationHolder extends BaseViewHolder<ProfileViewList> {
    @Bind(R.id.tv_job_language_number)
    TextView mTv_job_language_number;
    @Bind(R.id.tv_degree1)
    TextView mTv_degree1;
    @Bind(R.id.tv_date1)
    TextView mTv_date1;
    @Bind(R.id.tv_degree11)
    TextView mTv_degree11;
    @Bind(R.id.tv_degree2)
    TextView mTv_degree2;
    @Bind(R.id.tv_degree21)
    TextView mTv_degree21;
    @Bind(R.id.tv_date2)
    TextView mTv_date2;
    BaseHolderInterface viewInterface;
    private ProfileViewList dataItem;


    public ProfileEducationHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public ProfileEducationHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }



    @Override
    public void bindData(ProfileViewList obj, Context context, int position) {
        this.dataItem = obj;
        itemView.setOnClickListener(this);

        mTv_job_language_number.setText(dataItem.getTag());
        mTv_degree1.setText(dataItem.getItem1());
        mTv_date1.setText(dataItem.getItem3());
        mTv_degree11.setText(dataItem.getItem2());
        mTv_degree2.setText(dataItem.getItem4());
        mTv_degree21.setText(dataItem.getItem5());
        mTv_date2.setText(dataItem.getItem6());

    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

       // viewInterface.handleOnClick(this.dataItem,view);


    }
}
