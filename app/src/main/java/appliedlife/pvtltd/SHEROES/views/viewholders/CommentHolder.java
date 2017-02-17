package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionDoc;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentHolder extends BaseViewHolder<CommentReactionDoc> {
    private final String TAG = LogUtils.makeLogTag(CommentHolder.class);
    @Bind(R.id.li_list_comment)
    LinearLayout liListComment;
    @Bind(R.id.iv_list_comment_profile_pic)
    CircleImageView ivListCommentProfilePic;
    @Bind(R.id.tv_list_user_comment)
    TextView tvUserComment;
    @Bind(R.id.tv_user_comment_list_menu)
    TextView tvUserCommentListMenu;
    Context mContext;
    BaseHolderInterface viewInterface;
    private CommentReactionDoc dataItem;

    public CommentHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(CommentReactionDoc item, final Context context, int position) {
        this.dataItem = item;
        this.mContext = context;
        if (item.isAnonymous()) {
            tvUserCommentListMenu.setVisibility(View.GONE);
        }
        if (StringUtil.isNotNullOrEmptyString(item.getParticipantImageUrl())) {
            ivListCommentProfilePic.setCircularImage(true);
            ivListCommentProfilePic.bindImage(item.getParticipantImageUrl());
        }
        if (StringUtil.isNotNullOrEmptyString(item.getComment())) {
            tvUserComment.setText(item.getComment());
            ivListCommentProfilePic.bindImage(item.getParticipantImageUrl());
        }
    }

    @Override
    public void viewRecycled() {

    }

    @OnClick(R.id.tv_user_comment_list_menu)
    public void onCommentMenuClick() {
        viewInterface.handleOnClick(dataItem, tvUserCommentListMenu);
    }

    @Override
    public void onClick(View view) {
    }

}