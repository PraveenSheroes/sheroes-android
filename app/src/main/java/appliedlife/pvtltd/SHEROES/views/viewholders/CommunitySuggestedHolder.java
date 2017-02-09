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
import appliedlife.pvtltd.SHEROES.models.entities.communities.CommunitySuggestion;
import appliedlife.pvtltd.SHEROES.models.entities.searchmodule.Feature;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 02-02-2017.
 */

public class CommunitySuggestedHolder extends BaseViewHolder<Feature> {
    private final String TAG = LogUtils.makeLogTag(CommunitySuggestedHolder.class);
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.rv_suggested_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.li_suggested_community_see_more_like)
    LinearLayout liSuggestedCommunitySeeMoreLike;
    BaseHolderInterface viewInterface;
    private Feature dataItem;


    public CommunitySuggestedHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(Feature item, final Context context, int position) {
        this.dataItem = item;
        List<CommunitySuggestion> suggestedPost=new ArrayList<>();
        CommunitySuggestion communitySuggestion1=new CommunitySuggestion();
        communitySuggestion1.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14846520381484652038.png");
        communitySuggestion1.setPostName("First name");
        CommunitySuggestion communitySuggestion2=new CommunitySuggestion();
        communitySuggestion2.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14845475641484547564.png");
        communitySuggestion2.setPostName("Second name");
        CommunitySuggestion communitySuggestion3=new CommunitySuggestion();
        communitySuggestion3.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14847278181484727818.png");
        communitySuggestion3.setPostName("Third name");
        CommunitySuggestion communitySuggestion4=new CommunitySuggestion();
        communitySuggestion4.setImageUrl("https://img.sheroes.in/img/uploads/forumbloggallary/14842893151484289315.png");
        suggestedPost.add(communitySuggestion1);
        suggestedPost.add(communitySuggestion2);
        suggestedPost.add(communitySuggestion3);
        suggestedPost.add(communitySuggestion4);

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

        int id = view.getId();
        switch (id) {
            //case R.id.tv_community_join:
          //      break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}
