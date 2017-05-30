package appliedlife.pvtltd.SHEROES.models.entities.challenge;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import appliedlife.pvtltd.SHEROES.basecomponents.baseresponse.BaseResponse;

/**
 * Created by Praveen_Singh on 25-05-2017.
 */

public class ChallengeDataItem extends BaseResponse {
    private int itemPosition;
    private int stateChallengeAfterAccept;
    @SerializedName("completion_percent")
    private int completionPercent;
    @SerializedName("is_accepted")
    @Expose
    private boolean is_accepted;
    @SerializedName("author_img_url")
    @Expose
    private String authorImgUrl;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("challenge_id")
    @Expose
    private long challengeId;
    @SerializedName("challenge_duration")
    @Expose
    private int challengeDuration;
    @SerializedName("challenge_name")
    @Expose
    private String challengeName;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("solr_ignore_author_id")
    @Expose
    private int solrIgnoreAuthorId;
    @SerializedName("solr_ignore_create_by")
    @Expose
    private int solrIgnoreCreateBy;
    @SerializedName("total_people_accepted")
    @Expose
    private int totalPeopleAccepted;
    @SerializedName("total_people_accepted_delhi")
    @Expose
    private int totalPeopleAcceptedDelhi;

    @SerializedName("deep_link_url")
    @Expose
    private String deepLinkUrl;
    public String getAuthorImgUrl() {
        return authorImgUrl;
    }

    public void setAuthorImgUrl(String authorImgUrl) {
        this.authorImgUrl = authorImgUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }



    public int getChallengeDuration() {
        return challengeDuration;
    }

    public void setChallengeDuration(int challengeDuration) {
        this.challengeDuration = challengeDuration;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getSolrIgnoreAuthorId() {
        return solrIgnoreAuthorId;
    }

    public void setSolrIgnoreAuthorId(int solrIgnoreAuthorId) {
        this.solrIgnoreAuthorId = solrIgnoreAuthorId;
    }

    public int getSolrIgnoreCreateBy() {
        return solrIgnoreCreateBy;
    }

    public void setSolrIgnoreCreateBy(int solrIgnoreCreateBy) {
        this.solrIgnoreCreateBy = solrIgnoreCreateBy;
    }

    public int getTotalPeopleAccepted() {
        return totalPeopleAccepted;
    }

    public void setTotalPeopleAccepted(int totalPeopleAccepted) {
        this.totalPeopleAccepted = totalPeopleAccepted;
    }

    public int getTotalPeopleAcceptedDelhi() {
        return totalPeopleAcceptedDelhi;
    }

    public void setTotalPeopleAcceptedDelhi(int totalPeopleAcceptedDelhi) {
        this.totalPeopleAcceptedDelhi = totalPeopleAcceptedDelhi;
    }


    public long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(long challengeId) {
        this.challengeId = challengeId;
    }

    public ChallengeDataItem() {
    }

    public int getStateChallengeAfterAccept() {
        return stateChallengeAfterAccept;
    }

    public void setStateChallengeAfterAccept(int stateChallengeAfterAccept) {
        this.stateChallengeAfterAccept = stateChallengeAfterAccept;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public boolean is_accepted() {
        return is_accepted;
    }

    public void setIs_accepted(boolean is_accepted) {
        this.is_accepted = is_accepted;
    }

    public int getCompletionPercent() {
        return completionPercent;
    }

    public void setCompletionPercent(int completionPercent) {
        this.completionPercent = completionPercent;
    }

    public String getDeepLinkUrl() {
        return deepLinkUrl;
    }

    public void setDeepLinkUrl(String deepLinkUrl) {
        this.deepLinkUrl = deepLinkUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.itemPosition);
        dest.writeInt(this.stateChallengeAfterAccept);
        dest.writeInt(this.completionPercent);
        dest.writeByte(this.is_accepted ? (byte) 1 : (byte) 0);
        dest.writeString(this.authorImgUrl);
        dest.writeString(this.authorName);
        dest.writeLong(this.challengeId);
        dest.writeInt(this.challengeDuration);
        dest.writeString(this.challengeName);
        dest.writeString(this.createdDate);
        dest.writeString(this.endDate);
        dest.writeInt(this.solrIgnoreAuthorId);
        dest.writeInt(this.solrIgnoreCreateBy);
        dest.writeInt(this.totalPeopleAccepted);
        dest.writeInt(this.totalPeopleAcceptedDelhi);
        dest.writeString(this.deepLinkUrl);
    }

    protected ChallengeDataItem(Parcel in) {
        super(in);
        this.itemPosition = in.readInt();
        this.stateChallengeAfterAccept = in.readInt();
        this.completionPercent = in.readInt();
        this.is_accepted = in.readByte() != 0;
        this.authorImgUrl = in.readString();
        this.authorName = in.readString();
        this.challengeId = in.readLong();
        this.challengeDuration = in.readInt();
        this.challengeName = in.readString();
        this.createdDate = in.readString();
        this.endDate = in.readString();
        this.solrIgnoreAuthorId = in.readInt();
        this.solrIgnoreCreateBy = in.readInt();
        this.totalPeopleAccepted = in.readInt();
        this.totalPeopleAcceptedDelhi = in.readInt();
        this.deepLinkUrl = in.readString();
    }

    public static final Creator<ChallengeDataItem> CREATOR = new Creator<ChallengeDataItem>() {
        @Override
        public ChallengeDataItem createFromParcel(Parcel source) {
            return new ChallengeDataItem(source);
        }

        @Override
        public ChallengeDataItem[] newArray(int size) {
            return new ChallengeDataItem[size];
        }
    };
}
