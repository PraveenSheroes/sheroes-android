package appliedlife.pvtltd.SHEROES.models.entities.comment;

/**
 * Created by Praveen_Singh on 15-02-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

public class CommentReactionResponsePojo extends BaseResponse {

    @SerializedName("docs")
    @Expose
    private List<CommentReactionDoc> commentReactionDocList = null;

    public List<CommentReactionDoc> getCommentReactionDocList() {
        return commentReactionDocList;
    }

    public void setCommentReactionDocList(List<CommentReactionDoc> commentReactionDocList) {
        this.commentReactionDocList = commentReactionDocList;
    }


}