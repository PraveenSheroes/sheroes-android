package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileHorList;
import appliedlife.pvtltd.SHEROES.models.entities.profile.ProfileViewList;
import appliedlife.pvtltd.SHEROES.views.activities.ProfileActicity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 16-02-2017.
 */

public class ProfileHorizontalViewHolder  extends BaseViewHolder<ProfileViewList> {
    @Bind(R.id.rv_profile_horizontal_list)
    RecyclerView mRecyclerView;
    GenericRecyclerViewAdapter mAdapter;
    List<ProfileHorList> profileList = new ArrayList<ProfileHorList>();
    LinearLayoutManager mLayoutManager;
    BaseHolderInterface viewInterface;
    private ProfileViewList dataItem;


    public ProfileHorizontalViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public ProfileHorizontalViewHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }



    @Override
    public void bindData(ProfileViewList obj, Context context, int position) {
        this.dataItem = obj;
        itemView.setOnClickListener(this);
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(context,(ProfileActicity) context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        setListValue();
        mAdapter.setSheroesGenericListData(profileList);

    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

        viewInterface.handleOnClick(this.dataItem,view);
    }
    private void setListValue() {


        ProfileHorList listProfile3=new ProfileHorList();
        listProfile3.setId("3");
        listProfile3.setTag("Are you willing to travel to client side location?");
        listProfile3.setCity("Yes, My Country");
        profileList.add(listProfile3);
        ProfileHorList listProfile4=new ProfileHorList();
        listProfile4.setId("4");
        listProfile4.setTag("City which would like to work?");
        listProfile4.setCity("Chandigarh, Punjab, India");
        profileList.add(listProfile4);



    }

}
