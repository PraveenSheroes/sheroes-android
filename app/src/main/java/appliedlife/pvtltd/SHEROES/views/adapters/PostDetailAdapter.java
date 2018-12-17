package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.CommentCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.models.entities.feed.PollSolarObj;
import appliedlife.pvtltd.SHEROES.models.entities.feed.UserPostSolrObj;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.viewholder.CommentLoaderViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.CommentNewViewHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.FeedPollCardHolder;
import appliedlife.pvtltd.SHEROES.views.viewholders.UserPostHolder;

/**
 * Created by ujjwal on 07/12/17.
 */

public class PostDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private List<BaseResponse> mFeedDetailList;
    private final PostDetailCallBack mPostDetailCallback;
    private final CommentCallBack mCommentCallback;
    private static final int TYPE_LOADER = 1;
    private static final int TYPE_USER_POST = 2;
    private static final int TYPE_COMMENT = 3;
    private static final int TYPE_POLL = 4;
    private boolean showLoader = false;
    private boolean hasMoreItem = false;
    private int loaderPostion;

    //region Constructor
    public PostDetailAdapter(Context context, PostDetailCallBack postDetailCallBack, CommentCallBack commentCallBack) {
        this.mContext = context;
        mFeedDetailList = new ArrayList<>();
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
            case TYPE_POLL:
                View viewPoll = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_poll_card_detail_holder, parent, false);
                return new FeedPollCardHolder(viewPoll, mPostDetailCallback);
            case TYPE_COMMENT:
                View viewAlbum = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_new_layout, parent, false);
                return new CommentNewViewHolder(viewAlbum, mCommentCallback);
            case TYPE_LOADER:
                return new CommentLoaderViewHolder(mInflater.inflate(R.layout.comment_loader_view, parent, false));
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
                FeedDetail feedDetail = (FeedDetail) mFeedDetailList.get(position);
                userPostHolder.bindData(feedDetail, mContext, position);
                break;
            case TYPE_POLL:
                FeedPollCardHolder feedPollCardHolder = (FeedPollCardHolder) holder;
                PollSolarObj pollSolarObj = (PollSolarObj) mFeedDetailList.get(position);
                feedPollCardHolder.bindData(pollSolarObj, mContext, position);
                break;
            case TYPE_COMMENT:
                CommentNewViewHolder commentNewViewHolder = (CommentNewViewHolder) holder;
                BaseResponse baseResponse = mFeedDetailList.get(position);
                commentNewViewHolder.bindData((Comment) baseResponse, mContext, position);
                break;
            case TYPE_LOADER:
                CommentLoaderViewHolder commentLoaderViewHolder = ((CommentLoaderViewHolder) holder);
                commentLoaderViewHolder.bindData(holder.getAdapterPosition(), showLoader, mPostDetailCallback);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mFeedDetailList == null ? 0 : mFeedDetailList.size();
    }

    public void setData(final List<BaseResponse> feedDetails) {
        mFeedDetailList = feedDetails;
        notifyDataSetChanged();
    }

    public void setItem(BaseResponse baseResponse, int position) {
        mFeedDetailList.set(position, baseResponse);
        notifyItemChanged(position);
    }

    public List<BaseResponse> getItems() {
        return mFeedDetailList;
    }

    @Override
    public int getItemViewType(int position) {
        BaseResponse feedDetail = mFeedDetailList.get(position);
        if (feedDetail instanceof UserPostSolrObj) {
            return TYPE_USER_POST;
        }
        if (feedDetail instanceof PollSolarObj) {
            return TYPE_POLL;
        }
        if (feedDetail instanceof Comment) {
            return TYPE_COMMENT;
        }
        return TYPE_LOADER;
    }

    public void addData(BaseResponse response, int indexAt) {
        if (indexAt < mFeedDetailList.size()) {
            mFeedDetailList.add(indexAt, response);
            notifyItemInserted(indexAt);
        } else {
            addData(response);
        }
    }

    public void addData(BaseResponse baseResponse) {
        int size = mFeedDetailList.size();
        mFeedDetailList.add(baseResponse);
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
        if (hasMoreItem) {
            if (!StringUtil.isNotEmptyCollection(mFeedDetailList)) {
                BaseResponse baseResponse = mFeedDetailList.get(0);
                if (baseResponse instanceof UserPostSolrObj) {
                    pos = 1;
                }
            }
        }
        return pos;
    }

    public void setHasMoreComments(boolean hasMoreComments) {
        if (!hasMoreComments) {
            int lodPos = getLoaderPostion();
            if (lodPos >= 0) {
                mFeedDetailList.remove(lodPos);
                notifyItemRemoved(lodPos);
            }
        }
        this.hasMoreItem = hasMoreComments;
    }

    public void addDatas(int startIndex, List<Comment> commentList) {
        mFeedDetailList.addAll(startIndex, commentList);
        notifyItemRangeInserted(startIndex, commentList.size());
    }

    public void removeData(int index) {
        if (index < mFeedDetailList.size()) {
            mFeedDetailList.remove(index);
            notifyItemRemoved(index);
        }
    }

    public void setData(int index, BaseResponse baseResponse) {
        mFeedDetailList.set(index, baseResponse);
        notifyItemChanged(index);
    }

    //endregion
}
