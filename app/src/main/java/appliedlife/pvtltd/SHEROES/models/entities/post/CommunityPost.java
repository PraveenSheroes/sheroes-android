package appliedlife.pvtltd.SHEROES.models.entities.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import appliedlife.pvtltd.SHEROES.usertagging.mentions.MentionSpan;


/**
 * Created by ujjwal on 17/10/17.
 */
@Parcel(analyze = {CommunityPost.class, Post.class})
public class CommunityPost extends Post {
    public static final String COMMUNITY_POST_OBJ = "COMMUNITY_POST_OBJ";
    public ArrayList<Photo> photos = new ArrayList<>();
    public int createPostRequestFrom;
    public boolean isAnonymous;
    public Community community;
    public boolean isEdit;
    public boolean isMyPost;
    public boolean isPoll;
    public boolean isPostByCommunity;
    public List<Community> myCommunitiesList = new ArrayList<>();
    public int challengeId;
    public String challengeType;
    public boolean isChallengeType;
    public String challengeHashTag;
    public boolean isCompanyAdmin;
    public boolean hasMention;
    public List<MentionSpan> userMentionList=new ArrayList<>();
}
