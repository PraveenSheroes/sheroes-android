package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.community.LeaderBoardUserDetail;
import butterknife.ButterKnife;

/**
 * Created by Ravi on 17/06/18.
 */

public class LeaderBoardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<LeaderBoardUserDetail> leaderBoardUserDetails;
    private static OnItemClickListener listener;

    private static final int TYPE_WINNER = 1;
    private static final int TYPE_HEADER = 2;

    public LeaderBoardListAdapter(Context context, OnItemClickListener itemClickListener) {
        this.mContext = context;
        leaderBoardUserDetails = new ArrayList<>();
        listener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_HEADER:
                return new LeaderBoardHeaderItemViewHolder(mInflater.inflate(R.layout.list_winner_header_item, parent, false));
            case TYPE_WINNER:
                return new WinnerListItemViewHolder(mInflater.inflate(R.layout.list_leaderboard_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                LeaderBoardHeaderItemViewHolder leaderBoardHeaderItemViewHolder = (LeaderBoardHeaderItemViewHolder) holder;
                LeaderBoardUserDetail leaderBoardUserDetail = leaderBoardUserDetails.get(position);
                leaderBoardHeaderItemViewHolder.bindData(leaderBoardUserDetail, mContext);
                break;
            case TYPE_WINNER:
                WinnerListItemViewHolder winnerListItemViewHolder = (WinnerListItemViewHolder) holder;
                LeaderBoardUserDetail leaderBoardUserDetailItem = leaderBoardUserDetails.get(position);
                winnerListItemViewHolder.bindData(leaderBoardUserDetailItem, mContext);
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        /*LeaderBoardUserDetail winner = leaderBoardUserDetails.get(position);
        if (winner.isHeader) {
            return TYPE_HEADER;
        } else {
            return TYPE_WINNER;
        }*/

        return TYPE_WINNER;
    }

    @Override
    public int getItemCount() {
        return leaderBoardUserDetails == null ? 0 : leaderBoardUserDetails.size();
    }

    public void setData(List<LeaderBoardUserDetail> leaderBoardUserDetails) {
        this.leaderBoardUserDetails = leaderBoardUserDetails;
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

        public void bindData(final LeaderBoardUserDetail leaderBoardUserDetail, Context context) {
            if (leaderBoardUserDetail != null) {
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
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(leaderBoardUserDetail);
                    }
                });
            }
        }
    }

    static class LeaderBoardHeaderItemViewHolder extends RecyclerView.ViewHolder {

        public LeaderBoardHeaderItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(LeaderBoardUserDetail le, Context context) {

        }
    }

    public interface OnItemClickListener {
        void onItemClick(LeaderBoardUserDetail leaderBoardUserDetail);

        void onProfilePicClick(LeaderBoardUserDetail leaderBoardUserDetail);
    }

}
