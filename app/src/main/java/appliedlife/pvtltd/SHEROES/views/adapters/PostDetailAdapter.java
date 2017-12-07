package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.viewholder.LoaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommentHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.UserPostHolder;

/**
 * Created by ujjwal on 07/12/17.
 */

public class PostDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private List<BaseResponse> mFeedDetail;
    private final View.OnClickListener mOnClickListener;
    private static final int TYPE_LOADER = 1;
    private static final int TYPE_USER_POST = 2;
    private static final int TYPE_COMMENT = 3;
    private boolean showLoader = false;

    //region Constructor
    public PostDetailAdapter(Context context, View.OnClickListener onClickListener) {
        this.mContext = context;
        mFeedDetail = new ArrayList<>();
        this.mOnClickListener = onClickListener;
    }
    //endregion

    //region PostDetailListAdapter methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_USER_POST:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_post, parent, false);
                return new UserPostHolder(view, null);
            case TYPE_COMMENT:
                View viewAlbum = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_comments_list_layout, parent, false);
                return new CommentHolder(viewAlbum, null);
            case TYPE_LOADER:
                return new LoaderViewHolder(mInflater.inflate(R.layout.comment_loader_view, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        switch (holder.getItemViewType()) {
            case TYPE_USER_POST:
                UserPostHolder userPostHolder = (UserPostHolder) holder;
                FeedDetail feedDetail = (FeedDetail) mFeedDetail.get(position);
                userPostHolder.bindData(feedDetail, mContext, position);
                break;
            case TYPE_COMMENT:
                CommentHolder commentHolder = (CommentHolder) holder;
                BaseResponse baseResponse = (BaseResponse) mFeedDetail.get(position);
                commentHolder.bindData((Comment) baseResponse, mContext, position);
                break;
            case TYPE_LOADER:
                LoaderViewHolder loaderViewHolder = ((LoaderViewHolder) holder);
                loaderViewHolder.bindData(holder.getAdapterPosition(), showLoader);
        }
    }

    @Override
    public int getItemCount() {
        return mFeedDetail == null ? 0 : mFeedDetail.size();
    }

    public void setData(final List<BaseResponse> feedDetails) {
        mFeedDetail = feedDetails;
        notifyDataSetChanged();
    }

    public void setItem(BaseResponse baseResponse, int position) {
        mFeedDetail.set(position, baseResponse);
        notifyItemChanged(position);
    }

    @Override
    public int getItemViewType(int position) {
        BaseResponse feedDetail = mFeedDetail.get(position);
        if (feedDetail instanceof UserPostSolrObj) {
            return TYPE_USER_POST;
        }
        if (feedDetail instanceof Comment) {
            return TYPE_COMMENT;
        }
        return 0;
    }

    public void addData(UserPostSolrObj userPostSolrObj) {
        mFeedDetail.add(0,userPostSolrObj);
        notifyItemChanged(0);
    }

    //endregion
}
