package appliedlife.pvtltd.SHEROES.models.entities.comment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 24-01-2017.
 */

public class CommentResponse extends BaseResponse{
    @SerializedName("listOfComment")
    @Expose
    private List<CommentsList> listOfComment = null;
    @SerializedName("listOfReaction")
    @Expose
    private List<ReactionList> listOfReaction = null;


    public List<CommentsList> getListOfComment() {
        return listOfComment;
    }

    public void setListOfComment(List<CommentsList> listOfComment) {
        this.listOfComment = listOfComment;
    }

    public List<ReactionList> getListOfReaction() {
        return listOfReaction;
    }

    public void setListOfReaction(List<ReactionList> listOfReaction) {
        this.listOfReaction = listOfReaction;
    }
}
