package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.RequestedList;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 08-02-2017.
 */

public class RequestedHolder  extends BaseViewHolder<RequestedList> {
    @Bind(R.id.textView1)
    TextView tvCity;
    @Bind(R.id.tv_owner)
    TextView tv_owner;
    @Bind(R.id.img1)
    CircleImageView background;
    BaseHolderInterface viewInterface;
    private RequestedList dataItem;
    private int position;

    public RequestedHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(RequestedList obj, Context context, int position) {
        this.dataItem = obj;
       // itemView.setOnClickListener(this);

        tvCity.setText(dataItem.getName());
        int pos=position+1;
        tv_owner.setText("Admin"+pos);
        String images = dataItem.getBackground();

        background.setCircularImage(true);
        background.bindImage(images);
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
