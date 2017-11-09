package appliedlife.pvtltd.SHEROES.models.entities.post;

import android.content.Context;
import android.support.annotation.StringRes;


import org.parceler.Parcel;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;
import appliedlife.pvtltd.SHEROES.utils.stringutils.StringUtil;


/**
 * Created by Ujjwal on 28/04/17.
 */
@Parcel(analyze = {Contest.class, Post.class})
public class Contest extends Post {
    public static final String URL_PATH_STRING = "contests";
    public static final String CONTEST_OBJ = "CONTEST_OBJ";
    public static final String CONTEST_ID = "CONTEST_ID";
    public String shortUrl;
    public int likesCount;
    public boolean isLiked;
    public boolean hasStarted;
    public boolean hasMyPost;
    public int submissionCount;
    public Date startAt;
    public int totalViews;
    public Date endAt;
    public boolean isWinner;
    public Date winnerAnnouncementDate;
    public List<FeedDetail> submissions;
    public String thumbImage;

    //public int totalViews;
    public ContestStatus getContestStatus() {
        return CommonUtil.getContestStatus(this.startAt, this.endAt);
    }

    public String getURLPathString() {
        return URL_PATH_STRING;
    }

    public String getDaysCount() {
        String count;
        switch (getContestStatus()) {
            case ONGOING:
                count = DateUtil.contestDate(this.endAt);
                break;
            case UPCOMING:
                count = DateUtil.contestDate(this.startAt);
                break;
            case COMPLETED:
                count = DateUtil.contestDate(this.endAt);
                break;
            default:
                count = "";
        }
        return count;
    }

    @StringRes
    public int getDaysText() {
        int days;
        switch (getContestStatus()) {
            case ONGOING:
                days = R.string.endsOn;
                break;
            case UPCOMING:
                days = R.string.starts_at;
                break;
            case COMPLETED:
                days = R.string.completed;
                break;
            default:
                days = 0;
        }
        return days;
    }

    public String getParticipantsCount() {
        String count;
        switch (getContestStatus()) {
            case ONGOING:
                count = String.format(Locale.getDefault(), "%d", this.submissionCount);
                break;
            case UPCOMING:
                count = String.format(Locale.getDefault(), "%d", this.likesCount);
                break;
            case COMPLETED:
                count = String.format(Locale.getDefault(), "%d", this.submissionCount);
                break;
            default:
                count = "";
        }
        return count;
    }

    public String getParticipantsText(Context context) {
        String text;
        switch (getContestStatus()) {
            case ONGOING:
                text = context.getResources().getQuantityString(R.plurals.numberOfResponses, this.submissionCount);
                break;
            case UPCOMING:
                text = context.getResources().getString(R.string.joined_by);
                break;
            case COMPLETED:
                text = context.getResources().getQuantityString(R.plurals.numberOfResponses, this.submissionCount);
                break;
            default:
                text = "";
        }
        return text;
    }
}
