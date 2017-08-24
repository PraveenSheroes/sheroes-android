package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.community.CommunityPostResponse;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ajit Kumar on 22-01-2017.
 */

public class SelectDilogHolder extends BaseViewHolder<CommunityPostResponse> {
    @Bind(R.id.rl_community_name)
    RelativeLayout rlCommunityname;
    @Bind(R.id.textView1)
    TextView tvCity;
    @Bind(R.id.img1)
    CircleImageView background;
    BaseHolderInterface viewInterface;
    CommunityPostResponse communityPostResponse;

    public SelectDilogHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @Override
    public void bindData(CommunityPostResponse obj, Context context, int position) {
        communityPostResponse =obj;
        tvCity.setText(communityPostResponse.getTitle());
        String images = communityPostResponse.getLogo();
        if(communityPostResponse.isClosedCommunity()) {
            tvCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0);
        }else
        {
            tvCity.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
        }
        background.setCircularImage(true);
        background.bindImage(images);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }
    @OnClick(R.id.rl_community_name)
    public void onItemClick(View view) {
        viewInterface.handleOnClick(communityPostResponse,tvCity);
    }
    @OnClick(R.id.textView1)
    public void onTextViewClick(View view) {
        viewInterface.handleOnClick(communityPostResponse,tvCity);
    }
}
