package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityList;
import appliedlife.pvtltd.SHEROES.models.entities.community.Docs;
import appliedlife.pvtltd.SHEROES.models.entities.community.SelectedCommunityResponse;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners.EditNameDialogListener;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ajit Kumar on 22-01-2017.
 */

public class SelectDilogHolder extends BaseViewHolder<Docs> {
    @Bind(R.id.textView1)
    TextView tvCity;
    @Bind(R.id.img1)
    CircleImageView background;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;
    private int position;
    Docs docs;

    public SelectDilogHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    public SelectDilogHolder(View itemView, EditNameDialogListener baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(Docs obj, Context context, int position) {
        docs=obj;
        tvCity.setText(docs.getTitle());
        tvCity.setOnClickListener(this);
        String images = docs.getLogo();

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
        map.put("collection id",docs.getId());
//    map.put("collection type",dataItem.getType());
        viewInterface.handleOnClick(this.docs,view);
        //createCommunityViewInterface.closeDialog("communityDialog");


    }
}
