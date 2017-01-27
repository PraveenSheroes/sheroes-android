package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentsList;
import appliedlife.pvtltd.SHEROES.models.entities.comment.ReactionList;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public interface AllCommentReactionView extends BaseMvpView {
    void getAllComments(List<CommentsList> data);
    void getAllReactions(List<ReactionList> data);
    void showNwError();
}