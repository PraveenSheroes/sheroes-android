package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 01-02-2017.
 */

public class CommunityCardDetailHeader extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(CommunityCardDetailHeader.class);
    @Bind(R.id.card_community_detail)
    CardView cardCommunityDetail;
    @Bind(R.id.tv_community_name)
    TextView tvCommunityName;
    @Bind(R.id.tv_community_related)
    TextView tvCommunityRelated;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;


    public CommunityCardDetailHeader(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
        this.viewInterface = baseHolderInterface;

    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        if (StringUtil.isNotNullOrEmptyString(dataItem.getNameOrTitle())) {
            tvCommunityName.setText(dataItem.getNameOrTitle());
        }
        if (StringUtil.isNotNullOrEmptyString(dataItem.getCommunityType())) {
            tvCommunityRelated.setText(dataItem.getCommunityType());
        }

    }

    @OnClick(R.id.card_community_detail)
    public void onBackClick()
    {
        viewInterface.handleOnClick(dataItem, cardCommunityDetail);
    }
    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {

    }

}
