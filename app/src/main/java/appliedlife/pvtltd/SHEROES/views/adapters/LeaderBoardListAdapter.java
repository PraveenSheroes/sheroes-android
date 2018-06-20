package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.post.Winner;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 10/10/17.
 */

public class LeaderBoardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Winner> mPrizes;
    private static OnItemClickListener listener;

    private static final int TYPE_WINNER = 1;
    private static final int TYPE_HEADER = 2;

    public LeaderBoardListAdapter(Context context, OnItemClickListener itemClickListener) {
        this.mContext = context;
        mPrizes = new ArrayList<>();
        listener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_HEADER:
                return new LeaderboardHeaderItemViewHolder(mInflater.inflate(R.layout.list_winner_header_item, parent, false));
            case TYPE_WINNER:
                return new WinnerListItemViewHolder(mInflater.inflate(R.layout.list_leaderboard_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                LeaderboardHeaderItemViewHolder winnerHeaderItemViewHolder = (LeaderboardHeaderItemViewHolder) holder;
                Winner winner =  mPrizes.get(position);
                winnerHeaderItemViewHolder.bindData(winner, mContext);
                break;
            case TYPE_WINNER:
                WinnerListItemViewHolder winnerListItemViewHolder = (WinnerListItemViewHolder) holder;
                Winner winnerr =  mPrizes.get(position);
                winnerListItemViewHolder.bindData(winnerr, mContext);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Winner winner = mPrizes.get(position);
        if (winner.isHeader) {
            return TYPE_HEADER;
        } else {
            return TYPE_WINNER;
        }
    }

    @Override
    public int getItemCount() {
        return mPrizes == null ? 0 : mPrizes.size();
    }

    public void setData(List<Winner> winners) {
        mPrizes = winners;
        notifyDataSetChanged();
    }

    // region Winner List Item ViewHolder
    static class WinnerListItemViewHolder extends RecyclerView.ViewHolder {

       /* @Bind(R.id.profile_pic)
        ImageView mProfilePic;

        @Bind(R.id.trophy_image)
        ImageView mTrophyImage;

        @Bind(R.id.name)
        TextView mName;

        @Bind(R.id.rank)
        TextView mRank;

        @Bind(R.id.description)
        TextView mDescription;

        @BindDimen(R.dimen.dp_size_40)
        int mUserPicSize;

        @BindDimen(R.dimen.dp_size_24)
        int mTrophySize;*/


        public WinnerListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Winner prize, Context context){
            if (prize!=null) {
             /*   mDescription.setText(prize.prizeDescription);
                if (CommonUtil.isNotEmpty(prize.mPrizeIcon)) {
                    String trophyImageUrl = CommonUtil.getThumborUri(prize.mPrizeIcon, mTrophySize, mTrophySize);
                    Glide.with(mTrophyImage.getContext())
                            .load(trophyImageUrl)
                            .into(mTrophyImage);
                }
                mName.setText(prize.name);
                mRank.setText(prize.rank);
                if (prize.imageUrl != null && CommonUtil.isNotEmpty(prize.imageUrl)) {
                    String userImage = CommonUtil.getThumborUri(prize.imageUrl, mUserPicSize, mUserPicSize);
                    Glide.with(mProfilePic.getContext())
                            .load(userImage)
                            .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(context)))
                            .into(mProfilePic);
                }*/

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        listener.onItemClick(prize);
                    }
                });
            }
        }
    }

    static class LeaderboardHeaderItemViewHolder extends RecyclerView.ViewHolder {
        public LeaderboardHeaderItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Winner prize, Context context){

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Winner item);
    }

}
