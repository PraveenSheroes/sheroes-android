package appliedlife.pvtltd.SHEROES.views.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseHolderInterface;
import appliedlife.pvtltd.SHEROES.basecomponents.BaseViewHolder;
import appliedlife.pvtltd.SHEROES.basecomponents.SheroesApplication;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentsList;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.LogUtils;
import appliedlife.pvtltd.SHEROES.views.cutomeviews.CircleImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentHolder extends BaseViewHolder<CommentsList> {
    private final String TAG = LogUtils.makeLogTag(CommentHolder.class);
    @Bind(R.id.li_comment)
    LinearLayout liComment;
    @Bind(R.id.iv_comment_profile_pic)
    CircleImageView ivCommentProfilePic;
    @Bind(R.id.tv_user_comment)
    TextView tvUserComment;
    BaseHolderInterface viewInterface;
    private CommentsList dataItem;
    public CommentHolder(View itemView, BaseHolderInterface baseHolderInterface) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.viewInterface = baseHolderInterface;
        SheroesApplication.getAppComponent(itemView.getContext()).inject(this);
    }

    @Override
    public void bindData(CommentsList item, final Context context, int position) {
        this.dataItem = item;
        tvUserComment.setText(item.getDescription());
        String images = dataItem.getProfilePicUrl();
        ivCommentProfilePic.setCircularImage(true);
        ivCommentProfilePic.bindImage(images);
    }

    @Override
    public void viewRecycled() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.li_comment:
                break;
            default:
                LogUtils.error(TAG, AppConstants.CASE_NOT_HANDLED + " " + TAG + " " + id);
        }
    }

}