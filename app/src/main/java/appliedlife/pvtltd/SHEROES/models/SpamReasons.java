
package appliedlife.pvtltd.SHEROES.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpamReasons {

    @SerializedName("user")
    @Expose
    private List<Spam> userTypeSpams = null;

    @SerializedName("post")
    @Expose
    private List<Spam> postTypeSpams = null;

    @SerializedName("comment")
    @Expose
    private List<Spam> commentTypeSpams = null;

    public List<Spam> getCommentTypeSpams() {
        return commentTypeSpams;
    }

    public void setCommentTypeSpams(List<Spam> commentTypeSpams) {
        this.commentTypeSpams = commentTypeSpams;
    }

    public List<Spam> getPostTypeSpams() {
        return postTypeSpams;
    }

    public void setPostTypeSpams(List<Spam> postTypeSpams) {
        this.postTypeSpams = postTypeSpams;
    }

    public List<Spam> getUserTypeSpams() {
        return userTypeSpams;
    }

    public void setUserTypeSpams(List<Spam> userTypeSpams) {
        this.userTypeSpams = userTypeSpams;
    }
}
