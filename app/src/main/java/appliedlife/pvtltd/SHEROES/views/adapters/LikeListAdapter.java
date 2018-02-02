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
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 04/02/16.
 */
public class LikeListAdapter extends RecyclerView.Adapter<LikeListAdapter.LikeListItemViewHolder> {
    private final Context mContext;
    private List<Comment> mCommentList = new ArrayList<>();
    private LikeHolderListener onLikeClickListener;

    //region Constructor
    public LikeListAdapter(Context context, List<Comment> likeList) {
        this.mContext = context;
        this.mCommentList = likeList;
    }
    //endregion

    //region Constructor
    public LikeListAdapter(Context context, List<Comment> likeList, LikeHolderListener onLikeClickListener) {
        this.mContext = context;
        this.mCommentList = likeList;
        this.onLikeClickListener = onLikeClickListener;
    }

    //region UserLikedListAdapter methods
    @Override
    public LikeListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_liked_item, parent, false);
        return new LikeListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LikeListItemViewHolder holder, int position) {
        holder.bindData(mCommentList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }

    public Comment getComment(int position){
        if(position >= 0 && position < mCommentList.size() ){
            return mCommentList.get(position);
        }else {
            return null;
        }
    }
    //endregion

    // region User Liked List Item ViewHolder
    public class LikeListItemViewHolder extends RecyclerView.ViewHolder {

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLikeClickListener.onLikeRowItemClicked(LikeListItemViewHolder.this);
                }
            });
        }

        public void bindData(final Comment comment, final int position) {
            if (comment != null) {
                if (comment.getParticipantImageUrl() != null && CommonUtil.isNotEmpty(comment.getParticipantImageUrl())) {
                    String userImage = CommonUtil.getImgKitUri(comment.getParticipantImageUrl(), authorPicSize, authorPicSize);
                    Glide.with(userPic.getContext())
                            .load(userImage)
                            .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mContext)))
                            .into(userPic);
                }
                userName.setText(comment.getParticipantName());
            }
        }
    }

    public interface LikeHolderListener {
        void onLikeRowItemClicked(LikeListItemViewHolder likeListItemViewHolder);
    }
    // endregion
}
