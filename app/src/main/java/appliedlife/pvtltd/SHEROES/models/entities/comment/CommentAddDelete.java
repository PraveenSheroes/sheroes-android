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
    private Comment commentReactionModel = null;


    public Comment getCommentReactionModel() {
        return commentReactionModel;
    }

    public void setCommentReactionModel(Comment commentReactionModel) {
        this.commentReactionModel = commentReactionModel;
    }
}
