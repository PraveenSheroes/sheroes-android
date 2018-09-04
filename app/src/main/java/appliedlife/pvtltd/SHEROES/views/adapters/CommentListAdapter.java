package appliedlife.pvtltd.SHEROES.views.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.presenters.ArticlePresenterImpl;
import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;
import appliedlife.pvtltd.SHEROES.utils.AppUtils;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;

/**
 * Created by ujjwal on 06/02/17.
 */

public class CommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Comment> mCommentList;
    private final Context mContext;
    private final View.OnClickListener mOnViewClickListener;
    private final ArticlePresenterImpl mArticlePresenter;
    private boolean showMoreItem = false;
    public static final int INITIAL_ITEM_COUNT = 1;
    public int commentAdded = 0;
    private List<MentionSpan> mentionSpanList;
    private boolean hasMentions=false;
    //region Constructor
    public CommentListAdapter(Context context, ArticlePresenterImpl articlePresenter, View.OnClickListener onDeleteClickListener) {
        mContext = context;
        mArticlePresenter = articlePresenter;
        mOnViewClickListener = onDeleteClickListener;
    }

    //endregion
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        return new CommentListItemViewHolder(mInflater.inflate(R.layout.list_comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentListItemViewHolder commentListItemViewHolder = (CommentListItemViewHolder) holder;
        Comment comment = mCommentList.get(position);
        commentListItemViewHolder.bindData(comment, position);
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(mCommentList)) {
            return 0;
        }
        if (!showMoreItem) {
            if (mCommentList.size() > INITIAL_ITEM_COUNT + commentAdded) {
                return INITIAL_ITEM_COUNT + commentAdded;
            }
        }
        return mCommentList.size();
    }

    public void showMoreItem(boolean isShowMoreItem) {
        showMoreItem = isShowMoreItem;
        notifyDataSetChanged();
    }

    public void setData(ArrayList<Comment> comments) {
        if (comments == null) {
            mCommentList = new ArrayList<>();
        }
        mCommentList = comments;
        notifyDataSetChanged();
    }

    public Comment getComment(int position){
        if(position >= 0 && position < mCommentList.size() ){
            return mCommentList.get(position);
        }else {
            return null;
        }
    }

    public void addDataAndNotify(Comment comment) {
        if (mCommentList == null) {
            mCommentList = new ArrayList<>();
        }
        mCommentList.add(0, comment);
        commentAdded++;
        notifyDataSetChanged();
    }

    public void removeAndNotify(int position) {
        if (CommonUtil.isEmpty(mCommentList)) {
            return;
        }
        mCommentList.remove(position);
        notifyDataSetChanged();
    }

    public void setData(Comment comment1, int position) {
        mCommentList.set(position, comment1);
        notifyItemChanged(position);
    }

    // region Comment List Item ViewHolder
    public class CommentListItemViewHolder extends RecyclerView.ViewHolder {

        // region Butterknife Bindings
        @Bind(R.id.author_pic)
        ImageView authorPic;

        @Bind(R.id.comment_relative_time)
        TextView relativeTime;

        @Bind(R.id.author_pic_container)
        FrameLayout authorPicContainer;

        @BindDimen(R.dimen.authorPicSize)
        int authorPicSize;

        @Bind(R.id.author)
        TextView author;

        @Bind(R.id.user_badge)
        ImageView badgeIcon;

        @Bind(R.id.body)
        TextView body;

        @Bind(R.id.edit_body)
        EditText editBody;

        @Bind(R.id.edit)
        TextView edit;

        @Bind(R.id.delete)
        ImageView delete;

        @Bind(R.id.submit)
        Button submit;

        @Bind(R.id.cancel)
        RelativeLayout mCancel;

        @Bind(R.id.spam_comment_container)
        LinearLayout spamContainer;

        @Bind(R.id.spam_article_comment_menu)
        ImageView spamCommentMenuIcon;

        // endregion

        public CommentListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Comment comment, final int position) {
            if (comment != null) {
                String lineSep = System.getProperty("line.separator");
                String commentBody = comment.getComment().replaceAll("(<br />|<br>|<br/>)", lineSep);
                body.setText(commentBody);
                body.setTextIsSelectable(true);
                body.setLinkTextColor(ContextCompat.getColor(mContext, R.color.link_color));
                StringUtil.linkifyURLs(body);

                if(comment.isSpamComment()) {
                    spamContainer.setVisibility(View.VISIBLE);
                } else {
                    spamContainer.setVisibility(View.GONE);
                }

                if (comment.getPostedDate() != null) {
                    relativeTime.setText(comment.getPostedDate());
                    //relativeTime.setText(DateUtil.getRelativeTimeSpanString(comment.publishedAt));
                }
                if (comment.getParticipantName() != null) {
                    author.setText(comment.getParticipantName());
                   // bio.setText(comment.getPa);
                    if (comment.getParticipantImageUrl() != null && CommonUtil.isNotEmpty(comment.getParticipantImageUrl())) {
                        String authorImage = CommonUtil.getThumborUri(comment.getParticipantImageUrl(), authorPicSize, authorPicSize);
                        Glide.with(mContext)
                                .load(authorImage)
                               // .placeholder(comment.author.getPlaceholder())
                                .apply(new RequestOptions().transform(new CommonUtil.CircleTransform(mContext)))
                                .into(authorPic);

                    } else {
                        //authorPic.setImageResource(comment.author.getPlaceholder());
                    }
                } else {
                    author.setText("User");
                    //authorPic.setImageDrawable(ContextCompat.getDrawable(mContext, User.getAnonymousPlaceholder()));
                }
                CommonUtil.showHideUserBadge(mContext, comment.isAnonymous(), badgeIcon, comment.isBadgeShown(), comment.getBadgeUrl());

                hideEditorView(comment);
                author.setOnClickListener(mOnViewClickListener);
                authorPicContainer.setOnClickListener(mOnViewClickListener);
                delete.setOnClickListener(mOnViewClickListener);
                delete.setVisibility(View.VISIBLE);

                if (comment.isMyOwnParticipation()) {
                    edit.setVisibility(View.VISIBLE);
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showEditorView();
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    hideKeyboard();
                                    hideEditorView(comment);
                                    mArticlePresenter.onEditComment(position, AppUtils.getInstance().editCommentRequestBuilder(comment.getEntityId(), editBody.getText().toString().trim(), false, true, comment.getId(),hasMentions,mentionSpanList));
                                }
                            });
                            mCancel.setOnClickListener((new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    hideKeyboard();
                                    hideEditorView(comment);
                                }
                            }));
                        }
                    });
                  //  delete.setVisibility(View.VISIBLE);
                  //  delete.setOnClickListener(mOnDeleteClickListener);
                } else {
                    edit.setVisibility(View.GONE);
                    edit.setOnClickListener(null);
                  //  delete.setVisibility(View.GONE);
                  //  delete.setOnClickListener(null);
                }

            }

        }

        private void showKeyboard() {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editBody, InputMethodManager.SHOW_IMPLICIT);
        }

        public void hideKeyboard(){
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editBody.getWindowToken(), 0);
        }

        private void showEditorView() {
            body.setVisibility(View.GONE);
            editBody.setText(body.getText().toString());
            editBody.setVisibility(View.VISIBLE);
            editBody.setSelection(editBody.getText().length());
            editBody.requestFocus();
            showKeyboard();
            submit.setVisibility(View.VISIBLE);
            mCancel.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }

        private void hideEditorView(Comment comment) {
            submit.setVisibility(View.GONE);
            mCancel.setVisibility(View.GONE);
            editBody.setVisibility(View.GONE);
            body.setVisibility(View.VISIBLE);
            if (comment.isMyOwnParticipation()) {
                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
            } else {
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
            }
        }

    }
    //endregion
}
