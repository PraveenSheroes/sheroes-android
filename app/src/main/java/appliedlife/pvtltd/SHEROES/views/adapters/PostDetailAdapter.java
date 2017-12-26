package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.CommentCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.viewholder.LoaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommentNewViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.UserPostHolder;

/**
 * Created by ujjwal on 07/12/17.
 */

public class PostDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private List<BaseResponse> mFeedDetail;
    private final PostDetailCallBack mPostDetailCallback;
    private final CommentCallBack mCommentCallback;
    private static final int TYPE_LOADER = 1;
    private static final int TYPE_USER_POST = 2;
    private static final int TYPE_COMMENT = 3;
    private boolean showLoader = false;
    private boolean hasMoreItem = false;
    private int loaderPostion;

    //region Constructor
    public PostDetailAdapter(Context context, PostDetailCallBack postDetailCallBack, CommentCallBack commentCallBack) {
        this.mContext = context;
        mFeedDetail = new ArrayList<>();
        this.mPostDetailCallback = postDetailCallBack;
        this.mCommentCallback = commentCallBack;
    }
    //endregion

    //region PostDetailListAdapter methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_USER_POST:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_post, parent, false);
                return new UserPostHolder(view, mPostDetailCallback);
            case TYPE_COMMENT:
                View viewAlbum = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_new_layout, parent, false);
                return new CommentNewViewHolder(viewAlbum, mCommentCallback);
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
                CommentNewViewHolder commentNewViewHolder = (CommentNewViewHolder) holder;
                BaseResponse baseResponse = (BaseResponse) mFeedDetail.get(position);
                commentNewViewHolder.bindData((Comment) baseResponse, mContext, position);
                break;
            case TYPE_LOADER:
                LoaderViewHolder loaderViewHolder = ((LoaderViewHolder) holder);
                loaderViewHolder.bindData(holder.getAdapterPosition(), showLoader, mPostDetailCallback);
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
        return TYPE_LOADER;
    }

    public void addData(BaseResponse response, int indexAt) {
        mFeedDetail.add(indexAt, response);
        notifyItemInserted(indexAt);
    }

    public void addData(BaseResponse baseResponse) {
        int size = mFeedDetail.size();
        mFeedDetail.add(baseResponse);
        notifyItemInserted(size);
    }

    public void commentStartedLoading() {
        if (showLoader) return;
        showLoader = true;
        int loadingPos = getLoaderPostion();
        if (loadingPos != RecyclerView.NO_POSITION) {
            notifyItemChanged(loadingPos);
        }
    }

    public void commentFinishedLoading() {
        if (!showLoader) return;
        final int loadingPos = getLoaderPostion();
        showLoader = false;
        if (loadingPos != RecyclerView.NO_POSITION) {
            notifyItemChanged(loadingPos);
        }
    }

    public int getLoaderPostion() {
        int pos = RecyclerView.NO_POSITION;
        if(hasMoreItem){
            if(!CommonUtil.isEmpty(mFeedDetail)){
                BaseResponse baseResponse = mFeedDetail.get(0);
                if(baseResponse instanceof UserPostSolrObj){
                    pos = 1;
                }
            }
        }
        return pos;
    }

    public void setHasMoreComments(boolean hasMoreComments) {
        if(!hasMoreComments){
            int lodPos = getLoaderPostion();
            mFeedDetail.remove(lodPos);
            notifyItemRemoved(lodPos);
        }
        this.hasMoreItem = hasMoreComments;
    }

    public void addDatas(int startIndex, List<Comment> commentList) {
        mFeedDetail.addAll(startIndex, commentList);
        notifyItemRangeInserted(startIndex, commentList.size());
    }

    public void removeData(int index) {
        mFeedDetail.remove(index);
        notifyItemRemoved(index);
    }

    public void setData(int index, BaseResponse baseResponse) {
        mFeedDetail.set(index, baseResponse);
        notifyItemChanged(index);
    }

    //endregion
}
