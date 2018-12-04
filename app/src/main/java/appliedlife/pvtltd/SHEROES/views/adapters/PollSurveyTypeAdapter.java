package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.models.entities.post.PollOptionType;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.PostBottomSheetFragment;
import appliedlife.pvtltd.SHEROES.views.viewholders.PollTypesViewHolder;


/**
 * Created by Praveen on 06/08/18.
 */

public class PollSurveyTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //region Private variables & Constants
    private static final int TYPE_POLL = 0;
    private static final int TYPE_SHOW_MORE = 1;
    private List<PollOptionType> mPollOptionTypes = null;
    private final Context mContext;
    private BaseHolderInterface baseHolderInterface;
    private PostBottomSheetFragment mPostBottomSheetFragment;
    //endregion


    //region Constructor
    public PollSurveyTypeAdapter(Context context, BaseHolderInterface baseHolderInterface, PostBottomSheetFragment postBottomSheetFragment) {
        mContext = context;
        this.baseHolderInterface = baseHolderInterface;
        this.mPostBottomSheetFragment = postBottomSheetFragment;
    }
    //endregion

    //region Adapter method
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mPostBottomSheetFragment.getContext());
        View view = mInflater.inflate(R.layout.poll_survey_type_holder, parent, false);
        return new PollTypesViewHolder(view, baseHolderInterface, mPostBottomSheetFragment);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (CommonUtil.isEmpty(mPollOptionTypes)) return;

        if (holder.getItemViewType() == TYPE_POLL) {
            PollTypesViewHolder pollTypesViewHolder = (PollTypesViewHolder) holder;
            PollOptionType pollOptionType = mPollOptionTypes.get(position);
            pollTypesViewHolder.bindData(pollOptionType, mContext, position);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_POLL;
    }

    @Override
    public int getItemCount() {
        return CommonUtil.isEmpty(mPollOptionTypes) ? 0 : mPollOptionTypes.size();
    }
    //endregion

    //region Public method
    public void setData(List<PollOptionType> communities) {
        if (!CommonUtil.isEmpty(communities)) {
            this.mPollOptionTypes = communities;
        }
        notifyDataSetChanged();
    }
    //endregion
}
