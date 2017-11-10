package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.login.UserBO;
import appliedlife.pvtltd.SHEROES.models.entities.post.Prize;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 10/10/17.
 */

public class WinnerListAdapter extends RecyclerView.Adapter<WinnerListAdapter.WinnerListItemViewHolder> {

    private final Context mContext;
    private List<Prize> mPrizes;

    public WinnerListAdapter(Context context) {
        this.mContext = context;
        mPrizes = new ArrayList<>();
    }

    @Override
    public WinnerListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_winner_item, parent, false);
        return new WinnerListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WinnerListItemViewHolder holder, int position) {
        Prize prize = mPrizes.get(position);
        holder.mWinnerTitle.setText(prize.title);
        int pos = 0;
        if (!CommonUtil.isEmpty(prize.winners)) {
            for (final UserBO user : prize.winners) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.layout_user_winner, null);

                ImageView profilePic = ButterKnife.findById(view, R.id.profile_pic);
                TextView name = ButterKnife.findById(view, R.id.name);
                TextView ageInfo = ButterKnife.findById(view, R.id.age_info);
                TextView mDescription = ButterKnife.findById(view, R.id.description);
                ImageView mTrophyImage = ButterKnife.findById(view, R.id.trophy_image);
                View dividerView = ButterKnife.findById(view, R.id.divider);

                if(pos == prize.winners.size() - 1){
                    dividerView.setVisibility(View.GONE);
                }
                mDescription.setText(prize.description);

                if (CommonUtil.isNotEmpty(prize.featureImage)) {
                    String trophyImage = CommonUtil.getImgKitUri(prize.featureImage, holder.authorPicSize, holder.authorPicSize);
                    Glide.with(profilePic.getContext())
                            .load(trophyImage)
                            .into(mTrophyImage);
                }

                if (user != null) {
                    name.setText(user.getName());
                    ageInfo.setText(user.getJobTag());

                    if (user.getPhotoUrlPath() != null && CommonUtil.isNotEmpty(user.getPhotoUrlPath())) {
                        String userImage = CommonUtil.getImgKitUri(user.getPhotoUrlPath(), holder.authorPicSize, holder.authorPicSize);
                        Glide.with(profilePic.getContext())
                                .load(userImage)
                                //.placeholder(user.getPlaceholder())
                                .bitmapTransform(new CommunityOpenAboutFragment.CircleTransform(mContext))
                                .into(profilePic);
                    } /*else {
                        profilePic.setImageResource(user.getPlaceholder());
                    }*/

                }

                holder.winnersView.addView(view);
                pos ++;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mPrizes == null ? 0 : mPrizes.size();
    }

    public void setData(List<Prize> prizeList) {
        mPrizes = prizeList;
        notifyDataSetChanged();
    }

    // region Winner List Item ViewHolder
    static class WinnerListItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.winner_title)
        TextView mWinnerTitle;

        @BindDimen(R.dimen.dp_size_70)
        int authorPicSize;


        @Bind(R.id.list_winners)
        LinearLayout winnersView;

        public WinnerListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
