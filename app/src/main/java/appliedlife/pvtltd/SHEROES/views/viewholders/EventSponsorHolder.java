package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.home.EventSponsorData;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 19-06-2017.
 */

public class EventSponsorHolder extends BaseViewHolder<EventSponsorData> {
    private final String TAG = LogUtils.makeLogTag(EventSponsorHolder.class);
    @Inject
    DateUtil mDateUtil;
    BaseHolderInterface viewInterface;
    private EventSponsorData dataItem;
    Context mContext;
    @Bind(R.id.iv_event_sponsor_icon)
    ImageView mIvSponserImage;

    public EventSponsorHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(EventSponsorData item, Context context, int position) {
        this.dataItem = item;
        mContext = context;
        if (null != dataItem && StringUtil.isNotNullOrEmptyString(dataItem.getSponsorImageUrl()))
            Glide.with(mContext)
                    .load(dataItem.getSponsorImageUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA).skipMemoryCache(true))
                    .into(mIvSponserImage);
    }


    @Override
    public void onClick(View view) {


    }

    @Override
    public void viewRecycled() {

    }
}


