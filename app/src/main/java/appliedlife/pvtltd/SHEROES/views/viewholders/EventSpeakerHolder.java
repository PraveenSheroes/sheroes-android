package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventSpeakerData;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
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
    @Bind(R.id.tv_event_speaker_card_title)
    TextView tvEventSpeakerCardTitle;
    @Bind(R.id.tv_event_speaker_company)
    TextView tvEventSpeakerCompany;
    @Bind(R.id.iv_event_speaker_circle_icon)
    ImageView mIvSpeakerImage;
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
        if(StringUtil.isNotNullOrEmptyString(dataItem.getSpeakerName()))
        {
            tvEventSpeakerCardTitle.setText(dataItem.getSpeakerName());
        }
        if ( StringUtil.isNotNullOrEmptyString(dataItem.getSpeakerImageUrl()))
            Glide.with(mContext)
                    .load(dataItem.getSpeakerImageUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true))
                    .into(mIvSpeakerImage);
        if(StringUtil.isNotNullOrEmptyString(dataItem.getSpeakerDesignation()))
        {
            tvEventSpeakerCompany.setText(dataItem.getSpeakerDesignation());
        }
    }


    @Override
    public void onClick(View view) {


    }

    @Override
    public void viewRecycled() {

    }
}

