package appliedlife.pvtltd.SHEROES.models.entities.post;

import android.support.annotation.StringRes;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

import appliedlife.pvtltd.SHEROES.R;
import appliedlife.pvtltd.SHEROES.models.entities.feed.FeedDetail;
import appliedlife.pvtltd.SHEROES.utils.AppConstants;
import appliedlife.pvtltd.SHEROES.utils.CommonUtil;
import appliedlife.pvtltd.SHEROES.utils.ContestStatus;
import appliedlife.pvtltd.SHEROES.utils.DateUtil;


/**
 * Created by Ujjwal on 21/11/17.
 */
@Parcel(analyze = {Contest.class})
public class Contest{
    public static final String URL_PATH_STRING = "contests";
    public static final String CONTEST_OBJ = "CONTEST_OBJ";
    public static final String CONTEST_ID = "CONTEST_ID";
    @SerializedName("challenge_name")
    public String title;

    @SerializedName("challenge_id")
    public int remote_id;

    @SerializedName("author_type")
    public String authorType;

    @SerializedName("author_name")
    public  String authorName;

    @SerializedName("author_image_url")
    public String authorImageUrl;

    public String body;

    @SerializedName("deep_link_url")
    public String shortUrl;

    @SerializedName("is_accepted")
    public String hasJoined;

    public String tag = "#Workplace Challenge";
    public int likesCount;
    public boolean isLiked;
    public boolean hasStarted;

    @SerializedName("solr_ignore_is_challenge_completed")
    public boolean hasMyPost;


    public int submissionCount;

    @SerializedName("created_date")
    public String createdDateString;

    @SerializedName("end_date")
    public String endDateString;
    public Date startAt;
    public int totalViews;
    public Date endAt;

    @SerializedName("challenge_has_winner_b")
    public boolean hasWinner = true;

    @SerializedName("is_challenge_winner_announced_b")
    public boolean isWinnerAnnounced = true;

    @SerializedName("solr_ignore_is_winner")
    public boolean isWinner;

    @SerializedName("Challenge_winner_announcement_dt")
    public String winnerAnnouncementDate;

    @SerializedName("solr_ignore_winner_address_updated")
    public boolean winnerAddressUpdated;

    @SerializedName("solr_ignore_winner_address_s")
    public String mWinnerAddress;

    public List<FeedDetail> submissions;
    public String thumbImage;

    //public int totalViews;
    public ContestStatus getContestStatus() {
        return CommonUtil.getContestStatus(this.getStartAt(), this.getEndAt());
    }

    public String getURLPathString() {
        return URL_PATH_STRING;
    }

    public Date getStartAt() {
        Date date = DateUtil.parseDateFormat(createdDateString, AppConstants.DATE_FORMAT);
        return date;
    }

    public Date getEndAt() {
        Date date = DateUtil.parseDateFormat(endDateString, AppConstants.DATE_FORMAT);
        return date;
    }

    public String getDaysCount() {
        String count;
        Date date = DateUtil.parseDateFormat(createdDateString, AppConstants.DATE_FORMAT);
        switch (getContestStatus()) {
            case ONGOING:
                count = DateUtil.contestDate(this.getEndAt());
                break;
            case UPCOMING:
                count = DateUtil.contestDate(this.getStartAt());
                break;
            case COMPLETED:
                count = DateUtil.contestDate(this.getEndAt());
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
}
