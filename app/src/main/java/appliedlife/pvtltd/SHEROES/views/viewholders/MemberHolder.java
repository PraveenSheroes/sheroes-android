package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class MemberHolder extends BaseViewHolder<MembersList> {
    @Bind(R.id.tv_member_city)
    TextView tv_member_city;
    @Bind(R.id.tv_member_name)
    TextView tv_member_name;
    @Bind(R.id.img1)
    CircleImageView background;
    @Bind(R.id.tv_member_cross)
    TextView tv_member_cross;
    BaseHolderInterface viewInterface;
    private MembersList dataItem;
    private int position;


    public MemberHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @Override
    public void bindData(MembersList obj, Context context, int position) {
        this.dataItem = obj;
        // itemView.setOnClickListener(this);
        tv_member_cross.setOnClickListener(this);

        tv_member_city.setText(dataItem.getCommunityUserCityName());
        tv_member_name.setText(dataItem.getCommunityUserFirstName());
        String images = dataItem.getCommunityUserPhotoUrlPath();
        background.setCircularImage(true);
        background.bindImage(images);
        dataItem.setPosition(position);
        if(dataItem.getIsOwner())
        {
            tv_member_cross.setVisibility(View.VISIBLE);
        }
        else {
            tv_member_cross.setVisibility(View.GONE);
        }

    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        //   map.put("collection name",dataItem.getTitle());
        map.put("collection id",dataItem.getId());
//    map.put("collection type",dataItem.getType());
        viewInterface.handleOnClick(this.dataItem,view);
        //createCommunityViewInterface.closeDialog("communityDialog");


    }

}
