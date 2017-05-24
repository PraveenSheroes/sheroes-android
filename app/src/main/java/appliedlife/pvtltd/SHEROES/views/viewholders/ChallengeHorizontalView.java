package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.communities.ChallengeDataItem;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
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
    @Bind(R.id.li_suggested_community_see_more_like)
    LinearLayout liSuggestedCommunitySeeMoreLike;
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
        List<ChallengeDataItem> suggestedPost=new ArrayList<>();
        ChallengeDataItem challengeDataItem1 =new ChallengeDataItem();
        challengeDataItem1.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14846520381484652038.png");
        challengeDataItem1.setPostName("First name");
        ChallengeDataItem challengeDataItem2 =new ChallengeDataItem();
        challengeDataItem2.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14845475641484547564.png");
        challengeDataItem2.setPostName("Second name");
        ChallengeDataItem challengeDataItem3 =new ChallengeDataItem();
        challengeDataItem3.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14847278181484727818.png");
        challengeDataItem3.setPostName("Third name");
        ChallengeDataItem challengeDataItem4 =new ChallengeDataItem();
        challengeDataItem4.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14842893151484289315.png");
        suggestedPost.add(challengeDataItem1);
        suggestedPost.add(challengeDataItem2);
        suggestedPost.add(challengeDataItem3);
        suggestedPost.add(challengeDataItem4);
        mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(context,(HomeActivity) context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setSheroesGenericListData(suggestedPost);
    }


    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
    }

}
