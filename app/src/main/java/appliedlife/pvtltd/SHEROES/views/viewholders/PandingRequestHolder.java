package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.PandingMember;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by SHEROES-TECH on 31-03-2017.
 */

public class PandingRequestHolder extends BaseViewHolder<PandingMember> {
    @Bind(R.id.tv_panding_member_city)
    TextView tv_member_city;
    @Bind(R.id.tv_panding_member_name)
    TextView tv_member_name;
    @Bind(R.id.ci_panding_memberimg1)
    CircleImageView background;
    @Bind(R.id.tv_panding_member_cross)
    TextView tv_member_cross;
    @Bind(R.id.tv_panding_member_check)
    TextView tv_member_check;
    @Bind(R.id.tv_panding_member_region)
    TextView tvPandingMemberRegion;
    BaseHolderInterface viewInterface;
    private PandingMember dataItem;
    private int position;

public PandingRequestHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        }


@Override
public void bindData(PandingMember obj, Context context, int position) {
    this.dataItem = obj;
    // itemView.setOnClickListener(this);
    tv_member_cross.setOnClickListener(this);
    tv_member_check.setOnClickListener(this);
    if(null !=dataItem.getResoneToJoinS()) {
        tvPandingMemberRegion.setText(dataItem.getResoneToJoinS());
    }
    if(null !=dataItem.getCommunityUserCityName()) {

        tv_member_city.setText(dataItem.getCommunityUserCityName());
    }
    if(null !=dataItem.getCommunityUserLastName()) {
        tv_member_name.setText(dataItem.getCommunityUserFirstName() + " " + dataItem.getCommunityUserLastName());
    }
   // tvPandingMemberRegion.setText(dataItem.get);
    String images = dataItem.getCommunityUserPhotoUrlPath();
    dataItem.setPosition(position);
    background.setCircularImage(true);
    background.bindImage(images);
}

@Override
public void viewRecycled() {

        }


@Override
public void onClick(View view) {
    switch (view.getId()) {

        case R.id.tv_panding_member_cross:
            HashMap<String,Object> map = new HashMap<String,Object>();
            //   map.put("collection name",dataItem.getTitle());
            map.put("collection id",dataItem.getId());
            // map.put("collection type",dataItem.getType());
            viewInterface.handleOnClick(this.dataItem,view);
            break;
        case R.id.tv_panding_member_check:
            HashMap<String,Object> map1 = new HashMap<String,Object>();
            //   map.put("collection name",dataItem.getTitle());
            map1.put("collection id",dataItem.getId());
            // map.put("collection type",dataItem.getType());
            viewInterface.handleOnClick(this.dataItem,view);
            break;
    }

        //createCommunityViewInterface.closeDialog("communityDialog");


        }

        }
