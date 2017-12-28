package appliedlife.pvtltd.SHEROES.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.PostDetailCallBack;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ujjwal on 23/02/17.
 */

public class CommentLoaderViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.progress_bar)
    ProgressBar mProgress;

    @Bind(R.id.add_container)
    LinearLayout mAddContainer;

    PostDetailCallBack mPostDetailCallBack;

    public CommentLoaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    public void bindData(int adapterPosition, boolean isFeedLoading, PostDetailCallBack postDetailCallBack) {
        mPostDetailCallBack = postDetailCallBack;
        if(isFeedLoading){
            mProgress.setVisibility(View.VISIBLE);
            mAddContainer.setVisibility(View.GONE);
        }else {
            mProgress.setVisibility(View.GONE);
            mAddContainer.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.add_container)
    public void onLoadMoreClick(){
        mPostDetailCallBack.loadMoreComments();
    }
}