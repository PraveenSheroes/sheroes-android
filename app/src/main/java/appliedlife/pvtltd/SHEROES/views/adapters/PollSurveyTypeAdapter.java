package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.post.PollType;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.viewholders.MyCommunitiesViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.PollTypesViewHolder;


/**
 * Created by Praveen on 06/08/18.
 */

public class PollSurveyTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //region Private variables & Constants
    private static final int TYPE_POLL = 0;
    private static final int TYPE_SHOW_MORE = 1;
    private List<PollType> mPollTypes = null;
    private final Context mContext;
    private BaseHolderInterface baseHolderInterface;
    //endregion


    //region Constructor
    public PollSurveyTypeAdapter(Context context, BaseHolderInterface baseHolderInterface) {
        mContext = context;
        this.baseHolderInterface = baseHolderInterface;
    }
    //endregion

    //region Adapter method
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.poll_survey_type_holder, parent, false);
        return new PollTypesViewHolder(view, baseHolderInterface);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (CommonUtil.isEmpty(mPollTypes)) return;

        if (holder.getItemViewType() == TYPE_POLL) {
            PollTypesViewHolder pollTypesViewHolder = (PollTypesViewHolder) holder;
            PollType pollType = mPollTypes.get(position);
            pollTypesViewHolder.bindData(pollType, mContext, position);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_POLL;
    }

    @Override
    public int getItemCount() {
        return CommonUtil.isEmpty(mPollTypes) ? 0 : mPollTypes.size();
    }
    //endregion

    //region Public method
    public void setData(List<PollType> communities) {
        if (!CommonUtil.isEmpty(communities)) {
            this.mPollTypes = communities;
        }
        notifyDataSetChanged();
    }
    //endregion
}
