package appliedlife.pvtltd.SHEROES.basecomponents;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;

/**
 * Created by ujjwal on 07/12/17.
 */

public interface CommentCallBack {
    void onCommentMenuClicked(Comment comment, ImageView userCommentListMenu);

    void userCommentLikeRequest(Comment comment, boolean isLikedAction, int adapterPosition);

    void userProfileNameClick(Comment comment, View view);

    void userProfilePicClick(Comment comment, View  view);
}
