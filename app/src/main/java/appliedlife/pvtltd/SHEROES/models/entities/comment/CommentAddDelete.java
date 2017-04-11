package appliedlife.pvtltd.SHEROES.models.entities.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 11-04-2017.
 */

public class CommentAddDelete extends BaseResponse {
    @SerializedName("participation_model")
    @Expose
    private CommentReactionDoc commentReactionModel = null;


    public CommentReactionDoc getCommentReactionModel() {
        return commentReactionModel;
    }

    public void setCommentReactionModel(CommentReactionDoc commentReactionModel) {
        this.commentReactionModel = commentReactionModel;
    }
}
