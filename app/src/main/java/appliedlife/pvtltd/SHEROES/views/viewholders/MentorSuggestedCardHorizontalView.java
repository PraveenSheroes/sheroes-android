package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.MentorDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserSolrObj;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.CommunityDetailActivity;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 24/11/17.
 */

public class MentorSuggestedCardHorizontalView extends BaseViewHolder<MentorDataObj> {
    private final String TAG = LogUtils.makeLogTag(MentorSuggestedCardHorizontalView.class);
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.rv_suggested_mentor_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_mentor_view_all)
    TextView tvMentorViewAll;
    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;

    public MentorSuggestedCardHorizontalView(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @Override
    public void bindData(MentorDataObj item, final Context context, int position) {
        this.dataItem = item;
        List<UserSolrObj> list=item.getMentorParticipantModel();
        if(StringUtil.isNotEmptyCollection(list)) {
            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            if(context instanceof HomeActivity){
                mAdapter = new GenericRecyclerViewAdapter(context, (HomeActivity) context);
            }else {
                mAdapter = new GenericRecyclerViewAdapter(context, (CommunityDetailActivity) context);
            }
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setSheroesGenericListData(list);
            mRecyclerView.scrollToPosition(dataItem.getItemPosition());
            mAdapter.setSuggestedCardPosition(position);
        }
    }
    @OnClick(R.id.tv_mentor_view_all)
    public void viewAllClick() {
        viewInterface.handleOnClick(dataItem, tvMentorViewAll);
    }
    @Override
    public void viewRecycled() {

    }
    @Override
    public void onClick(View view) {
    }

}

