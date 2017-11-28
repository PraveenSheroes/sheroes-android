package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.challenge.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 02-02-2017.
 */

public class ChallengeHorizontalView extends BaseViewHolder<FeedDetail> {
    private final String TAG = LogUtils.makeLogTag(ChallengeHorizontalView.class);
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.rv_suggested_list)
    RecyclerView mRecyclerView;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;


    public ChallengeHorizontalView(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(FeedDetail item, final Context context, int position) {
        this.dataItem = item;
        dataItem.setItemPosition(position);
        if (null != dataItem && StringUtil.isNotEmptyCollection(dataItem.getChallengeDataItems())) {
            List<ChallengeDataItem> challengeDataItems = dataItem.getChallengeDataItems();
            int challengePosition = 0;
            // TODO: ujjwal
       /*     if (dataItem.getCommunityId() != AppConstants.NO_REACTION_CONSTANT) {
                for (ChallengeDataItem challengeDataItem : challengeDataItems) {
                    if (dataItem.getCommunityId() == challengeDataItem.getChallengeId()) {
                        break;
                    }
                    challengePosition++;
                }
            }else
            {
                challengePosition=dataItem.getNoOfMembers();
            }*/
            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new GenericRecyclerViewAdapter(context, (HomeActivity) context);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setSheroesGenericListData(challengeDataItems);
            mLayoutManager.scrollToPosition(challengePosition);
        }

    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }

}
