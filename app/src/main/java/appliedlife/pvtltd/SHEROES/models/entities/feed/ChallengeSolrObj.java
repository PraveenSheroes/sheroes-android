package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {ChallengeSolrObj.class,BaseEntityOrParticipantModel.class})
public class ChallengeSolrObj extends BaseEntityOrParticipantModel {
    @SerializedName("challenge_is_public_b")
    private Boolean challengeIsPublic;

    @SerializedName("challenge_last_modified_on_dt")
    private Date challengeLastModifiedOn;

    @SerializedName("challenge_start_date_dt")
    private Date challengeStartDate;

    @SerializedName("challenge_end_date_dt")
    private Date challengeEndDate;

    @SerializedName("challenge_title_s")
    private String challengeTitle;

    @SerializedName("challenge_is_anonymous_b")
    private Boolean challengeIsAnonymous;

    @SerializedName("challenge_accept_post_text_s")
    private String challengeAcceptPostText;

    @SerializedName("challenge_update_post_text_s")
    private String challengeUpdatePostText;

    @SerializedName("challenge_hours_i")
    private Integer challengeHours;

    @SerializedName("challenge_minute_i")
    private Integer challengeMinute;

    @SerializedName("author_participant_id_l")
    private Long authorParticipantId;

    @SerializedName("challenge_author_type_s")
    private String challengeAuthorType;

    @SerializedName("solr_ignore_is_winner")
    private boolean isWinner;

    @SerializedName("solr_ignore_is_challenge_completed")
    private boolean isChallengeCompleted;

    @SerializedName("solr_ignore_not_interested")
    private boolean notInterested;

    @SerializedName("challenge_author_email_id_s")
    private String challengeAuthorEmailId;


    @SerializedName("solr_ignore_winner_address_s")
    private String winnerAddress;

    @SerializedName("solr_ignore_prize_discription_s")
    private String prizeDiscription;

    @SerializedName("solr_ignore_prize_icon_link_url_s")
    private String prizeIcon;

    @SerializedName("solr_ignore_winner_rank_i")
    private Integer winnerRank;

    @SerializedName("challenge_has_winner")
    private Boolean challengeHasWinner;

    @SerializedName("challenge_winner_announcement_dt")
    private Date challengeAnnouncementDate;


    @SerializedName("solr_ignore_is_future_challenge")
    private boolean isFutureChallenge;

    public Boolean getChallengeIsPublic() {
        return challengeIsPublic;
    }

    public void setChallengeIsPublic(Boolean challengeIsPublic) {
        this.challengeIsPublic = challengeIsPublic;
    }
}
