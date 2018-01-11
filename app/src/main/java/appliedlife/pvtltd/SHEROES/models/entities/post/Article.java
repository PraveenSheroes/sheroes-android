package appliedlife.pvtltd.SHEROES.models.entities.post;

import android.support.annotation.DrawableRes;
import android.text.Html;

import org.parceler.Parcel;

import java.util.ArrayList;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.comment.Comment;

/**
 * Created by ujjwal on 17/10/17.
 */
@Parcel(analyze = {Article.class, Post.class})
public class Article extends Post {
    public static final String ARTICLE_OBJ = "ARTICLE_OBJ";
    public static final String ARTICLE_ID = "ARTICLE_ID";
    public static final String URL_PATH_STRING = "articles";
    private static final int MIN_READING_TIME = 1;
    private static final int AVG_HUMAN_READING_SPEED = 275;
    public long id;
    public int readingTime;
    public int commentsCount;
    public int likesCount;
    public int totalViews;
    public boolean showComments = true;
    public boolean isThreadClosed;
    public String featureImage;
    public UserProfile author;
    public boolean isBookmarked;
    public boolean isLiked;
    public String deepLink;
    public int thumbImageWidth;
    public int thumbImageHeight;
    public int featureImageWidth;
    public int featureImageHeight;
    public long creatorId;
    public boolean isCreatorMentor;
    public ArrayList<Comment> comments = new ArrayList<>();

    public
    @DrawableRes
    int getBookmarkActivityDrawable() {
        return this.isBookmarked ? R.drawable.vector_bookmarked : R.drawable.vector_unbookmarked;
    }

    public
    @DrawableRes
    int getLikeActivityDrawable() {
        return this.isLiked ? R.drawable.vector_like : R.drawable.vector_unlike;
    }

    public String getReadingTime() {
        if (body == null && readingTime < MIN_READING_TIME) {
            return "";
        }
        String readingTimeSuffix = " mins read";
        return readingTime < MIN_READING_TIME ? calculateReadingTime() + readingTimeSuffix : readingTime + readingTimeSuffix;
    }

    private int calculateReadingTime() {
        String[] wordCount = Html.fromHtml(body).toString().split(" ");
        readingTime =  (wordCount.length/AVG_HUMAN_READING_SPEED < MIN_READING_TIME ? MIN_READING_TIME : wordCount.length/AVG_HUMAN_READING_SPEED);
        return readingTime;
    }
}
