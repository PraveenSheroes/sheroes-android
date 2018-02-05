package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.feed.CarouselDataObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.adapters.CarouselListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen on 24/11/17.
 */

public class CarouselViewHolder extends BaseViewHolder<CarouselDataObj> {
    private final String TAG = LogUtils.makeLogTag(CarouselViewHolder.class);
    private CarouselListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.rv_suggested_mentor_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_mentor_view_all)
    TextView tvMentorViewAll;
    @Bind(R.id.title)
    TextView title;

    BaseHolderInterface viewInterface;
    private FeedDetail dataItem;

    public CarouselViewHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }
    @Override
    public void bindData(CarouselDataObj item, final Context context, int position) {
        this.dataItem = item;

        if(StringUtil.isNotNullOrEmptyString(item.getTitle())) {
            title.setText(item.getTitle());
        }

        //if(StringUtil.isNotNullOrEmptyString(item.getEndPointUrl())) {
        ///    tvMentorViewAll.setText(item.getEndPointUrl());
       // }

        List<FeedDetail> list=item.getFeedDetails();
        if(StringUtil.isNotEmptyCollection(list)) {
            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new CarouselListAdapter(context, viewInterface);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.scrollToPosition(dataItem.getItemPosition());
            mAdapter.setData(item.getFeedDetails());
            mAdapter.notifyDataSetChanged();
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

