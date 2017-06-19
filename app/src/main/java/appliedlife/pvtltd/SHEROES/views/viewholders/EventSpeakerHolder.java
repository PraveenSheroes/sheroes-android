package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventSpeakerData;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 17-06-2017.
 */

public class EventSpeakerHolder extends BaseViewHolder<EventSpeakerData> {
    private final String TAG = LogUtils.makeLogTag(EventSpeakerHolder.class);
    @Inject
    DateUtil mDateUtil;
    BaseHolderInterface viewInterface;
    private EventSpeakerData dataItem;
    Context mContext;

    public EventSpeakerHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(EventSpeakerData item, Context context, int position) {
        this.dataItem = item;
        mContext = context;

    }


    @Override
    public void onClick(View view) {


    }

    @Override
    public void viewRecycled() {

    }
}

