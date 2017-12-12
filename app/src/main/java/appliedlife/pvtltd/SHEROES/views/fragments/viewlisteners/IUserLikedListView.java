package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;
import appliedlife.pvtltd.SHEROES.models.entities.post.Prize;

/**
 * Created by ujjwal on 04/05/17.
 */

public interface IUserLikedListView extends BaseMvpView {
    void showUserLikedList(List<Comment> commentList);
}
