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
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.views.fragments.CommunityOpenAboutFragment;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 04/02/16.
 */
public class LikeListAdapter extends RecyclerView.Adapter<LikeListAdapter.LikeListItemViewHolder> {
    private final Context mContext;
    private List<Comment> mCommentList = new ArrayList<>();

    //region Constructor
    public LikeListAdapter(Context context, List<Comment> likeList) {
        this.mContext = context;
        this.mCommentList = likeList;
    }
    //endregion

    //region UserLikedListAdapter methods
    @Override
    public LikeListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_liked_item, parent, false);
        return new LikeListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LikeListItemViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        if (comment != null) {
            if (comment.getParticipantImageUrl() != null && CommonUtil.isNotEmpty(comment.getParticipantImageUrl())) {
                String userImage = CommonUtil.getImgKitUri(comment.getParticipantImageUrl(), holder.authorPicSize, holder.authorPicSize);
                Glide.with(holder.userPic.getContext())
                        .load(userImage)
                        .bitmapTransform(new CommunityOpenAboutFragment.CircleTransform(mContext))
                        .into(holder.userPic);
            }
            holder.userName.setText(comment.getParticipantName());
        }
    }

    @Override
    public int getItemCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }
    //endregion

    // region User Liked List Item ViewHolder
    static class LikeListItemViewHolder extends RecyclerView.ViewHolder {

        // region Butterknife Bindings
        @BindDimen(R.dimen.dp_size_40)
        int authorPicSize;

        @Bind(R.id.user_pic)
        public ImageView userPic;

        @Bind(R.id.user_name)
        public TextView userName;
        // endregion

        public LikeListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    // endregion
}
