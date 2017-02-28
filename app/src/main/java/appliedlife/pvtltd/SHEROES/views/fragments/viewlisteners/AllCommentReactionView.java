package appliedlife.pvtltd.SHEROES.views.fragments.viewlisteners;

import appliedlife.pvtltd.SHEROES.basecomponents.BaseMvpView;
import appliedlife.pvtltd.SHEROES.models.entities.comment.CommentReactionResponsePojo;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public interface AllCommentReactionView extends BaseMvpView {
    void getAllCommentsAndReactions(CommentReactionResponsePojo commentReactionResponsePojo);
    void commentSuccess(String success,boolean isAddComment);
}