package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.MembersList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 03-02-2017.
 */

public class MemberHolder extends BaseViewHolder<MembersList> {
    @Bind(R.id.tv_admin)
    TextView tvAdmin;
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
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
    }

    @Override
    public void bindData(MembersList obj, Context context, int position) {
        this.dataItem = obj;
        tv_member_city.setText(dataItem.getCommunityUserCityName());
        tv_member_name.setText(dataItem.getCommunityUserFirstName());
        String images = dataItem.getCommunityUserPhotoUrlPath();
        background.setCircularImage(true);
        background.bindImage(images);
        dataItem.setPosition(position);
        if (dataItem.getIsOwner() && !dataItem.getTypeS().equalsIgnoreCase(AppConstants.OWNER_SUB_TYPE)) {
            tv_member_cross.setVisibility(View.VISIBLE);
            tvAdmin.setVisibility(View.GONE);
        } else if(dataItem.getIsOwner()) {
            tv_member_cross.setVisibility(View.GONE);
            tvAdmin.setVisibility(View.VISIBLE);
        }else {
            tv_member_cross.setVisibility(View.GONE);
            tvAdmin.setVisibility(View.GONE);
        }
        if(dataItem.getTypeS().equalsIgnoreCase(AppConstants.OWNER_SUB_TYPE))
        {
            tvAdmin.setVisibility(View.GONE);
        }

    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_member_cross)
    public void onRemoveMemberClick() {
        viewInterface.handleOnClick(dataItem, tv_member_cross);
    }

    @Override
    public void onClick(View view) {

    }

}
