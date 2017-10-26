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
    private List<Comment> commentList = null;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }


}