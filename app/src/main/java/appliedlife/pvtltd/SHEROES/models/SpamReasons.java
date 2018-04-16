
package appliedlife.pvtltd.SHEROES.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpamReasons {

    @SerializedName("user")
    @Expose
    private List<Spam> user = null;

    @SerializedName("post")
    @Expose
    private List<Spam> post = null;

    @SerializedName("comment")
    @Expose
    private List<Spam> comment = null;

    public List<Spam> getPost() {
        return post;
    }

    public void setPost(List<Spam> post) {
        this.post = post;
    }

    public List<Spam> getComment() {
        return comment;
    }

    public void setComment(List<Spam> comment) {
        this.comment = comment;
    }

    public List<Spam> getUser() {
        return user;
    }

    public void setUser(List<Spam> user) {
        this.user = user;
    }
}
