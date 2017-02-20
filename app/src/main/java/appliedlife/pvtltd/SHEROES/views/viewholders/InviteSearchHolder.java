package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.ListOfInviteSearch;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class InviteSearchHolder extends BaseViewHolder<ListOfInviteSearch> {
    @Bind(R.id.tv_search_list_header_text)
    TextView tvCity;
    @Bind(R.id.tv_search_list_label_text)
    TextView tv_owner;
    @Bind(R.id.img1)
    CircleImageView background;
    @Bind(R.id.tv_add_invite)
    TextView tv_add_invite;
    BaseHolderInterface viewInterface;
    private ListOfInviteSearch dataItem;
    private int position;

    public InviteSearchHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public InviteSearchHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }




    @Override
    public void bindData(ListOfInviteSearch obj, Context context, int position) {
        this.dataItem = obj;
        tv_add_invite.setOnClickListener(this);
      //  tvCity.setText(dataItem.getFeedTitle().substring(0,10));
        int pos=position+1;
        tv_owner.setText("Admin"+pos);
     //   String images = dataItem.getFeedCircleIconUrl();
        background.setCircularImage(true);
     //   background.bindImage(images);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_add_invite:
                tv_add_invite.setBackgroundResource(R.drawable.selected_add_btn_shap);
                tv_add_invite.setTextColor((Color.parseColor("#FFFFFF")));
                break;
            default:
                LogUtils.error("", AppConstants.CASE_NOT_HANDLED + " " + " " + " " + id);


        }
        HashMap<String,Object> map = new HashMap<String,Object>();
        //   map.put("collection name",dataItem.getTitle());
    //    map.put("collection id",dataItem.getId());
//    map.put("collection type",dataItem.getType());


       // viewInterface.handleOnClick(this.dataItem,view);


        //createCommunityViewInterface.closeDialog("communityDialog");


    }
}
