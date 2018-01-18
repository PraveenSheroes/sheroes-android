package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

public class WinnerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Winner> mPrizes;

    private static final int TYPE_WINNER = 1;
    private static final int TYPE_HEADER = 2;

    public WinnerListAdapter(Context context) {
        this.mContext = context;
        mPrizes = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_HEADER:
                return new WinnerHeaderItemViewHolder(mInflater.inflate(R.layout.list_winner_header_item, parent, false));
            case TYPE_WINNER:
                return new WinnerListItemViewHolder(mInflater.inflate(R.layout.list_winner_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                WinnerHeaderItemViewHolder winnerHeaderItemViewHolder = (WinnerHeaderItemViewHolder) holder;
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

        @Bind(R.id.profile_pic)
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
        int mTrophySize;


        public WinnerListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Winner prize, Context context){
            if (prize!=null) {
                mDescription.setText(prize.prizeDescription);
                if (CommonUtil.isNotEmpty(prize.mPrizeIcon)) {
                    String trophyImageUrl = CommonUtil.getImgKitUri(prize.mPrizeIcon, mTrophySize, mTrophySize);
                    Glide.with(mTrophyImage.getContext())
                            .load(trophyImageUrl)
                            .into(mTrophyImage);
                }
                mName.setText(prize.name);
                mRank.setText(prize.rank);
                if (prize.imageUrl != null && CommonUtil.isNotEmpty(prize.imageUrl)) {
                    String userImage = CommonUtil.getImgKitUri(prize.imageUrl, mUserPicSize, mUserPicSize);
                    Glide.with(mProfilePic.getContext())
                            .load(userImage)
                            .bitmapTransform(new CommonUtil.CircleTransform(context))
                            .into(mProfilePic);
                }
            }
        }
    }

    static class WinnerHeaderItemViewHolder extends RecyclerView.ViewHolder {
        public WinnerHeaderItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Winner prize, Context context){

        }
    }

}
