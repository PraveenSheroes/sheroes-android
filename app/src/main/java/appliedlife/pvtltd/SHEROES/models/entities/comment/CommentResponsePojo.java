package appliedlife.pvtltd.SHEROES.models.entities.comment;

/**
 * Created by Praveen_Singh on 15-02-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;
import appliedlife.pvtltd.SHEROES.models.entities.like.FieldErrorMessageMap;

public class CommentResponsePojo extends BaseResponse {

    @SerializedName("docs")
    @Expose
    private List<CommentDoc> commentDocList = null;
    @SerializedName("fieldErrorMessageMap")
    @Expose
    private FieldErrorMessageMap fieldErrorMessageMap;
    @SerializedName("numFound")
    @Expose
    private int numFound;
    @SerializedName("start")
    @Expose
    private int start;
    @SerializedName("status")
    @Expose
    private String status;


    public List<CommentDoc> getCommentDocList() {
        return commentDocList;
    }

    public void setCommentDocList(List<CommentDoc> commentDocList) {
        this.commentDocList = commentDocList;
    }

    public FieldErrorMessageMap getFieldErrorMessageMap() {
        return fieldErrorMessageMap;
    }

    public void setFieldErrorMessageMap(FieldErrorMessageMap fieldErrorMessageMap) {
        this.fieldErrorMessageMap = fieldErrorMessageMap;
    }

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}