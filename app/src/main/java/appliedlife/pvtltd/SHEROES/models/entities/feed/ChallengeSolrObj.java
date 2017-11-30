package appliedlife.pvtltd.SHEROES.models.entities.feed;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by ujjwal on 26/11/17.
 */
@Parcel(analyze = {ChallengeSolrObj.class,FeedDetail.class})
public class ChallengeSolrObj extends FeedDetail {
    @SerializedName("challenge_is_public_b")
    private boolean challengeIsPublic;

    @SerializedName("challenge_last_modified_on_dt")
    private String challengeLastModifiedOn;

    @SerializedName("challenge_start_date_dt")
    private String challengeStartDate;

    @SerializedName("challenge_end_date_dt")
    private String challengeEndDate;

    @SerializedName("challenge_title_s")
    private String challengeTitle;

    @SerializedName("challenge_is_anonymous_b")
    private boolean challengeIsAnonymous;

    @SerializedName("challenge_accept_post_text_s")
    private String challengeAcceptPostText;

    @SerializedName("challenge_update_post_text_s")
    private String challengeUpdatePostText;

    @SerializedName("challenge_hours_i")
    private Integer challengeHours;

    @SerializedName("challenge_minute_i")
    private Integer challengeMinute;

    @SerializedName("author_participant_id_l")
    private long authorParticipantId;

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
    private boolean challengeHasWinner;

    @SerializedName("challenge_winner_announcement_dt")
    private String challengeAnnouncementDate;


    @SerializedName("solr_ignore_is_future_challenge")
    private boolean isFutureChallenge;

    public boolean getChallengeIsPublic() {
        return challengeIsPublic;
    }

    public void setChallengeIsPublic(boolean challengeIsPublic) {
        this.challengeIsPublic = challengeIsPublic;
    }

    public String getChallengeLastModifiedOn() {
        return challengeLastModifiedOn;
    }

    public void setChallengeLastModifiedOn(String challengeLastModifiedOn) {
        this.challengeLastModifiedOn = challengeLastModifiedOn;
    }

    public String getChallengeStartDate() {
        return challengeStartDate;
    }

    public void setChallengeStartDate(String challengeStartDate) {
        this.challengeStartDate = challengeStartDate;
    }

    public String getChallengeEndDate() {
        return challengeEndDate;
    }

    public void setChallengeEndDate(String challengeEndDate) {
        this.challengeEndDate = challengeEndDate;
    }

    public String getChallengeTitle() {
        return challengeTitle;
    }

    public void setChallengeTitle(String challengeTitle) {
        this.challengeTitle = challengeTitle;
    }

    public boolean  getChallengeIsAnonymous() {
        return challengeIsAnonymous;
    }

    public void setChallengeIsAnonymous(boolean  challengeIsAnonymous) {
        this.challengeIsAnonymous = challengeIsAnonymous;
    }

    public String getChallengeAcceptPostText() {
        return challengeAcceptPostText;
    }

    public void setChallengeAcceptPostText(String challengeAcceptPostText) {
        this.challengeAcceptPostText = challengeAcceptPostText;
    }

    public String getChallengeUpdatePostText() {
        return challengeUpdatePostText;
    }

    public void setChallengeUpdatePostText(String challengeUpdatePostText) {
        this.challengeUpdatePostText = challengeUpdatePostText;
    }

    public Integer getChallengeHours() {
        return challengeHours;
    }

    public void setChallengeHours(Integer challengeHours) {
        this.challengeHours = challengeHours;
    }

    public Integer getChallengeMinute() {
        return challengeMinute;
    }

    public void setChallengeMinute(Integer challengeMinute) {
        this.challengeMinute = challengeMinute;
    }

    @Override
    public long getAuthorParticipantId() {
        return authorParticipantId;
    }

    @Override
    public void setAuthorParticipantId(long authorParticipantId) {
        this.authorParticipantId = authorParticipantId;
    }

    public String getChallengeAuthorTypeS() {
        return challengeAuthorType;
    }

    public void setChallengeAuthorTypeS(String challengeAuthorType) {
        this.challengeAuthorType = challengeAuthorType;
    }

    public boolean  isChallengeIsWinner() {
        return isWinner;
    }

    public void setWinner(boolean  winner) {
        isWinner = winner;
    }

    public boolean  isChallengeCompleted() {
        return isChallengeCompleted;
    }

    public void setChallengeCompleted(boolean  challengeCompleted) {
        isChallengeCompleted = challengeCompleted;
    }

    public boolean  isNotInterested() {
        return notInterested;
    }

    public void setNotInterested(boolean  notInterested) {
        this.notInterested = notInterested;
    }

    public String getChallengeAuthorEmailId() {
        return challengeAuthorEmailId;
    }

    public void setChallengeAuthorEmailId(String challengeAuthorEmailId) {
        this.challengeAuthorEmailId = challengeAuthorEmailId;
    }

    public String getWinnerAddress() {
        return winnerAddress;
    }

    public void setWinnerAddress(String winnerAddress) {
        this.winnerAddress = winnerAddress;
    }

    public String getPrizeDiscription() {
        return prizeDiscription;
    }

    public void setPrizeDiscription(String prizeDiscription) {
        this.prizeDiscription = prizeDiscription;
    }

    public String getPrizeIcon() {
        return prizeIcon;
    }

    public void setPrizeIcon(String prizeIcon) {
        this.prizeIcon = prizeIcon;
    }

    public Integer getWinnerRank() {
        return winnerRank;
    }

    public void setWinnerRank(Integer winnerRank) {
        this.winnerRank = winnerRank;
    }

    public boolean  isChallengeHasWinner() {
        return challengeHasWinner;
    }

    public void setChallengeHasWinner(boolean  challengeHasWinner) {
        this.challengeHasWinner = challengeHasWinner;
    }

    public String getChallengeAnnouncementDate() {
        return challengeAnnouncementDate;
    }

    public void setChallengeAnnouncementDate(String challengeAnnouncementDate) {
        this.challengeAnnouncementDate = challengeAnnouncementDate;
    }

    public boolean  isFutureChallenge() {
        return isFutureChallenge;
    }

    public void setFutureChallenge(boolean  futureChallenge) {
        isFutureChallenge = futureChallenge;
    }
}
