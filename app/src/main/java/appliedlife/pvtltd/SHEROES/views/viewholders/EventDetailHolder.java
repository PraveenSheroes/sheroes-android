package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventDetailPojo;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventSpeakerData;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.activities.HomeActivity;
import appliedlife.pvtltd.SHEROES.views.adapters.GenericRecyclerViewAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 16-06-2017.
 */

public class EventDetailHolder extends BaseViewHolder<EventDetailPojo> {
    private final String TAG = LogUtils.makeLogTag(EventDetailHolder.class);
    @Inject
    DateUtil mDateUtil;
    BaseHolderInterface viewInterface;
    private EventDetailPojo dataItem;
    @Bind(R.id.rv_event_speaker_list)
    RecyclerView mRecyclerView;
    private GenericRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    Context mContext;

    public EventDetailHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(EventDetailPojo item, Context context, int position) {
        this.dataItem = item;
        mContext = context;
        if (null != dataItem && StringUtil.isNotEmptyCollection(dataItem.getDisplayIdSpeakerId())) {
            List<EventSpeakerData> speakerList = new ArrayList<>();
            int size = dataItem.getDisplayIdSpeakerId().size();
            for (int i = 0; i < size; i++) {
                EventSpeakerData eventSpeakerData = new EventSpeakerData();
                eventSpeakerData.setSpeakerName(dataItem.getDisplayTextSpeakerName().get(i));
                eventSpeakerData.setSpeakerDescription(dataItem.getDisplayTextSpeakerDesc().get(i));
                eventSpeakerData.setSpeakerDesignation(dataItem.getDisplayTextSpeakerDesignation().get(i));
                eventSpeakerData.setSpeakerImageUrl(dataItem.getDisplayTextSpeakerImageUrl().get(i));
                eventSpeakerData.setSpeakerId(dataItem.getDisplayIdSpeakerId().get(i));
                speakerList.add(eventSpeakerData);
                speakerList.add(eventSpeakerData);
                speakerList.add(eventSpeakerData);
                speakerList.add(eventSpeakerData);
            }
            setSpeakerListItem(speakerList);
        }
    }

    private void setSpeakerListItem(List<EventSpeakerData> speakerList) {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GenericRecyclerViewAdapter(mContext, (HomeActivity) mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setSheroesGenericListData(speakerList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {


    }

    @Override
    public void viewRecycled() {

    }
}
